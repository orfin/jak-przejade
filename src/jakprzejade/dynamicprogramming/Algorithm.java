package jakprzejade.dynamicprogramming;

import jakprzejade.dto.FindRouteRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KonradOliwer
 */
public class Algorithm {
    private static final boolean DEBUG = true;

    public List execute(FindRouteRequest frr) {
        Knowledge knowledge = new Knowledge(frr);
        knowledge.getNodes().stream()
                .filter(node -> (node != knowledge.getEnd()))
                .forEach(node -> node.expand());
        List result = new ArrayList();
        return AlgorithmNode.getAllPathsFromStartToEnd(knowledge);
    }
}
