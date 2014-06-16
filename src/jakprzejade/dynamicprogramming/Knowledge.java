package jakprzejade.dynamicprogramming;

import jakprzejade.Utils;
import jakprzejade.dto.FindRouteRequest;
import jakprzejade.model2.DayType;
import jakprzejade.model2.GlobalKnowledge;
import jakprzejade.model2.Node;
import jakprzejade.model2.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
    private final FindRouteRequest frr;
    private final HashMap<String, AlgorithmNode> nodesMap;
    private final List<AlgorithmNode> nodes;
    private boolean possibleDayChange;
    private DayType startDayType;
    private DayType maxEndDayType;
    private int startTime;
    private int maxEndTime;
    private AlgorithmNode start;
    private AlgorithmNode end;

    public Knowledge(FindRouteRequest frr) {
        this.frr = frr;
        nodesMap = new HashMap();
        nodes = new ArrayList();
    }

    public void init() {
        addStartAndEndNode(frr);
        addTimeFrames(frr);
        addSutableNodes();
        initializeNodes();
    }

    private void addStartAndEndNode(FindRouteRequest frr) {
        start = new AlgorithmNode(new Node(START_NODE_ID, START_NODE_NAME, frr.from), this);
        nodesMap.put(start.getId(), start);
        nodes.add(start);

        end = new AlgorithmNode(new Node(END_NODE_ID, END_NODE_NAME, frr.to), this);
        nodesMap.put(end.getId(), end);
        nodes.add(end);

        Path fromStartToEnd = Path.getPathByFootBetween(start, end);
        start.setPathToEnd(fromStartToEnd);
    }

    private void addSutableNodes() {
        Elipse searchArea = new Elipse(start.getPosition(), end.getPosition());
        for (Node node : GlobalKnowledge.getNodeList()) {
            if (nodeIsInElipse(searchArea, node)) {

                AlgorithmNode algorithmNode = new AlgorithmNode(node, this);

                nodesMap.put(algorithmNode.getId(), algorithmNode);
                nodes.add(algorithmNode);

                algorithmNode.setPathToEnd(Path.getPathByFootBetween(algorithmNode, end));
                start.addPaths(Path.getPathByFootBetween(start, algorithmNode));
            }
        }
    }

    private void addTimeFrames(FindRouteRequest frr) {
        startTime = frr.date.get(Calendar.HOUR) * 60 + frr.date.get(Calendar.MINUTE);
        maxEndTime = startTime + Utils
                .calculateByFoot(start.getPosition(), end.getPosition());
        possibleDayChange = maxEndTime > 1440;

        startDayType = DayType.getDayType(frr.date.get(Calendar.DAY_OF_WEEK));
        if (possibleDayChange) {
            maxEndTime -= 1440;
            maxEndDayType = DayType.getDayType(frr.date.get(Calendar.DAY_OF_WEEK) + 1);
        } else {
            maxEndDayType = startDayType;
        }
        start.setBestGetHereTime(startTime);
        end.setBestGetHereTime(start.getBestGetHereTime() + start.getPathToEnd().timeCost + 1);
    }

    private boolean nodeIsInElipse(Elipse e, Node node) {
        return e.isInElipse(node.getPosition());
    }

    private void initializeNodes() {
        for (AlgorithmNode algorithmNode : nodes) {
            algorithmNode.init();
            if (algorithmNode != start && algorithmNode != end) {
                algorithmNode.setBestGetHereTime(Integer.MAX_VALUE - 1);
            }
        }
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

    public HashMap<String, AlgorithmNode> getNodesMap() {
        return nodesMap;
    }

    public List<AlgorithmNode> getNodes() {
        return nodes;
    }

    public AlgorithmNode getStart() {
        return start;
    }

    public AlgorithmNode getEnd() {
        return end;
    }

}
