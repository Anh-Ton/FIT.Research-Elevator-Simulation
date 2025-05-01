public class Building {
    public final static int NUMBER_OF_FLOORS = 12;
    Floor[] floors = new Floor[NUMBER_OF_FLOORS];

    public Building(){
        for (int i = 0; i < floors.length; i++){
            floors[i] = new Floor(floors.length - i - 1);
        }
    }

    @Override
    public String toString() {
        String returnString = "";
        for (Floor floor : floors){
            returnString += floor.toString() + "\n\n";
        }
        return returnString;
    }
}
