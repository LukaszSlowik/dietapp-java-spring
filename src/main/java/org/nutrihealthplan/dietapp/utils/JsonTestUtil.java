package org.nutrihealthplan.dietapp.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonTestUtil {
    public static String readJson(String resourcePath) throws Exception {
        try (InputStream is = JsonTestUtil.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) throw new IllegalArgumentException("File not found: " + resourcePath);
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
