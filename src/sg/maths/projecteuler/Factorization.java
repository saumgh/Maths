package sg.maths.projecteuler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Factorization {

	/* http://projecteuler.net/problem=3 */
	public static int largestPrimeFactor(long n) {
		
		TreeMap<Integer, Integer> primeFactors = getPrimeFactors(n);
		return primeFactors.lastKey();
	}
	
	public static TreeMap<Integer, Integer> getPrimeFactors(long n) {
		
		if (n < 1) { 
			throw new RuntimeException("n=" + n + " but was expected to be >=1");
		}
		
		TreeMap<Integer, Integer> primeFactors = new TreeMap<Integer, Integer>();
		long num = n;
		int factorCount;
		//System.out.print(n + " = 1");
		for (Integer factor=2; num!=1; ++factor) {
			factorCount = 0;
			while (num % factor == 0) {
				++factorCount;
				num /= factor;
			}
			if (factorCount > 0) {
				primeFactors.put(factor, factorCount);
				//System.out.print(" x " + factor + (factorCount>1 ? "^" + factorCount : ""));
			}
		}
		//System.out.println();
		return primeFactors;
	}
	
	public static TreeMap<Integer, Integer> mergePrimeFactors
										(TreeMap<Integer, Integer> primeFactorsFrom, TreeMap<Integer, Integer> primeFactorsTo) {
		
		Integer freqFrom, freqTo;
		for (int factor : primeFactorsFrom.keySet()) {
			freqFrom = primeFactorsFrom.get(factor);
			freqTo = primeFactorsTo.get(factor);
			if ((freqTo == null) || (freqTo < freqFrom)) {
				primeFactorsTo.put(factor, freqFrom);
			}
		}
		return primeFactorsTo;
	}
	
	public static TreeSet<Integer> mergePrimeFactors
										(TreeMap<Integer, Integer> primeFactorsFrom, TreeSet<Integer> primeFactorsTo) {
		
		Integer freqFrom;
		for (int prime : primeFactorsFrom.keySet()) {
			freqFrom = primeFactorsFrom.get(prime);
			int factor = 1;
			while (freqFrom > 0) {
				factor *= prime;
				--freqFrom;
			}
			if (!primeFactorsTo.contains(factor)) {
				primeFactorsTo.add(factor);
			}
		}
		return primeFactorsTo;
	}

	/* http://projecteuler.net/problem=5 */
	public static long smallestProductOfOneToN(long n) {
		
		TreeMap<Integer, Integer> primeFactors = getPrimeFactors(n);
		for (long i=n-1; i>1; --i) {
			primeFactors = mergePrimeFactors(getPrimeFactors(i), primeFactors);
		}
		
		long product = 1;
		int freq;
		for (Entry<Integer, Integer> entry : primeFactors.entrySet()) {
			freq = entry.getValue();
			while (freq > 0) {
				product *= entry.getKey();
				--freq;
			}
		}

		System.out.println("Smallest product of 1.." + n + " = " + product);
		return product;
	}
	
	/* http://projecteuler.net/problem=7 */
	public static long primeN(int n) {
		
		TreeSet<Long> primes = nPrimes(n);
		return primes.last();
	}
	
	public static TreeSet<Long> nPrimes(int n) {
		
		TreeSet<Long> primes = new TreeSet<Long>();
		long i = 1;
		while (primes.size() < n) {
			++i;
			boolean isPrime = isPrime(i, primes);
			if (isPrime) {
				System.out.println(primes.size() + "th prime = " + i);
			}
		}
		return primes;
	}

	/* http://projecteuler.net/problem=10 */
	public static long sumOfPrimesBelowN(long max) {
		TreeSet<Long> primes = new TreeSet<Long>();
		long i = 1, sum = 0;
		while (i < max) {
			++i;
			boolean isPrime = isPrime(i, primes);
			if (isPrime) {
				sum += i;
				System.out.println(primes.size() + "th prime = " + i);
			}
		}

		System.out.println("sum = " + sum);
		return sum;		
	}
	
	/* http://projecteuler.net/problem=12 */
	public static long getTriangularNumberOverNFactors(int n) {
	
		int count = 0, maxCount = 0;
		int i = 1;
		long triN = 0;
		while (count <= n) {
			triN += i;
			
			count = 0;
			for (int factor=1; (factor*factor)<=triN; ++factor) {
				if (triN % factor == 0) {
					if (factor*factor == triN) {
						count += 1;
					} else {
						count += 2;
					}
				}
			}
			
			if (maxCount < count) {
				maxCount = count;
				System.out.println("Number of factors for " + triN + " = " + count);
			}
			++i;
		}

		System.out.println("Triangular Number with >" + n + " factors = " + triN);
		return triN;
	}
	
	/* http://projecteuler.net/problem=21 */
	public static int getAmicableNumbersSum(int n) {

		int sum = 0, factorSum;
		Set<Integer> amicableNumbers = new HashSet<Integer>();
		for (int i=2; (i<n) && !amicableNumbers.contains(i); ++i) {
			factorSum = sumOfFactors(i);
			if ((factorSum != i) && (sumOfFactors(factorSum) == i)) {
				sum += i + factorSum;
				amicableNumbers.add(i);
				amicableNumbers.add(factorSum);
				System.out.println("Amicable pair (" + i + ", " + factorSum + ")");
			}
		}
		
		System.out.println("Sum of all amicable pairs for " + n + " = " + sum);
		return sum;
	}

	private static int sumOfFactors(int n) {

		int sum = 1, max = n;
		for (int i=2; i<max; ++i) {
			if (n % i == 0) {
				sum += i;
				max = n / i;
				if (max != i) {
					 sum += max;
				}
				//System.out.println("Factor pair (" + i + ", " + max + ")");
			}
		}
		
		//System.out.println("Sum of factors for " + n + " = " + sum);
		return sum;
	}

	/* http://projecteuler.net/problem=23 */
	public static int nonAbundantSplitSum(int n) {

		int sum = 0, abundantNumber;
		Set<Integer> abundantNumbers = new TreeSet<Integer>();
		Iterator<Integer> abundantNumbersIter;
		for (int i=1; i<=n; ++i) {
			if (sumOfFactors(i) > i) {
				abundantNumbers.add(i);
			}
			abundantNumbersIter = abundantNumbers.iterator();
			boolean isAbundantSum = false;
			while (abundantNumbersIter.hasNext()) {
				abundantNumber = abundantNumbersIter.next();
				if (abundantNumbers.contains(i-abundantNumber)) {
					isAbundantSum = true;
					break;
				}
			}
			if (!isAbundantSum) {
				sum += i;
				System.out.println("Non-abundant number = " + i);
			}
		}
		
		System.out.println("Non-abundant split sum = " + sum);
		return sum;
	}
	
	/* http://projecteuler.net/problem=26 */
	public static int getMaxReciprocalCycleNumber(int max) {
		
		Map<Integer, Integer> numeratorPosMap;
		int numerator, cycleSize, cycleSizeMax = 0, iMax = 0;
		Integer pos = null;
		String decimal, maxCycleDecimal = "";
		for (int i=2; i<max; ++i) {
			decimal = "";
			numerator = 10;
			numeratorPosMap = new HashMap<Integer, Integer>();
			cycleSize = 0;
			do {
				numeratorPosMap.put(numerator, numeratorPosMap.size());
				if (numerator < i) {
					decimal += "0";
				} else {
					decimal += numerator/i;
					numerator %= i;
				}
				numerator *= 10;
			} while ((numerator != 0) && ((pos = numeratorPosMap.get(numerator)) == null));
			
			if (numerator == 0) {
				decimal = "0." + decimal;
			} else {
				decimal = "0." + decimal.substring(0, pos) + "(" + decimal.substring(pos) + ")";
				cycleSize = numeratorPosMap.size() - pos;
				if (cycleSizeMax < cycleSize) {
					cycleSizeMax = cycleSize;
					maxCycleDecimal = decimal;
					iMax = i;
					System.out.println("max cycle size = " + cycleSizeMax + " for 1/" + iMax + " = " + maxCycleDecimal);
				}
			}
			//System.out.println("    cycle size = " + cycleSize + " for 1/" + i + " = " + decimal);
		}
		
		return iMax;
	}
	
	/* http://projecteuler.net/problem=27 */
	public static long getQuadraticPrimesCoefficientsProduct(int a, int b) {
		return getQuadraticPrimesCoefficientsProduct(-a, a, -b, b);
	}
	
	public static long getQuadraticPrimesCoefficientsProduct(int a1, int a2, int b1, int b2) {
		
		TreeSet<Long> primes = new TreeSet<Long>();
		int iMax = 0, jMax = 0, nMax = 0;
		long numMax = 0;
		for (int i=a1; i<=a2; ++i) {
			for (int j=b1; j<=b2; ++j) {
				int n = 0;
				int num = j; 
				while (isPrime(num, primes)) {
					//System.out.println("Prime (n = " + n + ") = " + num + " for a = " + i + ", b = " + j);
					if (nMax < n) {
						nMax = n;
						iMax = i;
						jMax = j;
						System.out.println("Max n = " + nMax + ", num = " + num + " for a = " + i + ", b = " + j);
					}
					++n;
					num = n * n + i * n + j;
					if (numMax < num) {
						for (++numMax; numMax<num; ++numMax) {
							isPrime(numMax, primes);
						}
						//System.out.println("Max number = " + numMax);
					}
				}
			}			
		}
		System.out.println("Max a*b = " + iMax*jMax);
		return iMax * jMax;
	}
	
	private static boolean isPrime(long n) {
		return isPrime(n, null);
	}
	
	private static boolean isPrime(long n, TreeSet<Long> coninuousPrimes) {
		
		if (n < 2) {
			return false;
		}
		
		long last = 1;
		if (coninuousPrimes != null) {
			if (coninuousPrimes.contains(n)) {
				return true;
			}
			for (long prime : coninuousPrimes) {
				if (prime > n / last) {
					coninuousPrimes.add(n);
					return true;
				}
				if (n % prime == 0) {
					return false;
				}
				last = prime;
			}
		}
		for (long i=last+1; i<n/last; ++i) {
			if (n % i == 0) {
				return false;
			}
			last = i;
		}
		if (coninuousPrimes != null) {
			coninuousPrimes.add(n);
		}
		return true;		
	}
	
	/* http://projecteuler.net/problem=35 */
	public static long getCircularPrimesCount(long max) {
		
		TreeSet<Long> primes = new TreeSet<Long>();
		for (long i=2; i<max; ++i) {
			isPrime(i, primes);
		}
		Set<Long> circularPrimes = new TreeSet<Long>();
		long num;
		int powerOf10;
		boolean circularPrime;
		for (long prime : primes) {
			if (circularPrimes.contains(prime)) {
				continue;
			}

			Set<Long> circularNums = new HashSet<Long>();
			circularNums.add(prime);
			circularPrime = true;
			powerOf10 = Gen.getNthPower(10, String.valueOf(prime).length() - 1);
			num = (prime % 10) * powerOf10 + prime / 10;
			while (num != prime) {
				if (!primes.contains(num)) {
					circularPrime = false;
					break;
				}
				circularNums.add(num);
				num = (num % 10) * powerOf10 + num / 10;
			}
			if (circularPrime) {
				circularPrimes.addAll(circularNums);
				System.out.println("Circular prime(s) = " + circularNums);
			}
		}
		
		System.out.println(circularPrimes.size() + " circular primes found (<" + max + ") = " + circularPrimes);
		return circularPrimes.size();
	}
	
	/* http://projecteuler.net/problem=37 */
	public static long getSumOfTruncatablePrimes() {
		
		long sum = 0, num, powerOf10;
		int count = 0;
		boolean truncablePrime;
		TreeSet<Long> primes = new TreeSet<Long>();
		List<Long> rightTruncablePrimes, leftTrunablePrimes;
		for (int i=2; count<11; ++i) {
			if (isPrime(i, primes)) {
				if (i < 11) {
					continue;
				}
				truncablePrime = true;
				rightTruncablePrimes = new ArrayList<Long>();
				leftTrunablePrimes = new ArrayList<Long>();
				powerOf10 = 1;
				num = i / 10;
				while (num > 0) {
					powerOf10 *= 10;
					if (!primes.contains(num)) {
						truncablePrime = false;
						break;
					}
					rightTruncablePrimes.add(num);
					num /= 10;
				}
				if (truncablePrime) {
					num = i % powerOf10;
					while (num > 0) {
						if (!primes.contains(num)) {
							truncablePrime = false;
							break;
						}
						leftTrunablePrimes.add(num);
						powerOf10 /= 10;
						num %= powerOf10;
					}
				}
				if (truncablePrime) {
					sum += i;
					++count;
					System.out.println(i + " is a truncable prime: right " + rightTruncablePrimes + ", left " + leftTrunablePrimes);
				}
			}
		}
		
		System.out.println("Sum of truncatable primes = " + sum);
		return sum;
	}

	/* http://projecteuler.net/problem=41 */
	public static int getMaxPandigitalPrime() {
		
		int i;
		for (i=987654321; i>=12; --i) {
			if (Gen.isPandigital(i) && isPrime(i)) {
				break;
			}
		}
		
		System.out.println("Max pandigital prime = " + i);
		return i;
	}
	
	/* http://projecteuler.net/problem=46 */
	public static long minNumberWithoutGoldbachsConjecture() {
		
		TreeSet<Long> primes = new TreeSet<Long>();
		for (long i=2; ; ++i) {
			if (!isPrime(i, primes) && (i%2==1)) {
				boolean goldbachsConjectureHeld = false;
				for (long prime : primes) {
					if ((prime<i) && ((i-prime)%2==0)) {
						long sqrt = Gen.getPerfectSquareRoot((i-prime)/2);
						if (sqrt > 0) {
							System.out.println(i + " = " + prime + " + " + 2 + " * " + sqrt + "^2");
							goldbachsConjectureHeld = true;
							break;
						}
					}
				}
				if (!goldbachsConjectureHeld) {
					System.out.println("Min number without Goldbachs Conjecture = " + i);
					return i;
				}
			}
		}
	}

	/* http://projecteuler.net/problem=47 */
	public static long getNContinuousNumbersWithNDistinctPrimeFactorsEach(int n) {

		TreeSet<Integer> primeFactors = new TreeSet<Integer>();
		int count;
		for (long i=2; ; ++i) {
			primeFactors = mergePrimeFactors(getPrimeFactors(i), primeFactors);
			count = 1;
			while (primeFactors.size() == count*n) {
				if (count == n) {
					System.out.println(n + " continuous numbers with " +
									   n + " distinct prime factors each starting with " + (i-n+1));
					return i-n+1;
				}
				++i;
				primeFactors = mergePrimeFactors(getPrimeFactors(i), primeFactors);
				++count;
			}
			primeFactors.clear();
		}
	}
	
	/* http://projecteuler.net/problem=49 */
	public static void primePermutationSequences() {
		
		Map<TreeSet<Integer>, TreeSet<Integer>> primeComboToSeqMap = new HashMap<TreeSet<Integer>, TreeSet<Integer>>();
		TreeSet<Long> primes = new TreeSet<Long>();
		primes.add(2l);
		for (int i=3; i<=9997; i+=2) {
			if (isPrime(i, primes) && (i>=1001)) {
				TreeSet<Integer> primeCombo = new TreeSet<Integer>();
				int num = i;
				while (num>0) {
					primeCombo.add(num%10);
					num /= 10;
				}
				TreeSet<Integer> primeSeq = primeComboToSeqMap.get(primeCombo);
				if (primeSeq == null) {
					primeSeq = new TreeSet<Integer>();
					primeComboToSeqMap.put(primeCombo, primeSeq);
				}
				primeSeq.add(i);
			}
		}
		for (TreeSet<Integer> primeSeq : primeComboToSeqMap.values()) {
			if (primeSeq.size()>=3) {
				for (int prime : primeSeq) {
					if (primeSeq.contains(prime+3330) & primeSeq.contains(prime+6660)) {
						System.out.println(prime + " " + (prime+3330) + " " + (prime+6660));
					}
				}
			}
		}
	}
	
	/* http://projecteuler.net/problem=50 */
	public static void mostConsecutivePrimeSum(long n) {
		
		TreeSet<Long> primes = new TreeSet<Long>();
		long sum = 0;
		int iMax = 0;
		for (long i=2; i<n; ++i) {
			if (isPrime(i, primes) && (sum < n)) {
				sum += i;
				++iMax;
			}
		}
		TreeSet<Long> primeSeq = new TreeSet<Long>(), primeSeqMax = null;
		ArrayList<Long> primesList = new ArrayList<Long>(primes);
		for (int i=iMax; i>=2; --i) {
			for (int j=0; j<=primesList.size()-i; ++j) {
				sum = 0;
				for (int k=j; k<j+i; ++k) {
					primeSeq.add(primesList.get(k));
					sum += primesList.get(k);
				}
				if ((sum <= primes.last()) && primes.contains(sum)) {
					if ((primeSeqMax == null) || (primeSeqMax.size() < primeSeq.size())) {
						primeSeqMax = new TreeSet<Long>(primeSeq);
						primeSeqMax.add(sum);
					}
				}
				primeSeq.clear();
			}
		}
		System.out.println("Most consecutive primes & sum (" + (primeSeqMax.size()-1) + " terms) = " + primeSeqMax);
	}
	
	/* http://projecteuler.net/problem=51 */
	public static long nPrimesByDigitReplacements(int n) {
		
		//56**3 -> 56003, 56113, 56333, 56443, 56663, 56773, 56993
		Map<String, LinkedList<Long>> digitsToNums = new HashMap<String, LinkedList<Long>>();
		TreeSet<Long> primes = new TreeSet<Long>();
		long powerOf10 = 10;
		for (long i=2; ; ++i) {
			if (i == powerOf10) {
				digitsToNums.clear();
				powerOf10 *= 10;
			}
			if (!isPrime(i, primes)) {
				continue;
			}
			TreeMap<Integer, Integer> digits = new TreeMap<Integer, Integer>();
			long num = i;
			while (num > 0) {
				int digit = (int)(num % 10);
				Integer count = digits.get(digit);
				if (count == null) {
					count = 0;
				}
				++count;
				digits.put(digit, count);
				num /= 10;
			}
			String origNumStr = String.valueOf(i);
			for (Entry<Integer, Integer> entry : digits.entrySet()) {
				String digitStr = String.valueOf(entry.getKey());
				int count = entry.getValue();
				List<String> numList = new ArrayList<String>();
				while (count >= 2) {
					numList.addAll(getNumCombos(origNumStr, 0, digitStr, count, entry.getValue()));
					--count;
				}
				for (String numStr : numList) {
					LinkedList<Long> nums = digitsToNums.get(numStr);
					if (nums == null) {
						nums = new LinkedList<Long>();
						digitsToNums.put(numStr, nums);
					}
					nums.add(i);
					if (nums.size() == n) {
						System.out.println(n + " prime value family (" + numStr + ") = " + nums);
						return nums.getFirst();
					}							
				}
			}
		}
	}
	
	private static List<String> getNumCombos(String origNumStr, int from, String digitStr, int count, int totalCount) {
		
		List<String> numList = new ArrayList<String>();
		int j = from;
		for (int i=1; i<=totalCount; ++i) {
			StringBuffer numStr = new StringBuffer(origNumStr);
			j = origNumStr.indexOf(digitStr, j);
			if (j < 0) {
				break;
			}
			numStr.setCharAt(j, '*');
			++j;
			if (count > 1) {
				numList.addAll(getNumCombos(numStr.toString(), j, digitStr, count-1, totalCount-1));
			} else {
				numList.add(numStr.toString());
			}
		}
		return numList;
	}

	/* http://projecteuler.net/problem=58 */
	public static long getSpiralSideLengthWithPrimesUnderNPercent(int n) {
		
		List<Long> diagonals = new ArrayList<Long>(4);
		long a = 3, d = 2, primes = 0, total = 1;
		while (true) {
			total += 4;
			diagonals = Gen.getAPSequence(a, d, 4, diagonals);
			// diagonals.get(3) is a perfect square, so not a prime
			for (int j=0; j<3; ++j) {
				if (isPrime(diagonals.get(j))) {
					++primes;
					//System.out.println(diagonals.get(j) + " is a prime between " + a + " and " + diagonals.get(3));
				}
			}
			if (primes*100 < total*n) {
				break;
			}
			System.out.println("Spiral side with length " + (d+1) + " has " + primes + " prime out of " + total + " diagonals");
			d += 2;
			a = diagonals.get(3) + d;
		}
		System.out.println("Spiral side with length " + (d+1) + " has prime diagonals %age " + ((float)primes*100/total)
				  		 + " < " + n + "%");
		return d+1;
	}
}
