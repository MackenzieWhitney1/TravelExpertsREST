package com.group3.travelexpertsrest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/rewards")
public class RewardResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRewards() {
        return TravelExpertsDB.getRewards();
    }
}
