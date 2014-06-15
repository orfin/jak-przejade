package jakprzejade.model;

import jakprzejade.model2.DayType;
import java.util.EnumMap;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Stop {

    private BusStop busStop;
    private Vehicle vehicle;
    private EnumMap<DayType, Schedule> schedules;

    public Stop(BusStop busStop, Vehicle vehicle) {
        this.busStop = busStop;
        this.vehicle = vehicle;
     
        schedules = new EnumMap(DayType.class);
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

    public Schedule getByDayType(DayType type) {
        return schedules.get(type);
    }

    @Override
    public String toString() {
        return "Stop{" + "vehicle=" + vehicle + ", schedules=" + schedules + '}';
    }
    
}
