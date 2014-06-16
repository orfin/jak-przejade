package jakprzejade.dynamicprogramming;

import jakprzejade.model2.Position;

/**
 *
 * @author KonradOliwer
 */
public class DynamicProgrammingUtils {

    public static double distance(Position p1, Position p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static int calculateByFoot(Position p1, Position p2) {
        return (int) Math.round(distance(p1, p2) / 65);
    }
}
