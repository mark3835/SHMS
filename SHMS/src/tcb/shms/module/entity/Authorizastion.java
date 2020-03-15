package tcb.shms.module.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  權限
 * @author Mark Huang
 * @version 2020/3/13
 */
@Entity
@Table(name="AUTHORIZASTION")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Authorizastion extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * menu ID
	 */
	@Column(name="MENU_ID")
	private String menuId;
	
	/**
	 *    可以看得權限
	 */
	@Column(name="AUTH_LV")
	private String authLv;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getAuthLv() {
		return authLv;
	}

	public void setAuthLv(String authLv) {
		this.authLv = authLv;
	}
	
	
	
}
