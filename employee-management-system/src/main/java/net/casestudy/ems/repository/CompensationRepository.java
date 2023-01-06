package net.casestudy.ems.repository;

import java.util.List;
import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.casestudy.ems.entity.CompensationEntity;

public interface CompensationRepository extends JpaRepository<CompensationEntity, Long> {

	/*
	@Query(value="SELECT compensation_id, SUM(amount) AS amount, comp_description, compensation_date, compensation_type, employee_id "
			+ "FROM compensation "
			+ "WHERE employee_id = ?1 GROUP BY MONTH(compensation_date) , YEAR(compensation_date)", nativeQuery=true)
			
	*/
	@Query(value="SELECT compensation_id, SUM(amount) AS amount, comp_description, compensation_type, "
			+ "compensation_date, employee_id "
			+ "FROM compensation WHERE employee_id = ?1 "
			+ "GROUP BY MONTH(compensation_date), YEAR(compensation_date) ORDER BY compensation_date DESC", nativeQuery=true)
	public List<CompensationEntity> getCompensationHistory(Long employee_id);
	
	/*
	@Query(value="SELECT * FROM compensation WHERE compensation_date LIKE CONCAT(YEAR(?1), '-', MONTH(?1), '-%') AND employee_id = ?2 ORDER BY compensation_date DESC", nativeQuery=true)
	*/
	@Query(value="SELECT * FROM compensation WHERE MONTH(compensation_date) = :month AND YEAR(compensation_date) = :year "
			+ "AND employee_id = :employee_id", nativeQuery=true)
	public List<CompensationEntity> getCompensationBreakdown(String month, int year, Long employee_id);
	
	@Query(value="SELECT compensation_id FROM compensation "
			+ "WHERE compensation_date LIKE CONCAT(YEAR(?1), '-', MONTH(?1), '-%') AND employee_id = ?2 AND compensation_type = 'Salary'", nativeQuery=true)
	public Long checkEmployeeSalary(Date compensationDate, Long employeeId);
	
	@Query(value="SELECT compensation_id, SUM(amount) AS amount, comp_description, compensation_date, compensation_type, employee_id "
			+ "FROM compensation "
			+ "WHERE employee_id = :employeeId AND compensation_date BETWEEN :startDate AND :endDate GROUP BY MONTH(compensation_date) , YEAR(compensation_date)"
			+ "ORDER BY compensation_date DESC", nativeQuery=true)
	public List<CompensationEntity> searchCompensationDateRange(@Param("employeeId") Long employeeId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
}
