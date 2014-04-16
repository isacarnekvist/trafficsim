public class Vehicle {
    double mass;      // (kg)
    double vel = 0;   // (m/s)
    double accel = 0; // (m/s^2)
    Strategy personality;

    /**
     * @param mass The mass of the car (kg)
     */
    public Vehicle(double mass, Personality personality) {
        this.mass = mass;
        this.personality = personality;
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
