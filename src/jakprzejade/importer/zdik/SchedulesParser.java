/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.importer.zdik;

import jakprzejade.importer.Importer;
import jakprzejade.importer.Repository;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class SchedulesParser {

    private String basePath;
    private Repository repo;

    public SchedulesParser(String basePath) {
        this.basePath = basePath;
        repo = new Repository();
    }

    public Repository parseFiles() throws Exception {
        Collection<File> scheduleFiles = getSheduleFiles();

        for (File file : scheduleFiles) {
            Importer.getLogger().log(Level.INFO, "Parsing file \"{0}\"", file.getName());
            
            if (file.getName().equalsIgnoreCase("rozklad.xml")) {
                Importer.getLogger().log(Level.INFO, "Passing");
                continue;
            }
            
            try {
                new ScheduleXMLParser(repo, file).parseFile();
            } catch (Exception e) {
                Importer.getLogger().log(Level.FINER, "Error!");
                e.printStackTrace();
            }
        }

        return repo;
    }

    private Collection<File> getSheduleFiles() {
        Collection<File> files = FileUtils.listFiles(
                FileUtils.getFile(basePath),
                FileFilterUtils.suffixFileFilter(".xml"),
                DirectoryFileFilter.DIRECTORY);

        return files;
    }
}
