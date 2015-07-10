package weChat.repository.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import weChat.utils.AppConstants;


@Component
public class TemplateDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getCompanyTemplate(int wechatpubinfoid,int msgTemplateID){
		String sql ="SELECT a.TemplateID,a.TopColour,a.Url,a.ExpireTime,b.MyMsgTemplateID,b.ItemIndex,b.ItemName,b.ItemNameCn,b.WechatKey,b.ItemValue, b.ItemColour from wj_tbl_mymsgtemplate a ,wj_tbl_mymsgtemplateitem b where a.MyMsgTemplateID = b.MyMsgTemplateID\n" +
				"and a.WechatPubInfoID = ? and a.TemplateType = ? and a.Status = ? ORDER BY b.ItemIndex";
		return jdbcTemplate.queryForList(sql,wechatpubinfoid,msgTemplateID,AppConstants.MODEL_MESSAGE_ACTIVE);
	}
	
	
	public String findCompanyTemplateTitle(int msgTemplateID){
		String sql ="SELECT TemplateTitle from wj_tbl_mymsgtemplate where TemplateType = ?";
		return (String) jdbcTemplate.queryForMap(sql,msgTemplateID).get("TemplateTitle");
	}
}
