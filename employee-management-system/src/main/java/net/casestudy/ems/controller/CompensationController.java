package net.casestudy.ems.controller;

import java.util.Arrays;
import java.util.List;
import java.sql.Date;
import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.casestudy.ems.entity.CompensationEntity;
import net.casestudy.ems.entity.EmployeeEntity;
import net.casestudy.ems.entity.df.CompensationDateFilter;
import net.casestudy.ems.service.CompensationService;
import net.casestudy.ems.service.EmployeeService;

@Controller
public class CompensationController {
	
	private CompensationService compensationService;
	private EmployeeService employeeService;
	
	public CompensationController(CompensationService compensationService, EmployeeService employeeService) {
		super();
		this.compensationService = compensationService;
		this.employeeService = employeeService;
	}
	
	@GetMapping("/employees/compensation/{id}")
	public String listcompensations(@PathVariable Long id, Model model) {
		
		model.addAttribute("employee", id);
		model.addAttribute("compensations", compensationService.getCompensationHistory(id));

		return "compensation_history";
		
	}
	
	@PostMapping("/employees/compensation/{id}/search")
	public String searchEmployees(@PathVariable("id") Long id, Model model, String startDate, String endDate, RedirectAttributes redirAttrs) throws ParseException {
		if(startDate.isEmpty() && endDate.isEmpty()) {
			redirAttrs.addFlashAttribute("warning", "Please enter desired date range...");
			return "redirect:/employees/compensation/{id}";
		}
		
		List<CompensationEntity> compensationList = compensationService.searchCompensationDateRange(id, startDate, endDate);
		if(compensationList == null) {
			redirAttrs.addFlashAttribute("warning", "Please enter desired date range...");
			return "redirect:/employees";
		}
		
		model.addAttribute("employee", id);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("compensations", compensationList);
		
		return "compensation_history";
	}
	
	@GetMapping("/employees/compensation/new")
	public String createEmployeeCompensationForm(Model model) {
		CompensationEntity compensationEntity = new CompensationEntity();
		
		model.addAttribute("employeeList", employeeService.getAllEmployees());
		model.addAttribute("compensation", compensationEntity);
		
		List<String> typeList = Arrays.asList("Salary", "Bonus", "Commission", "Allowance", "Adjustment");
		model.addAttribute("typeList", typeList);
		
		return "create_compensation";
	}
	
	@PostMapping("/employees/compensation/save")
	public String saveEmployeeCompensation(@ModelAttribute("compensation") CompensationEntity compensationEntity, RedirectAttributes redirAttrs) {
		String type = compensationEntity.getCompensationType();
		Long check = checkEmployeeSalary(compensationEntity.getCompensationDate(), compensationEntity.getEmployeeId());
		if(check > 0 && type.equals("Salary")) {
			redirAttrs.addFlashAttribute("duplicate", "Employee salary already exists for this month...");
			return "redirect:/employees/compensation/new";
		}else {
			compensationService.saveCompensation(compensationEntity);
			
			redirAttrs.addFlashAttribute("added", "New compensation has been added successfully...");
			
			return "redirect:/employees";
		}
	}
	
	@GetMapping("/employees/compensation/breakdown/{employeeId}/{compensationDate}/{month}/{year}")
	public String getCompensationBreakdown(@PathVariable("employeeId") Long employeeId, @PathVariable String month, @PathVariable int year, @PathVariable("compensationDate") Date compensationDate, Model model) {
		model.addAttribute("date", compensationDate);
		model.addAttribute("employee", employeeId);
		model.addAttribute("compensations", compensationService.getCompensationBreakdown(month, year, employeeId));
		return "compensation";
	}
	
	@GetMapping("/employees/compensation/edit/{employeeId}/{compensationId}")
	public String editCompensationForm(@PathVariable("employeeId") Long employeeId, @PathVariable("compensationId") Long compensationId, Model model) {
		model.addAttribute("employeeId", employeeId);
		model.addAttribute("compensation", compensationService.getCompensationById(compensationId));
		return "edit_compensation";
	}
	
	@PostMapping("/employees/compensation/update/{employeeId}/{compensationId}")
	public String updateEmployeeCompensation(@PathVariable("employeeId") Long employeeId, @PathVariable("compensationId") Long compensationId, Model model, @ModelAttribute("compensation") CompensationEntity compensationEntity, RedirectAttributes redirAttrs) {
		CompensationEntity existingCompensation = compensationService.getCompensationById(compensationId);
		existingCompensation.setCompensationId(compensationId);
		existingCompensation.setAmount(compensationEntity.getAmount());
		existingCompensation.setCompDescription(compensationEntity.getCompDescription());
		existingCompensation.setCompensationDate(compensationEntity.getCompensationDate());
		existingCompensation.setCompensationType(compensationEntity.getCompensationType());
		existingCompensation.setEmployeeId(compensationEntity.getEmployeeId());
		
		redirAttrs.addFlashAttribute("updated", "Employee compensation details has been updated successfully...");
		
		compensationService.saveCompensation(existingCompensation);
		return "redirect:/employees/compensation/{employeeId}";
	}
	
	public Long checkEmployeeSalary(Date compensationDate, Long employeeId) {
		Long salary = compensationService.checkEmployeeSalary(compensationDate, employeeId);
		
		if(salary == null) {
			salary = (long) 0;
		}
		return salary;
	}
}
