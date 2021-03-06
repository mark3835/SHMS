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
 * @version 2020/3/13
 */
@Entity
@Table(name="MENU")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Menu extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * menu名稱
	 */
	@Column(name="MENU_NAME")
	private String menuName;
	
	/**
	 * menu連結
	 */
	@Column(name="MENU_URL")
	private String menuUrl;
	
	/**
	 * 功能api URL 權限控管用
	 */
	@Column(name="MENU_API_URL")
	private String menuApiUrl;
	
	/**
	 * menu如果是第二層會有植 放第一層的MenuId
	 */
	@Column(name="MENU_TIER_TWO")
	private Long menuTierTwo;
	
	/**
	 * menu順序
	 */
	@Column(name="MENU_ORDER")
	private Integer menuOrder;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Long getMenuTierTwo() {
		return menuTierTwo;
	}

	public void setMenuTierTwo(Long menuTierTwo) {
		this.menuTierTwo = menuTierTwo;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuApiUrl() {
		return menuApiUrl;
	}

	public void setMenuApiUrl(String menuApiUrl) {
		this.menuApiUrl = menuApiUrl;
	}
		
}
