package org.specs2.matcher

import scalaz.Validation
import scala.language.adhocExtensions

/**
 * Matchers for the Validation datatype
 */
trait ValidationMatchers {

  def beSuccess[T](t: ValueCheck[T]) = SuccessValidationCheckedMatcher(t)
  def beSuccess[T] = SuccessValidationMatcher[T]()

  def beFailure[T](t: ValueCheck[T]) = FailureValidationCheckedMatcher(t)
  def beFailure[T] = FailureValidationMatcher[T]()

}

object ValidationMatchers extends ValidationMatchers

case class SuccessValidationMatcher[T]() extends OptionLikeMatcher[Validation[?, T], T]("Success", _.toOption)
case class SuccessValidationCheckedMatcher[T](check: ValueCheck[T]) extends OptionLikeCheckedMatcher[Validation[?, T], T]("Success", _.toEither.toOption, check)

case class FailureValidationMatcher[T]() extends OptionLikeMatcher[Validation[T, ?], T]("Failure", _.toEither.left.toOption)
case class FailureValidationCheckedMatcher[T](check: ValueCheck[T]) extends OptionLikeCheckedMatcher[Validation[T, ?], T]("Failure", _.toEither.left.toOption, check)
