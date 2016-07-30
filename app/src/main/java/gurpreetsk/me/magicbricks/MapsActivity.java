package gurpreetsk.me.magicbricks;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
    private static final String FETCHURL = "http://hackathon.magicbricks.com:1208/property/mobileSearch?campCode=android&page=1&ct=2624&resultPerPage=16&searchType=1&cg=R&ty=10002";

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

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                FETCHURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String resultstring = object.getString("result");
                            JSONArray resultarray = new JSONArray(resultstring);
                            for(int i = 0; i < resultarray.length() ;i++) {
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
                        }
                        catch (Exception e){
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
                headers.put( "date", "Sat, 30 Jul 2016 05:49:35 GMT");
                headers.put( "connection", "keep-alive");
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
        RequestQueue requestQueue =  Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        // Add a marker in Sydney and move the camera
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final double latm = marker.getPosition().latitude;
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        FETCHURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String resultstring = object.getString("result");
                                    JSONArray resultarray = new JSONArray(resultstring);
                                    for(int i = 0; i < resultarray.length() ;i++) {
                                        JSONObject res = resultarray.getJSONObject(i);
                                        String lat = res.getString("latLoc");
                                        double lati = Double.parseDouble(lat);
                                        if(lati == latm) {
                                            String contact = res.getString("contact");
                                            Dialog dialog = new Dialog(MapsActivity.this);
                                            dialog.setTitle("Information");
                                            dialog.setContentView(R.layout.dialog_layout);
                                            TextView text = (TextView)dialog.findViewById(R.id.textView);
                                            text.setText(contact);
                                            dialog.show();

                                        }
                                    }
                                }
                                catch (Exception e){
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
                        headers.put( "date", "Sat, 30 Jul 2016 05:49:35 GMT");
                        headers.put( "connection", "keep-alive");
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
                RequestQueue requestQueue =  Volley.newRequestQueue(MapsActivity.this);
                requestQueue.add(stringRequest);
                return false;
            }
        });

    }


}