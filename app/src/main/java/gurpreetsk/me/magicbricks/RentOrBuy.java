package gurpreetsk.me.magicbricks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RentOrBuy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_or_buy);
    }

    public void RentButtonClicked(View view){

        Intent intent = new Intent(RentOrBuy.this, MainActivity.class);
        intent.putExtra("RentOrBuy", "R");
        startActivity(intent);

    }

    public void BuyButtonClicked(View view){

        Intent intent = new Intent(RentOrBuy.this, MainActivity.class);
        intent.putExtra("RentOrBuy", "S");
        startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
