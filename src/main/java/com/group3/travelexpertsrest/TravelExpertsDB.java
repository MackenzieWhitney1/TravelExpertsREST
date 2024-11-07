package com.group3.travelexpertsrest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.*;
import model.Agent;
import model.Package;
import model.Reward;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TravelExpertsDB {
    private static String jsonResult = null;
    private static JsonObject result = new JsonObject();
    public static EntityManagerFactory createFactory() {
        Properties props = new Properties();
        Map<String,String> map = new HashMap<>();
        // retrieve connection info from the properties file
        try {
            FileInputStream fis = new FileInputStream("c:\\travelexperts.properties");
            props.load(fis);
            map.put("jakarta.persistence.jdbc.url", props.getProperty("DB_URL"));
            map.put("jakarta.persistence.jdbc.user", props.getProperty("DB_USER"));
            map.put("jakarta.persistence.jdbc.password", props.getProperty("DB_PASSWORD"));
            map.put("jakarta.persistence.jdbc.driver", props.getProperty("DB_DRIVER"));
            map.put("hibernate.dialect", props.getProperty("DB_DIALECT"));
        } catch (IOException e) {
            throw new RuntimeException("Problem with reading connection info: " + e.getMessage());
        }
        System.out.println(props);
        System.out.println(map);
        return Persistence.createEntityManagerFactory("TravelExpertsDB", map);
    }
    public static String getAgents() {
        EntityManagerFactory emf = createFactory();
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select a from Agent a");
        List<Agent> agentlist = q.getResultList();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Agent>>(){}.getType();
        return gson.toJson(agentlist, listType);
    }
    public static String getRewards() {
        EntityManagerFactory emf = createFactory();
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select r from Reward r");
        List<Reward> rewardlist = q.getResultList();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Reward>>(){}.getType();
        return gson.toJson(rewardlist, listType);
    }

    public static String getCustomerRewardsById(int customerId) {
        EntityManagerFactory emf = createFactory();
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select c.id.customerId, c.id.rewardId, c.rwdNumber from CustomersReward c where c.customer.id = :customerId");
        q.setParameter("customerId", customerId);
        List customerRewardList = q.getResultList();
        Gson gson = new Gson();
        return gson.toJson(customerRewardList);
    }
}
