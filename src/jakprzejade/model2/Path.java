package jakprzejade.model2;

/**
 * Container representing possible way you can take from given node (eg. from Sokola take bus D at
 * 900 to stop Halera, which takes 5 minute)
 *
 * @author KonradOliwer
 */
public class Path {

    public DayType dayType;
    public String line; //line identifier
    public int startTime; //time of day in minutes
    public int endTime;  //time of day in minutes
    public String fromId;
    public String toId;
}
