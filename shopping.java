import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: shopping
*/
/**
 * Thoughts: 
 *           
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 22nd, 2016
 */
public class shopping {
	// The minimum we need to pay to purchase all expected products
	private static int min_paid = Integer.MAX_VALUE;
	private static List<Integer> PRODUCT = new ArrayList<Integer>();
	private static long TIME = 0;
	private static class Offer {
		Map<Integer,Integer> offer_detail = new HashMap<Integer,Integer>(); 
		int price;
	}
	
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("shopping.in"));
		StringTokenizer input_tokens = new StringTokenizer(f.readLine());
		int S = Integer.parseInt(input_tokens.nextToken()); // # special offers
		
		List<Offer> special_offers = new ArrayList<Offer>();
		for (int i = 0; i < S; i++) {
			input_tokens = new StringTokenizer(f.readLine());
			int count = Integer.parseInt(input_tokens.nextToken()); // # products in the offer
			Offer offer = new Offer();
			for (int j = 0; j < count; j++) {
				int product_code = Integer.parseInt(input_tokens.nextToken());
				int product_count = Integer.parseInt(input_tokens.nextToken());
				offer.offer_detail.put(product_code, product_count);
			}
			offer.price = Integer.parseInt(input_tokens.nextToken());
			special_offers.add(offer);
		}
		
		Map<Integer,Integer> shopping_list = new HashMap<Integer,Integer>(); // products to be purchased
		input_tokens = new StringTokenizer(f.readLine());
		int wanted_product = Integer.parseInt(input_tokens.nextToken());
		for (int i = 0; i < wanted_product; i++) {
			input_tokens = new StringTokenizer(f.readLine());
			int product_code = Integer.parseInt(input_tokens.nextToken());
			int product_count = Integer.parseInt(input_tokens.nextToken());
			shopping_list.put(product_code, product_count);
			shopping.PRODUCT.add(product_code);
			
			Offer offer = new Offer();
			offer.offer_detail.put(product_code, 1);
			offer.price = Integer.parseInt(input_tokens.nextToken());
			special_offers.add(offer);
		}
		Collections.sort(shopping.PRODUCT);
		f.close();
		
		int[] plan = new int[5];
		for (int i = 0; i < shopping.PRODUCT.size(); i++) {
			plan[i] = shopping_list.get(shopping.PRODUCT.get(i));
		}
		
		long start = System.currentTimeMillis();
		int[][][][][] state_space = new int[6][6][6][6][6];
		recur(plan, 0, special_offers, 0, state_space);
		long end = System.currentTimeMillis();
		
		System.out.println(shopping.TIME);
		System.out.println(end-start);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));
		out.println(shopping.min_paid);
		out.close();
	}
	
	private static void recur(
			int[] shopping_plan,
			int paid,
			List<Offer> offers,
			int offer_index,
			int[][][][][] state_space) {
		boolean is_shopping_complete = true;
		for (int i = 0; i < 5; i++) {
			if (shopping_plan[i] != 0) {
				is_shopping_complete = false;
				break;
			}
		}
		if (is_shopping_complete) {
			shopping.min_paid = Math.min(shopping.min_paid, paid);
			return;
		}
		int[] want = new int[5];
		for (int i = 0; i < 5; i++) {
			want[i] = shopping_plan[i]; 
		}
		int prev_paid = state_space[want[0]][want[1]][want[2]][want[3]][want[4]];
		if (prev_paid != 0 && prev_paid <= paid) {
			return;
		}
		state_space[want[0]][want[1]][want[2]][want[3]][want[4]] = paid;
		
		for (int i = offer_index; i < offers.size(); i++) {
			Offer offer = offers.get(i);
			int[] provide = new int[5];
			for (int j = 0; j < shopping.PRODUCT.size(); j++) {
				int product_code = shopping.PRODUCT.get(j);
				provide[j] = offer.offer_detail.containsKey(product_code) ? offer.offer_detail.get(product_code) : 0;
			}
			boolean take_offer = true; 
			int[] remaining = new int[5];
			for (int j = 0; j < 5; j++) {
				remaining[j] = want[j] - provide[j];
				if (remaining[j] < 0) {
					take_offer = false;
					break;
				}
			}
			if (take_offer) {
				recur(remaining, paid + offer.price, offers, i, state_space);	
			}
		}
	}
}