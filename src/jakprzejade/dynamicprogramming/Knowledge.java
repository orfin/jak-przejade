package jakprzejade.dynamicprogramming;

import jakprzejade.dto.FindRouteRequest;
import jakprzejade.model2.DayType;
import jakprzejade.model2.GlobalKnowlage;
import jakprzejade.model2.Node;
import jakprzejade.model2.Path;
import jakprzejade.model2.Position;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Container for knowledge of algorithm
 *
 * @author KonradOliwer
 */
public class Knowledge {

    private static final String START_NODE_NAME = "Początek trasy";
    private static final String END_NODE_NAME = "Koniec trasy";
    private static final String START_NODE_ID = "START";
    private static final String END_NODE_ID = "END";
    private HashMap<String, Node> nodesMap = new HashMap();
    private ArrayList<Node> nodes = new ArrayList();
    private boolean possibleDayChange;
    private DayType startDayType;
    private DayType maxEndDayType;
    private int startTime;
    private int maxEndTime;
    private Node start;
    private Node end;

    public Knowledge(FindRouteRequest frr) {
        addStartAndEndNode(frr);
        addSutableNodes(frr);
        addTimeFrames(frr);
        addSutablePaths();
    }

    private void addStartAndEndNode(FindRouteRequest frr) {
        start = new Node(START_NODE_ID, START_NODE_NAME, frr.from);
        nodesMap.put(start.getId(), start);
        nodes.add(start);
        end = new Node(START_NODE_ID, START_NODE_NAME, frr.to);
        nodesMap.put(end.getId(), end);
        nodes.add(end);
    }

    private void addSutableNodes(FindRouteRequest frr) {
        Elipse searchArea = new Elipse(start.getPosition(), end.getPosition());
        for (Node node : GlobalKnowlage.getNodeList()) {
            if (nodeIsInElipse(searchArea, node)) {
                Node newNode = cloneWithoutPaths(node);
                nodesMap.put(newNode.getId(), newNode);
                nodes.add(newNode);
            }
        }
    }

    private void addTimeFrames(FindRouteRequest frr) {
        startTime = frr.date.get(Calendar.HOUR) * 60 + frr.date.get(Calendar.MINUTE);
        maxEndTime = startTime + DynamicProgrammingUtils
                .calculateByFoot(start.getPosition(), end.getPosition());
        possibleDayChange = maxEndTime > 1440;

        startDayType = DynamicProgrammingUtils.getDayType(frr.date.get(Calendar.DAY_OF_WEEK));
        if (possibleDayChange) {
            maxEndTime -= 1440;
            maxEndDayType = DynamicProgrammingUtils.getDayType(
                    frr.date.get(Calendar.DAY_OF_WEEK) + 1);
        } else {
            maxEndDayType = startDayType;
        }
    }

    private void addSutablePaths() {
        HashMap<String, Node> globalMap = GlobalKnowlage.nodesMap;
        for (Node node : nodes) {
            Node globalNode = globalMap.get(node.getId());
            for (Path path : globalNode.getIncoming()) {
                if (matchesRequiraments(path)) {
                    node.getIncoming().add(path);
                }
            }
            for (Path path : globalNode.getComingout()) {
                if (matchesRequiraments(path)) {
                    node.getIncoming().add(path);
                }
            }
        }

    }

    private boolean nodeIsInElipse(Elipse e, Node node) {
        return e.isInElipse(node.getPosition());
    }

    private Node cloneWithoutPaths(Node node) {
        return new Node(node.getId(), node.getName(), node.getGeoPoint());
    }

    private boolean matchesRequiraments(Path path) {
        if (possibleDayChange) {
            return nodesMap.containsKey(path.toId)
                    && ((path.dayType == startDayType && path.startTime >= startTime)
                    || (path.dayType == maxEndDayType && path.endTime < maxEndTime));
        }
        return nodesMap.containsKey(path.toId) && path.dayType == startDayType
                && path.startTime >= startTime && path.endTime < maxEndTime;
    }

    private class Elipse {

        final Position center;
        final double a;
        final double b;

        Elipse(Position start, Position end) {
            center = new Position((start.x + end.x) / 2, (start.y + end.y) / 2);
            a = DynamicProgrammingUtils.distance(start, end);
            b = a / 2;
        }

        boolean isInElipse(Position p) {
            return 1 == Math.pow(p.x - center.x, 2) / Math.pow(a, 2)
                    + Math.pow(p.y - center.y, 2) / Math.pow(b, 2);
        }
    }

    public HashMap<String, Node> getNodesMap() {
        return nodesMap;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public boolean isPossibleDayChange() {
        return possibleDayChange;
    }

    public DayType getStartDayType() {
        return startDayType;
    }

    public DayType getMaxEndDayType() {
        return maxEndDayType;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getMaxEndTime() {
        return maxEndTime;
    }

}
