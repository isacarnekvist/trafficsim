public class Vehicle {
	
	private double m;	// mass (kg)
	private double v;	// velocity (m/s)
	private double a;	// acceleration (m/s^2)
	
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
		
		// Räkna här ut hur vi ska anpassa hastigheten, eller kalla på en funktion som gör det.
	}
	
	/**
	 * @return How many Joules per second this vehicle consumes at the moment.
	 */
	double getCurrentWattage() {
		double k = 0.12345; // Detta är samma som 0.5*C*p*A i formeln för vindmotståndskraft
						            // den bör kanske bero på roten ur massan, då den beror på arean A
		
		//		  	motoreffekt vindeffekt
		double Ptot = v*(m*a + k*v*v);
		
		if (Ptot > 0.0) {
			return Ptot;
		} else {
			return 0.0;
		}
	}
	
	
}
