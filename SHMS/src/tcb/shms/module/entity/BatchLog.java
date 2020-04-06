package tcb.shms.module.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  批次LOG
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="BATCH_LOG")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BatchLog extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 批次id
	 */
	@Column(name="BATCH_ID")
	private Long batchId;
	
	/**
	 * 批次名稱
	 */
	@Column(name="BATCH_NAME")
	private String batchName;
	
	/**
	 *   批次結果
	 */
	@Column(name="BATCH_RESULT")
	private Date batchResult;
	
	/**
	 *   批次執行時間
	 */
	@Column(name="BATCH_BEGIN_TIME")
	private String batchBeginTime;
	
	/**
	 *    批次結束時間
	 */
	@Column(name="BATCH_END_TIME")
	private String batchEndTime;

	
	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Date getBatchResult() {
		return batchResult;
	}

	public void setBatchResult(Date batchResult) {
		this.batchResult = batchResult;
	}

	public String getBatchBeginTime() {
		return batchBeginTime;
	}

	public void setBatchBeginTime(String batchBeginTime) {
		this.batchBeginTime = batchBeginTime;
	}

	public String getBatchEndTime() {
		return batchEndTime;
	}

	public void setBatchEndTime(String batchEndTime) {
		this.batchEndTime = batchEndTime;
	}
	
	
}
