package ge.ibsu.demo.repositories;

import ge.ibsu.demo.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select d from Department d left join d.location l left join l.country c " +
           "where (:country is null or :country = '' or c.countryName = :country) " +
           "and (:city is null or :city = '' or l.city = :city)")
    Page<Department> searchDepartments(@Param("country") String country, @Param("city") String city, Pageable pageable);
}
