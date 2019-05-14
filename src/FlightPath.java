/*
Scott Spinali
Shaydon Bodemar

Program that determines the cheapest flight from a given source to a given destination,
using files fed in by the user.

5 - 6 - 2019
 */

import java.util.*;
import java.nio.file.*;
import java.io.*;

public class FlightPath{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String newFiles = null;
        do {
            System.out.println("\n\n\nWelcome to the flight searcher!\nThank you for choosing USAir.");
            System.out.println("\nWhat is the name of the cityFile?");
            String cityFile = in.nextLine();
            System.out.println("What is the name of the flightFile?");
            String flightFile = in.nextLine();
            LinkedList[] flights = cityReader(cityFile);
            if(flights != null){
                flights = flightReader(flights, flightFile);
            }
            else{
                newFiles = "y";
                continue;
            }
            if (flights != null) {
                String repeat = null;
                do {
                    System.out.println("\nWhat city would you like to depart from?");
                    String start = in.nextLine();
                    System.out.println("What city would you like to fly to?");
                    String dest = in.nextLine();

                    findFlight(start, dest, flights);

                    System.out.println("\nWould you like to perform another flight search? (Say no if you'd like to exit or enter new files.)");
                    repeat = in.nextLine();

                } while (willRepeat(repeat));
            }
            else{
                continue;
            }
            System.out.println("Would you like to enter new files for searching?");
            newFiles = in.nextLine();
        }while(willRepeat(newFiles));
        System.out.println("\nThank you for your interest in USAir, please come again.");
    }//main method which handles the passing of values around to the primary file reading and data handler methods, and the UI

    private static boolean willRepeat(String userEntry){
        if(userEntry == null){
            return false;
        }
        else if(userEntry.equalsIgnoreCase("y")|userEntry.equalsIgnoreCase("yes")|userEntry.substring(0,1).equalsIgnoreCase("y")){
            return true;
        }
        else{
            return false;
        }
    }//determines if the user-input String indicated a desire to repeat the loop

    private static LinkedList[] cityReader(String cityFile){
        long lineCount = 0;
        File file = new File(cityFile);
        Path path = Paths.get(cityFile);

        try{
            lineCount = Files.lines(path).count();
        }
        catch(IOException e){}

        LinkedList[] flight = new LinkedList[(int)lineCount];
        try{
            BufferedReader freader = new BufferedReader(new FileReader(file));
            for(int i = 0; i < lineCount; i++){
                flight[i] = new LinkedList();
                flight[i].append(freader.readLine(),0);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("City file could not be found.");
            return null;
        }
        catch(IOException e){
            System.out.println("City file format is incorrect.");
            return null;
        }
        return flight;
    }//reads the city file and returns the LinkedList array with all the origin locations set

    private static LinkedList[] flightReader(LinkedList[] flight, String flightFile){
        File file = new File(flightFile);
        try{
            BufferedReader freader = new BufferedReader(new FileReader(file));
            String nextLine;
            while((nextLine = freader.readLine()) != null){
                String[] temp = nextLine.split(",");
                int location = findCity(temp[0],flight);
                if(location == -1){
                    continue;
                }
                flight[location].append(temp[1], Integer.parseInt(temp[2]));
            }
        }
        catch(FileNotFoundException e){
            System.out.println("\nFlight file could not be found.");
            return null;
        }
        catch(IOException e){
            System.out.println("\nFlight file format is incorrect.");
            return null;
        }
        return flight;
    }//reads the flight file and returns the LinkedList array with all outgoing flights included for every origin

    private static void backtrack(int[] P, int source, int dest, LinkedList[] ll){
        System.out.println("");
        boolean notDone = true;
        int length = 1;
        int temp = dest;
        int cost = 0;

        //have a looping condition to break out of this loop if there are cycles
        while(notDone){
            length++;
            if(P[temp] == source){
                notDone = false;
            }
            else{
                temp = P[temp];
            }
        }

        int[] flightpath = new int[length];
        temp = dest;
        for(int i = flightpath.length-1; i >= 0; i--){
            flightpath[i] = temp;
            temp = P[temp];
        }

        for(int i = 1; i < flightpath.length; i++){
            int tempCost = ll[flightpath[i-1]-1].costOfDest(ll[flightpath[i]-1].nameAt(1));
            System.out.println("Flight from " + ll[flightpath[i-1]-1].nameAt(1) + " to " + ll[flightpath[i]-1].nameAt(1) + " \tCost: $" + tempCost);
            cost += tempCost;
        }
        System.out.println("Total Cost................................ $" + cost);
    }//goes backwards through the P array once it is complete and finds then prints the path from source to dest

    private static int findCity(String c, LinkedList[] f){
        for(int i = 0; i < f.length; i++){
            if(c.equals(f[i].nameAt(1))){
                return i;
            }
        }
        return -1;
    }//finds the location of origin cities, and returns -1 if not present

    private static boolean sContains(int[] S, int v){
        for(int i = 0; i < S.length; i++){
            if(S[i]==v){
                return true;
            }
        }
        return false;
    }//determines if the vertex has been places in S[] yet

    private static int indexUnusedLowestVal(int[] S, int[] D){
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for(int i = 1; i < D.length; i++){
            boolean inS = false;
            for(int j = 0; j < S.length; j++){
                if(S[j]==i){
                    inS = true;
                    break;
                }
            }
            if(inS){
                continue;
            }
            if(D[i] <= min){
                min = D[i];
                minIndex = i;
            }
        }
        return minIndex;
    }//finds the cheapest flight from the source that has not yet been determined (put in S[])

    private static void findFlight(String a, String b, LinkedList[] ll){
        int[] S = new int[ll.length+1];
        int[] D = new int[ll.length+1];
        int[] P = new int[ll.length+1];

        int source = -1;
        int dest = -1;

        for(int j = 0; j < ll.length; j++){
            if(a.equalsIgnoreCase(ll[j].nameAt(1))){
                source = j+1;
            }
            if(b.equalsIgnoreCase(ll[j].nameAt(1))){
                dest = j+1;
            }
            if(source != -1 && dest != -1){
                break;
            }
        }

        if(source == -1){
            System.out.println("\nUSAir does not offer any departing flights from " + a + ".");
            return;
        }
        if(dest == -1){
            System.out.println("\nUSAir does not offer any flights to " + b + ".");
            return;
        }
        if(source == dest){
            System.out.println("\nIt doesn't cost anything to stay in " + a + ".");
            return;
        }

        for(int i = 1; i <= ll.length; i++){
            D[i] = Integer.MAX_VALUE;
        }
        D[source] = 0;
        for(int i = 1; i < ll.length; i++){
            int w = indexUnusedLowestVal(S,D);
            S[i] = w;

            for(int j = 2; j <= ll[w-1].listLength(); j++){
                String vName = ll[w-1].nameAt(j);
                int v = findCity(vName,ll);

                if(!sContains(S,v+1)){
                    int test1 = D[v+1];
                    int test2 = D[w]+ll[w-1].costOfDest(vName);
                    if(test2 < 0){
                        test2 = Integer.MAX_VALUE;
                    }

                    if(test2 < test1){
                        D[v+1] = test2;
                        P[v+1] = w;
                    }
                }
            }
        }
        backtrack(P,source,dest,ll);
    }//using Dijkstra's algorithm, finds the cheapest path to each of the possible destinations from the given source
}
