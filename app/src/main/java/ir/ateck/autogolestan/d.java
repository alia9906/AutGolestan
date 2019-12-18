package ir.ateck.autogolestan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class d extends Activity {
    private Dialog d;
    private WebView d0 = null;
    private boolean d1 = false;
    private boolean d2 = false;
    private boolean d3 = false;
    private boolean d4 = false;
    private boolean d11 = true;
    private boolean d13 = false;
    private boolean d20  = false;
    private float d6  = -1f,d7 = -1f;
    private int d9 = -1;
    private String d10 = null;
    private String d12;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        f0();
    }

    private void f0(){
        setContentView(C.CD0.LAYOUT.c0);

        d0 = findViewById(C.CD0.IDS.c0);

        WebSettings t0 = d0.getSettings();
        t0.setJavaScriptEnabled(true);
        t0.setLoadWithOverviewMode(true);
        t0.setUseWideViewPort(true);
        t0.setJavaScriptCanOpenWindowsAutomatically(true);
        t0.setDomStorageEnabled(true);
        t0.setSupportZoom(false);

        d0.setWebViewClient(new dc1());

        SharedPreferences t4 = getSharedPreferences(C.CSHAREDPREFS0.c0 , MODE_PRIVATE);
        d12 = t4.getString(C.CSHAREDPREFS0.IDENTITIES.c6 , C.CURL0.DOMAIN.c0);
        d0.loadUrl(d12 + C.CURL0.AUTHPAGE.c0);

        d0.setOnTouchListener(d5);

        float[] t3 = new k(this).f0();
        d6 = t3[0];
        d7 = t3[1];
    }
    private class dc1 extends WebViewClient{
        @Override
        public void onLoadResource(WebView view, final String url) {
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

            if(URL.equals(C.CURL0.LOADING.c2))
                d20 = true;

            if(URL.equals(C.CURL0.LOADING.c0)) {
                d1 = true;
                d2 = true;
                while(d0.zoomOut());
            }
            if(d1) {
                if (URL.equals(C.CURL0.LOADING.c1)) {
                        final Thread t1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    handle(6,null,0,0);
                                    Thread.sleep(C.CTHREADS0.WHILELOADING.c1);
                                    handle(5,null,0,0);
                                    Thread.sleep(C.CTHREADS0.WHILELOADING.c0);
                                    j t1 = new j();
                                    while (!d13) ;
                                    String t2 = b.f3(C.FILES.DIRECTORY.c0 + "/" + C.FILES.NAMES.c0 + C.FILES.NAMES.c1, C.CURL0.ELEMENTIDS.c1, C.CURL0.ELEMENTIDS.c6);
                                    t2 = t1.f2(t1.f0(t2, C.CURL0.ELEMENTIDS.c4));
                                    d9 = Integer.valueOf(t2);

                                    t2 = b.f3(C.FILES.DIRECTORY.c0 + "/" + C.FILES.NAMES.c0 + C.FILES.NAMES.c1, C.CURL0.ELEMENTIDS.c0, C.CURL0.ELEMENTIDS.c7);
                                    t2 = t1.f2(t1.f0(t2, C.CURL0.ELEMENTIDS.c5));
                                    d10 = t2;

                                    b.f7(C.FILES.DIRECTORY.c0 + "/" + C.FILES.NAMES.c0 + C.FILES.NAMES.c1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        t1.start();
                        final Thread t0 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (d9 == -1 || d10 == null) ;
                                d3 = true;
                                for (int t00 = 0; t00 < d9; t00++) {
                                    handle(1,new Integer(t00) , 0 ,0);

                                    try {
                                        Thread.sleep(C.CTHREADS0.WHILELOADING.c0);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    while (!d13) ;
                                    if(t00 != d9 -1) {
                                        handle(0,null,0,0);

                                        try {
                                            Thread.sleep(C.CTHREADS0.WHILELOADING.c0);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        while (!d20) {
                                            handle(0,null,0,0);

                                            try {
                                                Thread.sleep(C.CTHREADS0.WHILELOADING.c0);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        });
                        t0.start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (t0.isAlive()) ;
                                d3 = false;
                                SharedPreferences t01 = getSharedPreferences(C.CSHAREDPREFS0.c0, MODE_PRIVATE);
                                SharedPreferences.Editor t02 = t01.edit();
                                if (!t01.getString(C.CSHAREDPREFS0.IDENTITIES.c0, "kill").equals(d10))
                                    d11 = false;
                                if (!d4) {
                                    if (!d11) {
                                        Message y = new Message();
                                        y.what = 7;
                                        d8.sendMessage(y);
                                        b.f6(false);
                                        b.b1 = null;
                                        Message x = new Message();
                                        x.what = 3;
                                        d8.sendMessage(x);
                                        long t08 = C.CTHREADS0.WHILETOAST.c0 * getString(R.string.mismatchidnum).length() + 750;
                                        try {
                                            Thread.sleep(t08);
                                        } catch (Exception e) {
                                        }
                                        finish();
                                        return;
                                    }
                                    b.b0 = b.b1;
                                    b.UPDATE(d.this);
                                    b.b1 = null;

                                    m.sexToSave = t01.getString(C.CSHAREDPREFS0.IDENTITIES.c8,C.CSHAREDPREFS0.SEXSTRINGS.FEMALE);
                                    m.masterContext = d.this;
                                    m t90 = new m();
                                    t90.execute(b.f5(true));
                                    while (t90.getStatus().equals(AsyncTask.Status.RUNNING)) ;

                                    handle(7,null,0,0);

                                    handle(2,null,0,0);
                                    long t08 = C.CTHREADS0.WHILETOAST.c0 * getString(R.string.saved).length() + 750;
                                    try {
                                        Thread.sleep(t08);
                                    } catch (Exception e) {
                                    }
                                    if (!t01.getString(C.CSHAREDPREFS0.PROGRESS.c0, "kill").equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c2) &&
                                            !t01.getString(C.CSHAREDPREFS0.PROGRESS.c0, "kill").equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c3)) {
                                        t02.putString(C.CSHAREDPREFS0.PROGRESS.c0, C.CSHAREDPREFS0.PROGRESS.STAGES.c2);
                                        t02.commit();
                                    }
                                    finish();
                                } else {
                                    handle(7,null,0,0);
                                    b.f6(false);
                                    b.b1 = null;
                                    handle(4,null,0,0);
                                    long t08 = C.CTHREADS0.WHILETOAST.c0 * getString(R.string.curruptedsavingprocess).length() + 750;
                                    try {
                                        Thread.sleep(t08);
                                    } catch (Exception e) {
                                    }
                                    d.this.recreate();
                                    return;
                                }
                            }
                        }).start();
                    }

                }
            super.onLoadResource(view, url);
        }
    }
    private View.OnTouchListener d5 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(d1)
                if(d2)
                    return true;
            return v.onTouchEvent(event);
        }
    };
    private Handler d8 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    long downTime = SystemClock.uptimeMillis();
                    long eventTime = SystemClock.uptimeMillis() + C.CTOUCHINPUT0.WAITTIME.c0;

                    MotionEvent m0 = MotionEvent.obtain(downTime,eventTime,MotionEvent.ACTION_DOWN,d6,d7,0);
                    d2 = false;
                    d0.dispatchTouchEvent(m0);
                    downTime = SystemClock.uptimeMillis();
                    eventTime = SystemClock.uptimeMillis() + C.CTOUCHINPUT0.WAITTIME.c0;
                    m0 = MotionEvent.obtain(downTime,eventTime,MotionEvent.ACTION_UP,d6,d7,0);
                    d0.dispatchTouchEvent(m0);
                    d2 = true;
                    break;
                case 1:
                    d13 = false;
                    if((Integer)msg.obj == 0){
                        b.b1 = C.FILES.NAMES.c0 + b.f2() + "0";
                    }
                    d0.saveWebArchive(b.f1(C.FILES.DIRECTORY.c0,C.FILES.NAMES.c0 + b.f2() + String.valueOf(msg.obj)).getAbsolutePath(),false,d14);
                    break;
                case 2:
                    new e(d.this).f0(getResources().getString(R.string.saved));
                    break;
                case 3:
                    new e(d.this).f0(getString(R.string.mismatchidnum));
                    break;
                case 4:
                    new e(d.this).f0(getResources().getString(R.string.curruptedsavingprocess));
                    break;
                case 5:
                    d13 = false;
                    d0.saveWebArchive(b.f1(C.FILES.DIRECTORY.c0,C.FILES.NAMES.c0 + C.FILES.NAMES.c1).getAbsolutePath(),false,d14);
                    break;
                case 6:
                    d = new Dialog(d.this);
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    d.setCancelable(false);
                    d.setCanceledOnTouchOutside(false);
                    d.setContentView(R.layout.progress);
                    RotateAnimation t0 = new RotateAnimation(0.0f,1080.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    t0.setDuration(1500);t0.setInterpolator(new FastOutSlowInInterpolator());
                    t0.setRepeatCount(Animation.INFINITE);
                    d.show();
                    d.findViewById(R.id.progress_image_view).startAnimation(t0);
                    break;
                case 7:
                    d.setCancelable(true);
                    d.cancel();
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onUserLeaveHint() {
        if(d3)
            d4 = true;
        super.onUserLeaveHint();
    }

    @Override
    protected void onPause() {
        if(d3)
            d4 = true;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(d3)
            d4 = true;
        super.onDestroy();
    }
    private ValueCallback<String> d14 = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String s) {
            if(s!= null)
                d13 = true;
            else
                d13 = false;
        }
    };
    private void handle(int what , Object obj , int ar1  , int arg2){
        Message x = new Message();
        x.what = what;
        x.obj = obj;
        x.arg1 = ar1;
        x.arg2 = arg2;
        d8.sendMessage(x);
    }
}
