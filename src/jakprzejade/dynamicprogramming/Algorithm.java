package jakprzejade.dynamicprogramming;

import jakprzejade.dto.FindRouteRequest;
import jakprzejade.dto.FindRouteResponse;
import jakprzejade.model2.GlobalKnowledge;
import jakprzejade.routefinder.RouteFinder;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author KonradOliwer
 */
public class Algorithm implements RouteFinder {

    @Override
    public FindRouteResponse findRoute(FindRouteRequest request) {
        long start = System.nanoTime();
        AlgorithmResult ar = execute(request);
        long end = System.nanoTime();
        Logger.getAnonymousLogger().info(String.format("Alorith execution time: %d",
                (end - start) / 1000000000));
        return transformToFindRouteResponse(ar);
    }

    private AlgorithmResult execute(FindRouteRequest request) {
        Knowledge knowledge = new Knowledge(request);
        knowledge.init();
        for (int i = 0; i < knowledge.getNodes().size(); i++) {
            AlgorithmNode node = knowledge.getNodes().get(i);
            node.expand();
            System.gc();
//            Logger.getAnonymousLogger().info(String.format("Done: %d/%d", i, 
//                    knowledge.getNodes().size()));
        }
        Logger.getAnonymousLogger().info("Expand all nodes");
        AlgorithmResult result = AlgorithmNode.getAllPathsFromStartToEnd(knowledge);
        return result;
    }

    private FindRouteResponse transformToFindRouteResponse(AlgorithmResult input) {
        FindRouteResponse response = new FindRouteResponse();
        Map<String, jakprzejade.model2.Node> internalNodesMap = GlobalKnowledge.nodesMap;

        throw new UnsupportedOperationException("Not supported yet.");
    }
}
