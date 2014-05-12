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
	private static final double EPSILON = 1e-17;
	private static final long[] TWO_POWERS = new long[30];

	public static void main(String[] args) {
		if(args.length < 1 || Integer.parseInt(args[0]) < 0) {
			System.err.println("No position given to calculate, any integer greater than 0 is allowed");
			System.exit(1);
		}
		int digitPosition = Integer.parseInt(args[0]);
		TWO_POWERS[0] = 1;
		for(int i = 1; i < TWO_POWERS.length; i++) {
			TWO_POWERS[i] = TWO_POWERS[i - 1] << 1;
		}
		
		double s1 = series(1, digitPosition); // compute part of a
		double s2 = series(4, digitPosition); // compute part of b
		double s3 = series(5, digitPosition); // compute part of c
		double s4 = series(6, digitPosition); // compute part of d
		
		double piPoint = 4 * s1 - 2 * s2 - s3 - s4;
		piPoint = piPoint - Math.floor(piPoint) + 1;
		
		String hex = Double.toHexString(piPoint).toUpperCase();
		System.out.println(hex.substring(4,hex.length() - 2));
	}
	
	/**
	 * Computes a portion of the formula, specifically:
	 *  (1/(16^k)) * (1 / (8k + m))
	 * Where m is the value passed in.
	 */
	private static double series(int m, int n) {
		double sum = 0;
		long denom = 0;
		long pow = 0;
		double term = 0;
		for(int i = 0; i < n; i++) {
			denom = 8 * i + m;
			pow = n - i;
			term = modPow16(pow, denom);
			sum = sum + term / denom;
			// cast to truncate
			// observe that sum is always positive here
			sum = sum - (int)(sum);
		}

		double divExp = 1;
		denom = 8 * n + m;
		term = divExp / denom;
		int i = 0;
		while(i < 100 && term >= EPSILON) {
			sum = sum + term;
			sum = sum - Math.floor(sum);
			i++;
			denom += 8;
			divExp /= 16;
			term = divExp / denom;
		}
		return sum;
	}

	/**
	 * Computes 16^exp % mod
	 */
	private static double modPow16(long exp, long mod) {
		if(mod == 1) {
			return 0;
		}

		int i = 0;
		while(i < TWO_POWERS.length && TWO_POWERS[i] <= exp) {
			i++;
		}
		
		long pow2 = TWO_POWERS[i];
		long pow1 = exp;
		double result = 1;
		
		for(int j = 0; j <= i; j++) {
			if(pow1 >= pow2) {
				result = 16 * result;
				result = result - (int)(result / mod) * mod;
				pow1 = pow1 - pow2;
			}
			pow2 = pow2 >> 1;
			if(pow2 >= 1) {
				result = result * result;
				result = result - (int)(result / mod) * mod;
			}
		}
		return result;
	}
}
