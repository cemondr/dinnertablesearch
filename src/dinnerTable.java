import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class dinnerTable {

    private static class Pair {
        public int i;
        public int j;

        public Pair (int x, int y){
            i = x;
            j = y;
        }

        public int getX(){
            return i;
        }

        public int getY(){
            return j;
        }

    }

    private int numOfGuest;
    private int tableScore;
    private int [][] guestList;
    LinkedList<Integer> toBeSeated = new LinkedList<Integer>();
    private int [][] dinnerTable;
    private int [] alreadySeated;


    dinnerTable(String guestList){
        getGuestInformation(guestList);
        alreadySeated = new int [numOfGuest];
        dinnerTable = new int[2][numOfGuest/2];
        for (int k = 0; k< 2; k++){
            Arrays.fill(dinnerTable[k], -1);
        }
        tableScore = 0;
        //toBeSeated = new LinkedList<Integer>();
        for (int i= 0; i<numOfGuest; i++){
            toBeSeated.add(i);
        }
        Collections.shuffle(toBeSeated);// Start with a random order;
    }

    private void getGuestInformation(String guestList){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(guestList));
            this.numOfGuest = Integer.parseInt(reader.readLine());
            this.guestList = new int[numOfGuest][numOfGuest];

            for (int i = 0; i < numOfGuest; i++){
                String [] temp = reader.readLine().split(" ");
                for(int j = 0; j< numOfGuest; j++){
                    this.guestList[i][j] = Integer.parseInt(temp[j]);
                }
            }
        }catch(FileNotFoundException fnfe){
            System.err.println("Guest list could not be found in the given location");
        }catch(IOException ioe){
            System.err.println("Unexpected Format");
        }
    }

    public ArrayList<Pair> getAvailableSeats(){
        ArrayList<Pair> availableSeats = new ArrayList<Pair>();
        for (int i = 0; i < 2; i++){
            for (int j = 0; j< numOfGuest/2; j++){
                if(dinnerTable[i][j] == -1){
                    Pair temp = new Pair(i,j);
                    availableSeats.add(temp);
                }
            }
        }
        return availableSeats;
    }

    public boolean isOpposite(int current, int target){
        if (((current >= numOfGuest/2 && current<numOfGuest) && (target < numOfGuest/2 && target>=0)) ||
                ((current< numOfGuest/2 && current >=0) && (target>= numOfGuest/2 && target < numOfGuest))) {
            return true;
        }
        return false;
    }

    public int preferenceValue(int p1, int p2){
        return guestList[p1][p2] + guestList[p2][p1];
    }

    int getNumOfGuest(){
        return numOfGuest;
    }




    public void seatGuest(){
        Random randomGenerator = new Random();
        Pair currentSeat;
        int x = 0;
        int y = 0;

        while(toBeSeated.size()>0){
            Integer currentGuest = toBeSeated.poll();
            ArrayList<Pair> availableSeats = getAvailableSeats();
            int randomNum = randomGenerator.nextInt(availableSeats.size());

            currentSeat= availableSeats.get(randomNum);
            x = currentSeat.getX();
            y = currentSeat.getY();

           // System.out.println("A) x = " + x+ " y =  " + y);
            dinnerTable[x][y] = currentGuest;


            /*Calculate the score of opposite guest  */
            if (x == 0 && dinnerTable[x+1][y]!= -1){
                if(isOpposite(dinnerTable[x][y], dinnerTable[x+1][y])){
                    tableScore+=2;
                }
                tableScore+= preferenceValue(dinnerTable[x][y], dinnerTable[x+1][y]);
            }else if(x == 1 && dinnerTable[x-1][y] != -1){
                if(isOpposite(dinnerTable[x][y], dinnerTable[x-1][y])){
                    tableScore+=2;
                }
                tableScore+= preferenceValue(dinnerTable[x][y], dinnerTable[x-1][y]);
            }

            //System.out.println("B) x = " + x+ " y =  " + y);

            /*Calculate point for the right side guest */
            if (y < dinnerTable[x].length-1){
                if(isOpposite(dinnerTable[x][y],dinnerTable[x][y+1])){
                    tableScore += 2;
                }
                //System.out.println("C) x = " + x+ " y =  " + y);

                if (dinnerTable[x][y+1] != -1){
                    tableScore+=preferenceValue(dinnerTable[x][y], dinnerTable[x][y+1]);
                }
            }

            /*Calculate points for the left side of guest */
            if (y > 0){
                if(isOpposite(dinnerTable[x][y],dinnerTable[x][y-1])){
                    tableScore += 2;
                }
                //System.out.println("C) x = " + x+ " y =  " + y);

                if (dinnerTable[x][y-1] != -1){
                    tableScore+=preferenceValue(dinnerTable[x][y], dinnerTable[x][y-1]);
                }
            }

        }
    }

    int  getTableScore(){
        return tableScore;
    }

    void displayDinnerTable(){

        for (int[] guest : dinnerTable) {
            for (int aGuest : guest) {
                System.out.print(aGuest + " ");
            }
            System.out.println();
        }


    }

    void displayGuests(){
        for (int[] guest : guestList) {
            for (int aGuest : guest) {
                System.out.print(aGuest + " ");
            }
            System.out.println();
        }
    }

    public static void main(String [] args){
        dinnerTable table = new dinnerTable(args[0]);
        table.seatGuest();
        table.displayDinnerTable();
        System.out.println();
        System.out.println("Score: "+table.getTableScore());
    }


}
