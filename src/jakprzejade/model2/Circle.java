package jakprzejade.model2;

/**
 *
 * @author KonradOliwer
 */
class Circle {

    final Position center;
    final int r;

    Circle(Position center, int r) {
        this.r = r;
        this.center = center;
    }

    boolean isNotOutside(Position point) {
        return center.distanceTo(point) <= r;
    }
}
