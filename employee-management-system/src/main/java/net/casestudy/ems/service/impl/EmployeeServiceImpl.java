package net.casestudy.ems.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.casestudy.ems.entity.EmployeeEntity;
import net.casestudy.ems.repository.EmployeeRepository;
import net.casestudy.ems.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<EmployeeEntity> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@Override
	public List<EmployeeEntity> searchEmployees(String firstName, String lastName, String position) {
		if(firstName != null || lastName != null || position != null) {
			return employeeRepository.searchEmployee(firstName, lastName, position);
		}
		return employeeRepository.findAll();
	}
	
	@Override
	public EmployeeEntity saveEmployee(EmployeeEntity employeeEntity) {
		return employeeRepository.save(employeeEntity);
	}

	@Override
	public EmployeeEntity getEmployeeById(Long id) {
		return employeeRepository.findById(id).get();
	}

	@Override
	public EmployeeEntity updateEmployee(EmployeeEntity employeeEntity) {
		return employeeRepository.save(employeeEntity);
	}

	@Override
	public void deleteEmployeeById(Long id) {
		employeeRepository.deleteById(id);
	}
	
	@Override
	public Long searchDuplicate(String firstName, String lastName, String middleName, Date birthDate) {
		return employeeRepository.searchDuplicate(firstName, lastName, middleName, birthDate);
	}
	
	/*@Override
	public Page<Employee> pagination(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo -1, pageSize);
		return this.employeeRepository.findAll(pageable);
	}*/
}
