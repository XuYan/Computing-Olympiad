import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: transform
*/
/**
 * Thoughts: Use a one-dimensional array to represent the matrix.
 * Pitfalls: if input matrix and target matrix are identical and also mutual-reflected around a horizontal line in the middle of the matrix, we should output '2' instead of '6'
 * Take-away tips: No matter how complicated combination operations we take, there are 6 output matrices we can get.
 * 
 * @author Xu Yan
 * @date April 22,2016
 */
public class transform {
    public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("transform.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transform.out")));
        
        int size = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        
        transform t = new transform();
        char[] before = t.init(f, size);
        char[] after = t.init(f, size);
        t.transforms(out, before, after);
        
        f.close();
        out.close();
      }
    
    /**
     * Write transform steps to PrintWriter
     * @param out the PrintWriter
     * @param before the input matrix
     * @param after the target matrix
     */
    public void transforms(PrintWriter out, char[] before, char[] after) {
        boolean noChangeMatch = this.isSame(before, after);
        
        char[] rotated90 = this.rotate90(before);
        if (this.isSame(rotated90, after)) {
            out.println('1');
            return;
        }
        
        char [] rotated180 = this.rotate90(rotated90);
        if (this.isSame(rotated180, after)) {
            out.println('2');
            return;
        }
        
        char[] rotated270 = this.rotate90(rotated180);
        if (this.isSame(rotated270, after)) {
            out.println('3');
            return;
        }
        
        this.reflect(before);
        if (this.isSame(before, after)) {
            out.println('4');
            return;
        }
        
        rotated90 = this.rotate90(before);
        if (this.isSame(rotated90, after)) {
            out.println('5');
            return;
        }
        
        rotated180 = this.rotate90(rotated90);
        if (this.isSame(rotated180, after)) {
            out.println('5');
            return;
        }
        
        rotated270 = this.rotate90(rotated180);
        if (this.isSame(rotated270, after)) {
            out.println('5');
            return;
        }
        
        if (noChangeMatch) {
            out.println('6');
        } else {
            out.println('7');
        }
    }
    
    /**
     * Checks if the given two matrix is the same
     * @param before matrix 1
     * @param after matrix 2
     * @return true if matrix 1 is the same(value comparison) as matrix 2 
     */
    public boolean isSame(char[] before, char[] after) {
        if (before.length != after.length) {
            return false;
        }
        for (int i = 0; i < before.length; i++) {
            if (before[i] != after[i]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @param f buffered reader to read from input file
     * @param size matrix length and width
     * @return matrix in array representation
     */
    public char[] init(BufferedReader f, int size) throws IOException {
        char[] matrix = new char[size * size]; // since size is an integer in [1, 10], overflow won't happen
        for (int i = 0; i < size; i++) {
            String line = new StringTokenizer(f.readLine()).nextToken();
            for (int j = 0; j < size; j++) {
                matrix[i * size + j] = line.charAt(j);
            }
        }
        return matrix;
    }
    
    /**
     * Rotate a matrix clockwise 90 degree
     * @param matrix before rotating
     * @return the matrix after rotating clockwise 90 degree
     */
    public char[] rotate90(char[] matrix) {
        char[] rotated = new char[matrix.length];
        int size = (int) Math.sqrt(matrix.length); // Array length is calculated as 'size' * 'size', so it's okay to cast to integer to get 'size'
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                rotated[col * size + (size - 1 - row)] = matrix[row * size + col];
            }
        }
        return rotated;
    }
    
    /**
     * Reflect a matrix horizontally around a vertical line in the middle of the matrix
     * @param matrix before reflecting
     */
    public void reflect(char[] matrix) {
        int size = (int) Math.sqrt(matrix.length); // Array length is calculated as 'size' * 'size', so it's okay to cast to integer to get 'size'
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size/2; col++) {
                char temp = matrix[row * size + col];
                matrix[row * size + col] = matrix[row * size + size - 1 - col];
                matrix[row * size + size - 1 - col] = temp;
            }
        }
    }
}
