import java.util.Random;

public class Person {
    int desiredFloor;

    public Person(int startingFloor) {
        Random random = new Random();

        if (startingFloor == 0) {
            int randomFloor = random.nextInt(Building.NUMBER_OF_FLOORS - 1) + 1;
            desiredFloor = randomFloor;
        }
        else {
            Double randomDouble = random.nextDouble();
            if (randomDouble < 0.7) {
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

        if (startingFloor == desiredFloor){
            throw new java.lang.Error("this is very bad: " + desiredFloor + " when sf is " + startingFloor);
        }
    }

    @Override
    public String toString() {
        return "P(" + desiredFloor + ")";
    }
}
