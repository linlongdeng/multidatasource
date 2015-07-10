package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the wj_tbl_sendmsglist database table.
 * 
 */
@Entity
@Table(name="wj_tbl_sendmsglist")
@NamedQuery(name="Sendmsglist.findAll", query="SELECT s FROM Sendmsglist s")
public class Sendmsglist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sendMsgListID;

	private int companyID;

	private int errCode;

	private String kmid;

	private int msgID;

	private Timestamp sendDateTime;

	private byte sendType;

	private byte status;

	public Sendmsglist() {
	}

	public int getSendMsgListID() {
		return this.sendMsgListID;
	}

	public void setSendMsgListID(int sendMsgListID) {
		this.sendMsgListID = sendMsgListID;
	}

	public int getCompanyID() {
		return this.companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getErrCode() {
		return this.errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getKmid() {
		return this.kmid;
	}

	public void setKmid(String kmid) {
		this.kmid = kmid;
	}

	public int getMsgID() {
		return this.msgID;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public Timestamp getSendDateTime() {
		return this.sendDateTime;
	}

	public void setSendDateTime(Timestamp sendDateTime) {
		this.sendDateTime = sendDateTime;
	}

	public byte getSendType() {
		return this.sendType;
	}

	public void setSendType(byte sendType) {
		this.sendType = sendType;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

}