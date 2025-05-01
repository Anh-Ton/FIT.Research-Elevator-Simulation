public class Elevator {
    int currentElevatorCapacity;

    public Elevator(){
        currentElevatorCapacity = 0;
    }

    @Override
    public String toString() {
        return "" + currentElevatorCapacity;
    }
}
