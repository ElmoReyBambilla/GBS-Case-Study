package net.casestudy.ems.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import net.casestudy.ems.entity.CompensationEntity;

public interface CompensationService {
	//Fetch all Compensation Data of an Employee
	List<CompensationEntity> getCompensationHistory(Long employees_id);
	
	//Fetch sum of all compensations per month each year
	//List<Compensation> getCompensationPerMonth();
	
	//Insert New Compensation Data in Database
	CompensationEntity saveCompensation(CompensationEntity compensationEntity);
	
	//Fetch Compensation by ID
	CompensationEntity getCompensationById(Long compensationId);
	
	//Fetch Compensations breakdown
	List<CompensationEntity> getCompensationBreakdown(String month, int year, Long employee_id);
	
	//Search if Salary already exists
	Long checkEmployeeSalary(Date compensationDate, Long employeeId);
	
	//Search Compensation in Date Range
	List<CompensationEntity> searchCompensationDateRange(Long employeeId, String startDate, String endDate) throws ParseException;
	
}
