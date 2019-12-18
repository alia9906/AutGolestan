package ir.ateck.autogolestan;

import android.util.Log;

import java.util.ArrayList;

public class o{
    //raw props persion

    public final static int START = 1;
    public final static int FINISH = 0;
    public final static int LESSONNAME = 2;
    public final static int TEACHERNAME = 3;
    public final static int ID = 4;
    public final static int GROUPE = 5;
    public final static int EXAMDATE = 6;
    public final static int ROW = 7;
    public final static int NUMOFVAHEDNAZARI = 8;
    public final static int WHICHGENDER = 9;
    public final static int WHEREHAPPENS = 10;
    public final static int EXPLANATIONS = 11;
    public final static int DAY = 12;
    public final static int NUMOFVAHEDAMALI  = 13;

    public final static String DIVIDER = "|";
    public final static String ARRDIVIDER = "(-+/\')";

    private ArrayList<String[]> o0;

    public o(){
        o0 = new ArrayList<>();
    }
    public o(String saved){
        o0 = new ArrayList<>();
        int first =0,last;
        while ((last = saved.indexOf(ARRDIVIDER,first)) != -1){
            String arr = saved.substring(first,last);
            int first2 = 0,last2;
            String[] toAdd;
            if(first == 0)
                toAdd = new String[14];
            else
                toAdd = new String[4];
            int i =0;
            while ((last2 = arr.indexOf(DIVIDER,first2)) != -1){
                String mono = arr.substring(first2,last2);
                toAdd[i] = mono;
                i++;
                first2 = last2 + DIVIDER.length();
            }
            o0.add(toAdd);
            first = last + ARRDIVIDER.length();
        }
    }

    public int f0(o temp){
        /*
         * -1 = no collision
         * 0 = same lesson cant add two times
         * 1 = same exam date
         * 2 = time collision
         */
        ArrayList<String[]> p2 = temp.f2();
        if(o0.size() == 0 || p2.size() == 0)
            return -1;
        if(o0.get(0)[ID].equals(p2.get(0)[ID])) {
            return 0;
        }
        if(o0.get(0)[EXAMDATE].length() != 0 && p2.get(0)[EXAMDATE].length() != 0)
        if(o0.get(0)[EXAMDATE].equals(p2.get(0)[EXAMDATE]))
            return 1;

        for(int i = 0 ;i < o0.size(); i++){
            for(int j = 0; j < p2.size() ; j++){
                if(get(i,DAY).equals(temp.get(j,DAY))){
                    String t1 = get(i,START);
                    String t2 = get(i,FINISH);
                    String t3 = temp.get(j,START);
                    String t4 = temp.get(j,FINISH);

                    t1 = convertToEnglish(t1);
                    t2 = convertToEnglish(t2);
                    t3 = convertToEnglish(t3);
                    t4 = convertToEnglish(t4);

                    double t10 = Float.valueOf(t1.substring(0,t1.indexOf(':'))) + Float.valueOf(t1.substring(t1.indexOf(':') + 1)) / 60.0;
                    double t20 = Float.valueOf(t2.substring(0,t2.indexOf(':'))) + Float.valueOf(t2.substring(t2.indexOf(':') + 1)) / 60.0;
                    double t30 = Float.valueOf(t3.substring(0,t3.indexOf(':'))) + Float.valueOf(t3.substring(t3.indexOf(':') + 1)) / 60.0;
                    double t40 = Float.valueOf(t4.substring(0,t4.indexOf(':'))) + Float.valueOf(t4.substring(t4.indexOf(':') + 1)) / 60.0;

                    if(t40 > t10 && t30 < t20)
                        return 2;
                }
            }
        }
        return -1;
    }
    public boolean f1(String[][] props){
        if(o0.size() > 0)
            return false;
        if(props.length > 0){
            if(props[0].length != 14)
                return false;
            for(int i =0 ; i< props.length ; i++){
                if(i > 0)
                    if(props[i].length != 4){
                        clear();
                        return false;
                    }
                o0.add(props[i]);
            }
            return true;
        }
        return false;
    }
    public final ArrayList<String[]> f2(){
        return o0;
    }
    public String get(int index , int index2){
        if(index > o0.size())
            return null;
        if(index == 0){
            return o0.get(index)[index2];
        }else {
            switch (index2){
                case START:
                    return o0.get(index)[1];
                case FINISH:
                    return o0.get(index)[2];
                case WHEREHAPPENS:
                    return o0.get(index)[3];
                case DAY:
                    return o0.get(index)[0];
                default:
                    return null;
            }
        }
    }
    public static String convertToEnglish(String x){
        return new j().f2(x);
    }
    @Override
    public o clone(){
        o cloned = new o();
        for(int i = 0; i < o0.size() ; i++)
            cloned.f2().add(o0.get(i));
        return cloned;
    }
    public String[][] getValuesForPlan(){
        String[][] retval  = new String[o0.size()][5];
        for(int i =0 ; i < retval.length ; i++){
            retval[i][0] = get(i,DAY);
            retval[i][1] = convertToEnglish(get(i,START));
            retval[i][2] = convertToEnglish(get(i,FINISH));
            String t1 = retval[i][1];
            String t2 = retval[i][2];
            double t10 = Float.valueOf(t1.substring(0,t1.indexOf(':'))) + Float.valueOf(t1.substring(t1.indexOf(':') + 1)) / 60.0;
            double t20 = Float.valueOf(t2.substring(0,t2.indexOf(':'))) + Float.valueOf(t2.substring(t2.indexOf(':') + 1)) / 60.0;
            retval[i][1] = String.valueOf(t10);
            retval[i][2] = String.valueOf(t20);
            retval[i][3] = get(0,LESSONNAME);
            retval[i][4] = get(0,TEACHERNAME);
        }
        return retval;
    }
    public String getSaveString(){
        String retval = "";
        for(int i = 0; i < o0.size() ; i++){
            for(int j = 0 ; j < o0.get(i).length ; j++)
                retval+=(o0.get(i)[j] + DIVIDER);
            retval+=(ARRDIVIDER);
        }
        return retval;
    }
    public void delete(){
        o0.clear();
        o0 = null;
    }
    public void clear(){
        o0.clear();
    }
    public void updateRow(String row){
        if(o0.size() != 0)
            o0.get(0)[o.ROW] = row;
    }
}
