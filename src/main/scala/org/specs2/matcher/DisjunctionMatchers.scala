package org.specs2
package matcher

import scalaz.\/
import scala.language.adhocExtensions

/**
 * Matchers for the \/ datatype
 */
trait DisjunctionMatchers {

  def be_\/-[T](t: ValueCheck[T]) = RightDisjunctionCheckedMatcher(t)
  def be_\/-[T] = RightDisjunctionMatcher[T]()

  def be_-\/[T](t: ValueCheck[T]) = LeftDisjunctionCheckedMatcher(t)
  def be_-\/[T] = LeftDisjunctionMatcher[T]()

}

object DisjunctionMatchers extends DisjunctionMatchers

case class RightDisjunctionMatcher[T]() extends OptionLikeMatcher[Any \/ T, T]("\\/-", (_:Any \/ T).toEither.toOption)
case class RightDisjunctionCheckedMatcher[T](check: ValueCheck[T]) extends OptionLikeCheckedMatcher[Any \/ T, T]("\\/-", (_:Any \/ T).toEither.toOption, check)

case class LeftDisjunctionMatcher[T]() extends OptionLikeMatcher[T \/ Any, T]("-\\/", (_:T \/ Any).toEither.left.toOption)
case class LeftDisjunctionCheckedMatcher[T](check: ValueCheck[T]) extends OptionLikeCheckedMatcher[T \/ Any, T]("-\\/", (_: T \/Any).toEither.left.toOption, check)
