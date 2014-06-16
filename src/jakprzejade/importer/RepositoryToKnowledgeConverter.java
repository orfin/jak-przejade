/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.importer;

import jakprzejade.model.*;
import jakprzejade.model2.DayType;
import jakprzejade.model2.Node;
import jakprzejade.model2.Path;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class RepositoryToKnowledgeConverter {

    private static final int INVALID_END_TIME = -2;
    private Repository repo;
    private HashMap<String, Node> nodesMap = new HashMap();

    public RepositoryToKnowledgeConverter(Repository repo) {
        this.repo = repo;
    }

    public HashMap<String, Node> convert() {
        Importer.getLogger().log(Level.INFO, "Converting repository to GlobalKnowledge with \"{0}\" bus stops.", new String[]{"" + repo.busStops.size()});

        long timeStart = System.currentTimeMillis();

        makeNodes();
        makePaths();

        Importer.getLogger().log(Level.INFO, "Conversion completed in {0} ms", "" + Importer.getTimeTotal(timeStart));

        return nodesMap;
    }

    public void makeNodes() {

        for (BusStop busStop : repo.busStops.values()) {
            if (busStop.getLocation() == null) {
                continue;
            }

            Node node = new Node(busStop.getId(), busStop.getName(), busStop.getLocation().clone());
            nodesMap.put(busStop.getId(), node);
        }
    }

    public void makePaths() {
        for (Route route : repo.routes) {
            for (int i = 0; i < (route.stops.size() - 1); i++) {
                Stop stop = route.stops.get(i);
                Stop nextStop = route.stops.get(i + 1);
                Node nodeStart = nodesMap.get(stop.getBusStop().getId());

                if (nodeStart == null) {
                    continue;
                }

                for (DayType dayType : DayType.values()) {
                    addPaths(stop, nextStop, nodeStart, dayType);
                }
            }
        }
    }

    private void addPaths(Stop stop, Stop nextStop, Node nodeStart, DayType dayType) {
        if (stop.getByDayType(dayType) == null) {
            return;
        }

        for (ScheduleTime time : stop.getByDayType(dayType).getTimes().values()) {
            for (Integer minute : time.getMinutes()) {
                int startTime = Path.toUnifiedTime(time.getHour(), minute);
                int endTime = getNearestTime(nextStop, startTime, dayType);
                int timeCost = Path.timeDifference(startTime, endTime);

                if (endTime != INVALID_END_TIME) {
                    Path path = new Path(dayType, nextStop.getBusStop().getId(),
                            stop.getVehicle().getName(), timeCost, startTime, endTime);
                    nodeStart.addPaths(path);
                }
            }
        }
    }

    private int getNearestTime(Stop nextStop, int startTime, DayType dayType) {
        return recurentNearestTime(nextStop, startTime, dayType, true);
    }

    private int recurentNearestTime(Stop nextStop, int startTime, DayType dayType,
            boolean firstMethodVisit) {
        if (nextStop.getByDayType(dayType) == null) {
            return INVALID_END_TIME;
        }

        for (ScheduleTime time : nextStop.getByDayType(dayType).getTimes().values()) {
            for (Integer minute : time.getMinutes()) {
                int unifiedTime = Path.toUnifiedTime(time.getHour(), minute);

                if (unifiedTime > startTime) {
                    return unifiedTime; //becouse list are sorted
                }
            }
        }

        if (firstMethodVisit) {
            return recurentNearestTime(nextStop, 0, dayType, false);
        }
        return INVALID_END_TIME;
    }
}
