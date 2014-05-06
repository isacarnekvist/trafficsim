public class Vehicle {
    double mass;      // (kg)
    double pos = 0;   // (m)
    double vel = 0;   // (m/s) // ass possision.
    double accel = 0; // (m/s^2)
    double wantedAccel = 0;
    double frontArea = 0; // m^2
    double maxEngineForce = 0;
    Personality personality;
    Sprite sprite;

    Vehicle(Sprite sprite, double mass, double frontArea, double maxEngineForce) {
        this.sprite = sprite;
        this.mass = mass;
        this.frontArea = frontArea;
        this.maxEngineForce = maxEngineForce;
    }

    double distanceTo(Vehicle next) {
        return next.pos - (this.pos + this.getLength());
    }
    
    public double getLength() {
    	return sprite.getWidth()/GTA.ppm;
    }
    
    /**
     * Simulate movement of car
     *
     * @param time Time elapsed passed since last simulation (seconds)
     * @param next Next vehicle, null if no next vehicle exists
     */
    public void simulate(double time, Vehicle next) {
        wantedAccel = personality.getWantedAcceleration(this, next);
        // No reverse gear! NEVER LOOK BACK!!! NO TOMORROW!!!!
        if (wantedAccel < 0 && vel < 0.1) {
            wantedAccel = 0;
        }
        //System.err.println(engineForce() + ", " + brakeForce());
        accel = (engineForce() + brakeForce() + windForce())/mass;

        /*
        vel += accel*time;
    	pos += vel*time;
        */

    	int integrations = 100000;
    	double deltaVel = accel*time/integrations;
    	double timePerIntegration = time/integrations;
        
        for (int i = 0; i < integrations; i++) {
            vel += deltaVel;
            double deltaPos = this.pos + (this.getLength()) - next.getMyAssPosition();
            if((deltaPos - deltaVel) > 0){ // Stupid driver just crached his car, fortunatly for him we don't simulate death.
                vel = 0;
                return;
            }
            pos += vel*timePerIntegration;
        }
        
        // Cars on the road doesn't go backwards.
        if(vel < 0)
            vel = 0;
    }

    // The drag coefficient is in [0.20, 0.30] for cars.
    static double drag = 0.19;
    static double airDensity = 1.293; // kg/m^3

    /**
     * Force due to wind is a quadratic function of velocity, force <= 0
     */
    double windForce() {
        // F_D = density * velocity^2 * drag coefficient * area / 2
        return - vel * vel * drag * airDensity * frontArea / 2.0;
    }

    /**
     * Velocity at equilibrium point of drag and max engine force
     */
    double maxVelocity() {
        return Math.sqrt(2.0*maxEngineForce/drag/airDensity/frontArea);
    }

    /**
     * Force we seek to apply to the car, adjusted for wind
     */
    double applyForce() {
        return wantedAccel*mass - windForce();
    }

    /**
     * Force due to engine, 0 <= force <= maxEngineForce
     */
    double engineForce() {
        return Math.min(maxEngineForce, Math.max(0, applyForce()));
    }

    /**
     * Force due to braking action, force <= 0
     */
    double brakeForce() {
        return Math.min(0, applyForce());
    }

    /**
     * Engine output amount \in [0..1]
     */
    double engineOutput() {
        return engineForce() / maxEngineForce;
=======
    	accel = personality.getWantedAcceleration(this, next);
        // Approximate a smoother simulation by integrating "in the middle" of
        // each quantized acceleration segment. See Riemann sums for theory.
        
>>>>>>> mockelind/master
    }

    /**
     * Draw car on sprite
     */
    public void draw() {
        sprite.draw((int)(pos*GTA.ppm), 270-sprite.getHeight()/2);
    }

    /**
     * @return How many Joules per second this vehicle consumes at the moment.
     */
    double getEnginePower() {
        // P = E/t = Fs/t = Fv
        return Math.abs(vel)*Math.max(0, engineForce());
    }

<<<<<<< HEAD
    @Override
    public String toString() {
        return String.format("<Vehicle %s, top speed %.2f km/h, engine output=%.0f%%>",
                sprite.toString(), maxVelocity()/3.6, engineOutput()*100);
=======
        // mva + kv^3 = motor effect + wind effect
        double Ptot = vel*(mass*accel + k*vel*vel);
        
        if (Ptot > 0.0) {
            return Ptot;
        } else {
            return 0;
        }
>>>>>>> mockelind/master
    }
}
