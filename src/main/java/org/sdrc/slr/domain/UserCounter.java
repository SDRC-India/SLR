/**
 * 
 */
package org.sdrc.slr.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 * dedicated to vistors counter
 *
 */

@Entity
@Table(name="visitor_count")
public class UserCounter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer visitorId;
	
	
	private long visitorCount;
	
	private Timestamp updatedDate;

	public Integer getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(Integer visitorId) {
		this.visitorId = visitorId;
	}

	public long getVisitorCount() {
		return visitorCount;
	}

	public void setVisitorCount(long visitorCount) {
		this.visitorCount = visitorCount;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	

}
