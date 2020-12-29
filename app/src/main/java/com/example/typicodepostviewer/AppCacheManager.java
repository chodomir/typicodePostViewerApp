package com.example.typicodepostviewer;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppCacheManager {
    // Class that holds data and when it was created
    private static class CacheInfo<T extends Serializable> implements Serializable {
        //default serialVersion id
        private static final long serialVersionUID = 1L;

        private final T mData;
        private final long mTimestamp;

        CacheInfo(T data, long timestamp) {
            mData = data;
            mTimestamp = timestamp;
        }

        public T getData() {
            return mData;
        }

        public long getTimestamp() {
            return mTimestamp;
        }
    }

    private static CacheInfo<ArrayList<Post>> sPosts;
    private static Map<Integer, CacheInfo<User>> sUsers;
    private static File sFile;

    public static final int CACHE_MAX_AGE_SECONDS = 300;
    public static final String TAG = "AppCacheManager";

    private static void LoadCache() {
        try(ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(sFile))) {
            sPosts = (CacheInfo<ArrayList<Post>>) objIn.readObject();
            sUsers = (Map<Integer, CacheInfo<User>>) objIn.readObject();
            objIn.close();

            Log.d(TAG, "LoadCache():: Cache load successful!");
        } catch (IOException | ClassNotFoundException e) {
            Log.d(TAG, "ERROR:: LoadCache():: " + e.getMessage());
        }
    }

    private static boolean IsCacheValid(long timestamp) {
        long t = System.currentTimeMillis() / 1000;
        return ((t - timestamp) <= CACHE_MAX_AGE_SECONDS);
    }

    /**
     * Initializes cache that will be used in the app. Methods needs to be called before
     * using any other of its static methods
     * @param dest filepath where the cache should be located
     * @param fileName name of the file
     */
    public static void Install(File dest, String fileName) {
        sFile = new File(dest, fileName);
        sPosts = new CacheInfo<>(null, 0);
        sUsers = new HashMap<>();
        if (sFile.exists()) {
            // Load data from previous usage
            Log.d(TAG, "Install():: Cache found! Loading cache data...");
            LoadCache();
        }
    }

    public static ArrayList<Post> GetPosts(boolean forceNetworkResponse) {
        // Request an API call if cache not available or cache expired
        if (forceNetworkResponse || !IsCacheValid(sPosts.getTimestamp())) {
            Log.d(TAG, "GetPosts():: Cache invalid. Requesting API call.");
            ArrayList<Post> posts = ApiRequest.GetPosts();
            long timestamp = System.currentTimeMillis() / 1000;
            sPosts = new CacheInfo<>(posts, timestamp);
        } else Log.d(TAG, "GetPost():: Cache hit!");

        return sPosts.getData();
    }

    public static User GetUser(int id) {
        CacheInfo<User> ci = sUsers.get(id);

        // Request API call if cache not available or cache expired
        if (ci == null || (ci != null && !IsCacheValid(ci.getTimestamp()))) {
            Log.d(TAG, "GetUser():: Cache invalid .Requesting API call");
            User user = ApiRequest.GetUser(id);
            long timestamp = System.currentTimeMillis()/1000;
            sUsers.put(id, new CacheInfo<>(user, timestamp));
        } else Log.d(TAG, "GetUser():: Cache hit!");

        return sUsers.get(id).getData();
    }

    public static void RemovePost(int position) {
        if (sPosts.getData() != null) {
            Log.d(TAG, "RemovePost():: Post removed");
            sPosts.getData().remove(position);
        } else Log.d(TAG, "RemovePost():: Removal failed:: sPost.getData() is Null");
    }
    
    public static void WriteToDisk() {
        try(ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(sFile))) {
            objOut.writeObject(sPosts);
            objOut.writeObject(sUsers);
            objOut.close();

            Log.d(TAG, "WriteToDisk():: Write successful!");
        } catch (IOException e) {
            Log.d(TAG, "ERROR:: WriteToDisk():: " + e.getMessage());
        }
    }
}
