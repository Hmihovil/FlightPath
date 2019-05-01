import java.util.*;
import java.nio.file.*;
import java.io.*;

public class FlightPath{
    public static void main(String[] args) {


        System.out.println("\nWelcome to the flight searcher!\nThank you for choosing USAir.");

        System.out.println()
        LinkedList[] flights = cityReader();
        flights = flightReader(flights);

        String repeat;

        do {
            Scanner in = new Scanner(System.in);
            System.out.println("\nWhat city would you like to depart from?");
            String start = in.nextLine();
            System.out.println("What city would you like to fly to?");
            String dest = in.nextLine();

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

    private static LinkedList[] cityReader(){
        long lineCount = 0;
        File file = new File("cityFile");
        Path path = Paths.get("cityFile");

        try{
            lineCount = Files.lines(path).count();
        }
        catch(IOException e){}

        LinkedList[] flight = new LinkedList[(int)lineCount];
        try{
            BufferedReader freader = new BufferedReader(new FileReader(file));
            for(int i = 0; i < lineCount; i++){
                flight[i].append(freader.readLine(),0);
            }
        }
        catch(FileNotFoundException e){}
        catch(IOException e){}

        return flight;
    }

    private static LinkedList[] flightReader(LinkedList[] flight){

    }
}