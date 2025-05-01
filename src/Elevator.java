import java.util.ArrayList;
import java.util.Random;

public class Elevator {
    int maxElevatorCapacity;
    ArrayList<Person> peopleOnElevator = new ArrayList<>();
    boolean isGoingUp;
    int currentFloor;

    public Elevator(){
        Random random = new Random();
        isGoingUp = random.nextBoolean();
        this.updateMaxElevatorCapacity(random);
        this.currentFloor = 0;
    }

    public void updateMaxElevatorCapacity(Random random){
        maxElevatorCapacity = random.nextInt(8 - 5) + 5;
    }

    public void setCurrentFloor(int currentFloor){
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public boolean isAtMaxCapacity(){
        return this.peopleOnElevator.size() >= this.maxElevatorCapacity;
    }

    public boolean isEmpty(){
        return this.peopleOnElevator.isEmpty();
    }

    public void addPerson(Person person){
        peopleOnElevator.add(person);
    }

    public Person removePerson(){
        Person person = peopleOnElevator.removeFirst();
        return person;
    }

    @Override
    public String toString() {
        return "" + peopleOnElevator.size();
    }

    public String longString(){
        return "Going up: " + isGoingUp +
                " | Max elevator capacity: " + maxElevatorCapacity +
                " | Size: " + peopleOnElevator.size() +
                " | Current floor: " + currentFloor;

    }
}
