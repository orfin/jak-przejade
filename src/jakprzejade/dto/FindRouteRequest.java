/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.dto;

import jakprzejade.model.GeoPoint;
import java.util.Calendar;

/**
 * DTO class for Route Finder parameters
 * 
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class FindRouteRequest {
    public Calendar date;
    public GeoPoint from;
    public GeoPoint to;

    public FindRouteRequest() {
    }
    
    public FindRouteRequest(Calendar date, GeoPoint from, GeoPoint to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }
    
}
