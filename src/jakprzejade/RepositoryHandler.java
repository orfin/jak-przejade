/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade;

import jakprzejade.importer.Repository;
import jakprzejade.importer.zdik.ZdikImporter;
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
