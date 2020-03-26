package tcb.shms.module.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  系統參數
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="CONFIG")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Config extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 參數代號
	 */
	@Column(name="CFG_KEY")
	private String cfgKey;
	
	/**
	 *  參數類別
	 */
	@Column(name="CFG_TYPE")
	private String cfgType;
	
	/**
	 *  參數名稱
	 */
	@Column(name="CFG_NAME")
	private String cfgName;
	
	/**
	 *  參數值
	 */
	@Column(name="CFG_VALUE")
	private String cfgValue;
	
	/**
	 *  參數說明
	 */
	@Column(name="CFG_MEMO")
	private String cfgMemo;
	
	/**
	 *  參數使用狀態
	 */
	@Column(name="CFG_IN_USE")
	private Integer cfgInUse;
	
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

	public String getCfgKey() {
		return cfgKey;
	}

	public void setCfgKey(String cfgKey) {
		this.cfgKey = cfgKey;
	}

	public String getCfgType() {
		return cfgType;
	}

	public void setCfgType(String cfgType) {
		this.cfgType = cfgType;
	}

	public String getCfgName() {
		return cfgName;
	}

	public void setCfgName(String cfgName) {
		this.cfgName = cfgName;
	}

	public String getCfgValue() {
		return cfgValue;
	}

	public void setCfgValue(String cfgValue) {
		this.cfgValue = cfgValue;
	}

	public String getCfgMemo() {
		return cfgMemo;
	}

	public void setCfgMemo(String cfgMemo) {
		this.cfgMemo = cfgMemo;
	}

	public int getCfgInUse() {
		return cfgInUse;
	}

	public void setCfgInUse(int cfgInUse) {
		this.cfgInUse = cfgInUse;
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
	
	
}
