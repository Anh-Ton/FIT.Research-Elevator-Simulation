import java.util.Random;
import java.util.Scanner;

public class Building {
    public final static int NUMBER_OF_FLOORS = 12;
    // DATA: 0, 17.78, 19.12, 20.23, 21.53, 23.21, 25.12, 26.43, 27.98, 30.52, 32.33, 33.78
    public final static int[] LEVELS_TRAVELLED_TO_TIME= {0, 18, 19, 20, 22, 23, 25, 26, 28, 31, 32, 34};
    public Floor[] floors = new Floor[NUMBER_OF_FLOORS];
    public Elevator[] elevators = new Elevator[7];
    public int secondElapsed;

    public Building(){
        // Initialise all floors
        for (int i = 0; i < floors.length; i++){
            floors[i] = new Floor(i);
            // Update up down buttons
            floors[i].updateUpDownButtons();
        }

        // Set and create all elevators to random level in each bank
        for (int i = 0; i < elevators.length; i++){
            elevators[i] = new Elevator(this, i);

            Random random = new Random();
            int randomInt = random.nextInt(NUMBER_OF_FLOORS);

            floors[randomInt].setElevator(i, elevators[i]);
            elevators[i].currentFloor = randomInt;
        }

        secondElapsed = 0;
    }

    public void runWithVisuals(){
        Scanner scanner = new Scanner(System.in);

        while (!isComplete()){
            secondElapsed++;

            System.out.println("            ~ " + secondElapsed + " seconds have passed ~\n");

            for (Elevator elevator: elevators){
                elevator.tick();
            }

            System.out.println(this);
//            scanner.nextLine();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n\nIt took a total of " + secondElapsed + " seconds to move everyone");
    }

    public void run(){
        while (!isComplete()){
            secondElapsed++;

            for (Elevator elevator: elevators){
                elevator.tick();
            }
        }
    }

    public boolean isComplete(){
        for (Floor floor : floors){
            if (!floor.isFloorFinished()){
                return false;
            }
        }

        for (Elevator elevator : elevators){
            if(!elevator.isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String returnString = "";
        for (int i = floors.length - 1; i >= 0; i--){
            returnString += floors[i].toString() + "\n\n";
        }

        for (Elevator elevator: elevators){
            returnString += elevator.longString() + '\n';
        }

        return returnString;
    }
}
