package ir.ateck.autogolestan;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Initilizer {
    private ArrayList<ab> views = new ArrayList<>();
    private ArrayList<Long> starttimes = new ArrayList<>();
    private ArrayList<Long> finishtimes = new ArrayList<>();
    private ArrayList<String> curhtmls = new ArrayList<>();
    private ArrayList<String> readabletimes = new ArrayList<>();
    public static String timeOnSite;
    public static String timeOnPhone;
    public static long responseTime = -1l;

    public void _init_(Context context, final String portalurl){
        for(int i = 0 ; i < C.CINITILIZER.c0 ; i++) {
            views.add(new ab(context));
            views.get(i).getSettings().setJavaScriptEnabled(true);
            views.get(i).getSettings().setDomStorageEnabled(true);
            views.get(i).setWebViewClient(new Client());
            views.get(i).setWebViewClient(new Client());
            starttimes.add(-1l);
            finishtimes.add(-1l);
            curhtmls.add("");
            readabletimes.add("");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0 ;i < views.size() ; i++) {
                    handle(2, portalurl, i, 0);
                    try{
                        Thread.sleep(C.CINITILIZER.c2);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                outer : while (true){
                    boolean tocon = false;
                    inner : for(int i = 0; i < readabletimes.size() ; i++)
                        if("".equals(readabletimes.get(i)) || readabletimes.get(i) == null) {
                            tocon = true;
                            break inner;
                        }

                    if(!tocon)
                        break outer;
                    try{
                        Thread.sleep(C.CINITILIZER.c1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                //create data
                for(int i =0; i < curhtmls.size() ; i++)
                    curhtmls.set(i,getCurrentTimeFromHtml(curhtmls.get(i)));

                //analyze
                int[] diffrences = new int[readabletimes.size()];
                int avg = 0;
                long avgresponse = 0;
                for(int i =0 ;i < readabletimes.size();i++){
                    diffrences[i] = timeDiff(curhtmls.get(i) , readabletimes.get(i));
                    avg+=diffrences[i];
                    avgresponse += (finishtimes.get(i) - starttimes.get(i));
                }
                avg/=diffrences.length;
                avgresponse/=readabletimes.size();

                timeOnPhone = readabletimes.get(0);
                timeOnSite = addDiff(readabletimes.get(0) , avg);
                responseTime = avgresponse;

            }
        }).start();
    }

    private Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(final Message msg) {
            if(msg.what == 0){
                final int arg1 = msg.arg1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final WebView web = views.get(arg1);
                        while (true){
                            try{
                                Thread.sleep(C.CINITILIZER.c1);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            handle(1,null,arg1,0);
                            if(!getCurrentTimeFromHtml(curhtmls.get(findIndexById(web.getId()))).equals("")){
                                readabletimes.set(findIndexById(web.getId()) , getCurrentTimeFromPhone());
                                break;
                            }
                        }
                    }
                }).start();
            }
            if(msg.what == 1){
                final WebView view  = views.get(msg.arg1);
                view.evaluateJavascript("document.getElementsByTagName('html')[0].innerText", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        curhtmls.set(findIndexById(view.getId()) , value);
                    }
                });
            }
            if(msg.what == 2){
                views.get(msg.arg1).loadUrl((String)msg.obj);
            }
            return false;
        }
    });
    private  Message handle(int what,Object obj,int arg1,int arg2){
        Message s = new Message();
        s.what = what;
        s.obj = obj;
        s.arg2 = arg2;
        s.arg1 = arg1;
        handle.sendMessage(s);
        return s;
    }
    private class Client extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            starttimes.set(findIndexById(view.getId()), SystemClock.uptimeMillis());
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            handle(0,null,findIndexById(view.getId()),0);
            finishtimes.set(findIndexById(view.getId()),SystemClock.uptimeMillis());
        }
    }
    private int findIndexById(int id){
        for(int i =0 ; i < views.size() ; i++)
            if(views.get(i).getId() == id)
                return i;
        return -1;
    }
    private String getCurrentTimeFromPhone(){
        return new j().f2(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
    }
    private String getCurrentTimeFromHtml(String html){
        return getFirstNumString(html,':');
    }
    private String getFirstNumString(String main, char ignore){
        String retval = "";
        boolean started = false;

        for(int i = 0; i < main.length() ; i++)
            if(!started){
                if(isNum(main.charAt(i))) {
                    started = true;
                    retval+=main.charAt(i);
                }
            }else{
                if(isNum(main.charAt(i)) || main.charAt(i) == ignore)
                    retval+=main.charAt(i);
                else
                    break;
            }
        return retval;
    }
    private boolean isNum(char x){
        switch (x) {
            case '0':
                return true;
            case '1':
                return true;
            case '2':
                return true;
            case '3':
                return true;
            case '4':
                return true;
            case '5':
                return true;
            case '6':
                return true;
            case '7':
                return true;
            case '8':
                return true;
            case '9':
                return true;
            default:
                return false;
        }
    }
    private int timeDiff(String x1,String x2){
        int retval = 0;
        retval+=((Integer.valueOf(x1.substring(0,2)) - Integer.valueOf(x2.substring(0 , 2))) * 3600);
        retval+=((Integer.valueOf(x1.substring(3,5)) - Integer.valueOf(x2.substring(3 , 5))) * 60);
        retval+=(Integer.valueOf(x1.substring(6,8)) - Integer.valueOf(x2.substring(6 , 8)));
        return retval;
    }
    private String addDiff(String main , int diff){

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

        String hh = curhour / 10 == 0 ? "0" + curhour : String.valueOf(curhour);
        String mm = curmin / 10 == 0 ? "0" + curmin : String.valueOf(curmin);
        String ss = cursec / 10 == 0 ? "0" + cursec : String.valueOf(cursec);

        return hh + ":" + mm + ":" + ss;
    }

    public void clear(){
        views.clear();
        starttimes.clear();
        finishtimes.clear();
        curhtmls.clear();
        readabletimes.clear();
    }
}
