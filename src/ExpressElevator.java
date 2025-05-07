import java.util.ArrayList;
import java.util.Arrays;

public class ExpressElevator extends Elevator{
    boolean[] skippedFloors = new boolean[Building.NUMBER_OF_FLOORS];

    public ExpressElevator(Building building, int bank, int[] choseFloorsToSkip) {
        super(building, bank);
        setSkippedFloors(choseFloorsToSkip);
    }

    public void setSkippedFloors(int[] choseFloorsToSkip) {
        Arrays.fill(skippedFloors, false);

        for (int floorLevel : choseFloorsToSkip) {
            if (floorLevel >= 0 && floorLevel < Building.NUMBER_OF_FLOORS) {
                skippedFloors[floorLevel] = true;
            }
        }
    }

    @Override
    public void loadElevator(){
        ArrayList<Person> peopleWaiting = building.floors[currentFloor].peopleWaiting;

        int originalSize = peopleWaiting.size();
        for (int i = 0; i < originalSize; i++){
            if (peopleOnElevator.size() >= maxElevatorCapacity){
                return;
            }

            Person person = peopleWaiting.removeFirst();
            if (skippedFloors[person.desiredFloor]){
                peopleWaiting.add(person);
                System.out.println(bank + " Can't take destination: " + person.desiredFloor);
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

    @Override
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
                    if (person.desiredFloor > currentFloor && person.desiredFloor < min && !skippedFloors[person.desiredFloor]){
                        min = person.desiredFloor;

                        if (!foundMin){
                            foundMin = true;
                        }
                    }
                }

                // Linear scan floors above for UP button pressed
                for (int i = currentFloor + 1; i < Building.NUMBER_OF_FLOORS; i++){
                    if (checkTargetAlreadyTaken(i)){
                        continue;
                    }

                    if (building.floors[i].isUpPressed && !skippedFloors[i]){
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
                        if (building.floors[i].isDownPressed && !skippedFloors[i]){
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
                    if (person.desiredFloor < currentFloor && person.desiredFloor > max  && !skippedFloors[person.desiredFloor]){
                        max = person.desiredFloor;

                        if (!foundMax){
                            foundMax = true;
                        }
                    }
                }

                // Linear scan floors below for DOWN button pressed
                for (int i = currentFloor - 1; i >= 0; i--){
                    if (checkTargetAlreadyTaken(i)){
                        continue;
                    }

                    if (building.floors[i].isDownPressed && !skippedFloors[i]){
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
                        if (building.floors[i].isUpPressed && !skippedFloors[i]){
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
        if (nextFloorTarget != -1){
            finishedNextFloorTick = Building.LEVELS_TRAVELLED_TO_TIME[Math.abs(currentFloor - nextFloorTarget)];
        }
        else {
            // Handle G and Top floor
            isGoingUp = !isGoingUp;
            loadElevator();
            if (peopleOnElevator.isEmpty()){
                noMoreTargets = true;
            }
            else{
                getToNextFloorStart();
            }
        }
    }
}