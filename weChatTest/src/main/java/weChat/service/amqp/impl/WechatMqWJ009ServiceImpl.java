package weChat.service.amqp.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import weChat.core.metatype.BaseDto;
import weChat.core.rabbit.RabbitClient;
import weChat.core.rabbit.RabbitClientConfig;
import weChat.core.utils.CommonUtils;
import weChat.core.utils.ValidationUtils;
import weChat.domain.primary.Onlinemember;
import weChat.parameter.IRespParam;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.common.CommonParam;
import weChat.repository.dao.CompanyDao;
import weChat.repository.primary.OnlinememberRepository;
import weChat.service.amqp.WechatMqService;
import weChat.service.amqp.WechatMqUtilsService;
import weChat.service.common.ValidationService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;

@Service("WJ009" + AppConstants.WJMQ_SUFFIX)
public class WechatMqWJ009ServiceImpl extends WechatMqService {
	
	@Autowired
	private WechatMqUtilsService wechatMqUtilsService;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private OnlinememberRepository onlinememberRepository;
	
	@Autowired
	public WechatMqWJ009ServiceImpl(RabbitClient rabbitClient,
			RabbitClientConfig config, ValidationService validationService) {
		super(rabbitClient, config, validationService);
	}
	
	@Override
	public IRespParam handle(AmqpReqParam param) throws Exception {
		//校验数据
		validate(param);
		String kmID = wechatMqUtilsService.createKmCardId();
		param.getParams().put("kmid", kmID);
		CommonParam commonParam = (CommonParam) sendMQ(param);
		BaseDto pDto = param.getParams();
		Integer ret = commonParam.getAsInteger("ret");
		if (AppUtils.checkSuccess(ret)) {
			Map<String, Object> resMap=companyDao.findCompanyWithGrand(param.getCompanycode(),AppConstants.onlineApp);
			Onlinemember onlinemember = new Onlinemember();
			onlinemember.setKmID(kmID);
			onlinemember.setCompanyID((int) resMap.get("companyid"));
			onlinemember.setGradeID((int)resMap.get("gradeid"));
			onlinemember.setMemberName(pDto.getAsString("membername"));
			onlinemember.setBirthday(pDto.getAsDate("birthday"));
			onlinemember.setSex(pDto.getAsString("sex"));
			onlinemember.setPaperType(pDto.getAsString("papertype"));
			onlinemember.setPaperNumber(pDto.getAsString("papernumber"));
			onlinemember.setMobile(pDto.getAsString("mobile"));
			onlinemember.setUpdateDatetime(CommonUtils.currentTimestamp());
			onlinememberRepository.save(onlinemember);
		}
		return commonParam;
	}
	
	@Override
	public void validate(Object target, Errors e) {
		AmqpReqParam rreqParam = (AmqpReqParam) target;
		BaseDto params = rreqParam.getParams();
		ValidationUtils.rejectIfEmpty(params, "param", e, "membername", "sex",
				"mobile", "birthday");

	}
	
}
