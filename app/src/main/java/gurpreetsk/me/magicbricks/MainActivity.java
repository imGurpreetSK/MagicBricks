package gurpreetsk.me.magicbricks;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    EditText msgET;
    Button btn;
    TextView inputTV, replyTV;
    String msg, reply;
    LinearLayout linearLayout;

    private String TAG = "MainActivity";

    Hashtable<String, Integer> city = new Hashtable<>();
    Hashtable<String, Integer> locality = new Hashtable<>();
    private ArrayList<String> cities, locations, roomSize, budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getHandles();

        TextView firstTV = new TextView(MainActivity.this);
        firstTV.setGravity(Gravity.CENTER);
        firstTV.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        firstTV.setTextColor(Color.parseColor("#FF4081"));
        firstTV.setTextSize(16);
        firstTV.setText("Welcome to the Hack\n Enter hi or query to start");
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(firstTV);

        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {

                LinearLayout.LayoutParams layoutParamsSender = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParamsSender.setMargins(100, 10, 50, 10);
                linearLayout.setOrientation(LinearLayout.VERTICAL);     //Internal LinearLayout

                //WHAT USER SAYS
                msg = msgET.getText().toString();
                inputTV = new TextView(MainActivity.this);
                linearLayout.addView(inputTV, layoutParamsSender);
                inputTV.setGravity(Gravity.END);
                inputTV.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                inputTV.setTextSize(18);
                inputTV.setTextColor(Color.parseColor("#0277BD"));         //Use #558B2F for bot reply
                inputTV.setText(msg);
                msgET.setText("");

                //WHAT WE SAY
                LinearLayout.LayoutParams layoutParamsBot = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParamsBot.setMarginStart(15);
                layoutParamsBot.setMarginEnd(100);

                if (msg.length() < 15) {
                    replyTV = new TextView(MainActivity.this);
                    linearLayout.addView(replyTV, layoutParamsSender);
                    replyTV.setGravity(Gravity.START);
                    replyTV.setTextSize(18);
                    replyTV.setTextColor(Color.parseColor("#558B2F"));         //Use #558B2F for bot reply
                    replyTV.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);
                    replyTV.setText(msg);
                } else {

                    createCityArrayList();
                    createLocationsArrayList();
                    createBudgetArrayList();
                    createRoomArrayList();
                    createLocalityHashtable();
                    createCityHashtable();

                    final Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                    msg = "LONGER THAN 15";
                    replyTV = new TextView(MainActivity.this);
                    linearLayout.addView(replyTV, layoutParamsSender);
                    replyTV.setGravity(Gravity.START);
                    replyTV.setTextSize(18);
                    replyTV.setTextColor(Color.parseColor("#558B2F"));         //Use #558B2F for bot reply
                    replyTV.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);
                    replyTV.setText(msg);

                    String locationNeeded = "", cityNeeded = "";
                    String budgetneeded = "", sizeNeeded = "";

                    msg = msg.toLowerCase();

                    for (String cty : cities) {
                        if (msg.contains(cty)) {
                            //Toast.makeText(MainActivity.this, "Finding houses in " + location, Toast.LENGTH_SHORT).show();
                            cityNeeded = city.get(cty).toString();
                        }
                    }
                    for (String location : locations) {
                        if (msg.contains(location)) {
                            //Toast.makeText(MainActivity.this, "Finding houses in " + location, Toast.LENGTH_SHORT).show();
                            locationNeeded = locality.get(location).toString();
                        }
                    }
                    for (String bud : budget) {
                        if (msg.contains(bud)) {
                            //Toast.makeText(MainActivity.this, "Finding houses in budget range " + bud, Toast.LENGTH_SHORT).show();
                            budgetneeded = bud;
                        }
                    }
                    for (String size : roomSize) {
                        if (msg.contains(size)) {
                            //Toast.makeText(MainActivity.this, "Finding houses of size " + size, Toast.LENGTH_SHORT).show();
                            sizeNeeded = size;
                        }
                    }

                    replyTV = new TextView(MainActivity.this);
                    //reply = giveReply(msg);

                    String needed, toBeSent;
                    String loc, siz, bud;

                    String replyString = "Please enter some data.";
                    if (cityNeeded.equals("") && locationNeeded.equals("") && budgetneeded.equals("") && sizeNeeded.equals("")) {
                        reply = replyString;
                        intent.putExtra("NEED", "nothing");
                        intent.putExtra("SENT", "nothing");
                    }

                    else if (cityNeeded.equals("") && !locationNeeded.equals("") && budgetneeded.equals("") && sizeNeeded.equals("")) {
                        reply = "Finding houses in locality " + locationNeeded;
                        needed = "location";
                        toBeSent = locationNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENT", toBeSent);
//                        reply = replyString;
                    }else if (!cityNeeded.equals("") && locationNeeded.equals("") && budgetneeded.equals("") && sizeNeeded.equals("")) {
                        reply = "Finding houses in city " + cityNeeded;
                        needed = "city";
                        toBeSent = cityNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENT", toBeSent);
//                        reply = replyString;
                    } else if (cityNeeded.equals("") && locationNeeded.equals("") && !budgetneeded.equals("") && sizeNeeded.equals("")) {
                        reply = "Finding houses in budget " + budgetneeded;
                        needed = "budget";
                        toBeSent = budgetneeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENT", toBeSent);
//                        reply = replyString;
                    } else if (cityNeeded.equals("") && locationNeeded.equals("") && budgetneeded.equals("") && !sizeNeeded.equals("")) {
                        reply = "Finding houses of size " + sizeNeeded;
                        needed = "size";
                        toBeSent = sizeNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENT", toBeSent);
//                        reply = replyString;
                    }

                    else if (!cityNeeded.equals("") && !locationNeeded.equals("") && sizeNeeded.equals("") && budgetneeded.equals("")) {
                        reply = "Finding houses in city " + cityNeeded + " in locality " + locationNeeded;
                        needed = "city and location";
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTCIT", cityNeeded);
                        intent.putExtra("SENTLOC", locationNeeded);
//                        reply = replyString;
                    }else if (cityNeeded.equals("") && !locationNeeded.equals("") && !sizeNeeded.equals("") && budgetneeded.equals("")) {
                        reply = "Finding houses in locality " + locationNeeded + " of size " + sizeNeeded;
                        needed = "location and size";
                        toBeSent = locationNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTLOC", locationNeeded);
                        intent.putExtra("SENTSIZ", sizeNeeded);
//                        reply = replyString;
                    } else if (cityNeeded.equals("") && locationNeeded.equals("") && !sizeNeeded.equals("") && !budgetneeded.equals("")) {
                        reply = "Finding houses of size " + sizeNeeded + " in budget " + budgetneeded;
                        needed = "size and budget";
                        toBeSent = sizeNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTSIZ", sizeNeeded);
                        intent.putExtra("SENTBUD", budgetneeded);
//                        reply = replyString;
                    }else if (!cityNeeded.equals("") && locationNeeded.equals("") && !sizeNeeded.equals("") && budgetneeded.equals("")) {
                        reply = "Finding houses in city " + cityNeeded + " of size " + sizeNeeded;
                        needed = "city and size";
                        toBeSent = cityNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTCIT", cityNeeded);
                        intent.putExtra("SENTSIZ", sizeNeeded);
//                        reply = replyString;
                    }  else if (cityNeeded.equals("") && !locationNeeded.equals("") && sizeNeeded.equals("") && !budgetneeded.equals("")) {
                        reply = "Finding houses in locality " + locationNeeded + " in budget " + budgetneeded;
                        needed = "location and budget";
                        toBeSent = locationNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTLOC", locationNeeded);
                        intent.putExtra("SENTBUD", budgetneeded);
//                        reply = replyString;
                    }else if (!cityNeeded.equals("") && locationNeeded.equals("") && sizeNeeded.equals("") && !budgetneeded.equals("")) {
                        reply = "Finding houses in city " + cityNeeded + " in budget " + budgetneeded;
                        needed = "city and budget";
                        toBeSent = cityNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTCIT", cityNeeded);
                        intent.putExtra("SENTBUD", budgetneeded);
//                        reply = replyString;
                    }

                    else if (!cityNeeded.equals("") && !locationNeeded.equals("") && !sizeNeeded.equals("") && budgetneeded.equals("")) {
                        reply = "Finding houses in city " + cityNeeded + " in budget " + budgetneeded +" of size "+sizeNeeded;
                        needed = "city size location";
                        toBeSent = cityNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTCIT", cityNeeded);
                        intent.putExtra("SENTBUD", budgetneeded);
                        intent.putExtra("SENTSIZ", sizeNeeded);
//                        reply = replyString;
                    }else if (!cityNeeded.equals("") && !locationNeeded.equals("") && sizeNeeded.equals("") && !budgetneeded.equals("")) {
                        reply = "Finding houses in city " + cityNeeded + " in locality "+ locationNeeded +" in budget " + budgetneeded;
                        needed = "city location budget";
                        toBeSent = cityNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTLOC", toBeSent);
                        intent.putExtra("SENTSIZ", budgetneeded);
//                        reply = replyString;
                    }else if (!cityNeeded.equals("") && locationNeeded.equals("") && !sizeNeeded.equals("") && !budgetneeded.equals("")) {
                        reply = "Finding houses in city " + cityNeeded + " of size "+ sizeNeeded +" in budget " + budgetneeded;
                        needed = "city budget size";
                        toBeSent = cityNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTCIT", cityNeeded);
                        intent.putExtra("SENTBUD", budgetneeded);
                        intent.putExtra("SENTSIZ", sizeNeeded);
//                        reply = replyString;
                    }else if (cityNeeded.equals("") && !locationNeeded.equals("") && sizeNeeded.equals("") && !budgetneeded.equals("")) {
                        reply = "Finding houses of size " + sizeNeeded + " at location " + locationNeeded +" in budget " + budgetneeded;
                        needed = "location budget size";
                        toBeSent = cityNeeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENTLOC", locationNeeded);
                        intent.putExtra("SENTSIZ", sizeNeeded);
                        intent.putExtra("SENTBUD", budgetneeded);
//                        reply = replyString;
                    }


                    else if (!cityNeeded.equals("") && !locationNeeded.equals("") && !sizeNeeded.equals("") && !budgetneeded.equals("")) {              //MAIN FOCUS HERE
                        reply = "Finding houses in " + cityNeeded + " locality " + locationNeeded + " of size " + sizeNeeded + " in budget " + budgetneeded;
                        needed = "all";
                        toBeSent = "all";
                        loc = locationNeeded;
                        siz = sizeNeeded;
                        bud = budgetneeded;
                        intent.putExtra("NEED", needed);
                        intent.putExtra("SENT", toBeSent);
                        intent.putExtra("CITY", cityNeeded);
                        intent.putExtra("LOC", loc);
                        intent.putExtra("SIZ", siz);
                        intent.putExtra("BUD", bud);
//                        reply = replyString;
                    }

                    //locationNeeded, sizeNeeded, budgetNeeded to be send

                    replyTV.setText(reply);
                    replyTV.setTextSize(16);
                    //replyTV.setBackgroundColor(Color.parseColor("#F0F4C3"));
                    replyTV.setGravity(Gravity.LEFT);
                    replyTV.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);
                    replyTV.setTextColor(Color.parseColor("#558B2F"));
                    linearLayout.addView(replyTV, layoutParamsBot);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Found results")
                                    .setMessage("Click to go to the results")
                                    .setPositiveButton("GO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
//                                        checkMessage(msg.toLowerCase());

                                            startActivity(intent);
                                            Toast.makeText(MainActivity.this, "start intent", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_menu_view)
                                    .show();
                        }
                    }, 2000);

                }

            }
        });


    }

    private void getHandles() {
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        msgET = (EditText) findViewById(R.id.msgET);
        btn = (Button) findViewById(R.id.sendBtn);
    }

    void createBudgetArrayList() {
        budget = new ArrayList<>();
        budget.add("5000");
        budget.add("10000");
        budget.add("15000");
        budget.add("20000");
        budget.add("upto 5000");
        budget.add("about 5000");
        budget.add("5000-10000");
        budget.add("b/w 5000-10000");
        budget.add("b/w 10000-20000");
        budget.add("10000-20000");
        budget.add("about 20000");
    }

    void createRoomArrayList() {
        roomSize = new ArrayList<>();
        roomSize.add("1bhk");
        roomSize.add("2bhk");
        roomSize.add("3bhk");
        //roomSize.add("");
    }

    void createCityArrayList() {
        cities.add("hyderabad");
        cities.add("delhi");
        cities.add("ahmedabad");
        cities.add("gurgaon");
        cities.add("bangalore");
        cities.add("mumbai");
        cities.add("pune");
        cities.add("kolkata");
        cities.add("noida");
    }

    void createLocationsArrayList() {
        locations.add("miyapur");
        locations.add("adibatla");
        locations.add("subhash");
        locations.add("sangam");
        locations.add("panchvati");
        locations.add("prahlad");
        locations.add("palam");
        locations.add("sector");
        locations.add("nelamangala");
        locations.add("sarjapur");
        locations.add("bandra");
        locations.add("malad");
        locations.add("kalyani");
        locations.add("viman");
        locations.add("rajarhat");
        locations.add("thakur");
        locations.add("eta");
    }

    void createCityHashtable() {
        city.put("hyderabad", 2060);
        city.put("delhi", 2624);
        city.put("ahmedabad", 2690);
        city.put("gurgaon", 2951);
        city.put("bangalore", 3327);
        city.put("mumbai", 4320);
        city.put("pune", 4378);
        city.put("kolkata", 6903);
        city.put("noida", 7045);
    }

    void createLocalityHashtable() {
        locality.put("miyapur", 80185);
        locality.put("adibatla", 85822);
        locality.put("subhash", 53514);
        locality.put("sangam", 78193);
        locality.put("panchvati", 54469);
        locality.put("prahlad", 84435);
        locality.put("palam", 78710);
        locality.put("sector", 86514);
        locality.put("nelamangala", 80455);
        locality.put("sarjapur", 80060);
        locality.put("bandra", 78839);
        locality.put("malad", 80084);
        locality.put("kalyani", 82343);
        locality.put("viman", 79726);
        locality.put("rajarhat", 79304);
        locality.put("thakur", 84834);
        locality.put("eta", 98493);
//        locality.put("noida", 93788);     both in city and locality
    }

}
