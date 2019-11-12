package team.exp.dimagsekhelo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import team.exp.dimagsekhelo.R;

public class RedeemPointsScreen extends AppCompatActivity {


    EditText ifsc,accountNumber,accountName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_points_screen);

        ifsc = (EditText) findViewById(R.id.bankIfscCode);
        accountNumber = (EditText) findViewById(R.id.bankAccountNumber);
        accountName = (EditText) findViewById(R.id.accountHolderName);

    }

    public void redeemPointsGoback(View view) {
        finish();
    }

    public void redeemPoints(View view) {




    }
}