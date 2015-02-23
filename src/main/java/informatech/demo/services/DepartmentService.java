package informatech.demo.services;

import informatech.demo.domain.Department;
import informatech.demo.domain.Employee;
import informatech.demo.model.DepartmentModel;
import informatech.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DepartmentService {

    private DepartmentMapper mapper = new DepartmentMapper();

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public DepartmentModel getDepartmentById(Integer id){
        Department department = departmentRepository.findOne(id);
        if(department == null){
            throw new ResourceNotFoundException("The department " + id + " does not exist");
        }
        return mapper.toModel(department, new DepartmentModel());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DepartmentModel createNewDepartment(DepartmentModel model) {
        if(Objects.nonNull(model.getId())){
            throw new BadRequestServiceException("A new department must have a null id");
        }
        Department department = mapper.toDomain(model, new Department());
        departmentRepository.save(department);
        return mapper.toModel(department, new DepartmentModel());
    }

    @Transactional
    public List<DepartmentModel> createNewDepartments(List<DepartmentModel> models) {
        List<DepartmentModel> results = new ArrayList<>();
        for(DepartmentModel model : models) {
            results.add(createNewDepartment(model));
        }
        return results;
    }


    private static class DepartmentMapper {

        public DepartmentModel toModel(Department department, DepartmentModel model){
            model.setId(department.getId());
            model.setName(department.getName());
            if(department.getEmployees() != null) {
                model.setEmployees(new ArrayList<>(department.getEmployees().size()));
                for (Employee employee : department.getEmployees()) {
                    model.getEmployees().add(String.format("%s %s", employee.getFirstName(), employee.getLastName()));
                }
            }
            return model;
        }

        public Department toDomain(DepartmentModel model, Department department){
            department.setId(model.getId());
            department.setName(model.getName());
            return department;
        }

    }

}
