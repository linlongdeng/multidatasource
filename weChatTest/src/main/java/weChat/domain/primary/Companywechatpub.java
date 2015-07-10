package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the wj_tbl_companywechatpub database table.
 * 
 */
@Entity
@Table(name="wj_tbl_companywechatpub")
@NamedQuery(name="Companywechatpub.findAll", query="SELECT c FROM Companywechatpub c")
public class Companywechatpub implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int companyWechatPubID;

	private int companyID;

	private int wechatPubInfoID;

	public Companywechatpub() {
	}

	public int getCompanyWechatPubID() {
		return this.companyWechatPubID;
	}

	public void setCompanyWechatPubID(int companyWechatPubID) {
		this.companyWechatPubID = companyWechatPubID;
	}

	public int getCompanyID() {
		return this.companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getWechatPubInfoID() {
		return this.wechatPubInfoID;
	}

	public void setWechatPubInfoID(int wechatPubInfoID) {
		this.wechatPubInfoID = wechatPubInfoID;
	}

}