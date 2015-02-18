package informatech.demo.controllers;

import informatech.demo.model.DepartmentModel;
import informatech.demo.services.DepartmentService;
import informatech.demo.services.exceptions.BadRequestServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public DepartmentModel getDepartment(
        @PathVariable Integer id,
        @RequestParam(value = "withEmployees", required = false) boolean withEmployees)
    {
        logger.debug("Retrieving department for id {}", id);

        return departmentService.getDepartmentById(id, withEmployees);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public List<DepartmentModel> createDepartment(@RequestBody @Validated List<DepartmentModel> departments, Errors errors) {
        if(errors.hasErrors()){
            throw new BadRequestServiceException(errors);
        }
        return departmentService.createNewDepartments(departments);
    }

    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"}
    )
    public DepartmentModel updateDepartment(@PathVariable Integer id, @RequestBody DepartmentModel department) {
        department.setId(id);
        return departmentService.changeExistingDepartment(department);
    }

    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Integer id) {
        departmentService.removeDepartment(id);
    }




}
