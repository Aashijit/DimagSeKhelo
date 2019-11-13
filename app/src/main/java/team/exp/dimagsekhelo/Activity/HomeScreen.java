package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.UpcomingMatchesListAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.TeamContestRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.UpcomingMatchesResponse;

public class HomeScreen extends AppCompatActivity implements Codes {


    //Member Variables
    private ListView listView;
    private ProgressBar progressBar;
    private String userPhoneNumber;

    List<TeamContestRequest> teamContestRequests;
    List<ContestUserRequest> contestUserRequests;

    private Button viewContests, viewTeams;

    private FirebaseAuth firebaseAuth;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceUpcomingMatches = database.getReference("UpcomingMatches");
    DatabaseReference databaseReferenceMyContests = database.getReference("ContestUser");
    DatabaseReference databaseReferenceMyTeams = database.getReference("TeamContest");


    private UpcomingMatchesListAdapter upcomingMatchesListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        listView =  (ListView) findViewById(R.id.listViewUpcomingMatches);
        progressBar = (ProgressBar) findViewById(R.id.progressBarUpcomingMatches);
        viewContests = (Button) findViewById(R.id.viewContestButton);
        viewTeams = (Button) findViewById(R.id.viewTeamButton);



        //Initialize the variables
        firebaseAuth = FirebaseAuth.getInstance();
        userPhoneNumber = firebaseAuth.getCurrentUser().getPhoneNumber();

        if(userPhoneNumber == null){
            Toast.makeText(getApplicationContext(),"Please re-login  !!!!",Toast.LENGTH_LONG).show();
            return;
        }
        //Initialize the variables



        //Fetch the upcoming matches here
        progressBar.setVisibility(View.VISIBLE);
        Log.d(this.getClass().getName(),"Progress Bar : "+progressBar.getProgress());
        fetchUpcomingMatches();
        fetchContests();
        fetchTeams();
    }

    private void fetchContests(){
        databaseReferenceMyContests.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contestUserRequests = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ContestUserRequest contestUserRequest = snapshot.getValue(ContestUserRequest.class);

                    if(contestUserRequest == null)
                        continue;

                    if(contestUserRequest.get_UserPhoneNumber().equalsIgnoreCase(userPhoneNumber))
                    {
                        contestUserRequests.add(contestUserRequest);
                        viewContests.setEnabled(true);
                    }

                }

                    if(contestUserRequests.size() > 0) {
                        makeToast(viewContests, "Joined " + contestUserRequests.size() + " Contests");
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(this.getClass().getName(),"Could not fetch contests !!!");
                return;
            }
        });
    }


    private void fetchTeams(){
        databaseReferenceMyTeams.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teamContestRequests = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TeamContestRequest teamContestRequest = snapshot.getValue(TeamContestRequest.class);

                    if(teamContestRequest == null)
                        continue;

                    if(teamContestRequest.get_UserPhoneNumber().equalsIgnoreCase(userPhoneNumber))
                    {
                        teamContestRequests.add(teamContestRequest);
                        viewTeams.setEnabled(true);
                    }

                }

                if(teamContestRequests.size() > 0)
                    makeToast(viewTeams,"Created "+teamContestRequests.size()+" Teams");


                viewTeams.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeScreen.this,MyTeamActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(this.getClass().getName(),"Could not fetch teams !!!");
                return;
            }
        });
    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(HomeScreen.this, MyProfileScreen.class);
        startActivity(intent);
    }


    private void fetchUpcomingMatches(){
        //Step 1 : Fetch the matches from the DB in a list
        Log.d(this.getClass().getName(),"Entered Here ");
        final List<UpcomingMatchesResponse> upcomingMatchesResponseList = new ArrayList<>();
        databaseReferenceUpcomingMatches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren()){

                    UpcomingMatchesResponse upcomingMatchesResponse = dsp.getValue(UpcomingMatchesResponse.class);
                    Log.d(this.getClass().getName(),"Entered Here : "+upcomingMatchesResponse);
                    upcomingMatchesResponseList.add(upcomingMatchesResponse);
                }
                //Step 2 : Update the List View
                upcomingMatchesListAdapter = new UpcomingMatchesListAdapter(HomeScreen.this,upcomingMatchesResponseList);
                listView.setAdapter(upcomingMatchesListAdapter);
                progressBar.setVisibility(View.GONE);

                //Step 3 : Setup the list on click adapter
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Transfer the control to the contest selection screen
                        Intent intent = new Intent(HomeScreen.this, ContestSelectionScreen.class);
                        intent.putExtra(MATCH_ID,upcomingMatchesResponseList.get(i).get_Id());
                        intent.putExtra(MATCH_NAME,upcomingMatchesResponseList.get(i).get_Caption());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Could not fetch the upcoming matches !!! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewContest(View view) {
        Intent intent = new Intent(HomeScreen.this, UserContestsScreen.class);
        intent.putParcelableArrayListExtra(CONTESTS, (ArrayList<? extends Parcelable>) contestUserRequests);
        startActivity(intent);
    }

    public void viewTeam(View view) {

    }


    public void makeToast(View view, String text) {

        int x = view.getLeft();
        int y = view.getTop() + 2 * view.getHeight();
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.LEFT, x-3, y);
        toast.show();
    }

    public void goToRulesPage(View view) {
        Intent intent = new Intent(HomeScreen.this,RulesScreen.class);
        startActivity(intent);
    }
}
