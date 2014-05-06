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
        return new Personality(Math.log(self.mass) / 8.0, 2);
    }

    public double getWantedAcceleration(Vehicle self, Vehicle next) {
        double dist = next.pos - (self.pos + self.getLength()) - tailingDistance;
        return next.accel + 2*(dist/responseTime + (next.vel - self.vel))/responseTime;
    }

    double sillyAccelerationStrategy(Vehicle self, Vehicle next) {
        double dist = next.pos - (self.pos + self.getLength()) - tailingDistance;
        // Model:
        // self.pos = self.pos0 + self.vel*t + self.accel*t*t/2
        // next.pos = next.pos0 + next.vel*t + next.accel*t*t/2
        // We want it so that self.pos - next.pos =
        return ((next.vel - self.vel ) + dist/responseTime)/responseTime;
        //return -60/(Math.pow(dist, 4));
    }


}
