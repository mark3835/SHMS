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
	private Long menuId;
	
	/**
	 *    可以看得權限
	 */
	@Column(name="AUTH_LV")
	private int authLv;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public int getAuthLv() {
		return authLv;
	}

	public void setAuthLv(int authLv) {
		this.authLv = authLv;
	}
	
	
	
}
