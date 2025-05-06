public class Main {
    public static void main(String[] args) {
        Building menziesBuilding = new Building();

        System.out.println(menziesBuilding);

        for (Elevator elevator: menziesBuilding.elevators){
            elevator.loadElevator();
        }


        for (Elevator elevator: menziesBuilding.elevators){
            elevator.getNextFloor();
        }

        System.out.println(menziesBuilding);
    }
}