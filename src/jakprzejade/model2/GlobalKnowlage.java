package jakprzejade.model2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author KonradOliwer
 */
public class GlobalKnowlage {
    public final static HashMap<String, Node> nodesMap = new HashMap();
    
    public static List getNodeList(){
        return new ArrayList(nodesMap.values());
    }
}
