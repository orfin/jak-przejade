package jakprzejade.model2;

/**
 *
 * @author KonradOliwer
 */
public enum DayType {

    WEEK_DAY, SUTURDAY, SUNDAY, ANY;

    public int getNumber() {
        switch (this) {
            case ANY:
                return -1;
            case SUTURDAY:
                return 6;
            case SUNDAY:
                return 7;
            default:
                throw new RuntimeException("WEEK_DAY doesn't apply for specyfic day.");
        }
    }

    public boolean isBefore(DayType type) {
        switch (this) {
            case WEEK_DAY:
                return type == SUTURDAY || type == SUNDAY;
            case SUTURDAY:
                return type == SUNDAY;
            default:
                return false;
        }
    }

    public boolean match(DayType type) {
        return this == ANY || type == ANY || this == type;
    }

    public static DayType getDayType(int weekDayNumber) {
        switch (weekDayNumber) {
            case -1:
                return DayType.ANY;
            case 6:
                return DayType.SUTURDAY;
            case 7:
                return DayType.SUNDAY;
            default:
                return DayType.WEEK_DAY;
        }
    }
}
