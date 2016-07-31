package gurpreetsk.me.magicbricks;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import gurpreetsk.me.magicbricks.Data;
import gurpreetsk.me.magicbricks.R;
import gurpreetsk.me.magicbricks.RenterOrRentee;
import gurpreetsk.me.magicbricks.Splash;

public class GetOwnerDetailsActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    MaterialEditText name, email, rent, localityET;
    EditText roomsET;
    Switch sharing;
    String ownerName, ownerEmail, locality;
    int noOfRooms = 0, expectedRent, budget = 5000;
    boolean isSharing ;
//    String pincode = "201301";
    Double lon, lat;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefUserData = database.getReference("Data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_owner_details);

        getHandles();

        sharing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isSharing = b;
            }
        });

        Location loc = Splash.tellLocation();
        lat = loc.getLatitude();
        lon = loc.getLongitude();

    }

    private void getHandles() {

        name = (MaterialEditText) findViewById(R.id.nameET);
        email = (MaterialEditText) findViewById(R.id.emailET);
        localityET = (MaterialEditText) findViewById(R.id.localityET);
        rent = (MaterialEditText) findViewById(R.id.expectedRent);
        sharing = (Switch) findViewById(R.id.sharingSwitch);
        roomsET = (EditText) findViewById(R.id.roomsET);

    }

    private void getContent() {

        ownerName = name.getText().toString();
        ownerEmail = email.getText().toString();
        expectedRent = Integer.parseInt(rent.getText().toString());
        locality = localityET.getText().toString();
        noOfRooms = Integer.parseInt(roomsET.getText().toString());

        /*Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList != null && addressList.size() > 0) {
//                    Address address = addressList.get(0);
                pincode = addressList.get(0).getPostalCode();
                Log.v(TAG, pincode);
                Toast.makeText(this, pincode, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable connect to Geocoder", e);
        }*/

    }

    public void Submit(View view) {

        getContent();
        if (!ownerName.equals("") && !ownerEmail.equals("") && noOfRooms != 0 && !locality.equals("")) {
            Data data = new Data(ownerName, lat, lon, ownerEmail, noOfRooms, isSharing, expectedRent, locality);
            myRefUserData.push().setValue(data);
            new AlertDialog.Builder(GetOwnerDetailsActivity.this)
                    .setTitle("Success")
                    .setMessage("Submitted your data")
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GetOwnerDetailsActivity.this, RenterOrRentee.class);
                            //intent.putExtra();
                            startActivity(intent);
//                            Toast.makeText(GetOwnerDetailsActivity.this, "start intent", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //.setIcon(android.R.drawable.ic_menu_view)
                    .show();
        } else
            Toast.makeText(this, "Fill all information", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
