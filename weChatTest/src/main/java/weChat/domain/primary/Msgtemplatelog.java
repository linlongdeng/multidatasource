package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the wj_tbl_msgtemplatelog database table.
 * 
 */
@Entity
@Table(name="wj_tbl_msgtemplatelog")
@NamedQuery(name="Msgtemplatelog.findAll", query="SELECT m FROM Msgtemplatelog m")
public class Msgtemplatelog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int msgTemplateLogID;

	private int errorCode;

	private String itemColour;

	private String itemValue;

	private String uniqueCode;

	private Timestamp updateDatetime;

	private String wechatKey;

	public Msgtemplatelog() {
	}

	public int getMsgTemplateLogID() {
		return this.msgTemplateLogID;
	}

	public void setMsgTemplateLogID(int msgTemplateLogID) {
		this.msgTemplateLogID = msgTemplateLogID;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getItemColour() {
		return this.itemColour;
	}

	public void setItemColour(String itemColour) {
		this.itemColour = itemColour;
	}

	public String getItemValue() {
		return this.itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getUniqueCode() {
		return this.uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getWechatKey() {
		return this.wechatKey;
	}

	public void setWechatKey(String wechatKey) {
		this.wechatKey = wechatKey;
	}

}