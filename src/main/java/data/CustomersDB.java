package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.group3.travelexpertsrest.TravelExpertsDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import model.Agent;
import model.Customer;



import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDB {
    private static String jsonResult = null;
    private static JsonObject result = new JsonObject();

    public static String getCustomers() {
        EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("select c from Customer c");
        List<Customer> customers = query.getResultList();
        String jsonOut = "";
        for(Customer customer : customers){
            JsonObject json = new JsonObject();
            json.addProperty("CustomerId",customer.getId());
            json.addProperty("CustFirstName",customer.getCustFirstName());
            json.addProperty("CustLastName",customer.getCustLastName());
            json.addProperty("CustAddress",customer.getCustAddress());
            json.addProperty("CustCity",customer.getCustCity());
            json.addProperty("CustPostal",customer.getCustPostal());
            json.addProperty("CustCountry",customer.getCustCountry());
            json.addProperty("CustHomePhone",customer.getCustHomePhone());
            json.addProperty("CustBusPhone",customer.getCustBusPhone());
            json.addProperty("CustEmail",customer.getCustEmail());
            json.addProperty("AgentId",customer.getAgent().getId());
            jsonOut += json.toString();
        }
        //Gson gson = new Gson();
//        Type type = new TypeToken<List<Customer>>(){}.getType();

        return jsonOut;//gson.toJson(customers, type);
    }

    public static String getCustomerById( int customerId) {
        EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        model.Customer cust = entityManager.find(model.Customer.class, customerId);
        Gson gson = new Gson();
        return gson.toJson(cust);
    }

    public static String addCustomer(String jsonString) {
        try {
            EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Gson gson = new Gson();
            model.Customer cust = gson.fromJson(jsonString, Customer.class);
            entityManager.getTransaction().begin();
            entityManager.persist(cust);
            entityManager.getTransaction().commit();
            entityManager.close();
            jsonResult = "{\"msg\":\"Customer was created.\"}";
        } catch (Exception e) {
            jsonResult = "{\"msg\":\"Customer creation failed.\"}";
            throw new RuntimeException(e);
        }
        return jsonResult;
    }

    public static String updateCustomer(String jsonString) {
        try {
            EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Gson gson = new Gson();
            model.Customer cust = gson.fromJson(jsonString, model.Customer.class);
            entityManager.getTransaction().begin();
            model.Customer mergedObject = entityManager.merge(cust);
            entityManager.getTransaction().commit();
            entityManager.close();
            jsonResult = "{\"msg\":\"Customer was modified.\"}";
        }catch (Exception e) {
            jsonResult = "{\"msg\":\"Customer update failed.\"}";
            throw new RuntimeException(e);
        }
        return jsonResult;
    }

    public static String deleteCustomer(int customerId) {
        EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        model.Customer cust = entityManager.find(Customer.class, customerId);
        jsonResult = "";
        if(cust == null){
            jsonResult = "{\"msg\": \"Customer was not found.\"}";
        }else {
            entityManager.getTransaction().begin();
            entityManager.remove(cust);
            entityManager.getTransaction().commit();
            entityManager.close();
            jsonResult = "{\"msg\": \"Customer was deleted successfully.\"}";
        }
        return jsonResult;
    }
}
