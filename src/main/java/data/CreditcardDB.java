package data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.group3.travelexpertsrest.TravelExpertsDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import model.Creditcard;
import model.CreditcardDTO;
import model.Customer;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import static com.group3.travelexpertsrest.TravelExpertsDB.createFactory;

public class CreditcardDB {
    private static String jsonResult = null;
    private static final JsonObject result = new JsonObject();
    public static String getAllCreditcards() {
        EntityManager em;
        try (EntityManagerFactory emf = createFactory()) {
            em = emf.createEntityManager();
            Query q = em.createQuery("select c from Creditcard c");
            getCreditCardDTOsFromQuery(q);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonResult;
    }

    public static String getCustomerCreditcardsById(int customerId) {
        EntityManager em;
        try (EntityManagerFactory emf = createFactory()) {
            em = emf.createEntityManager();
            Query q = em.createQuery("select c from Creditcard c where c.customer.id = :customerId");
            q.setParameter("customerId", customerId);
            getCreditCardDTOsFromQuery(q);
        }
        return jsonResult;
    }

    private static void getCreditCardDTOsFromQuery(Query q) {
        List<Creditcard> creditcardlist = q.getResultList();

        List<CreditcardDTO> listCreditcardDTO = creditcardlist.stream().map(
                cc -> new CreditcardDTO(
                        cc.getId(),
                        cc.getCustomer().getId(),
                        cc.getCCNumber(),
                        cc.getCCExpiry(),
                        cc.getCCName()
                )).toList();

        Gson gson = new Gson();
        Type listType = new TypeToken<List<CreditcardDTO>>() {}.getType();
        jsonResult = gson.toJson(listCreditcardDTO, listType);
    }
    
    public static String addCreditCard(String jsonString){
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            
            Gson gson = new GsonBuilder().setDateFormat("YYYY-mm-dd").create();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            Creditcard creditcard = getCreditcardFromJSONObject(jsonObject, entityManager);

            entityManager.getTransaction().begin();
            entityManager.persist(creditcard);
            entityManager.getTransaction().commit();
            
            result.addProperty("msg", "Credit Card successfully created.");
            result.add("creditCard", jsonObject);
            jsonResult = gson.toJson(result);
            
        }
        return jsonResult;
    }

    private static Creditcard getCreditcardFromJSONObject(JsonObject jsonObject, EntityManager entityManager) {
        Creditcard creditcard = new Creditcard();

        // creditcard.setId(jsonObject.get("id").getAsInt());
        creditcard.setCCName(jsonObject.get("cCName").getAsString());
        String ccexpiry = jsonObject.get("cCExpiry").getAsString();

        creditcard.setCCExpiry(java.sql.Date.valueOf(ccexpiry));
        creditcard.setCCNumber(jsonObject.get("cCNumber").getAsString());
        Customer customer = entityManager.find(Customer.class, jsonObject.get("customerId").getAsInt());
        creditcard.setCustomer(customer);
        return creditcard;
    }

    public static String updateCreditCard(String jsonString) {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setDateFormat("YYYY-mm-dd").create();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
            Creditcard creditcard = getCreditcardFromJSONObject(jsonObject, entityManager);
            creditcard.setId(jsonObject.get("id").getAsInt());

            entityManager.getTransaction().begin();
            Creditcard mergeObject = entityManager.merge(creditcard);
            entityManager.getTransaction().commit();

            // Create a custom JSON object to include both the updated object and the message
            result.add("creditcard", gson.toJsonTree(mergeObject));  // Add the updated package
            result.addProperty("msg", "Update was successful!");  // Add the success message

            // Convert the result object to JSON
            jsonResult = gson.toJson(result);

        } catch (Exception e) {
            // Handle exceptions (e.g., logging)
            jsonResult = "{ \"msg\": \"Credit card update failed\" }";
            throw new RuntimeException(e);
        }

        return jsonResult;
    }

    public static String deleteCreditCard(int creditCardId) {
        try (EntityManagerFactory entityManagerFactory = TravelExpertsDB.createFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Creditcard creditcard = entityManager.find(Creditcard.class, creditCardId);

            if (creditcard == null) {
                jsonResult = "{\"msg\": \"Package not found\": }";
            }
            else {
                entityManager.getTransaction().begin();
                entityManager.remove(creditcard);
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
