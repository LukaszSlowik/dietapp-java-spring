package org.nutrihealthplan.dietapp.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class CacheKeyUtils {

    /**
     * Tworzy klucz dla cache oparty na filtrze produktów i parametrach paginacji.
     *
     * @param productFilter filtr produktów
     * @param pageable parametry paginacji
     * @return wygenerowany klucz do cache
     */
    public static String buildKey(Object productFilter, Pageable pageable) {
        // Tworzenie unikalnego ciągu znaków z parametrów
        String filterKey = productFilter != null ? productFilter.toString() : "";
        String pageableKey = pageable != null ? pageable.toString() : "";

        // Łączenie filtrów i parametrów paginacji, tworzenie unikalnego klucza
        String keyString = filterKey + "_" + pageableKey;

        // Zastosowanie funkcji hashującej (np. SHA-256) dla bezpieczeństwa klucza
        return DigestUtils.md5DigestAsHex(keyString.getBytes(StandardCharsets.UTF_8));
    }
}