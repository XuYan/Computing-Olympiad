import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: lamps
*/
/**
 * Thoughts: 
 * Pitfalls: 
 * Take-away tips:
 * 
 * @author Xu Yan
 * @date June 2nd, 2016
 */
public class lamps {
	private int lampCount;
	private int[] button1_mask, button2_mask, button3_mask, button4_mask;
	private List<Integer> setBit;
	private List<Integer> notSetBit;
	private int[] lamp_mask;
	
	public lamps(int N, List<Integer> setBit, List<Integer> notSetBit) {
		this.lampCount = N;
		this.lamp_mask = new int[] {0, 0, 0, 0};
		for (int i = 1; i <= N; i++) {
			if (i <= 32) {
				this.lamp_mask[0] |= (1 << (32 - i));
			} else if (i <= 64) {
				this.lamp_mask[1] |= (1 << (64 - i));
			} else if (i <= 96) {
				this.lamp_mask[2] |= (1 << (96 - i));
			} else { /*i <= 128) */
				this.lamp_mask[3] |= (1 << (128 - i));
			}
		}
		
		this.button1_mask = new int[] {0, 0, 0, 0};
		for (int i = 1; i <= N; i++) {
			this.setBit(this.button1_mask, i);
		}
		
		this.button2_mask = new int[] {0, 0, 0, 0};
		for (int i = 1; i <= N; i++) {
			if (i % 2 != 0) {
				this.setBit(this.button2_mask, i);
			}
		}
		
		this.button3_mask = new int[] {0, 0, 0, 0};
		for (int i = 1; i <= N; i++) {
			if (i % 2 == 0) {
				this.setBit(this.button3_mask, i);
			}
		}
		
		this.button4_mask = new int[] {0, 0, 0, 0};
		for (int i = 1; i <= N; i++) {
			if (i % 3 == 1) {
				this.setBit(this.button4_mask, i);
			}
		}
		
		this.setBit = setBit;
		this.notSetBit = notSetBit;
	}
	
	private void setBit(int[] mask, int i) {
		if (i <= 32) {
			mask[0] |= (1 << (32 - i));
		} else if (i <= 64) {
			mask[1] |= (1 << (64 - i));
		} else if (i <= 96) {
			mask[2] |= (1 << (96 - i));
		} else { /*i <= 128) */
			mask[3] |= (1 << (128 - i));
		}
	}
	
	public List<String> solve(int c) {
		int[][] configurations;
		if (c % 2 == 0) { /* c is even */
			if (c == 0) {
				configurations = new int[][] {this.xor(this.button1_mask, this.button1_mask)};
			} else if (c == 2) {
				configurations = new int[][] {this.xor(this.button1_mask, this.button1_mask),
						this.xor(this.button1_mask, this.button2_mask),
						this.xor(this.button1_mask, this.button3_mask),
						this.xor(this.button1_mask, this.button4_mask),
						this.xor(this.button2_mask, this.button3_mask),
						this.xor(this.button2_mask, this.button4_mask),
						this.xor(this.button3_mask, this.button4_mask)};
			} else { /* c >= 4 */
				configurations = new int[][] {this.xor(this.button1_mask, this.button1_mask),
						this.xor(this.button1_mask, this.button2_mask),
						this.xor(this.button1_mask, this.button3_mask),
						this.xor(this.button1_mask, this.button4_mask),
						this.xor(this.button2_mask, this.button3_mask),
						this.xor(this.button2_mask, this.button4_mask),
						this.xor(this.button3_mask, this.button4_mask),
						this.xor(this.xor(this.xor(this.button1_mask, this.button2_mask), this.button3_mask), this.button4_mask)};
			}
		} else { /* c is odd */
			if (c == 1) {
				configurations = new int[][] {this.button1_mask, this.button2_mask, this.button3_mask, this.button4_mask};	
			} else { /* c >= 3 */
				configurations = new int[][] {this.button1_mask, this.button2_mask, this.button3_mask, this.button4_mask,
						this.xor(this.xor(this.button1_mask, this.button2_mask), this.button3_mask),
						this.xor(this.xor(this.button1_mask, this.button2_mask), this.button4_mask),
						this.xor(this.xor(this.button1_mask, this.button3_mask), this.button4_mask),
						this.xor(this.xor(this.button2_mask, this.button3_mask), this.button4_mask)};
			}
		}
		
		int[] originalState = new int[] {-1 & this.lamp_mask[0], -1 & this.lamp_mask[1], -1 & this.lamp_mask[2], -1 & this.lamp_mask[3]}; // All lamps are on
		List<String> satisfiedFinalConfiguration = new ArrayList<String>();
		for (int[] configuration : configurations) {
			int[] finalConfiguration  = this.xor(originalState, configuration);
			if (this.isValidConfiguration(finalConfiguration)) {
				satisfiedFinalConfiguration.add(this.convertToString(finalConfiguration));
			}
		}
		
		Collections.sort(satisfiedFinalConfiguration);
		
		return satisfiedFinalConfiguration;
	}
	
	private String convertToString(int[] configuration) {
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i <= this.lampCount; i++) {
			if (i <= 32) {
				builder.append((configuration[0] & (1 << (32 - i))) == 0 ? "0" : "1");	
			} else if (i <= 64) {
				builder.append((configuration[1] & (1 << (64 - i))) == 0 ? "0" : "1");
			} else if (i <= 96) {
				builder.append((configuration[2] & (1 << (96 - i))) == 0 ? "0" : "1");
			} else {
				builder.append((configuration[3] & (1 << (128 - i))) == 0 ? "0" : "1");
			}
		}
		
		return builder.toString();
	}
	
	private boolean isValidConfiguration(int[] configuration) {
		for (int bit: this.setBit) {
			if (bit <= 32) {
				if ((configuration[0] & (1 << (32 - bit))) == 0) {
					return false;
				}
			} else if (bit <= 64) {
				if ((configuration[1] & (1 << (64 - bit))) == 0) {
					return false;
				}
			} else if (bit <= 96) {
				if ((configuration[2] & (1 << (96 - bit))) == 0) {
					return false;
				}
			} else { /* bit <= 128 */
				if ((configuration[3] & (1 << (128 - bit))) == 0) {
					return false;
				}
			}
		}
		
		for (int bit : this.notSetBit) {
			if (bit <= 32) {
				if (((configuration[0] >> (32 - bit)) & 1) == 1) {
					return false;
				}
			} else if (bit <= 64) {
				if (((configuration[1] >> (64 - bit)) & 1) == 1) {
					return false;
				}
			} else if (bit <= 96) {
				if (((configuration[2] >> (96 - bit)) & 1) == 1) {
					return false;
				}
			} else { /* bit <= 128 */
				if (((configuration[3] >> (128 - bit)) & 1) == 1) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private int[] xor(int[] op1, int[] op2) {
		return new int[] {op1[0] ^ op2[0], op1[1] ^ op2[1], op1[2] ^ op2[2], op1[3] ^ op2[3]};
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("lamps.in"));
        int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        int C = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        
        List<Integer> on_lamps = new ArrayList<Integer>();
        StringTokenizer on = new StringTokenizer(f.readLine());
        while (on.hasMoreTokens()) {
        	int nextInt = Integer.parseInt(on.nextToken());
        	if (nextInt == -1) {
        		break;
        	}
        	on_lamps.add(nextInt);
        }
        
        List<Integer> off_lamps = new ArrayList<Integer>();
        StringTokenizer off = new StringTokenizer(f.readLine());
        while (off.hasMoreTokens()) {
        	int nextInt = Integer.parseInt(off.nextToken());
        	if (nextInt == -1) {
        		break;
        	}
        	off_lamps.add(nextInt);
        }
        
        f.close();
        
        lamps solver = new lamps(N, on_lamps, off_lamps);
        List<String> ans = solver.solve(C);
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lamps.out")));
		if (ans.isEmpty()) {
			out.println("IMPOSSIBLE");
		} else {
			for (String s : ans) {
				out.println(s);
			}			
		}
		out.close();
	}
}
