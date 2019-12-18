package ir.ateck.autogolestan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class w extends Activity {
    public static p plan;
    private boolean isCustom = false;
    private Dialog curdial;
    ArrayList<TextView> views = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(plan == null)
            finish();
        if(plan.isEmpty())
            isCustom = true;
        f0();
    }




    private void f0(){
        setContentView(C.CW0.LAYOUTS.c0);

        FrameLayout view = findViewById(C.CW0.IDS.c0);
        ImageView backview = findViewById(C.CW0.IDS.c1);
        findViewById(C.CW0.IDS.c2).setOnClickListener(listener);
        u.fullifyScreen(this);

        DisplayMetrics r0 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(r0);

        if(r0.heightPixels< 1080){
            backview.setImageResource(R.mipmap.plan_view_720p);
        }
        else if(r0.heightPixels< 1440) {
            backview.setImageResource(R.mipmap.plan_view_1080p);
        }else if(r0.heightPixels< 2160){
            backview.setImageResource(R.mipmap.plan_view_1440p);
        }else{
            backview.setImageResource(R.mipmap.plan_view_2160p);
        }

        ArrayList<o> compatments = plan.f2();
        for(int i =0 ; i < compatments.size() ; i++){
            String[][] mincomps = compatments.get(i).getValuesForPlan();
            for(int j =0 ; j < mincomps.length ; j++){
                float stime = Float.valueOf(mincomps[j][1]).floatValue();
                float endtime = Float.valueOf(mincomps[j][2]).floatValue();
                int day = p.whichDayIsIt(mincomps[j][0]);
                String lessname = mincomps[j][3];
                String teachname = mincomps[j][4];

                views.add(x.getTextViewCompartment(view,stime,endtime,day,lessname,teachname,w.this,listener));
            }
        }

    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v instanceof TextView){
                String whole = ((TextView) v).getText().toString();
                String title = whole.substring(0,whole.indexOf("  "));
                int which = -1;
                for(int i = 0 ; i< views.size() ; i++)
                    if(views.get(i).getText().toString().equals(whole)){
                        which = i;
                        break;
                    }
                if(which == -1)
                    Log.d("search","failed");
                new y(w.this,new DoActionByCode(),which,title).show();
                return;
            }
            if(v instanceof ImageView){
                Dialog x = new Dialog(w.this);
                x.requestWindowFeature(Window.FEATURE_NO_TITLE);
                String[] textstemp = getResources().getStringArray(R.array.plan_menue);
                ArrayList<String> texts = new ArrayList<>();
                for(int i = 0; i < textstemp.length ; i++)
                    texts.add(textstemp[i]);
                if(isCustom) {
                    texts.set(0, "ذخیره و " + texts.get(0));
                    texts.set(2,"پاک کردن تمام موارد برنامه");
                }
                x.setContentView(new z(w.this).getView(texts,menulistener));
                x.show();
                curdial = x;
            }
        }
    };
    View.OnClickListener menulistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            curdial.cancel();
            String[] texts = getResources().getStringArray(R.array.plan_menue);
            if(isCustom) {
                texts[0] = "ذخیره و " + texts[0];
                texts[2] = "پاک کردن تمام موارد برنامه";
            }
            if(((Button) v).getText().toString().equals(texts[0])){
                Intent result = new Intent();
                result.putExtra("ID" , plan.id);
                setResult(Activity.RESULT_OK,result);
                finish();
                return;
            }
            if(((Button) v).getText().toString().equals(texts[1])){
                aa.plan = plan;
                startActivity(new Intent(w.this,aa.class));
                return;
            }
            if(((Button) v).getText().toString().equals(texts[2])){
                if(!isCustom) {
                    u.deleteId(plan.id);
                    Intent result = new Intent();
                    result.putExtra("ID", plan.id);
                    setResult(Activity.RESULT_CANCELED, result);
                    finish();
                    return;
                }else
                {
                    plan.clear();
                    new DoActionByCode().refresh();
                    return;
                }
            }
        }
    };
    public class DoActionByCode{
        ArrayList<ArrayList<String>> currents = new ArrayList<>();
        void doAction(String code,int whichTextView){
            /*
            -1 == del
            0 == changegroupetobuttontext
             */
            String text = code.substring(1);
            String whole = views.get(whichTextView).getText().toString();
            String lessonname = whole.substring(0,whole.indexOf("  "));
            if(code.charAt(0) == '-' && code.charAt(1) == '1') {
                String fullid = "";
                ArrayList<o> comps = plan.f2();
                for(int i =0 ; i < comps.size() ; i++){
                    if(comps.get(i).get(0,o.LESSONNAME).equals(lessonname))
                        fullid = comps.get(i).get(0,o.ID);
                }
                if(fullid.length() > 0)
                plan.f1(fullid);
                if(plan.isEmpty() && !isCustom){
                    u.deleteId(plan.id);
                    Intent result = new Intent();
                    result.putExtra("ID", plan.id);
                    setResult(Activity.RESULT_CANCELED, result);
                    finish();
                }
                return;
            }
            if(code.charAt(0) == '0'){
                String fullid = "";
                ArrayList<o> comps = plan.f2();
                for(int i =0 ; i < comps.size() ; i++){
                    if(comps.get(i).get(0,o.LESSONNAME).equals(lessonname))
                        fullid = comps.get(i).get(0,o.ID);
                }
                if(fullid.length() > 0)
                    plan.f1(fullid);

                int row = Integer.valueOf(text.substring(0,text.indexOf("."))) -1;
                String[][] props = u.getProperties(currents.get(row));
                plan.f0(props);
            }
        }
        ArrayList<String> getSameIdLessons(int which){
            try {
                String whole = views.get(which).getText().toString();
                String lessonName = whole.substring(0, whole.indexOf("  "));
                currents.clear();
                ArrayList<ArrayList<String>> table212 =(ArrayList<ArrayList<String>>) new s(C.CSQ0.NAMES.c0).readArray(C.CSQ0.NAMES.c2);
                ArrayList<String> retval = new ArrayList<>();
                boolean reached = false;
                int row = 0;
                for(int i =0 ; i < table212.size();i++){
                    String[][] props = u.getProperties(table212.get(i));
                    if(props[0][o.LESSONNAME].equals(lessonName)){
                        currents.add(table212.get(i));
                        reached = true;
                        String toAdd = "";
                        for(int j = 0; j < props.length ; j++){
                            if(j == 0)
                                toAdd+=((row + 1) + "." +  props[0][o.TEACHERNAME]);
                            if(j == 0)
                                toAdd+=(" " + props[j][o.DAY] + " " + props[j][o.START] + "-" + props[j][o.FINISH] + "  ");
                            else
                                toAdd+=(" " + props[j][0] + " " + props[j][1] + "-" + props[j][2] + "  ");
                        }
                        retval.add(toAdd);
                        row++;
                    }else{
                        if(reached)
                            break;
                    }
                }
                return retval;
            }catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
        int canAdd(String text){
            int row = Integer.valueOf(text.substring(0,text.indexOf("."))) -1;
            p cloned = plan.clone();
            String[][] props = u.getProperties(currents.get(row));
            cloned.f1(props[0][o.ID]);
            return cloned.f0(props);
        }
        public void refresh(){
            FrameLayout view = findViewById(C.CW0.IDS.c0);
            views.clear();
            view.removeViews(2,view.getChildCount() - 2);
            ArrayList<o> compatments = plan.f2();
            for(int i =0 ; i < compatments.size() ; i++){
                String[][] mincomps = compatments.get(i).getValuesForPlan();
                for(int j =0 ; j < mincomps.length ; j++){
                    float stime = Float.valueOf(mincomps[j][1]).floatValue();
                    float endtime = Float.valueOf(mincomps[j][2]).floatValue();
                    int day = p.whichDayIsIt(mincomps[j][0]);
                    String lessname = mincomps[j][3];
                    String teachname = mincomps[j][4];

                    views.add(x.getTextViewCompartment(view,stime,endtime,day,lessname,teachname,w.this,listener));
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        String temp = isCustom ? "ذخیره و "+ getString(R.string.doyouwanttosetdefault) : getString(R.string.doyouwanttosetdefault);
        new AlertDialog.Builder(w.this).setMessage(temp)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(plan.isEmpty())
                            new e(w.this).f0(getString(R.string.chooseatleastsome));
                        else {
                            Intent result = new Intent();
                            result.putExtra("ID", plan.id);
                            setResult(Activity.RESULT_OK, result);
                            finish();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(isCustom){
                            u.deleteId(plan.id);
                            Intent result = new Intent();
                            result.putExtra("ID", plan.id);
                            setResult(Activity.RESULT_CANCELED, result);
                        }
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        new DoActionByCode().refresh();
        super.onResume();
    }
}
