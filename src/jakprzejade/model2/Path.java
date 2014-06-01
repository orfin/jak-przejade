package jakprzejade.model2;

/**
 * Container representing possible way you can take from given node 
 * (eg. from Sokola take bus D at 900 to stop Halera, which takes 5 minute)
 *
 * @author KonradOliwer
 */
public class Path {

    String line; //line identifier
    int startTime; //time of day in minutes
    int last; //how long it takes to get to target node
    Node from;
    Node to;
}
