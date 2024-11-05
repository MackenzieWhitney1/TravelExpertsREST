package com.group3.travelexpertsrest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.Agent;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TravelExpertsDB {
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
        } //end getConnection
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
}
