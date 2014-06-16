package jakprzejade.model2;

import jakprzejade.Configuration;
import jakprzejade.RepositoryHandler;
import jakprzejade.importer.Repository;
import jakprzejade.importer.RepositoryToKnowledgeConverter;
import jakprzejade.importer.zdik.ZdikImporter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KonradOliwer
 */
public class GlobalKnowledge {

    public static HashMap<String, Node> nodesMap;
    private static boolean initialized = false;

    public static List<Node> getNodeList() {
        return new ArrayList(nodesMap.values());
    }

    public static boolean isInitialized() {
        return initialized;
    }
    
    public static boolean init() {
        boolean success;
        success = initNodes();
        success = success && addByFootPaths();
        initialized = success;
        System.gc();
        return success;
    }

    private static boolean initNodes() {
        ZdikImporter importer = new ZdikImporter();

        try {
            Repository repository = importer.importRepository();
            RepositoryToKnowledgeConverter converter = new RepositoryToKnowledgeConverter(repository);

            nodesMap = converter.convert();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(RepositoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean addByFootPaths() {
        List<Node> nodes = new ArrayList<Node>(nodesMap.values());
        for (int i = 0; i < nodes.size(); i++) {
            Node n1 = nodes.get(i);
            Circle range = new Circle(n1.getPosition(), Configuration.MAX_DISTANCE_TO_WALK);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node n2 = nodes.get(j);
                if (range.isNotOutside(n2.getPosition())){
                    n1.addPaths(Path.getPathByFootBetween(n1, n2));
                    n2.addPaths(Path.getPathByFootBetween(n2, n1));
                }
            }
        }
        return true;
    }
}
