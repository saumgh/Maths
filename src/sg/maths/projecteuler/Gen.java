package sg.maths.projecteuler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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

class RevIntegerSort implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		return -o1.compareTo(o2);
	}
}

public class Gen {

	/* http://projecteuler.net/problem=6 */
	public static long diffSumSquareAndSquareSum(int n) {
		
		long diff = 0;
		long sum = (n * (n+1)) / 2 - 1; 
		for (int i=1; i<n; ++i) {
			diff += i * sum;
			sum -= i+1;
		}
		diff *= 2;
		System.out.println("(SquareOfSum - SumOfSquare) for 1.." + n + " = " + diff);
		return diff;
	}
	
	/* http://projecteuler.net/problem=8 */
	public static long findGreatestProduct(String num, int nDigits) {
		
		long product, maxProduct = -1;
		int first, i, start, maxStart = -1;
		
		start = 0;
		i = start + nDigits - 1;
		first = getNthInt(num, start);
		product = getNDigitProduct(num.substring(start, i+1), 0, nDigits);
		while (i < num.length()-1) {
			if (maxProduct < product) {
				maxProduct = product;
				maxStart = start;
			}
			
			if (first == 0) {
				product = getNDigitProduct(num.substring(start+1, i+1), 0, nDigits-1);
			} else {
				product /= first;
			}

			++start;
			++i;
			first = getNthInt(num, start);
			product *= getNthInt(num, i);
		}
		
		System.out.println("Max product of " + nDigits + " digitSet starting at " + maxStart + "th index = " + maxProduct);
		return product;
	}
	
	private static int getNDigitProduct(String num, int start, int nDigits) {
		
		int product = 1;
		for (int i=start; i<start+nDigits; ++i) {
			product *= getNthInt(num, i);
		}
		return product;
	}

	private static int getNthInt(String num, int pos) {
		return Integer.parseInt(num.substring(pos, pos+1));
	}
	
	/* http://projecteuler.net/problem=9 */
	public static void findPythagoreanTriplets(long tripletSum) {
		
		long a, b, c, aSq;
		long product, sum;
		int count = 0;
		String details, oldDetails;
		TreeMap<Long, String> tripletSumMap = new TreeMap<Long, String>();
		for (a=1; a<tripletSum/3; ++a) {
			aSq = a * a;
			for (b=a+1; b<(tripletSum-a)/2; ++b) {
				for (c=b+1; c<(a+b) && c<=(tripletSum-a-b); ++c) {
					if ((c+b) * (c-b) == aSq) {
						++count;
						sum = a + b + c;
						product = a * b * c;
						
						details = count + ". " + a + ", " + b + ", " + c
								   + " (sum = " + sum + ", product = " + product + ")";
						System.out.println(details);

						oldDetails = tripletSumMap.get(sum);
						if (oldDetails == null) {
							oldDetails = "";
						} else {
							oldDetails += "\n";
						}
						
						tripletSumMap.put(sum, oldDetails+details);
					}
				}
			}			
		}

		int N = 5;
		int topNPercent = tripletSumMap.size() * N / 100;		
		System.out.println("\ntop " + N + "% (" + topNPercent + " in " + tripletSumMap.size()
						  + ") entries with highest sums are:\n");
		int i = 0;
		topNPercent = tripletSumMap.size() - topNPercent;
		for (Entry<Long, String> entry : tripletSumMap.entrySet()) {
			++i;
			if (i > topNPercent) {
				System.out.println(entry.getValue());
			}
		}
	}
	
	/* http://projecteuler.net/problem=11 */
	public static long getLargestProductOfNAdjacentsInGrid(int[][] grid, int n) {
	
		long first, product, maxProduct = -1;
		int start, i1, j1, i2, j2;
		
		int i, j, k;
		
		for (i=0; i<grid.length; ++i) {
			product = 1;
			start = 0;
			for (j=start; j<start+n; ++j) {
				product *= grid[i][j];
			}
			if (maxProduct < product) {
				maxProduct = product;
				j1 = start;
				j2 = j;
				System.out.print("Current max product: " + grid[i][j1]);
				for (k=j1+1; k<j2; ++k) {
					System.out.print(" x " + grid[i][k]);
				}
				System.out.println(" = " + maxProduct);
			}
			for (; j<grid[i].length; ++j) {
				first = grid[i][start];
				if (first == 0) {
					product = 1;
					for (k=start+1; k<j; ++k) {
						product *= grid[i][k];
					}
				} else {
					product /= first;
				}
				product *= grid[i][j];
				++start;
				if (maxProduct < product) {
					maxProduct = product;
					j1 = start;
					j2 = j;
					System.out.print("Current max product: " + grid[i][j1]);
					for (k=j1+1; k<=j2; ++k) {
						System.out.print(" x " + grid[i][k]);
					}
					System.out.println(" = " + maxProduct);
				}
			}
		}
		
		for (j=0; j<grid[0].length; ++j) {
			product = 1;
			start = 0;
			for (i=start; i<start+n; ++i) {
				product *= grid[i][j];
			}
			if (maxProduct < product) {
				maxProduct = product;
				i1 = start;
				i2 = i;
				System.out.print("Current max product: " + grid[i1][j]);
				for (k=i1+1; k<i2; ++k) {
					System.out.print(" x " + grid[k][j]);
				}
				System.out.println(" = " + maxProduct);
			}
			for (; i<grid.length; ++i) {
				first = grid[start][j];
				if (first == 0) {
					product = 1;
					for (k=start+1; k<i; ++k) {
						product *= grid[k][j];
					}
				} else {
					product /= first;
				}
				product *= grid[i][j];
				++start;
				if (maxProduct < product) {
					maxProduct = product;
					i1 = start;
					i2 = i;
					System.out.print("Current max product: " + grid[i1][j]);
					for (k=i1+1; k<=i2; ++k) {
						System.out.print(" x " + grid[k][j]);
					}
					System.out.println(" = " + maxProduct);
				}
			}
		}
		
		int d, iStart, jStart, iK, jK;
		
		for (d=grid.length-n; d>=0; --d) {
			product = 1;
			iStart = d;
			jStart = 0;
			for (i=iStart, j=jStart; (i<iStart+n) && (jStart<n) && (i<grid.length) && (j<grid[i].length); ++i, ++j) {
				product *= grid[i][j];
			}
			if (maxProduct < product) {
				maxProduct = product;
				i1 = iStart; j1 = jStart;
				i2 = i; j2 = j;
				System.out.print("Current max product: " + grid[i1][j1]);
				for (iK=iStart+1,jK=jStart+1; (iK<i2) && (jK<j2); ++iK, ++jK) {
					System.out.print(" x " + grid[iK][jK]);
				}
				System.out.println(" = " + maxProduct);
			}
			for (; (i<grid.length) && (j<grid[i].length); ++i, ++j) {
				first = grid[iStart][jStart];
				if (first == 0) {
					product = 1;
					for (iK=iStart+1,jK=jStart+1; (iK<i) && (jK<j); ++iK, ++jK) {
						product *= grid[iK][jK];
					}
				} else {
					product /= first;
				}
				product *= grid[i][j];
				++iStart;
				++jStart;
				if (maxProduct < product) {
					maxProduct = product;
					i1 = iStart; j1 = jStart;
					i2 = i; j2 = j;
					System.out.print("Current max product: " + grid[i1][j1]);
					for (iK=iStart+1,jK=jStart+1; (iK<=i2) && (jK<=j2); ++iK, ++jK) {
						System.out.print(" x " + grid[iK][jK]);
					}
					System.out.println(" = " + maxProduct);
				}
			}
		}
		
		for (d=n-1; d<grid.length; ++d) {
			product = 1;
			iStart = d;
			jStart = 0;
			for (i=iStart, j=jStart; (i>iStart-n) && (jStart<n) && (i>=0) && (j<grid[i].length); --i, ++j) {
				product *= grid[i][j];
			}
			if (maxProduct < product) {
				maxProduct = product;
				i1 = iStart; j1 = jStart;
				i2 = i; j2 = j;
				System.out.print("Current max product: " + grid[i1][j1]);
				for (iK=iStart-1, jK=jStart+1; (iK>i2) && (jK<j2); --iK, ++jK) {
					System.out.print(" x " + grid[iK][jK]);
				}
				System.out.println(" = " + maxProduct);
			}
			for (; (i>=0) && (j<grid[i].length); --i, ++j) {
				first = grid[iStart][jStart];
				if (first == 0) {
					product = 1;
					for (iK=iStart-1,jK=jStart+1; (iK>i) && (jK<j); --iK, ++jK) {
						product *= grid[iK][jK];
					}
				} else {
					product /= first;
				}
				product *= grid[i][j];
				--iStart;
				++jStart;
				if (maxProduct < product) {
					maxProduct = product;
					i1 = iStart; j1 = jStart;
					i2 = i; j2 = j;
					System.out.print("Current max product: " + grid[i1][j1]);
					for (iK=iStart-1, jK=jStart+1; (iK>=i2) && (jK<=j2); --iK, ++jK) {
						System.out.print(" x " + grid[iK][jK]);
					}
					System.out.println(" = " + maxProduct);
				}
			}
		}
		
		return maxProduct;
	}
	
	/* http://projecteuler.net/problem=14 */
	public static long getNumWithLongestCollatzSequence(long max) {
		
		if (max < 1) {
			return 0;
		}
		
		HashMap<Long, Integer> numToCollatzLengthMap = new HashMap<Long, Integer>();
		numToCollatzLengthMap.put(new Long(1),  1);
		long i, seq, numWithMaxSeq = 1;
		int seqSize = 1, maxSeqSize = 1;
		TreeSet<Long> collatzSequence;
		for (i=2; i<max; ++i) {
			seq = i;
			collatzSequence = new TreeSet<Long>();
			while (numToCollatzLengthMap.get(seq) == null) {
				collatzSequence.add(seq);
				if (seq % 2 == 0) {
					seq /= 2;
				} else {
					seq = 3 * seq + 1;
				}
			}

			seqSize = collatzSequence.size() + numToCollatzLengthMap.get(seq);
			numToCollatzLengthMap.put(new Long(i), seqSize);
			
			if (maxSeqSize < seqSize) {
				maxSeqSize = seqSize;
				numWithMaxSeq = i;
				System.out.println(i + " has current longest Collatz sequence of size = " + seqSize);
			}
		}
		
		System.out.println((i-1) + " has Collatz sequence of size = " + seqSize);
		
		return numWithMaxSeq;
	}
	
	/* http://projecteuler.net/problem=17 */
	public static int getNumberLetterCount(int n) {
		
		int count = 0;
		for (int i=1; i<=n; ++i) {
			count += getNumberLetter(i, "", "").length();
		}
		System.out.println("sum of letters = " + count);
		return count;
	}
	
	public static String getNumberLetter(int n) {
		return getNumberLetter(n, "-", " ");
	}
	
	public static String getNumberLetter(int n, String hyphenSep, String spaceSep) {
		
		int num = n / 100, digit = n % 100, unit = 10;
		String numberLetter = "";
		if (digit > 0) {
			numberLetter = numberToLetter(digit);
			if (numberLetter.isEmpty()) {
				numberLetter = numberToLetter(digit - digit % 10) + hyphenSep + numberToLetter(digit % 10);
			}
			numberLetter = (num>0 ? "and"+spaceSep : "") + numberLetter;
		}
		while (num > 0) {
			digit = num % 10;
			num = num / 10;
			unit *= 10;
			numberLetter = (digit>0 ? numberToLetter(digit)+spaceSep+numberToLetter(unit) : "")
						 + (numberLetter.isEmpty() ? "" : spaceSep+numberLetter);
		}
		
		System.out.println(n + " in words is \"" + numberLetter + "\" (" + numberLetter.length() + " letters)");
		return numberLetter;
	}
	
	private static String numberToLetter(int n) {
		
		switch (n) {
			case    1: return "one";
			case    2: return "two";
			case    3: return "three";
			case    4: return "four";
			case    5: return "five";
			case    6: return "six";
			case    7: return "seven";
			case    8: return "eight";
			case    9: return "nine";
			case   10: return "ten";
			case   11: return "eleven";
			case   12: return "twelve";
			case   13: return "thirteen";
			case   14: return "fourteen";
			case   15: return "fifteen";
			case   16: return "sixteen";
			case   17: return "seventeen";
			case   18: return "eighteen";
			case   19: return "nineteen";
			case   20: return "twenty";
			case   30: return "thirty";
			case   40: return "forty";
			case   50: return "fifty";
			case   60: return "sixty";
			case   70: return "seventy";
			case   80: return "eighty";
			case   90: return "ninety";
			case  100: return "hundred";
			case 1000: return "thousand";
			  default: return "";
		}
	}
	
	/* http://projecteuler.net/problem=18 */
	public static int getMaximumPathSum(int[][] triangle) {
		LinkedList<Integer> path = getMaximumPathSum(triangle, 0, 0, new LinkedList<Integer>());
		int sum = getPathSum(path);
		System.out.println("Maximum sum = " + sum + ", path = " + path);
		return sum;
	}
	
	public static LinkedList<Integer> getMaximumPathSum(int[][] triangle, int i, int j, LinkedList<Integer> path) {
		
		LinkedList<Integer> path1 = new LinkedList<Integer>(path);
		path1.add(triangle[i][j]);
		LinkedList<Integer> path2 = new LinkedList<Integer>(path);
		path2.add(triangle[i][j]);
		if (i+1 < triangle.length) {
			if (j <= triangle[i+1].length) {
				path1 = getMaximumPathSum(triangle, i+1, j, path1);
			}
			if (j+1 < triangle[i+1].length) {
				path2 = getMaximumPathSum(triangle, i+1, j+1, path2);
			}
		}
		@SuppressWarnings("unused")
		int sum, sum1 = getPathSum(path1), sum2 = getPathSum(path2);
		if (sum1 >= sum2) {
			sum = sum1;
			path = path1;
		} else {
			sum = sum2;
			path = path2;
		}
		//System.out.println("Maximum sum = " + sum + ", path = " + path);
		return path;
	}
	
	private static int getPathSum(LinkedList<Integer> path) {
		
		int sum = 0;
		for (int node : path) {
			sum += node;
		}
		return sum;
	}
	
	/* http://projecteuler.net/problem=19 */
	public static int countSundaysTheFirst(Calendar startDate, Calendar endDate) {
		
		//1 Jan 1900 was a Monday => 1 April 1900 was a Sunday
		Calendar date = Calendar.getInstance();
		date.clear();
		date.set(0, 3, 1);

		int days = 0, count = 0;
		while (date.before(endDate)) {
			days += daysInMonth(date.get(Calendar.MONTH), date.get(Calendar.YEAR));
			days %= 7;
			if (date.after(startDate)) {
				if (days == 0) {
					++count;
				}
			}
			date.add(Calendar.MONTH, 1);
		}
		
		System.out.println("Count of Sundays that fell on the first of the month (between "
						  + dateToStr(startDate) + " to " + dateToStr(endDate) + ") = " + count);
		return count;
	}
	
	private static String dateToStr(Calendar date) {
		return (1900+date.get(Calendar.YEAR)) + "/" + (date.get(Calendar.MONTH)+1) + "/" + date.get(Calendar.DATE);
	}
	
	@SuppressWarnings("unused")
	private static int daysInMonth(Integer mm) {
		return daysInMonth(mm, null);
	}
	
	private static int daysInMonth(Integer mm, Integer yy) {
		
		switch(mm) {
			case 0:
			case 2:
			case 4:
			case 6:
			case 7:
			case 9:
			case 11:
				return 31;
			case 3:
			case 5:
			case 8:
			case 10:
				return 30;
			case 1:
				if (yy == null) {
					throw new RuntimeException("yyyy must be passed for mm = 1");
				}
				yy += 1900;
				if (yy % 400 == 0) {
					return 29;
				}
				if (yy % 100 == 0) {
					return 28;					
				}
				if (yy % 4 == 0) {
					return 29;
				}
				return 28;
			default:
				throw new RuntimeException("mm should be in [0, 11]");
		}
	}
	
	/* http://projecteuler.net/problem=22 */
	public static long getNameScoreSum(String[] names) {
		
		Map<String, Integer> nameCountMap = new TreeMap<String, Integer>();
		Integer count;
		for (int i=0; i<names.length; ++i) {
			count = nameCountMap.get(names[i]);
			if (count == null) {
				count = 0;
			}
			nameCountMap.put(names[i], ++count);
		}
		
		long sum = 0;
		int rank = 0;
		Iterator<Entry<String, Integer>> entryIter = nameCountMap.entrySet().iterator();
		Entry<String, Integer> entry;
		while (entryIter.hasNext()) {
			entry = entryIter.next();
			for (int i=0; i<entry.getValue(); ++i) {
				++rank;
				sum += rank * getNameScore(entry.getKey());
				System.out.println("Rank for " + entry.getKey() + " = " + rank);
			}
		}
		System.out.println("Total score = " + sum);
		return sum;
	}
	
	private static int getNameScore(String name) {
		
		int score = 0;
		name = name.toUpperCase();
		for (int i=0; i<name.length(); ++i) {
			score += name.charAt(i) - 'A' + 1;
		}
		System.out.println("Score for " + name + " = " + score);
		return score;
	}
	

	/* http://projecteuler.net/problem=24 */
	public static String ithLexicographicPermutationFor0ToN(int n, long i) {
		
		List<Integer> numList = new ArrayList<Integer>(n+1);
		int j = 1;
		long permutation = 1;
		while (j <= n+1) {
			numList.add(j-1);
			permutation *= j;
			++j;
		}
		
		if (i > permutation) {
			throw new RuntimeException("i=" + i + " must be <= " + (n+1) + "P" + (n+1) + "=" + permutation);
		}
		
		String ithNum = "";
		int index;
		long relPos = i;
		j = n + 1;
		while (relPos != 0) {
			permutation /= j;
			while (permutation > relPos) {
				ithNum += numList.remove(0);
				--j;
				permutation /= j;
			}
			
			index = (int) ((relPos - 1) / permutation);
			ithNum += numList.remove(index);
			--j;
			relPos %= permutation;
			System.out.println("First " + ithNum.length() + " digit(s) of " + i + "th lexicographic permutation = " + ithNum);
		}
		while (numList.size() > 0) {
			ithNum += numList.remove(numList.size() - 1);
		}
		
		System.out.println("=> " + i + "th lexicographic permutation = " + ithNum);
		return ithNum;
	}
	
	/* http://projecteuler.net/problem=28 */
	public static long getSumOfNumberSpiralDiagonals(int nSide) {
		
		List<Long> diagonals = getSpiralDiagonals(nSide);
		long sum = 0;
		for (long d : diagonals) {
			sum += d;
		}
		System.out.println("Sum of number spiral diagonals with side " + nSide + " = " + sum);
		return sum;
	}
	
	public static List<Long> getSpiralDiagonals(int nSide) {
		
		if ((nSide < 3) || (nSide % 2 == 0)) {
			throw new RuntimeException("An odd value (> 1) is expected for nSide while " + nSide + " was passed");
		}
		
		List<Long> diagonals = new ArrayList<Long>();
		diagonals.add(1l);
		long a = 3, d = 2;
		for (int i=1; i<=nSide/2; ++i) {
			diagonals.addAll(getAPSequence(a, d, 4));
			d += 2;
			a = diagonals.get(diagonals.size()-1) + d;
		}
		return diagonals;
	}
	
	public static List<Long> getAPSequence(long a, long d, int n) {
		
		List<Long> diagonals = new ArrayList<Long>();
		return getAPSequence(a, d, n, diagonals);
	}
		
	public static List<Long> getAPSequence(long a, long d, int n, List<Long> diagonals) {
		
		diagonals.clear();
		for (int i=1; i<=n; ++i) {
			diagonals.add(a);
			//System.out.println("a = " + a);
			a += d;
		}
		return diagonals;
	}
	
	/* http://projecteuler.net/problem=30 */
	public static int getSumOfNthPowerDigitSumNumbers(int n) {
		
		if (n < 2) {
			throw new RuntimeException("n is expected to be >=2 but was " + n);
		}
		
		Map<Integer, Integer> nthPower = getNthPowers(0, 9, n);		
		int sum = 0, num, digitPowerSum;
		String sumDesc;
		for (int i=nthPower.get(2); i<=n*nthPower.get(9); ++i) {
			digitPowerSum = nthPower.get(i%10);
			sumDesc = i%10 + "^" + n;
			num = i/10;
			while (num > 0) {
				digitPowerSum += nthPower.get(num%10);
				sumDesc = num%10 + "^" + n + " + " + sumDesc;
				num /= 10;				
			}
			if (digitPowerSum == i) {
				System.out.println(i + " = " + sumDesc);
				sum += i;
			}
		}
		
		System.out.println("Sum of all numbers which are sum of " + n + "th power of its digitSet = " + sum);
		return sum;
	}
	
	public static int getNthPower(int i, int n) {
		int power = 1;
		for (int j=n; j>=1; --j) {
			power *= i;
		}
		return power;
	}
	
	public static Map<Integer, Integer> getNthPowers(int iFrom, int iTo, int n) {
		Map<Integer, Integer> nthPowers = new HashMap<Integer, Integer>();
		int power;
		for (int i=iFrom; i<=iTo; ++i) {
			power = getNthPower(i, n);
			nthPowers.put(i, power);
			System.out.println(i + "^" + n + " = " + power);
		}
		return nthPowers;
	}
	
	/* http://projecteuler.net/problem=31 */
	public static int getPermutationsForNPences(int n) {
		
		TreeSet<Integer> coinSet = new TreeSet<Integer>();
		coinSet.add(1);
		coinSet.add(2);
		coinSet.add(5);
		coinSet.add(10);
		coinSet.add(20);
		coinSet.add(50);
		coinSet.add(100);
		coinSet.add(200);
		int perms = getPermutationsForNPences(n, coinSet);
		System.out.println("Total permutations for " + n + " pences = " + perms);
		return perms;
	}
	
	public static int getPermutationsForNPences(int n, TreeSet<Integer> coinSet) {
		
		int perms = 0;
		
		TreeSet<Integer> newCoinSet = new TreeSet<Integer>(coinSet);
		int coin = newCoinSet.last();
		newCoinSet.remove(coin);
		int sum = 0;
		while (sum < n) {
			if (newCoinSet.size() > 0) {
				perms += getPermutationsForNPences(n-sum, newCoinSet);
			}
			sum += coin;
		}
		if (sum == n) {
			++perms;
		}
		
		return perms;
	}

	/* http://projecteuler.net/problem=32 */
	public static int getPandigitalProductsSum() {

		int sum = 0, i, j;		
		int product, productDigit;
		Set<Integer> pandigitalProductsSet = new HashSet<Integer>(); 
		Set<Integer> digitSet = new HashSet<Integer>();
		for (int i1=0; i1<=9; ++i1) {
			if (i1 != 0) {
				digitSet.add(i1);
			}
			for (int i2=1; i2<=9; ++i2) {
				if (digitSet.contains(i2)) {
					continue;
				}
				digitSet.add(i2);
				i = i1 * 10 + i2;
				for (int j1=0; j1<=9; ++j1) {
					if (digitSet.contains(j1)) {
						continue;
					}
					if (j1 != 0) {
						digitSet.add(j1);
					}
					for (int j2=1; j2<=9; ++j2) {
						if (digitSet.contains(j2)) {
							continue;
						}
						digitSet.add(j2);
						for (int j3=1; j3<=9; ++j3) {
							if (digitSet.contains(j3)) {
								continue;
							}
							digitSet.add(j3);
							for (int j4=1; j4<=9; ++j4) {
								if (digitSet.contains(j4)) {
									continue;
								}
								digitSet.add(j4);
								j = j1 * 1000 + j2 * 100 + j3 * 10 + j4;
								product = i * j;
								int temp = product;
								boolean pandigitalProduct = true;
								Set<Integer> tempDigitSet = new HashSet<Integer>(digitSet);
								while (temp > 0) {
									productDigit = temp % 10;
									if ((productDigit == 0) || tempDigitSet.contains(productDigit)) {
										pandigitalProduct = false;
										break;
									}
									tempDigitSet.add(productDigit);
									temp /= 10;
								}
								if (pandigitalProduct && (tempDigitSet.size() == 9) && !pandigitalProductsSet.contains(product)) {
									pandigitalProductsSet.add(product);
									sum += product;
									System.out.println("Pandigital product set: " + i + " * " + j + " = " + product);
								}								
								digitSet.remove(j4);
							}
							digitSet.remove(j3);
						}
						digitSet.remove(j2);
					}
					digitSet.remove(j1);
				}
				digitSet.remove(i2);
			}
			digitSet.remove(i1);
		}
		System.out.println("Sum of pandigital products = " + sum);
		return sum;
	}

	/* http://projecteuler.net/problem=33 */
	public static int getNDigitCancelingFractionsDenominator() {
		
		int i1, i2, j1, j2, nominator = 1, denominator = 1;
		String fraction = "";
		boolean matched;
		for (int i=11; i<=98; ++i) {
			i1 = i % 10;
			i2 = (i - i1) / 10;
			if (i1 == 0) {
				continue;
			}
			for (int j=i+1; j<=99; ++j) {
				j1 = j % 10;
				j2 = (j - j1) / 10;
				if (j1 == 0) {
					continue;
				}
				matched = true;						
				if ((i1 == j1) && (i * j2 == j * i2)) {
					fraction = i2 + "/" + j2;
					nominator *= i2;
					denominator *= j2;
				} else if ((i1 == j2) && (i * j1 == j * i2)) {
					fraction = i2 + "/" + j1;							
					nominator *= i2;
					denominator *= j1;
				} else if ((i2 == j1) && (i * j2 == j * i1)) {
					fraction = i1 + "/" + j2;							
					nominator *= i1;
					denominator *= j2;
				} else if ((i2 == j2) && (i * j1 == j * i1)) {
					fraction = i1 + "/" + j1;							
					nominator *= i1;
					denominator *= j1;
				} else {
					matched = false;
				}
				if (matched) {
					System.out.println(i + "/" + j + " = " + fraction);
				}
			}
		}
		for (int k=2; k<=7; ++k) {
			while ((nominator%k == 0) && (nominator%k == 0)) {
				nominator /= k;
				denominator /= k;				
			}
		}
		System.out.println("Product of fractions = " + nominator + "/" + denominator);
		return denominator;
	}
	
	/* http://projecteuler.net/problem=34 */
	public static int getSumOfDigitFactorialsSumNumber() {
		Map<Integer, Integer> factorials = getFactorials(0, 9);
		int num, digitFactorialSum, sum = 0;
		String sumDesc;
		for (int i=10; i<=factorials.get(9)*10; ++i) {
			digitFactorialSum = factorials.get(i%10);
			sumDesc = i%10 + "!";
			num = i/10;
			while (num > 0) {
				digitFactorialSum += factorials.get(num%10);
				sumDesc = num%10 + "! + " + sumDesc;
				num /= 10;				
			}
			if (digitFactorialSum == i) {
				System.out.println(i + " = " + sumDesc);
				sum += i;
			}			
		}
		System.out.println("sum = " + sum);
		return sum;
	}
	
	public static int getFactorial(int i) {
		int factorial = 1;
		for (int j=i; j>1; --j) {
			factorial *= i;
		}
		return factorial;
	}
	
	public static Map<Integer, Integer> getFactorials(int iFrom, int iTo) {
		Map<Integer, Integer> factorials = new HashMap<Integer, Integer>();
		int factorial;
		for (int i=iFrom; i<=iTo; ++i) {
			if (factorials.containsKey(i-1)) {
				factorial = factorials.get(i-1) * i;				
			} else {
				factorial = getFactorial(i);
			}
			factorials.put(i, factorial);
			System.out.println(i + "! = " + factorial);
		}
		return factorials;
	}
		
	/* http://projecteuler.net/problem=38 */
	public static int getMaxPandigitalMultiples() {
		
		int product, iMax = 0, jMax = 0, maxPandigitalMultiple = 0;
		String multiples;
		Set<Integer> digitSet;
		boolean pandigital;
		for (int i=1; i<=9999; ++i) {
			digitSet = new HashSet<Integer>();
			digitSet.add(0);
			multiples = "";
			pandigital = true;
			int j;
			for (j=1; ; ++j) {
				product = i * j;
				multiples += product;
				while (product > 0) {
					if (digitSet.contains(product%10)) {
						pandigital = false;
						break;
					}
					digitSet.add(product%10);
					product /= 10;
				}
				if (!pandigital || (digitSet.size() == 10)) {
					break;
				}
			}
			if (pandigital) {
				System.out.println("Pandigital multiple = " + multiples + " formed by:");
				for (int k=1; k<=j; ++k) {
					System.out.println(i + " * " + k + " = " + i * k);
				}
				product = Integer.parseInt(multiples);
				if (maxPandigitalMultiple < product) {
					maxPandigitalMultiple = product;
					iMax = i;
					jMax = j; 
				}
			}
		}
		
		System.out.println("Max pandigital multiple = " + maxPandigitalMultiple + " formed by:");
		for (int j=1; j<=jMax; ++j) {
			System.out.println(iMax + " * " + j + " = " + iMax * j);
		}
		return maxPandigitalMultiple;
	}
	
	/* http://projecteuler.net/problem=39 */
	public static int getMaxRightAngleTriangleSolutions(int maxPerimeter) {
		return getMaxRightAngleTriangleSolutions(12, maxPerimeter);
	}
	
	public static int getMaxRightAngleTriangleSolutions(int minPerimeter, int maxPerimeter) {
		
		int solCount, solCountMax = 0, pMax = 0;
		for (int p=minPerimeter; p<=maxPerimeter; ++p) {
			solCount = 0;
			for (int a=3; a<p/2; ++a) {
				for (int b=a+1; b<p/2; ++b) {
					int c = p - a - b;
					if ((c >= a + b) || (c <= a) || (c <= b)) {
						continue;
					}
					if (a*a+b*b == c*c) {
						++solCount;
						System.out.println(solCount + "th solution for p=" + p + " : " + a + "^2 + " + b + "^2 = " + c + "^2");
					}
				}				
			}
			if (solCountMax < solCount) {
				solCountMax = solCount;
				pMax = p;
			}
		}
		System.out.println("A max of " + solCountMax + " solutions found for p = " + pMax);
		return solCountMax;
	}
	
	/* http://projecteuler.net/problem=40 */
	public static int getChampernowneExpression() {
		
		int dProduct = 1;
		String productStr = "";
		for (int i=0; i<=6; ++i) {
			dProduct *= getNthDigitOfChampernowneConstant(getNthPower(10, i));
			productStr += "d(" + getNthPower(10, i) + ")";
			if (i < 6) {
				productStr += " x ";
			}
		}
		System.out.println(productStr + " = " + dProduct);
		return dProduct;
	}
	
	private static int getNthDigitOfChampernowneConstant(int i) {
		
		if (i <= 0) {
			throw new RuntimeException("i should be >0 but was " + i);
		}
		
		int index = 0, pos = 0, n = 1, len = 0, digit = i;
		while (index < i) {
			pos = i - index;
			n *= 10;
			++len;
			index += (n - n / 10) * len;
		}
		n = n / 10 - 1 + pos / len;
		pos = (len - pos % len) % len;
		digit = n % 10;
		n = (n + 1) / 10;
		while (pos > 0) {
			digit = n % 10;
			n /= 10;
			--pos;
		}
		
		System.out.println("d(" + i + ") = " + digit);
		return digit;
	}
	
	public static boolean isPandigital(long n) {
		return isPandigital(n, 1, 0);
	}
	
	public static boolean isPandigital(long n, int fromDigit, int toDigit) {
		
		Set<Integer> digitSet = new HashSet<Integer>();
		int length = toDigit - fromDigit + 1;
		if (length < 2) {
			length = String.valueOf(n).length();			
		}
		while (n > 0) {
			int digit = (int)(n % 10);
			if ((digit < fromDigit) || (digit > length) || digitSet.contains(digit)) {
				return false;
			}
			digitSet.add(digit);
			n /= 10;
		}
		if (digitSet.size() != length) {
			return false;
		}
		return true;
	}
	
	/* http://projecteuler.net/problem=40 */
	public static int getTriangleWordCount(String[] words) {
		
		TreeMap<Integer, Integer> triangleNumMap = new TreeMap<Integer, Integer>();
		int num, count = 0;
		for (int i=0; i<words.length; ++i) {
			num = convertWordToNumber(words[i]);
			getTriangleNumbers(num, triangleNumMap);
			if (triangleNumMap.containsKey(num)) {
				++count;
				System.out.println("Coded word " + words[i] + " = " + num + " is a triangle number");
			}
		}
		System.out.println("Coded triangle number count = " + count);
		return count;
	}
	
	private static int convertWordToNumber(String word) {
		
		int num = 0;
		word = word.toUpperCase();
		for (int i=0; i<word.length(); ++i) {
			num += word.charAt(i) - 'A' + 1;
		}
		return num;
	}
	
	@SuppressWarnings("unused")
	private static Map<Integer, Integer> getTriangleNumbers(int max) {
		
		TreeMap<Integer, Integer> triangleNumMap = new TreeMap<Integer, Integer>();
		return getTriangleNumbers(max, triangleNumMap);
	}
	
	private static Map<Integer, Integer> getTriangleNumbers(int max, TreeMap<Integer, Integer> triangleNumMap) {
		
		int i = 0, sum = 0;
		if ((triangleNumMap != null) && (triangleNumMap.size() > 0)) {
			sum = triangleNumMap.lastEntry().getKey();
			i = triangleNumMap.lastEntry().getValue();
		}
		for (++i; i<=max; ++i) {
			sum += i;
			triangleNumMap.put(sum, i);
		}
		return triangleNumMap;
	}

	private static long getTriangleNumber(long n) {
		return n * (n + 1) / 2;
	}
	
	private static long getTriangleNumberIndex(long n) {
		long numerator = getPerfectSquareRoot(8 * n + 1) - 1;
		if (numerator % 2 == 0) {
			return numerator / 2;
		}
		return 0;
	}
	
	@SuppressWarnings("unused")
	private static boolean isTriangleNumber(int n) {
		if (getTriangleNumberIndex(n) > 0) {
			return true;
		}
		return false;
	}
	
	/* http://projecteuler.net/problem=43 */
	public static long getSubstringDivisiblePandigital0ToN() {
		
		TreeSet<Long> primes = Factorization.nPrimes(7);
		ArrayList<Long> primesArray = new ArrayList<Long>(primes);
		
		long i, num, subNum, sum = 0;
		for (i=9876543201l; i>=1023456789l; i=i-2) {
			if (Gen.isPandigital(i, 0, 9)) {
				num = i;
				boolean substringPandigital = true;
				for (int j=6; j>=0; --j) {
					subNum = num % 1000;
					if (subNum % primesArray.get(j) != 0) {
						substringPandigital = false;
						break;
					}
					num /= 10;
				}
				if (substringPandigital) {
					System.out.println("Substring divisible pandigital (0 to 9) = " + i);
					sum += i;
				}
			}
		}
		
		System.out.println("Sum of substring divisible pandigital (0 to 9) = " + sum);
		return sum;
	}
	
	/* http://projecteuler.net/problem=44 */
	public static long getPentagonNumberPairMinDiff() {

		long diff, minDiff = 0, min_i_j = 0, jMin, sum, iSum, iDiff, p1, p2;
		for (long i=2; ; ++i) {
			diff = (3 * (2 * i - 1) - 1) / 2; //for j=i-1			
			if ((minDiff > 0) && (diff > minDiff)) {
				break;
			}
			p1 = getPentagonNumber(i);
			jMin = 1;
			if (min_i_j != 0) {
				min_i_j -= 3; //d(diff)/d(i) = 3
				jMin = i - min_i_j;
			}
			for (long j=i-1; j>=jMin; --j) {
				p2 = getPentagonNumber(j);
				diff = p1 - p2;
				if ((minDiff != 0) && (minDiff < diff)) {
					continue;
				}
				iDiff = getPentagonNumberIndex(diff);
				if (iDiff == 0) {
					continue;
				}
				sum = p1 + p2;
				iSum = getPentagonNumberIndex(sum);
				if (iSum > 0) {
					min_i_j = i - j;
					System.out.println("P(" + (i+j) + ") - P(" + i + ") = " + p1 + " - " + p2 + " = " + diff + " = P(" + iDiff + ")");
					System.out.println("P(" + (i+j) + ") + P(" + i + ") = " + p1 + " + " + p2 + " = " + sum + " = P(" + iSum + ")");
				}
			}			
		}
		System.out.println("Min pentagon number pair diff = " + minDiff);
		return minDiff;
	}
	
	private static long getPentagonNumber(long n) {
		return n * (3 * n - 1) / 2;
	}
	
	private static long getPentagonNumberIndex(long n) {
		long numerator = getPerfectSquareRoot(24 * n + 1) + 1;
		if (numerator % 6 == 0) {
			return numerator / 6;
		}
		return 0;
	}
	
	@SuppressWarnings("unused")
	private static boolean isPentagonNumber(int n) {
		if (getPentagonNumberIndex(n) > 0) {
			return true;
		}
		return false;
	}
	
	public static long getPerfectSquareRoot(long n) {
		
		for (long i=1; i<=n/i; ++i) {
			if ((i == n/i) && (n%i == 0)) {
				return i;
			}
		}
		return 0;
	}	
	
	@SuppressWarnings("unused")
	private static long getHexagonNumber(long n) {
		return n * (2 * n - 1);
	}
	
	private static long getHexagonNumberIndex(long n) {
		long numerator = getPerfectSquareRoot(8 * n + 1) + 1;
		if (numerator % 4 == 0) {
			return numerator / 4;
		}
		return 0;
	}
	
	@SuppressWarnings("unused")
	private static boolean isHexagonNumber(int n) {
		if (getHexagonNumberIndex(n) > 0) {
			return true;
		}
		return false;
	}
	
	/* http://projecteuler.net/problem=45 */
	public static long getTriangularPentagonalHexagonalNumber(int min) {

		long n, iPentagon, iHexagon;
		for (long i=min; ; ++i) {
			n = getTriangleNumber(i);
			iPentagon = getPentagonNumberIndex(n);
			if (iPentagon > 0) {
				iHexagon = getHexagonNumberIndex(n);
				if (iHexagon > 0) {
					System.out.println("T(" + i + ") = " + "P(" + iPentagon + ") = " + "H(" + iHexagon + ") = " + n);
					return i;
				}
			}
		}
	}

	/* http://projecteuler.net/problem=52 */
	public static long getNumberWithPermutedMultiples(int n) {
		
		for (long i=125874; ; ++i) {
			Set<Integer> digits = new HashSet<Integer>();
			long num = i;
			int digit;
			boolean isPermutedMultiiple = true;
			while (num>0) {
				digit = (int)(num % 10);
				if (digits.contains(digit)) {
					isPermutedMultiiple = false;
					break;
				}
				digits.add(digit);
				num /= 10;
			}
			for (int j=2; isPermutedMultiiple && (j<=n); ++j) {
				long multiple = i * j;
				int digitCount = 0;
				while (multiple>0) {
					digit = (int)(multiple % 10);
					++digitCount;
					if (!digits.contains(digit) || (digitCount > digits.size())) {
						isPermutedMultiiple = false;
						break;
					}
					multiple /= 10;
				}
			}
			if (isPermutedMultiiple) {
				System.out.println(i + " has " + n + " permuted multiples:");
				for (int j=2; j<=n; ++j) {
					System.out.println(i + " * " + j + " = " + (i*j));
				}
				return i;
			}
		}		
	}
}
