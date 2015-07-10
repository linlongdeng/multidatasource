package weChat.repository.primary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import weChat.domain.primary.Mymsgtemplate;
import weChat.domain.primary.Mymsgtemplateitem;


public interface MymsgtemplateitemRepository extends JpaRepository<Mymsgtemplateitem, Integer> {
	
	
	//@Query("SELECT a.wjTblMymsgtemplateitems FROM Mymsgtemplate a WHERE a.wechatPubInfoID=?1 and a.myMsgTemplateID = ?2 ")
	@Query("from Mymsgtemplateitem a where a.wjTblMymsgtemplate.myMsgTemplateID=?2 and a.wjTblMymsgtemplate.wechatPubInfoID=?1 order by  a.itemIndex")
	public List<Mymsgtemplateitem> queryMsgItemByWechatIDAndMsgTempID(int wechatpubinfoid,int myMsgTemplateID);
	
	
}
