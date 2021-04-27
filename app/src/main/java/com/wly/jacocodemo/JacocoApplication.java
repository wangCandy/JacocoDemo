package com.wly.jacocodemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: wangliyan
 * Date: 2021/4/7 11:34 AM
 * Description: application类
 */
public class JacocoApplication extends Application {

    private static final String TAG = "JacocoApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        createEcFile();
        new JacocoThread().start();
    }

    private void createEcFile() {
        String DEFAULT_COVERAGE_FILE_PATH = Environment.getExternalStorageDirectory() + "/coverage.ec";
        Log.d(TAG , DEFAULT_COVERAGE_FILE_PATH);
        File file = new File(DEFAULT_COVERAGE_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d(TAG, "异常 : " + e);
                e.printStackTrace();
            }
        }
    }

    private void writeEcFile() {
        Log.d(TAG, "writeEcFile");
        String DEFAULT_COVERAGE_FILE_PATH = Environment.getExternalStorageDirectory() + "/coverage.ec";
        Log.d(TAG , "path:" + DEFAULT_COVERAGE_FILE_PATH);
        OutputStream out = null;
        try {
            out = new FileOutputStream(DEFAULT_COVERAGE_FILE_PATH , true);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);
            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));
        } catch (Exception e) {
            Log.d(TAG, e.toString(), e);
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class JacocoThread extends Thread{
        @Override
        public void run() {
            while (true) {
                writeEcFile();
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
