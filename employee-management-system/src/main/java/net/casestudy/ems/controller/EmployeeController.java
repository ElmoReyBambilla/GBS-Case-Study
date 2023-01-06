package net.casestudy.ems.controller;

import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.casestudy.ems.entity.EmployeeEntity;
import net.casestudy.ems.service.EmployeeService;

@Controller
public class EmployeeController {
	
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping("/employees")
	public String listEmployees(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "employees";
		
		//return pagination(1, model);
	}
	
	@PostMapping("/employees/search")
	public String searchEmployees(Model model, @ModelAttribute("filter") EmployeeEntity filter) {
		model.addAttribute("employees", employeeService.searchEmployees(filter.getFirstName(), filter.getLastName(), filter.getPosition()));
		model.addAttribute("firstName", filter.getFirstName());
		model.addAttribute("lastName", filter.getLastName());
		model.addAttribute("position", filter.getPosition());
		return "employees";
	}
	
	@GetMapping("/employees/new")
	public String createEmployeeForm(Model model) {
		
		EmployeeEntity employee = new EmployeeEntity();
		model.addAttribute("employee", employee);
		
		return "create_employee";
	}
	
	@PostMapping("/employees")
	public String saveEmployee(@ModelAttribute("employee") EmployeeEntity employeeEntity, RedirectAttributes redirAttrs) {
		Long check = duplicateEmployee(employeeEntity.getFirstName(), employeeEntity.getLastName(), employeeEntity.getMiddleName(), employeeEntity.getBirthDate());
		if(check > 0) {
			redirAttrs.addFlashAttribute("duplicate", "Employee details already exists...");
			return "redirect:/employees/new";
		}else {
			employeeService.saveEmployee(employeeEntity);
			
			redirAttrs.addFlashAttribute("added", "New employee has been added successfully...");
			return "redirect:/employees";
		}
	}
	
	@GetMapping("/employees/edit/{id}")
	public String editEmployeeForm(@PathVariable Long id, Model model) {
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		return "edit_employee";
	}
	
	@PostMapping("/employees/{id}")
	public String updateEmployee(@PathVariable Long id, @ModelAttribute("employee") EmployeeEntity employeeEntity, Model model, RedirectAttributes redirAttrs) {
		Long check = duplicateEmployee(employeeEntity.getFirstName(), employeeEntity.getLastName(), employeeEntity.getMiddleName(), employeeEntity.getBirthDate());
		
		if(check == employeeEntity.getId() || check == 0) {
			EmployeeEntity existingEmployee = employeeService.getEmployeeById(id);
			existingEmployee.setId(id);
			existingEmployee.setFirstName(employeeEntity.getFirstName());
			existingEmployee.setLastName(employeeEntity.getLastName());
			existingEmployee.setMiddleName(employeeEntity.getMiddleName());
			existingEmployee.setPosition(employeeEntity.getPosition());
			existingEmployee.setBirthDate(employeeEntity.getBirthDate());
			
			employeeService.updateEmployee(existingEmployee);
			redirAttrs.addFlashAttribute("updated", "Employee details has been updated successfully...");
			return "redirect:/employees";
		}else {
			redirAttrs.addFlashAttribute("duplicate", "Employee details already exists...");
			return "redirect:/employees/edit/{id}";
		}
	}
	
	@GetMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployeeById(id);
		
		return "redirect:/employees";
	}

	public Long duplicateEmployee(String firstName, String lastName, String middleName, Date birthDate) {
		Long duplicate = employeeService.searchDuplicate(firstName, lastName, middleName, birthDate);
		if(duplicate == null) {
			duplicate = (long) 0;
		}
		return duplicate;
	}
	
	//Pagination Not Working
	/*@GetMapping("/employees/{pageNo}")
	public String pagination(@PathVariable int pageNo, Model model) {
		int pageSize = 1;
		
		Page<Employee> page = employeeService.pagination(pageNo, pageSize);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("employees", listEmployees);
		return "employees";
	}*/
}
