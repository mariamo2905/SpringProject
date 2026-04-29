package ge.ibsu.demo.services;

import ge.ibsu.demo.dto.DepartmentResponse;
import ge.ibsu.demo.dto.Paging;
import ge.ibsu.demo.dto.SearchDepartment;
import ge.ibsu.demo.entities.Department;
import ge.ibsu.demo.repositories.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department getById(Long id) throws Exception {
        return  departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("DEPARTMENT_NOT_FOUND"));
    }

    public Page<DepartmentResponse> search(SearchDepartment searchDepartment, Paging p) {
        Pageable pageable = PageRequest.of(p.getPage() - 1, p.getSize(), Sort.by(Sort.Direction.ASC, "id"));
        Page<Department> departments = departmentRepository.searchDepartments(
                searchDepartment.getCountry(), 
                searchDepartment.getCity(), 
                pageable);
        
        return departments.map(d -> {
            DepartmentResponse response = new DepartmentResponse();
            response.setDepartmentName(d.getName());
            
            if (d.getManager() != null) {
                response.setManagerFullName(d.getManager().getFirstName() + " " + d.getManager().getLastName());
            }
            
            if (d.getLocation() != null) {
                response.setCity(d.getLocation().getCity());
                response.setStreetAddress(d.getLocation().getStreetAddress());
                if (d.getLocation().getCountry() != null) {
                    response.setCountry(d.getLocation().getCountry().getCountryName());
                }
            }
            
            return response;
        });
    }
}
