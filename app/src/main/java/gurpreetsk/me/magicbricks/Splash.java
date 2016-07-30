package gurpreetsk.me.magicbricks;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.Hashtable;


//activity gets user location and define basic bot learning and replies

public class Splash extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SPLASH.java";
    private Hashtable<String, String> learning = new Hashtable<>();
    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        learningDataDefine();

        displayLocation();

        startNextActivity();

    }

    private void startNextActivity() {

        Intent intent = new Intent(Splash.this, MainActivity.class);
        //intent.putExtra();
        startActivity(intent);

    }

    private void displayLocation() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(Splash.this, "Please provide location permission.", Toast.LENGTH_SHORT).show();
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            Log.v(TAG, mLastLocation.toString());
            Toast.makeText(this, latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Splash.this, "Couldn't get user location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(Splash.this, "Connection suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(Splash.this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }


    private void learningDataDefine() {

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //For custom query
                learning.put("suggest", "Please enter your query\nTips: Keep it simple and include things like location, budget, rooms, etc.");
                learning.put("find", "Please enter your query\nTips: Keep it simple and include things like location, budget, rooms, etc.");
                learning.put("locate", "Please enter your query\nTips: Keep it simple and include things like location, budget, rooms, etc.");
                learning.put("magic", "Please enter your query and wait for it \nTips: Keep it simple and include things like location, budget, rooms, etc.");


                //starting call to bot: level-0
                learning.put("hi", "Hello!  Welcome to PropertyBot. Please enter your query or select one:\n1. Location\n2. Budget\n3. No. of rooms");
                learning.put("hello", "Hello!  Welcome to PropertyBot. Please enter your query or select one:\n1. Location\n2. Budget\n3. No. of rooms");
                learning.put("/hello", "Hello!  Welcome to PropertyBot. Please enter your query or select one:\n1. Location\n2. Budget\n3. No. of rooms");
                learning.put("start", "Hello!  Welcome to PropertyBot. Please enter your query or select one:\n1. Location\n2. Budget\n3. No. of rooms");
                learning.put("/start", "Hello!  Welcome to PropertyBot. Please enter your query or select one:\n1. Location\n2. Budget\n3. No. of rooms");
                learning.put("bonjour", "Hello!  Welcome to PropertyBot. Please enter your query or select one:\n1. Location\n2. Budget\n3. No. of rooms");

                //level-1 calls
                learning.put("1", "location");
                learning.put("1.", "location");
                learning.put("location", "location");

                learning.put("2", "Choose a budget option:\na. under 5000\nb. 5000-10000\nc. 10000-15000\nd. 15000-20000");
                learning.put("2.", "Choose a budget option:\na. under 5000\nb. 5000-10000\nc. 10000-15000\nd. 15000-20000");
                learning.put("budget", "Choose a budget option:\na. under 5000\nb. 5000-10000\nc. 10000-15000\nd. 15000-20000");

                learning.put("3", "1BHK\n2BHK\n3BHK");
                learning.put("3.", "1BHK\n2BHK\n3BHK");
                learning.put("rooms", "1BHK\n2BHK\n3BHK");
                learning.put("no of rooms", "1BHK\n2BHK\n3BHK");
                learning.put("no. of rooms", "1BHK\n2BHK\n3BHK");
                learning.put("number of rooms", "1BHK\n2BHK\n3BHK");

                //level-2 calls
                learning.put("a", "Getting locations near you...");
                learning.put("b", "Getting locations near you...");
                learning.put("c", "Getting locations near you...");
                learning.put("5000", "Getting locations near you...");
                learning.put("<5000", "Getting locations near you...");
                learning.put("under 5000", "Getting locations near you...");
                learning.put("less than 5000", "Getting locations near you...");
                learning.put("5000-10000", "Getting locations near you...");
                learning.put("10000-15000", "Getting locations near you...");
                learning.put("15000-20000", "Getting locations near you...");
                learning.put("b/w 5000-10000", "Getting locations near you...");
                learning.put("b/w 10000-15000", "Getting locations near you...");
                learning.put("b/w 10000-20000", "Getting locations near you...");       //for budget details

                learning.put("1bhk", "Getting locations near you...");
                learning.put("2bhk", "Getting locations near you...");
                learning.put("3bhk", "Getting locations near you...");
                learning.put("1 bhk", "Getting locations near you...");
                learning.put("2 bhk", "Getting locations near you...");
                learning.put("3 bhk", "Getting locations near you...");          //these 3 for space details


                //whether available on rent or not
                learning.put("rent", "rent");
                learning.put("buy", "buy");

//                Toast.makeText(Splash.this, "Learned", Toast.LENGTH_SHORT).show();

            }
        });

        startNextActivity();

    }
}
