/**
 * Created by lericson on 2014-05-06.
 */
public enum CarFactory {
    BUS         ("bus",         5000,   39, 120, 39*39/2/GTA.ppm, 10),
    MC          ("mc",          200,    19, 46,  19*19/9/GTA.ppm, 5),
    POLICE      ("police",      1000,   33, 64,  33*33/4/GTA.ppm, 4),
    SMALL_TRUCK ("small_truck", 2000,   29, 64,  29*29/2/GTA.ppm, 7),
    SPORTSCAR   ("sports",      900,    33, 64,  33*33/9/GTA.ppm, 3),
    TAXI        ("taxi",        1000,   33, 64,  33*33/4/GTA.ppm, 7),
    TRUCK       ("truck",       12000,  44, 124, 44*44/2/GTA.ppm, 15);

    String name;
    double mass;
    int width, height;
    double frontArea;
    double maxEngineForce;

    /**
     * A standard car type
     *
     * @param name Vehicle name, also sprite loaded from res/name.png
     * @param width Vehicle sprite's width, and also vehicle's length
     * @param height Vehicle sprite's height, and also vehicle's width
     * @param frontArea Area of vehicle's front
     * @param zeroToHundred Seconds for max engine force to get from 0 to 100 km/h
     */
    CarFactory(String name, double mass, int height, int width, double frontArea, double zeroToHundred) {
        this.name = name;
        this.mass = mass;
        this.height = height;
        this.width = width;
        this.frontArea = frontArea;
        // v = v0 + at, going from zero to a hundred means
        // 100*1000/3600 = 0 + at, so F = 1000*m/36/m/t, and F = engine force - wind force,
        // so engine force = 1000*m/36/t + wind force at 100 km/h
        // and wind force = density * velocity^2 * drag coefficient * area / 2, so
        double totalForce = 1000.0*mass/zeroToHundred/36.0;
        double windForce =  Vehicle.airDensity*100*100*Vehicle.drag*frontArea/2.0;
        this.maxEngineForce = totalForce + windForce;
    }

    public Vehicle produceVehicle() {
        Sprite sprite = new Sprite("res/" + name + ".png", height, width);
        return new Vehicle(sprite, mass, frontArea, maxEngineForce);
    }

    static CarFactory random() {
        CarFactory[] cars = CarFactory.values();
        return cars[(int)(Math.random()*cars.length)];
    }

}
