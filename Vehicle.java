public class Vehicle {
	
	private double m;	// mass (kg)
	private double v;	// velocity (m/s)
	private double a;	// acceleration (m/s^2)
	
	// This is no good!!! Better definition anyone?
	// How many meters in advance does driver brake before a resting obstacle? (m)
	private double advance;
	// What speed does driver have if possible? (m/s)
	private double aimVelocity;
	// How fast does driver want to accelerate? (m/s^2)
	private double aimAcceleration;
	
	// The car in front
	private double nextCarDistance;
	private double nextCarVelocity;
	
	/**
	 * @param mass The mass of the car (kg)
	 * @param aimVelocity The speed aimed at driving (m/s)
	 * @param aimAcceleration How fast does driver accelerate?
	 * @param advance Distance to resting obstacle that makes driver brake. (m)
	 */
	public Vehicle(double mass, double aimVelocity, double aimAcceleration, double advance) {
		if(mass <= 0 || aimVelocity <= 0 || aimAcceleration <= 0 || advance <= 0) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Notifies this vehicle of where next obstacle is and its velocity
	 * @param distance
	 * @param velocity
	 */
	public void setNextObstacleData(double distance, double velocity) {
		nextCarDistance = distance;
		nextCarVelocity = velocity;
		
		// Calculate how to adjust speed, or call function that does
	}
	
	/**
	 * @return How many Joules per second this vehicle consumes at the moment.
	 */
	double getCurrentWattage() {
		double k = 0.12345; // This is the same as 0.5*C*p*A in the formula for 
							// air resistance force.
							// should maybe depend on sqrt(mass) because of its front area
							
		// mva + kv^3 = motor effect + wind effect
		double Ptot = v*(m*a + k*v*v);
		
		if (Ptot > 0.0) {
			return Ptot;
		} else {
			return 0.0;
		}
	}
	
	
}
