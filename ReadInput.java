/*******************************************************************************
 * Maciej Nowaczyk
 * Date: 14-04-2020
 * This code is my own work, it was developed without using or copying code
 * from other students or other resources.
 ******************************************************************************/


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import ilog.concert.*;
import ilog.cplex.*;

public class ReadInput {
public static int[][]  readComposition() {
    //        use try...catch... in case file can't be read
    int[][] finalMatrix = new int[1][1];
    try {
        //HERE CHANGE THE NUMBER OF THE INSTANCE

//            read the file
        File file = new File("instance1.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

//            get name
        String name = reader.readLine();
//            get # nominees
        int n = Integer.parseInt(reader.readLine());

//            get 5 lists
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String line = reader.readLine();
//                if multiple elements in a list
            if (line.contains(",")) {
                String[] array = line.split(",");
                lists.add(new ArrayList<>(Arrays.asList(array)));
            }
//                if one element in the list
            else if (line.length() > 0) {
                String[] array = new String[]{line};
                lists.add(new ArrayList<>(Arrays.asList(array)));
            }
//                if no elements in the list
            else {
                lists.add(new ArrayList<>());
            }
        }
//            close the reader after reading all input
        reader.close();

//            fill in the final matrix and print it
        finalMatrix = new int[5][n];
        System.out.println("Final matrix:");

//            for each row (list)
        for (int row = 0; row < 5; row++) {
            ArrayList<String> l = lists.get(row);
//                for each column (nominee)
            for (int col = 0; col < n; col++) {
//                    if the list contains the nominee
                if (l.contains(Integer.toString(col + 1))) {
                    finalMatrix[row][col] = 1;
                } else {
                    finalMatrix[row][col] = 0;
                }
                System.out.print(finalMatrix[row][col] + " ");
            }
            System.out.println();
        }

    } catch (IOException e) {
        e.printStackTrace();
    } return finalMatrix;
}
   public static void SolveILP(){

    try {
        IloCplex cplex = new IloCplex();
        cplex.setOut(null);
        int [][] finalMatrix=readComposition();
        int numberOfcommittes = 5;
        int numberOfpeople = finalMatrix[0].length;
        int []costObjective=new int [numberOfpeople];
        int[] lowerbound=new int[5];
        int[][]composition= new int[5][numberOfpeople];
        for(int  i=0; i<5; i++)
            for(int j=0;j<numberOfpeople;j++)
                composition[i][j]=finalMatrix[i][j];


        //fill the objective values with 1 (coefficients)
        for (int i=0; i<numberOfpeople; i++){
            costObjective[i]=1;
        }
        //create the lower bound for the constraints
        for(int i=0; i<5; i++){
            lowerbound[i]=1;
        }

        // declare variable array: numFoods many integer variables with lower bound 0 and upper bound 100
        //   IloNumVar[] numberServings = cplex.numVarArray(numFoods,0,100,IloNumVarType.Int);
        IloNumVar[] variables = cplex.numVarArray(numberOfpeople,0,1,IloNumVarType.Int); //decision variables
        // add objective function
        IloLinearNumExpr expr1 = cplex.linearNumExpr();
        for (int i = 0; i < numberOfpeople; i++) {
            expr1.addTerm(costObjective[i],variables[i]);
        }
        cplex.addMinimize(expr1);


        //IloRange[] listOfCons= new IloRange[/**/numberOfcommittes];

        // add lower bound for nutrients constraints
        for(int j=0; j < numberOfcommittes; j++){
            IloLinearNumExpr expr2 = cplex.linearNumExpr();
            for (int i = 0; i < numberOfpeople; i++){
                expr2.addTerm(variables[i], composition[j][i]);
            }
            cplex.addGe(expr2,lowerbound[j]);
        }
        for(int i=0; i<numberOfcommittes; i++){
            IloLinearNumExpr expr2 = cplex.linearNumExpr();
        }

        //export
        cplex.exportModel("model.lp");
        // solve ILP
        cplex.solve();

        // output solution
        System.out.println("Optimal value  = " + cplex.getObjValue());

        for (int i = 0; i < numberOfpeople; i++) {
            System.out.println("x"+(i+1)+" = "+cplex.getValue(variables[i]));
        }

        // close cplex object
        cplex.end();
    }
    catch (IloException exc) {
        exc.printStackTrace();
    }

}
    public static void main(String[] args){
        double startTime = System.currentTimeMillis();
        SolveILP();
        double endTime = System.currentTimeMillis();
        double runningTime = (endTime - startTime);
        System.out.println("Running Time : " + runningTime);
    }
}