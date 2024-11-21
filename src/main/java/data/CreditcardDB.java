package data;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import model.Creditcard;
import model.CreditcardDTO;

import java.lang.reflect.Type;
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
}
