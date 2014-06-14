package jakprzejade.dynamicprogramming;

import jakprzejade.model.GeoPoint;
import jakprzejade.model2.Node;
import jakprzejade.model2.Path;
import jakprzejade.model2.Position;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author KonradOliwer
 */
public class AlgorithmNode {

    private final Node node;
    private final Knowledge k;
    private final List<AlgorithmNode> observerts;
    private Path pathToEnd;

    //dynamicly changed in runtime
    private AlgorithmNode previous;
    private Path bestPathToThis;
    private int bestVisitTime;
    private Stream<Path> algorithmPaths;

    AlgorithmNode(Node node, Knowledge knowledge) {
        this.node = node;
        k = knowledge;
        observerts = new LinkedList();
    }

    public static List<Path> getAllPathsFromStartToEnd(Knowledge k) {
        AlgorithmNode algorithmNode = k.getEnd();
        List<Path> list = new LinkedList();
        while(algorithmNode != k.getStart()){
            list.add(algorithmNode.bestPathToThis);
        }
        Collections.reverse(list);
        return list;
    }

    public void expand() {
        init();
        getBestPaths(new HashMap(), algorithmPaths).forEach(((destination, path)
                -> (((AlgorithmNode) destination).visit(this, (Path) path))));
    }

    private Map<AlgorithmNode, Path> getBestPaths(Map<AlgorithmNode, Path> bestPaths,
            Stream<Path> paths) {
        paths.forEach((path) -> {
            AlgorithmNode destination = k.getNodesMap().get(path.destination);
            if (bestPaths.containsKey(destination)) {
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
        });
        return bestPaths;
    }

    public void visit(AlgorithmNode visitedFrom, Path path) {
        int vistitTime = path.getVisitTime(bestVisitTime);
        if (vistitTime < bestVisitTime) {
            previous.observerts.remove(this);
            previous = visitedFrom;
            visitedFrom.observerts.add(this);
            bestVisitTime = vistitTime;
            bestPathToThis = path;
            observerts.stream().forEach((observingNode) -> {
                recomputeWayTo(observingNode);
            });
        }
    }

    public void recomputeWayTo(AlgorithmNode nextNode) {
        Map<AlgorithmNode, Path> map = new HashMap();
        map.put(this, bestPathToThis);
        Path bestPathToNextNode = getBestPaths(map,
                algorithmPaths.filter(path -> nextNode == k.getNodesMap().get(path.destination))
        ).get(nextNode);
        nextNode.visit(this, bestPathToNextNode);
    }

    private void init() {
        if (algorithmPaths == null) {
            algorithmPaths = node.getPaths().stream()
                    .filter(path
                            -> path.byFoot
                            || (!k.isPossibleDayChange()
                            && path.dayType.match(k.getStartDayType())
                            && path.startTime >= k.getStartTime()
                            && path.endTime <= k.getMaxEndTime())
                            || (k.isPossibleDayChange()
                            && ((path.dayType.match(k.getStartDayType())
                            && path.startTime >= k.getStartTime())
                            || (path.dayType.match(k.getMaxEndDayType())
                            && path.endTime <= k.getMaxEndTime())))
                    );
        }
    }

    public String getId() {
        return node.getId();
    }

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
}
