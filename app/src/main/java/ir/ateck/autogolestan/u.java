package ir.ateck.autogolestan;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

public class u {
    private static ArrayList<Integer> ids = new ArrayList<>();
    private static ArrayList<p> mainlessonsplan;
    private static ArrayList<p> lesslessonsplan;
    private static ArrayList<ArrayList<ArrayList<String>>> convert(ArrayList<ArrayList<String>> AlllessonProperties , ArrayList<String> lessonswanted){
        ArrayList<ArrayList<ArrayList<String>>> retval = new ArrayList<>();
        int j = 0;
        for(int i = 0; i < lessonswanted.size() ; i++){
            String current  = lessonswanted.get(i);
            ArrayList<ArrayList<String>> currentProps = new ArrayList<>();
            boolean visited = false;
            for(;j<AlllessonProperties.size() ;j++){
                if(AlllessonProperties.get(j).get(C.CSQ0.SPECIFICCOLUMNS.c0).equals(current)){
                    visited = true;
                    currentProps.add(AlllessonProperties.get(j));
                }else{
                    if(visited)
                        break;
                }
            }
            retval.add(currentProps);
        }
        return retval;
    }
    private static ArrayList<p> generateMainPlans(ArrayList<ArrayList<String>> LessonProperties,ArrayList<String> mainlessons){
         ArrayList<ArrayList<ArrayList<String>>> lessonProperties = convert(LessonProperties,mainlessons);
         ArrayList<p> retVal = new ArrayList<>();
        int[] xi = new int[lessonProperties.size()];
        int p = 1;
        for(int i = 0; i < lessonProperties.size();i++) {
            xi[i] = lessonProperties.get(i).size();
            p *= xi[i];
        }
            outer:
            for (int i = 0; i < p; i++) {
                p tempPlan = new p();
                for (int j = 0; j < lessonProperties.size(); j++) {
                    String[][] toadd = getProperties(lessonProperties.get(j).get((i * xi[j]) / p));
                    int ret;
                    if((ret = tempPlan.f0(toadd)) != -100){
                        tempPlan.delete();
                        continue outer;
                    }
                }
               if(!tempPlan.isEmpty())
                   retVal.add(tempPlan);
            }
        return retVal;
    }
    private static ArrayList<p> generateLessLessons(ArrayList<ArrayList<String>> LessonProperties,ArrayList<String> Lesslessons){
        ArrayList<ArrayList<ArrayList<String>>> lessonProperties = convert(LessonProperties,Lesslessons);
        ArrayList<p> retVal = new ArrayList<>();
        int[] xi = new int[lessonProperties.size()];
        int p = 1;
        for(int i = 0; i < lessonProperties.size();i++) {
            xi[i] = lessonProperties.get(i).size();
            p *= xi[i];
        }
            outer:
            for (int i = 0; i < p; i++) {
                p tempPlan = new p();
                for (int j = 0; j < lessonProperties.size(); j++) {
                    String[][] toadd = getProperties(lessonProperties.get(j).get((i * xi[j]) / p));
                    tempPlan.f0(toadd);
                }
                if (!tempPlan.isEmpty())
                    retVal.add(tempPlan);
            }
        return retVal;
    }
    public static ArrayList<p> generateAllPossiblePlans(final ArrayList<ArrayList<String>> allPrperties , final ArrayList<String> mainLessons, final ArrayList<String> lessLessons){
        Thread main = new Thread(new Runnable() {
            @Override
            public void run() {
                mainlessonsplan = generateMainPlans(allPrperties,mainLessons);
            }
        });
        main.start();
        Thread less = new Thread(new Runnable() {
            @Override
            public void run() {
                lesslessonsplan = generateLessLessons(allPrperties,lessLessons);
            }
        });
        less.start();
        while (less.isAlive() || main.isAlive());

        ArrayList<p> retval = new ArrayList<>();
        int i =0,j;
        while(i < mainlessonsplan.size()){
            int root = retval.size();
            retval.add(mainlessonsplan.get(i));
            j = 0;
            boolean addedanythind = false;
            while (j < lesslessonsplan.size()){
                p temp = mainlessonsplan.get(i).clone();
                if(mainlessonsplan.get(i).f4(lesslessonsplan.get(j))){
                    mainlessonsplan.get(i).eligilizeId();
                    retval.add(mainlessonsplan.get(i));
                    addedanythind = true;
                    mainlessonsplan.set(i,temp);
                }
                j++;
            }
            if(addedanythind)
                retval.remove(root);
            i++;
        }
        mainlessonsplan = null;
        lesslessonsplan = null;
        return retval;
    }
    public static String[][] getProperties(ArrayList<String> properties){
        String[] c0 = new String[2];
        c0[0] = C.CU0.KEYWORD.c00;
        c0[1] = C.CU0.KEYWORD.c01;
        String[][] retval = null;
        int howmanytimesaday = 0;
        int tempi = 0;
        ArrayList<ArrayList<String>> daysandtimes= new ArrayList<>();
        final String temptimestamp = properties.get(C.CSQ0.SPECIFICCOLUMNS.c3);

        int start = 0;
        while ((tempi = getIndexOfString(temptimestamp,c0,start)) != -1) {
            howmanytimesaday++;
            String dayandtime = temptimestamp.substring(start,tempi);
            if(dayandtime.length() == 0) {
                tempi+=c0[0].length();
                start = tempi;
                continue;
            }
            dayandtime = dayandtime.replace(" ","");
            dayandtime  = dayandtime.replace("\n","");
            dayandtime = dayandtime.replace("\r","");

            int mid = dayandtime.indexOf(C.CU0.KEYWORD.c4);

            ArrayList<String> tempo = new ArrayList<>();

            tempo.add(dayandtime.substring(0,mid - 5));
            tempo.add(dayandtime.substring(mid + 1));
            tempo.add(dayandtime.substring(mid-5,mid));

            daysandtimes.add(tempo);
            tempi+=c0[0].length();
            start = tempi;
        }
        int begin = temptimestamp.indexOf(C.CU0.KEYWORD.c2);

        if(begin == -1)
            begin = temptimestamp.length();

        String dayandtime = temptimestamp.substring(start,begin);
        if(dayandtime.length() > 0) {
            dayandtime = dayandtime.replace(" ", "");
            dayandtime = dayandtime.replace("\n", "");
            dayandtime = dayandtime.replace("\r", "");

            int mid = dayandtime.indexOf(C.CU0.KEYWORD.c4);

            ArrayList<String> tempo = new ArrayList<>();

            tempo.add(dayandtime.substring(0, mid - 5));
            tempo.add(dayandtime.substring(mid + 1));
            tempo.add(dayandtime.substring(mid - 5, mid));

            daysandtimes.add(tempo);
            tempi += c0[0].length();
            start = tempi;
        }
        retval = new String[howmanytimesaday][];

        for(int i = 0 ; i < howmanytimesaday ; i++) {
            if (i == 0) {
                retval[i] = new String[14];
                retval[i][o.DAY] = daysandtimes.get(i).get(0);
                retval[i][o.START] = daysandtimes.get(i).get(2);
                retval[i][o.FINISH] = daysandtimes.get(i).get(1);
                retval[i][o.LESSONNAME] = properties.get(C.CSQ0.SPECIFICCOLUMNS.c0);
                retval[i][o.TEACHERNAME] = properties.get(C.CSQ0.SPECIFICCOLUMNS.c2);
                String temp = properties.get(C.CSQ0.SPECIFICCOLUMNS.c1);
                retval[i][o.ID] = temp.substring(0,temp.indexOf(C.CU0.KEYWORD.c1));
                retval[i][o.GROUPE] = temp.substring(temp.indexOf(C.CU0.KEYWORD.c1) + 1);
                if (begin != temptimestamp.length()) {
                    temp = temptimestamp.substring(begin);
                    temp = temp.replace(" ", "");
                    temp = temp.replace("\n", "");
                    temp = temp.replace("\r", "");
                    String temp2 = temp.substring(C.CU0.KEYWORD.c5, C.CU0.KEYWORD.c6);
                    String temp3 = temp.substring(C.CU0.KEYWORD.c7);
                    retval[i][o.EXAMDATE] = temp2 + "," + temp3;
                } else
                    retval[i][o.EXAMDATE] = "";
                retval[i][o.ROW] = properties.get(0);
                retval[i][o.NUMOFVAHEDAMALI] = properties.get(C.CSQ0.SPECIFICCOLUMNS.c5);
                int nazari = Integer.valueOf(o.convertToEnglish(properties.get(C.CSQ0.SPECIFICCOLUMNS.c4))) - Integer.valueOf(o.convertToEnglish(retval[i][o.NUMOFVAHEDAMALI]));
                retval[i][o.NUMOFVAHEDNAZARI] = String.valueOf(nazari);
                retval[i][o.WHICHGENDER] = properties.get(C.CSQ0.SPECIFICCOLUMNS.c6);
                retval[i][o.WHEREHAPPENS] = properties.get(C.CSQ0.SPECIFICCOLUMNS.c7);
                retval[i][o.EXPLANATIONS] = properties.get(C.CSQ0.SPECIFICCOLUMNS.c8) + "\n\n" + properties.get(C.CSQ0.SPECIFICCOLUMNS.c9);
            }else{
                retval[i] = new String[4];
                retval[i][0] = daysandtimes.get(i).get(0);
                retval[i][1] = daysandtimes.get(i).get(2);
                retval[i][2] = daysandtimes.get(i).get(1);
                retval[i][3] = retval[0][o.WHEREHAPPENS];
            }
        }
        return retval;
    }
    private static  void print(String[][] x){
        for(int i = 0; i < x.length ; i++){
            String write = "";
            for(int j = 0; j < x[i].length ; j++)
                write+=(x[i][j] + "|");
            Log.d("write",write);
        }
    }
    private static int getIndexOfString(String main,String[] a,int start){
        for(int i =0 ; i < a.length ; i++) {
            int index;
            if ((index = main.indexOf(a[i],start))!= -1)
                return index;
        }
        return -1;
    }
    public static boolean isIdEligible(int id){
        if(id == Integer.MIN_VALUE || id == Integer.MAX_VALUE)
            return false;
        if(ids.indexOf(id) != -1)
            return false;
        ids.add(id);
        return true;
    }
    public static void deleteId(int id){
        ids.remove(Integer.valueOf(id));
    }
    public static void clearIds(){
        ids.clear();
    }
    public static int whatIsTheDimension(Object x){
        try{
            ArrayList<String> c = (ArrayList<String>) x;
            return 1;
        }catch (Exception e){
            if(e instanceof ClassCastException){
                try{
                    ArrayList<ArrayList<String>> c = (ArrayList<ArrayList<String>>) x;
                    return 2;
                }catch (Exception e1){
                    if(e1 instanceof ClassCastException){
                        try{
                            ArrayList<ArrayList<ArrayList<String>>> c = (ArrayList<ArrayList<ArrayList<String>>>) x;
                            return 3;
                        }catch (Exception e2){
                            if(e2 instanceof ClassCastException){
                                return 0;
                            }
                            else
                                return 0;
                        }
                    }
                    else
                        return 0;
                }
            }
            else return 0;
        }
    }
    public static void fullifyScreen(Context r0){
        ((Activity) r0).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
