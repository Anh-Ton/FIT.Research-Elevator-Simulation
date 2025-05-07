import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static final int SIMULATION_ITERATIONS_PER_RATIO = 500;
    public static final int MAX_CELL_ROW_LENGTH = 50;

    public static void main(String[] args) {

        Building menziesBuilding = new Building();
        menziesBuilding.runWithVisuals();

//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter("simulation_results.csv"));
//            for (int i = 0; i < SIMULATION_ITERATIONS_PER_RATIO; i++) {
//                if (i % MAX_CELL_ROW_LENGTH == 0) {
//                    writer.write("\n");
//                }
//
//                Building menziesBuilding = new Building();
//                menziesBuilding.run();
//                writer.write(menziesBuilding.secondElapsed + ",");
//            }
//            writer.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}