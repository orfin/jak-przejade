package jakprzejade.model;

import java.util.ArrayList;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class ScheduleTime {
    private int hour;
    private ArrayList<Integer> minutes;

    public ScheduleTime(int hour) {
        this.hour = hour;
        minutes = new ArrayList<Integer>();
    }

    public int getHour() {
        return hour;
    }

    public ArrayList<Integer> getMinutes() {
        return minutes;
    }
    
    public void addMinute(int min) {
        minutes.add(min);
    }

    @Override
    public String toString() {
        return "ScheduleTime{" + "hour=" + hour + ", minutes=" + minutes + '}';
    }
}
