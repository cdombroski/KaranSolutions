package org.icanttype.karan;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by chris.dombroski on 8/12/2014.
 */
public class Pi {

	int negator = 1;
	BigInteger term1 = BigInteger.ONE; //(6k)!
	BigInteger term1LastTerm = BigInteger.ONE;
	BigInteger term2 = BigInteger.ZERO; //545140134k
	BigInteger term3 = BigInteger.ONE; //(3k)!
	BigInteger term3LastTerm = BigInteger.ONE;
	BigInteger term4 = BigInteger.ONE; //(k!)^3
	int term5 = 0; //3k
	int k = 0;
	MathContext mc;
	int digits;
	boolean calculated = false;
	BigDecimal result = BigDecimal.ZERO;

	public Pi(int digits) {
		mc = new MathContext(digits, RoundingMode.HALF_EVEN);
		this.digits = digits;
	}

	public static void main(String[] args) {
		System.out.println(new Pi(Integer.parseInt(args[0])).getPi());
	}

	public BigDecimal getPi() {
		if (!calculated) {
			result = result.add(new BigDecimal(BigInteger.valueOf(12).multiply(BigInteger.valueOf(negator)).multiply(term1).multiply(BigInteger.valueOf(13591409).add(term2)))
					.divide(new BigDecimal(term3.multiply(term4)).multiply(BigDecimal.valueOf(640320).pow(term5 + 1)).multiply(BigDecimal.valueOf(Math.pow(640320, 0.5))), mc));
			for(int x = 0; x < digits / 14; x++) {
				increment();
			}
			result = result.pow(-1, mc);
			calculated = true;
		}
		return result;
	}

	private void increment() {
		negator *= -1;
		k++;

		BigInteger[] term1Results = incrementalFactorial(term1, term1LastTerm, 6);
		term1 = term1Results[0];
		term1LastTerm = term1Results[1];

		term2 = term2.add(BigInteger.valueOf(545140134));

		BigInteger[] term3Results = incrementalFactorial(term3, term3LastTerm, 3);
		term3 = term3Results[0];
		term3LastTerm = term3Results[1];

		term4 = term4.multiply(BigInteger.valueOf(k).pow(3));

		term5 += 3;

		result = result.add(new BigDecimal(BigInteger.valueOf(12).multiply(BigInteger.valueOf(negator)).multiply(term1).multiply(BigInteger.valueOf(13591409).add(term2)))
				.divide(new BigDecimal(term3.multiply(term4)).multiply(BigDecimal.valueOf(640320).pow(term5 + 1)).multiply(BigDecimal.valueOf(Math.pow(640320, 0.5))), mc));

	}

	private BigInteger[] incrementalFactorial(BigInteger term, BigInteger termLastTerm, int step) {
		BigInteger x;
		for(x = termLastTerm; x.compareTo(termLastTerm.add(BigInteger.valueOf(step - 1))) <= 0; x = x.add(BigInteger.ONE)) {
			term = term.multiply(x);
		}
		return new BigInteger[] {term, x};
	}

}
