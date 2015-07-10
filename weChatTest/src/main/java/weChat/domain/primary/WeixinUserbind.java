package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the wj_tbl_weixin_userbind database table.
 * 
 */
@Entity
@Table(name="wj_tbl_weixin_userbind")
@NamedQuery(name="WeixinUserbind.findAll", query="SELECT w FROM WeixinUserbind w")
public class WeixinUserbind implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userbindID;

	private byte bindSource;

	private Timestamp bindTime;

	private String kmid;

	private String openID;

	private byte status;

	public WeixinUserbind() {
	}

	public int getUserbindID() {
		return this.userbindID;
	}

	public void setUserbindID(int userbindID) {
		this.userbindID = userbindID;
	}

	public byte getBindSource() {
		return this.bindSource;
	}

	public void setBindSource(byte bindSource) {
		this.bindSource = bindSource;
	}

	public Timestamp getBindTime() {
		return this.bindTime;
	}

	public void setBindTime(Timestamp bindTime) {
		this.bindTime = bindTime;
	}

	public String getKmid() {
		return this.kmid;
	}

	public void setKmid(String kmid) {
		this.kmid = kmid;
	}

	public String getOpenID() {
		return this.openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

}