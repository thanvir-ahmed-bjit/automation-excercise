package com.bjitgroup.utils;

import com.bjitgroup.exceptions.FrameworkException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * JSON read/write helpers backed by Jackson.
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() { /* utility */ }

    /** Deserialise a classpath JSON resource into the given type. */
    public static <T> T read(String resourcePath, Class<T> clazz) {
        try (InputStream in = stream(resourcePath)) {
            return MAPPER.readValue(in, clazz);
        } catch (IOException e) {
            throw new FrameworkException("Failed to read JSON: " + resourcePath, e);
        }
    }

    /** Deserialise a classpath JSON resource into a {@code List<Map<String,Object>>}. */
    public static List<Map<String, Object>> readList(String resourcePath) {
        try (InputStream in = stream(resourcePath)) {
            return MAPPER.readValue(in, new TypeReference<>() {});
        } catch (IOException e) {
            throw new FrameworkException("Failed to read JSON list: " + resourcePath, e);
        }
    }

    /** Deserialise a classpath JSON resource into a {@code Map<String,Object>}. */
    public static Map<String, Object> readMap(String resourcePath) {
        try (InputStream in = stream(resourcePath)) {
            return MAPPER.readValue(in, new TypeReference<>() {});
        } catch (IOException e) {
            throw new FrameworkException("Failed to read JSON map: " + resourcePath, e);
        }
    }

    private static InputStream stream(String resourcePath) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (in == null) throw new FrameworkException("JSON resource not found: " + resourcePath);
        return in;
    }
}


