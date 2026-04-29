package ge.ibsu.demo.services;

import ge.ibsu.demo.dto.AddEmployee;
import ge.ibsu.demo.dto.EmployeeContactInfo;
import ge.ibsu.demo.dto.Paging;
import ge.ibsu.demo.dto.SearchEmployee;
import ge.ibsu.demo.entities.Department;
import ge.ibsu.demo.entities.Employee;
import ge.ibsu.demo.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import ge.ibsu.demo.utils.GeneralUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentService departmentService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) throws ResourceNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("EMPLOYEE_NOT_FOUND"));
    }

    @Transactional
    public Employee saveEmployee(AddEmployee data, Long id) throws Exception {
        GeneralUtil.checkRequiredProperties(data, Arrays.asList("firstName", "lastName", "phone", "salary"));
        Employee employee = new Employee();

        if (id != null) {
            employee = getById(id);
        }

        GeneralUtil.getCopyOf(data, employee);
        employee.setHireDate(new Date());

        Department department = this.departmentService.getById(data.getDepartmentId());

        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    public List<Employee> getByDepartment(Long departmentId) {
        return employeeRepository.findAllByDepartmentViaNative(departmentId);
    }

    public Page<Employee> search(SearchEmployee searchEmployee, Paging p) {
        Pageable pageable = PageRequest.of(p.getPage() - 1, p.getSize(), Sort.by(Sort.Direction.ASC, "id"));
        String searchText = searchEmployee.getSearchText() != null ? "%" + searchEmployee.getSearchText() + "%" : null;
        return employeeRepository.searchEmployees(searchText, pageable);
    }

    public EmployeeContactInfo getEmployeeContacts(SearchEmployee searchEmployee) {
        return employeeRepository.findByPhoneOrEmail(searchEmployee.getPhone(), searchEmployee.getEmail(), EmployeeContactInfo.class);
    }
}
