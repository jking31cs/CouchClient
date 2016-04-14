package com.jking31cs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jking31cs.annotations.CouchDB;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by jking31cs on 4/11/16.
 */
public class RestClient {

    private final ObjectMapper objectMapper;
    private final String baseURL;

    public RestClient(String baseURL) {
        this.baseURL = baseURL;
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private String createDbURL(String db) {
        return baseURL + "/" + db;
    }

    @SuppressWarnings("unchecked")
    private String getUUID() throws IOException {
        String url = createDbURL("_uuids");
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setUseCaches(false);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accpet", "application/json");

        Map result = objectMapper.readValue(conn.getInputStream(), Map.class);
        List<String> uuids = (List<String>) result.get("uuids");
        return uuids.get(0);
    }

    public <T> T get(String uuid, Class<T> clazz) throws IOException {
        if (!clazz.isAnnotationPresent(CouchDB.class)) {
            throw new RuntimeException("Generic Type must be annotated with CouchDB");
        }
        String url = clazz.getAnnotation(CouchDB.class).value();
        url = createDbURL(url) + "/" + uuid;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setUseCaches(false);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accpet", "application/json");

        return objectMapper.readValue(conn.getInputStream(), clazz);
    }

    public <T extends CouchObject> CouchResponse put(T object) throws IOException {
        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(CouchDB.class)) {
            throw new RuntimeException("Object class must be annotated with CouchDB.");
        }
        String uuid = object._id;
        if (uuid == null) {
            uuid = getUUID();
        } else {
            if (object._rev == null) {
                throw new RuntimeException("Rev must be defined to do an update.");
            }
        }
        String url = createDbURL(clazz.getAnnotation(CouchDB.class).value()) + "/" + uuid;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setUseCaches(false);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "*/*");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        OutputStream os = conn.getOutputStream();
        os.write(objectMapper.writeValueAsBytes(object));
        os.flush();
        return objectMapper.readValue(conn.getInputStream(), CouchResponse.class);
    }

}
