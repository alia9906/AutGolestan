package ir.ateck.autogolestan;

import android.os.Environment;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class s{
    private File idFile;
    public static Object expectedNullReturn;
    private String arrDirectory = Environment.getExternalStorageDirectory().toString() + "/.database/.arrs";
    public final static Charset defCharset = StandardCharsets.UTF_8;
    public s(String r1){
        arrDirectory+=("/" + r1);
        idFile = new File(C.FILES.DIRECTORY.c0,r1);
        if(!idFile.exists())
            try{idFile.createNewFile();}catch(Exception e){e.printStackTrace();}
        File dir = new File(arrDirectory);
        if(!dir.exists()){
            dir.mkdir();
            dir.mkdirs();
        }
    }

    private void writeSingleDimensionArray(String id,final ArrayList<String> arr) throws IOException {
        if(id.contains("\n"))
            return;
        List<String> ids;
        String fileName = null;
        ids = FileUtils.readLines(idFile, defCharset);
        for(int i = 0 ; i < ids.size() ; i++)
            if(id.equals(getArrayId(ids.get(i)))) {
                fileName = getArrayFileName(ids.get(i));
                break;
            }
        if(fileName == null){
            fileName = C.FILES.NAMES.c0 + id + b.f2();
            FileUtils.writeStringToFile(idFile,id + "|" + fileName + "\n",defCharset,true);
        }else {
            File tempFile = new File(arrDirectory,fileName);
            tempFile.delete();
            tempFile = new File(arrDirectory,fileName);
            tempFile.createNewFile();
        }
        final ArrayList<String> arr2 = new ArrayList<>();

        for(int i = 0; i < arr.size() ; i++)
            arr2.add("[|" + arr.get(i) + "|]");

        FileUtils.writeLines(new File(arrDirectory,fileName),defCharset.name(),arr2);
        arr2.clear();
    }
    private String getArrayId(String line){
        return line.substring(0,line.indexOf("|"));
    }
    private String getArrayFileName(String line){
        return line.substring(line.indexOf("|") + 1);
    }
    private ArrayList<String> readSingleDimensionArray(String id) throws IOException{
        if(id.contains("\n"))
            return new ArrayList<>();
        List<String> ids;
        String fileName = null;
        ids = FileUtils.readLines(idFile, defCharset);
        for(int i = 0 ; i < ids.size() ; i++)
            if(id.equals(getArrayId(ids.get(i)))) {
                fileName = getArrayFileName(ids.get(i));
                break;
            }
        if(fileName == null)
            return new ArrayList<>();
        String data = "";
        if(new File(arrDirectory,fileName).exists())
              data = FileUtils.readFileToString(new File(arrDirectory,fileName),defCharset);

        ArrayList<String> retVal = new ArrayList<>();

        int i = 0;

        while (i < data.length()){

            int first = data.indexOf("[|",i);
            int last = data.indexOf("|]",first + 2);

            if(last < 0 || first < 0)
                break;

            if(last >= first)
                retVal.add(data.substring(first + 2,last));

            i = last + 3;
        }
        return retVal;
    }
    public void writeArray(String id , Object arr , int dimension) throws IOException{
        deleteArray(id);

        if(dimension == 1){
            writeSingleDimensionArray(id,(ArrayList<String>) arr);
        }else if(dimension == 2){
            ArrayList<ArrayList<String>> arr2 = (ArrayList<ArrayList<String>>) arr;

            for(int i = 0; i < arr2.size() ; i++)
                writeSingleDimensionArray(id + "[" + String.valueOf(i) + "]",arr2.get(i));
        }else if(dimension == 3){
            ArrayList<ArrayList<ArrayList<String>>> arr2 = (ArrayList<ArrayList<ArrayList<String>>>) arr;

            for(int i = 0 ;i < arr2.size() ; i++)
                for(int j =0 ; j < arr2.get(i).size() ; j++)
                    writeSingleDimensionArray(id + "[" + (i) + "][" + (j)+ "]",arr2.get(i).get(j));

        }
    }
    public Object readArray(String id) throws IOException{
        int dimension = 0;
        int first = -1;
        if(id.contains("\n"))
            return new Object();
        List<String> ids = FileUtils.readLines(idFile, defCharset);
        for(int i = 0 ; i < ids.size() ; i++) {
            String ID = getArrayId(ids.get(i));
            if(ID.contains("["))
                ID = ID.substring(0,ID.indexOf("["));
            if (ID.equals(id)) {
                first = i;
                dimension = 1;
                if (getArrayId(ids.get(i)).contains("[")) {
                    dimension = 2;
                    if (getArrayId(ids.get(i)).contains("]["))
                        dimension = 3;
                }
                break;
            }
        }
        if(first == -1){
            return expectedNullReturn;
        }

        if(dimension == 1)
            return readSingleDimensionArray(id);
        if(dimension == 2){
            ArrayList<ArrayList<String>> retval = new ArrayList<>();

            int current = first;

            String ID;
            do{
                retval.add(readSingleDimensionArray(getArrayId(ids.get(current))));
                current++;

                if(current == ids.size())
                    break;
                ID = getArrayId(ids.get(current));
                if(ID.contains("["))
                    ID = ID.substring(0,ID.indexOf("["));
            }while (ID.equals(id));
            return retval;
        }
        if(dimension == 3) {
            ArrayList<ArrayList<ArrayList<String>>> retval = new ArrayList<>();

            int current = first;
            String trueid;
            String ID;
            ArrayList<ArrayList<String>> tempval = null;
            do {
                trueid = getArrayId(ids.get(current));
                int j = Integer.valueOf(trueid.substring(trueid.indexOf("][") + 2, trueid.length() - 1));

                if (j == 0) {
                    if (tempval != null)
                        retval.add(tempval);
                    tempval = new ArrayList<>();
                }

                tempval.add(readSingleDimensionArray(trueid));
                current++;

                if (current == ids.size())
                    break;

                ID = getArrayId(ids.get(current));
                if (ID.contains("["))
                    ID = ID.substring(0, ID.indexOf("["));
            }
            while (ID.equals(id));
            return retval;
        }
        return new Object();
    }
    public boolean deleteArray(String id) throws  IOException{
        List<String> ids = FileUtils.readLines(idFile,defCharset);
        int ip = 0;
        boolean tempbool = false;
        boolean tempbool1 = false;
        loop : while (ids.size() > 0){
            if(tempbool1)
                break;
            String ID = getArrayId(ids.get(ip));
            if(ID.contains("["))
                ID = ID.substring(0,ID.indexOf("["));
            if(ID.equals(id)){
                tempbool = true;
                String filename = getArrayFileName(ids.get(ip));
                new File(arrDirectory,filename).delete();
                ids.remove(ip);
                if(ip == ids.size())
                    break loop;
            }else {
                if(tempbool)
                    tempbool1 = true;
                ip++;
                if(ip == ids.size())
                    break loop;
            }
        }
        if(tempbool){
            idFile.delete();
            idFile.createNewFile();
            FileUtils.writeLines(idFile,defCharset.name(),ids);
        return true;}
        return false;
    }
}
