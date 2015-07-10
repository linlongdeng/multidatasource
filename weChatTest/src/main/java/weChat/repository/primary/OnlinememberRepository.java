package weChat.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import weChat.domain.primary.Onlinemember;

public interface OnlinememberRepository extends JpaRepository<Onlinemember, Integer> {
	
	/**
	 * 根据kmid删除在线申请会员缓存表
	 * @author deng
	 * @param kmid
	 * @return
	 */
	@Modifying
	@Query("delete from Onlinemember where kmID=?1")
	public int deleteByKmid(String kmid);

}
