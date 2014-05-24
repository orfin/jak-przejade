package jakprzejade.model;

import java.util.HashMap;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Schedule {
    
    private ScheduleType type;
    private HashMap<Integer, ScheduleTime> times;

    public Schedule(ScheduleType type) {
        this.type = type;
        this.times = new HashMap<Integer, ScheduleTime>();
    }

    public ScheduleType getType() {
        return type;
    }

    public HashMap<Integer, ScheduleTime> getTimes() {
        return times;
    }
    
    public void addTime(ScheduleTime time) {
        times.put(time.getHour(), time);
    }
    
    public enum ScheduleType {

        WORKING_DAYS, SATURDAY, SUNDAY
    }

    @Override
    public String toString() {
        return "Schedule{" + "type=" + type + ", times=" + times + '}';
    }
}
