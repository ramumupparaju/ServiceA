package com.incon.service.utils;

import android.content.Context;


import com.incon.service.AppUtils;
import com.incon.service.ConnectApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created on 26 Jul 2017 5:36 PM.
 *
 */
public class OfflineDataManager {

    public void saveData(Object data, String fileName) {


        Logger.e("cache", "cacheData===save data");
        try {
            String pojoToJson = AppUtils.pojoToJson(data);
            Logger.e("cache", "cacheData=toJason=" + pojoToJson);
            FileOutputStream fos = ConnectApplication.getAppContext().openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pojoToJson);
            oos.close();
            Logger.e("cache", "cacheData===save data success");
        }
        catch (Exception e) {
            Logger.e("cache", "cacheData===save data error");
            e.printStackTrace();
        }

    }

    public <T> T loadData(Class<T> aClass, String fileName) {

        Logger.e("cache", "cacheData===load data");
        String data = null;
        FileInputStream fis;
        try {
            fis = ConnectApplication.getAppContext().openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            data  = (String) ois.readObject();
            ois.close();

            Logger.e("cache", "cacheData===load data success");
        } catch (Exception e) {
            Logger.e("cache", "cacheData===load data error");
            e.printStackTrace();
        }
        T t = AppUtils.jsonToPojo(aClass, data);
        Logger.e("cache", "cacheData=toPojo=" + t);
        return t;
    }

    public void clearCache(Context context, String fileName) {
        File cacheDir = context.getFilesDir();
        File cacheFile = new File(cacheDir, "CachedData");
        Logger.e("cache", "cacheData==clear==file exists===1=" + cacheFile.exists());
        cacheFile.delete();
        Logger.e("cache", "cacheData==clear==file exists===2=" + cacheFile.exists());
    }

    public void clearAllCache(Context context) {

        File cacheDir = context.getFilesDir();
        for (File file : cacheDir.listFiles()) {
            try {
                delete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void delete(File file) throws IOException {
        if (file.isDirectory()) {
            for (File c : file.listFiles())
                c.delete();
        }
        if (!file.delete())
            throw new FileNotFoundException("Failed to delete file: " + file);
    }
}
