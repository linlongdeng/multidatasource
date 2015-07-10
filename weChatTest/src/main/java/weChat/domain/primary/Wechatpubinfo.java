package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the wj_tbl_wechatpubinfo database table.
 * 
 */
@Entity
@Table(name="wj_tbl_wechatpubinfo")
@NamedQuery(name="Wechatpubinfo.findAll", query="SELECT w FROM Wechatpubinfo w")
public class Wechatpubinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int wechatPubInfoID;

	private String access_token;

	private String appID;

	private String appSecret;

	private byte appType;

	private int expiretokentime;

	private String jsTicket;

	private String wechatPubID;

	private String wechatPubName;

	private String wechatUrl;

	public Wechatpubinfo() {
	}

	public int getWechatPubInfoID() {
		return this.wechatPubInfoID;
	}

	public void setWechatPubInfoID(int wechatPubInfoID) {
		this.wechatPubInfoID = wechatPubInfoID;
	}

	public String getAccess_token() {
		return this.access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getAppID() {
		return this.appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public byte getAppType() {
		return this.appType;
	}

	public void setAppType(byte appType) {
		this.appType = appType;
	}

	public int getExpiretokentime() {
		return this.expiretokentime;
	}

	public void setExpiretokentime(int expiretokentime) {
		this.expiretokentime = expiretokentime;
	}

	public String getJsTicket() {
		return this.jsTicket;
	}

	public void setJsTicket(String jsTicket) {
		this.jsTicket = jsTicket;
	}

	public String getWechatPubID() {
		return this.wechatPubID;
	}

	public void setWechatPubID(String wechatPubID) {
		this.wechatPubID = wechatPubID;
	}

	public String getWechatPubName() {
		return this.wechatPubName;
	}

	public void setWechatPubName(String wechatPubName) {
		this.wechatPubName = wechatPubName;
	}

	public String getWechatUrl() {
		return this.wechatUrl;
	}

	public void setWechatUrl(String wechatUrl) {
		this.wechatUrl = wechatUrl;
	}

}