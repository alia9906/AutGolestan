package ir.ateck.autogolestan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class aa extends Activity {
    public static p plan;
    TextView before;
    private ArrayList<ArrayList<String>> table212;
    private ArrayList<String> lessonsmain;
    private ScrollView submain;
    private String currentLesson;
    private ArrayList<ArrayList<String>> currents = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(plan == null)
            finish();
        f0();
    }
    private void f0(){
        setContentView(C.CAA0.LAYOUT.c0);

        LinearLayout main = findViewById(C.CAA0.IDS.c0);
        submain = findViewById(C.CAA0.IDS.c1);

        try{
            table212 = (ArrayList<ArrayList<String>>) new s(C.CSQ0.NAMES.c0).readArray(C.CSQ0.NAMES.c2);
        }catch (Exception e){
            e.printStackTrace();
        }

        lessonsmain = new ArrayList<>();
        for(int i =0 ; i < table212.size() ; i++){
            if(!lessonsmain.contains(table212.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c0)))
                if(!doesHaveLesson(table212.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c1)))
            lessonsmain.add(table212.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c0));
        }

        for(int i = 0; i < lessonsmain.size(); i++){
            TextView temp = new TextView(this);
            temp.setText(lessonsmain.get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            DisplayMetrics t0 = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(t0);
            int pad = t0.heightPixels / 25;
            temp.setPadding(pad,pad,pad,pad);
            temp.setOnClickListener(listener);
            main.addView(temp,params);
            temp.setTextColor(C.COLORS.c1);
            if(i == 0) {
                temp.setTextColor(C.COLORS.c0);
                before = temp;
            }
        }

        submain.removeAllViews();

        submain.addView(new z(this).getView(getSameIdLessons(lessonsmain.get(0)),listener));
        currentLesson = lessonsmain.get(0);
    }

    private boolean doesHaveLesson(String FULLID) {
        if(FULLID == null)
            return false;
        if(FULLID.length() <= 1)
            return false;
        String id = FULLID.substring(0,FULLID.indexOf(C.CU0.KEYWORD.c1));
        ArrayList<o> comps = plan.f2();
        for(int i = 0; i < comps.size() ; i++)
            if(comps.get(i).get(0,o.ID).equals(id))
                return true;
        return false;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if(v instanceof Button){
               if(((Button) v).getCurrentTextColor() == 0xFF000001){
                   String text = ((Button) v).getText().toString();
                   int row = Integer.valueOf(text.substring(0,text.indexOf("."))) -1;
                   String[][] props = u.getProperties(currents.get(row));
                   int added = plan.f0(props);
                   new e(aa.this).f0("اضافه شد.");
                   ((Button) v).setTextColor(0xFF000000);
                   ((Button) v).setBackgroundColor(C.COLORS.c4);
                   ((Button)v).setClickable(false);
               }else{
                   int canadd = canAdd(((Button) v).getText().toString());
                   if(canadd == -100){
                       v.setBackgroundColor(0xFF00FF00);
                       ((Button) v).setTextColor(0xFF000001);
                       Toast.makeText(aa.this, "برای تایید دوباره لمس کنید.",Toast.LENGTH_LONG).show();
                   }else {
                       switch (canadd / 100){
                           case -2:
                               new e(aa.this).f0("اطلاعات ناکامل!");
                               break;
                           case 0:
                               new e(aa.this).f0("در گروه دیگری از این درس ثبت نام کرده اید.");
                               break;
                           case 1:
                               new e(aa.this).f0("تاریخ امتحان با " + "\' " + plan.f2().get(canadd%100).get(0,o.LESSONNAME) + " \'" + " تداخل دارد.");
                               break;
                           case 2:
                               new e(aa.this).f0("زمان کلاس با " + "\' " + plan.f2().get(canadd%100).get(0,o.LESSONNAME) + " \'" + " تداخل دارد.");
                               break;
                       }
                   }
               }
               return;
           }
           if(v instanceof TextView){
               before.setTextColor(C.COLORS.c1);
               int index = lessonsmain.indexOf(((TextView) v).getText().toString());
               submain.removeAllViews();
               submain.addView(new z(aa.this).getView(getSameIdLessons(lessonsmain.get(index)),listener));
               currentLesson = lessonsmain.get(index);
               ((TextView) v).setTextColor(C.COLORS.c0);
               before = (TextView ) v;
               return;
           }
       }
   };
    ArrayList<String> getSameIdLessons(String lessonName){
        String debug;
        try {
            currents.clear();
            ArrayList<String> retval = new ArrayList<>();
            int row = 0;
            for(int i =0 ; i < table212.size();i++){
                String[][] props = u.getProperties(table212.get(i));
                if(props != null)
                    if(props.length > 0)
                        if(props[0].length == 14)
                if(props[0][o.LESSONNAME].equals(lessonName)) {
                    currents.add(table212.get(i));
                    String toAdd = "";
                    for (int j = 0; j < props.length; j++) {
                        if (j == 0)
                            toAdd += ((row + 1) + "." + props[0][o.TEACHERNAME]);
                        if (j == 0)
                            toAdd += (" " + props[j][o.DAY] + " " + props[j][o.FINISH] + "-" + props[j][o.START] + "  ");
                        else
                            toAdd += (" " + props[j][0] + " " + props[j][2] + "-" + props[j][1] + "  ");
                    }
                    retval.add(toAdd);
                    row++;
                }
            }
            return retval;
        }catch (Exception e) {
            try{
                File dir = new File(Environment.getExternalStorageDirectory().toString());
                dir.mkdir();
                dir.mkdirs();
                File towrite = new File(dir,"mostafa.txt");
                towrite.createNewFile();
                FileUtils.write(towrite,"\n\n\n\n" + e.getMessage() + "\n\n\n" + e.getLocalizedMessage() + "\n\n\n" + e.toString());
            }catch (Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    int canAdd(String text){
        int row = Integer.valueOf(text.substring(0,text.indexOf("."))) -1;
        p cloned = plan.clone();
        String[][] props = u.getProperties(currents.get(row));
        return cloned.f0(props);
    }
}
