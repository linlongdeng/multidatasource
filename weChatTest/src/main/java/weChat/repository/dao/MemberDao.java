package weChat.repository.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> findMemberWithCompany(int CompanyID,String kmID){
		String sql = "SELECT a.membername,case a.sex when '男' then '先生' when '女'	then '女士'	end  as sex,a.cardnum,b.companyname from wj_tbl_member_cache a ,wj_tbl_company b  where a.CompanyID = b.CompanyID and  a.CompanyID=? and a.KMID = ?  LIMIT 1";
		Map<String, Object> resMap = jdbcTemplate.queryForMap(sql,CompanyID,kmID);
		return resMap;
	}
}
