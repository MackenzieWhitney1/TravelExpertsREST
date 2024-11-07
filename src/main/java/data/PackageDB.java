package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.group3.travelexpertsrest.TravelExpertsDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import model.Package;

import java.lang.reflect.Type;
import java.util.List;

public class PackageDB {
    private static String jsonResult = null;
    private static final JsonObject result = new JsonObject();

    public static String getPackages() {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            // Query to retrieve the packages
            TypedQuery<model.Package> query = entityManager.createQuery("select p from Package p", model.Package.class);
            List<model.Package> packages = query.getResultList();

            // Set scale for BigDecimal fields to 2 decimal places
            for (model.Package pkg : packages) {
                pkg.setPkgBasePrice(pkg.getPkgBasePrice());
                pkg.setPkgAgencyCommission(pkg.getPkgAgencyCommission());
            }

            // Convert the list of packages to JSON
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            Type type = new TypeToken<List<Package>>() {}.getType();
            jsonResult = gson.toJson(packages, type);

        } catch (Exception e) {
            // Handle exceptions (e.g., logging)
            throw new RuntimeException(e);
        }

        return jsonResult;
    }


    public static String getPackageById(int packageId) {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            Package pkg = entityManager.find(Package.class, packageId);

            Gson gson = new Gson();
            jsonResult = gson.toJson(pkg);
        } catch (Exception e) {
            // Handle exceptions (e.g., logging)
            throw new RuntimeException(e);
        }

        return jsonResult;
    }

    public static String addPackages(String jsonString) {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            // Set up Gson with a custom date format to handle dates in JSON
            Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss").create();
            Package pkg = gson.fromJson(jsonString, Package.class);

            entityManager.getTransaction().begin();
            entityManager.persist(pkg);
            entityManager.getTransaction().commit();

            // Access the generated ID
            Integer packageId = pkg.getId();

            result.addProperty("msg", "Package successfully created.");
            result.addProperty("id", packageId);

            jsonResult = gson.toJson(result);

        } catch (Exception e) {
            // Handle exceptions (e.g., logging)
            jsonResult = "{ \"msg\": \"Package creation failed\" }";
            throw new RuntimeException(e);
        }

        return jsonResult;
    }

    public static String updatePackage(String jsonString) {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss").create();
            Package pkg = gson.fromJson(jsonString, Package.class);

            entityManager.getTransaction().begin();
            Package mergeObject = entityManager.merge(pkg);
            entityManager.getTransaction().commit();

            // Create a custom JSON object to include both the updated object and the message
            result.add("package", gson.toJsonTree(mergeObject));  // Add the updated package
            result.addProperty("msg", "Update was successful!");  // Add the success message

            // Convert the result object to JSON
            jsonResult = gson.toJson(result);

        } catch (Exception e) {
            // Handle exceptions (e.g., logging)
            jsonResult = "{ \"msg\": \"Package creation failed\" }";
            throw new RuntimeException(e);
        }

        return jsonResult;
    }

    public static String deletePackage(int packageId) {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Package pkg = entityManager.find(Package.class, packageId);

            if (pkg == null) {
                jsonResult = "{\"msg\": \"Package not found\": }";
            }
            else {
                entityManager.getTransaction().begin();
                entityManager.remove(pkg);
                entityManager.getTransaction().commit();
                jsonResult = "{ \"msg\": \"Package deleted\" }";
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., logging)
            jsonResult = "{ \"msg\": \"Package deletion failed\" }";
            throw new RuntimeException(e);
        }

        return jsonResult;
    }
}
