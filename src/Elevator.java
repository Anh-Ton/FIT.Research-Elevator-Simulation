import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Elevator {
    public final Building building;

    //Capacity
    public int maxElevatorCapacity;
    public ArrayList<Person> peopleOnElevator = new ArrayList<>();

    // State
    public boolean isGoingUp;
    public int currentTick;
    public int finishedNextFloorTick;
    public boolean noMoreTargets;
    public boolean[] skippedFloors = new boolean[Main.NUMBER_OF_FLOORS];
    public boolean isExpress;

    // Location
    public int currentFloor;
    public final int bank;

    // Target
    public int nextFloorTarget;

    public Elevator(Building building, int bank, int[] choseFloorsToSkip){
        // Randomise SCAN direction
        Random random = new Random();
        isGoingUp = random.nextBoolean();

        // Randomise Initial Capacity
        this.updateMaxElevatorCapacity(random);

        this.currentFloor = 0;
        this.building = building;
        this.bank = bank;
        this.currentTick = -1;
        this.noMoreTargets = false;

        setSkippedFloors(choseFloorsToSkip);
    }

    public void setSkippedFloors(int[] choseFloorsToSkip) {
        Arrays.fill(skippedFloors, false);
        isExpress = false;

        for (int floorLevel : choseFloorsToSkip) {
            if (floorLevel >= 0 && floorLevel < Main.NUMBER_OF_FLOORS) {
                skippedFloors[floorLevel] = true;
                isExpress = true;
            }
        }
    }

    public void tick(){
        if (currentTick == -1){
            currentTick++;
            getToNextFloorStart();
            return;
        }

        if (currentTick < finishedNextFloorTick){
            currentTick++;
        }
        else {
            getToNextFloorEnd();
        }
    }

    public void getToNextFloorEnd(){
        if (nextFloorTarget == -1){
            return;
        }
        moveElevator(nextFloorTarget);
        unloadElevator();
        currentTick = -1;
        nextFloorTarget = -1;
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
            // Check full
            if (peopleOnElevator.size() >= maxElevatorCapacity){
                return;
            }

            Person person = peopleWaiting.removeFirst();

            // Don't Pick Up Skipped Destination
            if (skippedFloors[person.desiredFloor]){
                peopleWaiting.add(person);
                continue;
            }

            // Don't Pick Up Express Person if Not Express Elevator
            if (person.wantsExpress && !isExpress){
                peopleWaiting.add(person);
                continue;
            }

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
                building.numberOfPeopleFinished++;
                peopleAtDestination.add(person);
            }
            else {
                peopleOnElevator.add(person);
            }
        }
    }

    public void getToNextFloorStart(){
        currentTick = 0;

        // Loop at most twice
        for (int j = 0; j < 2; j++) {
            loadElevator();

            // Going UP logic
            if (isGoingUp){
                int min = Main.NUMBER_OF_FLOORS + 1;
                boolean foundMin = false;

                // Linear scan everyone on elevator for: min that is >currentFloor
                for (Person person : peopleOnElevator){
                    if (person.desiredFloor > currentFloor && person.desiredFloor < min && !skippedFloors[person.desiredFloor]){
                        min = person.desiredFloor;

                        if (!foundMin){
                            foundMin = true;
                        }
                    }
                }

                // Linear scan floors above for UP button pressed
                if (isExpress){
                    for (int i = currentFloor + 1; i < Main.NUMBER_OF_FLOORS; i++){
                        if ((building.floors[i].isUpPressed || building.floors[i].isExpBankUpPressed[bank]) && !skippedFloors[i]){
                            if (building.floors[i].floorLevel < min){
                                min = building.floors[i].floorLevel;

                                if (!foundMin){
                                    foundMin = true;
                                }
                            }
                        }
                    }
                }
                else {
                    for (int i = currentFloor + 1; i < Main.NUMBER_OF_FLOORS; i++){
                        if (building.floors[i].isUpPressed && !skippedFloors[i]){
                            if (building.floors[i].floorLevel < min){
                                min = building.floors[i].floorLevel;

                                if (!foundMin){
                                    foundMin = true;
                                }
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
                    if (isExpress){
                        for (int i = currentFloor + 1; i < Main.NUMBER_OF_FLOORS; i++){
                            if ((building.floors[i].isDownPressed || building.floors[i].isExpBankDownPressed[bank]) && !skippedFloors[i]){
                                if (building.floors[i].floorLevel < min){
                                    min = building.floors[i].floorLevel;

                                    if (!foundMin){
                                        foundMin = true;
                                    }
                                }
                            }
                        }
                    }
                    else {
                        for (int i = currentFloor + 1; i < Main.NUMBER_OF_FLOORS; i++){
                            if (building.floors[i].isDownPressed && !skippedFloors[i]){
                                if (building.floors[i].floorLevel < min){
                                    min = building.floors[i].floorLevel;

                                    if (!foundMin){
                                        foundMin = true;
                                    }
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
                    if (person.desiredFloor < currentFloor && person.desiredFloor > max  && !skippedFloors[person.desiredFloor]){
                        max = person.desiredFloor;

                        if (!foundMax){
                            foundMax = true;
                        }
                    }
                }

                // Linear scan floors below for DOWN button pressed
                if (isExpress){
                    for (int i = currentFloor - 1; i >= 0; i--){
                        if ((building.floors[i].isDownPressed || building.floors[i].isExpBankDownPressed[bank]) && !skippedFloors[i]){
                            if (building.floors[i].floorLevel > max){
                                max = building.floors[i].floorLevel;

                                if (!foundMax){
                                    foundMax = true;
                                }
                            }
                        }
                    }
                }
                else {
                    for (int i = currentFloor - 1; i >= 0; i--){
                        if (building.floors[i].isDownPressed && !skippedFloors[i]){
                            if (building.floors[i].floorLevel > max){
                                max = building.floors[i].floorLevel;

                                if (!foundMax){
                                    foundMax = true;
                                }
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
                    if (isExpress){
                        for (int i = currentFloor - 1; i >= 0; i--){
                            if ((building.floors[i].isUpPressed || building.floors[i].isExpBankUpPressed[bank]) && !skippedFloors[i]){
                                if (building.floors[i].floorLevel > max){
                                    max = building.floors[i].floorLevel;

                                    if (!foundMax){
                                        foundMax = true;
                                    }
                                }
                            }
                        }
                    }
                    else {
                        for (int i = currentFloor - 1; i >= 0; i--){
                            if (building.floors[i].isUpPressed && !skippedFloors[i]){
                                if (building.floors[i].floorLevel > max){
                                    max = building.floors[i].floorLevel;

                                    if (!foundMax){
                                        foundMax = true;
                                    }
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
        if (nextFloorTarget != -1){
            finishedNextFloorTick = Building.LEVELS_TRAVELLED_TO_TIME[Math.abs(currentFloor - nextFloorTarget)];
        }
        else {
            isGoingUp = !isGoingUp;
        }
    }

    public void updateMaxElevatorCapacity(Random random){
        maxElevatorCapacity = random.nextInt(8 - 5) + 5;
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

        if (isExpress){
            returnString += "E-";
        }

        returnString += "Bank " + bank +
                        " | Current floor: " + currentFloor;
        if(nextFloorTarget == -1){
            returnString += " --> No Target";
        }
        else{
            returnString += " --> " + nextFloorTarget;
        }

        if (isGoingUp){
            returnString += " | " + "UP";
        }
        else {
            returnString += " | " + "DOWN";
        }

        returnString += " | Capacity (" + peopleOnElevator.size() +
                        "/" + maxElevatorCapacity + ")";

        if(noMoreTargets){
            returnString += " | STATIONARY";
        }
        else if(currentTick == -1){
            returnString += " | Docked";
        }
        else{
            returnString += " | Tick (" + currentTick +
                    "/" + finishedNextFloorTick + ")";
        }

        returnString += " | People: ";

        for (Person person : peopleOnElevator){
            returnString += person + " ";
        }

        return returnString;
    }
}
