package org.rest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private static EmployeeRepository empRepoInstance = new EmployeeRepository();
    public EmployeeRepository() {}
    public static EmployeeRepository getInstance() {
        System.out.println("Getting EmpRepo Instance");
        return empRepoInstance;
    }

    public List<Employee> getEmployees()
    {
        List<Employee> employees = new ArrayList<>();
        String query = "select * from employees";
        Connection con = DbConnect.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next())
            {
                Employee emp = new Employee();
                emp.setId(result.getInt(1));
                emp.setName(result.getString(2));
                emp.setDepartment(result.getString(3));
                emp.setSalary(result.getInt(4));
                emp.setAge(result.getInt(5));
                emp.setMobile(result.getLong(6));
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on getting Employees");
        }
        return employees;
    }

    public Employee getEmployee(int id)
    {
        Employee emp = new Employee();
        String query = "select * from employees where Id=?";
        Connection con = DbConnect.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet result = stmt.executeQuery();
            if(result.next())
            {
                emp.setId(result.getInt(1));
                emp.setName(result.getString(2));
                emp.setDepartment(result.getString(3));
                emp.setSalary(result.getInt(4));
                emp.setAge(result.getInt(5));
                emp.setMobile(result.getLong(6));
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Getting Employee by Id");
        }
        try {
            con.close();
        } catch (SQLException e) {}
        return emp;
    }

    public void addEmployee(Employee emp)
    {
        String query = "insert into employees(Name,Department,Salary,Age,Mobile) values(?,?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getDepartment());
            stmt.setInt(3, emp.getSalary());
            stmt.setInt(4, emp.getAge());
            stmt.setLong(5,emp.getMobile());
            int rows = stmt.executeUpdate();
            System.out.println(rows+" rows inserted");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Adding Employee");
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(int empId,Employee emp)
    {
        String query = "update employees set Name=?,Department=?,Salary=?,Age=?,Mobile=? where Id=?";
        Connection con = DbConnect.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getDepartment());
            stmt.setInt(3, emp.getSalary());
            stmt.setInt(4, emp.getAge());
            stmt.setLong(5,emp.getMobile());
            stmt.setInt(6,empId);
            int rows = stmt.executeUpdate();
            System.out.println(rows+" rows updated");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while updating employee");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id)
    {
        String query = "delete from employees where Id=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,id);
            int rows = stmt.executeUpdate();
            System.out.println(rows+" rows deleted");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting employee");
        }
    }

    public List<Vehicle> getVehicles(int empId)
    {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "select * from empvehicles where empId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,empId);
            ResultSet result = stmt.executeQuery();

            while(result.next())
            {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(result.getInt(1));
                vehicle.setRegNo(result.getString(2));
                vehicle.setCompanyName(result.getString(3));
                vehicles.add(vehicle);
            }
            result.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while getting vehicles");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public Vehicle getVehicle(int vehicleId,int empId)
    {
        Vehicle vehicle = new Vehicle();
        String query = "select * from empvehicles where vehicleId=? and empId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,vehicleId);
            stmt.setInt(2,empId);
            ResultSet result = stmt.executeQuery();
            if(result.next())
            {
                vehicle.setVehicleId(result.getInt(1));
                vehicle.setRegNo(result.getString(2));
                vehicle.setCompanyName(result.getString(3));
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while getting vehicle using vehicle Id");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }

    public void addVehicle(int empId,Vehicle vehicle)
    {
        String query = "insert into empvehicles(vehicleRegNo,vehicleCompName,empId) values(?,?,?)";
        Connection con = DbConnect.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,vehicle.getRegNo());
            stmt.setString(2,vehicle.getCompanyName());
            stmt.setInt(3,empId);
            int rows = stmt.executeUpdate();
            System.out.println(rows+" rows inserted");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while adding vehicle");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVehicle(int vehicleId,Vehicle vehicle)
    {
        String query = "update empvehicles set vehicleRegNo=?,vehicleCompName=? where vehicleId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,vehicle.getRegNo());
            stmt.setString(2,vehicle.getCompanyName());
            stmt.setInt(3,vehicleId);
            int rows = stmt.executeUpdate();
            System.out.println(rows+" rows updated");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while updating vehicle");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicle(int vehicleId)
    {
        String query = "delete from empvehicles where vehicleId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,vehicleId);
            int rows = stmt.executeUpdate();
            System.out.println(rows+" rows deleted");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting vehicle");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
