/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.importer.zdik;

import jakprzejade.importer.Importer;
import jakprzejade.importer.Repository;
import jakprzejade.model.BusStop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;

/**
 * Sample schema:
 * długości i szerokości geograficznej, ID słupka, typie przystanku (tramwajowy - 0; autobusowy - 3, tramwajowo autobusowy - 03).
 * longitude;latitude;bus stop ID;type
 * 
 * 17,09871941;51,11386787;24324;3
 * 17,09871941;51,11386787;24324;3
 * 17,08217030;51,11140173;24312;3
 * 17,12194787;51,11982726;24725;3
 * 
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class BusStopsLocationsParser {

    private String basePath;
    private Repository repo;

    public BusStopsLocationsParser(String basePath, Repository repo) {
        this.basePath = basePath;
        this.repo = repo;
    }

    public Repository parseFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(basePath));
        String line;
        int currentLine = 0;
        
        while ((line = br.readLine()) != null) {
            parseLine(line, currentLine);
            currentLine++;
        }
        
        br.close();
        
        return repo;
    }

    private void parseLine(String line, int currentLine) throws Exception {
        String[] params;
        params = line.split(";");
        
        if (params.length != 4) {
            throw new Exception("Line params mismatch: \"" + line + "\" at line " + currentLine);
        }
        
        double longitude = Double.parseDouble(params[0].replace(',', '.'));
        double latitude = Double.parseDouble(params[1].replace(',', '.'));
        String busStopId = params[2];
        
        BusStop busStop = getBusStopById(busStopId);
        
        if (busStop == null) {
            Importer.getLogger().log(Level.FINE, "Passing! Couldn''t find bus stop with id: \"{0}\" at line {1}", new Object[]{busStopId, currentLine});
        } else {
            busStop.setLocation(longitude, latitude);
        }
    }
    
    private BusStop getBusStopById(String id) {
        return repo.busStops.get(id);
    }
}
