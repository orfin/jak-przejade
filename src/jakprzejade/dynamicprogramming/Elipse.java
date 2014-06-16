package jakprzejade.dynamicprogramming;

import jakprzejade.model2.Position;

/**
 *
 * @author KonradOliwer
 */
class Elipse {

    final Position center;
    final double a;
    final double b;

    Elipse(Position start, Position end) {
        center = new Position((start.x + end.x) / 2, (start.y + end.y) / 2);
        a = DynamicProgrammingUtils.distance(start, end);
        b = a / 2;
    }

    boolean isInElipse(Position p) {
        return 1 >= Math.pow(p.x - center.x, 2) / Math.pow(a, 2)
                + Math.pow(p.y - center.y, 2) / Math.pow(b, 2);
    }
}
