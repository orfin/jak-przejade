/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.model;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class GeoPoint implements Cloneable {

    // Długość geograficzna
    private double longitude;

    // Szerokość geograficzna
    private double latitude;
    
    public GeoPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    @Override
    public String toString() {
        return "GeoPoint{" + "longitude=" + longitude + ", latitude=" + latitude + '}';
    }
    
    @Override
    public GeoPoint clone() {
        return new GeoPoint(longitude, latitude);
    }
    
}
