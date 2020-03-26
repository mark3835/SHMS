package tcb.shms.module.entity;

import java.sql.Timestamp;

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
	private int batchType;
	
	/**
	 *    第一類執行一次 時間
	 */
	@Column(name="BATCH_TYPE_1_TIME")
	private Timestamp batchType1Time;
	
	/**
	 *    第二類執行每隔幾分
	 */
	@Column(name="BATCH_TYPE_2_EVERY_MIN")
	private int batchType2EveryMin;
	
	/**
	 *    第三類執行每隔幾小時
	 */
	@Column(name="BATCH_TYPE_3_EVERY_HOUR")
	private int batchType3EveryHour;
	
	/**
	 *    第四類執行每週幾號
	 */
	@Column(name="BATCH_TYPE_4_EVERY_WEEK")
	private int batchType4EveryWeek;
	
	/**
	 *    第五類執行每週幾號
	 */
	@Column(name="BATCH_TYPE_5_EVERY_MONTH")
	private int batchType5EveryMonth;
	
	/**
	 *    最後一次執行一次
	 */
	@Column(name="LAST_START_TIME")
	private Timestamp lastStratTime;

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

	public int getBatchType() {
		return batchType;
	}

	public void setBatchType(int batchType) {
		this.batchType = batchType;
	}

	public Timestamp getBatchType1Time() {
		return batchType1Time;
	}

	public void setBatchType1Time(Timestamp batchType1Time) {
		this.batchType1Time = batchType1Time;
	}

	public int getBatchType2EveryMin() {
		return batchType2EveryMin;
	}

	public void setBatchType2EveryMin(int batchType2EveryMin) {
		this.batchType2EveryMin = batchType2EveryMin;
	}

	public int getBatchType3EveryHour() {
		return batchType3EveryHour;
	}

	public void setBatchType3EveryHour(int batchType3EveryHour) {
		this.batchType3EveryHour = batchType3EveryHour;
	}

	public int getBatchType4EveryWeek() {
		return batchType4EveryWeek;
	}

	public void setBatchType4EveryWeek(int batchType4EveryWeek) {
		this.batchType4EveryWeek = batchType4EveryWeek;
	}

	public int getBatchType5EveryMonth() {
		return batchType5EveryMonth;
	}

	public void setBatchType5EveryMonth(int batchType5EveryMonth) {
		this.batchType5EveryMonth = batchType5EveryMonth;
	}

	public Timestamp getLastStratTime() {
		return lastStratTime;
	}

	public void setLastStratTime(Timestamp lastStratTime) {
		this.lastStratTime = lastStratTime;
	}
	
	
	
}
