package informatech.demo.model;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

public class DepartmentModel {

    private Integer id;

    @NotBlank
    private String name;
    private List<String> employees;

    public DepartmentModel(){
    }

    public DepartmentModel(Integer id, String name){
        this.id = id;
        this.name = name;
    }

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
