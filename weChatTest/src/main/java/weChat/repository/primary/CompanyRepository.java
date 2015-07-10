package weChat.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import weChat.domain.primary.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	/**
	 * 通过商家编号查找商家信息
	 * 
	 * @param companyCode
	 * @return
	 */
	public Company findFirstByCompanyCode(String companyCode);
	
	/**商家密码更新
	 * @param companyID
	 * @param newcompanypsw
	 * @return
	 */
	@Modifying
	@Query("update Company  set companyPsw = ?2 where companyID = ?1")
	public int setPSWDByCompanyID(int companyID,String newcompanypsw);
}
