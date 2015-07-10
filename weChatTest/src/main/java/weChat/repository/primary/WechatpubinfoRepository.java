package weChat.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;

import weChat.domain.primary.Wechatpubinfo;

public interface WechatpubinfoRepository extends
		JpaRepository<Wechatpubinfo, Integer> {
	
	/**公众号信息查询
	 * @param wechatPubInfoID	公众号ID
	 * @return
	 */
	public Wechatpubinfo findTopByWechatPubInfoID(int wechatPubInfoID);
}
