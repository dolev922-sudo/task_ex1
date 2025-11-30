import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * * Introduction to Computer Science 2026, Ariel University,
 * * Ex1: arrays, static functions and JUnit
 * <p>
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
    static final double[] P1 = {2, 0, 3, -1, 0}, P2 = {0.1, 0, 1, 0.1, 3};
    static double[] po1 = {2, 2}, po2 = {-3, 0.61, 0.2};
    ;
    static double[] po3 = {2, 1, -0.7, -0.02, 0.02};
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
        double[] xx = {0, 1, 2, 3, 4.1, -15.2222};
        double[] p12 = Ex1.mul(po1, po2);
        for (int i = 0; i < xx.length; i = i + 1) {
            double x = xx[i];
            double f1x = Ex1.f(po1, x);
            double f2x = Ex1.f(po2, x);
            double f12x = Ex1.f(p12, x);
            assertEquals(f12x, f1x * f2x, Ex1.EPS);
        }
    }

    @Test
    /**
     * Tests a simple derivative examples - till ZERO.
     */
    void testDerivativeArrayDoubleArray() {
        double[] p = {1, 2, 3}; // 3X^2+2x+1
        double[] pt = {2, 6}; // 6x+2
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
        double[] p = {-1.1, 2.3, 3.1}; // 3.1X^2+ 2.3x -1.1
        String sp2 = "3.1x^2 +2.3x -1.1";
        String sp = Ex1.poly(p);
        double[] p1 = Ex1.getPolynomFromString(sp);
        double[] p2 = Ex1.getPolynomFromString(sp2);
        boolean isSame1 = Ex1.equals(p1, p);
        boolean isSame2 = Ex1.equals(p2, p);
        if (!isSame1) {
            fail();
        }
        if (!isSame2) {
            fail();
        }
        assertEquals(sp, Ex1.poly(p1));
    }

    @Test
    /**
     * Tests the equality of pairs of arrays.
     */
    public void testEquals() {
        double[][] d1 = {{0}, {1}, {1, 2, 0, 0}};
        double[][] d2 = {Ex1.ZERO, {1 + Ex1.EPS / 2}, {1, 2}};
        double[][] xx = {{-2 * Ex1.EPS}, {1 + Ex1.EPS * 1.2}, {1, 2, Ex1.EPS / 2}};
        for (int i = 0; i < d1.length; i = i + 1) {
            assertTrue(Ex1.equals(d1[i], d2[i]));
        }
        for (int i = 0; i < d1.length; i = i + 1) {
            assertFalse(Ex1.equals(d1[i], xx[i]));
        }
    }

    @Test
    /**
     * Tests is the sameValue function is symmetric.
     */
    public void testSameValue2() {
        double x1 = -4, x2 = 0;
        double rs1 = Ex1.sameValue(po1, po2, x1, x2, Ex1.EPS);
        double rs2 = Ex1.sameValue(po2, po1, x1, x2, Ex1.EPS);
        assertEquals(rs1, rs2, Ex1.EPS);
    }

    @Test
    /**
     * Test the area function - it should be symmetric.
     */
    public void testArea() {
        double x1 = -4, x2 = 0;
        double a1 = Ex1.area(po1, po2, x1, x2, 100);
        double a2 = Ex1.area(po2, po1, x1, x2, 100);
        assertEquals(a1, a2, Ex1.EPS);
    }

    @Test
    /**
     * Test the area f1(x)=0, f2(x)=x;
     */
    public void testArea2() {
        double[] po_a = Ex1.ZERO;
        double[] po_b = {0, 1};
        double x1 = -1;
        double x2 = 2;
        double a1 = Ex1.area(po_a, po_b, x1, x2, 1);
        double a2 = Ex1.area(po_a, po_b, x1, x2, 2);
        double a3 = Ex1.area(po_a, po_b, x1, x2, 3);
        double a100 = Ex1.area(po_a, po_b, x1, x2, 100);
        double area = 2.5;
        assertEquals(a1, area, Ex1.EPS);
        assertEquals(a2, area, Ex1.EPS);
        assertEquals(a3, area, Ex1.EPS);
        assertEquals(a100, area, Ex1.EPS);
    }

    @Test
    /**
     * Test the area function.
     */
    public void testArea3() {
        double[] po_a = {2, 1, -0.7, -0.02, 0.02};
        double[] po_b = {6, 0.1, -0.2};
        double x1 = Ex1.sameValue(po_a, po_b, -10, -5, Ex1.EPS);
        double a1 = Ex1.area(po_a, po_b, x1, 6, 8);
        double area = 58.5658;
        assertEquals(a1, area, Ex1.EPS);
    }

    /**
     * I wrote all the test with "my_test_" soo there won't be same name with other test that already given to us.
     * test for derivative
     * checks for normal case
     * checks for 0 in largest ^
     *checks for negative num in the polynome
     */
    @Test
    public void my_test_derivative() {
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
        double[] p4 = {0, -4, 5};//5x^-4x
        double[] test_p4 = Ex1.derivative(p4);
        double[] expected_p4 = {-4, 10};//10x-4
        assertArrayEquals(expected_p4, test_p4, Ex1.EPS);
    }

    @Test
    public void my_test_integral() {
        double[] p1 = {3, 4, 6};
        double[] test_p1 = Ex1.integral(p1);
        double[] expected_p1 = {0, 3, 2, 2};
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
    public void my_test_length() {
        double[] p1 = {3, 4, 6};
        double x1 = 0.0;
        double x2 = 7.0;
        int numberOfSegments = 3;
        double expected_len = 322.10588;
        double test_len = Ex1.length(p1, x1, x2, numberOfSegments);
        assertEquals(expected_len, test_len, Ex1.EPS);
        double[] p2 = {0};
        double x3 = -1;
        double x4 = 0.0;
        int numberOfSegments2 = 12;
        double expected_len2 = 1;
        double test_len2 = Ex1.length(p2, x3, x4, numberOfSegments2);
        assertEquals(expected_len2, test_len2, Ex1.EPS);
        double[] p3 = {0, 0, -2};
        double x5 = 13;
        double x6 = 15;
        int numberOfSegments3 = 10;
        double expected_len3 = 112.018;
        double test_len3 = Ex1.length(p3, x5, x6, numberOfSegments3);
        assertEquals(expected_len3, test_len3, Ex1.EPS);
        double[] p4 = {1, 2, 3};
        double x7 = 12;
        double x8 = 15;
        int numberOfSegments4 = -1;
        double expected_len4 = 0;
        double test_len4 = Ex1.length(p4, x7, x8, numberOfSegments4);
        assertEquals(expected_len4, test_len4, Ex1.EPS);
        double[] p5 = {1, 2, 1};
        double x9 = 10;
        double x10 = 7;
        int numberOfSegments5 = 3;
        double expected_len5 = 57.079;
        double test_len5 = Ex1.length(p5, x9, x10, numberOfSegments5);

    }

    /**
     * test String from polynome
     * checks for normal case
     * checks if the length !=
     * checks for negative numbers
     */
    @Test
    public void my_test_poly() {
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
        double[] p4 = {-1, 0, 7, 24};// 24x^3+7^2-1
        String test_poly4 = Ex1.poly(p4);
        String expected_p4 = "24.0x^3+7.0x^2-1.0";
        assertEquals(expected_p4, test_poly4);
    }

    /**
     * test add
     * checks normal case
     * checks + and - polynomes
     * checks when p1 is null and the other is 0
     * checks for both null
     */
    @Test
    public void my_test_add() {
        double[] p1 = {3, 4, 6};
        double[] p2 = {1, 2.5, 4};
        double[] expected_poly = {4, 6.5, 10};
        double[] test_poly = Ex1.add(p1, p2);
        assertArrayEquals(expected_poly, test_poly, Ex1.EPS);
        double[] p3 = {3, 4, -3};
        double[] p4 = {-2, 0, 6};
        double[] expected_poly2 = {1, 4, 3};
        double[] test_poly2 = Ex1.add(p3, p4);
        assertArrayEquals(expected_poly2, test_poly2, Ex1.EPS);
        double[] p5 = {};
        double[] p6 = {0};
        double[] expected_poly3 = {0};
        double[] test_poly3 = Ex1.add(p5, p6);
        assertArrayEquals(expected_poly3, test_poly3, Ex1.EPS);
        double[] p7 = null;
        double[] p8 = null;
        double[] test_poly4 = Ex1.add(p7, p8);
        assertNull(test_poly4);
        double[] p9 = {3, 0, 4, 0, 0};
        double[] p10 = {2};
        double[] expected_poly5 = {5, 0, 4, 0, 0};
        double[] test_poly5 = Ex1.add(p9, p10);
        assertArrayEquals(expected_poly5, test_poly5, Ex1.EPS);
    }
    /**
     * checks normal case
     * checks what if p1 is 0
     * checks what if the polynome is negative
     *checks what if one of the polynomes=null
     */
    @Test
    public void my_test_mul() {
        double[] p1 = {3, 4, 6};
        double[] p2 = {1, 2.5};
        double[] expected_poly = {3,11.5,16,15};
        double[] test_poly = Ex1.mul(p1, p2);
        assertArrayEquals(expected_poly, test_poly, Ex1.EPS);
        double[] p3 = {0};
        double[] p4 = {5, 12,10};
        double[] expected_poly2 = {0,0,0};
        double[] test_poly2 = Ex1.mul(p3, p4);
        assertArrayEquals(expected_poly2, test_poly2, Ex1.EPS);
        double[] p5 = {3, 0, -3};
        double[] p6 = {-2, 7, -6};
        double[] expected_poly3 = {-6,21,-12,-21,18};
        double[] test_poly3 = Ex1.mul(p5, p6);
        assertArrayEquals(expected_poly3, test_poly3, Ex1.EPS);
        double[] p7 = null;
        double[] p8 = {2};
        double[] test_poly4 = Ex1.mul(p7, p8);
        assertNull(test_poly4);
    }

    /**
     * test equals
     * checks if normal p1 = p2
     * checks if they are negative
     * checks for false value
     * checks for null
     */
    @Test
    public void my_test_equals() {
        double[] p1 = {3, 4, 6};
        double[] p2 = {3, 4.00005,6};
        boolean expected_poly = true;
        boolean test_poly = Ex1.equals(p1, p2);
        assertEquals(expected_poly, test_poly);
        double[] p3 = {-3};
        double[] p4 = {-3+Ex1.EPS/2};
        boolean test_poly2 = Ex1.equals(p3, p4);
        boolean expected_poly2 = true;
        assertEquals(expected_poly2, test_poly2);
        double[] p5 = {3};
        double[] p6 = {3+Ex1.EPS*2};
        boolean test_poly3 = Ex1.equals(p5, p6);
        boolean expected_poly3 = false;
        assertEquals(expected_poly3, test_poly3);
        double[] p7 = null;
        double[] p8 = null;
        boolean test_poly4 = Ex1.equals(p7, p8);
        boolean expected_poly4 = true;
        assertEquals(expected_poly4, test_poly4);
    }

    /**
     * test same value
     * checks normal case
     * checks for one of the polys=0
     */
    @Test
    public void my_test_same_value() {
        double x1 = 0;
        double x2 = 5;
        double[] p1 = {0,1};
        double[] p2 = {4,-1};
        double expected_value = 2;
        double test_same_value = Ex1.sameValue(p1,p2,x1,x2,Ex1.EPS);
        assertEquals(expected_value, test_same_value,Ex1.EPS);
        double x3 = 2;
        double x4 = 4;
        double[] p3 = {0,0,1};
        double[] p4 = {3,2,0};
        double expected_value2 = 3;
        double test_same_value2 = Ex1.sameValue(p3,p4,x3,x4,Ex1.EPS);
        assertEquals(expected_value2, test_same_value2,Ex1.EPS);
        double x5 = 9;
        double x6 = 10;
        double[] p5 = {0};
        double[] p6 = {-10,1,0};
        double expected_value3 = 10;
        double test_same_value3 = Ex1.sameValue(p5,p6,x5,x6,Ex1.EPS);
        assertEquals(expected_value3, test_same_value3,Ex1.EPS);
    }

    /**
     * test PolynomFromPoints
     * chechs for normal case
     * checks for negative in one of the parameters
     * checks for strait line
     * checks for dividing in 0
     */
    @Test
    public void my_test_PolynomFromPoints() {
        double [] xx1 = {0,1,2};
        double [] yy1 = {0,1,4};
        double [] expected_poly1 = {0,0,1};
        double [] test_PolynomFromPoints = Ex1.PolynomFromPoints(xx1,yy1);
        assertArrayEquals(expected_poly1, test_PolynomFromPoints, Ex1.EPS);
        double [] xx2 = {-1,0,1};
        double [] yy2 = {1,0,3};
        double [] expected_poly2 = {0,1,2};
        double [] test_PolynomFromPoints2 = Ex1.PolynomFromPoints(xx2,yy2);
        assertArrayEquals(expected_poly2, test_PolynomFromPoints2, Ex1.EPS);
        double [] xx3 = {0,1,2};
        double [] yy3 = {1,3,5};
        double [] expected_poly3 = {1,2,0};
        double [] test_PolynomFromPoints3 = Ex1.PolynomFromPoints(xx3,yy3);
        assertArrayEquals(expected_poly3, test_PolynomFromPoints3, Ex1.EPS);
        double [] xx4 = {1,1,3};
        double [] yy4 = {2,4,6};
        double [] test_PolynomFromPoints4 = Ex1.PolynomFromPoints(xx4,yy4);
        assertNull(test_PolynomFromPoints4);
    }

    /**
     * test area
     * checks for normal case
     * checks for numberOfTrapezoid <=0
     * checks what if p1=null||p2=null
     */
    @Test
    public void my_test_area() {
        double[] p1 ={2,5,1};
        double[] p2 ={0,2,4};
        double x1=2;
        double x2=3;
        int numberOfTrapezoid =0;
        double expected_area = 0;
        double test_area = Ex1.area(p1,p2,x1,x2,numberOfTrapezoid);
        assertEquals(expected_area, test_area,Ex1.EPS);
        double[] p3 ={0,1,0};
        double[] p4 ={0,0,1};
        double x3=0;
        double x4=2;
        int numberOfTrapezoid2 =100;
        double expected_area2 = 1;
        double test_area2 = Ex1.area(p3,p4,x3,x4,numberOfTrapezoid2);
        assertEquals(expected_area2, test_area2,Ex1.EPS);
        double[] p5 =null;
        double[] p6 ={0,2,4};
        double x5=2;
        double x6=3;
        int numberOfTrapezoid3 =10;
        double expected_area3 = 0;
        double test_area3 = Ex1.area(p5,p6,x5,x6,numberOfTrapezoid3);
        assertEquals(expected_area3, test_area3,Ex1.EPS);


    }

    /**
     * test getPolynomFromString
     * checks for normal case
     * checks for string that = 0
     * checks for negative numbers inside the string
     */
    @Test
    public void my_test_getPolynomFromString() {
        String p1 = "4x^3+3x+2";
        double[] expected = {2.0,3.0,0,4.0};
        double [] test = Ex1.getPolynomFromString(p1);
        assertEquals(expected.length, test.length);
        assertArrayEquals(expected,test);
        String p2 = "0x^6+0x^2+0";
        double[] expected2 = {0,0,0,0,0,0,0};
        double[] test2 = Ex1.getPolynomFromString(p2);
        assertEquals(expected2.length, test2.length);
        assertArrayEquals(expected2,test2);
        String p3 = "-3x^2+4x-2x+5";
        double[] expected3 = {5,2,-3};
        double[] test3 = Ex1.getPolynomFromString(p3);
        assertEquals(expected3.length, test3.length);
        assertArrayEquals(expected3,test3);
    }


}

