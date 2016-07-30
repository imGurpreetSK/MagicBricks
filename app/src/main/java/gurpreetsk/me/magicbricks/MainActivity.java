package gurpreetsk.me.magicbricks;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText msgET;
    Button btn;
    TextView inputTV, replyTV;
    String msg, reply;
    LinearLayout linearLayout;

    private String TAG = "MainActivity";


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
                replyTV = new TextView(MainActivity.this);
                linearLayout.addView(replyTV, layoutParamsSender);
                replyTV.setGravity(Gravity.START);
                replyTV.setTextSize(18);
                replyTV.setTextColor(Color.parseColor("#558B2F"));         //Use #558B2F for bot reply
                replyTV.setText(msg);

            }
        });


    }

    private void getHandles(){
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        msgET = (EditText) findViewById(R.id.msgET);
        btn = (Button) findViewById(R.id.sendBtn);
    }

}
