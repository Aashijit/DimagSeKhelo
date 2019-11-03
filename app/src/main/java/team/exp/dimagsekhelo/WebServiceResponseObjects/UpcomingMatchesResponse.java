package team.exp.dimagsekhelo.WebServiceResponseObjects;

public class UpcomingMatchesResponse {

    private String _Id;
    private String _Caption;
    private String _IsActive;
    private String _MatchTime;
    private String _TeamName1;
    private String _TeamName2;
    private String _MatchType;

    public String get_Id() {
        return _Id;
    }

    public void set_Id(String _Id) {
        this._Id = _Id;
    }

    public String get_Caption() {
        return _Caption;
    }

    public void set_Caption(String _Caption) {
        this._Caption = _Caption;
    }

    public String get_IsActive() {
        return _IsActive;
    }

    public void set_IsActive(String _IsActive) {
        this._IsActive = _IsActive;
    }

    public String get_MatchTime() {
        return _MatchTime;
    }

    public void set_MatchTime(String _MatchTime) {
        this._MatchTime = _MatchTime;
    }

    public String get_TeamName1() {
        return _TeamName1;
    }

    public void set_TeamName1(String _TeamName1) {
        this._TeamName1 = _TeamName1;
    }

    public String get_TeamName2() {
        return _TeamName2;
    }

    public void set_TeamName2(String _TeamName2) {
        this._TeamName2 = _TeamName2;
    }

    public String get_MatchType() {
        return _MatchType;
    }

    public void set_MatchType(String _MatchType) {
        this._MatchType = _MatchType;
    }

    @Override
    public String toString() {
        return "UpcomingMatchesResponse{" +
                "_Id='" + _Id + '\'' +
                ", _Caption='" + _Caption + '\'' +
                ", _IsActive='" + _IsActive + '\'' +
                ", _MatchTime='" + _MatchTime + '\'' +
                ", _TeamName1='" + _TeamName1 + '\'' +
                ", _TeamName2='" + _TeamName2 + '\'' +
                ", _MatchType='" + _MatchType + '\'' +
                '}';
    }
}