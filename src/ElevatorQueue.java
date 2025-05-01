public class ElevatorQueue {
    int queueLength;

    public ElevatorQueue(int startingLength) {
        queueLength = startingLength;
    }

    @Override
    public String toString() {
        return "" + queueLength;
    }
}
