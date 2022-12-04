package dev.degallant.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CachedResourcesTests {

    @Test
    public void cacheIsHitOnSecondRequest() {

        var server = new CachedResourceServer();

        String firstResponse = server.getResource(1);
        assertTrue(firstResponse.contains("\"id\":1"), "id not found on response: " + firstResponse);
        assertNull(server.getLastCacheHit());

        String secondResponse = server.getResource(1);
        String lastCacheHit = server.getLastCacheHit();
        assertEquals(secondResponse, lastCacheHit);

    }

    @Test
    public void serverHasSupportForPagination() {

        var server = new CachedResourceServer();

        String response = server.getResources();
        assertTrue(response.contains("\"page\":1"), "Did not found page count on response " + response);

        response = server.getResources(2);
        assertTrue(response.contains("\"page\":2"), "Did not found page count on response " + response);

        response = server.getResources(1, 3);
        assertTrue(response.contains("\"per_page\":3"), "Did not found per page count on response " + response);

    }

    @Test
    public void paginationRequestsAreCached() {

        var server = new CachedResourceServer();

        server.getResources();
        String response = server.getResources();
        assertEquals(response, server.getLastCacheHit());

        server.getResources(2);
        response = server.getResources(2);
        assertEquals(response, server.getLastCacheHit());

        server.getResources(1, 3);
        response = server.getResources(1, 3);
        assertEquals(response, server.getLastCacheHit());

    }

}