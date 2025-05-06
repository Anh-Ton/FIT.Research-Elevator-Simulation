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

    public void getToNextFloorEnd(){
        moveElevator(nextFloorTarget);
        unloadElevator();
        currentTick = -1;
    }

    public void moveElevator(int newFloor){
        // Make current null (as null represents no elevator)
        building.floors[currentFloor].setElevator(bank, null);

        // Set elevator object at new floor
        building.floors[newFloor].setElevator(bank, this);

        // Update attribute
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

        building.floors[currentFloor].updateUpDownButtons();
    }

    public void unloadElevator(){
        ArrayList<Person> peopleAtDestination = building.floors[currentFloor].peopleAtDestination;
        int originalSize = peopleOnElevator.size();

        for (int i = 0; i < originalSize; i++){
            Person person = peopleOnElevator.removeFirst();

            if (person.desiredFloor == currentFloor) {
                peopleAtDestination.add(person);
            }
            else {
                peopleOnElevator.add(person);
            }
        }
    }

    public void getToNextFloorStart(){
        loadElevator();

        currentTick = 0;

        // Loop at most twice
        for (int j = 0; j < 2; j++) {
            // Going UP logic
            if (isGoingUp){
                int min = Building.NUMBER_OF_FLOORS + 1;
                boolean foundMin = false;

                // Linear scan everyone on elevator for: min that is >currentFloor
                for (Person person : peopleOnElevator){
                    if (person.desiredFloor > currentFloor && person.desiredFloor < min){
                        min = person.desiredFloor;

                        if (!foundMin){
                            foundMin = true;
                        }
                    }
                }

                // Linear scan floors above for UP button pressed
                for (int i = currentFloor + 1; i < Building.NUMBER_OF_FLOORS; i++){
                    if (building.floors[i].isUpPressed){
                        if (building.floors[i].floorLevel < min){
                            min = building.floors[i].floorLevel;

                            if (!foundMin){
                                foundMin = true;
                            }
                        }
                    }
                }

                // If found, set target
                if (foundMin){
                    nextFloorTarget = min;
                    break;
                }
                else {
                    // Else, search above for DOWN button pressed
                    for (int i = currentFloor + 1; i < Building.NUMBER_OF_FLOORS; i++){
                        if (building.floors[i].isDownPressed){
                            if (building.floors[i].floorLevel < min){
                                min = building.floors[i].floorLevel;

                                if (!foundMin){
                                    foundMin = true;
                                }
                            }
                        }
                    }

                    // Flip direction
                    isGoingUp = false;
                }

                if (foundMin){
                    nextFloorTarget = min;
                    break;
                }
                // Else loop once more if first loop
            }

            // Going DOWN logic
            else {
                int max = -1;
                boolean foundMax = false;

                // Linear scan everyone on elevator for: max that is <currentFloor
                for (Person person : peopleOnElevator){
                    if (person.desiredFloor < currentFloor && person.desiredFloor > max){
                        max = person.desiredFloor;

                        if (!foundMax){
                            foundMax = true;
                        }
                    }
                }

                // Linear scan floors below for DOWN button pressed
                for (int i = currentFloor - 1; i >= 0; i--){
                    if (building.floors[i].isDownPressed){
                        if (building.floors[i].floorLevel > max){
                            max = building.floors[i].floorLevel;

                            if (!foundMax){
                                foundMax = true;
                            }
                        }
                    }
                }

                // If found, set target
                if (foundMax){
                    nextFloorTarget = max;
                    break;
                }
                else {
                    // Else, search below for UP button pressed
                    for (int i = currentFloor - 1; i >= 0; i--){
                        if (building.floors[i].isUpPressed){
                            if (building.floors[i].floorLevel > max){
                                max = building.floors[i].floorLevel;

                                if (!foundMax){
                                    foundMax = true;
                                }
                            }
                        }
                    }
                    // Flip direction
                    isGoingUp = true;
                }

                if (foundMax){
                    nextFloorTarget = max;
                    break;
                }
                // Else loop once more if first loop
            }
        }

        // Update finishedNextFloorTick
        finishedNextFloorTick = Building.LEVELS_TRAVELLED_TO_TIME[Math.abs(currentFloor - nextFloorTarget)];
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
