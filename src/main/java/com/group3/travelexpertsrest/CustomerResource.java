package com.group3.travelexpertsrest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import data.CustomersDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.Customer;

import java.lang.reflect.Type;
import java.util.List;

@Path("/customers")
public class CustomerResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomers() {
        return CustomersDB.getCustomers();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getcustomer/{ customerId }")
    public String getCustomerById(@PathParam("customerId") int customerId) {
        return CustomersDB.getCustomerById(customerId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("addcustomer")
    public String addCustomer(String jsonString) {
        return CustomersDB.addCustomer(jsonString);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("updatecustomer")
    public String updateCustomer(String jsonString) {
        return CustomersDB.updateCustomer(jsonString);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deletecustomer/{customerId}")
    public String deleteCustomer(@PathParam("customerId") int customerId) {
        return CustomersDB.deleteCustomer(customerId);
    }

}
