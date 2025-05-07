import java.util.Random;

public class Person {
    public int desiredFloor;

    public Person(int startingFloor) {
        Random random = new Random();

        if (startingFloor == 0) {
            int randomFloor = random.nextInt(Building.NUMBER_OF_FLOORS - 1) + 1;
            desiredFloor = randomFloor;
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
    }

    @Override
    public String toString() {
        return "P(" + desiredFloor + ")";
    }
}
