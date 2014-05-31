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
    private static final String URL_SCHEDULES = "http://www.um.wroc.pl/zdikzip/rozklady_xml.zip";
    private static final String URL_LOCATIONS = "http://geoportal.wroclaw.pl/www/pliki/KomunikacjaZbiorowa/SlupkiWspolrzedne.txt";
    
    
    private static final String DATE_FORMAT = "yyyyMMdd-HHmmss";

    @Override
    public Repository importRepository() throws Exception {
        long timeStart;

        String outputName = getOutputName();
        String zipName = outputName + ".zip";
        String locationsName = outputName + ".txt";

        makeDir();

        getLogger().log(Level.INFO, "Downloading schedules from \"{0}\" to \"{1}\"", new String[]{URL_SCHEDULES, zipName});

        timeStart = System.currentTimeMillis();
        org.apache.commons.io.FileUtils.copyURLToFile(new URL(URL_SCHEDULES), new File(zipName));

        getLogger().log(Level.INFO, "Downloading completed in {0} ms", "" + getTimeTotal(timeStart));


        getLogger().log(Level.INFO, "Extracting ZIP \"{0}\" to \"{1}\"", new String[]{zipName, outputName});

        timeStart = System.currentTimeMillis();
        ZipExtractor.extract(zipName, outputName, true);

        getLogger().log(Level.INFO, "Extracting ZIP completed in {0} ms", "" + getTimeTotal(timeStart));


        getLogger().log(Level.INFO, "Starting creating repository...");

        timeStart = System.currentTimeMillis();
        Repository repo = new SchedulesParser(outputName).parseFiles();

        getLogger().log(Level.INFO, "Repository completed in {0} ms \r\nBusStops: {1}, Vehicles: {2}, Routes: {3}", new String[]{"" + getTimeTotal(timeStart), "" + repo.busStops.size(), "" + repo.vehicles.size(), "" + repo.routes.size()});

        
        getLogger().log(Level.INFO, "Downloading bus stops locations from \"{0}\" to \"{1}\"", new String[]{URL_LOCATIONS, locationsName});

        timeStart = System.currentTimeMillis();
        org.apache.commons.io.FileUtils.copyURLToFile(new URL(URL_LOCATIONS), new File(locationsName));

        getLogger().log(Level.INFO, "Downloading completed in {0} ms", "" + getTimeTotal(timeStart));
        
        
        getLogger().log(Level.INFO, "Starting setting locations of bus stops...");

        timeStart = System.currentTimeMillis();
        new BusStopsLocationsParser(locationsName, repo).parseFile();

        getLogger().log(Level.INFO, "Setting locations completed in {0} ms", new String[]{"" + getTimeTotal(timeStart)});

        
        return repo;
    }

    private String getOutputName() {
        return BASE_PATH + "/" + getDateString() + "-rozklady";
    }

    private String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();

        return dateFormat.format(date);
//        return "20140531-163338";
    }

    private void makeDir() {
        File baseDir = new File(BASE_PATH);

        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
    }
}
