package ir.ateck.autogolestan;

import android.app.Activity;
public class g extends Activity {
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    s y = new s(C.CSQ0.NAMES.c0);
                    s.expectedNullReturn = new ArrayList<ArrayList<String>>();
                    ArrayList<ArrayList<String>> planssaved = (ArrayList<ArrayList<String>>) y.readArray(C.CSQ0.NAMES.c6);

                    if(planssaved.size() == 0)
                        ERROR("planssaved");

                    ArrayList<p> plans = new ArrayList<>();
                    for(int i =0 ; i < planssaved.size() ; i++)
                        plans.add(new p(planssaved.get(i)));
                    planssaved.clear();
                    planssaved = null;

                    SharedPreferences sh = getSharedPreferences(C.CSHAREDPREFS0.c0 , MODE_PRIVATE);
                    int id = sh.getInt(C.CSHAREDPREFS0.IDENTITIES.c7 , Integer.MAX_VALUE);
                    domain = sh.getString(C.CSHAREDPREFS0.IDENTITIES.c6 , "poop");
                    tododate[0] = sh.getString(C.CSHAREDPREFS0.IDENTITIES.c1,"00");
                    tododate[1] = sh.getString(C.CSHAREDPREFS0.IDENTITIES.c2 , "00");
                    tododate[2] = sh.getString(C.CSHAREDPREFS0.IDENTITIES.c3,"00");
                    tododate[3] = sh.getString(C.CSHAREDPREFS0.IDENTITIES.c4,"00");
                    tododate[4] = sh.getString(C.CSHAREDPREFS0.IDENTITIES.c5,"00");
                    if(!domain.contains("https://"))
                        domain = ("https://" + domain);
                    domain = domain.replace(" " , "");
                    domain = domain.replace("\n" , "" );
                    domain = domain.replace("\r" , "");
                    if(id == Integer.MAX_VALUE || domain.equals("poop"))
                        ERROR("id not saved");

                    for(int i = 0 ;i < plans.size() ; i++)
                        if(plans.get(i).id == id) {
                            plan = plans.get(i);
                            break;
                        }
                    if(plan == null)
                        ERROR("plan null");
                    plans.clear();
                    plans = null;

                    rows = new ArrayList<>();
                    for(int i = 0; i < plan.f2().size() ; i++) {
                        rows.add(Integer.valueOf(plan.f2().get(i).get(0, o.ROW)));
                        Log.e(plan.f2().get(i).get(0,o.LESSONNAME) + plan.f2().get(i).get(0,o.GROUPE) , rows.get(i) + "");
                    }
                    if(rows.size() == 0)
                        ERROR("plan empty");

                    sort(rows,0,rows.size() - 1);

                    plan.delete();
                    plan = null;



                    LOAD();


                }catch (Exception e){
                    ERROR(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

        */

    }
