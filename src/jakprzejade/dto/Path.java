/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.dto;

import java.util.ArrayList;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Path {
    public String vehicleName;
    public String vehicleType;
    
    public ArrayList<Node> nodes = new ArrayList<Node>();

    @Override
    public String toString() {
        return "Path{" + "vehicleName=" + vehicleName + ", vehicleType=" + vehicleType + ", nodes=" + nodes + '}';
    }
}
