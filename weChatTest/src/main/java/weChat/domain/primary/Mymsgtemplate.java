package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the wj_tbl_mymsgtemplate database table.
 * 
 */
@Entity
@Table(name="wj_tbl_mymsgtemplate")
@NamedQuery(name="Mymsgtemplate.findAll", query="SELECT m FROM Mymsgtemplate m")
public class Mymsgtemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int myMsgTemplateID;

	private int expireTime;

	private String industry1;

	private String industry2;

	private byte status;

	private String templateID;

	private String templateNo;

	private String templateTitle;

	private byte templateType;

	private String topColour;

	private Timestamp updateDatetime;

	private String url;

	private int urlTypeID;

	private int wechatPubInfoID;

	//bi-directional many-to-one association to Mymsgtemplateitem
	@OneToMany(mappedBy="wjTblMymsgtemplate")
	private List<Mymsgtemplateitem> wjTblMymsgtemplateitems;

	public Mymsgtemplate() {
	}

	public int getMyMsgTemplateID() {
		return this.myMsgTemplateID;
	}

	public void setMyMsgTemplateID(int myMsgTemplateID) {
		this.myMsgTemplateID = myMsgTemplateID;
	}

	public int getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	public String getIndustry1() {
		return this.industry1;
	}

	public void setIndustry1(String industry1) {
		this.industry1 = industry1;
	}

	public String getIndustry2() {
		return this.industry2;
	}

	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getTemplateID() {
		return this.templateID;
	}

	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}

	public String getTemplateNo() {
		return this.templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}

	public String getTemplateTitle() {
		return this.templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public byte getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(byte templateType) {
		this.templateType = templateType;
	}

	public String getTopColour() {
		return this.topColour;
	}

	public void setTopColour(String topColour) {
		this.topColour = topColour;
	}

	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUrlTypeID() {
		return this.urlTypeID;
	}

	public void setUrlTypeID(int urlTypeID) {
		this.urlTypeID = urlTypeID;
	}

	public int getWechatPubInfoID() {
		return this.wechatPubInfoID;
	}

	public void setWechatPubInfoID(int wechatPubInfoID) {
		this.wechatPubInfoID = wechatPubInfoID;
	}

	public List<Mymsgtemplateitem> getWjTblMymsgtemplateitems() {
		return this.wjTblMymsgtemplateitems;
	}

	public void setWjTblMymsgtemplateitems(List<Mymsgtemplateitem> wjTblMymsgtemplateitems) {
		this.wjTblMymsgtemplateitems = wjTblMymsgtemplateitems;
	}

	public Mymsgtemplateitem addWjTblMymsgtemplateitem(Mymsgtemplateitem wjTblMymsgtemplateitem) {
		getWjTblMymsgtemplateitems().add(wjTblMymsgtemplateitem);
		wjTblMymsgtemplateitem.setWjTblMymsgtemplate(this);

		return wjTblMymsgtemplateitem;
	}

	public Mymsgtemplateitem removeWjTblMymsgtemplateitem(Mymsgtemplateitem wjTblMymsgtemplateitem) {
		getWjTblMymsgtemplateitems().remove(wjTblMymsgtemplateitem);
		wjTblMymsgtemplateitem.setWjTblMymsgtemplate(null);

		return wjTblMymsgtemplateitem;
	}

}