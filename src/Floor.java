public class Floor {
    Elevator[] Banks = new Elevator[7];
    ElevatorQueue elevatorQueues;
    int floorLevel;

    public Floor(int floorLevel){
        elevatorQueues = new ElevatorQueue(0);
        this.floorLevel = floorLevel;
    }

    @Override
    public String toString() {
        String returnString = "";

        if (floorLevel == 0){
            returnString += String.format("%3s", "G") + ")  ";
        }
        else {
            returnString += String.format("%3s", floorLevel) + ")  ";
        }


        for(int i = 0; i < Banks.length; i++){
            if (Banks[i] == null){
                returnString += "| |" + i;
            }
            else{
                returnString += "|" + Banks[i].toString() + "|" + i;
            }

            returnString += "  ";
        }
        returnString += "Waiting: " + elevatorQueues.toString();
        return returnString;
    }
}
