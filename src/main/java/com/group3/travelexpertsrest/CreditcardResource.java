package com.group3.travelexpertsrest;

import com.google.gson.JsonObject;
import data.CreditcardDB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/creditcards")
public class CreditcardResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCreditcards() {
        return CreditcardDB.getAllCreditcards();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{customerId}")
    public String getCustomerCreditcard(@PathParam("customerId") int customerId) {
        return CreditcardDB.getCustomerCreditcardsById(customerId);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/post")
    public String addCreditCard(String jsonString){
        return CreditcardDB.addCreditCard(jsonString);
    }
    
}