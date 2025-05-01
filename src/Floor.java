import java.util.ArrayList;

public class Floor {
    // Display only
    Elevator[] banks = new Elevator[7];

    ArrayList<Person> elevatorQueues = new ArrayList<>();
    int floorLevel;

    public Floor(int floorLevel){
        for (int i = 0; i < 4; i++) {
            elevatorQueues.add(new Person(floorLevel));
        }
        this.floorLevel = floorLevel;
    }

    public void setElevator(int bank, Elevator elevator){
        banks[bank] = elevator;
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

        return returnString;
    }
}
