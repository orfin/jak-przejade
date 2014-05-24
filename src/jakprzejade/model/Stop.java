package jakprzejade.model;

import jakprzejade.model.Schedule.ScheduleType;
import java.util.EnumMap;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Stop {

    private BusStop busStop;
    private Vehicle vehicle;
    private EnumMap<Schedule.ScheduleType, Schedule> schedules;

    public Stop(BusStop busStop, Vehicle vehicle) {
        this.busStop = busStop;
        this.vehicle = vehicle;
     
        schedules = new EnumMap(ScheduleType.class);
    }

    public BusStop getBusStop() {
        return busStop;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public void addSchedule(Schedule schedule) {
        schedules.put(schedule.getType(), schedule);
    }

    public Schedule getByScheduleType(ScheduleType type) {
        return schedules.get(type);
    }

    @Override
    public String toString() {
        return "Stop{" + "vehicle=" + vehicle + ", schedules=" + schedules + '}';
    }
    
}
