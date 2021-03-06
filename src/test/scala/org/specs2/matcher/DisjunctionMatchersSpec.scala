package org.specs2
package matcher

import scalaz.*

class DisjunctionMatchersSpec extends Spec with DisjunctionMatchers with ResultMatchers { def is = s2"""

 The DisjunctionMatchers trait provides matchers to check \/ instances

  be_\/- checks if an element is \/-(_)
  ${ \/-(1) must be_\/-(1) }
  ${ \/-(1) must be_\/-((i: Int) => i must be_>(0)) }
  ${ \/-(1) must be_\/-(Seq(true, true)) }
  ${ \/-(1) must be_\/-(===(1)) }
  ${ \/-(Seq(1)) must be_\/-(===(Seq(1))) }
  ${ -\/(1) must not(be_\/-(1)) }
  ${ \/-(1) must be_\/-.like { case i => i must be_>(0) } }
  ${ (\/-(1) must be_\/-.like { case i => i must be_<(0) }) returns "\\/-(1) is \\/- but 1 is greater than 0" }

  be_-\/ checks if an element is -\/(_)
  ${ -\/(1) must be_-\/(1) }
  ${ -\/(1) must be_-\/(===(1)) }
  ${ \/-(1) must not(be_-\/(1)) }
  ${ -\/(1) must be_-\/.like { case i => i must be_>(0) } }

  be_\/- / be_-\/ must typecheck when composed with other matchers
  ${ val boomException: Throwable = new Exception("boom")
     Some(-\/(boomException)) must beSome(be_-\/(boomException)) }

"""

}
