package weChat.service.km;

import org.springframework.transaction.annotation.Transactional;

import weChat.core.metatype.Dto;
import weChat.parameter.IRespParam;

public interface KmbindcardService {
	/**
	 * K米APP绑卡关系表维护
	 * 
	 * @param dto
	 * @return
	 */
	@Transactional
	public IRespParam kmbindcard(Dto dto);

}
