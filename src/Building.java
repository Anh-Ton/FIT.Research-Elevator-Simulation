import java.util.Random;

public class Building {
    public final static int NUMBER_OF_FLOORS = 12;
    Floor[] floors = new Floor[NUMBER_OF_FLOORS];
    Elevator[] elevators = new Elevator[7];

    public Building(){
        for (int i = 0; i < floors.length; i++){
            floors[i] = new Floor(floors.length - i - 1);
        }

        for (int i = 0; i < elevators.length; i++){
            elevators[i] = new Elevator();

            Random random = new Random();
            int randomInt = random.nextInt(NUMBER_OF_FLOORS);

            floors[randomInt].setElevator(i, elevators[i]);
        }
    }

    @Override
    public String toString() {
        String returnString = "";
        for (Floor floor : floors){
            returnString += floor.toString() + "\n\n";
        }
        return returnString;
    }
}
