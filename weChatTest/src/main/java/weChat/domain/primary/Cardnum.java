package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the wj_tbl_cardnum database table.
 * 
 */
@Entity
@Table(name="wj_tbl_cardnum")
@NamedQuery(name="Cardnum.findAll", query="SELECT c FROM Cardnum c")
public class Cardnum implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cardNumID;

	private String uniqueCode;

	private Timestamp updateDateTime;

	public Cardnum() {
	}

	public int getCardNumID() {
		return this.cardNumID;
	}

	public void setCardNumID(int cardNumID) {
		this.cardNumID = cardNumID;
	}

	public String getUniqueCode() {
		return this.uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public Timestamp getUpdateDateTime() {
		return this.updateDateTime;
	}

	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

}