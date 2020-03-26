package tcb.shms.module.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  錯誤紀錄
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="ERROR_LOG")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ErrorLog extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 錯誤程式
	 */
	@Column(name="ERROR_CLASS")
	private String errorClass;
	
	/**
	 * 錯誤訊息
	 */
	@Column(name="ERROR_MSG")
	private String errorMsg;
	
	/**
	 *  錯誤時間
	 */
	@Column(name="ERROR_TIME")
	private Timestamp errorTime;

	public String getErrorClass() {
		return errorClass;
	}

	public void setErrorClass(String errorClass) {
		this.errorClass = errorClass;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Timestamp getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Timestamp errorTime) {
		this.errorTime = errorTime;
	}
	
	
}
