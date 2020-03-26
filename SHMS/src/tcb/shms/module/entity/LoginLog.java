package tcb.shms.module.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  登入紀錄
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="LOGIN_LOG")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginLog extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 帳號
	 */
	@Column(name="ACCOUNT")
	private String account;
	
	/**
	 * 時間
	 * 
	 */
	@Column(name="LOGIN_TIME")
	private Timestamp loginTime;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
