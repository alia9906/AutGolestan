package ir.ateck.autogolestan;

import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;

class j {
    public String f0(String r0,int r1){
        String t0 = "";
        boolean t2 = false;
        int t1 = 0;
        while (r1 >= 0) {
            for (; t1 < r0.length() - 3; t1++) {
                if (r0.charAt(t1) == '=' && r0.charAt(t1+3) == '='&& !t2) {
                    t2 = true;
                }
                if (t2) {
                    if(r0.charAt(t1) == '<' || (r0.charAt(t1) == '/' && r0.charAt(t1+1) == '>'))
                        t2 = false;
                    if(t2) {
                        if(r1 == 0)
                        t0 += r0.charAt(t1);
                    }
                    else
                        break;
                }
            }
            r1--;
        }
        return new jc1().raw(t0);
    }
    public String f2(String r0){
        String t0 = "";
        for(int t1 = 0;t1 < r0.length() ; t1++ ){
            switch (r0.charAt(t1)){
                case '\u06f0':
                    t0+='0';
                    break;
                case '\u06f1':
                    t0+='1';
                    break;
                case '\u06f2':
                    t0+='2';
                    break;
                case '\u06f3':
                    t0+='3';
                    break;
                case '\u06f4':
                    t0+='4';
                    break;
                case '\u06f5':
                    t0+='5';
                    break;
                case '\u06f6':
                    t0+='6';
                    break;
                case '\u06f7':
                    t0+='7';
                    break;
                case '\u06f8':
                    t0+='8';
                    break;
                case '\u06f9':
                    t0+='9';
                    break;
                    default:
                        t0+=r0.charAt(t1);
            }
        }
        return t0;
    }
    public ArrayList<String> f1(String r0){
        r0 = r0.replace("=","");
        r0 = r0.replaceAll("\n","");
        r0 = r0.replaceAll("\r","");
        ArrayList<String> t0 = new ArrayList<>();
        int i = 0;

        while ((i = r0.indexOf(C.CURL0.ELEMENTIDS.c2,i)) != -1){
            i+=C.CURL0.ELEMENTIDS.c2.length();
            int j = r0.indexOf(C.CURL0.ELEMENTIDS.c2,i);
            if(j == -1)
                j = r0.length();

            String workOn = r0.substring(i,j);
            String t0Add = "";
            int first = 0,last =0;

            while (true){
                first = workOn.indexOf('>' , last);
                if(first == -1) {
                    break;
                }
                last = workOn.indexOf('<',first + 1);

                String UTF = workOn.substring(first + 1,last);
                if(UTF.contains("D"))
                    if(!t0Add.equals(""))
                        t0Add+=(UTF + "\n");
                    else
                        t0Add+=UTF;
                first = last + 1;
            }

            t0.add(new jc1().raw(t0Add));
            i = j;
        }

        return t0;
    }
    private class jc1{
        //2byte coding//
        public String raw(String utf){
            utf = convertToUTF8(utf);
            String ret = "";
            for(int i = 0 ; i < utf.length();){
                if(hexCharToBinary(utf.charAt(i))!=null){
                    String r0 = hexCharToBinary(utf.charAt(i));
                    String r1 = hexCharToBinary(utf.charAt(i+1));
                    String r2 = hexCharToBinary(utf.charAt(i+2));
                    String r3 = hexCharToBinary(utf.charAt(i+3));

                    int x1 = Integer.parseInt(r0 + r1 ,2);
                    int x2 = Integer.parseInt(r2 + r3 , 2);

                    if(x1 > 127)
                        x1-=256;
                    if(x2 > 127)
                        x2-=256;

                    byte[] b = new byte[2];
                    b[0] = (byte) x1;
                    b[1] = (byte) x2;

                    ret+=new String(b, Charset.forName("UTF-8"));

                    i+=4;
                }else{
                    ret+=utf.charAt(i);
                    i++;
                }
            }
            return ret;
        }
        private String convertToUTF8(String data){
            String retval="";
            for(int i = 0 ; i< data.length();i++) {
                char c = data.charAt(i);
                if (c !='=')
                    retval += c;
            }
            return retval;
        }
        private String hexCharToBinary(char c){
            switch (c){
                case 'A':
                    return "1010";
                case 'B':
                    return "1011";
                case 'C':
                    return "1100";
                case 'D':
                    return "1101";
                case 'E':
                    return "1110";
                case 'F':
                    return "1111";
                case '0':
                    return "0000";
                case '1':
                    return "0001";
                case '2':
                    return "0010";
                case '3':
                    return "0011";
                case '4':
                    return "0100";
                case '5':
                    return "0101";
                case '6':
                    return "0110";
                case '7':
                    return "0111";
                case '8':
                    return "1000";
                case '9':
                    return "1001";
                    default:
                        return null;
            }
        }
    }
}
