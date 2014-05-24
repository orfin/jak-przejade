package jakprzejade.importer;

import java.util.logging.Logger;

/**
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public abstract class Importer {

    public static Logger getLogger() {
        return Logger.getLogger("importer");
    }
    
    public static long getTimeTotal(long start) {
        return System.currentTimeMillis() - start;
    }

    abstract public Repository importRepository() throws Exception;
}
