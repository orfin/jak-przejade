/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade;

import jakprzejade.importer.Repository;
import jakprzejade.importer.RepositoryToKnowledgeConverter;
import jakprzejade.importer.zdik.ZdikImporter;
import jakprzejade.model2.GlobalKnowledge;
import jakprzejade.model2.Node;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Static handler for database.
 * 
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class RepositoryHandler {

    static private Repository repository;

    static public boolean isInitialized() {
        return repository != null;
    }

    static public boolean initRepository() {
        ZdikImporter importer = new ZdikImporter();

        try {
            repository = importer.importRepository();
            RepositoryToKnowledgeConverter converter = new RepositoryToKnowledgeConverter(repository);
            
            HashMap<String, Node> nodesMap = converter.convert();
            
            GlobalKnowledge.nodesMap.clear();
            GlobalKnowledge.nodesMap.putAll(nodesMap);

            return true;
        } catch (Exception ex) {
            Logger.getLogger(RepositoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    static public Repository getRepository() {
        return repository;
    }
}
