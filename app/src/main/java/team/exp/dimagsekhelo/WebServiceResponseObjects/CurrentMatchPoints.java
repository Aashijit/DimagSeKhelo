package team.exp.dimagsekhelo.WebServiceResponseObjects;

import java.util.List;

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
    private List<WicketsTaken> wicketsTaken;
    private String _BowledOvers;
    private String _RunsGiven;
    private String _BowlingStatus;


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

    public List<WicketsTaken> getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(List<WicketsTaken> wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
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
                ", wicketsTaken=" + wicketsTaken +
                ", _BowledOvers='" + _BowledOvers + '\'' +
                ", _RunsGiven='" + _RunsGiven + '\'' +
                ", _BowlingStatus='" + _BowlingStatus + '\'' +
                '}';
    }
}