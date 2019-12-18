package ir.ateck.autogolestan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class n extends Activity implements View.OnClickListener{
    ArrayList<String> n0;
    String debug;
    ArrayList<ArrayList<String>> n2 = new ArrayList<>();
    ListView n1;
    ArrayList<SeekBar> n3 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        f0();
    }
    void f0(){
        setContentView(C.CN0.LAYOUT.c0);
        n1 = findViewById(C.CN0.IDS.c0);

        findViewById(C.CN0.IDS.c1).setOnClickListener(this);

        n0 = new ArrayList<>();

        f1();

        n1.setAdapter(new cn1(
                this,android.R.layout.simple_list_item_2 , C.CN0.IDS.c20 , n0));
    }
    private class cn1 extends ArrayAdapter<String> {
        cn1(Context r0, int r1, int r2, ArrayList<String> r3){
            super(r0,r1,r2,r3);
        }

        @Override
        public View getView(int position, View convertView,  ViewGroup parent) {
            LayoutInflater t0 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View t1 = t0.inflate(C.CN0.LAYOUT.c1 , parent,false);

            ((TextView)t1.findViewById(C.CN0.IDS.c20)).setText(n0.get(position));
            n3.add((SeekBar)t1.findViewById(C.CN0.IDS.c2));
            n3.get(n3.size() - 1).setProgress(Integer.valueOf(n2.get(1).get(position)));
            return t1;
        }
    }
    @Override
    public void onClick(View v) {
        if(n3 == null)
            debug = "\\1";
        Integer[] t1 = new Integer[n3.size()];

        for(int i = 0; i < t1.length ; i++)
            if(n3.get(i) != null)
               t1[i] = n3.get(i).getProgress();
            else
                debug = i + "";

        try{
            if(n2.size() > 1)
            for (int i =0 ;i < t1.length ;i++)
                n2.get(1).set(i,String.valueOf(t1[i]));
            else
                debug = "\\2";

            new s(C.CSQ0.NAMES.c0).writeArray(C.CSQ0.NAMES.c5,n2,2);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(debug != null)
            new e(n.this).f0(debug);
        else {
            startActivity(new Intent(this, v.class));
            finish();
        }
    }
    private void f1(){
        s t0 = new s(C.CSQ0.NAMES.c0);
        try{

            ArrayList<String> t1 = (ArrayList<String>) t0.readArray(C.CSQ0.NAMES.c3);
            ArrayList<String> t2 = (ArrayList<String>) t0.readArray(C.CSQ0.NAMES.c4);
            ArrayList<ArrayList<String>> t3 = (ArrayList<ArrayList<String>>) t0.readArray(C.CSQ0.NAMES.c2);
            ArrayList<ArrayList<String>> t4 = (ArrayList<ArrayList<String>>) t0.readArray(C.CSQ0.NAMES.c5);

            for(int i =0 ;i < t3.size() ;i++)
                if(t1.contains(t3.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c0)) || t2.contains(t3.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c0)))
                    if(!n0.contains(t3.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c2)))
                        n0.add(t3.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c2));
            t1 = null;t2 = null;t3 = null;

            ArrayList<String> temp = new ArrayList<>();
            ArrayList<String> temp2 = new ArrayList<>();
            for(int i = 0; i< n0.size() ;i++){
                temp.add(n0.get(i));
                temp2.add(String.valueOf(5));
            }
            n2.add(temp);n2.add(temp2);

            if(t4.size() > 0)
            for(int i = 0; i < t4.get(0).size() ;i++){
                int j;
                if((j = n2.get(0).indexOf(t4.get(0).get(i))) != -1){
                    n2.get(1).set(j,t4.get(1).get(i));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
