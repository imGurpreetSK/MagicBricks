package gurpreetsk.me.magicbricks;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String url;
    private String contact;
    private String mobile;
    private String price;
    private String bathroom;
    private String covArea;
    private String ageOfConstruction;
    private String city;
    private String locality;
    private String address;
    private String transType;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

      /*  Location userloaction = Splash.tellLocation();
        double userlon = userloaction.getLongitude();
        double userlat = userloaction.getLatitude();
        LatLng sydney = new LatLng(userlat, userlon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11.0f));*/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras.containsKey("NEED")) {
            try {
                String intentResult = getIntent().getExtras().getString("NEED"); //TODO: ADD AND CHANGE KEYS HERE
                System.out.println("ANDROID" + intentResult);

                if (intentResult.equals("nothing")) {
                    url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=2624&resultPerPage=16&searchType=1&cg=R&ty=10002";
                    data(url);

                } else if (intentResult.equals("city")) {
                    String city = getIntent().getExtras().getString("SENT");
                    System.out.println("DAMAN" + city);
                    url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&resultPerPage=16&searchType=1&cg=R&ty=10002";
                    data(url);

                } else if (intentResult.equals("budget")) {
                    String bgmx = getIntent().getExtras().getString("SENT");
                    int budgetsent = Integer.parseInt(bgmx);
                    int budgetminus = budgetsent - 5000;
                    String bgmn = String.valueOf(budgetminus);
                    url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=7045&resultPerPage=16&searchType=1&cg=R&ty=10002&bgmn=" + bgmn + "&bgmx=" + bgmx;
                    data(url);

                } else if (intentResult.equals("size")) {
                    String size = getIntent().getExtras().getString("SENT");
                    System.out.println(size);
                    String sub = size.substring(0, 1);
                    System.out.println(sub);
                    url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=7045&resultPerPage=16&searchType=1&cg=R&ty=10002&bd=" + sub;
                    data(url);

                } else if (intentResult.equals("city and location")) {
                    String city = getIntent().getExtras().getString("BOTcity");
                    System.out.println(city);
                    String loc = getIntent().getExtras().getString("SENTLOC");
                    System.out.println(loc);
                    url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&lt=" + loc + "&resultPerPage=16&searchType=1&cg=R&ty=10002";
                    data(url);

                } else if (intentResult.equals("size and budget")) {
                    String bgmx = getIntent().getExtras().getString("SENTBUD");
                    int budgetsent = Integer.parseInt(bgmx);
                    int budgetminus = budgetsent - 5000;
                    String bgmn = String.valueOf(budgetminus);
                    String size = getIntent().getExtras().getString("SENTSIZ");
                    System.out.println(size);
                    String sub = size.substring(0, 1);
                    System.out.println(sub);
                    url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=7045&resultPerPage=16&searchType=1&cg=R&ty=10002&bd=" + sub + "&bgmn=" + bgmn + "&bgmx=" + bgmx;
                    data(url);

                } else if (intentResult.equals("city and size")) {
                    String city = getIntent().getExtras().getString("BOTcity");
                    System.out.println(city);
                    String size = getIntent().getExtras().getString("SENTSIZ");
                    System.out.println(size);
                    String sub = size.substring(0, 1);
                    System.out.println(sub);
                    url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&resultPerPage=16&searchType=1&cg=R&ty=10002&bd=" + sub;
                    data(url);

                } else if (intentResult.equals("city and budget")) {
                    String city = getIntent().getExtras().getString("BOTcity");
                    System.out.println(city);
                    String bgmx = getIntent().getExtras().getString("SENTBUD");
                    int budgetsent = Integer.parseInt(bgmx);
                    int budgetminus = budgetsent - 5000;
                    String bgmn = String.valueOf(budgetminus);
                    url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&resultPerPage=16&searchType=1&cg=R&ty=10002&bgmn=" + bgmn + "&bgmx=" + bgmx;
                    data(url);

                } else if (intentResult.equals("city budget size")) {
                    String city = getIntent().getExtras().getString("BOTcity");
                    System.out.println(city);
                    String bgmx = getIntent().getExtras().getString("SENTBUD");
                    int budgetsent = Integer.parseInt(bgmx);
                    int budgetminus = budgetsent - 5000;
                    String bgmn = String.valueOf(budgetminus);
                    String size = getIntent().getExtras().getString("SENTSIZ");
                    System.out.println(size);
                    String sub = size.substring(0, 1);
                    System.out.println(sub);
                    url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&resultPerPage=16&searchType=1&cg=R&ty=10002&bd=" + sub + "&bgmn=" + bgmn + "&bgmx=" + bgmx;
                    data(url);

                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Nothing passed in intent", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            String what = getIntent().getExtras().getString("what");
            if(what.equals("location")){
                String city = getIntent().getExtras().getString("BOTcity");
                System.out.println("DAMAN" + city);
                url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&resultPerPage=16&searchType=1&cg=R&ty=10002";
                data(url);

            }
            else if(what.equals("budget")){
                String bgmx = getIntent().getExtras().getString("BOTmsg");
                int budgetsent = Integer.parseInt(bgmx);
                int budgetminus = budgetsent - 5000;
                String bgmn = String.valueOf(budgetminus);
                String city = getIntent().getExtras().getString("BOTcity");
                System.out.println("DAMAN" + city);
                url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" +city+ "&resultPerPage=16&searchType=1&cg=R&ty=10002&bgmn=" + bgmn + "&bgmx=" + bgmx;
                data(url);
            }
            else if(what.equals("bhk")){
                String size = getIntent().getExtras().getString("BOTmsg");
                System.out.println(size);
                String sub = size.substring(0, 1);
                System.out.println(sub);
                String city = getIntent().getExtras().getString("BOTcity");
                System.out.println("DAMAN" + city);
                url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" +city+ "&resultPerPage=16&searchType=1&cg=R&ty=10002&bd=" + sub;
                data(url);
            }
        }

                // Add a marker in Sydney and move the camera
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        final String name = marker.getTitle();
                        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject object = new JSONObject(response);
                                            String resultstring = object.getString("result");
                                            JSONArray resultarray = new JSONArray(resultstring);
                                            for (int i = 0; i < resultarray.length(); i++) {
                                                JSONObject res = resultarray.getJSONObject(i);
                                                String cont = res.getString("contact");
                                                if (cont.equals(name)) {
                                                    Toast.makeText(MapsActivity.this, "Marker clicked", Toast.LENGTH_LONG).show();
                                                    contact = res.getString("contact");
                                                    price = res.getString("price");
                                                    bathroom = res.getString("bathroom");
                                                    city = res.getString("city");
                                                    covArea = res.getString("covArea");
                                                    ageOfConstruction = res.getString("ageOfConstruction");
                                                    transType = res.getString("transType");
                                                    mobile = res.getString("mobile");
                                                    address = res.getString("address");
                                                    locality = res.getString("locality");
                                                    id = res.getString("id");
                                                    dialog(id);

                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("date", "Sat, 30 Jul 2016 05:49:35 GMT");
                                headers.put("connection", "keep-alive");
                                headers.put("transfer-encoding", "chunked");
                                headers.put("x-application-context", "mbapis:1208");
                                headers.put("content-type", "application/json; charset=UTF-8");
                                return headers;
                            }

                            @Override
                            public Request.Priority getPriority() {
                                return Request.Priority.IMMEDIATE;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
                        requestQueue.add(stringRequest);
                        return false;
                    }
                });
    }

    public void dialog(final String id){
        final Dialog dialog=new Dialog(MapsActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_layout);
        TextView textname = (TextView) dialog.findViewById(R.id.name);
        TextView textmob = (TextView) dialog.findViewById(R.id.mobile);
        TextView textprice = (TextView) dialog.findViewById(R.id.price);
        TextView textbath = (TextView) dialog.findViewById(R.id.bathroom);
        TextView textcov = (TextView) dialog.findViewById(R.id.covArea);
        TextView textage = (TextView) dialog.findViewById(R.id.age);
        TextView texttrans = (TextView) dialog.findViewById(R.id.transtype);
        TextView textcity = (TextView) dialog.findViewById(R.id.city);
        TextView textloc = (TextView) dialog.findViewById(R.id.locality);
        TextView textadd = (TextView) dialog.findViewById(R.id.add);
        textname.setText("Name: "+contact);
        textmob.setText("Mobile: "+mobile);
        textprice.setText(Html.fromHtml("<b>Price: </b>"+price));
        textbath.setText(Html.fromHtml("<b>No. of bedrooms: </b>"+bathroom));
        textcov.setText(Html.fromHtml("<b>Area covered: </b>"+covArea));
        textage.setText(Html.fromHtml("<b>Construction age: </b>"+ageOfConstruction));
        texttrans.setText(Html.fromHtml("<b>Transaction type: </b>"+transType));
        textcity.setText(Html.fromHtml("<b>City: </b>"+city));
        textloc.setText(Html.fromHtml("<b>Locality: </b>"+locality));
        textadd.setText(Html.fromHtml("<b>Address: </b>"+address));
        final TextView textview = (TextView) dialog.findViewById(R.id.textView);
        final TextView textpropertyType = (TextView) dialog.findViewById(R.id.propertyType);
        final TextView textposted = (TextView) dialog.findViewById(R.id.posted);
        final TextView textlocNm = (TextView) dialog.findViewById(R.id.locNm);
        final TextView textdesc = (TextView) dialog.findViewById(R.id.desc);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview.setText("HIDE MORE");
                LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.moreview);
                if (linearLayout.getVisibility() == View.GONE) {
                    linearLayout.setVisibility(View.VISIBLE);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                            "http://hackathon.magicbricks.com:1208/getPropertyDetails?campCode=android&id=" + id,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject res = new JSONObject(response);
                                        String propertyType = res.getString("propertyType");
                                        String posted = res.getString("posted");
                                        String locNm = res.getString("locNm");
                                        String desc = res.getString("desc");
                                        textpropertyType.setText(Html.fromHtml("<b>Property type: </b>"+propertyType));
                                        textposted.setText(Html.fromHtml("<b>Posted on: </b>"+posted));
                                        textlocNm.setText(Html.fromHtml("<b>Extension: </b>"+locNm));
                                        textdesc.setText(Html.fromHtml("<b>Description: </b>"+desc));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("date", "Sat, 30 Jul 2016 05:49:35 GMT");
                            headers.put("connection", "keep-alive");
                            headers.put("transfer-encoding", "chunked");
                            headers.put("x-application-context", "mbapis:1208");
                            headers.put("content-type", "application/json; charset=UTF-8");
                            return headers;
                        }

                        @Override
                        public Request.Priority getPriority() {
                            return Request.Priority.IMMEDIATE;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
                    requestQueue.add(stringRequest);
                }
                else{
                    linearLayout.setVisibility(View.GONE);
                    textview.setText("VIEW MORE");
                }
            }
        });

        dialog.show();
    }

    public void data(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(!object.has("result")){
                             Toast.makeText(MapsActivity.this,"No match found",Toast.LENGTH_LONG).show();
                            }
                            else{
                            String resultstring = object.getString("result");
                            JSONArray resultarray = new JSONArray(resultstring);
                            for (int i = 0; i < resultarray.length(); i++) {
                                JSONObject res = resultarray.getJSONObject(i);
                                String lat = res.getString("latLoc");
                                String lon = res.getString("longLoc");
                                String contact = res.getString("contact");
                                double lati = Double.parseDouble(lat);
                                double longi = Double.parseDouble(lon);
                                LatLng sydney = new LatLng(lati, longi);
                                mMap.addMarker(new MarkerOptions().position(sydney).title(contact));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11.0f));
                            }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("date", "Sat, 30 Jul 2016 05:49:35 GMT");
                headers.put("connection", "keep-alive");
                headers.put("transfer-encoding", "chunked");
                headers.put("x-application-context", "mbapis:1208");
                headers.put("content-type", "application/json; charset=UTF-8");
                return headers;
            }

            @Override
            public Request.Priority getPriority() {
                return Request.Priority.IMMEDIATE;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
