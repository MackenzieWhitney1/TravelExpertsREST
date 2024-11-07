package com.group3.travelexpertsrest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import data.PackageDB;
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
    public String getPackages() { return PackageDB.getPackages(); }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{packageId}")
    public String getPackage(@PathParam("packageId") int packageId) {
        return PackageDB.getPackageById(packageId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addPackage(String jsonString) {
        return PackageDB.addPackages(jsonString);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePackage(String jsonString) {
        return PackageDB.updatePackage(jsonString);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{packageId}")
    public String deletePackage(@PathParam("packageId") int packageId) {
        return PackageDB.deletePackage(packageId);
    }
}
