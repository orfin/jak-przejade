/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.importer.zdik;

import jakprzejade.importer.Importer;
import jakprzejade.importer.Repository;
import jakprzejade.utils.ZipExtractor;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class ZdikImporter extends Importer {

    private static final String BASE_PATH = jakprzejade.Configuration.IMPORTER_ZDIK_PATH;
    private static final String URL = "http://www.um.wroc.pl/zdikzip/rozklady_xml.zip";
    private static final String DATE_FORMAT = "yyyyMMdd-HHmmss";

    @Override
    public Repository importRepository() throws Exception {
        long timeStart;

        String outputName = getOutputName();
        String zipName = outputName + ".zip";

        makeDir();

        getLogger().log(Level.INFO, "Downloading from \"{0}\" to \"{1}\"", new String[]{URL, zipName});

        timeStart = System.currentTimeMillis();
        org.apache.commons.io.FileUtils.copyURLToFile(new URL(URL), new File(zipName));

        getLogger().log(Level.INFO, "Downloading completed in {0} ms", "" + getTimeTotal(timeStart));


        getLogger().log(Level.INFO, "Extracting ZIP \"{0}\" to \"{1}\"", new String[]{zipName, outputName});

        timeStart = System.currentTimeMillis();
        ZipExtractor.extract(zipName, outputName, true);

        getLogger().log(Level.INFO, "Extracting ZIP completed in {0} ms", "" + getTimeTotal(timeStart));


        getLogger().log(Level.INFO, "Starting creating repository...");

        timeStart = System.currentTimeMillis();
        Repository repo = new SchedulesParser(outputName).parseFiles();

        getLogger().log(Level.INFO, "Repository completed in {0} ms \r\nBusStops: {1}, Vehicles: {2}, Routes: {3}", new String[]{"" + getTimeTotal(timeStart), "" + repo.busStops.size(), "" + repo.vehicles.size(), "" + repo.routes.size()});

        return repo;
    }

    private String getOutputName() {
        return BASE_PATH + "/" + getDateString() + "-rozklady";
    }

    private String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();

        return dateFormat.format(date);
//        return "20140524-130056";
    }

    private void makeDir() {
        File baseDir = new File(BASE_PATH);

        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
    }
}
