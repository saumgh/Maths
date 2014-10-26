package sg.maths.projecteuler;

import java.util.LinkedList;
import java.util.List;

public class Palindrome {

	public static boolean isPalindrome(long num) {
		return isPalindrome(num, 10);
	}
	
	public static boolean isPalindrome(long num, int base) {
		
		if (num <= base) {
			return false;
		}
		
		List<Integer> digits = splitNumberIntoDigits(num, base);
		boolean palindrome = isPalindrome(digits, base);
		if (palindrome) {
			System.out.println(num + (base==10 ? "" : " in base " + base + " " + digits) + " is a palindrome");
		}
		return palindrome;
	}
	
	@SuppressWarnings("unused")
	private static boolean isPalindrome(List<Integer> digits) {
		return isPalindrome(digits, 10);
	}
	
	private static boolean isPalindrome(List<Integer> digits, int base) {
		
		int len = digits.size() - 1;
		for (int i=0; i<=len/2; ++i) {
			if (digits.get(i).intValue() != digits.get(len-i).intValue()) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean isPalindrome(String digits) {
		
		int len = digits.length() - 1;
		for (int i=0; i<=len/2; ++i) {
			if (digits.charAt(i) != digits.charAt(len-i)) {
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	private static List<Integer> splitNumberIntoDigits(long num) {
		return 	splitNumberIntoDigits(num, 10);
	}
	
	private static List<Integer> splitNumberIntoDigits(long num, int base) {
		
		List<Integer> digits = new LinkedList<Integer>();
		long n = num;
		while (n > 0) {
			digits.add(0, (int)(n % base));
			n /= base;
		}
		return digits;
	}
	
	/* http://projecteuler.net/problem=4 */
	public static long findLargestPalindromeFromProductOfNDigitNumbers(int n) {
		if (n <= 0) {
			throw new RuntimeException("Input number was " + n + " while its expected to be >0");
		}
		long min = Gen.getNthPower(10, n-1);
		long max = min * 10 - 1;
		
		long product, largestPalindrome = 0, factor = 0;
		for (long x=max; x>=min; --x) {
			product = x * x;
			if (product < largestPalindrome) {
				break;
			}
			while ((product > largestPalindrome) && (product >= x * min)) {
				if (isPalindrome(product)) {
					if (product > largestPalindrome) {
						largestPalindrome = product;
						factor = x;
					}
					System.out.println(product/x + " x " + x + " = " + product + " is a palindrome");
				}
				product -= x;
			}
		}
		
		if (largestPalindrome > 0) {
			System.out.println(largestPalindrome/factor + " x " + factor + " = " + largestPalindrome + " is the largest palindrome");
		} else {
			System.out.println("No palindrome found");
		}
		return largestPalindrome;
	}
	
	/* http://projecteuler.net/problem=36 */
	public static long getSumOfDoubleBasePalindromes(int max) {
		
		long sum = 0;
		for (int i=1; i<max; ++i) {
			if (isPalindrome(i) && isPalindrome(i, 2)) {
				sum += i;
				System.out.println("Double-base palindrome = " + i);
			}
		}
		
		System.out.println("Sum of double-base palindromes (<" + max + ") = " + sum);
		return sum;
	}
	
	/* http://projecteuler.net/problem=55 */
	public static int getLychrelNumbersCount(long max) {
		
		int count = 0;
		for (long i=10; i<max; ++i) {
			if (isLychrelNumber(i)) {
				++count;
			}
		}
		System.out.println("Lychrel numbers count = " + count);
		return count;
	}
	
	public static boolean isLychrelNumber(long num) {
		return isLychrelNumber(num, 50);
	}
	
	public static boolean isLychrelNumber(long num, int maxIterations) {
		
		String digits = String.valueOf(num);
		StringBuffer newDigits;
		for (int i=1; i<maxIterations; ++i) {
			newDigits = reverseAndAdd(digits);
			digits = newDigits.toString();
			if (isPalindrome(digits)) {
				return false;
			}
		}
		System.out.println(num + " is a Lychrel number");
		return true;
	}
	
	private static StringBuffer reverseAndAdd(String digits) {
		
		return BigCalc.add(new StringBuffer(digits).reverse(), digits);
	}
}
