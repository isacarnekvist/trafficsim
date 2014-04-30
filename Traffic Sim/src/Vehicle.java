public class Vehicle {
	final double ppm = 10; // Pixels per meter, also set in GTA.java (not very neat, I know)
    double mass;      // (kg)
    double pos = 0;   // (m)
    double vel = 0;   // (m/s)
    double accel = 0; // (m/s^2)
    Personality personality;
    private Sprite sprite;
    
    // Next car data
    private double nextDist; // (m)
    private double nextVel;  // (m/s)
    private double nextAcc;  // (m/s^2)

    /**
     * Constructor
     * @param vehicleType
     * @param pos
     * @param personality
     */
    public Vehicle(int vehicleType, double pos, Personality personality) {
        this.pos = pos;
        this.personality = personality;
        switch (vehicleType) {
        case 0:
        	sprite = new Sprite("res/bus.png", 39, 120);
        	mass = 5000;
        	break;
        case 1:
        	sprite = new Sprite("res/mc.png", 19, 46);
        	mass = 200;
        	break;
        case 2:
        	sprite = new Sprite("res/police.png", 33, 64);
        	mass = 1000;
        	break;
		case 3:
        	sprite = new Sprite("res/small_truck.png", 29, 64);
        	mass = 2000;
        	break;
		case 4:
        	sprite = new Sprite("res/sports.png", 33, 64);
        	mass = 900;
        	break;
		case 5:
        	sprite = new Sprite("res/taxi.png", 33, 64);
        	mass = 1000;
        	break;
		case 6:
        	sprite = new Sprite("res/truck.png", 44, 124);
			mass = 12000;
			break;
		default:
			System.err.println("No such vehicle: " + vehicleType);
			System.exit(1);
			break;
        }
        
        // Initial speed
        vel = 10;
    }    
    
    
    public void setNextCarData(double dist, double vel, double acc) {
    	nextAcc = acc;
    	nextDist = dist;
    	nextVel = vel;
    }
    
    public double getLength() {
    	return sprite.getWidth()*ppm;
    }
    
    public double getMyAssPosition() {
    	return pos;
    }
    
    /**
     * First: Calculate speed and acceleration depending on next car.
     * Second: Draw me!
     * @param deltaMs
     */
    public void draw(int deltaMs) {
    	// v = at + v0
    	vel = personality.getWantedAcceleration(nextDist, nextVel, nextAcc, vel)*deltaMs/1000 + vel;
    	pos += vel*deltaMs/1000;
    	sprite.draw((int)(pos*ppm), 270-sprite.getHeight()/2);
    }

    /**
     * @return How many Joules per second this vehicle consumes at the moment.
     */
    double getCurrentWattage() {
        double k = 0.12345; // This is the same as 0.5*C*p*A in the formula for 
                            // air resistance force.
                            // should maybe depend on sqrt(mass) because of its front area

        // mva + kv^3 = motor effect + wind effect
        double Ptot = vel*(mass*accel + k*vel*vel);
        
        if (Ptot > 0.0) {
            return Ptot;
        } else {
            return 0.0;
        }
    }
    
    
}
