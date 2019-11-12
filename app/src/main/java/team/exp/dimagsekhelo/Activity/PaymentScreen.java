package team.exp.dimagsekhelo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceRequestObjects.PaymentRequest;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;
import static team.exp.dimagsekhelo.Utils.Codes.MATCH_ID;

public class PaymentScreen extends AppCompatActivity implements PaymentResultListener {

    String amt;
    TextView textView;


    String contestId;
    String matchId;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceRoot = firebaseDatabase.getReference();
    DatabaseReference databaseReferencePayment = databaseReferenceRoot.child("Payments");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        textView = findViewById(R.id.paymentScreenText);
        amt= getIntent().getStringExtra("amount");

        contestId = getIntent().getStringExtra(CONTEST_ID);
        matchId = getIntent().getStringExtra(MATCH_ID);

        firebaseAuth = FirebaseAuth.getInstance();

        textView.setText("You are about to pay "+getString(R.string.Rs)+amt+"/- to join the contest, only then you will be allowed to create team.");

    }

    public void payAndJoinContest(View view) {

        String payment = amt;
        double total = Double.parseDouble(payment);
        total = total * 100;


        //Call the Razorpay Order API then use the order Id to make the payment
//        try {
//            JSONObject orderRequest = new JSONObject();
//            orderRequest.put("amount", total);
//            orderRequest.put("currency", "INR");
//            orderRequest.put("receipt", "order_rcptid_11");
//            orderRequest.put("payment_capture", true);
//
//            Order order = razorpay.Orders.create(orderRequest);
//
//        }catch (Exception e)
//        {
//            Log.e("Payment Screen",e.getMessage());
//        }

        final Checkout checkout = new Checkout();
            final Activity activity = this;
            try {

                JSONObject options = new JSONObject();
                options.put("name", "DSK Pvt. Ltd.");
                options.put("description", "Entry Fee");
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
                options.put("currency", "INR");

//                String payment = amt;
//
//                double total = Double.parseDouble(payment);
//                total = total * 100;
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


        //Add the information to the screen
        String key = databaseReferencePayment.push().getKey();


        if(key == null)
            return;

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.set_Amount(amt);
        paymentRequest.set_ContestId(contestId);
        paymentRequest.set_OrderId("");
        paymentRequest.set_UserPhoneNumber(firebaseAuth.getCurrentUser().getPhoneNumber());
        paymentRequest.set_MatchId(matchId);

        databaseReferencePayment.child(key).setValue(paymentRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Contest Joining Successful !!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PaymentScreen.this, HomeScreen.class);
                    //      intent.putExtra(CONTEST_ID, getIntent().getStringExtra(CONTEST_ID));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext(),"Sorry we did not receive any payments !!!",Toast.LENGTH_LONG).show();
    }

}