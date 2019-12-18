package ir.ateck.autogolestan;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

public class t extends Dialog {
    public t( Context context,String r0) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(C.CT0.LAYOUT.c0);
        ((TextView)this.findViewById(C.CT0.IDS.c0)).setText(r0);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
        this.show();
    }
}
