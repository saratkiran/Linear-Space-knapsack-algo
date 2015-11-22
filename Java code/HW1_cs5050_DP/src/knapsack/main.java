package knapsack;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

/**
 * Created by saratkiran on 1/17/14.
 */

public class main {

    public static int randvariables[] = new int[8200];
    public static int randvalues[] = new int [8200];
    public static boolean kUse[] = new boolean[10];
    public static void main(String[] args) {
        int no_objects = 4096;                ; // What should be the number of objects.


            try{
                BufferedWriter out = new BufferedWriter(new FileWriter("filers.txt"));// to write output into a txt file
                for(int l =0;l<30;l++){

                    long startTime = System.nanoTime();    //Start the timer

                    int min = 1;        // Assigning the minimum value to the random generator
                    int max = no_objects;     // Assigning the minimum value to the random generator
                    rand_generator(randvariables,no_objects,min,max);  // generating random weights
                    rand_generator(randvalues,no_objects,min,max);      //Generating random values
                    //System.out.println(Arrays.toString(randvariables));
                    //System.out.println(Arrays.toString(randvalues));

                    int cap = no_objects*10;  // total capacity for problem 2 - wide and narrow
                    //int cap = 6;
                   // int cache_array[][] = new int[no_objects+1][cap+1]; // initializing cache array



                    // --------------------Uncomment here to get the recursive solution ---------------------
                    //System.out.println("Recursive Solution");
                    //recursive(no_objects - 1, 1000);
                    //System.out.println(recursive(no_objects-1,cap));
                    //----------------------------------------------------------------------------------------

                    //----------------- Uncomment the following code to get the cache solution ---------------
                    //System.out.println("Cache Solution");
                    //System.out.println(cache_solution(cache_array,no_objects-1, cap));
                    //Initialing the cache array with -1
//                    for(int i =0;i<=no_objects;i++)
//                        for(int j =0;j<= cap;j++)
//                            cache_array[i][j] = -1;
//                    cache_solution(cache_array,no_objects-1, cap);
                    //-------------------------------------------------------------------------------------------

                    //----------------- Uncomment the following code to get the Dynamic Programming solution ----
                    //System.out.println("Dynaminc programing solution");
                    System.out.println(dynamic_p(no_objects,cap));
                    //dynamic_p(no_objects,cap);
                    //find_sol(no_objects,cap);
                    //System.out.println(Arrays.toString(solution));
                    //-------------------------------------------------------------------------------------------



                    // Calculating the time it took for the program execution
                    long stopTime = System.nanoTime();
                    float elapsedTime = stopTime - startTime;
                    System.out.println(elapsedTime/1000000);
                    String conv_to_float = Float.toString(elapsedTime/1000000);

                    out.write(conv_to_float); // sending values to txt file
                    out.newLine();
                    //cache_array =null ;   // to delete the cache array

            }
            } catch (IOException e) {}
            no_objects++;

    }
    // Function to generate random variables and store it in the array
    public static void rand_generator(int[] ran,int n,int min, int max){
        Random r1 = new Random();   // rand function
        for(int i=0;i<n;i++)
        {
            ran[i] = r1.nextInt((max - min) + 1) + min;  // saving the random values in the corresponding arrays
        }
    }

    // Function to solve the Cache solution
    public static int cache_solution(int[][] cache_array,int objects,int capacity){

        //base cases
        if(capacity <= 0)
            return 0;
        if(objects < 0)
            return 0;

        if(cache_array[objects][capacity] != -1)
            return cache_array[objects][capacity];
        else{
        if(randvariables[objects] > capacity)       // check if the weight is more than capacity
            return cache_solution(cache_array,objects-1,capacity);
        else{
            // recursive calls 1. to take the item(add the value,subtract the weight) 2. Dont take the value(subtract the weight)
            int do_take = randvalues[objects] + cache_solution(cache_array,objects-1, capacity-randvariables[objects]);
            int dont_take  = cache_solution(cache_array,objects - 1, capacity);

            // Returning the highest value
            if(do_take > dont_take){
                cache_array[objects][capacity] = do_take;
                return do_take;
            }
            else{
                cache_array[objects][capacity] = dont_take;
                return dont_take;
            }
        }
        }

    }

     // Function to find the knapsack problem using recursive method
    public static int recursive(int objects,int capacity)
    {
        //base cases
        if(capacity <= 0)
            return 0;
        if(objects < 0)
            return 0;

        if(randvariables[objects] > capacity)       // check if the weight is more than capacity
            return recursive(objects-1,capacity);
        else{
            // recursive calls 1. to take the item(add the value,subtract the weight) 2. Dont take the value(subtract the weight)
            int do_take = randvalues[objects] + recursive(objects-1, capacity-randvariables[objects]);
            int dont_take  = recursive(objects - 1, capacity);

            // Returning the highest value
            if(do_take > dont_take){
                return do_take;
            }
            else{
                return dont_take;
            }
        }
    }

    // Function to find the knapsack problem using Dynamic Programming method
    public static int dynamic_p(int objects,int capacity){
         // Array to accommodate dynamic programming solutions
        int dp_array[][] = new int[objects+1][capacity+1];
        // filling the 2D array
        for(int i=0; i<= objects;i++){
            for(int j=0; j<= capacity;j++){
                if(i == 0)
                    dp_array[i][j]=0;  // base case(number of objects are zero)
                else if(j == 0)
                    dp_array[i][j]=0;  // base case(capacity is zero)
                else if(randvariables[i-1] <= j){   // checking if the wight of object is less than the current knapsack capacity
                    int included = randvalues[i-1]+dp_array[i-1][j-randvariables[i-1]]; // object is included
                    int not_included = dp_array[i-1][j];        // object is not included

                    //taking the max of these two
                    if(included > not_included)
                        dp_array[i][j] = included;
                    else
                        dp_array[i][j]= not_included;
                }
                else                                    // weight is more than capacity .. not including the object
                    dp_array[i][j] = dp_array[i-1][j];
            }
            }

        //System.out.println(Arrays.deepToString(dp_array));
        int solution =  dp_array[objects][capacity];
        boolean[] optimalset = solution_used(dp_array,objects,capacity);
        System.out.println(Arrays.toString(optimalset));
        dp_array = null;
        return solution;                 // return the final solution

        }
    // function to get the traceback item.
    public static boolean[] solution_used(int[][] dp_array,int objects,int capacity){

        boolean[] opt_set = new boolean[objects+1]; // to store solutions
        int Items = 0;
        int i = dp_array.length-1;
        for (int j = dp_array[0].length - 1; j >= 0 && i > 0;i--) {
            if (dp_array[i][j] != dp_array[i-1][j]) { // trace back algorithm
                opt_set[i-1] = true ;                 // set the array  value true if the values are used
                j -= randvariables[i-1];
                Items++;
            }
        }
       return Arrays.copyOfRange(opt_set, 0, objects);  //return the items
    }

}
