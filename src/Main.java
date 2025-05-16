import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    // -------------------------------------------------------- //
    public final static int NUMBER_OF_FLOORS = 12;
    public final static int NUMBER_OF_ELEVATORS = 7;
    public static final int SIMULATION_ITERATIONS_PER_RATIO = 5000;
//    public static final int MAX_CELL_ROW_LENGTH = 25;
    public static final double PERCENTAGE_COMPLETE_NEEDED = 0.8;

    public static final boolean RUN_WITH_VISUALS = false;

    // If run with visuals
    public static final boolean RUN_WITH_COMPLETION_INCREMENTS = true;
    // -------------------------------------------------------- //

    public static void main(String[] args) {

        System.out.println();

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

//        int[][] twoExpressV2 = {
//                {},
//                {},
//                {},
//                {},
//                {},
//                {1,2,3,4,5,6,7,8},
//                {1,2,3,4,5,6,7,8}
//        };
//        expressConfigurations.add(twoExpressV2);
//        configNames.add("twoExpressV2");

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

//        int[][] threeExpressV2 = {
//                {},
//                {},
//                {},
//                {},
//                {1,2,3,4,5,6,7,8},
//                {1,2,3,4,5,6,7,8},
//                {1,2,3,4,5,6,7,8}
//        };
//        expressConfigurations.add(threeExpressV2);
//        configNames.add("threeExpressV2");

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

//        int[][] fourExpressV2 = {
//                {},
//                {},
//                {},
//                {1,2,3,4,5,6,7,8},
//                {1,2,3,4,5,6,7,8},
//                {1,2,3,4,5,6,7,8},
//                {1,2,3,4,5,6,7,8}
//        };
//        expressConfigurations.add(fourExpressV2);
//        configNames.add("fourExpressV2");

        if (RUN_WITH_VISUALS) {
            for (int[][] expressConfiguration : expressConfigurations) {
                for (int i = 0; i < 1; i++) {
                    Building menziesBuilding = new Building(expressConfiguration, PERCENTAGE_COMPLETE_NEEDED);
                    menziesBuilding.runWithVisuals();
                }
            }
        }

        else{

            if (RUN_WITH_COMPLETION_INCREMENTS) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("simulation_results_increment.csv"));
                    for (int j = 0; j < expressConfigurations.size(); j++) {

                        writer.write(configNames.get(j));

                        for (double k = 0.1; k <= 1; k += 0.1){
                            writer.write("\n" + (Math.round(k * 100)) + "%\n");

                            for (int i = 0; i < SIMULATION_ITERATIONS_PER_RATIO; i++) {
//                                if (i % MAX_CELL_ROW_LENGTH == 0) {
//                                    writer.write("\n");
//                                }

                                Building menziesBuilding = new Building(expressConfigurations.get(j), k);
                                menziesBuilding.run();
                                writer.write(menziesBuilding.secondElapsed + ",");
                            }

                            writer.write("\n\n");
                        }

                        writer.write("\n\n\n\n\n");
                    }

                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            else {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("simulation_results.csv"));
                    for (int j = 0; j < expressConfigurations.size(); j++) {

                        writer.write(configNames.get(j));
                        for (int i = 0; i < SIMULATION_ITERATIONS_PER_RATIO; i++) {
//                            if (i % MAX_CELL_ROW_LENGTH == 0) {
//                                writer.write("\n");
//                            }

                            Building menziesBuilding = new Building(expressConfigurations.get(j), PERCENTAGE_COMPLETE_NEEDED);
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
}