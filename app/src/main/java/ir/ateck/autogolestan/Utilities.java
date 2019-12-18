package ir.ateck.autogolestan;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Utilities {
    private Context context;
    private SharedPreferences sharedPreferences;
    Utilities(Context r0){
        if(r0 == null)
            Error.printError("Uitilities:" + this.toString() + "::null context passed");
        context = r0;
        sharedPreferences = context.getSharedPreferences(C.CSHAREDPREFS0.c0 , Context.MODE_PRIVATE);
    }
    public boolean isNetWorkConnectionAvailable(){
        try {
            boolean haveConnectedWifi = false;
            boolean haveConnectedMobile = false;

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            return haveConnectedWifi || haveConnectedMobile;
        }catch (Exception e){
            return false;
        }
    }
    public int findIndexById(int id , ArrayList<ab> collextion){
        for(int i =0 ; i < collextion.size() ; i++) {
            if (collextion.get(i) != null) {
                if (collextion.get(i).getId() == id) {
                    return i;
                }
            }
        }

        return -1;
    }
    public String getStringSetting(String key){
        return sharedPreferences.getString(key , null);
    }
    public int getIntSetting(String key){
        return sharedPreferences.getInt(key , Integer.MAX_VALUE);
    }
    public boolean writeSetting(String key , String value){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key,value);
        return edit.commit();
    }
    public boolean writeSetting(String key , int value){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key,value);
        return edit.commit();
    }
    public boolean removeSetting(String key){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(key);
        return edit.commit();
    }

    public void showProgressBar(final TaskChecker checker , String info , boolean notouch){
        final Dialog progDial = new Dialog(context);
        progDial.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progDial.setContentView(R.layout.progress);
        if(notouch) {
            progDial.setCancelable(false);
            progDial.setCanceledOnTouchOutside(false);
        }
        final RotateAnimation t1 = new RotateAnimation(0.0f,1080f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        t1.setDuration(1500);
        t1.setRepeatCount(Animation.INFINITE);
        progDial.findViewById(R.id.progress_image_view).startAnimation(t1);
        progDial.show();

        Toast.makeText(context , info , Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!checker.isItDone());
                handle(0 , progDial ,0,0);
            }
        }).start();
    }

    public void initilizeWebView(ab temp){
        temp.getSettings().setJavaScriptEnabled(true);
        temp.getSettings().setLoadWithOverviewMode(true);
        temp.getSettings().setUseWideViewPort(true);
        temp.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        temp.getSettings().setSupportMultipleWindows(true);
        temp.getSettings().setDomStorageEnabled(true);
        temp.getSettings().setBuiltInZoomControls(true);
    }

    public String getCurrentTimeFromPhone(String format){
        return new j().f2(new SimpleDateFormat(format).format(Calendar.getInstance().getTime()));
    }

    public String addTimeDiffToTimeString(String main , int diff){

        Log.d("time" , main + " " + diff);

        int hour = diff / 3600;
        int min = (diff - hour * 3600) / 60;
        int sec = (diff - hour * 3600 - min * 60);

        int curhour = Integer.valueOf(main.substring(0,2));
        int curmin = Integer.valueOf(main.substring(3,5));
        int cursec = Integer.valueOf(main.substring(6,8));

        curhour+=hour;
        curmin+=min;
        cursec+=sec;

        curmin+=cursec/60;
        cursec = cursec % 60;

        curhour+=curmin/60;
        curmin = curmin % 60;

        curhour = curhour % 24;

        if(cursec < 0) {
            cursec+=60;
            curmin--;
        }
        if(curmin < 0){
            curmin+=60;
            curhour--;
        }
        if(curhour < 0){
            curhour+=24;
        }
        String hh = curhour / 10 == 0 ? "0" + curhour : String.valueOf(curhour);
        String mm = curmin / 10 == 0 ? "0" + curmin : String.valueOf(curmin);
        String ss = cursec / 10 == 0 ? "0" + cursec : String.valueOf(cursec);

        return hh + ":" + mm + ":" + ss;
    }
    public boolean isItLater(String dead , String now){
        for(int i =0 ;i < dead.length() ; i++)
            if(now.charAt(i) < dead.charAt(i))
                return false;
            else if(now.charAt(i) > dead.charAt(i))
                return true;
        return true;
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 0){
                Dialog d = (Dialog)msg.obj;
                d.cancel();
            }
            return false;
        }
    });
    public int timeDiff(String x1,String x2){
        Log.d(x1,x2);
        int retval = 0;
        retval+=((Integer.valueOf(x1.substring(0,2)) - Integer.valueOf(x2.substring(0 , 2))) * 3600);
        retval+=((Integer.valueOf(x1.substring(3,5)) - Integer.valueOf(x2.substring(3 , 5))) * 60);
        retval+=(Integer.valueOf(x1.substring(6,8)) - Integer.valueOf(x2.substring(6 , 8)));
        return retval;
    }
    public String getCurrentTimeOnSite(String format){
        if(Initilizer.timeOnSite == null || Initilizer.timeOnPhone == null)
            return getCurrentTimeFromPhone(format);
        return addTimeDiffToTimeString(getCurrentTimeFromPhone(format) , timeDiff(Initilizer.timeOnSite , Initilizer.timeOnPhone));
    }
    public void cleanWrite(String name , String directory , ArrayList<String> data , String lineending){
        try{
            File dir = new File(directory);
            dir.mkdirs();
            dir.mkdir();

            File towrite = new File(directory , name);

            if(towrite.exists())
                towrite.delete();
            towrite.createNewFile();
            FileUtils.writeLines(towrite , data , lineending);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void waitInThread(long waittime){
        if(waittime > 0){
            try{
                Thread.sleep(waittime);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public String getRawUrl(String url){
        int index  = 0;
        for(int i =0 ;i < 3 ; i++){
            if(index != -1)
                index = url.indexOf("/" , index + 1);
            else
                break;
        }
        final String URL;
        if(index != -1)
            URL= url.substring(index);
        else
            URL = url;
        return URL;
    }
    private Message handle(int what,Object obj , int arg1 , int arg2){
        Message x = new Message();
        x.what = what;
        x.obj = obj;
        x.arg1 = arg1;
        x.arg2 = arg2;
        handler.sendMessage(x);
        return x;
    }
}
