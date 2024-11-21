package com.group3.travelexpertsrest;

import data.CreditcardDB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
}