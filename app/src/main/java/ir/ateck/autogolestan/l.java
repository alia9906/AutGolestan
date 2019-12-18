package ir.ateck.autogolestan;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

public class l extends Dialog implements View.OnClickListener {
    private Integer[] l0 ;
    private boolean l1 = false;
    private Utilities l3;
    public l(Context context,Integer[] r0) {
        super(context);
        l0 = r0;
        l3 = new Utilities(context);
        this.setContentView(C.CL0.LAYOUTS.c0);
        this.setTitle(R.string.date);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        if(r0 !=null)
        for(int i = 0 ;i <5 && i< r0.length;i++) {
            EditText temp = this.findViewById(f0(i));
            if (r0[i] != null) {
                temp.setText(String.valueOf(r0[i]));
            }
        }
        this.findViewById(C.CL0.IDS.c5).setOnClickListener(this);
    }
    private int f0(int r0){
        if(r0 == 0){
            return C.CL0.IDS.c0;
        }
        if(r0 == 1){
            return C.CL0.IDS.c1;
        }
        if(r0 == 2){
            return C.CL0.IDS.c2;
        }
        if(r0 == 3){
            return C.CL0.IDS.c3;
        }
        if(r0 == 4){
            return C.CL0.IDS.c4;
        }
        return 0;
    }


    @Override
    public void onClick(View v) {
        if(l0 == null || l0.length < 5)
            l0 = new Integer[5];
        l1 = true;
        for(int i = 0 ;i <5 && i< l0.length && l1;i++) {
            String t0 = ((EditText) this.findViewById(f0(i))).getText().toString();
            if(t0 != null && !"".equals(t0)) {
                if (i == 1)
                    if (Integer.valueOf(t0) > 13 || Integer.valueOf(t0) == 0) {
                        l1 = false;
                        ((EditText) this.findViewById(f0(i))).setTextColor(C.COLORS.c3);
                        final int k = i;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(C.CTHREADS0.WHILEERRORING.c0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                handle(l.this.findViewById(f0(k)));
                            }
                        }).start();
                    }
                if (i == 2)
                    if (Integer.valueOf(t0) > 31 || Integer.valueOf(t0) == 0) {
                        l1 = false;
                        ((EditText) this.findViewById(f0(i))).setTextColor(C.COLORS.c3);
                        final int k = i;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(C.CTHREADS0.WHILEERRORING.c0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                handle(l.this.findViewById(f0(k)));
                            }
                        }).start();
                    }
                if (i == 3)
                    if (Integer.valueOf(t0) > 23) {
                        l1 = false;
                        ((EditText) this.findViewById(f0(i))).setTextColor(C.COLORS.c3);
                        final int k = i;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(C.CTHREADS0.WHILEERRORING.c0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                handle(l.this.findViewById(f0(k)));
                            }
                        }).start();
                    }
                if (i == 4)
                    if (Integer.valueOf(t0) > 59) {
                        l1 = false;
                        ((EditText) this.findViewById(f0(i))).setTextColor(C.COLORS.c3);
                        final int k = i;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(C.CTHREADS0.WHILEERRORING.c0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                handle(l.this.findViewById(f0(k)));

                            }
                        }).start();
                    }
                l0[i] = Integer.valueOf(t0);
            }else{
                l1 = false;
            }
        }
        if(l1) {
            this.setCancelable(true);
            this.cancel();
        }
    }
    public Integer[] f1(){
        if(l1)
        return l0;
        return null;
    }
    private Handler l2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            ((EditText)message.obj).setTextColor(C.COLORS.c0);
            return false;
        }
    });

    private void handle(Object obj){
        Message x = new Message();
        x.obj = obj;
        l2.sendMessage(x);
    }
}
