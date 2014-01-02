package network.ping.tester;

/**
 * PingStatistic is a simple class designed to hold information about the result
 * of attempted ping requests.
 * @author burke9077
 */
class PingStatistic {
    private boolean b_success;
    private long l_beginningTime;
    private long l_endTime;
    
    public PingStatistic(boolean _success, long _beginningTime, long _endTime) {
        b_success = _success;
        l_beginningTime = _beginningTime;
        l_endTime = _endTime;
    }
    
    // Setup variable getters
    public boolean getSuccess() {
        return b_success;
    }
    public long getBeginningTime() {
        return l_beginningTime;
    }
    public long getEndTime() {
        return l_endTime;
    }
}
