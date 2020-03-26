package tcb.shms.module.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  單位
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="UNIT")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Unit extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 *  單位ID
	 */
	@Column(name="UNIT_ID")
	private String unitId;
	
	/**
	 *  單位名稱
	 */
	@Column(name="UNIT_NAME")
	private String unitName;
	
	/**
	 *  主管
	 */
	@Column(name="MANAGER")
	private String manager;
	
	/**
	 * 職業安全衛生業務主管
	 */
	@Column(name="SAVE_MANAGER")
	private String saveManager;
	
	/**
	 *  防火管理人
	 */
	@Column(name="FIRE_HELPER")
	private String fireHelper;
	
	/**
	 *  急救人員
	 */
	@Column(name="HELPER")
	private String helper;
	
	/**
	 *  總務
	 */
	@Column(name="AFFAIRS")
	private String affairs;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getSaveManager() {
		return saveManager;
	}

	public void setSaveManager(String saveManager) {
		this.saveManager = saveManager;
	}

	public String getFireHelper() {
		return fireHelper;
	}

	public void setFireHelper(String fireHelper) {
		this.fireHelper = fireHelper;
	}

	public String getHelper() {
		return helper;
	}

	public void setHelper(String helper) {
		this.helper = helper;
	}

	public String getAffairs() {
		return affairs;
	}

	public void setAffairs(String affairs) {
		this.affairs = affairs;
	}
	
}
