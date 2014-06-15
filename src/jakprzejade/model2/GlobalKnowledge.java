package jakprzejade.model2;

import jakprzejade.RepositoryHandler;
import jakprzejade.importer.Repository;
import jakprzejade.importer.RepositoryToKnowledgeConverter;
import jakprzejade.importer.zdik.ZdikImporter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KonradOliwer
 */
public class GlobalKnowledge {

    public static HashMap<String, Node> nodesMap;

    public static List<Node> getNodeList() {
        return new ArrayList(nodesMap.values());
    }
    
    public static boolean isInitialized() {
        return nodesMap != null;
    }

    public static boolean initNodes() {
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
}
