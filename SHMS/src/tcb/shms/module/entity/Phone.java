package tcb.shms.module.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import tcb.shms.core.entity.GenericEntity;
/**
 *  簡訊
 * @author Mark Huang
 * @version 2020/3/19
 */
@Entity
@Table(name="PHONE")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Phone extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 標題
	 */
	@Column(name="TITLE")
	private String title;
	
	/**
	 * 內容
	 */
	@Column(name="CONTENT")
	private String content;
	
	/**
	 *  收件者
	 */
	@Column(name="RECIPIENT")
	private String recipient;
	
	/**
	 *  發送時間
	 */
	@Column(name="SEND_TIME")
	private Timestamp sendTime;
	
	/**
	 *    建立人
	 */
	@Column(name="CREATE_ID")
	private String createId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
}
