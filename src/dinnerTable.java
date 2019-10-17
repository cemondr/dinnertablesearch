import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class dinnerTable {

    private int numOfGuest;
    private int [][] guests;


    dinnerTable(String guestList){
        getGuestInformation(guestList);
    }

    private void getGuestInformation(String guestList){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(guestList));
            this.numOfGuest = Integer.parseInt(reader.readLine());
            guests = new int[numOfGuest][numOfGuest];

            for (int i = 0; i < numOfGuest; i++){
                String [] temp = reader.readLine().split(" ");
                for(int j = 0; j< numOfGuest; j++){
                    guests[i][j] = Integer.parseInt(temp[j]);
                }
            }

        }catch(FileNotFoundException fnfe){
            System.err.println("Guest list could not be found in the given location");
        }catch(IOException ioe){
            System.err.println("Unexpected Format");
        }
    }

    int getNumOfGuest(){
        return numOfGuest;
    }

    void displayGuests(){
        for (int[] guest : guests) {
            for (int aGuest : guest) {
                System.out.print(aGuest + " ");
            }
            System.out.println();
        }
    }

    public static void main(String [] args){
        dinnerTable table = new dinnerTable(args[0]);
        table.displayGuests();
    }


}
