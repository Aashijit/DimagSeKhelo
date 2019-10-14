package team.exp.dimagsekhelo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import team.exp.dimagsekhelo.R;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;

public class PaymentScreen extends AppCompatActivity implements PaymentResultListener {

    String amt;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        textView = findViewById(R.id.paymentScreenText);
        amt= getIntent().getStringExtra("amount");
        textView.setText("You are about to pay "+getString(R.string.Rs)+amt+"/- to join the contest, only then you will be allowed to create team.");

    }

    public void payAndJoinContest(View view) {
            final Checkout checkout = new Checkout();
            final Activity activity = this;
            try {

                JSONObject options = new JSONObject();
                options.put("name", "DSK Pvt. Ltd.");
                options.put("description", "Entry Fee");
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
                options.put("currency", "INR");

                String payment = amt;

                double total = Double.parseDouble(payment);
                total = total * 100;
                options.put("amount", total);

                JSONObject preFill = new JSONObject();
                preFill.put("email", "dimagSeKhelo2019@gmail.com");
                preFill.put("contact", "6285925549");

                options.put("prefill", preFill);

                checkout.open(activity, options);
            } catch(Exception e) {
                Log.e("Razorpay Error", "Error in starting Razorpay Checkout", e);
            }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplicationContext(),"Payment received !!!!" ,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(PaymentScreen.this, PlayerSelectionScreen.class);
        intent.putExtra(CONTEST_ID, getIntent().getStringExtra(CONTEST_ID));
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext(),"Sorry we did not receive any payments !!!",Toast.LENGTH_LONG).show();
    }

}