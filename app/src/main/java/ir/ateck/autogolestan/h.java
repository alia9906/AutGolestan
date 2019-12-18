package ir.ateck.autogolestan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

import saman.zamani.persiandate.PersianDate;

public class h extends Activity{

    private EditText h0;
    private TextView h1;
    private EditText h3;
    private String[] h2;
    private Utilities h12;
    private Thread h4;
    private boolean h5 = false;
    private t h6;
    private boolean h7 = false;
    private Dialog h8;
    private Spinner h10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        f0();
    }
    private void f0(){
        setContentView(C.CH0.LAYOUTS.c0);

        h0 = findViewById(C.CH0.IDS.c0);
        h1 = findViewById(C.CH0.IDS.c1);
        h3 = findViewById(C.CH0.IDS.c2);
        h10 = findViewById(C.CH0.IDS.c3);

        h2 = new String[8];
        h12 = new Utilities(h.this);

        ArrayList<String> sexes = new ArrayList<>();
        sexes.add(C.CSHAREDPREFS0.SEXSTRINGS.MALETOSHOW);
        sexes.add(C.CSHAREDPREFS0.SEXSTRINGS.FEMALETOSHOW);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sexes);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        h10.setAdapter(ad);
        h10.setBackgroundColor(0xffffffff);
        h10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String show = parent.getItemAtPosition(position).toString();
                if(show.equals(C.CSHAREDPREFS0.SEXSTRINGS.MALETOSHOW))
                    h2[7] = C.CSHAREDPREFS0.SEXSTRINGS.MALE;
                else
                    h2[7] = C.CSHAREDPREFS0.SEXSTRINGS.FEMALE;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        h10.setSelection(0);

        h4 =  new Thread(new Runnable() {
            @Override
            public void run() {
                try { Thread.sleep(C.CTHREADS0.WHILETOAST.c1); }catch (Exception e){e.printStackTrace();}
                while (true){
                    if(!h12.isNetWorkConnectionAvailable()){
                        h5 = false;
                        handle(0);

                        while (!h12.isNetWorkConnectionAvailable());

                        handle(1);

                        h5 = true;
                    }
                    h5 = true;
                }
            }
        });
        h4.start();

        SharedPreferences t2 = getSharedPreferences(C.CSHAREDPREFS0.c0,MODE_PRIVATE);
        h2[0] = t2.getString(C.CSHAREDPREFS0.IDENTITIES.c0,null);
        for(int i = 1 ; i < 6;i++){
            h2[i] = t2.getString(f1(i),null);
        }
        h2[6] = t2.getString(C.CSHAREDPREFS0.IDENTITIES.c6 , null);
        if(h2[0] != null)
            h0.setText(h2[0]);
        String t0 = "";
        for(int i = 1 ; i < 6 ; i++){
            if(h2[i] != null) {
                if (i < 3)
                    t0 += (h2[i] + "/");
                else if (i == 4)
                    t0 += ("  " + h2[i] + ":");
                else
                    t0 += h2[i];
            }
        }
        if(t0.length() > 0)
            h1.setText(t0);
        if(h2[6] != null)
            h3.setText(h2[6]);


        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer[] t1 = new Integer[5];
                for(int i =0 ;i <5;i++)
                    if(h2[i+1] != null)
                        t1[i] = Integer.valueOf(h2[i+1]);
                final l t0 = new l(h.this,t1);
                t0.show();
                t0.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if(t0.f1()!= null){
                            String t000 = "";
                            for(int i = 1; i < 6;i++) {
                                h2[i] = String.valueOf(t0.f1()[i - 1]);
                                if(h2[i].length() < 2)
                                    h2[i] = "0" + h2[i];
                                if(h2[i] != null) {
                                    if (i < 3)
                                        t000 += (h2[i] + "/");
                                    else if (i == 4)
                                        t000 += ("  " + h2[i] + ":");
                                    else
                                        t000 += h2[i];
                                }
                            }
                            h1.setText(t000);
                        }
                    }
                });
            }
        });

        findViewById(C.CH0.IDS.c4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APPLY();
            }
        });

    }

    private void APPLY(){
        if(h8!=null)
            return;
        h8 = new Dialog(this);
        h8.requestWindowFeature(Window.FEATURE_NO_TITLE);
        h8.setContentView(R.layout.progress);
        h8.setCancelable(false);
        h8.setCanceledOnTouchOutside(false);
        h8.show();
        h2[0] = h0.getText().toString();
        h2[6] = h3.getText().toString();
        final RotateAnimation a = f9();
        h8.findViewById(R.id.progress_image_view).startAnimation(a);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{Thread.sleep(100);}catch (Exception e){}
                Message x;
                switch (f2()){
                    case 0:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handle(2,h0,C.COLORS.c3);
                                handle(5,getString(R.string.sidinvalid));
                                try{ Thread.sleep(C.CTHREADS0.WHILEERRORING.c0);}catch (Exception e){e.printStackTrace();}
                                handle(2,h0,C.COLORS.c0);
                            }
                        }).start();
                        handle(3);
                        break;
                    case 1:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handle(2,h1,C.COLORS.c3);
                                handle(5,getString(R.string.dateinvalid));
                                try{ Thread.sleep(C.CTHREADS0.WHILEERRORING.c0);}catch (Exception e){e.printStackTrace();}
                               handle(2,h1,C.COLORS.c0);
                            }
                        }).start();
                       handle(3);
                        break;
                    case 2:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handle(2,h3,C.COLORS.c3);
                                handle(5,getString(R.string.urlinvalid));
                                try{ Thread.sleep(C.CTHREADS0.WHILEERRORING.c0);}catch (Exception e){e.printStackTrace();}
                                handle(2,h3,C.COLORS.c0);
                            }
                        }).start();
                        handle(3);
                        break;
                    case -1:
                        f6();
                        handle(3);
                        handle(4);
                        break;
                }
            }
        }).start();
    }

    private String f1(int r0){
        if(r0 == 0)
            return C.CSHAREDPREFS0.IDENTITIES.c0;
        if(r0 == 1)
            return C.CSHAREDPREFS0.IDENTITIES.c1;
        if(r0 == 2)
            return C.CSHAREDPREFS0.IDENTITIES.c2;
        if(r0 == 3)
            return C.CSHAREDPREFS0.IDENTITIES.c3;
        if(r0 == 4)
            return C.CSHAREDPREFS0.IDENTITIES.c4;
        if(r0 == 5)
            return C.CSHAREDPREFS0.IDENTITIES.c5;
        if(r0 == 6)
            return C.CSHAREDPREFS0.IDENTITIES.c6;
        if(r0 == 7)
            return C.CSHAREDPREFS0.IDENTITIES.c8;
        return null;
    }
    private int f2(){
        if(h2[0] == null || "".equals(h2[0]))
            return 0;
        SharedPreferences tx = getSharedPreferences(C.CSHAREDPREFS0.c0 , MODE_PRIVATE);
        for(int i = 1; i < 6;i++)
            if(h2[i] == null || "".equals(h2[i]))
                return 1;
        if(!f3())
            return 1;
        if(h2[6] == null || "".equals(h2[6]))
            return 2;
        if(!h2[6].equals(tx.getString(C.CSHAREDPREFS0.IDENTITIES.c6,"poop")))
            if(!f4(h2[6]))
                return 2;

        return -1;
    }
    private boolean f3(){
        if(Integer.valueOf(h2[1]) < 25 && Integer.valueOf(h2[1]) > 19){
            String t0 = (new SimpleDateFormat("yy/MM/dd HH:mm")).format(Calendar.getInstance().getTime());
            String t1 = "";
            for(int i = 1 ; i < 6 ; i++){
                if(h2[i] != null) {
                    if (i < 3)
                        t1 += (h2[i] + "/");
                    else if (i == 4)
                        t1 += ("  " + h2[i] + ":");
                    else
                        t1 += h2[i];
                }
            }
            j jx = new j();
            t1 = jx.f2(t1);
            t0 = jx.f2(t0);
            if(f5(t1 ,t0))
                return true;
            return false;
        }else{
            String t0,t1;
            t0 = (new SimpleDateFormat("yy/MM/dd HH:mm")).format(Calendar.getInstance().getTime());
            t1 = "";
            PersianDate p = new PersianDate();
            int yadd = 0;
            if(Integer.valueOf(h2[1]) >= 98)
                yadd = 1300;
            else
                yadd = 1400;
            int[] t10 = p.toGregorian(yadd+ Integer.valueOf(h2[1]),Integer.valueOf(h2[2]),Integer.valueOf(h2[3]));
            t1+=(String.valueOf(t10[0]).substring(2) + "/");
            t1+=((String.valueOf(t10[1]).length() < 2 ? "0" +String.valueOf(t10[1]) : String.valueOf(t10[1])) + "/");
            t1+=((String.valueOf(t10[2]).length() < 2 ? "0" +String.valueOf(t10[2]) : String.valueOf(t10[2])) + " " + h2[4] + ":" + h2[5]);

            j jx = new j();
            t1 = jx.f2(t1);
            t0 = jx.f2(t0);
            if(f5(t1,t0))
                return true;
            return false;
        }
    }
    private boolean f4(String r0){
        while (!h5);
        if(r0.contains("http://"))
            r0.replace("http://" , "");
        if(!r0.contains("https://"))
            r0 = ("https://" + r0);
        r0 = r0.replace(" " , "");
        r0 = r0.replace("\n" , "");
        r0.replace("\r" , "");
        if("".equals(r0))
            return false;
        final String t3 = r0 + C.CURL0.AUTHPAGE.c0;
        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpsURLConnection con = (HttpsURLConnection) new URL(t3).openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                    h7 = (con.getResponseCode() == HttpsURLConnection.HTTP_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    h7 = false;
                }
            }
        });
        t0.start();
        try{ t0.join();}catch (Exception e){e.printStackTrace();}
        return h7;
    }
    private boolean f5(String a,String b){
        for(int i= 0 ;i < a.length() ;i++)
            if(a.charAt(i) < b.charAt(i))
                return false;
            else if(a.charAt(i) > b.charAt(i))
                return true;
        return false;
    }


    private void f6(){
        SharedPreferences t0 = getSharedPreferences(C.CSHAREDPREFS0.c0 , MODE_PRIVATE);
        SharedPreferences.Editor t1 = t0.edit();
        if(!t0.getString(C.CSHAREDPREFS0.IDENTITIES.c0,"kill").equals(h2[0]) || !t0.getString(C.CSHAREDPREFS0.IDENTITIES.c6,"kill").equals(h2[6]))
            t1.putString(C.CSHAREDPREFS0.PROGRESS.c0 , C.CSHAREDPREFS0.PROGRESS.STAGES.c1);
        if(h2[6].contains("http://"))
            h2[6] = h2[6].replace("http://" , "");
        if(!h2[6].contains("https://"))
            h2[6] = ("https://" + h2[6]);
        h2[6] = h2[6].replace("\n" , "");
        h2[6] = h2[6].replace("\r" , "");
        h2[6] = h2[6].replace(" ", "");
        for(int i = 0 ; i < h2.length ; i++)
            t1.putString(f1(i),h2[i]);
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        t1.commit();
    }
    private Handler h9 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what == 0)
                h6 = new t(h.this,getString(R.string.connecttonet));
            else if(message.what == 1){
                if(h6!=null)
                h6.setCancelable(true);
                h6.cancel();
                h6 = null;
            }else if(message.what == 2)
                ((TextView)message.obj).setTextColor(message.arg1);
            else if(message.what == 3) {
                if(h8!=null)
                h8.cancel();
                h8 = null;
            }else if(message.what == 4){
                h.super.onBackPressed();
            }else if(message.what == 5){
                Toast.makeText(h.this,(String) message.obj , (int)C.CTHREADS0.WHILEERRORING.c0).show();
            }
            return false;
        }
    });
    private RotateAnimation f9(){
        RotateAnimation t0 = new RotateAnimation(0.0f,1080.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        t0.setDuration(1500);t0.setInterpolator(new FastOutSlowInInterpolator());
        t0.setRepeatCount(Animation.INFINITE);
        return t0;
    }


    private void handle(int what,Object obj,int ar1){
        Message toh = new Message();
        toh.what = what;
        toh.obj = obj;
        toh.arg1 = ar1;
        h9.sendMessage(toh);
    }
    private void handle(int what,Object obj){
        Message toh = new Message();
        toh.what = what;
        toh.obj = obj;
        h9.sendMessage(toh);
    }
    private void handle(int what){
        Message toh = new Message();
        toh.what = what;
        h9.sendMessage(toh);
    }
}
