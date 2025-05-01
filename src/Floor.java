import java.util.ArrayList;

public class Floor {
    Elevator[] banks = new Elevator[7];
    ArrayList<Person> elevatorQueues = new ArrayList<>();
    ArrayList<Person> finished = new ArrayList<>();
    int floorLevel;
    boolean isUpButtonPressed;
    boolean isDownButtonPressed;


    public Floor(int floorLevel){
        for (int i = 0; i < 6; i++) {
            elevatorQueues.add(new Person(floorLevel));
        }
        this.floorLevel = floorLevel;
        this.isUpButtonPressed = false;
        this.isDownButtonPressed = false;
    }

    public void setElevator(int bank, Elevator elevator){
        banks[bank] = elevator;
    }

    public ArrayList<Person> getFloorElevatorQueue(){
        return elevatorQueues;
    }

    public ArrayList<Person> getFinished(){
        return finished;
    }

    @Override
    public String toString() {
        String returnString = "";

        // Floor Level
        if (floorLevel == 0){
            returnString += String.format("%3s", "G") + ")  ";
        }
        else {
            returnString += String.format("%3s", floorLevel) + ")  ";
        }

        // Banks
        for(int i = 0; i < banks.length; i++){
            if (banks[i] == null){
                returnString += "|   |" + i;
            }
            else{
                returnString += "| " + banks[i].toString() + " |" + i;
            }

            returnString += "   ";
        }

        // Waiting Queue
        returnString += "Waiting: ";

        for (Person person : elevatorQueues){
            returnString += person.toString() + " ";
        }

        // Finished
        returnString += "Finished: ";

        for (Person person : finished){
            returnString += person.toString() + " ";
        }

        return returnString;
    }
}
