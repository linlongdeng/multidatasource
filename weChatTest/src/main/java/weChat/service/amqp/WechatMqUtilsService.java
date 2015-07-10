package weChat.service.amqp;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weChat.core.metatype.BaseDto;
import weChat.domain.primary.Cardnum;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.common.CommonParam;
import weChat.repository.primary.CardnumRepository;
import weChat.utils.AppConstants;



/**
 * MQ工具方法类
 * @author deng
 * @date 2015年6月23日
 * @version 1.0.0
 */
@Service
public class WechatMqUtilsService {
	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private CardnumRepository cardnumRepository;
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
	
	/**
	 * 调用MQ服务工具方法
	 * @param cmdid
	 * @param companycode
	 * @param wechatpubinfoid
	 * @param otherPamas
	 * @return
	 * @throws Exception 
	 */
	public CommonParam invokeMqService(String cmdid, String companycode, int wechatpubinfoid, BaseDto otherParam) throws Exception{
		AmqpReqParam mqParam = new AmqpReqParam();
		mqParam.setCmdid(cmdid);
		mqParam.setCompanycode(companycode);
		mqParam.setWechatpubinfoid(wechatpubinfoid);
		mqParam.setParams(otherParam);
		WechatMqService mqService = (WechatMqService) beanFactory.getBean(cmdid + AppConstants.WJMQ_SUFFIX);
		CommonParam resp = (CommonParam) mqService.handle(mqParam);
		return resp;
	}
}
