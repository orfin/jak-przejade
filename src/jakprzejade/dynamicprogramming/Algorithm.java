package jakprzejade.dynamicprogramming;

import jakprzejade.dto.FindRouteRequest;
import jakprzejade.dto.FindRouteResponse;
import jakprzejade.model2.GlobalKnowledge;
import jakprzejade.routefinder.RouteFinder;
import java.util.List;
import java.util.Map;

/**
 *
 * @author KonradOliwer
 */
public class Algorithm implements RouteFinder {

    @Override
    public FindRouteResponse findRoute(FindRouteRequest request) {
        return transformToFindRouteResponse(execute(request));
    }

    private AlgorithmResult execute(FindRouteRequest request) {
        Knowledge knowledge = new Knowledge(request);
        knowledge.init();
        for (AlgorithmNode node : knowledge.getNodes()){
            if (node != knowledge.getEnd()){
                node.expand();
            }
        }
        return AlgorithmNode.getAllPathsFromStartToEnd(knowledge);
    }

    private FindRouteResponse transformToFindRouteResponse(AlgorithmResult input) {
        FindRouteResponse response = new FindRouteResponse();
        Map<String, jakprzejade.model2.Node> internalNodesMap = GlobalKnowledge.nodesMap;
        
        return null;
    }
}
