import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

/*
Steps -
    1. Find closest obstable in 8 direction by iterating throgh list of obstacles. 
    2. Travel 8 direction from queens location using recursion and stop if obstacle found.
    3. Add count of squares travelled in 8 direction and return result.
*/
public class Solution {

    // Complete the queensAttack function below.
    static int queensAttack(int n, int k, int r_q, int c_q, int[][] obstacles) {
        System.out.println("Square size : " + n + ", Obstacle " + k);
        System.out.println("Queen position : " + r_q + " " + c_q);
        System.out.println("Obstacles " + Arrays.deepToString(obstacles));

        /* Step 1 - Get closest obstable in 8 direction */
        int[] westClosest = {0, 0};
        int[] eastClosest = {0, 0};
        int[] southClosest = {0, 0};
        int[] northClosest = {0, 0};
        int[] northWestClosest = {0, 0};
        int[] northEastClosest = {0, 0};
        int[] southWestClosest = {0, 0};
        int[] southEastClosest = {0, 0};
        int[] intialValues = {0, 0};
        for(int[] obstacle: obstacles){
            if(obstacle[0] == r_q){
                if(obstacle[1] < c_q ){
                    if(westClosest[1] < obstacle[1])
                        westClosest = obstacle;
                }else if (obstacle[1] > c_q ){
                    if(eastClosest[1] > obstacle[1] 
                    || Arrays.equals(eastClosest, intialValues))
                        eastClosest = obstacle;
                }
            }else if(obstacle[1] == c_q){
                 if(obstacle[0] < r_q ){
                    if(southClosest[0] < obstacle[0])
                        southClosest = obstacle;
                }else if (obstacle[0] > r_q ){
                    if(northClosest[0] > obstacle[0] 
                    || Arrays.equals(northClosest, intialValues))
                        northClosest = obstacle;
                }
            }else if(obstacle[0] - r_q == c_q - obstacle[1] && obstacle[0] > r_q){
                if(obstacle[1] > northWestClosest[1]){
                    northWestClosest = obstacle;
                }        
            }else if(obstacle[1] - obstacle[0] == c_q - r_q 
                && c_q < obstacle[1] && r_q < obstacle[0]){
                if(obstacle[1] < northEastClosest[1] 
                    || Arrays.equals(northEastClosest, intialValues)){
                    northEastClosest = obstacle;
                }        
            }else if(obstacle[1] - obstacle[0] == c_q - r_q
                && c_q > obstacle[1] && r_q > obstacle[0]){
                if(obstacle[1] > southWestClosest[1] 
                    || Arrays.equals(southWestClosest, intialValues)){
                    southWestClosest = obstacle;
                }        
            }else if(r_q - obstacle[0] == obstacle[1] - c_q && obstacle[0] < r_q){
                if(obstacle[1] < southEastClosest[1] 
                    || Arrays.equals(southEastClosest, intialValues)){
                    southEastClosest = obstacle;
                }        
            }


        }

        System.out.println("westClosest " + Arrays.toString(westClosest));
        System.out.println("eastClosest " + Arrays.toString(eastClosest));
        System.out.println("southClosest " + Arrays.toString(southClosest));
        System.out.println("northClosest " + Arrays.toString(northClosest));
        System.out.println("northWestClosest " + Arrays.toString(northWestClosest));
        System.out.println("northEastClosest " + Arrays.toString(northEastClosest));
        System.out.println("southWestClosest " + Arrays.toString(southWestClosest));
        System.out.println("southEastClosest " + Arrays.toString(southEastClosest));
        
        /* Step 2 - Count squares by travelling in all 8 directions */
        int north =  traversePath(n, r_q, c_q, 1, 0, northClosest);
        int south =  traversePath(n, r_q, c_q, -1, 0, southClosest);
        int northWest = traversePath(n, r_q, c_q, 1, -1, northWestClosest);
        int northEast = traversePath(n, r_q, c_q, 1, 1, northEastClosest);
        int west = traversePath(n, r_q, c_q, 0, -1, westClosest);
        int east = traversePath(n, r_q, c_q, 0, 1, eastClosest);
        int southWest = traversePath(n, r_q, c_q, -1, -1, southWestClosest);
        int southEast = traversePath(n, r_q, c_q, -1, 1, southEastClosest);

        /* instead of using recursion squares can be calculated by adding/substracting cols/rows.
           e.g north = northClosest[0] = r_q
           It will be more faster. I wanted to try it using recursion
        */   

        System.out.println("north " + north);
        System.out.println("south " + south);
        System.out.println("northWest " + northWest);
        System.out.println("northEast " + northEast);
        System.out.println("west " + west);
        System.out.println("east " + east);
        System.out.println("southWest " + southWest);
        System.out.println("southEast " + southEast);

        /* Add squares of all direction and return result */
        return north + south + northWest + northEast + west + east + southWest + southEast;

    }

    static int traversePath(int n, int x, int y, int x_inc, int y_inc, int[] obstacle){
        if(x_inc != 0 && ((x == 1 && x_inc == -1) || (x == n && x_inc == 1))){
            return 0;
        }

        if(y_inc != 0 && ((y == 1 && y_inc == -1) || (y == n && y_inc == 1))){
            return 0;
        }

        x = x + x_inc;
        y = y + y_inc;
        
        int[] currSquare = {x, y};
        if (Arrays.equals(obstacle, currSquare)) 
            return 0;
        

        return traversePath(n, x, y, x_inc, y_inc, obstacle) + 1;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        String[] r_qC_q = scanner.nextLine().split(" ");

        int r_q = Integer.parseInt(r_qC_q[0]);

        int c_q = Integer.parseInt(r_qC_q[1]);

        int[][] obstacles = new int[k][2];

        for (int i = 0; i < k; i++) {
            String[] obstaclesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 2; j++) {
                int obstaclesItem = Integer.parseInt(obstaclesRowItems[j]);
                obstacles[i][j] = obstaclesItem;
            }
        }

        int result = queensAttack(n, k, r_q, c_q, obstacles);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
