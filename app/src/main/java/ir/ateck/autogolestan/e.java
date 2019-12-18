package ir.ateck.autogolestan;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import java.util.ArrayList;

public class e {
    private Context e0;
    private Button t00;
    private Dialog t0;
    public e(Context r0){
        e0 = r0;this.t0 = new Dialog(e0);
    }
    public e(Context r0,Dialog.OnCancelListener listener){
        e0 = r0;this.t0 = new Dialog(e0);
        t0.setOnCancelListener(listener);
    }
    public void f0(final String r0){
        t0.requestWindowFeature(Window.FEATURE_NO_TITLE);
        t0.setContentView(C.CE0.LAYOUT.c0);
        t00 = t0.findViewById(C.CE0.IDS.c0);
        ScaleAnimation t1 = new ScaleAnimation(0.0f,1.0f,1.0f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        t1.setStartOffset(75);
        t1.setDuration(200);
        t1.setInterpolator(new AccelerateDecelerateInterpolator());
        t1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                t00.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                t00.setText(r0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        t0.create();
        t0.show();
        t00.startAnimation(t1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(C.CTHREADS0.WHILETOAST.c0 * r0.length() + 275);
                    ScaleAnimation t10 = new ScaleAnimation(1.0f,0.0f,1.0f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    t10.setDuration(200);
                    t10.setInterpolator(new AccelerateDecelerateInterpolator());
                    Message x = new Message();
                    x.obj = t10;
                    e3.sendMessage(x);
                    Thread.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }
                t0.dismiss();
            }
        }).start();

    }
    public void f1(){
        t0.cancel();
    }
    private Handler e3 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            t00.setText("");
            t00.startAnimation((ScaleAnimation)message.obj);
            return false;
        }
    });
}
