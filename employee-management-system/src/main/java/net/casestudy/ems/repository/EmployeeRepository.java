package net.casestudy.ems.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.casestudy.ems.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>{
	
	//SEARCH/FILTER
	@Query("SELECT e FROM EmployeeEntity e WHERE " +
			"e.firstName LIKE CONCAT('%',:firstName,'%')" +
			"AND e.lastName LIKE CONCAT('%',:lastName,'%')"+
			"AND e.position LIKE CONCAT('%',:position,'%')")
	public List<EmployeeEntity> searchEmployee(String firstName, String lastName, String position);
	
	//DUPLICATE DATA
	@Query("SELECT id FROM EmployeeEntity e WHERE " +
			"e.firstName LIKE CONCAT('',:firstName,'')" +
			"AND e.lastName LIKE CONCAT('',:lastName,'')" +
			"AND e.middleName LIKE CONCAT('',:middleName,'')" +
			"AND e.birthDate LIKE CONCAT('',:birthDate,'')")
	public Long searchDuplicate(String firstName, String lastName, String middleName, Date birthDate);
	
	
}
