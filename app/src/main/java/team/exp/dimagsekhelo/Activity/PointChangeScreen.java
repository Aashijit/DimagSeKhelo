package team.exp.dimagsekhelo.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import team.exp.dimagsekhelo.CustomUIElements.PlayerAdapter;
import team.exp.dimagsekhelo.Database.BusinessLogic.StringDeserializer;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.CurrentMatchPoints;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;
import team.exp.dimagsekhelo.WebServiceResponseObjects.WicketsTaken;

public class PointChangeScreen extends AppCompatActivity {


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceCurrentMatches = database.getReference("CurrentMatches");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_change_screen);
        pointChangeStart();
    }

    private void pointChangeStart() {

        Log.d(this.getClass().getName(),"Entered Here ");

        databaseReferenceCurrentMatches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CurrentMatchPoints> currentMatchPoints = new ArrayList<>();
                for(DataSnapshot dsp : dataSnapshot.getChildren()){


                    HashMap<String, CurrentMatchPoints> currentMatchPointsHashMap = (HashMap<String, CurrentMatchPoints>) dsp.getValue();

//                    [ , , , , wicketsTaken, , , , , , , , , ]
                    if(currentMatchPointsHashMap != null)
                    {
                        Log.d(this.getClass().getName(),"Current Match Points : "+currentMatchPointsHashMap.get("_ContestId"));

                        CurrentMatchPoints crm = new CurrentMatchPoints();
                        crm.set_ContestId(currentMatchPointsHashMap.get("_ContestId") != null ? currentMatchPointsHashMap.get("_ContestId")+"" : "");
                        crm.set_PlayerId(currentMatchPointsHashMap.get("_PlayerId")+"");
                        crm.set_PlayerName(currentMatchPointsHashMap.get("_PlayerName")+"");
                        crm.set_PlayerType(currentMatchPointsHashMap.get("_PlayerType")+"");
                        crm.set_BattingStatus(currentMatchPointsHashMap.get("_BattingStatus")+"");
                        crm.set_MaidenOvers(currentMatchPointsHashMap.get("_MaidenOvers")+"");
                        crm.set_MatchId(currentMatchPointsHashMap.get("_MatchId")+"");
                        crm.set_BowledOvers(currentMatchPointsHashMap.get("_BowledOvers")+"");
                        crm.set_RunsGiven(currentMatchPointsHashMap.get("_RunsGiven")+"");
                        crm.set_OversFaced(currentMatchPointsHashMap.get("_OversFaced")+"");
                        crm.set_BoundariesHit(currentMatchPointsHashMap.get("_BoundariesHit")+"");
                        crm.set_RunsScored(currentMatchPointsHashMap.get("_RunsScored")+"");
                        crm.set_BowlingStatus(currentMatchPointsHashMap.get("_BowlingStatus")+"");
                        crm.set_OverBoundariesHit(currentMatchPointsHashMap.get("_OverBoundariesHit")+"");


                        Log.d(this.getClass().getName(),"wickets Taken "+currentMatchPointsHashMap.get("wicketsTaken"));
                        Log.d(this.getClass().getName(),"Current Match Points Final : "+  crm);
                        Log.d(this.getClass().getName(),"wickets Taken List :  "+StringDeserializer.deserialze(currentMatchPointsHashMap.get("wicketsTaken")+""));



                    }
//                    CurrentMatchPoints currentMatchPoint = null;
//                    if(dsp.getValue() != null)
//                        currentMatchPoint = dsp.getValue(CurrentMatchPoints.class);
//                    Log.d(this.getClass().getName(),"Current Match Points : "+currentMatchPoint);


                }
                //Step 2 : Update the List View


                //Step 3 : Setup the list on click adapter

            }

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
