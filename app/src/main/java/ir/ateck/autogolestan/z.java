package ir.ateck.autogolestan;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class z {
    Context c;
    z(Context c){
        this.c = c;
    }
    public ScrollView getView(ArrayList<String> texts, View.OnClickListener listener){
        ScrollView retval = new ScrollView(c);
        LinearLayout inner = new LinearLayout(c);
        inner.setOrientation(LinearLayout.VERTICAL);

        for(int i = 0; i < texts.size() ; i++){
            Button temp = new Button(c);
            temp.setText(texts.get(i));
            temp.setOnClickListener(listener);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            inner.addView(temp,params);
        }

        retval.addView(inner);
        return retval;
    }
}
