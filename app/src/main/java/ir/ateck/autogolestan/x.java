package ir.ateck.autogolestan;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class x {
    public static TextView getTextViewCompartment(FrameLayout parent,float starttime, float endtime, int day, String lessonname, String ostadname, Context context, View.OnClickListener listener){

        if(starttime < 8.0f)
            starttime  = 8.0f;
        if(endtime > 19.0f)
            endtime = 19.0f;
        Log.d("time",(starttime) + " " +(endtime));
        DisplayMetrics t0 = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(t0);
        int height = t0.heightPixels;
        int width = t0.widthPixels;
        int offsetheight = 0;
        int offsetwidth = 0;
        int c1 = 0;

        if(t0.heightPixels< 1080){
            offsetheight = (height - 720) / 2;
            offsetwidth = (width - 1280) / 2;
            width = 1280;
            height = 720;
            c1 = C.CX0.SIZES.c1_720;
        }else if(t0.heightPixels< 1440) {
            offsetheight = (height - 1080) / 2;
            offsetwidth = (width - 1920) / 2;
            width = 1920;
            height = 1080;
            c1 = C.CX0.SIZES.c1_1080;
        }else if(t0.heightPixels< 2160){
            offsetheight = (height - 1440) / 2;
            offsetwidth = (width - 2160) / 2;
            width = 2160;
            height = 1440;
            c1 = C.CX0.SIZES.c1_1440;
        }else{
            offsetheight = (height - 2160) / 2;
            offsetwidth = (width - 3240) / 2;
            width = 3240;
            height = 2160;
            c1 = C.CX0.SIZES.c1_2160;

        }
        t0 = null;

        TextView retval = new TextView(context);
        int tvwidth = scaleTo(c1 * (endtime - starttime),C.CX0.SIZES.c10,width);
        int tvheight = scaleTo(C.CX0.SIZES.c0,C.CX0.SIZES.c00,height);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(tvwidth,tvheight);
        int leftMargin = scaleTo(C.CX0.SIZES.c2 + (starttime - 8.0f) * c1,C.CX0.SIZES.c10,width);
        int topMargin = scaleTo(C.CX0.SIZES.c20 + (day) * (C.CX0.SIZES.c3 + C.CX0.SIZES.c0),C.CX0.SIZES.c00,height);
        params.leftMargin = leftMargin + offsetwidth;
        params.topMargin = topMargin + offsetheight;
        retval.setText(lessonname + "  " + ostadname);
        retval.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        retval.setBackgroundColor(C.COLORS.c4);
        retval.setOnClickListener(listener);
        parent.addView(retval,params);
        return retval;
    }
    private static int scaleTo(float value,int a,int b){
        float ratio = (float) b / a;
        return (int)(ratio * value);
    }
}