package sg.maths.projecteuler;

public class Fibonacci {

	/* http://projecteuler.net/problem=2 */
	public static long sumOfEvenFibonacci(long max) {

		// (1, 2, 3, 5, 8, ...)
		long fN_2 = 1; // f(n-2)
		System.out.println("... f(1) = " + fN_2);
		long fN_1 = 2; // f(n-1)
		System.out.println("... f(2) = " + fN_1);
		
		long fN = fN_1 + fN_2;
		int N = 3;
		// f(n-1) + f(n-2) + 1, to convert the series to (1, 1, 2, 3, 5, 8, ...)
		// s.t. sumOfEvenFibonacci = sumOfOddFibonacci = sumOfAllFibonacci / 2
		long sum = fN + 1;
		while (fN < max) {
			System.out.println("... f(" + N + ") = " + fN);
			++N;
			sum += fN;
			
			fN_2 = fN_1;
			fN_1 = fN;
			fN	 = fN_1 + fN_2;
		}
		
		if (fN % 2 == 0) {
			sum -= fN;
		} else if (fN_1 % 2 != 0) {
			sum -= fN_1;
		}
		
		sum /= 2; // convert to the sum of original series
		System.out.println("sum of even fibonacci (< " + max + ") = " + sum);
		
		return sum;
	}
	
	/* http://projecteuler.net/problem=25 */
	public static int getTermOfFirstNDigitsFibonacci(int N) {
		
		// (1, 1, 2, 3, 5, 8, ...)
		int T = 1;
		String fT_1 = ""; // f(t-1)
		StringBuffer fT = new StringBuffer("1");
		String temp;
		while (fT.length() < N) {
			//System.out.println("... f(" + T + ") = " + fT);
			temp = fT.toString();
			++T;
			fT = BigCalc.add(fT, fT_1);
			fT_1 = temp;
		}
		System.out.println("... f(" + T + ") = " + fT);
		return T;
	}
}
