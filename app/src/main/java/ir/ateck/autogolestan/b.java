package ir.ateck.autogolestan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class b {
    public static String b0 = null;
    public static String b1 = null;
    public static boolean f0(Activity r0) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (r0.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && r0.checkSelfPermission(Manifest.permission.INTERNET)
                    == PackageManager.PERMISSION_GRANTED && r0.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && r0.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions( r0, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                ActivityCompat.requestPermissions( r0, new String[]{Manifest.permission.INTERNET}, 1);
                ActivityCompat.requestPermissions( r0, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                ActivityCompat.requestPermissions( r0, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
    public static File f1(String r0,String r1){
        File t1 = new File(r0);
        if(!t1.exists()){
            t1.mkdirs();
            t1.mkdir();
        }
        File t2 = new File(t1,r1);
        try{
            if(!t2.exists())
                t2.createNewFile();}catch(IOException t20){
            t20.printStackTrace();
            return null;
        }
        return t2;
    }
    public static boolean f7(String r0){
        try{
            File t0 = new File(r0);
            return t0.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static String f2(){
        return "(" +(new SimpleDateFormat("yyyy-MM-dd-HH-mm")).format(Calendar.getInstance().getTime()) + ")";
    }
    public static String f3 (String r0,String r1,int r3) throws Exception {
        File tm1 = new File(r0);
        while (!tm1.isFile()){
            tm1 = new File(r0);
            Log.d("wait","waiting");
        }
        final InputStream t0 = new FileInputStream(tm1);
        final InputStreamReader t1 = new InputStreamReader(t0,StandardCharsets.US_ASCII.name());
        final BufferedReader t2 = new BufferedReader(t1);
        String t3 = t2.readLine();
        boolean t4 = false;
        boolean t5 = true;
        final boolean t7 = r3 == 0;
        String t6 = "";
        while (t3!=null && r3 >= 0){
            if(!t4)
                if(t3.contains(r1))
                    t4 = true;
            if(t4){
                if(t5){
                    t5 = false;
                    t6+=(t3.substring(t3.indexOf(r1)));
                }else{
                    t6+=(t3);
                }
                if(!t7)
                    r3--;
            }
            t3 = t2.readLine();
        }
        return t6;
    }

    public static  File[] f5(boolean r0){
        String t4;
        if(r0)
            t4 = b0;
        else
            t4 = b1;
        ArrayList<File> t1 = new ArrayList<>();
        File t2 = new File(C.FILES.DIRECTORY.c0,t4);
        int t3 = Integer.valueOf(t4.substring(t4.indexOf(')')+1));
        while (t2.exists()){
            t1.add(t2);
            t3++;
            t2 = new File(C.FILES.DIRECTORY.c0,t4.substring(0,t4.indexOf(')') + 1) + String.valueOf(t3));
        }
        File[] t1000 = new File[t1.size()];
        for(int i = 0; i < t1000.length ; i++)
            t1000[i] = t1.get(i);

        return t1000;
    }
    public static boolean f6(boolean r0){
        File[] t0 = f5(r0);
        boolean t2 = true;
        for(int t1 = 0 ; t1 < t0.length ; t1++)
            t2 = t2 && t0[t1].delete();
        b0 = null;
        return t2;
    }
    public static String f8 (String r0,String r1,String r2) throws Exception {
        File tm1 = new File(r0);
        while (!tm1.isFile()){
            tm1 = new File(r0);
            Log.d("wait","waiting");
        }
        final InputStream t0 = new FileInputStream(tm1);
        final InputStreamReader t1 = new InputStreamReader(t0,StandardCharsets.US_ASCII.name());
        final BufferedReader t2 = new BufferedReader(t1);
        String t3 = t2.readLine();
        boolean t4 = false;
        boolean t5 = true;
        String t6 = "";
        while (t3!=null){
            if(!t4)
                if(t3.contains(r1))
                    t4 = true;
                if(t3.contains(r2)) {
                    t6+= t3.substring(0,t3.indexOf(r2) + 1);
                    break;
                }
            if(t4){
                if(t5){
                    t5 = false;
                    t6+=(t3.substring(t3.indexOf(r1)));
                }else{
                    t6+=(t3);
                }
            }
            t3 = t2.readLine();
        }
        return t6;
    }

    public static void UPDATE(Context r0){
        SharedPreferences t0 = r0.getSharedPreferences(C.CSHAREDPREFS0.c0,Context.MODE_PRIVATE);
        SharedPreferences.Editor t1 = t0.edit();
        t1.putString(C.CSHAREDPREFS0.PROGRESS.c2,b0);
        File[] t2 = f5(true);
        for(int i = 0 ; i < t2.length ; i++){
            t1.putLong(C.CSHAREDPREFS0.PROGRESS.c1 + String.valueOf(i) , t2[i].lastModified());
        }
        t1.commit();
    }
}
