package tcb.shms.module.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  公告
 * @author Mark Huang
 * @version 2020/3/18
 */
@Entity
@Table(name="ANNOUNCEMENT")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Announcement extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 公告事項
	 */
	@Column(name="ANNOUNCEMENT_NAME")
	private String announcementName;
	
	/**
	 *    公告日期
	 */
	@Column(name="ANNOUNCEMENT_DATE")
	private Date announcementDate;
	
	/**
	 *    檔案名稱
	 */
	@Column(name="FILE_NAME")
	private String fileName;
	
	/**
	 *    檔案路徑
	 */
	@Column(name="FILE_PATH")
	private String filePath;
	
	/**
	 *    建立人
	 */
	@Column(name="CREATE_ID")
	private String createId;
	
	/**
	 *    建立時間
	 */
	@Column(name="CREATE_TIME")
	private Timestamp createTime;
	
	/**
	 *    變更人
	 */
	@Column(name="EDIT_ID")
	private String editId;
	
	/**
	 *    變更時間
	 */
	@Column(name="EDIT_TIME")
	private Timestamp editTime;

	public String getAnnouncementName() {
		return announcementName;
	}

	public void setAnnouncementName(String announcementName) {
		this.announcementName = announcementName;
	}

	public Date getAnnouncementDate() {
		return announcementDate;
	}

	public void setAnnouncementDate(Date announcementDate) {
		this.announcementDate = announcementDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
	
	
	
}
