package org.rest;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
public class Employee {

    @PathParam("empId")
    private int id;

    @FormParam("name")
    @NotNull(message = "name required")
    private String name;

    @FormParam("department")
    @NotNull(message = "department required")
    private String department;

    @FormParam("salary")
    @Min(value = 10000,message = "Salary should be greater than or equal to 10000")
    private int salary;

    @FormParam("age")
    @Min(value = 18,message = "Age should be greater than or equal to 18")
    private int age;

    @FormParam("mobile")
    @Digits(integer = 10,fraction = 0,message = "Mobile Number should be exact 10 characters")
    @Min(value = 600000000)
    private long mobile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return  name;
    }
}
