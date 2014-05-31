/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.routefinder;

import jakprzejade.dto.FindRouteRequest;
import jakprzejade.dto.FindRouteResponse;

/**
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public interface RouteFinder {
    public FindRouteResponse findRoute(FindRouteRequest request);
}
