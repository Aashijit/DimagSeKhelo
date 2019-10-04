package team.exp.dimagsekhelo.WebServiceRequestObjects;

import java.util.List;

public class TeamContestRequest {

    private String _TeamId;
    private String _TeamName;
    private String _UserPhoneNumber;
    private List<PlayerTeamContest> players;
    private String _ViceCaptainPlayerId;
    private String _CaptainPlayerId;
    private String _IsDelete;

    public String get_TeamId() {
        return _TeamId;
    }

    public void set_TeamId(String _TeamId) {
        this._TeamId = _TeamId;
    }

    public String get_TeamName() {
        return _TeamName;
    }

    public void set_TeamName(String _TeamName) {
        this._TeamName = _TeamName;
    }

    public String get_UserPhoneNumber() {
        return _UserPhoneNumber;
    }

    public void set_UserPhoneNumber(String _UserPhoneNumber) {
        this._UserPhoneNumber = _UserPhoneNumber;
    }

    public List<PlayerTeamContest> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerTeamContest> players) {
        this.players = players;
    }

    public String get_ViceCaptainPlayerId() {
        return _ViceCaptainPlayerId;
    }

    public void set_ViceCaptainPlayerId(String _ViceCaptainPlayerId) {
        this._ViceCaptainPlayerId = _ViceCaptainPlayerId;
    }

    public String get_CaptainPlayerId() {
        return _CaptainPlayerId;
    }

    public void set_CaptainPlayerId(String _CaptainPlayerId) {
        this._CaptainPlayerId = _CaptainPlayerId;
    }

    public String get_IsDelete() {
        return _IsDelete;
    }

    public void set_IsDelete(String _IsDelete) {
        this._IsDelete = _IsDelete;
    }

    @Override
    public String toString() {
        return "TeamContestRequest{" +
                "_TeamId='" + _TeamId + '\'' +
                ", _TeamName='" + _TeamName + '\'' +
                ", _UserPhoneNumber='" + _UserPhoneNumber + '\'' +
                ", players=" + players +
                ", _ViceCaptainPlayerId='" + _ViceCaptainPlayerId + '\'' +
                ", _CaptainPlayerId='" + _CaptainPlayerId + '\'' +
                ", _IsDelete='" + _IsDelete + '\'' +
                '}';
    }
}