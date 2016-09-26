package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  test("adding ints") {
    assert(1 + 2 === 3)
  }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s6 = singletonSet(6)
    val s7 = singletonSet(7)
    val s1000 = singletonSet(1000)
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1 and does not contain 2") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s1, 2), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only elements that are in both union sets") {
    new TestSets {
      val u1 = union(s1, s2)
      var u2 = union(s2, s3)
      var i = intersect(u1, u2)
      assert(contains(i, 2), "Element from s2")
      assert(!contains(i, 1), "Element from s1")
      assert(!contains(i, 3), "Element from s3")
    }
  }

  test("diff contains only elements that are in the first union set, but not in the second") {
    new TestSets {
      val u1 = union(s1, s2)
      var u2 = union(s1, s3)
      var d = diff(u1, u2)
      assert(contains(d, 2), "Element from s2")
      assert(!contains(d, 1), "Element from s1")
      assert(!contains(d, 3), "Element from s3")
    }
  }

  test("filter of _ < 5") {
    new TestSets {
      val s = union(union(union(union(union(s1, s3), s4), s5), s7), s1000)
      printSet(s)
      val f = filter(s, elem => elem < 5)
      printSet(f)
      assert(contains(f, 1), "Element from s1")
      assert(contains(f, 3), "Element from s3")
      assert(contains(f, 4), "Element from s4")
      assert(!contains(f, 2), "Element from s2")
      assert(!contains(f, 7), "Element from s7")
      assert(!contains(f, 1000), "Element from s1000")
    }
  }

  test("Check whether all set members are multiples of 3") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(!forall(s, elem => elem % 3 == 0), "Not all are multiples of 3")
    }
  }

  test("Check whether all set members are multiples of 10") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(forall(s, elem => elem % 1 == 0), "All are multiples of 1")
    }
  }

  test("Check whether multiples of 1, 2 and 5 exist in the given set")(
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(exists(s, elem => elem % 1 == 0), "There exists one multiple of 1")
      assert(exists(s, elem => elem % 2 == 0), "There exists one multiple of 2")
      assert(!exists(s, elem => elem % 5 == 0), "There exists not one multiple of 5")
    }
  )

  test("Check map")(
    new TestSets {
      val s = union(union(s1, s2), s3)
      val m = map(s, elem => elem * 10)
      assert(forall(m, elem => elem % 10 == 0), "All are multiples of 10")
    }
  )

}
