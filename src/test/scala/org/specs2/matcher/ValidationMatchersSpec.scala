package org.specs2
package matcher

import scalaz.*
import scalaz.Leibniz.subst

class ValidationMatchersSpec extends Spec with ValidationMatchers with ResultMatchers { def is = s2"""

 The DisjunctionMatchers trait provides matchers to check \/ instances

  beSuccess checks if an element is \/-(_)
  ${ Validation.success[String, Int](1) must beSuccess(1) }
  ${ Validation.success[String, Int](1) must beSuccess((i: Int) => i must be_>(0)) }
  ${ Validation.success[String, Int](1) must beSuccess(Seq(true, true)) }
  ${ Validation.success[String, Int](1) must beSuccess(===(1)) }
  ${ Validation.success[String, Seq[Int]](Seq(1)) must beSuccess(===(Seq(1))) }
  ${ Validation.failure[String, Int]("wrong") must not(beSuccess(1)) }
  ${ Validation.success[String, Int](1) must beSuccess.like { case i => i must be_>(0) } }
  ${ (Validation.success[String, Int](1) must beSuccess.like { case i => i must be_<(0) }) returns "Success(1) is Success but 1 is greater than 0" }

  beFailure checks if an element is a Failure
  ${ Validation.failure("wrong") must beFailure("wrong") }
  ${ Validation.failure("wrong") must beFailure(===("wrong")) }
  ${ Validation.success(1) must not(beFailure("wrong")) }
  ${ Validation.failure("wrong") must beFailure.like { case m => m.size must be_>(0) } }

"""

}
