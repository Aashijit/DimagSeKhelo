package team.exp.dimagsekhelo.WebServiceResponseObjects;

public class ContestMasterResponse {

    private String _ContestId;
    private String _MatchId;
    private String _ContestType;
    private String _EntryFeePoints;
    private String _PrizePool;
    private String _TotalStrength;
    private String _TotalWinners;

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

    public String get_ContestType() {
        return _ContestType;
    }

    public void set_ContestType(String _ContestType) {
        this._ContestType = _ContestType;
    }

    public String get_EntryFeePoints() {
        return _EntryFeePoints;
    }

    public void set_EntryFeePoints(String _EntryFeePoints) {
        this._EntryFeePoints = _EntryFeePoints;
    }

    public String get_PrizePool() {
        return _PrizePool;
    }

    public void set_PrizePool(String _PrizePool) {
        this._PrizePool = _PrizePool;
    }

    public String get_TotalStrength() {
        return _TotalStrength;
    }

    public void set_TotalStrength(String _TotalStrength) {
        this._TotalStrength = _TotalStrength;
    }

    public String get_TotalWinners() {
        return _TotalWinners;
    }

    public void set_TotalWinners(String _TotalWinners) {
        this._TotalWinners = _TotalWinners;
    }

    @Override
    public String toString() {
        return "ContestMasterResponse{" +
                "_ContestId='" + _ContestId + '\'' +
                ", _MatchId='" + _MatchId + '\'' +
                ", _ContestType='" + _ContestType + '\'' +
                ", _EntryFeePoints='" + _EntryFeePoints + '\'' +
                ", _PrizePool='" + _PrizePool + '\'' +
                ", _TotalStrength='" + _TotalStrength + '\'' +
                ", _TotalWinners='" + _TotalWinners + '\'' +
                '}';
    }
}