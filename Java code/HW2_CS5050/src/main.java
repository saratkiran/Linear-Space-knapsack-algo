import java.util.Random;
import java.util.Arrays;
import java.io.*;

/**
 * Created by saratkiran on 2/13/14.
 */
public class main{
    public static int no_objects = 64;    // no of objects
    public static int[] randvariables = new int[no_objects+1];
    public static boolean[] kUse = new boolean[no_objects+1];
    public static int[] randvalues = new int[no_objects+1];
    public static int value_final  =0;
    public static int count = 0 ;

    public static void main(String[] args) {

        for(int l =0;l<30;l++){         // Change here to chance the number of runs

            long startTime = System.nanoTime();    //Start the timer

            int min = 1;        // Assigning the minimum value to the random generator
            int max = no_objects;     // Assigning the minimum value to the random generator
            rand_generator(randvariables,no_objects,min,max);  // generating random weights
            rand_generator(randvalues,no_objects,min,max);      //Generating random values
            System.out.println(Arrays.toString(randvariables));     // printing the Weights
            System.out.println(Arrays.toString(randvalues));        // printing the Values of items
            int capacity = no_objects*10;                                      // capacity of knapsack

            knapDC(min,no_objects,capacity);                          //Divide and conquor function call
            System.out.println("Used objects: " + Arrays.toString(kUse));
            System.out.println("Optimal Value: "+value_final);                           // printing optimal solution
            count = 0;

            // calculate the time taken
            long stopTime = System.nanoTime();
            float elapsedTime = stopTime - startTime;
            System.out.println(elapsedTime/1000000);
            String conv_to_float = Float.toString(elapsedTime/1000000);
        }
    }

    public static void knapDC(int lowindex,int highindex,int capacity){
        if(lowindex == highindex){                          // base case
            kUse[lowindex-1] = (randvariables[lowindex-1]<=capacity);
        }
        else{
            int midindex = (lowindex+highindex)/2;

            int[] leftcolumn = knapdcleft(lowindex,midindex,capacity);      //linear scan on left half
            int[] rightcolumn = knapdcright(midindex + 1, highindex, capacity); // linear scan on right half

            //System.out.println(Arrays.toString(leftcolumn));
            //System.out.println(Arrays.toString(rightcolumn));
            int best = argMax(leftcolumn, rightcolumn, capacity);
            //System.out.println(best);

            // recursive calls
            knapDC(lowindex, midindex, best);

            knapDC(midindex+1, highindex, capacity-best);


        }
    }

    // Function to generate random variables and store it in the array
    public static void rand_generator(int[] ran,int n,int min, int max){
        Random r1 = new Random();   // rand function
        for(int i=0;i<n;i++)
        {
            ran[i] = r1.nextInt((max - min) + 1) + min;  // saving the random values in the corresponding arrays
        }
    }

     // Finding the ArgMax of items
    public static int argMax(int[] leftcolumn,int[] rightcolumn,int capacity){
        int max=0;
        int bestindex=0;
        for(int i =0;i <= capacity;i++){
            if(leftcolumn[i]+rightcolumn[capacity-i] >max){
                max = leftcolumn[i]+rightcolumn[capacity - i];
                bestindex = i;
            }
        }

        count++;
        if(count == 1)
            value_final = max; // storing the optimal solution
        return bestindex;   // returning the best values
    }

    //left scan
    private static int[] knapdcleft(int lowindex, int midindex, int capacity) {

        if(capacity > 0){
            int[][] cache = new int[2][capacity+1]; // taking a 2 column 2D array

            for(int k =0;k<=capacity;k++)
                cache[0][k] = 0;
            for(int i=1;i<=midindex;i++){
                for(int j =0;j <= capacity;j++){
                    if((j - randvariables[i-1]) <= 0){
                        cache[i%2][j]= cache[(i-1)%2][j];
                    }
                    else{
                        // finding max
                        cache[i%2][j] = Math.max(cache[(i - 1) % 2][j], (cache[(i - 1) % 2][j - randvariables[i-1]]+randvalues[i-1]));
                    }
                }
            }
            // returning the final column
            int[] temp = new int[capacity+1];
            for(int k =0;k<=capacity;k++)
                temp[k] =  cache[1][k];
            return temp;
        }
        else
        // if capacity is less than 0
        {
            int[] temp2 = new int[2];  // return a dummy array if the capacity is less -1
            return temp2;
        }
    }
    //right scan .. similar to left scan but scan from down to middle
    private static int[] knapdcright(int midindex, int highindex, int capacity) {
        if(capacity > 0){
            int[][] cache = new int[2][capacity+1];
            for(int k =0;k<=capacity;k++)
                cache[1][k] = 0;
            for(int i=highindex;i>=midindex;i--){
                for(int j =0;j <= capacity;j++){
                    if((j - randvariables[i-1]) <= 0){
                        cache[i%2][j]= cache[(i+1)%2][j];
                    }
                    else{

                        cache[i%2][j] = Math.max(cache[(i +1) % 2][j], (cache[(i + 1) % 2][j - randvariables[i-1]]+randvalues[i-1]));
                    }
                }
            }
            int[] temp = new int[capacity+1];
            for(int k =0;k<=capacity;k++)
                temp[k] =  cache[0][k];
            return temp;
        }
        else
        {
            int[] temp2 = new int[2];    // return a dummy array if the capacity is less -1
            return temp2;
        }
    }
}
