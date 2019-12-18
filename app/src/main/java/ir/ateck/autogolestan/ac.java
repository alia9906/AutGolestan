package ir.ateck.autogolestan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ac extends Activity {

    private ArrayList<ab> main_views = new ArrayList<>();
    private ArrayList<Pixel> infopixels;
    private Initilizer initilizer;
    private int current_index = -1;
    private Utilities utils;
    private final String timeformat = "HH:mm:ss";
    private String deadTime = "23:59:59";
    private TaskChecker checker = new TaskChecker();
    private k toucher;
    private boolean isStayingOnMainPage = false;
    private static final boolean isdev = true;
    private String portalurl;
    private FrameLayout root;

    boolean[] reachedtodopage = new boolean[C.CINITILIZER.c5];
    boolean[] isTouchAllowed = new boolean[C.CINITILIZER.c5];
    boolean[] reachedmain = new boolean[C.CINITILIZER.c5];
    boolean[] reachedinfopage = new boolean[C.CINITILIZER.c5];
    boolean[] isStuck = new boolean[C.CINITILIZER.c5];
    long[] mainTime = new long[C.CINITILIZER.c5];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initilizeContent();

    }

    private void initilizeContent(){

        toucher = new k(this);
        infopixels = toucher.getInfoPagePixelAuth();
        utils = new Utilities(this);
        String domain = utils.getStringSetting(C.CSHAREDPREFS0.IDENTITIES.c6);
        if(domain == null)
            domain = "https://portal.sru.ac.ir";
        portalurl = domain + C.CURL0.AUTHPAGE.c0;

        initilizer = new Initilizer();
        initilizer._init_(this , portalurl);

        utils.showProgressBar(checker , getString(R.string.initilizingthedoing) , true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Initilizer.timeOnPhone == null || Initilizer.timeOnSite == null || Initilizer.responseTime == -1l);
                handle(1,null,0,0);
                handle(0,portalurl,0,0);
                checker.taskDone();
            }
        }).start();
    }

    private class CONTROLLERCLIENT extends WebViewClient {
        @Override
        public void onLoadResource(final WebView view, String url) {

            String URL = utils.getRawUrl(url);

            if(URL.equals(C.CURL0.LOADING.c4))
                reachedtodopage[utils.findIndexById(view.getId() , main_views)] = true;
            if(URL.equals(C.CURL0.LOADING.c3)) {
                reachedmain[utils.findIndexById(view.getId() , main_views)] = true;
                if(isdev)
                    isTouchAllowed[utils.findIndexById(view.getId() , main_views)] = false;
                else
                    isTouchAllowed[utils.findIndexById(view.getId() , main_views)] = false;
                while (view.zoomOut());
                mainTime[utils.findIndexById(view.getId() , main_views)] = SystemClock.uptimeMillis();
                stayOnMainPage();
            }
            if(URL.contains(C.CURL0.LOADING.c5)){
                reachedinfopage[utils.findIndexById(view.getId(),main_views)] = true;
            }
            if(URL.equals(C.CURL0.LOADING.c6)){
                reachedinfopage[utils.findIndexById(view.getId(),main_views)] = false;
            }
            super.onLoadResource(view, url);
        }
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(!isTouchAllowed[utils.findIndexById(v.getId() , main_views)])
                return true;
            return v.onTouchEvent(event);
        }
    };

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 0){
                for(int i =0; i< 1;i++)
                main_views.get(i).loadUrl((String)msg.obj);
                comeToFront(0);
                return true;
            }
            if(msg.what == 1){
                root = new FrameLayout(ac.this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT , FrameLayout.LayoutParams.MATCH_PARENT);

                for(int i =0 ; i < C.CINITILIZER.c5 ; i++){
                    ab temp = new ab(ac.this);
                    utils.initilizeWebView(temp);
                    temp.setWebViewClient(new CONTROLLERCLIENT());
                    temp.setOnTouchListener(onTouchListener);
                    current_index = 0;
                    root.addView(temp , params);
                    reachedtodopage[i] = false;
                    reachedmain[i] = false;
                    isTouchAllowed[i] = true;
                    reachedinfopage[i] = false;
                    isStuck[i] = false;
                    mainTime[i] = -1l;
                    main_views.add(temp);
                }
                setContentView(root);
                return true;
            }
            if(msg.what == 2){
                while (main_views.get(msg.arg1).zoomOut());
                isTouchAllowed[msg.arg1] = true;
                boolean retval = main_views.get(msg.arg1).dispatchTouchEvent((MotionEvent)msg.obj);
                if(!isdev)
                    isTouchAllowed[msg.arg1] = false;
                return retval;
            }
            if(msg.what == 10){
                main_views.get(msg.arg1).reload();
                mainTime[msg.arg1] = SystemClock.uptimeMillis();
                return true;
            }
            if(msg.what == 11){
                root.removeAllViews();

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT , FrameLayout.LayoutParams.MATCH_PARENT);

                for(int j =0 ; j < main_views.size() ; j++)
                    if(j != msg.arg1)
                        root.addView(main_views.get(j) , params);

                root.addView(main_views.get(msg.arg1) , params);
                return true;
            }
            return false;
        }
    });

    private boolean handle(int what,Object obj , int arg1 , int arg2){
        Message x = new Message();
        x.what = what;
        x.obj = obj ; x.arg2 = arg2 ;x.arg1 = arg1;
        return handler.sendMessage(x);
    }

    private void stayOnMainPage(){
        if(!isStayingOnMainPage)
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<MotionEvent> gotoinfopage = toucher.createStayOnMainPageDirection();
                ArrayList<MotionEvent> comeback = toucher.comeBackToMainPage();
                isStayingOnMainPage = true;
                while (true){
                    outer : for(int i =0; i < main_views.size() ; i++){
                        if(i != current_index){
                            if(reachedmain[i] && !isStuck[i]){
                                if(SystemClock.uptimeMillis() - mainTime[i] >= C.CINITILIZERCX) {
                                    comeToFront(i);
                                    long timestarted = SystemClock.uptimeMillis();
                                    for (int j = 0; j < gotoinfopage.size(); j++) {
                                        if (j == 2) {
                                            utils.waitInThread(C.CINITILIZER.c8);
                                            handle(2, gotoinfopage.get(j), i, 0);
                                        } else {
                                            handle(2, gotoinfopage.get(j), i, 0);
                                        }
                                    }
                                    utils.waitInThread(C.CG0.CHECKING.c0);
                                    while (!main_views.get(i).hasReachedState(infopixels, false)) {
                                        utils.waitInThread(C.CTHREADS0.WHILELOADING.c1);
                                        if(SystemClock.uptimeMillis() - timestarted > C.CINITILIZERCX) {
                                            isStuck[i] = true;
                                            continue outer;
                                        }
                                    }
                                    for (int j = 0; j < comeback.size(); j++)
                                        handle(2, comeback.get(j), i, 0);
                                    mainTime[i] = SystemClock.uptimeMillis();
                                    comeToFront(current_index);
                                }
                            }else if(!reachedmain[i]){
                                if(mainTime[i] != -1l)
                                    if(SystemClock.uptimeMillis() - mainTime[i] >= C.CINITILIZERCX)
                                        handle(10,null,i,0);
                            }
                        }else{
                            if(reachedmain[current_index] && !isStuck[current_index]){
                                if(utils.timeDiff(deadTime , utils.getCurrentTimeOnSite(timeformat)) > 120){
                                    if(SystemClock.uptimeMillis() - mainTime[i] >= C.CINITILIZERCX) {
                                        long timestarted = SystemClock.uptimeMillis();
                                        for (int j = 0; j < gotoinfopage.size(); j++) {
                                            if (j == 2) {
                                                utils.waitInThread(C.CINITILIZER.c8);
                                                handle(2, gotoinfopage.get(j), i, 0);
                                            } else {
                                                handle(2, gotoinfopage.get(j), i, 0);
                                            }
                                        }
                                        utils.waitInThread(C.CG0.CHECKING.c0);
                                        while (!main_views.get(i).hasReachedState(infopixels, false)) {
                                            utils.waitInThread(C.CTHREADS0.WHILELOADING.c1);
                                            if(SystemClock.uptimeMillis() - timestarted > C.CINITILIZERCX) {
                                                isStuck[i] = true;
                                                continue outer;
                                            }
                                        }
                                        for (int j = 0; j < comeback.size(); j++)
                                            handle(2, comeback.get(j), i, 0);
                                        mainTime[i] = SystemClock.uptimeMillis();
                                    }
                                }
                            }else if(!reachedmain[i]){
                                if(mainTime[i] != -1l)
                                    if(SystemClock.uptimeMillis() - mainTime[i] >= C.CINITILIZERCX)
                                        handle(10 , null , i , 0);
                            }
                        }
                    }
                    utils.waitInThread(C.CG0.CHECKING.c1);
                }
            }
        }).start();
        if(!isStayingOnMainPage)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        for (int i = 0; i < main_views.size(); i++) {
                            Log.d("index = " + i, "stuck=" + isStuck[i] + " " + "time=" + (SystemClock.uptimeMillis() - mainTime[i]));
                        }
                        utils.waitInThread(C.CG0.CHECKING.c0);
                    }
                }
            }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isdev) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                if(current_index > 0){
                    current_index--;
                    comeToFront(current_index);
                    if(!reachedmain[current_index]) {
                        main_views.get(current_index).loadUrl(portalurl);
                        mainTime[current_index] = SystemClock.uptimeMillis();
                    }
                }
                Toast.makeText(this , current_index + "" , Toast.LENGTH_SHORT).show();
                return true;
            }
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                if(current_index < main_views.size() - 1){
                    current_index++;
                    comeToFront(current_index);
                    if(!reachedmain[current_index]) {
                        main_views.get(current_index).loadUrl(portalurl);
                        mainTime[current_index] = SystemClock.uptimeMillis();
                    }
                }
                Toast.makeText(this , current_index + "" , Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void comeToFront(int i){
        handle(11,null,i,0);
    }
}
