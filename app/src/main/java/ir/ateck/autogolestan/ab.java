package ir.ateck.autogolestan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.Random;

public class ab extends WebView {

    private int id;
    private String url;
    private static final String[] USERGENTS = {C.AB0.USERAGENT1,"y", C.AB0.USERAGENT2,"y",C.AB0.USERAGENT3,"y",C.AB0.USERAGENT4,"y",C.AB0.USERAGENT5,"y"};
    private OnChromeErrorListener errorListener;
    public ab(Context r0){
        this(r0 , null);
    }
    public ab(Context r0 , AttributeSet r1){
        super(r0,r1);

        Random rand = new Random();
        rand.setSeed(rand.nextLong());
        do{
            id = rand.nextInt();
        }while (!u.isIdEligible(id) || id < 0);

        this.setId(id);

        for(int i =0 ; i < USERGENTS.length / 2 ; i++){
            if(USERGENTS[2 * i + 1].equals("y")){
                this.getSettings().setUserAgentString(USERGENTS[2 * i]);
                USERGENTS[2 * i + 1] = "n";
            }
        }
    }

    @Override
    public int getId() {
        return this.id;
    }

    private Bitmap getCurrentContent(){
        if(k.getMaxHeight() == -1 || k.getMaxWidth() == -1)
            return null;
        Bitmap b = Bitmap.createBitmap(k.getMaxWidth() , k.getMaxHeight() , Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        draw(c);
        return b;
    }

    public boolean hasReachedState(ArrayList<Pixel> infotomatch , boolean All){
        Bitmap current = getCurrentContent();
        if(current == null)
            return false;
        if(All) {
            for (int i = 0; i < infotomatch.size(); i++) {
                if (infotomatch.get(i) == null)
                    return false;

                int x = (int) infotomatch.get(i).getX();
                int y = (int) infotomatch.get(i).getY();

                Log.d("colors" , Integer.toHexString(current.getPixel(x,y)) + " " + Integer.toHexString(infotomatch.get(i).getColor()));

                if (x >= current.getWidth() || y >= current.getHeight())
                    return false;

                if (!isColorsEqual(current.getPixel(x, y),infotomatch.get(i).getColor()))
                    return false;
            }

            return true;
        }else{
            for (int i = 0; i < infotomatch.size(); i++) {
                if (infotomatch.get(i) == null)
                    continue;

                int x = (int) infotomatch.get(i).getX();
                int y = (int) infotomatch.get(i).getY();

                Log.d("colors" , Integer.toHexString(current.getPixel(x,y)) + " " + Integer.toHexString(infotomatch.get(i).getColor()));

                if (x >= current.getWidth() || y >= current.getHeight())
                    continue;

                if (isColorsEqual(current.getPixel(x, y),infotomatch.get(i).getColor()))
                    return true;
            }
            return false;
        }
    }

    private boolean isColorsEqual(int color1 , int color2){

        String hex1 = Integer.toHexString(color1);
        String hex2 = Integer.toHexString(color2);

        for(int i =0 ; i< hex1.length() ; i++)
            if(Math.abs(hexCharToBinary(hex1.charAt(i)) - hexCharToBinary(hex2.charAt(i))) >= C.AB0.c0)
                return false;

        return true;
    }

    private int hexCharToBinary(char c){
        switch (c){
            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                Log.d("invalid" , ";"+ c + ";");
                return -1;
        }
    }

    @Override
    public void loadUrl(String url) {
        this.url = url;
        super.loadUrl(url);
    }

    @Override
    public void reload() {
        this.loadUrl(url);
    }



    public void setErrorListener(OnChromeErrorListener errorListener) {
        this.errorListener = errorListener;
    }


    private class chromeClient extends WebChromeClient{
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if(errorListener != null)
                return errorListener.onError(consoleMessage);
            else
                return super.onConsoleMessage(consoleMessage);
        }
    }

    private class webClient extends WebViewClient{
    }
}
