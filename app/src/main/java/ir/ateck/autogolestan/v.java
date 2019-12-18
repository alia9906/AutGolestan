package ir.ateck.autogolestan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class v extends Activity {
    ArrayList<p> r0 = new ArrayList<>();
    ListView r2;
    ArrayList<ArrayList<String>> t04;
    public static ArrayList<ArrayList<String>> tableplan;
    float minostads = 10f;
    float maxostads = 0f;
    float mintimings = 1000f;
    float maxtimings = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        f0();
    }
    private void f0(){
        setContentView(C.CR0.LAYOUT.c0);

        final Dialog t0 = new Dialog(this);
        try {
            findViewById(C.CR0.IDS.c6).setOnClickListener(r5);
            findViewById(C.CR0.IDS.c7).setOnClickListener(r5);
            t0.requestWindowFeature(Window.FEATURE_NO_TITLE);
            t0.setContentView(R.layout.progress);
            RotateAnimation t1 = new RotateAnimation(0.0f, 1080f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            t1.setDuration(1500);
            t1.setRepeatCount(Animation.INFINITE);
            t0.setCancelable(false);
            t0.setCanceledOnTouchOutside(false);
            t0.findViewById(R.id.progress_image_view).startAnimation(t1);
        }catch (Exception e){
            new e(v.this).f0(e.getCause().toString());
        }
        final s y = new s(C.CSQ0.NAMES.c0);
        try{
            t04 = (ArrayList<ArrayList<String>>) y.readArray(C.CSQ0.NAMES.c5);
            if(tableplan == null) {
                    throw new Exception();
            }else{
                r0 = new ArrayList<>();
                for (int i = 0; i < tableplan.size(); i++) {
                    r0.add(new p(tableplan.get(i)));
                }
                Message fill = new Message();
                fill.what = 1;
                r1.sendMessage(fill);
            }
        }catch (Exception e) {
            e.printStackTrace();
            t0.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Dialog t00 = t0;
                        //
                        ArrayList<ArrayList<String>> t01 = (ArrayList<ArrayList<String>>) y.readArray(C.CSQ0.NAMES.c2);
                        ArrayList<String> t02 = (ArrayList<String>) y.readArray(C.CSQ0.NAMES.c3);
                        ArrayList<String> t03 = (ArrayList<String>) y.readArray(C.CSQ0.NAMES.c4);
                        t04 = (ArrayList<ArrayList<String>>) y.readArray(C.CSQ0.NAMES.c5);
                        r0 = u.generateAllPossiblePlans(t01, t02, t03);

                        if (r0.size() > 0) {
                            ArrayList<p> newr0 = new ArrayList<>();
                            while (r0.size() > 0){
                                newr0.add(r0.get(0));
                                r0.remove(0);
                                int i =0;
                                while (i < r0.size())
                                    if(r0.get(i).f7(newr0.get(newr0.size() - 1)) == 2)
                                        r0.remove(i);
                                    else
                                        i++;
                            }
                            r0 = newr0;
                            for (int i = 0; i < r0.size(); i++) {
                                p temp = r0.get(i);
                                temp.update(t04);

                                if (temp.teacherRating >= maxostads)
                                    maxostads = temp.teacherRating;
                                if (temp.teacherRating <= minostads)
                                    minostads = temp.teacherRating;
                                if (temp.timeRating >= maxtimings)
                                    maxtimings = temp.timeRating;
                                if (temp.timeRating <= mintimings)
                                    mintimings = temp.timeRating;
                            }
                            ArrayList<ArrayList<String>> tosave = new ArrayList<>();
                            for (int i = 0; i < r0.size(); i++) {
                                r0.get(i).scaleRates(minostads, maxostads, mintimings, maxtimings);
                                tosave.add(r0.get(i).f5());
                            }
                            y.writeArray(C.CSQ0.NAMES.c6, tosave, 2);
                            Message msg = new Message();
                            msg.what = 1;
                            r1.sendMessage(msg);
                            //
                            Message x = new Message();
                            x.what = 0;
                            x.obj = t00;
                            r1.sendMessage(x);
                        }else{
                            Message x = new Message();
                            x.what = 0;
                            x.obj = t00;
                            r1.sendMessage(x);
                            Dialog.OnCancelListener templistener = new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                }
                            };
                            x = new Message();
                            x.what = 2;
                            x.obj = templistener;
                            r1.sendMessage(x);
                        }
                        } catch(Exception e){
                            try {
                                y.deleteArray(C.CSQ0.NAMES.c6);
                            } catch (Exception w) {
                                w.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }
                }).start();
        }
    }
    View.OnClickListener r5 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case C.CR0.IDS.c6:
                    new AlertDialog.Builder(v.this).setMessage(R.string.reallyredoall)
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                dialog.cancel();
                                redo();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).show();
                    break;
                case C.CR0.IDS.c7:
                    r0.add(new p());
                    w.plan = r0.get(r0.size() - 1);
                    startActivityForResult(new Intent(v.this,w.class),209);
                    break;
            }
        }
    };
    Handler r1 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what == 0) {
                ((Dialog) message.obj).cancel();
            }
            if(message.what == 1){
                r2 = findViewById(C.CR0.IDS.c0);
                r2.setAdapter(new r3(v.this,android.R.layout.simple_list_item_2,C.CR0.IDS.c3,r0));
                r2.setOnItemClickListener(r4);
            }
            if(message.what == 2){
                new e(v.this,(Dialog.OnCancelListener)message.obj).f0(getString(R.string.noplanexists));
            }
            return false;
        }
    });
    AdapterView.OnItemClickListener r4 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            w.plan = r0.get(i);
            startActivityForResult(new Intent(v.this,w.class),204);
        }
    };
    private class r3 extends ArrayAdapter<p> {
        SharedPreferences sh;
        int id;
        public r3(Context r0,int r1,int r2,ArrayList<p> r3){
            super(r0,r1,r2,r3);
            sh = getSharedPreferences(C.CSHAREDPREFS0.c0,MODE_PRIVATE);
            id = sh.getInt(C.CSHAREDPREFS0.IDENTITIES.c7,Integer.MAX_VALUE);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater t0 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View t1 = t0.inflate(C.CR0.LAYOUT.c1,parent,false);
            p temp = r0.get(position);
            if(id == temp.id){
                t1.findViewById(C.CR0.IDS.c5).setBackgroundColor(C.COLORS.c0);
                ((TextView) t1.findViewById(C.CR0.IDS.c10)).setTextColor(C.COLORS.c1);
                ((TextView) t1.findViewById(C.CR0.IDS.c20)).setTextColor(C.COLORS.c1);
                ((TextView)t1.findViewById(C.CR0.IDS.c4)).setTextColor(C.COLORS.c1);
                ((TextView) t1.findViewById(C.CR0.IDS.c3)).setTextColor(C.COLORS.c1);
            }

            setSuitableColor((ImageView) t1.findViewById(C.CR0.IDS.c1),temp.timeRating);
            setSuitableColor((ImageView) t1.findViewById(C.CR0.IDS.c2),temp.teacherRating);

            ((TextView) t1.findViewById(C.CR0.IDS.c10)).setText(String.valueOf(temp.timeRating).substring(0,3));
            ((TextView) t1.findViewById(C.CR0.IDS.c20)).setText(String.valueOf(temp.teacherRating).substring(0,3));

            ((TextView)t1.findViewById(C.CR0.IDS.c4)).setText(String.valueOf(position + 1));

            ((TextView) t1.findViewById(C.CR0.IDS.c3)).setText(String.valueOf(temp.numOfVahed));

            return t1;
        }
    }
    private void setSuitableColor(ImageView iv,float rate){
        if(rate >= 8.0f) {
            iv.setImageResource(R.mipmap.rating_green);
            return;
        }else if(rate >= 5.0f){
            iv.setImageResource(R.mipmap.rating_yellow);
            return;
        }else
            iv.setImageResource(R.mipmap.rating_red);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 204) {
            if (data == null)
                return;
            if (resultCode == Activity.RESULT_OK) {
                p plan = null;
                int id = data.getIntExtra("ID", Integer.MIN_VALUE);
                for (int i = 0; i < r0.size(); i++)
                    if (r0.get(i).id == id)
                        plan = r0.get(i);
                if (plan != null) {
                    SharedPreferences temp = getSharedPreferences(C.CSHAREDPREFS0.c0, MODE_PRIVATE);
                    SharedPreferences.Editor edit = temp.edit();
                    edit.putInt(C.CSHAREDPREFS0.IDENTITIES.c7, plan.id);
                    edit.commit();
                    s saver = new s(C.CSQ0.NAMES.c0);
                    try {
                        ArrayList<ArrayList<String>> tosave = new ArrayList<>();
                        for (int i = 0; i < r0.size(); i++) {
                            tosave.add(r0.get(i).f5());
                        }
                        saver.writeArray(C.CSQ0.NAMES.c6, tosave, 2);
                    } catch (Exception e) {
                        try {
                            saver.deleteArray(C.CSQ0.NAMES.c6);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                int id = data.getIntExtra("ID", Integer.MIN_VALUE);
                SharedPreferences temp = getSharedPreferences(C.CSHAREDPREFS0.c0, MODE_PRIVATE);
                if (id == temp.getInt(C.CSHAREDPREFS0.IDENTITIES.c7, Integer.MAX_VALUE)) {
                    SharedPreferences.Editor edit = temp.edit();
                    edit.remove(C.CSHAREDPREFS0.IDENTITIES.c7);
                    edit.commit();
                    try {
                        s saver = new s(C.CSQ0.NAMES.c0);
                        saver.deleteArray(C.CSQ0.NAMES.c6);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                for (int i = 0; i < r0.size(); i++)
                if (r0.get(i).id == id)
                    r0.remove(i);
                if (r0.size() == 0) {
                    try {
                        s saver = new s(C.CSQ0.NAMES.c0);
                        saver.deleteArray(C.CSQ0.NAMES.c6);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    startActivity(new Intent(v.this, f.class));
                    finish();
                }
                if(Integer.MAX_VALUE != temp.getInt(C.CSHAREDPREFS0.IDENTITIES.c7, Integer.MAX_VALUE)){
                    try {
                        s saver = new s(C.CSQ0.NAMES.c0);
                        ArrayList<ArrayList<String>> saved = new ArrayList<>();
                        for(int i =0 ; i < r0.size() ; i++)
                            saved.add(r0.get(i).f5());
                        saver.writeArray(C.CSQ0.NAMES.c6,saved,2);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }else if(requestCode == 209){
            if(data == null)
                return;
            if(resultCode == RESULT_OK){
                p plan = null;
                int id = data.getIntExtra("ID", Integer.MIN_VALUE);
                for (int i = 0; i < r0.size(); i++)
                    if (r0.get(i).id == id)
                        plan = r0.get(i);
                if (plan != null) {
                    SharedPreferences temp = getSharedPreferences(C.CSHAREDPREFS0.c0, MODE_PRIVATE);
                    SharedPreferences.Editor edit = temp.edit();
                    edit.putInt(C.CSHAREDPREFS0.IDENTITIES.c7, plan.id);
                    edit.commit();
                    s saver = new s(C.CSQ0.NAMES.c0);
                    try {
                        ArrayList<ArrayList<String>> tosave = new ArrayList<>();
                        for (int i = 0; i < r0.size(); i++) {
                            tosave.add(r0.get(i).f5());
                        }
                        saver.writeArray(C.CSQ0.NAMES.c6, tosave, 2);
                    } catch (Exception e) {
                        try {
                            saver.deleteArray(C.CSQ0.NAMES.c6);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            }else if(resultCode == RESULT_CANCELED){
                r0.remove(r0.size() - 1);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    void refresh(){
        if(r0 != null) {
            for (int i = 0; i < r0.size(); i++) {
                p temp = r0.get(i);
                temp.update(t04);

                if (temp.teacherRating >= maxostads)
                    maxostads = temp.teacherRating;
                if (temp.teacherRating <= minostads)
                    minostads = temp.teacherRating;
                if (temp.timeRating >= maxtimings)
                    maxtimings = temp.timeRating;
                if (temp.timeRating <= mintimings)
                    mintimings = temp.timeRating;
            }
            for (int i = 0; i < r0.size(); i++) {
                r0.get(i).scaleRates(minostads, maxostads, mintimings, maxtimings);
            }
            if(r2 != null)
            r2.setAdapter(new r3(v.this, android.R.layout.simple_list_item_2, C.CR0.IDS.c3, r0));
        }
    }
    @Override
    protected void onResume() {
        refresh();
        super.onResume();
    }

    @Override
    protected void onPause() {
        SharedPreferences sh = getSharedPreferences(C.CSHAREDPREFS0.c0,MODE_PRIVATE);
        if(sh.getInt(C.CSHAREDPREFS0.IDENTITIES.c7 , Integer.MAX_VALUE) != Integer.MAX_VALUE){
            SharedPreferences.Editor edit = sh.edit();
            edit.putString(C.CSHAREDPREFS0.PROGRESS.c0,C.CSHAREDPREFS0.PROGRESS.STAGES.c3);
            edit.commit();
        }else{
            SharedPreferences.Editor edit = sh.edit();
            edit.putString(C.CSHAREDPREFS0.PROGRESS.c0,C.CSHAREDPREFS0.PROGRESS.STAGES.c2);
            edit.commit();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        tableplan = null;
        super.onDestroy();
    }

    private void redo() throws Exception{
        s saver = new s(C.CSQ0.NAMES.c0);
        saver.deleteArray(C.CSQ0.NAMES.c6);
        SharedPreferences shared = getSharedPreferences(C.CSHAREDPREFS0.c0,MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.remove(C.CSHAREDPREFS0.IDENTITIES.c7);
        edit.commit();
        startActivity(new Intent(v.this,f.class));
        w.plan = null;
        aa.plan = null;
        u.clearIds();
        finish();
    }
}