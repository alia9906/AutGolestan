package ir.ateck.autogolestan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class a extends Activity implements View.OnClickListener {
    private String a0;
    private Utilities a1;
    private e a2;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //f0();
        startActivity(new Intent(a.this , ac.class));
    }

    @Override
    public void onClick(View v) {
        a2.f1();
        switch (v.getId()){
            case C.CA0.IDS.c0:
                if (!a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c0)) {
                    if(a1.isNetWorkConnectionAvailable())
                        startActivity(new Intent(this, d.class));
                    else
                        a2.f0(getString(R.string.connecttonet));
                }
                else
                    new e(a.this).f0(getResources().getString(R.string.putinidfirst));
                break;
            case C.CA0.IDS.c1:
                if(a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c0))
                    new e(a.this).f0(getResources().getString(R.string.putinidfirst));
                else if(a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c1))
                    new e(a.this).f0(getResources().getString(R.string.putin212first));
                else
                    startActivity(new Intent(this,f.class));
                break;
            case C.CA0.IDS.c2:
                if(a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c0))
                    new e(a.this).f0(getResources().getString(R.string.putinidfirst));
                else if(a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c1))
                    new e(a.this).f0(getResources().getString(R.string.putin212first));
                else if(a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c2))
                    new e(a.this).f0(getResources().getString(R.string.putinbarnamefirst));
                else
                    startActivity(new Intent(a.this , g.class));
                break;
            case C.CA0.IDS.c3:
                startActivity(new Intent(this,h.class));
                break;
            case C.CA0.IDS.c4:
                startActivity(new Intent(this,i.class));
                break;
                default:
                    return;
        }
    }

    private void f0(){
        setContentView(C.CA0.LAYOUTS.ca0);

        final SharedPreferences t0 = getSharedPreferences(C.CSHAREDPREFS0.c0,MODE_PRIVATE);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!b.f0(a.this));
                if(t0.getString(C.CSHAREDPREFS0.PROGRESS.c0,C.CSHAREDPREFS0.PROGRESS.STAGES.c0).equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c0))
                    startActivity(new Intent(a.this,h.class));
            }
        }).start();


        a0 = t0.getString(C.CSHAREDPREFS0.PROGRESS.c0 , C.CSHAREDPREFS0.PROGRESS.STAGES.c0);


        b.b0 = t0.getString(C.CSHAREDPREFS0.PROGRESS.c2,null);

        findViewById(C.CA0.IDS.c0).setOnClickListener(this);
        findViewById(C.CA0.IDS.c1).setOnClickListener(this);
        findViewById(C.CA0.IDS.c2).setOnClickListener(this);
        findViewById(C.CA0.IDS.c3).setOnClickListener(this);
        findViewById(C.CA0.IDS.c4).setOnClickListener(this);

        legalize(a0);

        a1 = new Utilities(a.this);
        a2 = new e(a.this);
    }
    /*
    @Override
    protected void onResume() {
        SharedPreferences t0 = getSharedPreferences(C.CSHAREDPREFS0.c0,MODE_PRIVATE);
        a0 = t0.getString(C.CSHAREDPREFS0.PROGRESS.c0 , C.CSHAREDPREFS0.PROGRESS.STAGES.c0);

        legalize(a0);

        super.onResume();
    }
    */


    private void legalize(String a0){
        if(a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c0)){
            findViewById(C.CA0.IDS.c0).setAlpha(C.COLORS.ALPHA.c0);
            findViewById(C.CA0.IDS.c1).setAlpha(C.COLORS.ALPHA.c0);
            findViewById(C.CA0.IDS.c2).setAlpha(C.COLORS.ALPHA.c0);
        }else if(a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c1)){
            findViewById(C.CA0.IDS.c0).setAlpha(1.0f);
            findViewById(C.CA0.IDS.c1).setAlpha(C.COLORS.ALPHA.c0);
            findViewById(C.CA0.IDS.c2).setAlpha(C.COLORS.ALPHA.c0);
        }else if(a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c2)){
            findViewById(C.CA0.IDS.c0).setAlpha(1.0f);
            findViewById(C.CA0.IDS.c1).setAlpha(1.0f);
            findViewById(C.CA0.IDS.c2).setAlpha(C.COLORS.ALPHA.c0);
        }else if(a0.equals(C.CSHAREDPREFS0.PROGRESS.STAGES.c3)){
            findViewById(C.CA0.IDS.c0).setAlpha(1.0f);
            findViewById(C.CA0.IDS.c1).setAlpha(1.0f);
            findViewById(C.CA0.IDS.c2).setAlpha(1.0f);
        }
    }
}
