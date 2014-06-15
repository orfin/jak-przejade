package jakprzejade.model2;

import jakprzejade.model.GeoPoint;

/**
 * Container representing geographic position
 *
 * @author KonradOliwer
 */
public class Position {

    private static final GeoPoint base = new GeoPoint(17.0321038, 51.1098308);
    private static final double xFactor = 69999.99;
    private static final double yFactor = 11113.29;
    public double x; //[m]
    public double y; //[m]

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(GeoPoint p) {
        y = (base.getLatitude() - p.getLatitude()) * yFactor;
        x = (base.getLongitude() - p.getLongitude()) * xFactor;
    }
}
