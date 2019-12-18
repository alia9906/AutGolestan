package ir.ateck.autogolestan;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class y implements View.OnClickListener {
    w.DoActionByCode y0;
    int whichView;
    Context c;
    private int state = 0;
    private boolean doubleclicked = false;
    Dialog curdialog;
    private String title;
    y(Context c,w.DoActionByCode listener,int whichTextView,String title){
        this.c = c;
        y0 = listener;
        whichView = whichTextView;
        this.title = title;
    }
    public void show() {
            Dialog first = new Dialog(c);
            first.setTitle(title);
            ArrayList<String> fi = new ArrayList<>();
            fi.add(C.CY0.TEXTS.del);
            fi.add(C.CY0.TEXTS.chenge);
            first.setContentView(new z(c).getView(fi, this));
            first.show();
            curdialog = first;
            state = 1;
    }

    @Override
    public void onClick(View v){
        if(state == 1){
            if(((Button) v).getText().equals(C.CY0.TEXTS.del)){
                if(doubleclicked){
                    y0.doAction("-1",whichView);
                    y0.refresh();
                    curdialog.cancel();

                }else{
                    v.setBackgroundColor(0xFF00FF00);
                    ((Button) v).setTextColor(0xFF000001);
                    Toast.makeText(c,"برای تایید دوباره لمس کنید.",Toast.LENGTH_LONG).show();
                    doubleclicked = true;
                }
            }
            else{
                ArrayList<String> fi = y0.getSameIdLessons(whichView);
                if(fi.size() > 1) {
                    Dialog first = new Dialog(c);
                    first.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    first.setContentView(new z(c).getView(fi, this));
                    curdialog.cancel();
                    curdialog = first;
                    first.setTitle(title);
                    first.show();
                    state = 2;
                }else
                    Toast.makeText(c,"گروه دیگری نیست.",Toast.LENGTH_LONG).show();
            }
        }else if(state == 2){
            if(((Button)v).getCurrentTextColor() == 0xFF000001){
                y0.doAction("0" + ((Button)v).getText().toString(),whichView);
                y0.refresh();
                curdialog.cancel();
            }else{
                if(y0.canAdd(((Button)v).getText().toString()) == -100) {
                    v.setBackgroundColor(0xFF00FF00);
                    ((Button) v).setTextColor(0xFF000001);
                    Toast.makeText(c,"برای تایید دوباره لمس کنید.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(c,"تغییر گروه امکان پذیر نیست.",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
