package informatech.demo.services;


import informatech.demo.domain.Department;
import informatech.demo.domain.Employee;
import informatech.demo.model.DepartmentModel;
import informatech.demo.repository.DepartmentRepository;
import informatech.demo.services.exceptions.BadRequestServiceException;
import informatech.demo.services.exceptions.ResourceNotFoundException;
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

    @Autowired
    private DepartmentRepository departmentRepository;
    private DepartmentMapper mapper = new DepartmentMapper();

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public DepartmentModel getDepartmentById(Integer id, boolean withEmployees) {
        Department department = departmentRepository.findOne(id);
        if(department == null) {
            throw new ResourceNotFoundException("The department " + id + " does not exist");
        }
        return mapper.toModel(department, new DepartmentModel(), withEmployees);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DepartmentModel createNewDepartment(DepartmentModel model) {
        if(Objects.nonNull(model.getId())){
            throw new BadRequestServiceException("The department model must not have an ID");
        }
        Department department = mapper.toDomain(model, new Department());
        departmentRepository.save(department);
        return mapper.toModel(department, new DepartmentModel(), false);
    }


    @Transactional
    public List<DepartmentModel> createNewDepartments(List<DepartmentModel> models) {

        List<DepartmentModel> results = new ArrayList<>();
        for(DepartmentModel model : models) {
            results.add(createNewDepartment(model));
            if(model.getName().equals("HR")){
                throw new BadRequestServiceException("The HR department is not accepted");
            }
        }
        return results;
    }

    public DepartmentModel changeExistingDepartment(DepartmentModel model){
        if(Objects.isNull(model.getId())){
            throw new BadRequestServiceException("The department model must have an ID");
        }
        Department department = departmentRepository.findOne(model.getId());
        if(department == null) {
            throw new ResourceNotFoundException("The department " + model.getId() + " does not exist");
        }
        mapper.toDomain(model, department);
        departmentRepository.save(department);
        return mapper.toModel(department, model, false);
    }

    public void removeDepartment(Integer id){
        Department department = departmentRepository.findOne(id);
        if(department == null) {
            throw new ResourceNotFoundException("The department " + id + " does not exist");
        }
        departmentRepository.delete(department);
    }

    private static class DepartmentMapper {

        public DepartmentModel toModel(Department department, DepartmentModel model, boolean withEmployees){
            model.setId(department.getId());
            model.setName(department.getName());
            if(withEmployees){
                model.setEmployees(new ArrayList<>(department.getEmployees().size()));
                for(Employee employee : department.getEmployees()){
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
