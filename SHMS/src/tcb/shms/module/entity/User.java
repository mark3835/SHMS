package tcb.shms.module.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 * 使用者
 * @author Mark Huang
 * @version 2020/3/4
 */
@Entity
@Table(name="USERS")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class User extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 *    身分證字號
	 */
	@Column(name="ROC_ID")
	private String rocId;
	
	/**
	 * 使用者名稱
	 */
	@Column(name="NAME")
	private String name;
	
	/**
	 * 帳號
	 */
	@Column(name="ACCOUNT")
	private String account;
	
	/**
	 * 單位代號
	 */
	@Column(name="UNIT_ID")
	private String unitId;
	
	/**
	 * 職稱
	 */
	@Column(name="JOB_NAME")
	private String jobName;
	
	/**
	 * 職稱等級
	 */
	@Column(name="JOB_LEVEL")
	private Integer jobLevel;
	
	/**
	 * 電話
	 */
	@Column(name="PHONE")
	private String phone;
	
	/**
	 * EMAIL
	 */
	@Column(name="EMAIL")
	private String email;
	
	/**
	 * BIRTHDAY
	 */
	@Column(name="BIRTHDAY")
	private String birthday;
	
	/**
	 * PAGER
	 */
	@Column(name="PAGER")
	private String pager;
	
	/**
	 * TEL
	 */
	@Column(name="TEL")
	private String tel;
	
	/**
	 * 是否離職 0在職 1離職
	 */
	@Column(name="IS_LEAVE")
	private Integer isLeave;

	public String getRocId() {
		return rocId;
	}

	public void setRocId(String rocId) {
		this.rocId = rocId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Integer getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(Integer jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(int isLeave) {
		this.isLeave = isLeave;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
