package com.group3.travelexpertsrest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/agents")
public class AgentResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAgents() {
        return TravelExpertsDB.getAgents();
    }
}