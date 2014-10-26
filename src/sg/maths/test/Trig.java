package sg.maths.test;
public class Trig {
	public static final double TOLERANCE = 0.0001d;
	public static final double PIE = 3.14159d;

	public static double sin(double x_) {

		if (Double.isNaN(x_)) {
			return Double.NaN;
		}

		int sign = 1;

		// sin(-x) = -sin(x)
		if (x_ < 0.0d) {
			sign = -1;
			x_ = Math.abs(x_);
		}

		// sin(2*N*PIE+x) = sin(x)
		x_ = x_ % (2 * PIE); // => 0 <= x_ < 2*PIE

		// sin(PIE+x) = -sin(x)
		if (x_ >= PIE) {
			sign *= -1;
			x_ = x_ - PIE; // => 0 <= x_ < PIE
		}

		// sin(PIE-x) = sin(x)
		if (x_ > PIE / 2) {
			x_ = PIE - x_; // => 0 <= x_ <= PIE/2
		}

		int n = 1;
		double nthTerm = x_;
		double sinx = 0.0d;
		while (Math.abs(nthTerm) > TOLERANCE) {
			sinx += nthTerm;
			n++;
			nthTerm *= (-1) * (x_ / (2 * n - 2)) * (x_ / (2 * n - 1));
		}

		return sign * sinx;
	}
}
