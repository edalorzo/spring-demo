package codemasters.demo.repository;

import codemasters.demo.domain.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Integer>{

  List<Department> findByName(String name);


}
