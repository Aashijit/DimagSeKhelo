# Database of Dimag Se Khelo

The database of Dimag Se Khelo is present at [Firebase-DimageSeKhelo](https://dimagsekhelo-df5c0.firebaseio.com/)

## JSON Header Name : ContestMaster
>This table holds the Contest Master for every contest. They will be against a particular match. One particular match may have more >than one contests.

### Column Names : 
<ol>
<li>_ContestId:   The unique identifier of a contest </li>
<li>_ContestType:  The type of the contest</li>
<li>_EntryFeePoints:  The entry fee points</li>
<li>_MatchId:  The match id for which this contest has been created</li>
<li>_PrizePool:  The total prize pool</li>
<li>_TotalStrength:  The total strength</li>
<li>_TotalWinners: The total winners</li>
</ol>

---

## JSON Header Name : UpcomingMatches
>This contains all the upcoming matches

### Column Names : 
<ol>
<li>_Caption: The Caption of the match describing the match </li>
<li>_Id:  The unique identifier</li>
<li>_IsActive:  Flag to determine if the match is active</li>
<li>_MatchTime:  The timestamp of the match</li>
<li>_MatchType: The type of the match</li>
<li>_TeamName1: The name of the first team</li>
<li>_TeamName2: The name of the second team</li>
</ol>

---


## JSON Header Name : UserProfile
>This contains all the upcoming matches

### Column Names : 
<ol>
<li>_EmailId: The email id of the user </li>
<li>_HashedPassword: The hashed password of the user</li>
<li>_MobileNumber: The otp verified mobile number of the user</li>
<li>_Salt: The salt of the hashed passwords</li>
</ol>

---


## JSON Header Name : PlayerMasterContest
>This contains the players playing in a particular match (with an unique Match Id)

### Column Names : 
<ol>
<li>_MatchId: The match id of the match </li>
<li>_PlayerCredit: The credit of the player</li>
<li>_PlayerId: The unique player identifier</li>
<li>_PlayerName: The name of the player</li>
<li>_PlayerPictureUrl: The url of the player picture</li>
<li>_PlayerType: The type of the player</li>
</ol>

---



## JSON Header Name : TeamContest
>The mapping between User Phone Number **Unique Identifier** and Team Id **Unique >Identifier** for Team

### Column Names : 
<ol>
<li>_CaptainPlayerId: The player id of the captain </li>
<li>_IsDelete: If the team has been deleted</li>
<li>_TeamId: The team id of the team</li>
<li>_TeamName: The name of the team</li>
<li>_UserPhoneNumber: The unique identifier of the player</li>
<li>_ViceCaptainPlayerId: The player id of the vice captain</li>
<li>players: List of player ids of the team other than the captain and vice captain</li>
</ol>

---


## JSON Header Name : ContestUser
>The mapping between User Phone Number **Unique Identifier** and Team Id **Unique >Identifier** for Team and **Contest Id** for Contest, it also carries the Points and Rank 
>of the user in the particular contest

### Column Names : 
<ol>
<li>_ContestId: Unique Identifier </li>
<li>_ContestJoinTimeStamp: Contest Joining timestamp</li>
<li>_Points: The points earned in the contest</li>
<li>_Rank: The rank of the player in the contest</li>
<li>_TeamId: The team id using which he has joined</li>
<li>_UserPhoneNumber: User phone number of the user who has joined the contest</li>
</ol>

---

## JSON Header Name : MatchStatus
>The status of a particular match

### Column Names : 
<ol>
<li>_MatchId: The unique identifier of the match</li>
<li>_MatchStatus: The current status of the match</li>
</ol>

---



## JSON Header Name : RedeemPoints
>To redeem points, send a request to the system

### Column Names : 
<ol>
<li>_AccountHolderName: The name of the account holder</li>
<li>_BankAccountNumber: The bank account number</li>
<li>_ContestId: The Contest Id of the particular contest</li>
<li>_IfscCode: The IFSC code of the bank</li>
<li>_MatchId: The match id of the match</li>
<li>_Rank: The Rank of the user</li>
<li>_Score: The Score of the user</li>
<li>_UserPhoneNumber: The phone number of the user</li>
</ol>

---



## JSON Header Name : CurrentMatches
>Running current match score board

### Column Names : 
<ol>
<li>_BattingStatus: Batting status of the player</li>
<li>_BoundariesHit: Boundaries hit by the playee</li>
<li>_BowledOvers: Over bowled by the player</li>
<li>_BowlingStatus: Bowling status of the player</li>
<li>_ContestId: Contest Id of the match</li>
<li>_MaidenOvers: Maiden overs of the player</li>
<li>_MatchId: Match Id of the match</li>
<li>_OverBoundariesHit: Over-boundaries hit by the player</li>
<li>_OversFaced: Over faced by the player</li>
<li>_PlayerId: The player id</li>
<li>_PlayerName: The name of the player</li>
<li>_PlayerType: The type of the player</li>
<li>_RunsGiven: The runs given by the player</li>
<li>_RunsScored: The runs scored by the player</li>
<li>_WicketsB: Bowled wickets </li>
<li>_WicketsC: Caught wickets</li>
<li>_WicketsCB: Caught & Bowled wickets</li>
<li>_WicketsR: Run out wickets</li>
</ol>

---