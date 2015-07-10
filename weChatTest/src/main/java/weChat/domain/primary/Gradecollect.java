package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the wj_tbl_gradecollect database table.
 * 
 */
@Entity
@Table(name="wj_tbl_gradecollect")
@NamedQuery(name="Gradecollect.findAll", query="SELECT g FROM Gradecollect g")
public class Gradecollect implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int gradeCollectID;

	/**图片ID默认为空，所以必须是Integer**/
	private Integer cardPicID;

	private int companyID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String gradeCode;

	private int gradeID;

	private String gradeName;

	private String memberRights;

	private byte status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	private byte useIntegral;

	private byte useOnlineApp;

	private byte useStorage;

	private int wechatPubInfoID;

	public Gradecollect() {
	}

	public int getGradeCollectID() {
		return this.gradeCollectID;
	}

	public void setGradeCollectID(int gradeCollectID) {
		this.gradeCollectID = gradeCollectID;
	}

	public Integer getCardPicID() {
		return this.cardPicID;
	}

	public void setCardPicID(Integer cardPicID) {
		this.cardPicID = cardPicID;
	}

	public int getCompanyID() {
		return this.companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGradeCode() {
		return this.gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public int getGradeID() {
		return this.gradeID;
	}

	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getMemberRights() {
		return this.memberRights;
	}

	public void setMemberRights(String memberRights) {
		this.memberRights = memberRights;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public byte getUseIntegral() {
		return this.useIntegral;
	}

	public void setUseIntegral(byte useIntegral) {
		this.useIntegral = useIntegral;
	}

	public byte getUseOnlineApp() {
		return this.useOnlineApp;
	}

	public void setUseOnlineApp(byte useOnlineApp) {
		this.useOnlineApp = useOnlineApp;
	}

	public byte getUseStorage() {
		return this.useStorage;
	}

	public void setUseStorage(byte useStorage) {
		this.useStorage = useStorage;
	}

	public int getWechatPubInfoID() {
		return this.wechatPubInfoID;
	}

	public void setWechatPubInfoID(int wechatPubInfoID) {
		this.wechatPubInfoID = wechatPubInfoID;
	}

}