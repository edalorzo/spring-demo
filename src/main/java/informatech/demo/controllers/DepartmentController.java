package informatech.demo.controllers;

import informatech.demo.domain.Department;
import informatech.demo.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

  private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

  @Autowired
  private DepartmentRepository departmentRepository;


  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
  public Department getDepartment(@PathVariable Integer id) {
    logger.debug("Retrieving department for id {}", id);

    return departmentRepository.findOne(id);
  }

  @RequestMapping(
    method = RequestMethod.POST,
    consumes = {"application/json", "application/xml"},
    produces = {"application/json", "application/xml"}
  )
  @ResponseStatus(HttpStatus.CREATED)
  public Department createDepartment(@RequestBody Department department){
    department.setId(null);
    return departmentRepository.save(department);
  }

  @RequestMapping(
    value = "/{id}",
    method = RequestMethod.PUT,
    consumes = {"application/json", "application/xml"},
    produces = {"application/json", "application/xml"}
  )
  public Department updateDepartment(@PathVariable Integer id, @RequestBody Department department){
    department.setId(id);
    return departmentRepository.save(department);
  }

  @RequestMapping(
    value = "/{id}",
    method = RequestMethod.DELETE
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteDepartment(@PathVariable Integer id){
    departmentRepository.delete(id);
  }

}
