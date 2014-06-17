/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jakprzejade.importer.zdik;

import jakprzejade.importer.Repository;
import jakprzejade.model.BusStop;
import jakprzejade.model.Route;
import jakprzejade.model.Schedule;
import jakprzejade.model.ScheduleTime;
import jakprzejade.model.Stop;
import jakprzejade.model.Vehicle;
import jakprzejade.model2.DayType;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class ScheduleXMLParser extends DefaultHandler {

    private Repository repo;
    private File file;
    
    private Vehicle vehicle;
    private BusStop busStop;
    private Route route;
    private Stop stop;
    private Schedule schedule;
    private ScheduleTime scheduleTime;
    
    private String temp;

    public ScheduleXMLParser(Repository repo, File file) {
        this.repo = repo;
        this.file = file;
    }

    public Repository parseFile() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spfac = SAXParserFactory.newInstance();
        SAXParser sp = spfac.newSAXParser();
        sp.parse(file, this);
        
        return repo;
    }

    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);
    }


    /*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        temp = "";
        
        if (qName.equalsIgnoreCase("linia")) {
            vehicle = new Vehicle(
                    attributes.getValue("nazwa"), 
                    Vehicle.VehicleType.TRAM,   // todo how to decide if bus or tram?
                    getLineType(attributes.getValue("typ"))
            );
            
            repo.addVehicle(vehicle);
        }
        
        if (qName.equalsIgnoreCase("wariant")) {
            route = new Route(vehicle);
        }
        
        if (qName.equalsIgnoreCase("przystanek")) {
            busStop = new BusStop(
                    attributes.getValue("id"),
                    attributes.getValue("nazwa"), 
                    attributes.getValue("ulica")
            );
            
            repo.addBusStop(busStop);
            
            stop = new Stop(busStop, vehicle);
            busStop.addStop(stop);
            route.addStop(stop);
        }
        
        if (qName.equalsIgnoreCase("dzien")) {
            schedule = new Schedule(getDayType(attributes.getValue("nazwa")));
        }
        
        if (qName.equalsIgnoreCase("godz")) {
            scheduleTime = new ScheduleTime(Integer.parseInt(attributes.getValue("h")));
        }
        
        if (qName.equalsIgnoreCase("min")) {
            scheduleTime.addMinute(Integer.parseInt(attributes.getValue("m")));
        }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("wariant")) {
            repo.addRoute(route);
        }
        
        if (qName.equalsIgnoreCase("dzien")) {
            stop.addSchedule(schedule);
        }
        
        if (qName.equalsIgnoreCase("godz")) {
            schedule.addTime(scheduleTime);
        }
    }
    
    private static Vehicle.LineType getLineType(String typeString) {
        String lowered = typeString.toLowerCase();
        
        if (lowered.equals("linia normalna")) {
            return Vehicle.LineType.REGULAR;
        }
        
        if (lowered.equals("linia okresowa")) {
            return Vehicle.LineType.TEMPORARY;
        }
        
        if (lowered.equals("linia pospieszna")) {
            return Vehicle.LineType.EXPRESS;
        }
        
        if (lowered.equals("linia przyspieszona")) {
            return Vehicle.LineType.REGULAR;
        }
        
        if (lowered.equals("linia podmiejska")) {
            return Vehicle.LineType.SUBURBAN;
        }
        
        if (lowered.equals("linia strefowa")) {
            return Vehicle.LineType.ZONE;
        }
        
        if (lowered.contains("linia nocna")) {
            return Vehicle.LineType.NIGHT;
        }
        
        return Vehicle.LineType.REGULAR;
    }
    
    private static DayType getDayType(String typeString) {
        if (typeString.equalsIgnoreCase("Sobota")) {
            return DayType.SUTURDAY;
        }
        
        if (typeString.equalsIgnoreCase("Niedziela")) {
            return DayType.SUNDAY;
        }
        
        if (typeString.equalsIgnoreCase("w dni robocze")) {
            return DayType.WEEK_DAY;
        }
        
        return DayType.WEEK_DAY;
    }
}
