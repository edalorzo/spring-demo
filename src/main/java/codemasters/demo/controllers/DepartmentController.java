package codemasters.demo.controllers;

import codemasters.demo.domain.Department;
import codemasters.demo.model.DepartmentModel;
import codemasters.demo.repository.DepartmentRepository;
import codemasters.demo.services.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public DepartmentModel getDepartment(@PathVariable Integer id) {
        logger.debug("Retrieving department for id {}", id);

        return departmentService.getDepartmentById(id);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public List<DepartmentModel> createDepartment(@RequestBody List<DepartmentModel> departments) {
        return departmentService.createNewDepartments(departments);
    }

    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"}
    )
    public Department updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        department.setId(id);
        return departmentRepository.save(department);
    }

    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Integer id) {
        departmentRepository.delete(id);
    }




}
