package jakprzejade.dynamicprogramming;

import jakprzejade.model.GeoPoint;
import jakprzejade.model2.Node;
import jakprzejade.model2.Path;
import jakprzejade.model2.Position;
import jakprzejade.model2.Positionable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author KonradOliwer
 */
public class AlgorithmNode implements Positionable {

    private final Node node;
    private final Knowledge k;
//    private final List<AlgorithmNode> observerts;
    private Path pathToEnd;

    //dynamicly changed in runtime
    private final List<Path> temporaryPathsList;
    private AlgorithmNode previous;
    private Path bestPathToThis;
    private int bestVisitTime;
    private List<Path> algorithmPaths;

    AlgorithmNode(Node node, Knowledge knowledge) {
        this.node = node;
        k = knowledge;
//        observerts = new LinkedList();
        temporaryPathsList = new ArrayList();
    }

    public static AlgorithmResult getAllPathsFromStartToEnd(Knowledge k) {
        AlgorithmResult result = new AlgorithmResult();
        AlgorithmNode algorithmNode = k.getEnd();
        result.nodes.add(algorithmNode);
        while (algorithmNode.previous != null) {
            result.paths.add(algorithmNode.bestPathToThis);
            algorithmNode = algorithmNode.previous;
            result.nodes.add(algorithmNode);
        }
        Collections.reverse(result.paths);
        Collections.reverse(result.nodes);
        return result;
    }

    public void expand() {
        Map<AlgorithmNode, Path> bestPaths = getBestPaths(new HashMap(), algorithmPaths);
        for (Entry<AlgorithmNode, Path> entry : bestPaths.entrySet()) {
            entry.getKey().visit(this, entry.getValue());
        }
        if (pathToEnd != null) {
            k.getEnd().visit(this, pathToEnd);
        }
    }

    private Map<AlgorithmNode, Path> getBestPaths(Map<AlgorithmNode, Path> bestPaths,
            List<Path> paths) {
        for (Path path : paths) {
            AlgorithmNode destination = k.getNodesMap().get(path.destination);
            if (destination != null) {
                if (!bestPaths.containsKey(destination)) {
                    bestPaths.put(destination, path);
                } else {
                    Path currentlyBestPath = bestPaths.get(destination);
                    if (!currentlyBestPath.dayType.match(path.dayType)) {
                        if (path.dayType.isBefore(currentlyBestPath.dayType)) {
                            bestPaths.put(destination, path);
                        }
                    } else {
                        if (path.getVisitTime(bestVisitTime)
                                < currentlyBestPath.getVisitTime(bestVisitTime)) {
                            bestPaths.put(destination, path);
                        }
                    }
                }
            }
        }
        return bestPaths;
    }

    public void visit(AlgorithmNode visitedFrom, Path path) {
//        if (this == k.getEnd()) {
//            System.out.println("Check it");
//        }
        if (previous == null || previous.previous != this) {
            if (this != k.getStart()) {
                int vistitTime = path.getVisitTime(visitedFrom.bestVisitTime);
                if (vistitTime < bestVisitTime) {
                    if (previous != null) {
//                        previous.observerts.remove(this);
                    }
                    previous = visitedFrom;
//                    visitedFrom.observerts.add(this);
                    bestVisitTime = vistitTime;
                    bestPathToThis = path;
//                    for (AlgorithmNode observert : observerts) {
//                        recomputeWayTo(observert);
//                    }
                    recomputeV2();
                }
            }
        }
    }

    private void recomputeV2() {
        for (AlgorithmNode node : k.getNodes()) {
            if (node.previous == this) {
                Path bestPath = node.bestPathToThis;
                int bestTIme = bestPath.getVisitTime(this.bestVisitTime);
                for (Path path : this.algorithmPaths) {
                    int vistitTime = path.getVisitTime(this.bestVisitTime);
                    if (vistitTime < bestTIme) {
                        bestTIme = vistitTime;
                        bestPath = path;
                    }
                }
                if (bestPath != node.bestPathToThis) {
                    node.visit(this, bestPath);
                }
            }
        }
    }

//    public void recomputeWayTo(AlgorithmNode nextNode) {
//        Path bestPathToNextNode;
//        if (nextNode != k.getEnd()) {
//            Map<AlgorithmNode, Path> map = new HashMap();
//            map.put(this, bestPathToThis);
//            temporaryPathsList.clear();
//            for (Path path : algorithmPaths) {
//                if (nextNode == k.getNodesMap().get(path.destination)) {
//                    temporaryPathsList.add(path);
//                }
//            }
//            bestPathToNextNode = getBestPaths(map, temporaryPathsList).get(nextNode);
//        } else {
//            bestPathToNextNode = pathToEnd;
//        }
//        nextNode.visit(this, bestPathToNextNode);
//    }
    public void init() {
        if (algorithmPaths == null) {
            algorithmPaths = new ArrayList();
            for (Path path : node.getPaths()) {
                if (path.byFoot || (path.startTime > k.getStartTime()
                        && path.endTime < k.getMaxEndTime())
                        // remove day change option:
                        && path.startTime < k.getMaxEndTime()
                        && path.endTime > k.getStartTime()) {
                    algorithmPaths.add(path);
                }
            }
//            algorithmPaths = node.getPaths().stream()
//                    .filter(path
//                            -> k.getNodesMap().containsKey(path.destination)
//                            && (path.byFoot
//                            || (!k.isPossibleDayChange()
//                            && path.dayType.match(k.getStartDayType())
//                            && path.startTime >= k.getStartTime()
//                            && path.endTime <= k.getMaxEndTime())
//                            || (k.isPossibleDayChange()
//                            && ((path.dayType.match(k.getStartDayType())
//                            && path.startTime >= k.getStartTime())
//                            || (path.dayType.match(k.getMaxEndDayType())
//                            && path.endTime <= k.getMaxEndTime()))))
//                    ).collect(Collectors.toList());
        }
    }

    @Override
    public String getId() {
        return node.getId();
    }

    @Override
    public Position getPosition() {
        return node.getPosition();
    }

    public GeoPoint getGeoPoint() {
        return node.getGeoPoint();
    }

    public String getName() {
        return node.getName();
    }

    public void addPaths(Path path) {
        node.addPaths(path);
    }

    public AlgorithmNode getBestGetHereFrom() {
        return previous;
    }

    public void setBestGetHereFrom(AlgorithmNode bestGetHereFrom) {
        this.previous = bestGetHereFrom;
    }

    public int getBestGetHereTime() {
        return bestVisitTime;
    }

    public void setBestGetHereTime(int bestGetHereTime) {
        this.bestVisitTime = bestGetHereTime;
    }

    public Path getPathToEnd() {
        return pathToEnd;
    }

    public void setPathToEnd(Path pathToEnd) {
        this.pathToEnd = pathToEnd;
    }

    @Override
    public String toString() {
        return node.toString();
    }
}
