import java.util.ArrayList;
import java.util.Arrays;

public class Floor {
    public Elevator[] banks = new Elevator[Building.NUMBER_OF_ELEVATORS];
    public ArrayList<Person> peopleWaiting = new ArrayList<>();
    public ArrayList<Person> peopleAtDestination = new ArrayList<>();
    public int floorLevel;

    // Buttons
    public boolean isUpPressed;
    public boolean isDownPressed;
    public boolean[] isExpBankUpPressed;
    public boolean[] isExpBankDownPressed;


    public Floor(int floorLevel, ArrayList<boolean[]> boolSkipFloorsArray){
        this.floorLevel = floorLevel;

        this.isUpPressed = false;
        this.isDownPressed = false;
        this.isExpBankUpPressed = new boolean[Building.NUMBER_OF_ELEVATORS];
        this.isExpBankDownPressed = new boolean[Building.NUMBER_OF_ELEVATORS];

        Arrays.fill(isExpBankUpPressed, false);
        Arrays.fill(isExpBankDownPressed, false);

        if (floorLevel == 0){
            for (int i = 0; i < 9; i++) {
                peopleWaiting.add(new Person(floorLevel, boolSkipFloorsArray));
            }
        }
        else{
            for (int i = 0; i < 3; i++) {
                peopleWaiting.add(new Person(floorLevel, boolSkipFloorsArray));
            }
        }
    }

    public void setElevator(int bank, Elevator elevator){
        banks[bank] = elevator;
    }

    public void updateUpDownButtons(){
        this.isUpPressed = false;
        this.isDownPressed = false;

        Arrays.fill(isExpBankUpPressed, false);
        Arrays.fill(isExpBankDownPressed, false);

        for (Person person : peopleWaiting){

            if (person.wantsExpress){
                if (person.desiredFloor < floorLevel){

                    for (int i = 0; i < person.wantedBanks.length; i++){
                        if (person.wantedBanks[i]){
                            isExpBankDownPressed[i] = true;
                        }
                    }
                }
                else if (person.desiredFloor > floorLevel){

                    for (int i = 0; i < person.wantedBanks.length; i++){
                        if (person.wantedBanks[i]){
                            isExpBankUpPressed[i] = true;
                        }
                    }
                }
            }
            else {
                if (person.desiredFloor < floorLevel){
                    isDownPressed = true;
                }
                else if (person.desiredFloor > floorLevel){
                    isUpPressed = true;
                }
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
                returnString += "|(" + banks[i].toString() + ")|" + i;
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

        // EXPRESS UP and DOWN buttons V2
        for (int i = 0; i < isExpBankUpPressed.length; i++){
            if (isExpBankUpPressed[i] || isExpBankDownPressed[i]){
                returnString += "(";
                if (isExpBankUpPressed[i]){
                    returnString += "B" + i + "-UP";
                }
                else {
                    returnString += "-";
                }
                returnString += "|";
                if (isExpBankDownPressed[i]){
                    returnString += "B" + i + "-DOWN";
                }
                else {
                    returnString += "-";
                }
                returnString += ") ";
            }
        }

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
