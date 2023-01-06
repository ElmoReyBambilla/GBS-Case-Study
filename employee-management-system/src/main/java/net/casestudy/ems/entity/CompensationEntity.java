package net.casestudy.ems.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="compensation")
public class CompensationEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long compensationId;
	
	@Column(nullable=false)
	private String compensationType;
	
	@Column(nullable=false)
	private Float amount;
	
	@Column
	private String compDescription;
	
	@Column(nullable=false)
	private Date compensationDate;
	
	private Long employeeId;

	public CompensationEntity() {}
	
	public CompensationEntity(Long compensationId, String compensationType, Float amount, String compDescription, Date compensationDate, Long employeeId) {
		super();
		this.compensationId = compensationId;
		this.compensationType = compensationType;
		this.amount = amount;
		this.compDescription = compDescription;
		this.compensationDate = compensationDate;
		this.employeeId = employeeId;
	}


	public Long getCompensationId() {
		return compensationId;
	}

	public void setCompensationId(Long compensationId) {
		this.compensationId = compensationId;
	}

	public String getCompensationType() {
		return compensationType;
	}

	public void setCompensationType(String compensationType) {
		this.compensationType = compensationType;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getCompDescription() {
		return compDescription;
	}

	public void setCompDescription(String compDescription) {
		this.compDescription = compDescription;
	}

	public Date getCompensationDate() {
		return compensationDate;
	}

	public void setCompensationDate(Date compensationDate) {
		this.compensationDate = compensationDate;
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}
