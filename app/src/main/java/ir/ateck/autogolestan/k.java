package ir.ateck.autogolestan;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import java.util.ArrayList;

public class k{
    private static long startTime = 0;
    private static boolean firstTime = true;
    private static float ratio;
    private static float k0 , k1;
    // 720p display for asli//
    private static float k2 = C.CTOUCHINPUT0.TOUCHPOINTS.c2 ,
            k20 = C.CTOUCHINPUT0.TOUCHPOINTS.c20 , k21 = C.CTOUCHINPUT0.TOUCHPOINTS.c21 , k3 = C.CTOUCHINPUT0.TOUCHPOINTS.c3 ,
            k4 = C.CTOUCHINPUT0.TOUCHPOINTS.c4 , k40 = C.CTOUCHINPUT0.TOUCHPOINTS.c1 - C.CTOUCHINPUT0.TOUCHPOINTS.c40 ,
    k5 = C.CTOUCHINPUT0.TOUCHPOINTS.c1 - C.CTOUCHINPUT0.TOUCHPOINTS.c5
            ,k6 = C.CTOUCHINPUT0.TOUCHPOINTS.c1 - C.CTOUCHINPUT0.TOUCHPOINTS.c6 , k7 = C.CTOUCHINPUT0.TOUCHPOINTS.c7 , k8 = C.CTOUCHINPUT0.TOUCHPOINTS.c8;
    // 720p display for 212//
    private static float k9 = C.CTOUCHINPUT0.TOUCHPOINTS.c9, k90 = 1280f - C.CTOUCHINPUT0.TOUCHPOINTS.c90;
    // to get todopage
    private static float k10 = C.CTOUCHINPUT0.TOUCHPOINTS.c10 ,k100 = C.CTOUCHINPUT0.TOUCHPOINTS.c100
            ,k11 = C.CTOUCHINPUT0.TOUCHPOINTS.c11,k110 = C.CTOUCHINPUT0.TOUCHPOINTS.c110,k12 = C.CTOUCHINPUT0.TOUCHPOINTS.c12
            ,k120 = C.CTOUCHINPUT0.TOUCHPOINTS.c120;
    //to get to etelaat daneshgo (stay on main)
    private static float k13 = C.CTOUCHINPUT0.TOUCHPOINTS.c13 , k130 = C.CTOUCHINPUT0.TOUCHPOINTS.c130 ,
    k14 = C.CTOUCHINPUT0.TOUCHPOINTS.c14 , k140 = C.CTOUCHINPUT0.TOUCHPOINTS.c140;

    public k(Context r0){
        DisplayMetrics t0 = new DisplayMetrics();
        ((Activity) r0).getWindowManager().getDefaultDisplay().getMetrics(t0);
        k0 = t0.widthPixels;
        k1 = t0.heightPixels;
        if(firstTime)
            ratio = k0 / C.CTOUCHINPUT0.TOUCHPOINTS.c0;
        scaleEveryThing();
    }
    public float[] f0(){
        float[] t0 = new float[2];
        t0[0] = k9;
        t0[1] = k1 - k90;
        return t0;
    }
    public ArrayList<MotionEvent> createDirectionToToDoPage(){
        ArrayList<MotionEvent> retval = new ArrayList<>();
        retval.addAll(createPointedEvent(k0 - k10,k100));
        return retval;
    }
    public ArrayList<MotionEvent> directToToDoPage(){
        ArrayList<MotionEvent> retval = new ArrayList<>();
        retval.addAll(createPointedEvent(k0 - k11,k110));
        retval.addAll(createPointedEvent(k0 - k11,k110));
        return retval;
    }
    private void updateTime(){
        startTime = SystemClock.currentThreadTimeMillis();
    }
    private long getTime(){return SystemClock.currentThreadTimeMillis();}
    private void scaleEveryThing(){
        if(!firstTime)
            return;
        k2*=ratio;
        k20*=ratio;
        k21*=ratio;
        k3*=ratio;
        k4*=ratio;
        k40*=ratio;
        k5*=ratio;
        k6*=ratio;
        k7*=ratio;
        k8*=ratio;
        k9*=ratio;
        k90*=ratio;
        k10*=ratio;
        k100*=ratio;
        k11*=ratio;
        k110*=ratio;
        k12*=ratio;
        k120*=ratio;
        k13*=ratio;
        k130*=ratio;
        k14*=ratio;
        k140*=ratio;
        firstTime = false;
    }
    private ArrayList<MotionEvent> createPointedEvent(float x,float y){
        updateTime();
        MotionEvent retval1 = MotionEvent.obtain(startTime,getTime(),MotionEvent.ACTION_DOWN,x,y,0);
        MotionEvent retval2 = MotionEvent.obtain(startTime,getTime(),MotionEvent.ACTION_UP,x,y,0);
        ArrayList<MotionEvent> retval = new ArrayList<>();
        retval.add(retval1);
        retval.add(retval2);
        return retval;
    }
    private ArrayList<MotionEvent> createScrollEvent(final float x, float start, float end){
        ArrayList<MotionEvent> retval = new ArrayList<>();
        float point = start;
        updateTime();
        retval.add(MotionEvent.obtain(startTime,getTime(),MotionEvent.ACTION_DOWN,x,start,0));
        boolean add = end > start;
        while (!(add || (point < end)) || (add && (point < end))){
            retval.add(MotionEvent.obtain(startTime,getTime(),MotionEvent.ACTION_MOVE,x,point,0));
            if(add)
                point+= k0 * 0.1;
            else
                point-=k0 * 0.1;
        }
        retval.add(MotionEvent.obtain(startTime,getTime(),MotionEvent.ACTION_UP,x,end,0));
        return retval;
    }
    public ArrayList<MotionEvent> submitRows(ArrayList<Integer> rows,int currentFullScroll){
        ArrayList<MotionEvent> retval = new ArrayList<>();

        final float maxFittedOnScreen  = (k1 - k5 - k3 - 2.0f * k8) / (k7 + k8);
        final float Eoffset = maxFittedOnScreen - (int)maxFittedOnScreen;
        int i = 0;

        while (i < rows.size()){
            if(isRowInPage(rows.get(i) + 1,currentFullScroll) == 0){
                int row = rows.get(i);
                row-=((int)maxFittedOnScreen - 2)*currentFullScroll;
                float x = 0.7f * k0;
                float y = k3 + k8 + (row + 0.5f) * (k8 + k7);
                retval.addAll(createPointedEvent(x,y));
                i++;
            }else if(isRowInPage(rows.get(i) + 1,currentFullScroll) == -1){
                retval.addAll(doAUpScroll(Eoffset));
                currentFullScroll--;
            }else {
                retval.addAll(doADownScroll(Eoffset));
                currentFullScroll++;
            }
        }
        retval.addAll(createPointedEvent(k4,k1 - k40));
        return retval;
    }
    private int isRowInPage(int row,int page){
        int start = 1;
        int maxFittedOnScreen  = (int)((k1 - k5 - k3 - 2 * k8) / (k7 + k8));
        int end = start + maxFittedOnScreen - 1;
        for(int i =0 ; i < page ; i++){
            start += (maxFittedOnScreen - 2);
            end = start + maxFittedOnScreen - 1;
        }
        if(row < start)
            return -1;
        else if(row > end)
            return 1;
        else
            return 0;
    }
    public ArrayList<MotionEvent> createStayOnMainPageDirection(){
        ArrayList<MotionEvent> retval = new ArrayList<>();
        retval.addAll(createPointedEvent(k14,k140));
        retval.addAll(createPointedEvent(k14,k140));
        return retval;
    }
    public ArrayList<MotionEvent> comeBackToMainPage(){
        return createPointedEvent(k13 , k1 - k130);
    }
    private ArrayList<MotionEvent> doAUpScroll(float Eoffset){
        return createScrollEvent(0.66f * k0 , k3 + (k7 + k8) ,k1 - k5 - (1 + Eoffset) * (k7 + k8));
    }
    private ArrayList<MotionEvent> doADownScroll(float Eoffset){
        return createScrollEvent(0.66f * k0 , k1 - k5 - (1 + Eoffset ) * (k7 + k8),k3 + (k7 + k8));
    }

    public String getScreenMetrics(){
        return ("width = " + k0 + "\n" + "height = " + k1 + "\n" + "maxfit = " + ((k1 - k5 - k3 - 2.0f * k8) / (k7 + k8)) + "\n"+
                "upscroll = " + (k3 + (k7 + k8) /2) + "," + (k1 - k5 - (k7 + k8) /2) + "\n"+
                "downscrool = " + (k1 - k5 - (k7 + k8) /2) + "," + (k3 + (k7 + k8) /2) + "\n");
    }

    public static int getMaxHeight(){
        if(firstTime)
            return -1;
        return (int) k1;
    }
    public static int getMaxWidth(){
        if (firstTime)
            return -1;
        return (int) k0;
    }
    public ArrayList<Pixel> getInfoPagePixelAuth(){
        ArrayList<Pixel> retval = new ArrayList<>();
        retval.add(new Pixel(C.COLORAUTH.INFOPAGE.c0.x * ratio ,
                k1 - C.COLORAUTH.INFOPAGE.c0.y * ratio ,
                C.COLORAUTH.INFOPAGE.c0.color));
        retval.add(new Pixel(C.COLORAUTH.INFOPAGE.c1.x * ratio ,
                k1 - C.COLORAUTH.INFOPAGE.c1.y * ratio ,
                C.COLORAUTH.INFOPAGE.c1.color));
        return retval;
    }
    public ArrayList<Pixel> getToDoPagePixelAuth(int maxrow){
        int maxlessoncompartmentonscreen = getOnScreenLessonCompartment(maxrow);
        ArrayList<Pixel> retval =  new ArrayList<>();
        retval.add(new Pixel(C.COLORAUTH.TODOPAGE.c0.x * ratio ,
                C.COLORAUTH.TODOPAGE.c0.y * ratio ,
                C.COLORAUTH.TODOPAGE.c0.color));
        retval.add(new Pixel(C.COLORAUTH.TODOPAGE.c1.x * ratio ,
                C.COLORAUTH.TODOPAGE.c1.y * ratio ,
                C.COLORAUTH.TODOPAGE.c1.color));
        retval.add(new Pixel(C.COLORAUTH.TODOPAGE.c2.x * ratio ,
                C.COLORAUTH.TODOPAGE.c2.y * ratio ,
                C.COLORAUTH.TODOPAGE.c2.color));
        retval.add(new Pixel(C.COLORAUTH.TODOPAGE.c3.x * ratio ,
                C.COLORAUTH.TODOPAGE.c3.y * ratio ,
                C.COLORAUTH.TODOPAGE.c3.color));
        for(int i  = 1 ; i < maxlessoncompartmentonscreen ; i++){
            retval.add(new Pixel(C.COLORAUTH.TODOPAGE.c3.x * ratio ,
                    C.COLORAUTH.TODOPAGE.c3.y * ratio  + i * (k7 + k8),
                    C.COLORAUTH.TODOPAGE.c3.color));
        }
        return retval;
    }
    private int getOnScreenLessonCompartment(int maxrow){
        maxrow++;
        int maxFittedOnScreen  = (int)((k1 - k5 - k3 - 2 * k8) / (k7 + k8));
        if(maxrow >= maxFittedOnScreen)
            return maxFittedOnScreen;
        else
            return maxrow;
    }

}