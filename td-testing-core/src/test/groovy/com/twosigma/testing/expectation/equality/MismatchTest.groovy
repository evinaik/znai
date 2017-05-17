package com.twosigma.testing.expectation.equality

import com.twosigma.testing.expectation.ActualPath
import org.junit.Assert
import org.junit.Test

/**
 * @author mykola
 */
class MismatchTest {
    @Test
    void "should align multiline mismatch"() {
        def m = new Mismatch(ActualPath.createActualPath("my.var[0]"), "two lines\nmismatch message")

        Assert.assertEquals("my.var[0]: two lines\n" +
                "           mismatch message", m.fullMessage())
    }
}