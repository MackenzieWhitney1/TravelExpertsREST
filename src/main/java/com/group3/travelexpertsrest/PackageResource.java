package com.group3.travelexpertsrest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.Package;

@Path("/packages")
public class PackageResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPackages() { return TravelExpertsDB.getPackages(); }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{packageId}")
    public String getPackage(@PathParam("packageId") int packageId) {
        return TravelExpertsDB.getPackageById(packageId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("postpackage")
    public String postPackage(String jsonString) {
        return TravelExpertsDB.addPackages(jsonString);
    }
}
