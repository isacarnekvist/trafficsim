/**
 * Created by lericson on 2014-05-06.
 */
public class RoadBlock extends Vehicle {
    public RoadBlock(double pos) {
        super(null, 0, 0, 0);
        this.pos = pos;
    }

    @Override
    public void simulate(double time, Vehicle next) {
        return;
    }
}
