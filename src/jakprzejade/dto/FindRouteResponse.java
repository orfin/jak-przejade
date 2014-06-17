package jakprzejade.dto;

import java.util.ArrayList;

/**
 * Here representation of found route goes
 * 
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class FindRouteResponse {
    
    public ArrayList<Route> routes = new ArrayList<Route>();

    @Override
    public String toString() {
        return routes.toString();
    }
}
