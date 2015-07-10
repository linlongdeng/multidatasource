package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the wj_tbl_sendmsgdatadetail database table.
 * 
 */
@Entity
@Table(name="wj_tbl_sendmsgdatadetail")
@NamedQuery(name="Sendmsgdatadetail.findAll", query="SELECT s FROM Sendmsgdatadetail s")
public class Sendmsgdatadetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sendMsgDataDetailID;

	private String itemName;

	private String itemValue;

	private String uniqueCode;

	public Sendmsgdatadetail() {
	}

	public int getSendMsgDataDetailID() {
		return this.sendMsgDataDetailID;
	}

	public void setSendMsgDataDetailID(int sendMsgDataDetailID) {
		this.sendMsgDataDetailID = sendMsgDataDetailID;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

}