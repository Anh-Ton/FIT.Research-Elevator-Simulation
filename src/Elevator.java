import java.util.ArrayList;
import java.util.Random;

public class Elevator {
    public Building building;

    //Capacity
    public int maxElevatorCapacity;
    public ArrayList<Person> peopleOnElevator = new ArrayList<>();

    // State
    public boolean isGoingUp;
    public int currentTick;
    public int finishedNextFloorTick;

    // Location
    public int currentFloor;
    public int bank;

    // Target
    public int nextFloorTarget;

    public Elevator(Building building, int bank){
        // Randomise SCAN direction
        Random random = new Random();
        isGoingUp = random.nextBoolean();

        // Randomise Initial Capacity
        this.updateMaxElevatorCapacity(random);

        this.currentFloor = 0;
        this.building = building;
        this.bank = bank;
    }

    public void moveElevator(int newFloor){
        building.floors[currentFloor].setElevator(bank, null);
        building.floors[newFloor].setElevator(bank, this);
        this.currentFloor = newFloor;
    }

    public void loadElevator(){
        ArrayList<Person> peopleWaiting = building.floors[currentFloor].peopleWaiting;

        int originalSize = peopleWaiting.size();
        for (int i = 0; i < originalSize; i++){
            if (peopleOnElevator.size() >= maxElevatorCapacity){
                return;
            }

            Person person = peopleWaiting.removeFirst();
            if (isGoingUp){
                if (person.desiredFloor > currentFloor){
                    peopleOnElevator.add(person);
                }
                else {
                    peopleWaiting.add(person);
                }
            }
            else {
                if (person.desiredFloor < currentFloor){
                    peopleOnElevator.add(person);
                }
                else {
                    peopleWaiting.add(person);
                }
            }
        }
    }

    public void updateMaxElevatorCapacity(Random random){
        maxElevatorCapacity = random.nextInt(8 - 5) + 5;
    }

    public boolean isAtMaxCapacity(){
        return this.peopleOnElevator.size() >= this.maxElevatorCapacity;
    }

    public boolean isEmpty(){
        return this.peopleOnElevator.isEmpty();
    }














    @Override
    public String toString() {
        return "" + peopleOnElevator.size();
    }

    public String longString(){
        String returnString = "";

        returnString += "Bank " + bank +
                        " | Current floor: " + currentFloor +
                        " --> " + nextFloorTarget;

        if (isGoingUp){
            returnString += " | " + "UP";
        }
        else {
            returnString += " | " + "DOWN";
        }

        returnString += " | Capacity (" + peopleOnElevator.size() +
                        "/" + maxElevatorCapacity + ")";

        returnString += " | Tick (" + currentTick +
                "/" + finishedNextFloorTick + ")";

        returnString += " | People: ";

        for (Person person : peopleOnElevator){
            returnString += person + " ";
        }

        return returnString;
    }
}
