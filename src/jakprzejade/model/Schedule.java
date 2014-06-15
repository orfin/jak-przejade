package jakprzejade.model;

import jakprzejade.model2.DayType;
import java.util.HashMap;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Schedule {
    
    private DayType type;
    private HashMap<Integer, ScheduleTime> times;

    public Schedule(DayType type) {
        this.type = type;
        this.times = new HashMap<Integer, ScheduleTime>();
    }

    public DayType getType() {
        return type;
    }

    public HashMap<Integer, ScheduleTime> getTimes() {
        return times;
    }
    
    public void addTime(ScheduleTime time) {
        times.put(time.getHour(), time);
    }
    
    @Override
    public String toString() {
        return "Schedule{" + "type=" + type + ", times=" + times + '}';
    }
}
