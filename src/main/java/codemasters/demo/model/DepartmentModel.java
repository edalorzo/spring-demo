package codemasters.demo.model;

import java.util.List;

public class DepartmentModel {

    private Integer id;
    private String name;
    private List<String> employees;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmployees() {
        return employees;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "DepartmentModel{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
