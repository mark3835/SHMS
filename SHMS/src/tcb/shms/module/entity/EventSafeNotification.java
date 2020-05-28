package tcb.shms.module.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  平安通報事件
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="EVENT_SAFE_NOTIFICATION")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventSafeNotification extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;
	
	/**
	 * 通報事件編號
	 */
	@Column(name="EVENT_KEY")
	private String eventKey;

	/**
	 * 通報事件名稱
	 */
	@Column(name="EVENT_NAME")
	private String eventName;
	
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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
		
}
