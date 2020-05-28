package tcb.shms.module.entity;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  證照
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="CERTIFICATE")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Certificate extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 身分證字號
	 */
	@Column(name="ROC_ID")
	private String rocId;
	
	/**
	 * 證照類型
	 */
	@Column(name="CERTIFICATE_TYPE")
	private String certificateType;
	
	/**
	 * 證照名稱
	 */
	@Column(name="CERTIFICATE_NAME")
	private String certificateName;
	
	/**
	 * 發證單位
	 */
	@Column(name="CERTIFICATE_UNIT")
	private String certificateUnit;
	
	/**
	 * 取得日期
	 */
	@Column(name="GET_DATE")
	private Date gotDate;
	
	/**
	 * 取得金額 考照費
	 */
	@Column(name="GET_FEE")
	private Integer gotFee;
	
	/**
	 *  取得時單位
	 */
	@Column(name="GET_TRAIN_UNIT")
	private String gotTrainUnit;
	
	/**
	 * 審核人 ID
	 */
	@Column(name="REVIEW_ID")
	private String reviewId;
	
	/**
	 * 審核時間
	 */
	@Column(name="REVIEW_TIME")
	private Timestamp reviewTime;
	
	/**
	 * 是否為單位負責人
	 */
	@Column(name="IS_RESPONSIBLE")
	private int isResponsible;
	
	/**
	 * 審核人 ID
	 */
	@Column(name="MEMO")
	private String memo;
	
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

	public String getRocId() {
		return rocId;
	}

	public void setRocId(String rocId) {
		this.rocId = rocId;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateUnit() {
		return certificateUnit;
	}

	public void setCertificateUnit(String certificateUnit) {
		this.certificateUnit = certificateUnit;
	}

	public Date getGotDate() {
		return gotDate;
	}

	public void setGotDate(Date gotDate) {
		this.gotDate = gotDate;
	}

	public int getGotFee() {
		return gotFee;
	}

	public void setGotFee(int gotFee) {
		this.gotFee = gotFee;
	}

	public String getGotTrainUnit() {
		return gotTrainUnit;
	}

	public void setGotTrainUnit(String gotTrainUnit) {
		this.gotTrainUnit = gotTrainUnit;
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

	public int getIsResponsible() {
		return isResponsible;
	}

	public void setIsResponsible(int isResponsible) {
		this.isResponsible = isResponsible;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
