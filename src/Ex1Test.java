import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Ex1: arrays, static functions and JUnit
 *
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
	static final double[] P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3};
	static double[] po1 = {2,2}, po2 = {-3, 0.61, 0.2};;
	static double[] po3 = {2,1,-0.7, -0.02,0.02};
	static double[] po4 = {-3, 0.61, 0.2};
	
 	@Test
	/**
	 * Tests that f(x) == poly(x).
	 */
	void testF() {
		double fx0 = Ex1.f(po1, 0);
		double fx1 = Ex1.f(po1, 1);
		double fx2 = Ex1.f(po1, 2);
		assertEquals(fx0, 2, Ex1.EPS);
		assertEquals(fx1, 4, Ex1.EPS);
		assertEquals(fx2, 6, Ex1.EPS);
	}
	@Test
	/**
	 * Tests that p1(x) + p2(x) == (p1+p2)(x)
	 */
	void testF2() {
		double x = Math.PI;
		double[] po12 = Ex1.add(po1, po2);
		double f1x = Ex1.f(po1, x);
		double f2x = Ex1.f(po2, x);
		double f12x = Ex1.f(po12, x);
		assertEquals(f1x + f2x, f12x, Ex1.EPS);
	}
	@Test
	/**
	 * Tests that p1+p2+ (-1*p2) == p1
	 */
	void testAdd() {
		double[] p12 = Ex1.add(po1, po2);
		double[] minus1 = {-1};
		double[] pp2 = Ex1.mul(po2, minus1);
		double[] p1 = Ex1.add(p12, pp2);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1+p2 == p2+p1
	 */
	void testAdd2() {
		double[] p12 = Ex1.add(po1, po2);
		double[] p21 = Ex1.add(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1+0 == p1
	 */
	void testAdd3() {
		double[] p1 = Ex1.add(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1*0 == 0
	 */
	void testMul1() {
		double[] p1 = Ex1.mul(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, Ex1.ZERO));
	}
	@Test
	/**
	 * Tests that p1*p2 == p2*p1
	 */
	void testMul2() {
		double[] p12 = Ex1.mul(po1, po2);
		double[] p21 = Ex1.mul(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1(x) * p2(x) = (p1*p2)(x),
	 */
	void testMulDoubleArrayDoubleArray() {
		double[] xx = {0,1,2,3,4.1,-15.2222};
		double[] p12 = Ex1.mul(po1, po2);
		for(int i = 0;i<xx.length;i=i+1) {
			double x = xx[i];
			double f1x = Ex1.f(po1, x);
			double f2x = Ex1.f(po2, x);
			double f12x = Ex1.f(p12, x);
			assertEquals(f12x, f1x*f2x, Ex1.EPS);
		}
	}
	@Test
	/**
	 * Tests a simple derivative examples - till ZERO.
	 */
	void testDerivativeArrayDoubleArray() {
		double[] p = {1,2,3}; // 3X^2+2x+1
		double[] pt = {2,6}; // 6x+2
		double[] dp1 = Ex1.derivative(p); // 2x + 6
		double[] dp2 = Ex1.derivative(dp1); // 2
		double[] dp3 = Ex1.derivative(dp2); // 0
		double[] dp4 = Ex1.derivative(dp3); // 0
		assertTrue(Ex1.equals(dp1, pt));
		assertTrue(Ex1.equals(Ex1.ZERO, dp3));
		assertTrue(Ex1.equals(dp4, dp3));
	}
	@Test
	/** 
	 * Tests the parsing of a polynom in a String like form.
	 */
	public void testFromString() {
		double[] p = {-1.1,2.3,3.1}; // 3.1X^2+ 2.3x -1.1
		String sp2 = "3.1x^2 +2.3x -1.1";
		String sp = Ex1.poly(p);
		double[] p1 = Ex1.getPolynomFromString(sp);
		double[] p2 = Ex1.getPolynomFromString(sp2);
		boolean isSame1 = Ex1.equals(p1, p);
		boolean isSame2 = Ex1.equals(p2, p);
		if(!isSame1) {fail();}
		if(!isSame2) {fail();}
		assertEquals(sp, Ex1.poly(p1));
	}
	@Test
	/**
	 * Tests the equality of pairs of arrays.
	 */
	public void testEquals() {
		double[][] d1 = {{0}, {1}, {1,2,0,0}};
		double[][] d2 = {Ex1.ZERO, {1+ Ex1.EPS/2}, {1,2}};
		double[][] xx = {{-2* Ex1.EPS}, {1+ Ex1.EPS*1.2}, {1,2, Ex1.EPS/2}};
		for(int i=0;i<d1.length;i=i+1) {
		assertTrue(Ex1.equals(d1[i], d2[i]));
		}
		for(int i=0;i<d1.length;i=i+1) {
			assertFalse(Ex1.equals(d1[i], xx[i]));
		}
	}

	@Test
	/**
	 * Tests is the sameValue function is symmetric.
	 */
	public void testSameValue2() {
		double x1=-4, x2=0;
		double rs1 = Ex1.sameValue(po1,po2, x1, x2, Ex1.EPS);
		double rs2 = Ex1.sameValue(po2,po1, x1, x2, Ex1.EPS);
		assertEquals(rs1,rs2, Ex1.EPS);
	}
	@Test
	/**
	 * Test the area function - it should be symmetric.
	 */
	public void testArea() {
		double x1=-4, x2=0;
		double a1 = Ex1.area(po1, po2, x1, x2, 100);
		double a2 = Ex1.area(po2, po1, x1, x2, 100);
		assertEquals(a1,a2, Ex1.EPS);
}
	@Test
	/**
	 * Test the area f1(x)=0, f2(x)=x;
	 */
	public void testArea2() {
		double[] po_a = Ex1.ZERO;
		double[] po_b = {0,1};
		double x1 = -1;
		double x2 = 2;
		double a1 = Ex1.area(po_a,po_b, x1, x2, 1);
		double a2 = Ex1.area(po_a,po_b, x1, x2, 2);
		double a3 = Ex1.area(po_a,po_b, x1, x2, 3);
		double a100 = Ex1.area(po_a,po_b, x1, x2, 100);
		double area =2.5;
		assertEquals(a1,area, Ex1.EPS);
		assertEquals(a2,area, Ex1.EPS);
		assertEquals(a3,area, Ex1.EPS);
		assertEquals(a100,area, Ex1.EPS);
	}
	@Test
	/**
	 * Test the area function.
	 */
	public void testArea3() {
		double[] po_a = {2,1,-0.7, -0.02,0.02};
		double[] po_b = {6, 0.1, -0.2};
		double x1 = Ex1.sameValue(po_a,po_b, -10,-5, Ex1.EPS);
		double a1 = Ex1.area(po_a,po_b, x1, 6, 8);
		double area = 58.5658;
		assertEquals(a1,area, Ex1.EPS);
	}

    /**
     * my tests
     */
    @Test
    public void derivative() {
        double[] p1 = {3, 4, 5};//5x^2+4x+3
        double[] test_p1 = Ex1.derivative(p1);
        double[] expected_p1 = {4, 10,};//10x+4
        assertArrayEquals(expected_p1, test_p1, Ex1.EPS);
        double[] p2 = {13, 12, 0};//12x+13
        double[] test_p2 = Ex1.derivative(p2);
        double[] expected_p2 = {12, 0};//12
        assertArrayEquals(expected_p2, test_p2, Ex1.EPS);
        double[] p3 = {0};//0
        double[] test_p3 = Ex1.derivative(p3);
        double[] expected_p3 = {0};//0
        assertArrayEquals(expected_p3, test_p3, Ex1.EPS);
        double[] p4 = {0, 4, 5};//5x^2+4x
        double[] test_p4 = Ex1.derivative(p4);
        double[] expected_p4 = {4, 10};//10x+4
        assertArrayEquals(expected_p4, test_p4, Ex1.EPS);
    }
    @Test
    public void test_integral() {
        double[] p1 = {3, 4, 6};
        double[] test_p1 = Ex1.integral(p1);
        double[] expected_p1 = {0 ,3 ,2 ,2};
        assertArrayEquals(expected_p1, test_p1, Ex1.EPS);
        double[] p2 = {};
        double[] test_p2 = Ex1.integral(p2);
        double[] expected_p2 = {0};
        assertArrayEquals(expected_p2, test_p2, Ex1.EPS);
    }
    /**
     * test for length
     * checks normal case
     * checks what if the polynome = 0
     * checks what if the polynome is negative
     * checks what idf number of segments <=0
     * checks what if x2>x1
     */
    @Test
    public void test_length() {
        double[] p1 = {3, 4, 6};
        double x1 = 0.0;
        double x2 = 7.0;
        int numberOfSegments = 3;
        double expected_len = 322.10588;
        double test_len = Ex1.length(p1, x1, x2, numberOfSegments);
        assertEquals(expected_len,test_len , Ex1.EPS);
        double[] p2 = {0};
        double x3 = -1;
        double x4 = 0.0;
        int numberOfSegments2 = 12;
        double expected_len2 = 1;
        double test_len2 = Ex1.length(p2, x3, x4, numberOfSegments2);
        assertEquals(expected_len2,test_len2 , Ex1.EPS);
        double[] p3 = {0,0,-2};
        double x5 = 13;
        double x6 = 15;
        int numberOfSegments3 = 10;
        double expected_len3 =112.018 ;
        double test_len3 = Ex1.length(p3, x5, x6, numberOfSegments3);
        assertEquals(expected_len3,test_len3,Ex1.EPS);
        double[] p4 = {1,2,3};
        double x7 = 12;
        double x8 = 15;
        int numberOfSegments4 = -1;
        double expected_len4 = 0;
        double test_len4 = Ex1.length(p4, x7, x8, numberOfSegments4);
        assertEquals(expected_len4,test_len4 , Ex1.EPS);
        double[] p5 = {1,2,1};
        double x9 = 10;
        double x10 = 7;
        int numberOfSegments5 = 3;
        double expected_len5 =57.079;
        double test_len5 = Ex1.length(p5, x9, x10, numberOfSegments5);

    }
    @Test
    public void test_poly() {
        double[] p1 = {3, 4, 6};// 6x^2+4x+3
        String test_poly = Ex1.poly(p1);
        String expected_p1 = "6.0x^2+4.0x+3.0";
        assertEquals(expected_p1, test_poly);
        double[] p2 = {};//0
        String test_poly2 = Ex1.poly(p2);
        String expected_p2 = "0";
        assertEquals(expected_p2, test_poly2);
        double[] p3 = {2, 3.5, 0, 0};// 3.5x+2
        String test_poly3 = Ex1.poly(p3);
        String expected_p3 = "3.5x+2.0";
        assertEquals(expected_p3, test_poly3);
        double[] p4 = {-1,0,7,24};// 24x^3+7^2-1
        String test_poly4 = Ex1.poly(p4);
        String expected_p4 = "24.0x^3+7.0x^2-1.0";
        assertEquals(expected_p4,test_poly4);
    }
}

