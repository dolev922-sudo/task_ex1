/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 *
 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe

 */
public class Ex1 {
    /**
     * Epsilon value for numerical computation, it serves as a "close enough" threshold.
     */
    public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
    /**
     * The zero polynomial function is represented as an array with a single (0) entry.
     */
    public static final double[] ZERO = {0};

    /**
     * Computes the f(x) value of the polynomial function at x.
     *
     * @param poly - polynomial function
     * @param x
     * @return f(x) - the polynomial function value at x.
     */
    public static double f(double[] poly, double x) {
        double ans = 0;
        for (int i = 0; i < poly.length; i++) {
            double c = Math.pow(x, i);
            ans += c * poly[i];
        }
        return ans;
    }

    /**
     * Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
     * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps,
     * assuming p(x1)*p(x2) <= 0.
     * This function should be implemented recursively.
     *
     * @param p   - the polynomial function
     * @param x1  - minimal value of the range
     * @param x2  - maximal value of the range
     * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
     * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
     */
    public static double root_rec(double[] p, double x1, double x2, double eps) {
        double f1 = f(p, x1);
        double x12 = (x1 + x2) / 2;
        double f12 = f(p, x12);
        if (Math.abs(f12) < eps) {
            return x12;
        }
        if (f12 * f1 <= 0) {
            return root_rec(p, x1, x12, eps);
        } else {
            return root_rec(p, x12, x2, eps);
        }
    }

    /**
     * This function computes a polynomial representation from a set of 2D points on the polynom.
     * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
     * Note: this function only works for a set of points containing up to 3 points, else returns null.
     *
     * @param xx
     * @param yy
     * @return an array of doubles representing the coefficients of the polynom.
     * takes 3-2 point and get polynome from it
     */
    public static double[] PolynomFromPoints(double[] xx, double[] yy) {
        double[] ans = null;
        if (xx==null|| yy==null || xx.length != yy.length || xx.length<2) {
            return ans;
        }
        int lx = xx.length;
        if (lx == 2 && xx[0] == xx[1]) {
            return null;
        }
        if (lx == 3 && (xx[0] == xx[1] || xx[0] == xx[2] || xx[1] == xx[2])) {
            return null;
        }
        if (lx ==2) {
            double x1 = xx[0];
            double x2 = xx[1];
            double y1 = yy[0];
            double y2 = yy[1];
            double m = (y2 - y1) / (x2 - x1);
            double b = y1-m*x1;
            return new double[]{b,m};
        }
        if (lx==3) {
            double denom = (xx[0] - xx[1]) * (xx[0] - xx[2]) * (xx[1] - xx[2]);
            double A = (xx[2] * (yy[1] - yy[0]) + xx[1] * (yy[0] - yy[2]) + xx[0] * (yy[2] - yy[1])) / denom;
            double B = (xx[2] * xx[2] * (yy[0] - yy[1]) + xx[1] * xx[1] * (yy[2] - yy[0]) + xx[0] * xx[0] * (yy[1] - yy[2])) / denom;
            double C = (xx[1] * xx[2] * (xx[1] - xx[2]) * yy[0] + xx[2] * xx[0] * (xx[2] - xx[0]) * yy[1] + xx[0] * xx[1] * (xx[0] - xx[1]) * yy[2]) / denom;
            double xv = -B / (2 * A);
            double yv = C - B * B / (4 * A);
            return  new double[]{C,B,A};
        }


        return ans;
    }

    /**
     * Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
     * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
     *
     * @param p1 first polynomial function
     * @param p2 second polynomial function
     * @return true iff p1 represents the same polynomial function as p2.
     * if p1 || p2 null or both
     * checking if f(x1)=f2(x1)-+EPS
     */
    public static boolean equals(double[] p1, double[] p2) {
        boolean ans = true;
        if (p1 == null && p2 == null) {
            return true;
        }
        if (p1 == null || p2 == null) {
            return false;
        }
        int max_len = Math.max(p1.length, p2.length);

        for (int i = 0; i < max_len; i++) {
            double x = i;
            double y1 = f(p1, x);
            double y2 = f(p2, x);
            if (Math.abs(y1 - y2) > EPS) {
                ans = false;
            }
        }
        return ans;
    }

	/** 
	 * Computes a String representing the polynomial function.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
	 * @param poly the polynomial function represented as an array of doubles
	 * @return String representing the polynomial function:
     * cheecks if poly is 0
     * add for each [i] the +or- then the x then if needed ^
	 */
    public static String poly(double[] poly) {
        String ans = "";
        if (poly.length == 0 || poly == null) {
            ans = "0";
        }
        boolean is_first = true;
        for (int i = poly.length - 1; i >= 0; i--) {
            double val = poly[i];
            if (val == 0) {
                continue;
            }
            if (val> 0 && !is_first){
                ans += "+";
            }
            ans += val;
           if (i==1) {
               ans+="x";
           }
           else if (i>1) {
               ans+="x^"+i;
           }
           is_first = false;
        }
        if (ans.equals("")) {
            ans = "0";
        }
        return ans;
    }
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
	 * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
     * call for himself and always search the middle
     * if the mid isnt the same value search right or left and call same value again
     * continue untill find the same value
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		double ans = x1;
        double start = x1;
        double mid = (x2+x1)/2;
        double end = x2;
       if (p1 == null && p2 == null) {
           return 0;
       }
       if (p1 == null || p2 == null) {
           return -1;
       }
       if (Math.abs( f(p1, x1) - f(p2, x1))<(Ex1.EPS*Ex1.EPS)) {
           return x1;
       }
        if (Math.abs(f(p1, mid) - f(p2, mid))<(Ex1.EPS*Ex1.EPS)) {
            return mid;
        }
        if (Math.abs(x2-x1)<(Ex1.EPS*Ex1.EPS)) {
            return x1;
        }
       if (f(p1, x1) > f(p2, x1)) {//if f1(x1)>f2(x1)
           if (f(p1, mid) > f(p2, mid)) {
               return sameValue(p1, p2, mid, end, Ex1.EPS);
           }
           if (f(p1, mid) < f(p2, mid)) {
               return sameValue(p1, p2, start, mid, Ex1.EPS);
           }
       }
       else if (f(p2, x1) > f(p1, x1)) {
           if (f(p2, mid) > f(p1, mid)) {
               return sameValue(p1, p2, mid, end, Ex1.EPS);
           }
           if (f(p2, mid) < f(p1, mid)) {
               return sameValue(p1, p2, start, mid, Ex1.EPS);
           }
       }
        return ans;
	}
	/**
	 * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
	 * This function computes an approximation of the length of the function between f(x1) and f(x2) 
	 * using n inner sample points and computing the segment-path between them.
	 * assuming x1 < x2. 
	 * This function should be implemented iteratively (none recursive).
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfSegments - (A positive integer value (1,2,...).
	 * @return the length approximation of the function between f(x1) and f(x2).
     * get numberOfSegments and do Pythagoras on the 3 points that got from the numberOfSegments.
	 */
    public static double length(double[] p, double x1, double x2, int numberOfSegments) {
        double ans = 0;
        if(numberOfSegments<=0){
            return ans;
        }
        if (x1 == x2) {
            return ans;
        }
        double delta_dx = (x2-x1)/numberOfSegments;
        for (int i = 0; i < numberOfSegments; i++) {
            double currentX = x1 + i * delta_dx;
            double dy = (f(p, currentX + delta_dx) - f(p,currentX));
            double temp = Math.sqrt(Math.pow(delta_dx, 2) + Math.pow(dy, 2));
            ans += temp;
        }
        return ans;
    }
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
	 * This function computes an approximation of the area between the polynomial functions within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
	 * @return the approximated area between the two polynomial functions within the [x1,x2] range.
     * calculat trapezoid btween the range x1-x2
     * and if there is samevalue between p1 and p2 calculat the trapezoids between part1_area and part2_area
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
		double ans = 0;
        if (p1 == null || p2 == null || numberOfTrapezoid <= 0) {
            return ans;
        }
        double dx =Math.abs((x2-x1)/numberOfTrapezoid);
        double min_x =Math.min(x1,x2);
        for (int i = 0; i < numberOfTrapezoid; i++) {
            double x_start = min_x + i * dx;
            double x_end = min_x + (i+1) * dx;
            double intersect = sameValue(p1, p2, x_start , x_end, Ex1.EPS);
            boolean has_cut = (intersect >x_start+Ex1.EPS && intersect<x_end-Ex1.EPS);
            if (has_cut) {
                double hight_start =Math.abs(f(p1, x_start) - f(p2, x_start));
                double hight_mid = Math.abs(f(p1, intersect) - f(p2,intersect));
                double part1_area = ((hight_start+hight_mid)/2)*(intersect-x_start);
                double hight_end =  Math.abs(f(p1, x_end) - f(p2, x_end));
                double paert2_area = ((hight_mid+hight_end)/2)*(x_end-intersect);
                ans +=(paert2_area+part1_area);
            }
            else {
            double hight_start =Math.abs(f(p1, x_start) - f(p2, x_start));
            double hight_end =  Math.abs(f(p1, x_end) - f(p2, x_end));
            double trapezoid_area = ((hight_start + hight_end)/2)*dx;
            ans += trapezoid_area;
        }
        }

		return ans;
	}
	/**
	 * This function computes the array representation of a polynomial function from a String
	 * representation. Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynomial function.
	 * @return
     *
	 */
	public static double[] getPolynomFromString(String p) {
		double [] ans = ZERO;
        String s = p.replace(" ", "");
        s = s.replace("-", "+-");
        String[] parts = s.split("\\+");
        int maxPower = 0;
        for (String term : parts) {
            if(term.isEmpty())
                continue;
            int exponent = term.indexOf('^');
            if (exponent != -1) {
                int pow = Integer.parseInt(term.substring(exponent+1));
                if(pow > maxPower)
                    maxPower = pow;
            } else if (term.indexOf('x') != -1) {
                if(1 > maxPower)
                    maxPower = 1;
            }
        }

        ans = new double[maxPower + 1];
        for (int i = 0; i < parts.length; i++) {
            String term = parts[i];
            if (term.isEmpty())
                continue;
            int x_index = term.indexOf('x');
            int hat_index = term.indexOf('^');
            int exponent = 0;
            if (hat_index != -1) {
                exponent = Integer.parseInt(term.substring(hat_index + 1));
            }
            else if (x_index != -1) {
                exponent = 1;
            }
            double value = 0;
            if (x_index == -1) {
                value = Double.parseDouble(term);
            }
            else {
                String before = term.substring(0, x_index);
                if (before.isEmpty() || before.equals("+"))
                    value = 1.0;
                else if (before.equals("-"))
                    value = -1.0;
                else value = Double.parseDouble(before);
            }

            ans[exponent] += value;
        }
        return ans;
    }
	/**
	 * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
     * if (p1 || p2 != null && p1.len>1 || p2.len>1)
     * if(l1>l2){replace}
     * int max = Math.max(l1,l2)
     * int min = Math.min(l1,l2)
     * doulble[] ans = new double[max]
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] add(double[] p1, double[] p2) {
        if (p1 ==null && p2 ==null ) {
            return null;
        }
        if (p1.length > p2.length) {//replace
              double [] temp = p1;
              p1 = p2;
              p2 = temp;
          }
          int max_length = p2.length;
          int min_length = p1.length;
          double[] ans = new double[max_length];
          for (int i = 0; i < min_length; i++) {
              ans[i] = p1[i] + p2[i];
          }
          for(int i = min_length; i < max_length; i++) {
              ans[i] =p2[i];
          }
		return ans;
	}
	/**
	 * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
     * double ans = ZERO
     * for(int i = 0; i<p1.length; i+=1){
     *   double[] c = doulble[] mul(p2, p1[1],i)
     *   ans = Ex1.add(ans,c)
     * }
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] mul(double[] p1, double[] p2) {
		double [] ans = ZERO;//
      if(p1 == null || p2 == null){
          return null;
      }
        int len1 = p1.length;
        int len2 = p2.length;
        ans = new double[len1 + len2 - 1];
        for (int i = 0; i < len1; i++) {
            for (int j = 0 ; j < len2; j++) {
                double temp_num = p1[i] * p2[j];
                ans[i+j] += temp_num;
            }
        }
		return ans;
	}
	/**
	 * This function computes the derivative of the p0 polynomial function.
     * inpot poly
     * if(poly == null && poly.length>1)
     * int len = poly.length;
     * ands = new double[len-1]
     * for(int i=0,i<ans.length;i+=1
     * ans[1] = poly[i+1] * [i+1]
	 * @param po
	 * @return
	 */
    public static double[] derivative(double[] po) {
        double[] ans = ZERO;
        if (po.length != 0 && po.length > 1) {
            int len = po.length;
            ans = new double[len - 1];
            for (int i = 0; i < ans.length; i++) {
                ans[i] = po[i + 1] * (i + 1);
            }
        }
		return ans;
	}
}
