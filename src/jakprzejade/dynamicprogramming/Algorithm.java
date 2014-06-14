package jakprzejade.dynamicprogramming;

import jakprzejade.dto.FindRouteRequest;
import java.util.List;

/**
 *
 * @author KonradOliwer
 */
public class Algorithm {

    public List execute(FindRouteRequest frr) {
        Knowledge knowledge = new Knowledge(frr);
        knowledge.getNodes().stream()
                .filter(node -> (node != knowledge.getEnd()))
                .forEach(node -> node.expand());
        return AlgorithmNode.getAllPathsFromStartToEnd(knowledge);
    }
}
