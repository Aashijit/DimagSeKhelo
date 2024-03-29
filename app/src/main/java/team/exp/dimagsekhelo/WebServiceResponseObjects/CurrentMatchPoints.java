package team.exp.dimagsekhelo.WebServiceResponseObjects;


public class CurrentMatchPoints {

    private String _ContestId;
    private String _MatchId;
    private String _PlayerId;
    private String _PlayerName;
    private String _PlayerType;
    private String _RunsScored;
    private String _BoundariesHit;
    private String _OverBoundariesHit;
    private String _OversFaced;
    private String _BattingStatus;
    private String _MaidenOvers;
    private String _BowledOvers;
    private String _RunsGiven;
    private String _BowlingStatus;
    private String _WicketsC;
    private String _WicketsB;
    private String _WicketsCB;
    private String _WicketsR;
    private Double points;


    public String get_ContestId() {
        return _ContestId;
    }

    public void set_ContestId(String _ContestId) {
        this._ContestId = _ContestId;
    }

    public String get_MatchId() {
        return _MatchId;
    }

    public void set_MatchId(String _MatchId) {
        this._MatchId = _MatchId;
    }

    public String get_PlayerId() {
        return _PlayerId;
    }

    public void set_PlayerId(String _PlayerId) {
        this._PlayerId = _PlayerId;
    }

    public String get_PlayerName() {
        return _PlayerName;
    }

    public void set_PlayerName(String _PlayerName) {
        this._PlayerName = _PlayerName;
    }

    public String get_PlayerType() {
        return _PlayerType;
    }

    public void set_PlayerType(String _PlayerType) {
        this._PlayerType = _PlayerType;
    }

    public String get_RunsScored() {
        return _RunsScored;
    }

    public void set_RunsScored(String _RunsScored) {
        this._RunsScored = _RunsScored;
    }

    public String get_BoundariesHit() {
        return _BoundariesHit;
    }

    public void set_BoundariesHit(String _BoundariesHit) {
        this._BoundariesHit = _BoundariesHit;
    }

    public String get_OverBoundariesHit() {
        return _OverBoundariesHit;
    }

    public void set_OverBoundariesHit(String _OverBoundariesHit) {
        this._OverBoundariesHit = _OverBoundariesHit;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String get_OversFaced() {
        return _OversFaced;
    }

    public void set_OversFaced(String _OversFaced) {
        this._OversFaced = _OversFaced;
    }

    public String get_BattingStatus() {
        return _BattingStatus;
    }

    public void set_BattingStatus(String _BattingStatus) {
        this._BattingStatus = _BattingStatus;
    }

    public String get_MaidenOvers() {
        return _MaidenOvers;
    }

    public void set_MaidenOvers(String _MaidenOvers) {
        this._MaidenOvers = _MaidenOvers;
    }

    public String get_BowledOvers() {
        return _BowledOvers;
    }

    public void set_BowledOvers(String _BowledOvers) {
        this._BowledOvers = _BowledOvers;
    }

    public String get_RunsGiven() {
        return _RunsGiven;
    }

    public void set_RunsGiven(String _RunsGiven) {
        this._RunsGiven = _RunsGiven;
    }

    public String get_BowlingStatus() {
        return _BowlingStatus;
    }

    public void set_BowlingStatus(String _BowlingStatus) {
        this._BowlingStatus = _BowlingStatus;
    }

    public String get_WicketsC() {
        return _WicketsC;
    }

    public void set_WicketsC(String _WicketsC) {
        this._WicketsC = _WicketsC;
    }

    public String get_WicketsB() {
        return _WicketsB;
    }

    public void set_WicketsB(String _WicketsB) {
        this._WicketsB = _WicketsB;
    }

    public String get_WicketsCB() {
        return _WicketsCB;
    }

    public void set_WicketsCB(String _WicketsCB) {
        this._WicketsCB = _WicketsCB;
    }

    public String get_WicketsR() {
        return _WicketsR;
    }

    public void set_WicketsR(String _WicketsR) {
        this._WicketsR = _WicketsR;
    }

    @Override
    public String toString() {
        return "CurrentMatchPoints{" +
                "_ContestId='" + _ContestId + '\'' +
                ", _MatchId='" + _MatchId + '\'' +
                ", _PlayerId='" + _PlayerId + '\'' +
                ", _PlayerName='" + _PlayerName + '\'' +
                ", _PlayerType='" + _PlayerType + '\'' +
                ", _RunsScored='" + _RunsScored + '\'' +
                ", _BoundariesHit='" + _BoundariesHit + '\'' +
                ", _OverBoundariesHit='" + _OverBoundariesHit + '\'' +
                ", _OversFaced='" + _OversFaced + '\'' +
                ", _BattingStatus='" + _BattingStatus + '\'' +
                ", _MaidenOvers='" + _MaidenOvers + '\'' +
                ", _BowledOvers='" + _BowledOvers + '\'' +
                ", _RunsGiven='" + _RunsGiven + '\'' +
                ", _BowlingStatus='" + _BowlingStatus + '\'' +
                ", _WicketsC='" + _WicketsC + '\'' +
                ", _WicketsB='" + _WicketsB + '\'' +
                ", _WicketsCB='" + _WicketsCB + '\'' +
                ", _WicketsR='" + _WicketsR + '\'' +
                ", points=" + points +
                '}';
    }
}