/**
 * Created by lericson on 2014-05-06.
 */
public class RoadBlock extends Vehicle {
    public RoadBlock(double pos) {
        super(0, pos, null);
        vel = 0;
    }

    @Override
    public void simulate(double time, Vehicle next) {
        return;
    }
}
