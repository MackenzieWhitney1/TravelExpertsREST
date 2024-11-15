package data;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import model.Creditcard;

import java.lang.reflect.Type;
import java.util.List;

import static com.group3.travelexpertsrest.TravelExpertsDB.createFactory;

public class CreditcardDB {
    private static String jsonResult = null;
    private static final JsonObject result = new JsonObject();
    public static String getRewards() {
        EntityManager em;
        try (EntityManagerFactory emf = createFactory()) {
            em = emf.createEntityManager();
            Query q = em.createQuery("select c.id, c.cCName, c.cCExpiry, c.cCNumber, c.customer.id from Creditcard c");
            List<Creditcard> creditcardlist = q.getResultList();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Creditcard>>() {}.getType();
            jsonResult = gson.toJson(creditcardlist, listType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonResult;
    }

    public static String getCustomerCreditcardsById(int customerId) {
        EntityManager em;
        try (EntityManagerFactory emf = createFactory()) {
            em = emf.createEntityManager();
            Query q = em.createQuery("select c.id, c.cCName, c.cCNumber, c.cCExpiry from Creditcard c where c.customer.id = :customerId");
            q.setParameter("customerId", customerId);
            List customerCreditcardList = q.getResultList();
            Gson gson = new Gson();
            jsonResult = gson.toJson(customerCreditcardList);
        }
        return jsonResult;
    }
}
