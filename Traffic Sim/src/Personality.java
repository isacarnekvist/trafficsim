// Driving strategy
public class Personality {
    double responseTime;
    double tailingDistance;

    /**
     * @param responseTime    The responsiveness of the driver in seconds
     * @param tailingDistance Keep this distance to the next vehicle
     */
    public Personality(double responseTime, double tailingDistance) {
        if (responseTime <= 0 || tailingDistance <= 0) {
            throw new IllegalArgumentException();
        }
        this.responseTime = responseTime;
        this.tailingDistance = tailingDistance;
    }

    public static Personality standardDriver(Vehicle self) {
        return new Personality(Math.log(self.mass) / 4.0, 3);
    }

    public double getWantedAcceleration(Vehicle self, Vehicle next) {
        double dist = self.distanceTo(next) - tailingDistance;
        double deltaVel = next.vel - self.vel;

        if (dist < 0) {
            return -100*((self.pos + self.getLength()) - (next.pos - tailingDistance));
        }

        return next.accel + 2*(dist/responseTime + deltaVel)/responseTime;
    }
}
