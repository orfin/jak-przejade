package jakprzejade.dto;

import java.util.ArrayList;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Route {
    public int totalTime;
    public ArrayList<Path> paths = new ArrayList<Path>();

    @Override
    public String toString() {
        return "Route{" + "totalTime=" + totalTime + ", paths=" + paths + '}';
    }
}
