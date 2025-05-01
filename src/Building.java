import java.util.ArrayList;
import java.util.Random;

public class Building {
    public final static int NUMBER_OF_FLOORS = 12;
    Floor[] floors = new Floor[NUMBER_OF_FLOORS];
    Elevator[] elevators = new Elevator[7];

    public Building(){
        for (int i = 0; i < floors.length; i++){
            floors[i] = new Floor(i);
        }

        for (int i = 0; i < elevators.length; i++){
            elevators[i] = new Elevator();

            Random random = new Random();
            int randomInt = random.nextInt(NUMBER_OF_FLOORS);

            floors[randomInt].setElevator(i, elevators[i]);
            elevators[i].setCurrentFloor(randomInt);
        }
    }

    public void moveElevator(int bank, int newFloor){
        int currentFloor = elevators[bank].getCurrentFloor();
        floors[currentFloor].setElevator(bank,null);
        floors[newFloor].setElevator(bank, elevators[bank]);

        elevators[bank].setCurrentFloor(newFloor);
    }

    public void loadElevator(int bank){
        Elevator currentElevator = elevators[bank];
        int currentFloor = currentElevator.getCurrentFloor();
        ArrayList<Person> floorElevatorQueue = floors[currentFloor].getFloorElevatorQueue();

        while (!floorElevatorQueue.isEmpty() && !currentElevator.isAtMaxCapacity()){
            currentElevator.addPerson(floorElevatorQueue.removeFirst());
        }
    }

    public void unloadElevator(int bank){
        Elevator currentElevator = elevators[bank];
        int currentFloor = currentElevator.getCurrentFloor();
        ArrayList<Person> finished = floors[currentFloor].getFinished();

        while (!currentElevator.isEmpty()){
            finished.add(currentElevator.removePerson());
        }
    }

    @Override
    public String toString() {
        String returnString = "";
        for (int i = floors.length - 1; i >= 0; i--){
            returnString += floors[i].toString() + "\n\n";
        }
        return returnString;
    }
}
