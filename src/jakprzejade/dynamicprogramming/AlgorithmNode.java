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
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
    private int bestVisitTime = Integer.MAX_VALUE;
    private List<Path> algorithmPaths;

    AlgorithmNode(Node node, Knowledge knowledge) {
        this.node = node;
        k = knowledge;
        observerts = new LinkedList();
    }

    public static AlgorithmResult getAllPathsFromStartToEnd(Knowledge k) {
        AlgorithmResult result = new AlgorithmResult();
        AlgorithmNode algorithmNode = k.getEnd();
        result.nodes.add(algorithmNode.node);
        while (algorithmNode.previous != null) {
            result.paths.add(algorithmNode.bestPathToThis);
            algorithmNode = algorithmNode.previous;
            result.nodes.add(algorithmNode.node);
        }
        Collections.reverse(result.paths);
        return result;
    }

    public void expand() {
        Logger.getAnonymousLogger().log(Level.INFO, String.format("Expanding node %s", node.getName()));
        init();
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
        Logger.getAnonymousLogger().log(Level.INFO, String.format("%s getBestPaths: start", node.getName()));
        for (Path path : paths) {
            AlgorithmNode destination = k.getNodesMap().get(path.destination);
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
        Logger.getAnonymousLogger().log(Level.INFO, String.format("%s getBestPaths: finish", node.getName()));
        return bestPaths;
    }

    public void visit(AlgorithmNode visitedFrom, Path path) {
        Logger.getAnonymousLogger().log(Level.INFO, String.format("%s visitied from %s: start", node.getName(), visitedFrom.node.getName()));
        int vistitTime = path.getVisitTime(bestVisitTime);
        if (vistitTime < bestVisitTime) {
            if (previous != null) {
                previous.observerts.remove(this);
            }
            previous = visitedFrom;
            visitedFrom.observerts.add(this);
            bestVisitTime = vistitTime;
            bestPathToThis = path;
            observerts.stream().forEach((observingNode) -> {
                recomputeWayTo(observingNode);
            });
        }
        Logger.getAnonymousLogger().log(Level.INFO, String.format("%s visitied from %s: end", node.getName(), visitedFrom.node.getName()));
    }

    public void recomputeWayTo(AlgorithmNode nextNode) {
        Logger.getAnonymousLogger().log(Level.INFO, String.format("%s recomputeWayTo %s", node.getName(), nextNode.node.getName()));
        Map<AlgorithmNode, Path> map = new HashMap();
        map.put(this, bestPathToThis);
        Path bestPathToNextNode = getBestPaths(map,
                algorithmPaths.stream()
                .filter(path -> nextNode == k.getNodesMap().get(path.destination))
                .collect(Collectors.toList())
        ).get(nextNode);
        nextNode.visit(this, bestPathToNextNode);
    }

    private void init() {
        if (algorithmPaths == null) {
            algorithmPaths = node.getPaths().stream()
                    .filter(path
                            -> k.getNodesMap().containsKey(path.destination)
                            && (path.byFoot
                            || (!k.isPossibleDayChange()
                            && path.dayType.match(k.getStartDayType())
                            && path.startTime >= k.getStartTime()
                            && path.endTime <= k.getMaxEndTime())
                            || (k.isPossibleDayChange()
                            && ((path.dayType.match(k.getStartDayType())
                            && path.startTime >= k.getStartTime())
                            || (path.dayType.match(k.getMaxEndDayType())
                            && path.endTime <= k.getMaxEndTime()))))
                    ).collect(Collectors.toList());;
            Logger.getAnonymousLogger().log(Level.INFO, String.format("%s init()", String.format("%s initialized", node.getName())));
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
