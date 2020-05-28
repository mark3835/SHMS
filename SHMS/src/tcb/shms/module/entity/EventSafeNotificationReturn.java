package tcb.shms.module.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  平安回報
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="EVENT_SAFE_NOTIFICATION_RETURN")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventSafeNotificationReturn extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 回報事件ID
	 */
	@Column(name="EVENT_IT")
	private String eventId;
	
	/**
	 *    單位ID
	 */
	@Column(name="UNIT_ID")
	private String unitId;
	
	/**
	 *    回報時間
	 */
	@Column(name="RETURN_TIME")
	private Timestamp returnTime;
	
	/**
	 *    是否平安
	 */
	@Column(name="IS_SAFE")
	private int isSafe;
	
	/**
	 *    影響類別
	 */
	@Column(name="EFFECT_TYPE")
	private String effectType;
	
	/**
	 *    簡要說明重要案情
	 */
	@Column(name="MEMO")
	private String memo;
	
	/**
	 *    審核人
	 */
	@Column(name="REVIEW_ID")
	private String reviewId;
	
	/**
	 *    審核時間
	 */
	@Column(name="REVIEW_TIME")
	private Timestamp reviewTime;

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

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Timestamp getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Timestamp returnTime) {
		this.returnTime = returnTime;
	}

	public int getIsSafe() {
		return isSafe;
	}

	public void setIsSafe(int isSafe) {
		this.isSafe = isSafe;
	}

	public String getEffectType() {
		return effectType;
	}

	public void setEffectType(String effectType) {
		this.effectType = effectType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public Timestamp getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
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
