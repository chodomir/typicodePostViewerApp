package com.example.typicodepostviewer;

import org.junit.Test;

import java.io.File;
import java.io.Serializable;

import static org.junit.Assert.*;

public class ApiResponseCacheTest {

    private static class TestClass implements Serializable {
        private final int mFoo;

        TestClass(int foo) {
            mFoo = foo;
        }

        public int getFoo() {
            return mFoo;
        }
    }

    private static class TestApiRequest extends ApiRequest<TestClass> {
        @Override
        public TestClass getParsedResponse(String url) {
            return new TestClass(10);
        }
    }

    @Test
    public void getData_cacheKeyNotExist_addsItToCache() {
        ApiResponseCache<TestClass> cache =
                new ApiResponseCache<>(new File(""), "",
                        new TestApiRequest(), 10);

        TestClass a = cache.getData("key", false);

        assertNotNull(a);
    }

    @Test
    public void getData_cacheKeyExists_returnsCachedItem() {
        ApiResponseCache<TestClass> cache =
                new ApiResponseCache<>(new File(""), "",
                        new TestApiRequest(), 10);

        TestClass a = cache.getData("key", false);
        TestClass b = cache.getData("key", false);

        assertSame(a, b);
    }

    @Test
    public void getData_cacheExpired_updatesCache() throws InterruptedException {
        ApiResponseCache<TestClass> cache =
                new ApiResponseCache<>(new File(""), "",
                        new TestApiRequest(), 1);

        TestClass a = cache.getData("key", false);
        Thread.sleep(2000);
        TestClass b = cache.getData("key", false);

        assertNotSame(a, b);
    }

    @Test
    public void getData_forceNetworkResponse_overridesCache() {
        ApiResponseCache<TestClass> cache =
                new ApiResponseCache<>(new File(""), "",
                        new TestApiRequest(), 10);

        TestClass a = cache.getData("key", false);
        TestClass b = cache.getData("key", true);

        assertNotSame(a, b);
    }
}