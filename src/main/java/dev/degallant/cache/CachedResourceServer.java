package dev.degallant.cache;

import java.util.HashMap;
import java.util.Map;

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

        lastHitKey = null;
        if (cache.containsKey(key) && !refresh) {
            lastHitKey = key;
            return cache.get(key);
        }
        String resource = super.getResource(id);
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
