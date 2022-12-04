package dev.degallant.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * In memory cached implementation that uses ResourceServer class as base
 */
public class CachedResourceServer extends ResourceServer {

    private final Map<String, String> cache = new HashMap<>();
    private String lastHitKey;

    @Override
    public String getResource(int id) {

        return getResource(id, false);

    }

    public String getResource(int id, boolean refresh) {
        String key = "resource/" + id;
        return getFromCacheOrFetch(key, refresh, () -> super.getResource(id));
    }

    @Override
    public String getResources() {
        String key = "resources";
        return getFromCacheOrFetch(key, false, super::getResources);
    }

    @Override
    public String getResources(int page) {
        String key = "resources?page=" + page;
        return getFromCacheOrFetch(key, false, () -> super.getResources(page));
    }

    @Override
    public String getResources(int page, int perPage) {
        String key = "resources?page=" + page + "&perPage=" + perPage;
        return getFromCacheOrFetch(key, false, () -> super.getResources(page, perPage));
    }

    private String getFromCacheOrFetch(String key, boolean refresh, Supplier<String> request) {
        if (cache.containsKey(key) && !refresh) {
            lastHitKey = key;
            return cache.get(key);
        }
        String resource = request.get();
        cache.put(key, resource);
        return resource;
    }

    public String getLastCacheHit() {
        if (lastHitKey == null) {
            return null;
        }
        return cache.get(lastHitKey);
    }

}
