package org.sdrc.slr.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 * @since version 1.0.0
 * Mapping between CollectUser and Program_XForm_Mapping
 * forms are assigned to a user
 * 
 */
@Entity
public class User_Program_XForm_Mapping  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "UserProgramXFomrMapping_Id")
	private int userProgramXFomrMappingId;
	
	@Column(name="IsLive", nullable = false)
	private Boolean isLive;
	
	@Column(name="CreatedBy")
	private String createdBy;

	@Column(name="CreatedDate")
	private Timestamp createdDate;
	
	@Column(name="UpdatedBy")
	private String updatedBy;

	@Column(name="UpdatedDate")
	private Timestamp updatedDate;
	
	@ManyToOne
	@JoinColumn(name="ProgramXFromMappingId")
	private Program_XForm_Mapping program_XForm_Mapping;
	
	@ManyToOne
	@JoinColumn(name="UserId")
	private CollectUser collectUser;;

	public int getUserProgramXFomrMappingId() {
		return userProgramXFomrMappingId;
	}

	public void setUserProgramXFomrMappingId(int userProgramXFomrMappingId) {
		this.userProgramXFomrMappingId = userProgramXFomrMappingId;
	}

	public Boolean getIsLive() {
		return isLive;
	}

	public void setIsLive(Boolean isLive) {
		this.isLive = isLive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Program_XForm_Mapping getProgram_XForm_Mapping() {
		return program_XForm_Mapping;
	}

	public void setProgram_XForm_Mapping(Program_XForm_Mapping program_XForm_Mapping) {
		this.program_XForm_Mapping = program_XForm_Mapping;
	}

	public CollectUser getCollectUser() {
		return collectUser;
	}

	public void setCollectUser(CollectUser collectUser) {
		this.collectUser = collectUser;
	}

}
