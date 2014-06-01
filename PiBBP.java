/**
 * Java Bailey-Borwein-Plouffe formula implementation.
 * Adapted from a number of papers, the main one being:
 * 		http://www.davidhbailey.com/dhbpapers/bbp-alg.pdf
 *
 * Java implementation adapted from #C implementation
 * that can be found at: 
 * http://latkin.org/blog/2012/11/03/the-bailey-borwein-plouffe-algorithm-in-c-and-f/
 *
 * Main formula represented:
 * Sum from k = 0 to infinity of 1/(16^k) * 
 * [(4/(8k + 1)) - (2/(2k + 4)) - (1/(8k + 5)) - (1/(8k + 6))]
 *       a              b              c               d
 *
 * The letters beneath parts of the sum will represent them in comments
 */
import java.util.HashMap;

public class PiBBP {

	public static void main(String[] args) {
		if(args.length < 1 || Integer.parseInt(args[0]) < 0) {
			System.err.println("No position given to calculate, any integer greater than 0 is allowed");
			System.exit(1);
		}
		int digitPosition = Integer.parseInt(args[0]);

		double piPoint = BBP.pi(digitPosition);
		
		String hex = Double.toHexString(piPoint).toUpperCase();
		System.out.println(hex.substring(4,hex.length() - 2));
	}
}
