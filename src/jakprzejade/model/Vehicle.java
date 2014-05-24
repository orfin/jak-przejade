package jakprzejade.model;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Vehicle {

    private String name;
    private VehicleType vehicleType;
    private LineType lineType;

    public Vehicle(String name, VehicleType vehicleType, LineType lineType) {
        this.name = name;
        this.vehicleType = vehicleType;
        this.lineType = lineType;
    }

    public String getName() {
        return name;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public LineType getLineType() {
        return lineType;
    }

    @Override
    public String toString() {
        return "Vehicle{" + "name=" + name + ", vehicleType=" + vehicleType + ", lineType=" + lineType + '}';
    }
    
    public enum VehicleType {

        TRAM, BUS
    }

    public enum LineType {
        REGULAR, LIMITED, EXPRESS, NIGHT,
        SUBURBAN, TEMPORARY, ZONE, PEAK_HOUR
    }
}
