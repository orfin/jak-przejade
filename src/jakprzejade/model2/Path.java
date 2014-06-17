package jakprzejade.model2;

import jakprzejade.Utils;

/**
 * Path between two nodes
 *
 * @author KonradOliwer
 */
public class Path {

    public final static String BY_FOOT_NAME = "P";
    public final static int ANY_TIME = -1;
    public final DayType dayType;
    public final String lineName;
    public final int timeCost;
    public final int startTime;
    public final int endTime;
    public final String destination;
    public final boolean byFoot;

    private Path(DayType dayType, String destination, String lineName, int timeCost, int startTime,
            int endTime, boolean byFoot) {
        this.dayType = dayType;
        this.destination = destination;
        this.lineName = lineName;
        this.timeCost = timeCost;
        this.startTime = startTime;
        this.endTime = endTime;
        this.byFoot = byFoot;
    }

    public Path(DayType dayType, String destination, String lineName, int timeCost, int startTime,
            int endTime) {
        this(dayType, destination, lineName, timeCost, startTime, endTime, false);
    }

    public Path(String destination, int timeCost) {
        this(DayType.ANY, destination, BY_FOOT_NAME, timeCost, ANY_TIME, ANY_TIME, true);
    }

    public int getVisitTime(int startTime) {
        return byFoot ? startTime + timeCost : endTime;
    }

    public static int toUnifiedTime(int hour, int minute) {
        return hour * 60 + minute;
    }

    public static String fromUnifiedTime(int time) {
        return String.format("%d:%d", time / 60, time % 60);
    }

    public static int timeDifference(int startTime, int endTime) {
        return startTime < endTime ? endTime - startTime : 1440 - startTime + endTime;
    }

    public static Path getPathByFootBetween(Positionable begining, Positionable destination) {
        return new Path(destination.getId(), Utils.calculateByFoot(
                begining.getPosition(), destination.getPosition()));
    }
}
