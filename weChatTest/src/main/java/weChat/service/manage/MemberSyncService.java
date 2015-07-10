package weChat.service.manage;

import java.util.List;

import javax.transaction.Transactional;

import weChat.core.metatype.Dto;
import weChat.domain.primary.Company;
import weChat.parameter.IRespParam;

/**会员等级服务
 * @author deng
 * @date 2015年4月23日
 * @version 1.0.0
 */
public interface MemberSyncService {
	/**
	 * 同步会员等级
	 * @param param
	 * @return
	 */
	@Transactional
	public IRespParam memberLevel(Company company, int wechatpubinfoid, List<Dto> data);
	
	
	/**
	 * 会员信息同步
	 * @param param
	 * @return
	 */
	@Transactional
	public IRespParam memberInfo(Company company, int wechatpubinfoid, List<Dto> data);
	
	/**会员消费数据上传
	 * @param company 商家信息
	 * @param wechatpubinfoid 公众号信息
	 * @param data	消费数据
	 * @return
	 */
	@Transactional
	public IRespParam memberConsume(Company company, int wechatpubinfoid, List<Dto> data) throws Exception;
	
	/**会员充值数据上传
	 * @param company 商家信息
	 * @param wechatpubinfoid 公众号信息
	 * @param data	消费数据
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public IRespParam memberRecharge(Company company,int wechatpubinfoid, List<Dto> data) throws Exception;
	
	/**会员积分赠送数据上传
	 * @param company 商家信息
	 * @param wechatpubinfoid 公众号信息
	 * @param data	消费数据
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public IRespParam memberGivingPoints(Company company,int wechatpubinfoid, List<Dto> data) throws Exception;
	
	/**会员积分扣除数据上传
	 * @param company 商家信息
	 * @param wechatpubinfoid 公众号信息
	 * @param data	消费数据
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public IRespParam memberPointsDeduction(Company company,int wechatpubinfoid, List<Dto> data) throws Exception;
	
	/**会员积分兑换物品数据上传
	 * @param company 商家信息
	 * @param wechatpubinfoid 公众号信息
	 * @param data	消费数据
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public IRespParam memberPointsToGift(Company company,int wechatpubinfoid, List<Dto> data) throws Exception;
	
	
	/**会员寄存数据上传
	 * @param company 商家信息
	 * @param wechatpubinfoid 公众号信息
	 * @param data	消费数据
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public IRespParam memberChecked(Company company,int wechatpubinfoid, List<Dto> data) throws Exception;
	
	/**会员支取数据上传
	 * @param company 商家信息
	 * @param wechatpubinfoid 公众号信息
	 * @param data	消费数据
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public IRespParam memberDraw(Company company,int wechatpubinfoid, List<Dto> data) throws Exception;
	
}
