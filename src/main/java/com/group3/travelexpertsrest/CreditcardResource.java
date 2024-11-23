package com.group3.travelexpertsrest;

import data.CreditcardDB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.text.ParseException;

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
    public String addCreditCard(String jsonString) throws ParseException {
        return CreditcardDB.addCreditCard(jsonString);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/put")
    public String updateCreditCard(String jsonString){
        return CreditcardDB.updateCreditCard(jsonString);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{creditCardId}")
    public String deletePackage(@PathParam("creditCardId") int creditCardId) {
        return CreditcardDB.deleteCreditCard(creditCardId);
    }
    
}