package weChat.service.km;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Kmbindcard;
import weChat.domain.primary.WeixinUserbind;
import weChat.parameter.IRespParam;
import weChat.repository.primary.CompanyRepository;
import weChat.repository.primary.KmbindcardRepository;
import weChat.repository.primary.WeixinUserbindRepository;
import weChat.utils.RespUtils;
import static weChat.utils.AppUtils.*;
import static weChat.utils.AppConstants.*;

@Service
public class KmbindcardServiceImpl implements KmbindcardService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WeixinUserbindRepository weixinUserbindRepository;
	@Autowired
	private KmbindcardRepository kmbindcardRepository;
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public IRespParam kmbindcard(Dto dto) {

		String companycode = dto.getAsString("companycode");
		Company company = companyRepository.findFirstByCompanyCode(companycode);
		// 商家不能为空
		assertCompanyNotNull(company);
		String kmid = dto.getAsString("kmid");
		int customerid = dto.getAsInteger("customerid");
		logger.debug("开始更新K米APP绑卡关系表信息, kmid={}, customerid={}", kmid,
				customerid);
		// 把原来绑定关系改为解绑状态
		kmbindcardRepository.setUnbindByKmid(company.getCompanyID(), kmid);
		// /把原来绑定关系改为解绑状态
		kmbindcardRepository.setUnbindByOrCustomerID(company.getCompanyID(),
				customerid);
		// 获取微信绑卡情况
		WeixinUserbind weixinUserbind = weixinUserbindRepository
				.findFirstByKmidAndStatus(kmid, WEIXIN_USER_BIND_STATUS_BIND);
		// 获取用户openid
		String openID = weixinUserbind != null ? weixinUserbind.getOpenID()
				: "";
		Kmbindcard kmbindcard = new Kmbindcard();
		kmbindcard.setOpenID(openID);
		kmbindcard.setKmid(kmid);
		kmbindcard.setBindTime(CommonUtils.currentTimestamp());
		kmbindcard.setCompanyID(company.getCompanyID());
		kmbindcard.setCustomerID(customerid);
		kmbindcard.setStatus(KM_BIND_CARD_STATUS_BIND);
		// 微信绑卡
		kmbindcard.setSource(KM_BIND_CARD_SOURCE_WEIXIN);
		// 保存K米APP绑卡关系信息
		kmbindcardRepository.save(kmbindcard);
		logger.debug("完成更新K米APP绑卡关系表信息, kmid={}, customerid={}", kmid,
				customerid);
		return RespUtils.successMR();

	}

}
