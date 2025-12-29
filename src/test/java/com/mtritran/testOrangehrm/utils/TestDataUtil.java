package com.mtritran.testOrangehrm.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class TestDataUtil {
    private static JsonNode rootNode;
    private static ObjectMapper mapper;

    static {
        try {
            mapper = new ObjectMapper();
            String filePath = "src/test/resources/test-data.json";
            rootNode = mapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Lỗi đọc file JSON: " + e.getMessage());
        }
    }

    public static String getLogin(String key) {
        return rootNode.path("login").path(key).asText();
    }

    // Trả về Template gốc từ JSON (Chưa random, chưa xử lý)
    public static EmployeeData getEmployeeTemplate(String key) {
        try {
            JsonNode node = rootNode.path("employees").path(key);
            return mapper.treeToValue(node, EmployeeData.class);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi map data employee key: " + key, e);
        }
    }

    public static String getImage(String key) {
        String path = rootNode.path("images").path(key).asText();
        URL resource = TestDataUtil.class.getClassLoader().getResource("images/" + path);

        if (resource == null) {
            throw new RuntimeException("Image not found: " + key + " -> " + path);
        }

        try {
            return Paths.get(resource.toURI()).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}