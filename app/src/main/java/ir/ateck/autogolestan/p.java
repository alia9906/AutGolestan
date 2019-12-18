package ir.ateck.autogolestan;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class p {
    private final static float MAX = 7.0f;
    private final static float SIGMA = 1.0f / 7.0f;
    private ArrayList<o> p0;
    public int id;
    public float teacherRating;
    public float timeRating;
    public int numOfVahed;

    public p(){
        p0 = new ArrayList<>();
        Random rand = new Random();
        do{
            rand.setSeed(rand.nextInt());
            this.id = rand.nextInt();
        }while(!u.isIdEligible(this.id));
    }
    public p(ArrayList<String> saved){
        p0 = new ArrayList<>();
        int start = saved.get(0).indexOf("\n");
        id = Integer.valueOf(saved.get(0).substring(0,start));
        start++;
        int end = start;
        end = saved.get(0).indexOf("\n",start);
        teacherRating = Float.valueOf(saved.get(0).substring(start,end));
        start = end+1;
        end = saved.get(0).indexOf("\n",start);
        timeRating = Float.valueOf(saved.get(0).substring(start,end));
        start = end+1;
        end = saved.get(0).indexOf("\n",start);
        numOfVahed = Integer.valueOf(saved.get(0).substring(start,end));
        for (int i =1 ; i < saved.size();i++)
            p0.add(new o(saved.get(i)));
    }
    private int f00(o toAdd){
        for(int i = 0 ; i < p0.size() ; i++) {
            int current = p0.get(i).f0(toAdd);
            if(current != -1) {
                if (current == 0)
                    return 0;
                return current * 100 + i;
            }
        }

        p0.add(toAdd);
        return -100;
    }
    public int f0(String[][] props){
        /*
        -200 = invalid arg
        -100 = added
        0 = same lesson
        1xx = same Exam date + index of collision lesson
        2xx = time collision + index of collision lesson
         */
        o toAdd = new o();
        if(!convertProps(props,toAdd)) return -200;

        return f00(toAdd);
    }
    public boolean f1(String ID){
        for (int i =0 ; i < p0.size() ; i++)
            if(p0.get(i).f2().get(0)[o.ID].equals(ID)){
                p0.get(i).delete();
                p0.remove(i);
                return true;
            }
        return false;
    }
    public ArrayList<o> f2(){
        return p0;
    }
    public boolean f4(final p r0){
        ArrayList<o> p1 = r0.f2();
        boolean retval = false;
        for(int i =0 ; i < p1.size() ; i++)
            if(f00(p1.get(i)) == -100)
                retval = true;
        return retval;
    }
    public ArrayList<String> getIds(){
        ArrayList<String> ret = new ArrayList<>();
        for(int i = 0; i < p0.size() ; i++)
            ret.add(p0.get(i).f2().get(0)[o.ID] + "_" + p0.get(i).get(0,o.GROUPE));
        return ret;
    }
    public ArrayList<String> f5(){
        ArrayList<String> ret = new ArrayList<>();
        ret.add(id + "\n" + teacherRating + "\n" + timeRating + "\n" + numOfVahed +"\n");
        for(int i =0 ; i < p0.size() ; i++)
            ret.add(p0.get(i).getSaveString());
        return ret;
    }
    public int f7(p r0){
        /*
        this C r0 = 0
        r0 C this = 1
        r0 C/ this = -1
        r0 == this
         */
        ArrayList<String> t0 = getIds();
        ArrayList<String> t1 = r0.getIds();

        int retval = isSubset(t0,t1);
        return retval;
    }
    public boolean isEmpty(){
        return p0.size() == 0;
    }
    public void clear(){
        for(int i =0 ;i < p0.size();i++){
            p0.get(i).clear();
        }
        p0.clear();
    }
    public void delete(){
        for(int i =0 ;i < p0.size();i++){
            p0.get(i).delete();
        }
        p0.clear();
        p0 = null;
    }
    private boolean convertProps(String[][] props, o retval){
        return retval.f1(props);
    }
    @Override
    public p clone(){
        p cloned = new p();
        for(int i = 0; i < p0.size() ; i++){
            cloned.f2().add(p0.get(i).clone());
        }
        cloned.id = id;
        cloned.teacherRating = teacherRating;
        cloned.timeRating = timeRating;
        cloned.numOfVahed = numOfVahed;
        return cloned;
    }

    public void update(ArrayList<ArrayList<String>> teacherRating){
        this.teacherRating = calculateTeacherRating(teacherRating);
        this.numOfVahed = calculateNumOfVahed();
        this.timeRating = calculateTimeRating();
    }
    private float calculateTeacherRating(ArrayList<ArrayList<String>> teacherRating){
        float retval = 0.0f;
        int sum = 0;

        for(int i =0 ; i < p0.size() ; i++){
            String teacherName = p0.get(i).f2().get(0)[o.TEACHERNAME];

            int index = teacherRating.get(0).indexOf(teacherName);

            if(index != -1){
                int amali =Integer.valueOf(o.convertToEnglish(p0.get(i).f2().get(0)[o.NUMOFVAHEDAMALI]));
                int nazari =Integer.valueOf(o.convertToEnglish(p0.get(i).f2().get(0)[o.NUMOFVAHEDNAZARI]));
                sum+=(amali + nazari);
                retval+= (amali + nazari) * (Float.valueOf(teacherRating.get(1).get(index)));
            }else
                Log.e("TeacherError", "cant find the teacher rating :" + teacherName);
        }
        if(sum == 0)
            return 10.0f;
        return retval / sum;
    }
    private  float calculateTimeRating(){
        String[] days = C.CURL0ELEMENTIDSc9;
        ArrayList<float[]>[] structure = new ArrayList[6];
        for(int i =0 ; i < structure.length ; i++)
            structure[i] = new ArrayList<>();

        for(int i =0 ; i < p0.size() ; i++){
            String[][] values = p0.get(i).getValuesForPlan();
            for(int j =0 ; j< values.length ; j++){
                String day = values[j][0];
                int index = whichDayIsIt(day);
                if(index != -1){
                    float[] temp = new float[2];
                    temp[0] = Float.valueOf(values[j][1]);
                    temp[1] = Float.valueOf(values[j][2]);
                    structure[index].add(temp);
                }else
                    Log.e("TimeRatingError","cant find day :"+day+";");
            }
        }

        for(int i =0 ; i < structure.length ; i++)
            sort(structure[i],0,structure[i].size() -1);

        int n = 0;
        float f = 0.0f;
        float phi = 0.0f;

        for(int i =0 ; i < structure.length ; i++){
            if(structure[i].size() > 0){
                ArrayList<float[]> temp = structure[i];
                n++;
                float timeEff = 2.0f;
                float totalTime =temp.get(temp.size() - 1)[1] -  temp.get(0)[0];
                float timeInvolved = 0.0f;
                for(int j = 0; j < temp.size() ; j++){
                    timeInvolved+=temp.get(j)[1] - temp.get(j)[0];
                    if(temp.get(j)[0] < 10.0f){
                        if(temp.get(j)[1] <= 10.0f){
                            timeEff-=((temp.get(j)[1] - temp.get(j)[0]) / 3.0f);
                        }else{
                            timeEff-=((10.0f - temp.get(j)[0]) / 3.0f);
                        }
                    }
                    if(temp.get(j)[1] > 17.0f){
                        if(temp.get(j)[0] >= 17.0f){
                            timeEff-=((temp.get(j)[1] - temp.get(j)[0]) / 3.0f);
                        }else{
                            timeEff-=((temp.get(j)[1] - 17.0f) / 3.0f);
                        }
                    }
                }
                if(totalTime != 0.0f) {
                    f += (timeInvolved / totalTime);
                    phi += timeEff;
                }
            }
        }

        if(n == 0)
            return 10.0f;
        f/=n;
        phi/=n;
        f = scaleTo(0.0f,1.0f,0.0f,MAX,f);
        phi = scaleTo(0.0f,2.0f,0.0f,MAX,phi);
        if(f == 0.0f)
            f = 0.05f;
        if(phi == 0)
            phi = 0.1f;
        float retval = SIGMA * f * phi / n;
        float retvalScaled;
        if((retvalScaled = scaleTo(0.0f,1.0f,0.0f,10.0f,retval)) == Float.NaN)
            Log.d(toString() , f + " " + phi + " " + n);
        return retvalScaled;
    }
    public void scaleRates(float minos,float maxos,float minti,float maxti){
        if(maxos - minos < C.CU0.RATING.c0){
            teacherRating = scaleTo(minos,maxos,minos - C.CU0.RATING.c1,maxos + C.CU0.RATING.c1,teacherRating);
        }
        timeRating = scaleTo(minti,maxti,C.CU0.RATING.c2,C.CU0.RATING.c3,timeRating);
    }
    private float scaleTo(float a, float b ,float c,float d,float x1){
        if(a != b)
            return (d * (x1 - a) + c*(b - x1))/(b - a);
        return (c + d)/2;
    }
    private int calculateNumOfVahed(){
        int retval = 0;
        for(int i = 0; i < p0.size() ; i++){
            int amali =Integer.valueOf(o.convertToEnglish(p0.get(i).f2().get(0)[o.NUMOFVAHEDAMALI]));
            int nazari =Integer.valueOf(o.convertToEnglish(p0.get(i).f2().get(0)[o.NUMOFVAHEDNAZARI]));
            retval+=(amali + nazari);
        }
        return retval;
    }
    private boolean compareTo(float[] a,float[] b){
        //a < b true;
        //a > b false;
        if(a[1] < b[0])
            return true;
        return false;
    }
    private int partition(ArrayList<float[]> arr, int low, int high)
    {
        float[] pivot = arr.get(high);
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (compareTo(arr.get(j),pivot))
            {
                i++;

                // swap arr[i] and arr[j]
                float[] temp = arr.get(i);
                arr.set(i,arr.get(j));
                arr.set(j,temp);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        float[] temp = arr.get(i+1);
        arr.set(i+1,arr.get(high));
        arr.set(high,temp);

        return i+1;
    }
    private void sort(ArrayList<float[]> arr, int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }
    public static int whichDayIsIt(String x){
        String[] days = C.CURL0ELEMENTIDSc9;
        for(int i = 0; i< days.length; i++)
            if(days[i].equals(x))
                return i;

        return -1;
    }
    public void eligilizeId(){
        Random rand = new Random();
        do{
            rand.setSeed(rand.nextInt());
            this.id = rand.nextInt();
        }while(!u.isIdEligible(this.id));
    }
    void printArr(ArrayList<String> x,String off){
        for(int i =0 ; i < x.size() ; i++)
            Log.e(off,x.get(i));
    }
    public int isSubset(ArrayList<String> a, ArrayList<String> b){
        /*
        a C b = 0
        b C a = 1
        a C/ b = -1
        a == b = 2
         */
        if(a.size() == 0)
            return 0;
        if(b.size() == 0)
            return 1;
        boolean[] commonsa = new boolean[a.size()];
        boolean[] commonsb = new boolean[b.size()];

        for(int i = 0 ; i < commonsa.length ; i++)
            commonsa[i] = b.indexOf(a.get(i)) != -1;

        for(int j = 0 ;j <  commonsb.length ; j++)
            commonsb[j] = a.indexOf(b.get(j)) != -1;

        boolean temp = true;
        for(int i = 0; i < commonsa.length && temp ; i++)
            temp = temp && commonsa[i];

        if(temp && a.size() == b.size())
            return 2;
        if(temp)
            return 0;

        temp = true;
        for(int i = 0; i < commonsb.length && temp ; i++)
            temp = temp && commonsb[i];

        if(temp && a.size() == b.size())
            return 2;

        if(temp)
            return 1;

        return -1;

    }

    public void rowUpdate(ArrayList<String> ids , ArrayList<String> rows){
        for(int i = 0 ; i < p0.size() ; i++){
            int index;
            if((index = ids.indexOf(p0.get(i).get(0,o.ID) + "_" + p0.get(i).get(0,o.GROUPE))) != -1){
                p0.get(i).updateRow(rows.get(index));
            }
        }
    }
}

