package team.exp.dimagsekhelo.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.ContestMasterAdapter;
import team.exp.dimagsekhelo.CustomUIElements.MyContestAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;

public class UserContestsScreen extends AppCompatActivity implements Codes {

    private ListView listView;
    private ProgressBar progressBar;
    private MyContestAdapter myContestAdapter;


    private List<ContestUserRequest> contestUserRequests;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceContestMaster = database.getReference("ContestMaster");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contests_screen);

        listView =  (ListView) findViewById(R.id.myContestsViews);
        progressBar = (ProgressBar) findViewById(R.id.progressBarMyContests);

        contestUserRequests = getIntent().getParcelableArrayListExtra(CONTESTS);

        progressBar.setVisibility(View.VISIBLE);
        Log.d(this.getClass().getName(),"Progress Bar : "+progressBar.getProgress());

        Log.v(this.getClass().getName(),contestUserRequests+"");

        if(!contestUserRequests.isEmpty())
            fetchContests();
        else
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"No Contests Joined !!!",Toast.LENGTH_LONG).show();
        }
    }

    public void myContestsGoBack(View view) {
        finish();
    }


    private void fetchContests() {

        Log.d(this.getClass().getName(),"Entered Here ");
        final List<ContestMasterResponse> contestMasterResponses = new ArrayList<>();
        databaseReferenceContestMaster.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren()){

                    ContestMasterResponse contestMasterResponse = dsp.getValue(ContestMasterResponse.class);
                    Log.d(this.getClass().getName(),"Entered Here : "+contestMasterResponse);

                    if(contestMasterResponse != null)
                        for(ContestUserRequest contestUserRequest : contestUserRequests) {
                            if(contestUserRequest.get_ContestId().equalsIgnoreCase(contestMasterResponse.get_ContestId()))
                                contestMasterResponses.add(contestMasterResponse);
                        }
                }
                //Step 2 : Update the List View
                myContestAdapter = new MyContestAdapter(UserContestsScreen.this,contestMasterResponses,contestUserRequests);
                listView.setAdapter(myContestAdapter);
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"No Contests Available !!! ", Toast.LENGTH_LONG).show();
            }
        });
    }

}
