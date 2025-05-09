import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static final int SIMULATION_ITERATIONS_PER_RATIO = 3000;
    public static final int MAX_CELL_ROW_LENGTH = 50;

    public static void main(String[] args) {

        System.out.println();

        // -------------------------- //
        boolean runWithVisuals = false;
        // -------------------------- //

        ArrayList<int[][]> expressConfigurations = new ArrayList<>();
        ArrayList<String> configNames = new ArrayList<>();

        int[][] noExpress = {
                {},
                {},
                {},
                {},
                {},
                {},
                {}
        };
        expressConfigurations.add(noExpress);
        configNames.add("noExpress");

        int[][] oneExpress = {
                {},
                {},
                {},
                {},
                {},
                {},
                {1,2,3,4,5,6,7,8}
        };
        expressConfigurations.add(oneExpress);
        configNames.add("oneExpress");

        int[][] twoExpress = {
                {},
                {},
                {},
                {},
                {},
                {1,2,3,4,5, 9,10,11},
                {1,2,3,4,5,6,7,8}
        };
        expressConfigurations.add(twoExpress);
        configNames.add("twoExpress");

        int[][] twoExpressV2 = {
                {},
                {},
                {},
                {},
                {},
                {1,2,3,4,5,6,7,8},
                {1,2,3,4,5,6,7,8}
        };
        expressConfigurations.add(twoExpressV2);
        configNames.add("twoExpressV2");

        int[][] threeExpress = {
                {},
                {},
                {},
                {},
                {1,2, 6,7,8,9,10,11},
                {1,2,3,4,5, 9,10,11},
                {1,2,3,4,5,6,7,8}
        };
        expressConfigurations.add(threeExpress);
        configNames.add("threeExpress");

        int[][] threeExpressV2 = {
                {},
                {},
                {},
                {},
                {1,2,3,4,5,6,7,8},
                {1,2,3,4,5,6,7,8},
                {1,2,3,4,5,6,7,8}
        };
        expressConfigurations.add(threeExpressV2);
        configNames.add("threeExpressV2");

        int[][] fourExpress = {
                {},
                {},
                {},
                {1,2,3 ,6,7,8,9,10,11},
                {1,2,3,4,5, 8,9,10,11},
                {1,2,3,4,5,6,7, 10,11},
                {1,2,3,4,5,6,7,8,9 }
        };
        expressConfigurations.add(fourExpress);
        configNames.add("fourExpress");

        int[][] fourExpressV2 = {
                {},
                {},
                {},
                {1,2,3,4,5,6,7,8},
                {1,2,3,4,5,6,7,8},
                {1,2,3,4,5,6,7,8},
                {1,2,3,4,5,6,7,8}
        };
        expressConfigurations.add(threeExpressV2);
        configNames.add("threeExpressV2");

        if (runWithVisuals) {
            for (int[][] expressConfiguration : expressConfigurations) {
                for (int i = 0; i < 1; i++) {
                    Building menziesBuilding = new Building(expressConfiguration);
                    menziesBuilding.runWithVisuals();
                }
            }
        }

        else{
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("simulation_results.csv"));
                for (int j = 0; j < expressConfigurations.size(); j++) {

                    writer.write(configNames.get(j));
                    for (int i = 0; i < SIMULATION_ITERATIONS_PER_RATIO; i++) {
                        if (i % MAX_CELL_ROW_LENGTH == 0) {
                            writer.write("\n");
                        }

                        Building menziesBuilding = new Building(expressConfigurations.get(j));
                        menziesBuilding.run();
                        writer.write(menziesBuilding.secondElapsed + ",");
                    }

                    writer.write("\n\n\n");
                }

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}