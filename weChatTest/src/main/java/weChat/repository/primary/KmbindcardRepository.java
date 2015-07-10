package weChat.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import weChat.domain.primary.Kmbindcard;
import static weChat.utils.AppConstants.*;

public interface KmbindcardRepository extends
		JpaRepository<Kmbindcard, Integer> {

	/**
	 * 查找K米APP绑卡关系
	 * 
	 * @param kmid
	 * @return
	 */
	public Kmbindcard findFirstByKmid(String kmid);

	/**
	 * 修改K米APP绑卡关系表的绑定关系为解绑状态
	 * 
	 * @param companyid
	 * @param kmid
	 * @return
	 */
	@Modifying
	@Query("update Kmbindcard k set k.status= " + KM_BIND_CARD_STATUS_UNBIND
			+ " where k.companyID=?1 and k.status =" + KM_BIND_CARD_STATUS_BIND
			+ " and (k.kmid =?2  ) ")
	public int setUnbindByKmid(int companyid, String kmid
			);
	/**
	 * 修改K米APP绑卡关系表的绑定关系为解绑状态,之所以搞两条，据说是索引的问题
	 * @param companyid
	 * @param customerID
	 * @return
	 */
	@Modifying
	@Query("update Kmbindcard k set k.status= " + KM_BIND_CARD_STATUS_UNBIND
			+ " where k.companyID=?1 and k.status =" + KM_BIND_CARD_STATUS_BIND
			+ " and (k.customerID=?2 ) ")
	public int setUnbindByOrCustomerID(int companyid,
			int customerID);
}
