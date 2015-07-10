package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the wj_tbl_kmbindcard database table.
 * 
 */
@Entity
@Table(name="wj_tbl_kmbindcard")
@NamedQuery(name="Kmbindcard.findAll", query="SELECT k FROM Kmbindcard k")
public class Kmbindcard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int kmBindCardID;

	private Timestamp bindTime;

	private int companyID;

	private int customerID;

	private String kmid;

	private String openID;

	private byte source;

	private byte status;

	public Kmbindcard() {
	}

	public int getKmBindCardID() {
		return this.kmBindCardID;
	}

	public void setKmBindCardID(int kmBindCardID) {
		this.kmBindCardID = kmBindCardID;
	}

	public Timestamp getBindTime() {
		return this.bindTime;
	}

	public void setBindTime(Timestamp bindTime) {
		this.bindTime = bindTime;
	}

	public int getCompanyID() {
		return this.companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getCustomerID() {
		return this.customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
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

	public byte getSource() {
		return this.source;
	}

	public void setSource(byte source) {
		this.source = source;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

}