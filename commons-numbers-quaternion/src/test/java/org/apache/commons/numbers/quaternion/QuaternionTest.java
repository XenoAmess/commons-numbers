/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.numbers.quaternion;

import java.util.Random;

import org.junit.Test;
import org.junit.Assert;

public class QuaternionTest {
    /** Epsilon for double comparison. */
    private static final double EPS = Math.ulp(1d);
    /** Epsilon for double comparison. */
    private static final double COMPARISON_EPS = 1e-14;

    @Test
    public final void testAccessors1() {
        final double q0 = 2;
        final double q1 = 5.4;
        final double q2 = 17;
        final double q3 = 0.0005;
        final Quaternion q = Quaternion.of(q0, q1, q2, q3);

        Assert.assertEquals(q0, q.getQ0(), 0);
        Assert.assertEquals(q1, q.getQ1(), 0);
        Assert.assertEquals(q2, q.getQ2(), 0);
        Assert.assertEquals(q3, q.getQ3(), 0);
    }

    @Test
    public final void testAccessors2() {
        final double q0 = 2;
        final double q1 = 5.4;
        final double q2 = 17;
        final double q3 = 0.0005;
        final Quaternion q = Quaternion.of(q0, q1, q2, q3);

        final double sP = q.getScalarPart();
        final double[] vP = q.getVectorPart();

        Assert.assertEquals(q0, sP, 0);
        Assert.assertEquals(q1, vP[0], 0);
        Assert.assertEquals(q2, vP[1], 0);
        Assert.assertEquals(q3, vP[2], 0);
    }

    @Test
    public final void testAccessors3() {
        final double q0 = 2;
        final double q1 = 5.4;
        final double q2 = 17;
        final double q3 = 0.0005;
        final Quaternion q = Quaternion.of(q0, new double[] { q1, q2, q3 });

        final double sP = q.getScalarPart();
        final double[] vP = q.getVectorPart();

        Assert.assertEquals(q0, sP, 0);
        Assert.assertEquals(q1, vP[0], 0);
        Assert.assertEquals(q2, vP[1], 0);
        Assert.assertEquals(q3, vP[2], 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testWrongDimension() {
        Quaternion.of(new double[] { 1, 2 });
    }

    @Test
    public final void testConjugate() {
        final double q0 = 2;
        final double q1 = 5.4;
        final double q2 = 17;
        final double q3 = 0.0005;
        final Quaternion q = Quaternion.of(q0, q1, q2, q3);

        final Quaternion qConjugate = q.conjugate();

        Assert.assertEquals(q0, qConjugate.getQ0(), 0);
        Assert.assertEquals(-q1, qConjugate.getQ1(), 0);
        Assert.assertEquals(-q2, qConjugate.getQ2(), 0);
        Assert.assertEquals(-q3, qConjugate.getQ3(), 0);
    }

    /* TODO remove dependency on Vector3D
    @Test
    public final void testProductQuaternionQuaternion() {

        // Case : analytic test case

        final Quaternion qA = Quaternion.of(1, 0.5, -3, 4);
        final Quaternion qB = Quaternion.of(6, 2, 1, -9);
        final Quaternion qResult = Quaternion.multiply(qA, qB);

        Assert.assertEquals(44, qResult.getQ0(), EPS);
        Assert.assertEquals(28, qResult.getQ1(), EPS);
        Assert.assertEquals(-4.5, qResult.getQ2(), EPS);
        Assert.assertEquals(21.5, qResult.getQ3(), EPS);

        // comparison with the result given by the formula :
        // qResult = (scalarA * scalarB - vectorA . vectorB) + (scalarA * vectorB + scalarB * vectorA + vectorA ^
        // vectorB)

        final Vector3D vectorA = new Vector3D(qA.getVectorPart());
        final Vector3D vectorB = new Vector3D(qB.getVectorPart());
        final Vector3D vectorResult = new Vector3D(qResult.getVectorPart());

        final double scalarPartRef = qA.getScalarPart() * qB.getScalarPart() - Vector3D.dotProduct(vectorA, vectorB);

        Assert.assertEquals(scalarPartRef, qResult.getScalarPart(), EPS);

        final Vector3D vectorPartRef = ((vectorA.scalarMultiply(qB.getScalarPart())).add(vectorB.scalarMultiply(qA
                .getScalarPart()))).add(Vector3D.crossProduct(vectorA, vectorB));
        final double norm = (vectorResult.subtract(vectorPartRef)).norm();

        Assert.assertEquals(0, norm, EPS);

        // Conjugate of the product of two quaternions and product of their conjugates :
        // Conj(qA * qB) = Conj(qB) * Conj(qA)

        final Quaternion conjugateOfProduct = qB.getConjugate().multiply(qA.getConjugate());
        final Quaternion productOfConjugate = (qA.multiply(qB)).getConjugate();

        Assert.assertEquals(conjugateOfProduct.getQ0(), productOfConjugate.getQ0(), EPS);
        Assert.assertEquals(conjugateOfProduct.getQ1(), productOfConjugate.getQ1(), EPS);
        Assert.assertEquals(conjugateOfProduct.getQ2(), productOfConjugate.getQ2(), EPS);
        Assert.assertEquals(conjugateOfProduct.getQ3(), productOfConjugate.getQ3(), EPS);
    }
    */
    /* TODO remove dependency on Vector3D
    @Test
    public final void testProductQuaternionVector() {

        // Case : Product between a vector and a quaternion : QxV

        final Quaternion quaternion = Quaternion.of(4, 7, -1, 2);
        final double[] vector = {2.0, 1.0, 3.0};
        final Quaternion qResultQxV = Quaternion.multiply(quaternion, Quaternion.of(vector));

        Assert.assertEquals(-19, qResultQxV.getQ0(), EPS);
        Assert.assertEquals(3, qResultQxV.getQ1(), EPS);
        Assert.assertEquals(-13, qResultQxV.getQ2(), EPS);
        Assert.assertEquals(21, qResultQxV.getQ3(), EPS);

        // comparison with the result given by the formula :
        // qResult = (- vectorQ . vector) + (scalarQ * vector + vectorQ ^ vector)

        final double[] vectorQ = quaternion.getVectorPart();
        final double[] vectorResultQxV = qResultQxV.getVectorPart();

        final double scalarPartRefQxV = -Vector3D.dotProduct(new Vector3D(vectorQ), new Vector3D(vector));
        Assert.assertEquals(scalarPartRefQxV, qResultQxV.getScalarPart(), EPS);

        final Vector3D vectorPartRefQxV = (new Vector3D(vector).scalarMultiply(quaternion.getScalarPart())).add(Vector3D
                .crossProduct(new Vector3D(vectorQ), new Vector3D(vector)));
        final double normQxV = (new Vector3D(vectorResultQxV).subtract(vectorPartRefQxV)).norm();
        Assert.assertEquals(0, normQxV, EPS);

        // Case : Product between a vector and a quaternion : VxQ

        final Quaternion qResultVxQ = Quaternion.multiply(Quaternion.of(vector), quaternion);

        Assert.assertEquals(-19, qResultVxQ.getQ0(), EPS);
        Assert.assertEquals(13, qResultVxQ.getQ1(), EPS);
        Assert.assertEquals(21, qResultVxQ.getQ2(), EPS);
        Assert.assertEquals(3, qResultVxQ.getQ3(), EPS);

        final double[] vectorResultVxQ = qResultVxQ.getVectorPart();

        // comparison with the result given by the formula :
        // qResult = (- vector . vectorQ) + (scalarQ * vector + vector ^ vectorQ)

        final double scalarPartRefVxQ = -Vector3D.dotProduct(new Vector3D(vectorQ), new Vector3D(vector));
        Assert.assertEquals(scalarPartRefVxQ, qResultVxQ.getScalarPart(), EPS);

        final Vector3D vectorPartRefVxQ = (new Vector3D(vector).scalarMultiply(quaternion.getScalarPart())).add(Vector3D
                .crossProduct(new Vector3D(vector), new Vector3D(vectorQ)));
        final double normVxQ = (new Vector3D(vectorResultVxQ).subtract(vectorPartRefVxQ)).norm();
        Assert.assertEquals(0, normVxQ, EPS);
    }
    */
    @Test
    public final void testDotProductQuaternionQuaternion() {
        // expected output
        final double expected = -6.;
        // inputs
        final Quaternion q1 = Quaternion.of(1, 2, 2, 1);
        final Quaternion q2 = Quaternion.of(3, -2, -1, -3);

        final double actual1 = Quaternion.dotProduct(q1, q2);
        final double actual2 = q1.dotProduct(q2);

        Assert.assertEquals(expected, actual1, EPS);
        Assert.assertEquals(expected, actual2, EPS);
    }

    @Test
    public final void testScalarMultiplyDouble() {
        // expected outputs
        final double w = 1.6;
        final double x = -4.8;
        final double y = 11.20;
        final double z = 2.56;
        // inputs
        final Quaternion q1 = Quaternion.of(0.5, -1.5, 3.5, 0.8);
        final double a = 3.2;

        final Quaternion q = q1.multiply(a);

        Assert.assertEquals(w, q.getQ0(), COMPARISON_EPS);
        Assert.assertEquals(x, q.getQ1(), COMPARISON_EPS);
        Assert.assertEquals(y, q.getQ2(), COMPARISON_EPS);
        Assert.assertEquals(z, q.getQ3(), COMPARISON_EPS);
    }

    @Test
    public final void testAddQuaternionQuaternion() {
        // expected outputs
        final double w = 4;
        final double x = -1;
        final double y = 2;
        final double z = -4;
        // inputs
        final Quaternion q1 = Quaternion.of(1., 2., -2., -1.);
        final Quaternion q2 = Quaternion.of(3., -3., 4., -3.);

        final Quaternion qa = Quaternion.add(q1, q2);
        final Quaternion qb = q1.add(q2);

        Assert.assertEquals(w, qa.getQ0(), EPS);
        Assert.assertEquals(x, qa.getQ1(), EPS);
        Assert.assertEquals(y, qa.getQ2(), EPS);
        Assert.assertEquals(z, qa.getQ3(), EPS);

        Assert.assertEquals(w, qb.getQ0(), EPS);
        Assert.assertEquals(x, qb.getQ1(), EPS);
        Assert.assertEquals(y, qb.getQ2(), EPS);
        Assert.assertEquals(z, qb.getQ3(), EPS);
    }

    @Test
    public final void testSubtractQuaternionQuaternion() {
        // expected outputs
        final double w = -2.;
        final double x = 5.;
        final double y = -6.;
        final double z = 2.;
        // inputs
        final Quaternion q1 = Quaternion.of(1., 2., -2., -1.);
        final Quaternion q2 = Quaternion.of(3., -3., 4., -3.);

        final Quaternion qa = Quaternion.subtract(q1, q2);
        final Quaternion qb = q1.subtract(q2);

        Assert.assertEquals(w, qa.getQ0(), EPS);
        Assert.assertEquals(x, qa.getQ1(), EPS);
        Assert.assertEquals(y, qa.getQ2(), EPS);
        Assert.assertEquals(z, qa.getQ3(), EPS);

        Assert.assertEquals(w, qb.getQ0(), EPS);
        Assert.assertEquals(x, qb.getQ1(), EPS);
        Assert.assertEquals(y, qb.getQ2(), EPS);
        Assert.assertEquals(z, qb.getQ3(), EPS);
}

    @Test
    public final void testNorm() {

        final double q0 = 2;
        final double q1 = 1;
        final double q2 = -4;
        final double q3 = 3;
        final Quaternion q = Quaternion.of(q0, q1, q2, q3);

        final double norm = q.norm();

        Assert.assertEquals(Math.sqrt(30), norm, 0);

        final double normSquareRef = Quaternion.multiply(q, q.conjugate()).getScalarPart();
        Assert.assertEquals(Math.sqrt(normSquareRef), norm, 0);
    }

    @Test
    public final void testNormalize() {

        final Quaternion q = Quaternion.of(2, 1, -4, -2);

        final Quaternion versor = q.normalize();

        Assert.assertEquals(2.0 / 5.0, versor.getQ0(), 0);
        Assert.assertEquals(1.0 / 5.0, versor.getQ1(), 0);
        Assert.assertEquals(-4.0 / 5.0, versor.getQ2(), 0);
        Assert.assertEquals(-2.0 / 5.0, versor.getQ3(), 0);

        Assert.assertEquals(1, versor.norm(), 0);
    }

    @Test(expected=IllegalStateException.class)
    public final void testNormalizeFail() {
        final Quaternion zeroQ = Quaternion.of(0, 0, 0, 0);
        zeroQ.normalize();
    }

    @Test
    public final void testObjectEquals() {
        final double one = 1;
        final Quaternion q1 = Quaternion.of(one, one, one, one);
        Assert.assertTrue(q1.equals(q1));

        final Quaternion q2 = Quaternion.of(one, one, one, one);
        Assert.assertTrue(q2.equals(q1));

        final Quaternion q3 = Quaternion.of(one, Math.nextUp(one), one, one);
        Assert.assertFalse(q3.equals(q1));

        Assert.assertFalse(q3.equals("bar"));
    }

    @Test
    public void testHashCode() {
        Quaternion x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        Quaternion y = Quaternion.of(0.0, 0.0 + Double.MIN_VALUE, 0.0, 0.0);
        Assert.assertFalse(x.hashCode()==y.hashCode());
        y = Quaternion.of(0.0 + Double.MIN_VALUE, 0.0, 0.0, 0.0);
        Assert.assertFalse(x.hashCode()==y.hashCode());

        // "equals" and "hashCode" must be compatible: if two objects have
        // different hash codes, "equals" must return false.
        final String msg = "'equals' not compatible with 'hashCode'";

        x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        y = Quaternion.of(-0.0, 0.0, 0.0, 0.0);
        Assert.assertTrue(x.hashCode() != y.hashCode());
        Assert.assertFalse(msg, x.equals(y));

        x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        y = Quaternion.of(0.0, -0.0, 0.0, 0.0);
        Assert.assertTrue(x.hashCode() != y.hashCode());
        Assert.assertFalse(msg, x.equals(y));

        x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        y = Quaternion.of(0.0, 0.0, -0.0, 0.0);
        Assert.assertTrue(x.hashCode() != y.hashCode());
        Assert.assertFalse(msg, x.equals(y));

        x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        y = Quaternion.of(0.0, 0.0, 0.0, -0.0);
        Assert.assertTrue(x.hashCode() != y.hashCode());
        Assert.assertFalse(msg, x.equals(y));
    }

    @Test
    public final void testQuaternionEquals() {
        final double inc = 1e-5;
        final Quaternion q1 = Quaternion.of(2, 1, -4, -2);
        final Quaternion q2 = Quaternion.of(q1.getQ0() + inc, q1.getQ1(), q1.getQ2(), q1.getQ3());
        final Quaternion q3 = Quaternion.of(q1.getQ0(), q1.getQ1() + inc, q1.getQ2(), q1.getQ3());
        final Quaternion q4 = Quaternion.of(q1.getQ0(), q1.getQ1(), q1.getQ2() + inc, q1.getQ3());
        final Quaternion q5 = Quaternion.of(q1.getQ0(), q1.getQ1(), q1.getQ2(), q1.getQ3() + inc);

        Assert.assertFalse(q1.equals(q2, 0.9 * inc));
        Assert.assertFalse(q1.equals(q3, 0.9 * inc));
        Assert.assertFalse(q1.equals(q4, 0.9 * inc));
        Assert.assertFalse(q1.equals(q5, 0.9 * inc));

        Assert.assertTrue(q1.equals(q2, 1.1 * inc));
        Assert.assertTrue(q1.equals(q3, 1.1 * inc));
        Assert.assertTrue(q1.equals(q4, 1.1 * inc));
        Assert.assertTrue(q1.equals(q5, 1.1 * inc));
    }

    @Test
    public final void testQuaternionEquals2() {
        final Quaternion q1 = Quaternion.of(1, 4, 2, 3);
        final double gap = 1e-5;
        final Quaternion q2 = Quaternion.of(1 + gap, 4 + gap, 2 + gap, 3 + gap);

        Assert.assertTrue(q1.equals(q2, 10 * gap));
        Assert.assertFalse(q1.equals(q2, gap));
        Assert.assertFalse(q1.equals(q2, gap / 10));
    }

    @Test
    public final void testIsUnitQuaternion() {
        final Random r = new Random(48);
        final int numberOfTrials = 1000;
        for (int i = 0; i < numberOfTrials; i++) {
            final Quaternion q1 = Quaternion.of(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());
            final Quaternion q2 = q1.normalize();
            Assert.assertTrue(q2.isUnitQuaternion(COMPARISON_EPS));
        }

        final Quaternion q = Quaternion.of(1, 1, 1, 1);
        Assert.assertFalse(q.isUnitQuaternion(COMPARISON_EPS));
    }

    @Test
    public final void testIsPureQuaternion() {
        final Quaternion q1 = Quaternion.of(0, 5, 4, 8);
        Assert.assertTrue(q1.isPureQuaternion(EPS));

        final Quaternion q2 = Quaternion.of(0 - EPS, 5, 4, 8);
        Assert.assertTrue(q2.isPureQuaternion(EPS));

        final Quaternion q3 = Quaternion.of(0 - 1.1 * EPS, 5, 4, 8);
        Assert.assertFalse(q3.isPureQuaternion(EPS));

        final Random r = new Random(48);
        final double[] v = {r.nextDouble(), r.nextDouble(), r.nextDouble()};
        final Quaternion q4 = Quaternion.of(v);
        Assert.assertTrue(q4.isPureQuaternion(0));

        final Quaternion q5 = Quaternion.of(0, v);
        Assert.assertTrue(q5.isPureQuaternion(0));
    }

    @Test
    public final void testPositivePolarFormWhenScalarPositive(){
        Quaternion q = Quaternion.of(3, -3, -3, 3);
        Quaternion actual = q.positivePolarForm();
        Quaternion expected = Quaternion.of(0.5, -0.5, -0.5, 0.5);
        assertEquals(actual, expected, EPS);
    }

    @Test
    public final void testPositivePolarFormWhenScalarNegative(){
        Quaternion q = Quaternion.of(-3, 3, -3, 3);
        Quaternion actual = q.positivePolarForm();
        Quaternion expected = Quaternion.of(0.5, -0.5, 0.5, -0.5);
        assertEquals(actual, expected, EPS);
    }

    /* TODO remove dependency on Rotation
    @Test
    public final void testPolarForm() {
        final Random r = new Random(48);
        final int numberOfTrials = 1000;
        for (int i = 0; i < numberOfTrials; i++) {
            final Quaternion q = Quaternion.of(2 * (r.nextDouble() - 0.5), 2 * (r.nextDouble() - 0.5),
                                                2 * (r.nextDouble() - 0.5), 2 * (r.nextDouble() - 0.5));
            final Quaternion qP = q.positivePolarForm();

            Assert.assertTrue(qP.isUnitQuaternion(COMPARISON_EPS));
            Assert.assertTrue(qP.getQ0() >= 0);

            final Rotation rot = new Rotation(q.getQ0(), q.getQ1(), q.getQ2(), q.getQ3(), true);
            final Rotation rotP = new Rotation(qP.getQ0(), qP.getQ1(), qP.getQ2(), qP.getQ3(), true);

            Assert.assertEquals(rot.getAngle(), rotP.getAngle(), COMPARISON_EPS);
            Assert.assertEquals(rot.getAxis(RotationConvention.VECTOR_OPERATOR).getX(),
                                rot.getAxis(RotationConvention.VECTOR_OPERATOR).getX(),
                                COMPARISON_EPS);
            Assert.assertEquals(rot.getAxis(RotationConvention.VECTOR_OPERATOR).getY(),
                                rot.getAxis(RotationConvention.VECTOR_OPERATOR).getY(),
                                COMPARISON_EPS);
            Assert.assertEquals(rot.getAxis(RotationConvention.VECTOR_OPERATOR).getZ(),
                                rot.getAxis(RotationConvention.VECTOR_OPERATOR).getZ(),
                                COMPARISON_EPS);
        }
    }
*/
    @Test
    public final void testInverse() {
        final Quaternion q = Quaternion.of(1.5, 4, 2, -2.5);

        final Quaternion inverseQ = q.inverse();
        Assert.assertEquals(1.5 / 28.5, inverseQ.getQ0(), 0);
        Assert.assertEquals(-4.0 / 28.5, inverseQ.getQ1(), 0);
        Assert.assertEquals(-2.0 / 28.5, inverseQ.getQ2(), 0);
        Assert.assertEquals(2.5 / 28.5, inverseQ.getQ3(), 0);

        final Quaternion product = Quaternion.multiply(inverseQ, q);
        Assert.assertEquals(1, product.getQ0(), EPS);
        Assert.assertEquals(0, product.getQ1(), EPS);
        Assert.assertEquals(0, product.getQ2(), EPS);
        Assert.assertEquals(0, product.getQ3(), EPS);

        final Quaternion qNul = Quaternion.of(0, 0, 0, 0);
        try {
            final Quaternion inverseQNul = qNul.inverse();
            Assert.fail("expecting ZeroException but got : " + inverseQNul);
        } catch (IllegalStateException ex) {
            // expected
        }
    }

    @Test
    public final void testMultiply() {
        final Quaternion q1 = Quaternion.of(1, 2, 3, 4);
        final Quaternion q2 = Quaternion.of(4, 3, 2, 1);
        final Quaternion actual = q1.multiply(q2);
        final double w = 1 * 4 - 2 * 3 - 3 * 2 - 4 * 1;
        final double x = 1 * 3 + 2 * 4 + 3 * 1 - 4 * 2;
        final double y = 1 * 2 - 2 * 1 + 3 * 4 + 4 * 3;
        final double z = 1 * 1 + 2 * 2 - 3 * 3 + 4 * 4;
        final Quaternion expected = Quaternion.of(w, x, y, z);
        assertEquals(actual, expected, EPS);
    }

    @Test
    public final void testParseFromToString() {
        final Quaternion q = Quaternion.of(1.1, 2.2, 3.3, 4.4);
        Quaternion parsed = Quaternion.parse(q.toString());
        assertEquals(parsed, q, EPS);
    }

    @Test
    public final void testParseSpecials() {
        Quaternion parsed = Quaternion.parse("[1e-5 Infinity NaN -0xa.cp0]");
        Assert.assertEquals(1e-5, parsed.getQ0(), EPS);
        Assert.assertTrue(Double.isInfinite(parsed.getQ1()));
        Assert.assertTrue(Double.isNaN(parsed.getQ2()));
        Assert.assertEquals(-0xa.cp0, parsed.getQ3(), EPS);
    }

    @Test
    public final void testParseMissingStart() {
        try {
            final Quaternion parsed = Quaternion.parse("1.0 2.0 3.0 4.0]");
            Assert.fail("expecting QuaternionParsingException but got : " + parsed);
        } catch (Quaternion.QuaternionParsingException ex) {
            // expected
        }
    }

    @Test
    public final void testParseMissingEnd() {
        try {
            final Quaternion parsed = Quaternion.parse("[1.0 2.0 3.0 4.0");
            Assert.fail("expecting QuaternionParsingException but got : " + parsed);
        } catch (Quaternion.QuaternionParsingException ex) {
            // expected
        }
    }

    @Test
    public final void testParseMissingPart() {
        try {
            final Quaternion parsed = Quaternion.parse("[1.0 2.0 3.0 ]");
            Assert.fail("expecting QuaternionParsingException but got : " + parsed);
        } catch (Quaternion.QuaternionParsingException ex) {
            // expected
        }
    }

    @Test
    public final void testParseInvalidScalar() {
        try {
            final Quaternion parsed = Quaternion.parse("[1.x 2.0 3.0 4.0]");
            Assert.fail("expecting QuaternionParsingException but got : " + parsed);
        } catch (Quaternion.QuaternionParsingException ex) {
            // expected
        }
    }

    @Test
    public final void testParseInvalidI() {
        try {
            final Quaternion parsed = Quaternion.parse("[1.0 2.0x 3.0 4.0]");
            Assert.fail("expecting QuaternionParsingException but got : " + parsed);
        } catch (Quaternion.QuaternionParsingException ex) {
            // expected
        }
    }

    @Test
    public final void testParseInvalidJ() {
        try {
            final Quaternion parsed = Quaternion.parse("[1.0 2.0 3.0x 4.0]");
            Assert.fail("expecting QuaternionParsingException but got : " + parsed);
        } catch (Quaternion.QuaternionParsingException ex) {
            // expected
        }
    }

    @Test
    public final void testParseInvalidK() {
        try {
            final Quaternion parsed = Quaternion.parse("[1.0 2.0 3.0 4.0x]");
            Assert.fail("expecting QuaternionParsingException but got : " + parsed);
        } catch (Quaternion.QuaternionParsingException ex) {
            // expected
        }
    }

    @Test
    public final void testToString() {
        final Quaternion q = Quaternion.of(1, 2, 3, 4);
        Assert.assertEquals("[1.0 2.0 3.0 4.0]", q.toString());
    }

    /**
     * Assert that two quaternions are equal within tolerance
     * @param actual
     * @param expected
     * @param tolerance
     */
    private void assertEquals(Quaternion actual, Quaternion expected, double tolerance) {
        Assert.assertTrue("expecting " + expected + " but got " + actual, actual.equals(expected, tolerance));
    }

}
