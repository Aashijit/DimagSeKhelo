package team.exp.dimagsekhelo.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.CurrentMatchAdapter;
import team.exp.dimagsekhelo.CustomUIElements.PlayerAdapter;
import team.exp.dimagsekhelo.Database.BusinessLogic.StringDeserializer;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.CurrentMatchPoints;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;
import team.exp.dimagsekhelo.WebServiceResponseObjects.WicketsTaken;

public class PointChangeScreen extends AppCompatActivity {


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceCurrentMatches = database.getReference("CurrentMatches");
    private CurrentMatchAdapter currentMatchAdapter;

    private TextView textViewPointChangeScreenTextView;
    private ListView pointSystemList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_change_screen);

        textViewPointChangeScreenTextView = (TextView)  findViewById(R.id.totalPointsEarned);
        pointSystemList = (ListView) findViewById(R.id.pointSystemList);
        pointChangeStart();
    }

    private void pointChangeStart() {

        Log.d(this.getClass().getName(),"Entered Here ");

        databaseReferenceCurrentMatches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CurrentMatchPoints> currentMatchPoints = new ArrayList<>();
                Log.d(this.getClass().getName(),dataSnapshot.getChildrenCount()+"");
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    CurrentMatchPoints currentMatchPoints1 = snapshot.getValue(CurrentMatchPoints.class);
                    currentMatchPoints.add(currentMatchPoints1);
                }

                currentMatchAdapter=  new CurrentMatchAdapter(PointChangeScreen.this,currentMatchPoints, Codes.MATCH_TYPE_ODI,textViewPointChangeScreenTextView);
                pointSystemList.setAdapter(currentMatchAdapter);

              }
                //Step 2 : Update the List View


                //Step 3 : Setup the list on click adapter



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"No Contests Available !!! ", Toast.LENGTH_LONG).show();
            }
        });





    }

    public void pointsGoBack(View view) {
        finish();
    }



}
