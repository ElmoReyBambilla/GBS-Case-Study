package net.casestudy.ems.entity.df;

import java.sql.Date;

public class CompensationDateFilter {
	
	private Date startDate;
	private Date endDate;
	
	public CompensationDateFilter() {}
	
	public CompensationDateFilter(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
