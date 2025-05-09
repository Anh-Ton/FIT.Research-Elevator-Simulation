import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Person {
    public int desiredFloor;
    public boolean wantsExpress;
    public boolean[] wantedBanks;

    public Person(int startingFloor, ArrayList<boolean[]> boolSkipFloorsArray) {
        Random random = new Random();

        if (startingFloor == 0) {
            desiredFloor = random.nextInt(Building.NUMBER_OF_FLOORS - 1) + 1;
        }
        else {
            double randomDouble = random.nextDouble();
            if (randomDouble < 0.3) {
                desiredFloor = 0;
            }
            else{
                int randomFloor = random.nextInt((Building.NUMBER_OF_FLOORS - 1) - 1) + 1;
                if (randomFloor >= startingFloor) {
                    desiredFloor = randomFloor + 1;
                }
                else{
                    desiredFloor = randomFloor;
                }
            }
        }

        updateWantsExpress(startingFloor, boolSkipFloorsArray);
    }

    public void updateWantsExpress(int startingFloor, ArrayList<boolean[]> boolSkipFloorsArray) {
        wantsExpress = false;
        wantedBanks = new boolean[Building.NUMBER_OF_ELEVATORS];
        Arrays.fill(wantedBanks, false);

        for (int i = 0; i < boolSkipFloorsArray.size(); i++) {
            boolean[] elevatorSkippedFloors = boolSkipFloorsArray.get(i);

            boolean allFalse = true;
            for (boolean elevatorSkippedFloor : elevatorSkippedFloors) {

                if (elevatorSkippedFloor) {
                    allFalse = false;
                    break;
                }
            }
            if (allFalse){
                continue;
            }

            if (!elevatorSkippedFloors[desiredFloor] && !elevatorSkippedFloors[startingFloor]) {
                // Check if we need to reverse IntStream
//                if (startingFloor < desiredFloor) {
//                    for (int floor: IntStream.rangeClosed(startingFloor, desiredFloor).toArray()){
//                        if (elevatorSkippedFloors[floor]) {
//                            wantsExpress = true;
//                            wantedBanks[i] = true;
//                        }
//                    }
//                }
//
//                else {
//                    for (int floor: IntStream.rangeClosed(desiredFloor, startingFloor).toArray()){
//                        if (elevatorSkippedFloors[floor]) {
//                            wantsExpress = true;
//                            wantedBanks[i] = true;
//                        }
//                    }
//                }

                wantsExpress = true;
                wantedBanks[i] = true;

            }
        }

    }

    @Override
    public String toString() {
        String returnString = "P(" + desiredFloor;

        if (wantsExpress) {
            returnString += " E";
        }

        for (int i = 0; i < wantedBanks.length; i++) {
            if (wantedBanks[i]) {
                returnString += "-B" + i;
            }
        }

        returnString += ")";

        return returnString;
    }
}
