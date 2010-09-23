package org.parboiled.scala

import org.testng.annotations.Test
import org.scalatest.testng.TestNGSuite
import org.testng.Assert.assertEquals
import parserunners.Rules
import testing.ParboiledTest
import org.parboiled.examples.calculators.SimpleCalculator1

class TracingTest extends ParboiledTest with TestNGSuite {

  val parser = new SimpleCalculator1

  type Result = Int

  @Test
  def testTraceParse() {
    parse(TracingParseRunner(parser.InputLine).filter(Rules.only(parser.Factor, parser.Term)), "1+2") {
      assertEquals(traceLog,
         """InputLine/Expression/Term/Factor, matched, cursor at 1:2 after "1"
..(2)../Term, matched, cursor at 1:2 after "1"
..(1)../Expression/ZeroOrMore/FirstOf/Sequence/Term/Factor, matched, cursor at 1:4 after "1+2"
..(5)../Term, matched, cursor at 1:4 after "1+2"
""")
    }

    parse(TracingParseRunner(parser.InputLine).filter(Rules.below(parser.Factor) && !Rules.below(parser.Digits)), "1+2") {
      assertEquals(traceLog,
         """InputLine/Expression/Term/Factor/Number/Digits, matched, cursor at 1:2 after "1"
..(4)../Number/Action, matched, cursor at 1:2 after "1"
..(4)../Number, matched, cursor at 1:2 after "1"
..(1)../Expression/ZeroOrMore/FirstOf/Sequence/Term/Factor/Number/Digits, matched, cursor at 1:4 after "1+2"
..(7)../Number/Action, matched, cursor at 1:4 after "1+2"
..(7)../Number, matched, cursor at 1:4 after "1+2"
""")
    }
  }

}