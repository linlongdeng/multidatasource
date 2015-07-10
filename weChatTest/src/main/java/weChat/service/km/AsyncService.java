package weChat.service.km;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import weChat.core.metatype.BaseDto;
import weChat.domain.primary.Sendmsgdata;
import weChat.domain.primary.Sendmsgdatadetail;
import weChat.domain.primary.Sendmsglist;
import weChat.parameter.amqp.AmqpReqParam;

/**
 * 专门用于执行异步线程的
 * @author deng
 * @date 2015年6月5日
 * @version 1.0.0
 */
public interface AsyncService {
	/**
	 * 把会员信息修改同步K米
	 * @param param
	 * @throws Exception
	 */
	@Async
	public void syncKM(AmqpReqParam param) throws Exception;
	/**
	 * 绑定K米会员，并维护K米关系绑定表
	 * @param param
	 * @throws Exception
	 */
	@Async
	public void bindKM(AmqpReqParam param) throws Exception;
	
	/**模板消息发送成功后的日志保存
	 * @param historyParamesList
	 * @param sendmsgdatas
	 * @param sendmsglists
	 */
	@Async
	public void saveModelMessageLog(List<Sendmsgdatadetail> historyParamesList,
			List<Sendmsgdata> sendmsgdatas, List<Sendmsglist> sendmsglists) throws Exception;
	
	/**想KM推送模板消息
	 * @param kmTemplateList
	 * @throws Exception
	 */
	@Async
	public void pushTemplate(List<BaseDto> kmTemplateList) throws Exception;

}
