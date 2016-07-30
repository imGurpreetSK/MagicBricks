package gurpreetsk.me.magicbricks;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f));*/

        try {
            String intentResult = getIntent().getExtras().getString("NEED"); //TODO: ADD AND CHANGE KEYS HERE
            System.out.println("ANDROID"+intentResult);

            if (intentResult.equals("nothing")) {
                url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=2624&resultPerPage=16&searchType=1&cg=R&ty=10002";
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
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

            } else if (intentResult.equals("city")) {
                String city = getIntent().getExtras().getString("SENT");
                System.out.println("DAMAN"+city);
                url="http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&resultPerPage=16&searchType=1&cg=R&ty=10002";
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url  ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String resultstring = object.getString("result");
                                    JSONArray resultarray = new JSONArray(resultstring);
                                    for (int i = 0; i < resultarray.length(); i++) {
                                        JSONObject res = resultarray.getJSONObject(i);
                                        if (res.has("latLoc")) {
                                            String lat = res.getString("latLoc");
                                            String lon = res.getString("longLoc");
                                            String contact = res.getString("contact");
                                            double lati = Double.parseDouble(lat);
                                            double longi = Double.parseDouble(lon);
                                            LatLng sydney = new LatLng(lati, longi);
                                            mMap.addMarker(new MarkerOptions().position(sydney).title(contact));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

            } else if (intentResult.equals("budget")) {
                String bgmx = getIntent().getExtras().getString("SENT");
                int budgetsent = Integer.parseInt(bgmx);
                int budgetminus = budgetsent - 5000;
                String bgmn = String.valueOf(budgetminus);
                url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=7045&resultPerPage=16&searchType=1&cg=R&ty=10002&bgmn=" + bgmn + "&bgmx=" + bgmx;
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url  ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
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

            } else if (intentResult.equals("size")) {
                String size = getIntent().getExtras().getString("SENT");
                System.out.println(size);
                String sub = size.substring(0, 1);
                System.out.println(sub);
                url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=7045&resultPerPage=16&searchType=1&cg=R&ty=10002&bd=" + sub;
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
                                        String lat = res.getString("latLoc");
                                        String lon = res.getString("longLoc");
                                        String contact = res.getString("contact");
                                        double lati = Double.parseDouble(lat);
                                        double longi = Double.parseDouble(lon);
                                        LatLng sydney = new LatLng(lati, longi);
                                        mMap.addMarker(new MarkerOptions().position(sydney).title(contact));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

            } else if (intentResult.equals("city and location")) {
                String city = getIntent().getExtras().getString("BOTcity");
                System.out.println(city);
                String loc = getIntent().getExtras().getString("SENTLOC");
                System.out.println(loc);
                url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&lt=" + loc + "&resultPerPage=16&searchType=1&cg=R&ty=10002";
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
                                        String lat = res.getString("latLoc");
                                        String lon = res.getString("longLoc");
                                        String contact = res.getString("contact");
                                        double lati = Double.parseDouble(lat);
                                        double longi = Double.parseDouble(lon);
                                        LatLng sydney = new LatLng(lati, longi);
                                        mMap.addMarker(new MarkerOptions().position(sydney).title(contact));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
                                        String lat = res.getString("latLoc");
                                        String lon = res.getString("longLoc");
                                        String contact = res.getString("contact");
                                        double lati = Double.parseDouble(lat);
                                        double longi = Double.parseDouble(lon);
                                        LatLng sydney = new LatLng(lati, longi);
                                        mMap.addMarker(new MarkerOptions().position(sydney).title(contact));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

            } else if (intentResult.equals("city and size")) {
                String city = getIntent().getExtras().getString("BOTcity");
                System.out.println(city);
                String size = getIntent().getExtras().getString("SENTSIZ");
                System.out.println(size);
                String sub = size.substring(0, 1);
                System.out.println(sub);
                url = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&resultPerPage=16&searchType=1&cg=R&ty=10002&bd=" + sub;
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
                                        String lat = res.getString("latLoc");
                                        String lon = res.getString("longLoc");
                                        String contact = res.getString("contact");
                                        double lati = Double.parseDouble(lat);
                                        double longi = Double.parseDouble(lon);
                                        LatLng sydney = new LatLng(lati, longi);
                                        mMap.addMarker(new MarkerOptions().position(sydney).title(contact));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

            } else if (intentResult.equals("city and budget")) {
                String city = getIntent().getExtras().getString("BOTcity");
                System.out.println(city);
                String bgmx = getIntent().getExtras().getString("SENTBUD");
                int budgetsent = Integer.parseInt(bgmx);
                int budgetminus = budgetsent - 5000;
                String bgmn = String.valueOf(budgetminus);
                url =  "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=" + city + "&resultPerPage=16&searchType=1&cg=R&ty=10002&bgmn=" + bgmn + "&bgmx=" + bgmx;
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
                                        String lat = res.getString("latLoc");
                                        String lon = res.getString("longLoc");
                                        String contact = res.getString("contact");
                                        double lati = Double.parseDouble(lat);
                                        double longi = Double.parseDouble(lon);
                                        LatLng sydney = new LatLng(lati, longi);
                                        mMap.addMarker(new MarkerOptions().position(sydney).title(contact));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
                                        String lat = res.getString("latLoc");
                                        String lon = res.getString("longLoc");
                                        String contact = res.getString("contact");
                                        double lati = Double.parseDouble(lat);
                                        double longi = Double.parseDouble(lon);
                                        LatLng sydney = new LatLng(lati, longi);
                                        mMap.addMarker(new MarkerOptions().position(sydney).title(contact));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

            // Add a marker in Sydney and move the camera
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    final String name = marker.getTitle();
                    String cityDaman = "7045";
                    cityDaman = getIntent().getExtras().getString("BOTcity");
                    System.out.println("Gurpreet"+cityDaman);
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
//                                            double longi = Double.parseDouble(lon);
                                            if (cont.equals(name)) {
                                                Toast.makeText(MapsActivity.this, "Marker clicked", Toast.LENGTH_LONG).show();
                                                String contact = res.getString("contact");
                                                String price = res.getString("price");
                                                String bathroom = res.getString("bathroom");
                                                String city = res.getString("city");
                                                String covArea = res.getString("covArea");
                                                String ageOfConstruction = res.getString("ageOfConstruction");
                                                String transType = res.getString("transType");
                                                String mobile = res.getString("mobile");
                                                String address = res.getString("address");
                                                String locality = res.getString("locality");
                                                Dialog dialog = new Dialog(MapsActivity.this);
                                                dialog.setTitle("Information");
                                                dialog.setContentView(R.layout.dialog_layout);
                                                TextView text = (TextView) dialog.findViewById(R.id.textView);
                                                text.setText(contact + "\n" + price + "\n" + bathroom + "\n" + city + "\n" + covArea + "\n" + ageOfConstruction + "\n" + transType + "\n" + mobile + "\n" + address + "\n" + locality);
                                                dialog.show();

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

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Nothing passed in intent", Toast.LENGTH_SHORT).show();
        }
    }

}