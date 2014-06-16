package jakprzejade.dj;

import jakprzejade.dynamicprogramming.AlgorithmNode;
import jakprzejade.model2.DayType;
import jakprzejade.model2.Node;
import jakprzejade.model2.Path;
import jakprzejade.model2.Position;
import jakprzejade.model2.Positionable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author KonradOliwer
 */
public class DjNode implements Positionable {

    int value;
    DjNode previous;
    Node node;
    Path pathToEnd;
    Path previousPath;

    DjNode(Node node) {
        this.node = node;
    }

    DjNode(Node node, DjNode end) {
        this(node);
        pathToEnd = Path.getPathByFootBetween(node, end);
    }

    @Override
    public String getId() {
        return node.getId();
    }

    @Override
    public Position getPosition() {
        return node.getPosition();
    }

    List<Path> bestPaths(Map<String, DjNode> nodesMap, DayType dayType) {
        return getBestPaths(nodesMap, node.getPaths(), dayType);
    }

    private List<Path> getBestPaths(Map<String, DjNode> nodesMap, List<Path> paths,
            DayType dayType) {
        Map<DjNode, Path> bestPaths = new HashMap();
        for (Path path : paths) {
            DjNode destination = nodesMap.get(path.destination);
            if (destination != null) {
                if (value > 1440) {
                    if (!dayType.equals(DayType.ANY)) {
                        dayType = dayType.nextDayType();
                    }
                    value -= 1440;
                }
                if (path.dayType.match(dayType)) {
                    if (!bestPaths.containsKey(destination)) {
                        bestPaths.put(destination, path);
                    } else {
                        Path currentlyBestPath = bestPaths.get(destination);
                        if (path.getVisitTime(this.value)
                                < currentlyBestPath.getVisitTime(this.value)) {
                            bestPaths.put(destination, path);
                        }
                    }
                }
            }
        }
        List<Path> list = new ArrayList();
        for (Entry<DjNode, Path> entry : bestPaths.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    @Override
    public String toString() {
        return node.toString();
    }
}
