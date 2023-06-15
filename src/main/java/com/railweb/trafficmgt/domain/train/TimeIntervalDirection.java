package com.railweb.trafficmgt.domain.train;

public enum TimeIntervalDirection {

	FORWARD(true,1),BACKWARD(false,-1); 
	
	private final boolean forward;
	private final int numerical;
	
	TimeIntervalDirection(boolean forward, int numerical){
		this.forward = forward;
		this.numerical = numerical;
	}

	public boolean isForward() {
		return forward;
	}

	public int getNumerical() {
		return numerical;
	}
	
	public TimeIntervalDirection reverse(){
		return (this == FORWARD) ? BACKWARD : FORWARD;
	}
    /**
     * converts boolean value to this enum.
     * 
     * @param forward direction
     * @return direction enum
     */
    public static TimeIntervalDirection toTimeIntervalDirection(boolean forward) {
        return forward ? FORWARD : BACKWARD;
    }

    /**
     * converts numerical value to this enum.
     * 
     * @param numerical numerical direction
     * @return direction enum
     */
    public static TimeIntervalDirection toTimeIntervalDirection(int numerical) {
        if (numerical == 0) {
            return null;
        }
        return (numerical < 0) ? BACKWARD : FORWARD;
    }
}
