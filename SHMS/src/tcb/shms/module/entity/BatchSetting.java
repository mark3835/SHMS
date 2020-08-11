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
 *  批次設定
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="BATCH_SETTING")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BatchSetting extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 批次名稱
	 */
	@Column(name="BATCH_NAME")
	private String batchName;
	
	/**
	 * 批次執行程式
	 */
	@Column(name="BATCH_CLASS")
	private String batchClass;
	
	/**
	 *    執行類型
	 */
	@Column(name="BATCH_TYPE")
	private Integer batchType;
	
	/**
	 *    第一類執行一次 時間
	 */
	@Column(name="BATCH_TYPE_1_DATE")
	private Date batchType1Date;
	
	/**
	 *    第二類執行每隔幾分
	 */
	@Column(name="BATCH_TYPE_2_EVERY_MIN")
	private Integer batchType2EveryMin;
	
	/**
	 *    第三類執行每隔幾小時
	 */
	@Column(name="BATCH_TYPE_3_EVERY_HOUR")
	private Integer batchType3EveryHour;
	
	/**
	 *    第四類執行每週幾
	 */
	@Column(name="BATCH_TYPE_4_EVERY_WEEK")
	private Integer batchType4EveryWeek;
	
	/**
	 *    第五類執行每月幾號
	 */
	@Column(name="BATCH_TYPE_5_EVERY_MONTH")
	private Integer batchType5EveryMonth;
	
	/**
	 *    第四第五類執行小時
	 */
	@Column(name="BATCH_HOUR")
	private Integer batchHour;
	
	/**
	 *    第四第五類執行分鐘
	 */
	@Column(name="BATCH_MINUTE")
	private Integer batchMinute;
	
	/**
	 *    最後一次執行時間
	 */
	@Column(name="LAST_START_TIME")
	private Timestamp lastStartTime;

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getBatchClass() {
		return batchClass;
	}

	public void setBatchClass(String batchClass) {
		this.batchClass = batchClass;
	}

	public Integer getBatchType() {
		return batchType;
	}

	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}

	public Date getBatchType1Date() {
		return batchType1Date;
	}

	public void setBatchType1Date(Date batchType1Date) {
		this.batchType1Date = batchType1Date;
	}

	public Integer getBatchType2EveryMin() {
		return batchType2EveryMin;
	}

	public void setBatchType2EveryMin(Integer batchType2EveryMin) {
		this.batchType2EveryMin = batchType2EveryMin;
	}

	public Integer getBatchType3EveryHour() {
		return batchType3EveryHour;
	}

	public void setBatchType3EveryHour(Integer batchType3EveryHour) {
		this.batchType3EveryHour = batchType3EveryHour;
	}

	public Integer getBatchType4EveryWeek() {
		return batchType4EveryWeek;
	}

	public void setBatchType4EveryWeek(Integer batchType4EveryWeek) {
		this.batchType4EveryWeek = batchType4EveryWeek;
	}

	public Integer getBatchType5EveryMonth() {
		return batchType5EveryMonth;
	}

	public void setBatchType5EveryMonth(Integer batchType5EveryMonth) {
		this.batchType5EveryMonth = batchType5EveryMonth;
	}

	public Integer getBatchHour() {
		return batchHour;
	}

	public void setBatchHour(Integer batchHour) {
		this.batchHour = batchHour;
	}

	public Integer getBatchMinute() {
		return batchMinute;
	}

	public void setBatchMinute(Integer batchMinute) {
		this.batchMinute = batchMinute;
	}

	public Timestamp getLastStartTime() {
		return lastStartTime;
	}

	public void setLastStartTime(Timestamp lastStartTime) {
		this.lastStartTime = lastStartTime;
	}
	
}
