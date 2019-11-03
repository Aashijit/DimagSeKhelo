package team.exp.dimagsekhelo.WebServiceResponseObjects;

public class MatchStatus {

    private String _MatchId;
    private String _MatchStatus;

    public String get_MatchId() {
        return _MatchId;
    }

    public void set_MatchId(String _MatchId) {
        this._MatchId = _MatchId;
    }

    public String get_MatchStatus() {
        return _MatchStatus;
    }

    public void set_MatchStatus(String _MatchStatus) {
        this._MatchStatus = _MatchStatus;
    }

    @Override
    public String toString() {
        return "MatchStatus{" +
                "_MatchId='" + _MatchId + '\'' +
                ", _MatchStatus='" + _MatchStatus + '\'' +
                '}';
    }
}