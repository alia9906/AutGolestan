package ir.ateck.autogolestan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;

public class m extends AsyncTask<File,Integer,Boolean>{
    public static String sexToSave;
    public static Context masterContext;
    private j m0;
    private File[] m1;
    private s m2;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        m0 = new j();
        m2 = new s(C.CSQ0.NAMES.c0);
    }
    @Override
    protected Boolean doInBackground(File... dialogs) {
        if(sexToSave == null || masterContext == null)
            return Boolean.valueOf(false);
        m1 = dialogs;

        ArrayList<ArrayList<String>> main = new ArrayList<>();
        int k = 0;

        try{

            for(int i = 0 ; i < m1.length ; i++){

                String t0 = b.f8(m1[i].getAbsolutePath(),C.CURL0.ELEMENTIDS.c2,C.CURL0.ELEMENTIDS.c3);
                ArrayList<String> t1 = m0.f1(t0);
                t0 = null;


                if(t1.size() % C.CURL0.ELEMENTIDS.c8 == 0){
                    while (t1.size() > 0){
                        ArrayList<String> t10 = new ArrayList<>();
                        t10.add(0 , String.valueOf(k));
                        for(int j = 0 ; j < C.CURL0.ELEMENTIDS.c8 ;j++){
                            t10.add(t1.get(0));
                            t1.remove(0);
                        }
                        if(sexToSave.contains(t10.get(C.CSQ0.SPECIFICCOLUMNS.c6)) && t10.get(C.CSQ0.SPECIFICCOLUMNS.c0).length()> 0) {
                            if(t10.get(C.CSQ0.SPECIFICCOLUMNS.c1).length() > 18)
                                k+=2;
                            else
                                k++;
                            main.add(t10);
                        }
                    }
                }else{
                    return new Boolean(false);
                }
            }

            m2.writeArray(C.CSQ0.NAMES.c2,main,2);


            sexToSave = null;

                ArrayList<ArrayList<String>> planssaved = (ArrayList<ArrayList<String>>) m2.readArray(C.CSQ0.NAMES.c6);

                if(planssaved.size() == 0)
                    return new Boolean(true);

                    ArrayList<p> plans = new ArrayList<>();
                for(int i =0 ; i < planssaved.size() ; i++)
                    plans.add(new p(planssaved.get(i)));
                planssaved.clear();
                planssaved = null;

                ArrayList<String> ids = new ArrayList<>();
                for(int i =0 ; i < plans.size() ; i++){
                    ArrayList<String> idstemp = plans.get(i).getIds();
                    for(int j = 0 ; j < idstemp.size() ; j++)
                        if(!ids.contains(idstemp.get(j)))
                            ids.add(idstemp.get(j));
                }
                ArrayList<String> rows = new ArrayList<>();
                for(int i =0 ; i < main.size() ; i++) {
                    int index;
                    if ((index = ids.indexOf(main.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c1))) != -1)
                        rows.set(index,main.get(i).get(0));
                }

                if(rows.size() != ids.size()){
                    SharedPreferences sh = masterContext.getSharedPreferences(C.CSHAREDPREFS0.c0 , Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sh.edit();
                    ed.remove(C.CSHAREDPREFS0.IDENTITIES.c7);
                    m2.deleteArray(C.CSQ0.NAMES.c6);
                }else{
                    planssaved = new ArrayList<>();
                    for(int i =0 ; i < plans.size() ; i++) {
                        plans.get(i).rowUpdate(ids, rows);
                        planssaved.add(plans.get(i).f5());
                    }
                    m2.writeArray(C.CSQ0.NAMES.c6,planssaved,2);
                }

        }catch (Exception e){
            e.printStackTrace();
            return new Boolean(false);
        }
        return new Boolean(true);
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
    }


}