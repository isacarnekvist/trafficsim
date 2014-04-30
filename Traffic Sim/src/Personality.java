// Driving strategy
public class Personality {
    // How many meters in advance does driver brake before a resting obstacle? (m)
    double foresightDistance;
    // What speed does driver have if possible? (m/s)
    double aimVelocity;
    // How fast does driver want to accelerate? (m/s^2)
    double aimAcceleration;

    /**
     * @param aimVelocity The speed aimed at driving (m/s)
     * @param aimAcceleration How fast does driver accelerate?
     * @param advance Distance to resting obstacle that makes driver brake. (m)
     */
    public Personality(double aimVelocity, double aimAcceleration, double foresightDistance) {
        if (aimVelocity <= 0 || aimAcceleration <= 0 || foresightDistance <= 0) {
            throw new IllegalArgumentException();
        }

        this.aimVelocity = aimVelocity;
        this.aimAcceleration = aimAcceleration;
        this.foresightDistance = foresightDistance;
    }
    
    public double getWantedAcceleration(double dist, double vel, double acc, double myVel) {
    	if (dist > 10) {
    		return 2;
    	} else {
    		return -60/(Math.pow(dist, 4));
    	}
    }
}
