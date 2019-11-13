package team.exp.dimagsekhelo.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.RedeemPointsRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;
import team.exp.dimagsekhelo.WebServiceResponseObjects.UpcomingMatchesResponse;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;
import static team.exp.dimagsekhelo.Utils.Codes.MATCH_ID;

public class RedeemPointsScreen extends AppCompatActivity {


    EditText ifsc,accountNumber,accountName;
    TextView matchCaptionType,scoreRank;

    String matchId;
    String contestId;
    String matchCaption;
    String contestType;
    String ranks;
    String points;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceRoot;
    DatabaseReference databaseReferenceUpcomingMatches;
    DatabaseReference databaseReferenceContestMaster;
    DatabaseReference databaseReferenceContestUser;
    DatabaseReference databaseReferenceRedeemPoints;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_points_screen);

        matchId = getIntent().getStringExtra(MATCH_ID);
        contestId = getIntent().getStringExtra(CONTEST_ID);

        ifsc = (EditText) findViewById(R.id.bankIfscCode);
        accountNumber = (EditText) findViewById(R.id.bankAccountNumber);
        accountName = (EditText) findViewById(R.id.accountHolderName);
        matchCaptionType = (TextView) findViewById(R.id.matchCaptionContestType);
        scoreRank = (TextView)findViewById(R.id.matchScoreAndRank);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceRoot = firebaseDatabase.getReference();
        databaseReferenceUpcomingMatches = databaseReferenceRoot.child("UpcomingMatches");
        databaseReferenceContestMaster = databaseReferenceRoot.child("ContestMaster");
        databaseReferenceContestUser = databaseReferenceRoot.child("ContestUser");
        databaseReferenceRedeemPoints = databaseReferenceRoot.child("RedeemPoints");

        firebaseAuth = FirebaseAuth.getInstance();




        //Fetch the matches and the
        databaseReferenceUpcomingMatches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UpcomingMatchesResponse upcomingMatchesResponse = dataSnapshot1.getValue(UpcomingMatchesResponse.class);

                    if(upcomingMatchesResponse == null)
                        continue;

                    if(upcomingMatchesResponse.get_Id().equalsIgnoreCase(matchId))
                    {
                        matchCaption = upcomingMatchesResponse.get_Caption();
                        break;
                    }
                }
                //fetch the Caption
                fetchCaption();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void redeemPointsGoback(View view) {
        finish();
    }

    public void redeemPoints(View view) {
        String key = databaseReferenceRedeemPoints.push().getKey();

        RedeemPointsRequest redeemPointsRequest  = new RedeemPointsRequest();
        redeemPointsRequest.set_AccountHolderName(accountName.getText().toString());
        redeemPointsRequest.set_BankAccountNumber(accountNumber.getText().toString());
        redeemPointsRequest.set_ContestId(contestId);
        redeemPointsRequest.set_MatchId(matchId);
        redeemPointsRequest.set_Rank(ranks);
        redeemPointsRequest.set_Score(points);
        redeemPointsRequest.set_UserPhoneNumber(firebaseAuth.getCurrentUser().getPhoneNumber());
        redeemPointsRequest.set_IfscCode(ifsc.getText().toString());

        if(key == null)
            return;

        databaseReferenceRedeemPoints.child(key).setValue(redeemPointsRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"We will get back to you soon !!!",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void fetchCaption() {
        databaseReferenceContestMaster.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ContestMasterResponse contestMasterResponse = dataSnapshot1.getValue(ContestMasterResponse.class);

                    if(contestMasterResponse == null)
                        continue;

                    if(contestMasterResponse.get_ContestId().equalsIgnoreCase(contestId)){
                        contestType = contestMasterResponse.get_ContestType();
                        break;
                    }
                }
                fetchContestUserRequest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void fetchContestUserRequest() {

        databaseReferenceContestUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ContestUserRequest contestUserRequest = dataSnapshot1.getValue(ContestUserRequest.class);
                    if(contestUserRequest == null)
                        continue;

                    if(contestUserRequest.get_ContestId().equalsIgnoreCase(contestId) && contestUserRequest.get_UserPhoneNumber().equalsIgnoreCase(firebaseAuth.getCurrentUser().getPhoneNumber()))
                    {
                        ranks = contestUserRequest.get_Rank();
                        points = contestUserRequest.get_Points();
                        break;
                    }
                }
                //Update the
                matchCaptionType.setText(matchCaption+" "+matchCaptionType);
                scoreRank.setText("Points : "+points+",Ranks : "+ranks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });
    }
}