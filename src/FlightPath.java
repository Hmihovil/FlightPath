import java.util.*;
import java.nio.file.*;
import java.io.*;

public class FlightPath{
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("\nWelcome to the flight searcher!\nThank you for choosing USAir.");

        System.out.println("\nWhat is the name of the cityFile?");
        String cityFile = in.nextLine();
        System.out.println("What is the name of the flightFile?");
        String flightFile = in.nextLine();
        LinkedList[] flights = cityReader(cityFile);
        flights = flightReader(flights,flightFile);

        System.out.println(flights);

        String repeat;

        do {
            System.out.println("\nWhat city would you like to depart from?");
            String start = in.nextLine();
            System.out.println("What city would you like to fly to?");
            String dest = in.nextLine();

            findFlight(start,dest,flights);

            System.out.println("Would you like to perform another flight search?");
            repeat = in.nextLine();

        }while(willRepeat(repeat));

        System.out.println("\nThank you for searching, please come again.");
    }

    private static boolean willRepeat(String userEntry){
        if(userEntry.equalsIgnoreCase("y")|userEntry.equalsIgnoreCase("yes")|userEntry.substring(0,1).equalsIgnoreCase("y")){
            return true;
        }
        else{
            return false;
        }
    }

    private static LinkedList[] cityReader(String cityFile){
        long lineCount = 0;
        File file = new File(cityFile);
        Path path = Paths.get(cityFile);

        try{
            lineCount = Files.lines(path).count();
        }
        catch(IOException e){}

        System.out.println((int)lineCount);
        LinkedList[] flight = new LinkedList[(int)lineCount];
        try{
            BufferedReader freader = new BufferedReader(new FileReader(file));
            for(int i = 0; i < lineCount; i++){
                flight[i] = new LinkedList();
                flight[i].append(freader.readLine(),0);
            }
        }
        catch(FileNotFoundException e){}
        catch(IOException e){}

        return flight;
    }

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
        catch(IOException e){}
        return flight;
    }

    private static int findCity(String c, LinkedList[] f){
        for(int i = 0; i < f.length; i++){
            if(c.equals(f[i].nameAt(1))){
                return i;
            }
        }
        return -1;
    }

    private static int indexUnusedLowestVal(int[] S, int[] D){
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for(int i = 1; i <= D.length; i++){
            boolean inS = false;
            for(int j = 0; j <= S.length; j++){
                if(S[j]==i){
                    inS = true;
                    break;
                }
            }
            if(inS){
                continue;
            }
            if(D[i] < min){
                min = D[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private static void findFlight(String a, String b, LinkedList[] ll){
        int[] S = new int[ll.length+1];
        int[] D = new int[ll.length+1];
        int[] P = new int[ll.length+1];

        int source = -1;
        int dest = -1;

        for(int j = 0; j < ll.length; j++){
            if(a.equalsIgnoreCase(ll[j].nameAt(1))){
                source = j;
            }
            if(b.equalsIgnoreCase(ll[j].nameAt(1))){
                dest = j;
            }
        }
        if(source == -1){
            System.out.println("\nThe entered starting location does not offer any departing flights.");
            return;
        }
        if(source == dest){
            System.out.println("\nIt doesn't cost anything to stay where you are.");
            return;
        }

        S[1] = source;
        for(int m = 0; m < P.length; m++){
            P[m] = source;
        }

        for(int i = 1; i < ll.length+1; i++){
            D[i] = ll[i].costOfDest(b);
        }
        for(int i = 1; i < ll.length+1; i++){
            int w = indexUnusedLowestVal(S,D);
            S[i+1] = w;

            for(int j = 1; j <= ll[w-1].listLength(); j++){
                //pick back up here
                ll[w-1].nameAt(j);
            }
        }
    }
}