package jakprzejade.model2;

import jakprzejade.model.GeoPoint;

/**
 * Container representing geographic position
 *
 * @author KonradOliwer
 */
public class Position {

    private static final GeoPoint base = new GeoPoint(17.0321038, 51.1098308);
    private static final double xFactor = 68510;
    private static final double yFactor = 111269;
    public double x; //[m]
    public double y; //[m]

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(GeoPoint p) {
        x = (base.getLongitude() - p.getLongitude()) * xFactor;
        y = (base.getLatitude() - p.getLatitude()) * yFactor;
    }

    public double distanceTo(Position p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }
}
