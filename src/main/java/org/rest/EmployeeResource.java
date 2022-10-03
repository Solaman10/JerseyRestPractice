package org.rest;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("Employees")
public class EmployeeResource {

    EmployeeRepository empRepo = EmployeeRepository.getInstance();

    @GET
    @Produces({"application/xml","application/json"})
    public Response getEmployees()
    {
        List<Employee> employees = empRepo.getEmployees();
        if(employees.size()>0)
        {
            GenericEntity<List<Employee>> genericEntity = new GenericEntity<List<Employee>>(employees){};
            return Response.ok(genericEntity).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Records not found").build();
    }

    @GET
    @Path("{empId}")
    @Produces({"application/xml","application/json"})
    public Response getEmployee(@PathParam("empId") int empId)
    {
        Employee employee = empRepo.getEmployee(empId);
        if(employee.getId()==0)
            return Response.status(Response.Status.NOT_FOUND).entity("Id not found").build();
        return Response.ok(employee).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response addEmployeeBean(@Valid @BeanParam Employee emp)
    {
        System.out.println("Adding new employee");
        empRepo.addEmployee(emp);
        return Response.status(Response.Status.CREATED).entity("Created").build();
    }

    @POST
    @Consumes({"application/xml","application/json"})
    public Response addEmployee(@Valid Employee emp)
    {
        System.out.println("Adding new employee");
        empRepo.addEmployee(emp);
        return Response.status(Response.Status.CREATED).entity("Created").build();
    }

    @PATCH
    @Path("{empId}")
    @Consumes("application/x-www-form-urlencoded")
    public Response updateEmployeeBean(@Valid @BeanParam Employee emp,@PathParam("empId") int empId)
    {
        System.out.println("Updating employee by using Id: "+empId);
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Id not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Id not found").build();
        }
        empRepo.updateEmployee(empId,emp);
        return Response.status(Response.Status.OK).entity("Records Updated").build();
    }

    @PATCH
    @Path("{empId}")
    @Consumes({"application/xml","application/json"})
    public Response updateEmployee(@Valid Employee emp,@PathParam("empId") int empId)
    {
        System.out.println("Updating employee by using Id: "+empId);
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Id not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Id not found").build();
        }
        empRepo.updateEmployee(empId,emp);
        return Response.status(Response.Status.OK).entity("Records Updated").build();
    }

    @DELETE
    @Path("{empId}")
    public Response deleteEmployee(@PathParam("empId") int empId)
    {
        System.out.println("Deleting employee by using Id: "+empId);
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Id not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Id not found").build();
        }
        empRepo.deleteEmployee(empId);
        return Response.status(Response.Status.OK).entity("Recodes Deleted").build();
    }

    @GET
    @Path("{empId}/Vehicles")
    @Produces({"Application/xml","Application/json"})
    public Response getVehicleDetails(@PathParam("empId") int empId)
    {
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Vehicle Id not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Vehicle Id not found").build();
        }
        List<Vehicle> vehicles = empRepo.getVehicles(empId);
        if(vehicles.size()>0)
        {
            GenericEntity<List<Vehicle>> genericEntity = new GenericEntity<List<Vehicle>>(vehicles){};
            return Response.ok(genericEntity).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Employee "+empId+" don't have Vehicles").build();
    }

    @GET
    @Path("{empId}/Vehicles/{vehicleId}")
    @Produces({"application/xml","application/json"})
    public Response getVehicle(@PathParam("empId") int empId,@PathParam("vehicleId") int vehicleId)
    {
        System.out.println("Getting vehicle details for having employee id: "+empId);
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Employee Id not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Employee Id not found").build();
        }
        Vehicle vehicle = empRepo.getVehicle(vehicleId,empId);
        if(empRepo.getVehicle(vehicleId,empId).getVehicleId()==0)
            return Response.status(Response.Status.NOT_FOUND).entity("Employee don't have vehicle with id: "+vehicleId).build();
        return Response.ok(Response.Status.OK).entity(vehicle).build();
    }

    @POST
    @Path("{empId}/Vehicles")
    @Consumes("application/x-www-form-urlencoded")
    public Response addVehicleBean(@Valid @BeanParam Vehicle vehicle, @PathParam("empId") int empId)
    {
        System.out.println("Adding vehicle to employee");
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Employee Id not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }
        empRepo.addVehicle(empId,vehicle);
        return Response.status(Response.Status.CREATED).entity("Vehicle added").build();
    }

    @POST
    @Path("{empId}/Vehicles")
    @Consumes({"application/xml","application/json"})
    public Response addVehicle(@Valid Vehicle vehicle, @PathParam("empId") int empId)
    {
        System.out.println("Adding vehicle to employee");
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Employee Id not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }
        empRepo.addVehicle(empId,vehicle);
        return Response.status(Response.Status.CREATED).entity("Vehicle added").build();
    }

    @PATCH
    @Path("{empId}/Vehicles/{vehicleId}")
    @Consumes("application/x-www-form-urlencoded")
    public Response updateVehicleBean(@Valid Vehicle vehicle,@PathParam("empId") int empId,@PathParam("vehicleId") int vehicleId)
    {
        System.out.println("Updating Vehicle having Id: "+empId);
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Employee not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Employee Id not found").build();
        }
        if(empRepo.getVehicle(vehicleId,empId).getVehicleId()==0)
        {
            System.out.println("Vehicle not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Employee don't have vehicle with id: "+vehicleId).build();
        }
        empRepo.updateVehicle(vehicleId,vehicle);
        return Response.status(Response.Status.OK).entity("Vehicle details updated").build();
    }

    @PATCH
    @Path("{empId}/Vehicles/{vehicleId}")
    @Consumes({"application/xml","application/json"})
    public Response updateVehicle(@Valid Vehicle vehicle,@PathParam("empId") int empId,@PathParam("vehicleId") int vehicleId)
    {
        System.out.println("Updating Vehicle having Id: "+empId);
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Employee not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Employee Id not found").build();
        }
        if(empRepo.getVehicle(vehicleId,empId).getVehicleId()==0)
        {
            System.out.println("Vehicle not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Employee don't have vehicle with id: "+vehicleId).build();
        }
        empRepo.updateVehicle(vehicleId,vehicle);
        return Response.status(Response.Status.OK).entity("Vehicle details updated").build();
    }

    @DELETE
    @Path("{empId}/Vehicles/{vehicleId}")
    public Response deleteVehicle(@PathParam("empId") int empId,@PathParam("vehicleId") int vehicleId)
    {
        System.out.println("Deleting vehicle having Id: "+empId);
        if(empRepo.getEmployee(empId).getId()==0)
        {
            System.out.println("Employee not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Employee Id not found").build();
        }
        if(empRepo.getVehicle(vehicleId,empId).getVehicleId()==0)
        {
            System.out.println("Vehicle not found");
            return Response.status(Response.Status.NOT_FOUND).entity("Employee don't have vehicle with id: "+vehicleId).build();
        }
        empRepo.deleteVehicle(vehicleId);
        return Response.status(Response.Status.OK).entity("Vehicle details deleted").build();
    }

}
