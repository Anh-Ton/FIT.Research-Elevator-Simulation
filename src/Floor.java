import java.util.ArrayList;

public class Floor {
    Elevator[] Banks = new Elevator[7];
    ArrayList<Person> elevatorQueues = new ArrayList<>();
    int floorLevel;

    public Floor(int floorLevel){
        for (int i = 0; i < 4; i++) {
            elevatorQueues.add(new Person(floorLevel));
        }
        this.floorLevel = floorLevel;
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
        for(int i = 0; i < Banks.length; i++){
            if (Banks[i] == null){
                returnString += "| |" + i;
            }
            else{
                returnString += "|" + Banks[i].toString() + "|" + i;
            }

            returnString += "  ";
        }

        // Waiting Queue
        returnString += "Waiting: ";

        for (Person person : elevatorQueues){
            returnString += person.toString() + " ";
        }

        return returnString;
    }
}
