package team.exp.dimagsekhelo.WebServiceResponseObjects;

public class CurrentMatchPoints {

    private String _PlayerId;
    private String _PlayerName;
    private String _PlayerType;
    private String _RunsScored;
    private String _OversFaced;
    private String _BattingStatus;
    private String _MaidenOvers;
    private String _BowledOvers;
    private String _BowlingStatus;

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

    public String get_BowlingStatus() {
        return _BowlingStatus;
    }

    public void set_BowlingStatus(String _BowlingStatus) {
        this._BowlingStatus = _BowlingStatus;
    }

    @Override
    public String toString() {
        return "CurrentMatchPoints{" +
                "_PlayerId='" + _PlayerId + '\'' +
                ", _PlayerName='" + _PlayerName + '\'' +
                ", _PlayerType='" + _PlayerType + '\'' +
                ", _RunsScored='" + _RunsScored + '\'' +
                ", _OversFaced='" + _OversFaced + '\'' +
                ", _BattingStatus='" + _BattingStatus + '\'' +
                ", _MaidenOvers='" + _MaidenOvers + '\'' +
                ", _BowledOvers='" + _BowledOvers + '\'' +
                ", _BowlingStatus='" + _BowlingStatus + '\'' +
                '}';
    }
}