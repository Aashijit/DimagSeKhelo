package team.exp.dimagsekhelo.DataObject;

public class Leaderboard {

    private String _PhoneNumber;
    private String _Ranks;
    private String _Score;
    private String contestId;


    public String get_PhoneNumber() {
        return _PhoneNumber;
    }

    public void set_PhoneNumber(String _PhoneNumber) {
        this._PhoneNumber = _PhoneNumber;
    }

    public String get_Ranks() {
        return _Ranks;
    }

    public void set_Ranks(String _Ranks) {
        this._Ranks = _Ranks;
    }

    public String get_Score() {
        return _Score;
    }

    public void set_Score(String _Score) {
        this._Score = _Score;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    @Override
    public String toString() {
        return "Leaderboard{" +
                "_PhoneNumber='" + _PhoneNumber + '\'' +
                ", _Ranks='" + _Ranks + '\'' +
                ", _Score='" + _Score + '\'' +
                ", contestId='" + contestId + '\'' +
                '}';
    }
}