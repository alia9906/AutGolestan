package ir.ateck.autogolestan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class f extends Activity implements View.OnClickListener , ListView.OnItemClickListener {

    private ListView f0;
    private ArrayList<String> f1 = new ArrayList<>();
    private ArrayList<String> f2 = new ArrayList<>();
    private ArrayList<String> f3 = new ArrayList<>();
    private ArrayList<String> f6 = new ArrayList<>();
    private String msg;
    private Intent f5;
    private int f4 = -1;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s saver = new s(C.CSQ0.NAMES.c0);
        try{
            ArrayList<ArrayList<String>>  x = (ArrayList<ArrayList<String>>) saver.readArray(C.CSQ0.NAMES.c6);
            if(x.size() == 0)
                throw new Exception();
            v.tableplan = x;
            startActivity(new Intent(f.this,v.class));
            finish();
        }catch (Exception e){
            e.printStackTrace();
            f0(getIntent().getBooleanExtra(C.INTENTS.F.c0,true));
        }
    }
    private void f0(boolean r0){
        if(r0) {
            msg = getString(R.string.infoforchoosingbaselessons);
            f5 = new Intent(this,f.class);
            f5.putExtra(C.INTENTS.F.c0,false);
        }
        else {
            msg = getString(R.string.infoforchoosingneedlesslessons);
            f5 = new Intent(this,n.class);
        }
        setContentView(C.CF0.LAYOUT.c0);
        f0 = findViewById(C.CF0.IDS.c0);

        f0.setOnItemClickListener(this);
        findViewById(C.CF0.IDS.c2).setOnClickListener(this);
        findViewById(C.CF0.IDS.c3).setOnClickListener(this);
        if(r0){
            ((TextView)findViewById(C.CF0.IDS.c5)).setText(R.string.choosemain);
        }
        else{
            ((TextView)findViewById(C.CF0.IDS.c5)).setText(R.string.chooseneedless);
        }
        f1();
        f3(r0);

        f0.setAdapter(new cf1(this,android.R.layout.simple_list_item_2,C.CF0.IDS.c1,f3));

    }
    private void f1(){
        s t0 = new s(C.CSQ0.NAMES.c0);
        try{
            s.expectedNullReturn = new ArrayList<ArrayList<String>>();

            ArrayList<ArrayList<String>> t1 = (ArrayList<ArrayList<String>>) t0.readArray(C.CSQ0.NAMES.c2);
            for(int i = 0; i < t1.size() ; i++)
                if(!f1.contains(t1.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c0)) && !t1.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c0).equals(""))
                    f1.add(t1.get(i).get(C.CSQ0.SPECIFICCOLUMNS.c0));
        }catch (Exception e){e.printStackTrace();}

    }
    private class cf1 extends ArrayAdapter<String>{
        cf1(Context r0,int r1,int r2,ArrayList<String> r3){
            super(r0,r1,r2,r3);
        }

        @Override
        public View getView(int position,View convertView,ViewGroup parent) {
            LayoutInflater t0 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View t1 = t0.inflate(C.CF0.LAYOUT.c1,parent,false);
            if(position <= f4) {
                ((TextView) t1.findViewById(C.CF0.IDS.c1)).setText(f3.get(position));
                ((TextView) t1.findViewById(C.CF0.IDS.c1)).setBackgroundColor(C.COLORS.c0);
                ((TextView) t1.findViewById(C.CF0.IDS.c1)).setTextColor(C.COLORS.c1);
            }else{
                ((TextView) t1.findViewById(C.CF0.IDS.c1)).setText(f3.get(position));
            }
            return t1;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case C.CF0.IDS.c2:
                new e(f.this).f0(msg);
                break;
            case C.CF0.IDS.c3:
                    f2();
                    if(f5(getIntent().getBooleanExtra(C.INTENTS.F.c0,true)))
                         startActivity(f5);
                    else
                        Toast.makeText(this,"انجام نشد.",Toast.LENGTH_LONG).show();
                    finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position <= f4){
            f2.remove(f3.get(position));
            f3.clear();
            for(int i =0 ;i < f2.size() ;i++)
                f3.add(f2.get(i));
            for(int i = 0; i < f1.size() ;i++)
                if(!f3.contains(f1.get(i)))
                    f3.add(f1.get(i));
            f4--;
            f0.setAdapter(new cf1(this,android.R.layout.simple_list_item_2,C.CF0.IDS.c5,f3));
        }else{
            if(f6.contains(f3.get(position))){
                String t00 = getIntent().getBooleanExtra(C.INTENTS.F.c0 , true) ? getString(R.string.thisitemisinneedlasslessons) : getString(R.string.thisitemisinmainlessons);
                new e(f.this).f0(t00);
                return;
            }
            f2.add(f3.get(position));
            f4++;
            String temp  = f3.get(position);
            f3.remove(position);
            f3.add(f4,temp);
            f0.setAdapter(new cf1(this,android.R.layout.simple_list_item_2,C.CF0.IDS.c5,f3));
        }
    }
    private void f2(){
        int[] t0 = new int[f2.size()];

        for(int i = 0; i < t0.length ; i++){
            t0[i] = f1.indexOf(f2.get(i));
        }

        sort(t0,0,t0.length-1);

        f2.clear();

        for(int i =0 ; i < t0.length ;i ++)
            f2.add(f1.get(t0[i]));
    }
    private int partition(int arr[], int low, int high)
    {
        int pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot)
            {
                i++;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    private void sort(int arr[], int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }
    private void f3(boolean r0){
        s t0 = new s(C.CSQ0.NAMES.c0);
        try {
            Object main = t0.readArray(C.CSQ0.NAMES.c3);
            Object less = t0.readArray(C.CSQ0.NAMES.c4);

            s.expectedNullReturn = new ArrayList<String>();

            ArrayList<String> t1 = r0 ? (ArrayList<String>) main : (ArrayList<String> )less ;
            ArrayList<String> t2 = !r0 ? (ArrayList<String>) main : (ArrayList<String>) less;

            f4 = t1.size() - 1;

            for(int i =0 ;i < t1.size() ;i++){
                f2.add(t1.get(i));
                f3.add(t1.get(i));
            }
            for(int i = 0; i < t2.size() ; i++)
                f6.add(t2.get(i));

            for(int i = 0; i < f1.size() ; i++)
                if(!f3.contains(f1.get(i)))
                    f3.add(f1.get(i));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean f5(boolean r0){
        s t0 = new s(C.CSQ0.NAMES.c0);
        String id = r0 ? C.CSQ0.NAMES.c3 : C.CSQ0.NAMES.c4;
        try{
            t0.writeArray(id,f2,1);
            return true;
        }catch (Exception e){
            try{t0.deleteArray(id);}
            catch (Exception e1){e1.printStackTrace(); return false;}
            e.printStackTrace();
            return false;
        }
    }


}
