package jakprzejade.dynamicprogramming;

import jakprzejade.model2.Position;

/**
 *
 * @author KonradOliwer
 */
public class Elipse {

    final Position center;
    final double a;
    final double b;

    public Elipse(Position start, Position end) {
        center = new Position((start.x + end.x) / 2, (start.y + end.y) / 2);
        int distance = (int) start.distanceTo(end);
        a = distance * 2.2;
        b = distance;
    }

    public boolean isInElipse(Position p) {
        return 1 >= Math.pow(p.x - center.x, 2) / Math.pow(a, 2)
                + Math.pow(p.y - center.y, 2) / Math.pow(b, 2);
    }
}
