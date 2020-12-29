package com.example.typicodepostviewer;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class ApiResponseCache<V extends Serializable> {
    public static final String TAG = "ApiResponseCache";
    private final File mFile;
    private HashMap<String, CacheInfo<V>> mData;
    private final ApiRequest<V> mRequest;
    private final long mCacheAge;

    private static class CacheInfo<T> implements Serializable {
        //default serialVersion id
        private static final long serialVersionUID = 1L;

        private final  T mData;
        private final long mTimestamp;

        public CacheInfo(T data, long timestamp) {
            mData = data;
            mTimestamp = timestamp;
        }

        public T getData() { return mData; }
        public long getTimestamp() { return mTimestamp; }
    }

    private boolean loadCache() {
        try(ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(mFile))) {
            mData = (HashMap<String, CacheInfo<V>>) objIn.readObject();
            objIn.close();
            return  true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    private boolean isCacheStale(long timestamp) {
        long now = System.currentTimeMillis() / 1000;
        return (now - timestamp) > mCacheAge;
    }

    private boolean isCacheMiss(String key) {
        CacheInfo<V> ci = mData.get(key);
        return ci == null || isCacheStale(ci.getTimestamp());
    }

    public ApiResponseCache(File file, String fileName, ApiRequest<V> request, long cacheMaxAge) {
        mFile = new File(file, fileName);
        mRequest = request;
        mCacheAge = cacheMaxAge;

        if (mFile.exists()) {
            boolean isLoaded = loadCache();
            if (!isLoaded) {
                mData = new HashMap<>();
                Log.d(TAG, "constructor():: Cache loading failed.");
            } else {
                Log.d(TAG, "constructor():: Cache successfully loaded.");
            }
        } else {
            mData = new HashMap<>();
        }
    }

    public V getData(String key, boolean forceNetworkResponse) {
        if (forceNetworkResponse || isCacheMiss(key)) {
            Log.d(TAG, "getData():: Cache miss or forced network response.");

            V parsedData = mRequest.getParsedResponse(key);
            long now = System.currentTimeMillis() / 1000;
            mData.put(key, new CacheInfo<V>(parsedData, now));
        } else {
            Log.d(TAG, "getData():: Cache hit.");
        }

        return mData.get(key).getData();
    }

    public boolean save() {
        try(ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(mFile))) {
            objOut.writeObject(mData);
            objOut.close();
            Log.d(TAG, "save():: Cache saved to disk.");
            return  true;
        } catch (IOException e) {
            Log.d(TAG, "save():: Save failed:" + e.getMessage());
            return false;
        }
    }
}
