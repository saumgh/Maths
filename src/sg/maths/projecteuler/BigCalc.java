package sg.maths.projecteuler;

import java.util.Set;
import java.util.TreeSet;

public class BigCalc {

	public static StringBuffer add(StringBuffer n1, String n2) {
		
		int len1 = n1.length();
		int len2 = n2.length();
		int iMax = (len1-len2 >= 0 ? len1 : len2);
		
		int carry = 0;
		for (int i=0; i<iMax; ++i) {
			if (i < len2) {
				carry += Integer.parseInt(n2.substring(len2-1-i, len2-i));
			}
			if (i < len1) {
				carry += Integer.parseInt(n1.substring(len1-1-i, len1-i));
				n1.replace(len1-1-i, len1-i, String.valueOf(carry%10));
			} else {
				n1 = new StringBuffer(String.valueOf(carry%10)).append(n1);
			}
			carry /= 10;
		}
		n1 = new StringBuffer(carry>0 ? String.valueOf(carry) : "").append(n1);
		
		return n1;
	}
	
	public static StringBuffer multiply(StringBuffer n, int i) {
		
		long carry = 0;
		for (int j=n.length()-1; j>=0; --j) {
			carry += Integer.parseInt(n.substring(j, j+1)) * i;
			n.replace(j, j+1, String.valueOf(carry % 10));
			carry /= 10;
		}
		n = new StringBuffer(carry>0 ? String.valueOf(carry) : "").append(n);
		
		return n;
	}

	/* http://projecteuler.net/problem=13 */
	public static String getFirstNDigitsOfSum(String[] nums, int n) {
		
		StringBuffer sum = new StringBuffer(nums[0]);
		for (int i=1; i<nums.length; ++i) {
			sum = add(sum, nums[i]);
		}
		
		String sep = "-";
		if (n > sum.length()) {
			n = sum.length();
			sep = "";
		}
		System.out.println("sum = " + sum.substring(0, n) + sep + sum.substring(n));
		return sum.substring(0, n);
	}
	
	/* http://projecteuler.net/problem=15 */
	public static long getLatticeRoutes(int gridSize) {
		return nCr(2*gridSize, gridSize);
	}
	
	public static long nCr(int n, int r) {
		
		if ((r < 0) || (r > n)) {
			return 0;
		}
		// nCr = nC(n-r)
		if (r > n/2) {
			r = n - r;
		}
		if ((r == 0) || (r == n)) {
			return 1;
		}
				
		long numerator = 1, denominator = 1;
		for (int i=r; i>=1; --i) {
			numerator *= n - r + i;
			denominator *= i;
			
			int factor = 2;
			while (factor <= denominator) {
				while ((numerator % factor == 0) && (denominator % factor == 0)) {
					numerator /= factor;
					denominator /= factor;
				}
				++factor;
			}
		}
		
		long nCr = numerator / denominator;
		
		String nCrStr = n + "C" + r + " = ";
		if (r * 2 != n) {
			nCrStr += n + "C" + (n-r) + " = ";
		}
		System.out.println(nCrStr + nCr);
		
		return nCr;
	}
	
	/* http://projecteuler.net/problem=16 */
	public static int getPowerDigitSum(int n, int exp) {
		
		StringBuffer power = power(n, exp);
		int sum = 0;
		for (int i=power.length()-1; i>=0; --i) {
			sum += Integer.parseInt(power.substring(i, i+1));
		}
		System.out.println("sum of digits (" + n + "^" + exp + ") = " + sum);
		
		return sum;
	}
	
	/* http://projecteuler.net/problem=20 */
	public static int getFactorialDigitSum(int n) {
		return getDigitSum(getFactorial(n));
	}
	
	public static int getDigitSum(String num) {
		
		int sum = 0;
		for (int i=num.length()-1; i>=0; --i) {
			sum += Integer.parseInt(num.substring(i, i+1));
		}
		System.out.println("sum of digits (" + num + "!) = " + sum);
		return sum;
	}

	public static String getFactorial(int n) {
		
		StringBuffer factorial = new StringBuffer("1");
		int powerOf10 = 0;
		for (int i=1; i<=n; ++i) {
			factorial = multiply(factorial, i);

			while (factorial.charAt(factorial.length()-1) == '0') {
				++powerOf10;
				factorial.deleteCharAt(factorial.length()-1);
			}
			
			System.out.println(i + "! = " + factorial + " x 10^" + powerOf10);
		}
		
		return factorial.toString();
	}

	/* http://projecteuler.net/problem=29 */
	public static int getDistinctPowersCount(int aMax, int bMax) {
		
		Set<String> powers = new TreeSet<String>();
		for (int a=2; a<=aMax; ++a) {
			for (int b=2; b<=bMax; ++b) {
				powers.add(powerInTensPower(a, b).toString());
			}			
		}
		
		System.out.println("Distinct powers of a^b = " + powers.size());
		return powers.size();
	}

	public static StringBuffer powerInTensPower(int a, int b) {
		
		StringBuffer power = new StringBuffer(String.valueOf(a));
		int powerOf10 = 0;
		for (int i=2; i<=b; ++i) {
			power = multiply(power, a);

			while (power.charAt(power.length()-1) == '0') {
				++powerOf10;
				power.deleteCharAt(power.length()-1);
			}
		}
		power.append("x10^" + powerOf10);
		System.out.println(a + "^" + b + " = " + power);
		
		return power;
	}
	
	public static StringBuffer power(int a, int b) {
		
		StringBuffer power = new StringBuffer("1");
		for (int i=1; i<=b; ++i) {
			power = multiply(power, a);
		}
		System.out.println(a + "^" + b + " = " + power);		
		return power;
	}
	
	/* http://projecteuler.net/problem=49 */
	public static StringBuffer getLastNDigitsOfSelfPowersSum(int n, int max) {
		
		StringBuffer power = new StringBuffer("0");
		for (int i=1; i<=max; ++i) {
			power = add(power, power(i, i).toString());
		}
		String powerOut;
		int len = power.length();
		if (power.length() > n) {
			powerOut = power.substring(0, len-n) + "-" + power.substring(len-n);
		} else {
			powerOut = power.toString();
		}
		System.out.println("Sum of self powers from 1^1 + ... + " + max + "^" + max + " = " + powerOut);
		return power;
	}
	
	/* http://projecteuler.net/problem=53 */
	public static int getCombinatoricSelectionsCount(int n, long max) {
		
		int count = 0;
		for (int i=1; i<=n; ++i) {
			long nCr = 1;
			for (int j=1; j<=i/2; ++j) {
				nCr = (nCr * (i - j + 1)) / (j);
				if (nCr > max) {
					count += i - 2 * j + 1;
					break;
				} else {
					continue;
				}
			}			
		}
		
		System.out.println("Count of " + n + "Cr>" + max + " = " + count);
		return count;
	}
	
	/* http://projecteuler.net/problem=56 */
	public static int getMaxPowerfulDigitSum(int n) {
		return getMaxPowerfulDigitSum(n, n);
	}
	
	public static int getMaxPowerfulDigitSum(int a, int b) {
		
		int sum, maxSum = 0, iMax = 0, jMax = 0;
		for (int i=1; i<a; ++i) {
			for (int j=1; j<b; ++j) {
				sum = getDigitSum(power(i, j).toString());
				if (maxSum < sum) {
					maxSum = sum;
					iMax = i;
					jMax = j;
				}
			}
		}
		System.out.println("Max sum of powerful digits of " + iMax + "^" + jMax + " = " + maxSum);
		return maxSum;
	}
	
	/* http://projecteuler.net/problem=57 */
	public static void squareRootConvergents(int n) {
		
		int count = 0;
		StringBuffer numerator = new StringBuffer("1"), denominator = new StringBuffer("2"), temp;
		numerator = add(numerator, denominator.toString());
		for (int i=1; i<=n; ++i) {
			System.out.println("Square root of 2 (after " + i + "th iterations) = " + numerator + "/" + denominator);
			if (numerator.length() > denominator.length()) {
				++count;
			}
			temp = new StringBuffer(denominator);
			denominator = add(denominator, numerator.toString());
			numerator = add(temp, denominator.toString());
		}
		System.out.println("Count where numerator length is greater than that of denominator = " + count);
	}
}
