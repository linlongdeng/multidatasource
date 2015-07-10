package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the wj_tbl_sendmsgdata database table.
 * 
 */
@Entity
@Table(name="wj_tbl_sendmsgdata")
@NamedQuery(name="Sendmsgdata.findAll", query="SELECT s FROM Sendmsgdata s")
public class Sendmsgdata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sendMsgDataID;

	private int companyID;

	private byte dataType;

	private String kmid;

	private Timestamp sendDateTime;

	private byte status;

	private String uniqueCode;

	public Sendmsgdata() {
	}

	public int getSendMsgDataID() {
		return this.sendMsgDataID;
	}

	public void setSendMsgDataID(int sendMsgDataID) {
		this.sendMsgDataID = sendMsgDataID;
	}

	public int getCompanyID() {
		return this.companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public byte getDataType() {
		return this.dataType;
	}

	public void setDataType(byte dataType) {
		this.dataType = dataType;
	}

	public String getKmid() {
		return this.kmid;
	}

	public void setKmid(String kmid) {
		this.kmid = kmid;
	}

	public Timestamp getSendDateTime() {
		return this.sendDateTime;
	}

	public void setSendDateTime(Timestamp sendDateTime) {
		this.sendDateTime = sendDateTime;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getUniqueCode() {
		return this.uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

}