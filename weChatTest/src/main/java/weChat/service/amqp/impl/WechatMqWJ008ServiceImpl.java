package weChat.service.amqp.impl;

import static weChat.core.utils.ValidationUtils.rejectIfEmpty;
import static weChat.core.utils.ValidationUtils.rejectParamError;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import weChat.core.metatype.BaseDto;
import weChat.core.rabbit.RabbitClient;
import weChat.core.rabbit.RabbitClientConfig;
import weChat.core.utils.CommonUtils;
import weChat.core.utils.StringUtils;
import weChat.domain.primary.Cardnum;
import weChat.parameter.IRespParam;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.common.CommonParam;
import weChat.repository.primary.CardnumRepository;
import weChat.service.amqp.WechatMqService;
import weChat.service.amqp.WechatMqUtilsService;
import weChat.service.common.ValidationService;
import weChat.service.km.AsyncService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;

/**
 * 会员卡绑定
 * 
 * @author deng
 * @date 2015年6月5日
 * @version 1.0.0
 */
@Service("WJ008" + AppConstants.WJMQ_SUFFIX)
public class WechatMqWJ008ServiceImpl extends WechatMqService {
	@Autowired
	private CardnumRepository cardnumRepository;
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private WechatMqUtilsService wechatMqUtilsService;

	@Autowired
	public WechatMqWJ008ServiceImpl(RabbitClient rabbitClient,
			RabbitClientConfig config, ValidationService validationService) {
		super(rabbitClient, config, validationService);
	}

	@Override
	public IRespParam handle(AmqpReqParam param) throws Exception {
		// 校验数据
		validate(param);
		BaseDto pDto = param.getParams();
		// 生成电子会员卡号
		if (CommonUtils.isEmpty(pDto.getAsString("kmid"))) {
			String cardNumID = wechatMqUtilsService.createKmCardId();
			pDto.put("kmid", cardNumID);
		}
		// 发送MQ消息
		CommonParam commonParam = (CommonParam) sendMQ(param);
		Integer ret = commonParam.getAsInteger("ret");
		// 判断线下系统绑定会员卡是否成功
		String mobile = pDto.getAsString("mobile");

		Boolean iskm = pDto.getAsBoolean("iskm") != null ? pDto
				.getAsBoolean("iskm") : false;
		if (AppUtils.checkSuccess(ret)) {
			// 是否是K米绑卡，如果是K米绑卡，就不用通知K米了
			if (CommonUtils.isNotEmpty(mobile) && !iskm) {
				// 通知K米，并维护K米关系绑定表
				asyncService.bindKM(param);
			}
		}
		return commonParam;

	}

	/**
	 * 生成KM电子会员卡
	 * 
	 * @return
	 */
	public String createKmCardId() {
		String randomStr = UUID.randomUUID().toString().replaceAll("-", "")
				.substring(8);
		Cardnum cardnum = new Cardnum();
		cardnum.setUniqueCode(randomStr);
		cardnum.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
		cardnumRepository.save(cardnum);
		int cardNumID = cardnum.getCardNumID();
		String cardnumidstr = String.format("%09d", cardNumID);
		return cardnumidstr;

	}

	@Override
	public void validate(Object target, Errors e) {
		BaseDto params = ((AmqpReqParam) target).getParams();
		rejectIfEmpty(params, "param", e, "cardnum", "validatetype");
		// 前面校验通过
		if (!e.hasErrors()) {
			String validatetype = params.getAsString("validatetype");
			// 验证类型 1随机码，2证件，3密码
			if (StringUtils.containStr(validatetype, "1", "2", "3")) {
				switch (validatetype) {
				case "1":
					rejectIfEmpty(params.get("mobile"), "mobile", e);
					break;
				case "2":
					rejectIfEmpty(params.get("papernumber"), "papernumber", e);
					break;
				case "3":
					rejectIfEmpty(params.get("memberpsw"), "memberpsw", e);
					break;
				}

			} else {
				rejectParamError("validatetype", e);
			}
		}

	}

}
