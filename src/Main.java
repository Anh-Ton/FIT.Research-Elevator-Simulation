public class Main {
    public static void main(String[] args) {
        Building menziesBuilding = new Building();

        System.out.println(menziesBuilding);

        for (Elevator elevator: menziesBuilding.elevators){
            elevator.loadElevator();
        }

        System.out.println(menziesBuilding);

        menziesBuilding.elevators[0].moveElevator(11);

        System.out.println(menziesBuilding);
    }
}