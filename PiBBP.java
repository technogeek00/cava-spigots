/**
 * Java Bailey-Borwein-Plouffe formula implementation.
 * Adapted from a number of papers, the main one being:
 * 		http://www.davidhbailey.com/dhbpapers/bbp-alg.pdf
 *
 * Java implementation adapted from #C implementation
 * that can be found at: 
 * http://latkin.org/blog/2012/11/03/the-bailey-borwein-plouffe-algorithm-in-c-and-f/
 */
public class PiBBP {
	private static final double epsilon = 1e-17;
	private static final int twosCount = 25;

	private static double[] twoPowers = new double[twosCount];

	public static void main(String[] args) {
		if(args.length < 1 || Integer.parseInt(args[0]) < 0) {
			System.err.println("No position given to calculate, any integer greater than 0 is allowed");
			System.exit(1);
		}
		int digitPosition = Integer.parseInt(args[0]);
		initPowers();
		
		double s1 = series(1, digitPosition);
		double s2 = series(4, digitPosition);
		double s3 = series(5, digitPosition);
		double s4 = series(6, digitPosition);
		
		double piPoint = 4 * s1 - 2 * s2 - s3 - s4;
		piPoint = piPoint - Math.floor(piPoint) + 1;
		
		System.out.println(hex(piPoint, 16));
	}
	
	private static String hex(double x, int numDigits) {
		String hexChars = "0123456789ABCDEF";
		String sb = "";
		double y = Math.abs(x);
		for(int i = 0; i < numDigits; i++) {
			y = 16 * (y - Math.floor(y));
			sb += hexChars.charAt((int) Math.floor(y));
		}
		return sb;
	}
	
	private static void initPowers() {
		twoPowers[0] = 1;
		for(int i = 1; i < twoPowers.length; i++) {
			twoPowers[i] = 2 * twoPowers[i - 1];
		}
	}
	
	private static double series(int m, int n) {
		double sum = 0;
		double denom = 0;
		double pow = 0;
		double term = 0;
		for(int i = 0; i < n; i++) {
			denom = 8 * i + m;
			pow = n - i;
			term = modPow16(pow, denom);
			sum = sum + term / denom;
			sum = sum - Math.floor(sum);
		}
		
		for(int i = n; i <= n + 100; i++) {
			denom = 8 * i + m;
			term = Math.pow(16, n - i) / denom;
			if(term < epsilon) {
				break;
			}
			sum = sum + term;
			sum = sum - Math.floor(sum);
		}
		return sum;
	}
	
	private static double modPow16(double p, double m) {
		int i;
		double pow1 = 0;
		double pow2 = 0;
		double result = 0;
		
		if(m == 1) {
			return 0;
		}
		
		for(i = 0; i < twosCount; i++) {
			if(twoPowers[i] > p) {
				break;
			}
		}
		
		pow2 = twoPowers[i - 1];
		pow1 = p;
		result = 1;
		
		for(int j = 1; j <= i; j++) {
			if(pow1 >= pow2) {
				result = 16 * result;
				result = result - Math.floor(result / m) * m;
				pow1 = pow1 - pow2;
			}
			pow2 = 0.5 * pow2;
			if(pow2 >= 1) {
				result = result * result;
				result = result - Math.floor(result / m) * m;
			}
		}
		return result;
	}
}
