package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the wj_tbl_onlinemember database table.
 * 
 */
@Entity
@Table(name="wj_tbl_onlinemember")
@NamedQuery(name="Onlinemember.findAll", query="SELECT o FROM Onlinemember o")
public class Onlinemember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int onlinememberID;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private int companyID;

	private int gradeID;

	private String kmID;

	private String memberName;

	private String mobile;

	private String paperNumber;

	private String paperType;

	private String sex;

	private Timestamp updateDatetime;

	public Onlinemember() {
	}

	public int getOnlinememberID() {
		return this.onlinememberID;
	}

	public void setOnlinememberID(int onlinememberID) {
		this.onlinememberID = onlinememberID;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getCompanyID() {
		return this.companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getGradeID() {
		return this.gradeID;
	}

	public void setGradeID(int gradeID) {
		this.gradeID = gradeID;
	}

	public String getKmID() {
		return this.kmID;
	}

	public void setKmID(String kmID) {
		this.kmID = kmID;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPaperNumber() {
		return this.paperNumber;
	}

	public void setPaperNumber(String paperNumber) {
		this.paperNumber = paperNumber;
	}

	public String getPaperType() {
		return this.paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

}