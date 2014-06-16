package jakprzejade;

import jakprzejade.model2.Position;

/**
 *
 * @author KonradOliwer
 */
public class Utils {

    public static int calculateByFoot(Position p1, Position p2) {
        return (int) Math.round((p1.distanceTo(p2) / 50)  + 0.5);
    }
}
