package com.group3.travelexpertsrest;

import data.RewardsDB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/customer-reward")
public class CustomerRewardResource {
    @GET
    @Path("get/{ customerId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomerRewardsByCustomerId(@PathParam("customerId") int customerId) {
        return RewardsDB.getCustomerRewardsById(customerId);
    }

    @POST
    @Path("post/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postCustomerRewardRecord(String jsonString){
        return RewardsDB.addCustomerReward(jsonString);
    }

    @PUT
    @Path("put/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String putCustomerRewardRecord(String jsonString){
        return RewardsDB.updateCustomerReward(jsonString);
    }

    @PUT
    @Path("put/batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String putBatchCustomerRewardRecord(String jsonString){
        return RewardsDB.batchUpdateCustomerRewards(jsonString);
    }

    @DELETE
    @Path("delete/{customerId}/{rewardId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteCustomerRewardRecord(
            @PathParam("customerId") int customerId,
            @PathParam("rewardId") int rewardId){
        return RewardsDB.deleteCustomerReward(customerId, rewardId);
    }
}
