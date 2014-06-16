package jakprzejade.dj;

import jakprzejade.dto.FindRouteRequest;
import jakprzejade.dto.FindRouteResponse;
import jakprzejade.dynamicprogramming.Elipse;
import jakprzejade.model2.DayType;
import jakprzejade.model2.GlobalKnowledge;
import jakprzejade.model2.Node;
import jakprzejade.model2.Path;
import jakprzejade.routefinder.RouteFinder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author KonradOliwer
 */
public class Dj implements RouteFinder {

    private static final String START_NODE_NAME = "Początek trasy";
    private static final String END_NODE_NAME = "Koniec trasy";
    private static final String START_NODE_ID = "START";
    private static final String END_NODE_ID = "END";

    Map<String, DjNode> nodesMap = new HashMap();
    List<DjNode> nodes = new ArrayList();
    DjNode start;
    DjNode end;
    DayType dayType;

    @Override
    public FindRouteResponse findRoute(FindRouteRequest request) {
        init(request, GlobalKnowledge.getNodeList());
        execute();
        return buildResponse(result());
    }

    private FindRouteResponse buildResponse(List<DjNode> result) {
        jakprzejade.dto.Route rout = new jakprzejade.dto.Route();
        rout.paths = new ArrayList();
        int index = -1;
        for (int i = 0; i < result.size(); i++) {
            jakprzejade.dto.Node dtoNode = new jakprzejade.dto.Node();
            dtoNode.lat = result.get(i).node.getGeoPoint().getLatitude();
            dtoNode.lon = result.get(i).node.getGeoPoint().getLongitude();
            dtoNode.name = result.get(i).node.getName();
            dtoNode.time = String.format("%d:%d", result.get(i).value / 60, result.get(i).value % 60);
            int j = i + 1;
            if (j < result.size()) {
                if (rout.paths.size() > 0 && result.get(j).previousPath.lineName
                        .equals(rout.paths.get(index).vehicleName)) {
                } else {
                    jakprzejade.dto.Path dtoPath = new jakprzejade.dto.Path();
                    dtoPath.vehicleName = result.get(j).previousPath.lineName;
                    rout.paths.add(++index, dtoPath);
                }
            }
            rout.paths.get(index).nodes.add(dtoNode);
        }

        FindRouteResponse response = new FindRouteResponse();
        response.routes.add(rout);
        rout.totalTime = result.get(result.size() - 1).value - result.get(0).value;
        return response;
    }

    private List<DjNode> result() {
        List<DjNode> n = new ArrayList();
        DjNode current = end;
        while (current != null) {
            n.add(current);
            current = current.previous;
        }
        Collections.reverse(n);
        return n;
    }

    public void execute() {
        while (!nodes.isEmpty()) {
            DjNode current = findMinNode();
            List<Path> bestPaths = current.bestPaths(nodesMap, dayType);
            for (Path path : bestPaths) {
                explore(current, path);
            }
            explore(current, current.pathToEnd);
            nodes.remove(current);
        }
    }

    private void explore(DjNode node, Path path) {
        DjNode checked = nodesMap.get(path.destination);
        if (checked.value > node.value + path.timeCost) {
            checked.previous = node;
            checked.value = node.value + path.timeCost;
            checked.previousPath = path;
        }
    }

    private DjNode findMinNode() {
        int bestValue = Integer.MAX_VALUE;
        DjNode best = null;
        for (DjNode node : nodes) {
            if (node.value < bestValue) {
                bestValue = node.value;
                best = node;
            }
        }
        return best;
    }

    private void init(FindRouteRequest frr, List<Node> input) {
        createStartAndEnd(frr);
        addNodes();
        visitAllNodesFromStart();
        setDates(frr);
    }

    private void visitAllNodesFromStart() {
        for (DjNode node : nodes) {
            Path path = Path.getPathByFootBetween(start, node);
            node.previous = start;
            node.value = start.value + path.timeCost;
            node.previousPath = path;
        }
        Path path = Path.getPathByFootBetween(start, end);
        end.previous = start;
        end.value = start.value + path.timeCost;
        end.previousPath = path;
    }

    private void createStartAndEnd(FindRouteRequest frr) {
        end = new DjNode(new Node(END_NODE_ID, END_NODE_NAME, frr.to));
        nodesMap.put(end.getId(), end);

        start = new DjNode(new Node(START_NODE_ID, START_NODE_NAME, frr.from), end);
        start.value = frr.date.get(Calendar.HOUR) * 60 + frr.date.get(Calendar.MINUTE);
        nodesMap.put(start.getId(), start);
    }

    private void addNodes() {
        Elipse searchArea = new Elipse(start.getPosition(), end.getPosition());
        for (Node node : GlobalKnowledge.getNodeList()) {
            if (searchArea.isInElipse(node.getPosition())) {

                DjNode algorithmNode = new DjNode(node, end);

                nodesMap.put(algorithmNode.getId(), algorithmNode);
                nodes.add(algorithmNode);
            }
        }
    }

    private void setDates(FindRouteRequest frr) {
        dayType = DayType.getDayType(frr.date.get(Calendar.DAY_OF_WEEK));
    }
}
