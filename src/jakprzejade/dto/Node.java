package jakprzejade.dto;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Node {
    public String name;
    public String time;
    
    // Długość geograficzna
    public double lon;
    // Szerokość geograficzna
    public double lat;

    @Override
    public String toString() {
        return "Node{" + "name=" + name + ", time=" + time + ", lon=" + lon + ", lat=" + lat + '}';
    }
}
