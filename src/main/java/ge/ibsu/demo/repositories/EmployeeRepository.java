package ge.ibsu.demo.repositories;

import ge.ibsu.demo.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where e.department.id = :depId")
    List<Employee> findAllByDepartment(@Param("depId") Long departmentId);

    @Query(
            value = "select * from employees e where e.department_id = :depId",
            nativeQuery = true
    )
    List<Employee> findAllByDepartmentViaNative(@Param("depId") Long departmentId);

    @Query("select e from Employee e " +
            "where (:searchText is null or concat(e.firstName, concat(' ', e.lastName)) like :searchText)")
    Page<Employee> searchEmployees(@Param("searchText") String searchText, Pageable pageable);

    @Query(
            value = "select * from employees e " +
            "where (:searchText is null or concat(e.first_name, concat(' ', e.last_name)) like :searchText)",
            countQuery = "select count(*) from employees e " +
                    "where (:searchText is null or concat(e.first_name, concat(' ', e.last_name)) like :searchText)",
            nativeQuery = true
    )
    Page<Employee> searchEmployeesViaNative(@Param("searchText") String searchText, Pageable pageable);

    <T> T findByPhoneOrEmail(String phone, String email, Class<T> type);

}
