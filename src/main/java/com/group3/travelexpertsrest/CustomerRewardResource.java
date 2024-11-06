package com.group3.travelexpertsrest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/customer-reward")
public class CustomerRewardResource {
    @GET
    @Path("get/{ customerId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomerRewardsByCustomerId(@PathParam("customerId") int customerId) {
        return TravelExpertsDB.getCustomerRewardsById(customerId);
    }
}
