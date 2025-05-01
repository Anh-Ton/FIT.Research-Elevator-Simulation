import java.util.ArrayList;
import java.util.Random;

public class Elevator {
    int maxElevatorCapacity;
    ArrayList<Person> peopleOnElevator = new ArrayList<>();
    boolean isGoingUp;

    public Elevator(){
        Random random = new Random();
        isGoingUp = random.nextBoolean();
        this.updateMaxElevatorCapacity(random);
    }

    public void updateMaxElevatorCapacity(Random random){
        maxElevatorCapacity = random.nextInt(8 - 5) + 5;
    }

    @Override
    public String toString() {
        return "" + peopleOnElevator.size();
    }

    public String longString(){
        return "Going up: " + isGoingUp +
                " | Max elevator capacity: " + maxElevatorCapacity +
                " | Size: " + peopleOnElevator.size();
    }
}
