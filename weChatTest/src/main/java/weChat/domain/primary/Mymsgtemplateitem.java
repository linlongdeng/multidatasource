package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the wj_tbl_mymsgtemplateitem database table.
 * 
 */
@Entity
@Table(name="wj_tbl_mymsgtemplateitem")
@NamedQuery(name="Mymsgtemplateitem.findAll", query="SELECT m FROM Mymsgtemplateitem m")
public class Mymsgtemplateitem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int myMsgTemplateItemID;

	private String itemColour;

	private byte itemIndex;

	private String itemName;

	private String itemNameCn;

	private String itemValue;

	private String wechatKey;

	//bi-directional many-to-one association to Mymsgtemplate
	@ManyToOne
	@JoinColumn(name="mymsgtemplateid")
	private Mymsgtemplate wjTblMymsgtemplate;

	public Mymsgtemplateitem() {
	}

	public int getMyMsgTemplateItemID() {
		return this.myMsgTemplateItemID;
	}

	public void setMyMsgTemplateItemID(int myMsgTemplateItemID) {
		this.myMsgTemplateItemID = myMsgTemplateItemID;
	}

	public String getItemColour() {
		return this.itemColour;
	}

	public void setItemColour(String itemColour) {
		this.itemColour = itemColour;
	}

	public byte getItemIndex() {
		return this.itemIndex;
	}

	public void setItemIndex(byte itemIndex) {
		this.itemIndex = itemIndex;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemNameCn() {
		return this.itemNameCn;
	}

	public void setItemNameCn(String itemNameCn) {
		this.itemNameCn = itemNameCn;
	}

	public String getItemValue() {
		return this.itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getWechatKey() {
		return this.wechatKey;
	}

	public void setWechatKey(String wechatKey) {
		this.wechatKey = wechatKey;
	}

	public Mymsgtemplate getWjTblMymsgtemplate() {
		return this.wjTblMymsgtemplate;
	}

	public void setWjTblMymsgtemplate(Mymsgtemplate wjTblMymsgtemplate) {
		this.wjTblMymsgtemplate = wjTblMymsgtemplate;
	}

}