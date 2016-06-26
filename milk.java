import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: milk
*/
/**
 * Thoughts: One thought is sorting on price. Then start from the lowest price and exhaust the amount available at that price until we reach total amount we need.
 *           The second thought is counting sort if price range is small. This means, we'll initialize an integer array of size 'price+1' with index as price and value as amount.
 * Pitfalls: 
 * Take-away tips: Data scale determines the way to tackle a problem.
 * 
 * @author Xu Yan
 * @date April 23,2016
 */
public class milk {
    public static void main (String [] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("milk.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk.out")));
        
        StringTokenizer st = new StringTokenizer(in.readLine());
        int neededBottles = Integer.parseInt(st.nextToken());
        int farmerCount = Integer.parseInt(st.nextToken());
        
        milk program = new milk();
        Map<Integer,Integer> price_amount = new HashMap<Integer,Integer>();
        
        for (int i = 0; i < farmerCount; i++) {
            st = new StringTokenizer(in.readLine());
            int price = Integer.parseInt(st.nextToken());
            int amount = Integer.parseInt(st.nextToken());
            if (price_amount.containsKey(price)) {
                price_amount.put(price, price_amount.get(price) + amount);
            } else {
                price_amount.put(price, amount);
            }
        }
        out.println(program.calcMinCost(price_amount, neededBottles));
        
        
        in.close();
        out.close();
    }
    
    private int calcMinCost(Map<Integer,Integer> price_amount, int neededBottles) {
        int[] unique_prices = new int[price_amount.keySet().size()];
        int i = 0;
        for (int price : price_amount.keySet()) {
            unique_prices[i++] = price;
        }
        Arrays.sort(unique_prices);
        
        int cost = 0;
        for (int price : unique_prices) {
            int bottles = price_amount.get(price); // the number of bottles can be bought at $price.
            if (bottles < neededBottles) {
                cost += price * bottles;
                neededBottles -= bottles;
            } else {
                cost += price * neededBottles;
                break;
            }
        }
        return cost;
    }
}
