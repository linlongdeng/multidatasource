package weChat.domain.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the wj_tbl_sceneqrcode database table.
 * 
 */
@Entity
@Table(name="wj_tbl_sceneqrcode")
@NamedQuery(name="Sceneqrcode.findAll", query="SELECT s FROM Sceneqrcode s")
public class Sceneqrcode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sceneID;

	private String sceneStr;

	private Timestamp updateDatetime;

	public Sceneqrcode() {
	}

	public int getSceneID() {
		return this.sceneID;
	}

	public void setSceneID(int sceneID) {
		this.sceneID = sceneID;
	}

	public String getSceneStr() {
		return this.sceneStr;
	}

	public void setSceneStr(String sceneStr) {
		this.sceneStr = sceneStr;
	}

	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

}