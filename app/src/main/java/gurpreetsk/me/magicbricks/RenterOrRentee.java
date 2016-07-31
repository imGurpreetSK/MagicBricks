package gurpreetsk.me.magicbricks;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RenterOrRentee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_or_rentee);

//        Location location = (Location) getIntent().getExtras().get("location");
//        Toast.makeText(this, "a "+location+" a", Toast.LENGTH_SHORT).show();

    }

    public  void TenantButtonClicked(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void OwnerButtonClicked(View view){
        startActivity(new Intent(this, GetOwnerDetailsActivity.class));
        //Toast.makeText(this, "Owner clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }
}
