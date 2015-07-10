package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the wj_tbl_interfacecheck database table.
 * 
 */
@Entity
@Table(name="wj_tbl_interfacecheck")
@NamedQuery(name="Interfacecheck.findAll", query="SELECT i FROM Interfacecheck i")
public class Interfacecheck implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int interfaceCheckID;

	private String accessToken;

	private int appID;

	private String appKey;

	private String grantType;

	private Timestamp keyExpireTime;

	private String tempAccessToken;

	private Timestamp updateDateTime;

	public Interfacecheck() {
	}

	public int getInterfaceCheckID() {
		return this.interfaceCheckID;
	}

	public void setInterfaceCheckID(int interfaceCheckID) {
		this.interfaceCheckID = interfaceCheckID;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getAppID() {
		return this.appID;
	}

	public void setAppID(int appID) {
		this.appID = appID;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getGrantType() {
		return this.grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public Timestamp getKeyExpireTime() {
		return this.keyExpireTime;
	}

	public void setKeyExpireTime(Timestamp keyExpireTime) {
		this.keyExpireTime = keyExpireTime;
	}

	public String getTempAccessToken() {
		return this.tempAccessToken;
	}

	public void setTempAccessToken(String tempAccessToken) {
		this.tempAccessToken = tempAccessToken;
	}

	public Timestamp getUpdateDateTime() {
		return this.updateDateTime;
	}

	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

}