package weChat.repository.primary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import weChat.domain.primary.WeixinUserbind;

public interface WeixinUserbindRepository extends JpaRepository<WeixinUserbind, Integer> {

	
	public WeixinUserbind findFirstByKmidAndStatus(String kmid, byte status);
	
	/**
	 * @param status 会员状态
	 * @param kmidList 查询的会员集合
	 * @return
	 */
	public List<WeixinUserbind> findByStatusAndKmidIn(byte status,List<String> kmidList);
}
