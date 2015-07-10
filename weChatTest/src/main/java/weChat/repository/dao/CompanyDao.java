package weChat.repository.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CompanyDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**根据商家编码，获取带默认等级的商家信息
	 * @param companyCode	商家编码
	 * @param onlineType	线上申请的标志
	 * @return
	 */
	public Map<String, Object> findCompanyWithGrand(String companyCode,int onlineType){
		String sql = "SELECT a.companyid,a.gradeid,gradename from wj_tbl_company b ,wj_tbl_gradecollect a where b.CompanyCode=? and a.CompanyID=b.CompanyID and a.useonlineapp = ?";
		Map<String, Object> resMap = jdbcTemplate.queryForMap(sql,companyCode,onlineType);
		return resMap;
	}
}
