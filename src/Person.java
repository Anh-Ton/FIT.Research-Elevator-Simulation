import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class Person {
    public int desiredFloor;
    public boolean wantsExpress;

    public Person(int startingFloor, ArrayList<boolean[]> boolSkipFloorsArray) {
        Random random = new Random();

        if (startingFloor == 0) {
            desiredFloor = random.nextInt(Building.NUMBER_OF_FLOORS - 1) + 1;
        }
        else {
            double randomDouble = random.nextDouble();
            if (randomDouble < 0.4) {
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

        for (boolean[] elevatorSkippedFloors : boolSkipFloorsArray) {

            if (!elevatorSkippedFloors[desiredFloor] && !elevatorSkippedFloors[startingFloor]) {
                // Check if we need to reverse IntStream
                if (startingFloor < desiredFloor) {
                    for (int floor: IntStream.rangeClosed(startingFloor, desiredFloor).toArray()){
                        if (elevatorSkippedFloors[floor]) {
                            wantsExpress = true;
                            return;
                        }
                    }
                }

                else {
                    for (int floor: IntStream.rangeClosed(desiredFloor, startingFloor).toArray()){
                        if (elevatorSkippedFloors[floor]) {
                            wantsExpress = true;
                            return;
                        }
                    }
                }

            }
        }

    }

    @Override
    public String toString() {
        String returnString = "P(" + desiredFloor;

        if (wantsExpress) {
            returnString += " E";
        }

        returnString += ")";

        return returnString;
    }
}
