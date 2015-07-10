package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the wj_tbl_company database table.
 * 
 */
@Entity
@Table(name="wj_tbl_company")
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer companyID;

	private String artificialPersonName;

	private String artificialPersonPaperID;

	private String artificialPersonPhone;

	private String companyAddress;

	private String companyCode;

	private String companyMemo;

	private String companyName;

	private String companyPsw;

	private byte companyStatus;

	private String companyType;

	private String companyURL;

	private String mapPositionX;

	private String mapPositionY;

	private String regionCode;

	private String relationName;

	private String relationPhone;

	private String relationQQ;

	public Company() {
	}

	public Integer getCompanyID() {
		return this.companyID;
	}

	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}

	public String getArtificialPersonName() {
		return this.artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getArtificialPersonPaperID() {
		return this.artificialPersonPaperID;
	}

	public void setArtificialPersonPaperID(String artificialPersonPaperID) {
		this.artificialPersonPaperID = artificialPersonPaperID;
	}

	public String getArtificialPersonPhone() {
		return this.artificialPersonPhone;
	}

	public void setArtificialPersonPhone(String artificialPersonPhone) {
		this.artificialPersonPhone = artificialPersonPhone;
	}

	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyMemo() {
		return this.companyMemo;
	}

	public void setCompanyMemo(String companyMemo) {
		this.companyMemo = companyMemo;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPsw() {
		return this.companyPsw;
	}

	public void setCompanyPsw(String companyPsw) {
		this.companyPsw = companyPsw;
	}

	public byte getCompanyStatus() {
		return this.companyStatus;
	}

	public void setCompanyStatus(byte companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyURL() {
		return this.companyURL;
	}

	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
	}

	public String getMapPositionX() {
		return this.mapPositionX;
	}

	public void setMapPositionX(String mapPositionX) {
		this.mapPositionX = mapPositionX;
	}

	public String getMapPositionY() {
		return this.mapPositionY;
	}

	public void setMapPositionY(String mapPositionY) {
		this.mapPositionY = mapPositionY;
	}

	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRelationName() {
		return this.relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getRelationPhone() {
		return this.relationPhone;
	}

	public void setRelationPhone(String relationPhone) {
		this.relationPhone = relationPhone;
	}

	public String getRelationQQ() {
		return this.relationQQ;
	}

	public void setRelationQQ(String relationQQ) {
		this.relationQQ = relationQQ;
	}

}