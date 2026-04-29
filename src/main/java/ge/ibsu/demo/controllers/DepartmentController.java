package ge.ibsu.demo.controllers;

import ge.ibsu.demo.dto.DepartmentResponse;
import ge.ibsu.demo.dto.RequestData;
import ge.ibsu.demo.dto.SearchDepartment;
import ge.ibsu.demo.entities.Department;
import ge.ibsu.demo.entities.Employee;
import ge.ibsu.demo.services.DepartmentService;
import ge.ibsu.demo.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public List<Department> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public Department getById(@PathVariable Long id) throws Exception {
        return departmentService.getById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployees(@PathVariable Long id) {
        return employeeService.getByDepartment(id);
    }

    @PostMapping("/search")
    public Page<DepartmentResponse> search(@RequestBody RequestData<SearchDepartment> rd) {
        return departmentService.search(rd.getData(), rd.getPaging());
    }
}
