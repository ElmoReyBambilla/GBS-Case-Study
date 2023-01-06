package net.casestudy.ems.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.casestudy.ems.entity.CompensationEntity;
import net.casestudy.ems.repository.CompensationRepository;
import net.casestudy.ems.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService{

	private CompensationRepository compensationRepository;
	
	public CompensationServiceImpl(CompensationRepository compensationRepository) {
		super();
		this.compensationRepository = compensationRepository;
	}

	@Override
	public List<CompensationEntity> getCompensationHistory(Long employee_id) {
		return compensationRepository.getCompensationHistory(employee_id);
	}

	@Override
	public CompensationEntity saveCompensation(CompensationEntity compensationEntity) {
		return compensationRepository.save(compensationEntity);
	}

	@Override
	public CompensationEntity getCompensationById(Long compensationId) {
		return compensationRepository.findById(compensationId).get();
	}

	@Override
	public List<CompensationEntity> getCompensationBreakdown(String month, int year, Long employee_id) {
		return compensationRepository.getCompensationBreakdown(month, year, employee_id);
	}

	@Override
	public Long checkEmployeeSalary(Date compensationDate, Long employeeId) {
		return compensationRepository.checkEmployeeSalary(compensationDate, employeeId);
	}

	@Override
	public List<CompensationEntity> searchCompensationDateRange(Long employeeId, String startDate, String endDate) throws ParseException {
		if(employeeId != null || startDate != null || endDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			
			java.util.Date newStartDate = sdf.parse(startDate);
			java.util.Date newEndDate = sdf.parse(endDate);
			Date finalStartDate = new Date(newStartDate.getTime());
			Date finalEndDate = new Date(newEndDate.getTime());
			
			return compensationRepository.searchCompensationDateRange(employeeId, finalStartDate, finalEndDate);
		}
		return compensationRepository.getCompensationHistory(employeeId);
	}

}
