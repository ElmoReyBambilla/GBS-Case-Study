package net.casestudy.ems.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import net.casestudy.ems.entity.EmployeeEntity;

public interface EmployeeService {
	//Fetch all Employee Data
	List<EmployeeEntity> getAllEmployees();
		
	//Search Employees with a given Information
	List<EmployeeEntity> searchEmployees(String firstName, String lastName, String position);
		
	//Search if the New Employee Data already Exists on the Database
	Long searchDuplicate(String firstName, String lastName, String middleName, Date birthDate);
		
	//Insert New Employee Data in Database
	EmployeeEntity saveEmployee(EmployeeEntity employeeEntity);
		
	//Get Employee Data by ID
	EmployeeEntity getEmployeeById(Long id);
		
	//Update Employee Data
	EmployeeEntity updateEmployee(EmployeeEntity employeeEntity);
		
	//Delete Employee
	void deleteEmployeeById(Long id);
	
	//Add Pagination to the Table
	//Page<Employee> pagination(int pageNo, int pageSize);

}
