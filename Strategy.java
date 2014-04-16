// Driving strategy
public class Personality {
    // How many meters in advance does driver brake before a resting obstacle? (m)
    double foresightDistance;
    // What speed does driver have if possible? (m/s)
    double aimVelocity;
    // How fast does driver want to accelerate? (m/s^2)
    double aimAcceleration;

    public Personality(double aimVelocity, double aimAcceleration, double foresightDistance) {
        if (aimVelocity <= 0 || aimAcceleration <= 0 || foresightDistance <= 0) {
            throw new IllegalArgumentException();
        }

        this.aimVelocity = aimVelocity;
        this.aimAcceleration = aimAcceleration;
        this.foresightDistance = foresightDistance;
    }

    public double input(Vehicle mine, Vehicle next) {
        return 0.0;
    }
}
