package data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.group3.travelexpertsrest.TravelExpertsDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import model.*;

import java.lang.reflect.Type;
import java.util.List;

public class RewardsDB {
    private static String jsonResult = null;
    private static JsonObject result = new JsonObject();

    public static String getRewards() {
        EntityManagerFactory emf = TravelExpertsDB.createFactory();
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select r from Reward r");
        List<Reward> rewardlist = q.getResultList();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Reward>>(){}.getType();
        return gson.toJson(rewardlist, listType);
    }

    public static String getCustomerRewardsById(int customerId) {
        EntityManagerFactory emf = TravelExpertsDB.createFactory();
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select c from CustomersReward c where c.customer.id = :customerId");
        q.setParameter("customerId", customerId);

        List<CustomersReward> customerRewardList = q.getResultList();

        List<CustomersRewardDTO> listCustomerRewardDTO = customerRewardList.stream().map(
                cr -> new CustomersRewardDTO(
                        cr.getId().getCustomerId(),
                        cr.getId().getRewardId(),
                        cr.getRwdNumber()
                )).toList();
        Gson gson = new Gson();
        return gson.toJson(listCustomerRewardDTO, listCustomerRewardDTO.getClass());
    }

    public static String deleteCustomerReward(int customerId, int rewardId) {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CustomersRewardId customersRewardId = new CustomersRewardId();
            customersRewardId.setCustomerId(customerId);
            customersRewardId.setRewardId(rewardId);
            model.CustomersReward customersReward = entityManager.find(CustomersReward.class, customersRewardId);

            if (customersReward == null) {
                jsonResult = "{\"msg\": \"Customer Reward not found\": }";
            }
            else {
                entityManager.getTransaction().begin();
                entityManager.remove(customersReward);
                entityManager.getTransaction().commit();
                Gson gson = new Gson();
                result.addProperty("msg", "Customer Reward deleted successfully");
                jsonResult = gson.toJson(result);
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., logging)
            jsonResult = "{ \"msg\": \"Customer Reward deletion failed\" }";
            throw new RuntimeException(e);
        }

        return jsonResult;
    }

    public static String addCustomerReward(String jsonString) {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            CustomersReward customersReward = getCustomersReward(jsonObject, entityManager);

            entityManager.getTransaction().begin();
            entityManager.persist(customersReward);
            entityManager.getTransaction().commit();

            result.addProperty("msg", "Customer Reward successfully created.");
            result.add("customerReward", jsonObject);
            jsonResult = gson.toJson(result);

        } catch (Exception e) {
            // Handle exceptions (e.g., logging)
            jsonResult = "{ \"msg\": \"Customer reward creation failed\" }";
            throw new RuntimeException(e);
        }
        return jsonResult;
    }

    private static CustomersReward getCustomersReward(JsonObject jsonObject, EntityManager entityManager) {
        int parsedCustomerId = jsonObject.get("customerId").getAsInt();
        int parsedRewardId = jsonObject.get("rewardId").getAsInt();
        String parsedRwdNumber = jsonObject.get("rwdNumber").getAsString();

        CustomersRewardId customersRewardId = new CustomersRewardId();
        customersRewardId.setCustomerId(parsedCustomerId);
        customersRewardId.setRewardId(parsedRewardId);

        CustomersReward customersReward = new CustomersReward();
        customersReward.setId(customersRewardId);
        customersReward.setRwdNumber(parsedRwdNumber);

        Customer customer = entityManager.find(Customer.class, parsedCustomerId);
        customersReward.setCustomer(customer);
        return customersReward;
    }

    public static String updateCustomerReward(String jsonString) {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            CustomersReward customersReward = getCustomersReward(jsonObject, entityManager);

            entityManager.getTransaction().begin();
            entityManager.merge(customersReward);
            entityManager.getTransaction().commit();

            result.addProperty("msg", "Update was successful!");  // Add the success message

            // Convert the result object to JSON
            jsonResult = gson.toJson(result);

        } catch (Exception e) {
            // Handle exceptions (e.g., logging)
            jsonResult = "{ \"msg\": \"Customer Reward update failed\" }";
            throw new RuntimeException(e);
        }

        return jsonResult;
    }

    public static String batchUpdateCustomerRewards(String jsonArrayString) {
        String jsonResult;
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
            JsonObject result = new JsonObject();

            entityManager.getTransaction().begin();

            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                CustomersReward customersReward = getCustomersReward(jsonObject, entityManager);
                entityManager.merge(customersReward);
            }

            entityManager.getTransaction().commit();

            result.addProperty("msg", "Batch update was successful!");  // Add the success message
            jsonResult = gson.toJson(result);

        } catch (Exception e) {
            jsonResult = "{ \"msg\": \"Customer Reward update failed\" }";
            throw new RuntimeException(e);
        }

        return jsonResult;
    }

}
