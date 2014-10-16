package informatech.demo.controllers;

import informatech.demo.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public Department getDepartment(@PathVariable Integer id) {

        RowMapper<Department> rm = (ResultSet rs, int rowNum) -> {

            Department dp = new Department();
            dp.setId(rs.getInt("id"));
            dp.setName(rs.getString("name"));
            return dp;
        };

        return jdbcTemplate.queryForObject("SELECT ID,NAME FROM DEPARTMENT  WHERE ID = ?", rm, id);


    }

}
