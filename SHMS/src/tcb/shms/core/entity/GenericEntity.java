/**
 * 
 */
package tcb.shms.core.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author Mark Huang
 *
 */
@MappedSuperclass
public abstract class GenericEntity implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8739689797669392643L;

	/** The log. */
	@Transient
	protected final transient Logger log = LogManager.getLogger(getClass());
	
	/** The id. */
	@Id  
	@GeneratedValue(strategy=GenerationType.IDENTITY)   
	@Column(name="ID", unique=true, nullable=false, insertable=true, updatable=false, precision=20)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Logger getLog() {
		return log;
	}
	
}
