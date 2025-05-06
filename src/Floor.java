import java.util.ArrayList;

public class Floor {
    public Elevator[] banks = new Elevator[7];
    public ArrayList<Person> peopleWaiting = new ArrayList<>();
    public ArrayList<Person> peopleAtDestination = new ArrayList<>();
    public int floorLevel;
    public boolean isUpPressed;
    public boolean isDownPressed;


    public Floor(int floorLevel){
        for (int i = 0; i < 6; i++) {
            peopleWaiting.add(new Person(floorLevel));
        }
        this.floorLevel = floorLevel;
        this.isUpPressed = false;
        this.isDownPressed = false;
    }

    public void setElevator(int bank, Elevator elevator){
        banks[bank] = elevator;
    }

    public void updateUpDownButtons(){
        for (Person person : peopleWaiting){
            if (isDownPressed && isUpPressed){
                return;
            }

            if (person.desiredFloor < floorLevel){
                isDownPressed = true;
            }
            else if (person.desiredFloor > floorLevel){
                isUpPressed = true;
            }
        }
    }

    public boolean isFloorFinished(){
        return peopleWaiting.isEmpty();
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

        // UP and DOWN buttons
        returnString += "(";
        if (isUpPressed){
            returnString += "UP";
        }
        else {
            returnString += "-";
        }
        returnString += "|";
        if (isDownPressed){
            returnString += "DOWN";
        }
        else {
            returnString += "-";
        }
        returnString += ") ";

        // Waiting Queue
        returnString += "Waiting: ";

        for (Person person : peopleWaiting){
            returnString += person.toString() + " ";
        }

        // Finished
        returnString += "Finished: ";

        for (Person person : peopleAtDestination){
            returnString += person.toString() + " ";
        }

        return returnString;
    }
}
