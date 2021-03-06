package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    // if column number is larger than the row, there is a mistake
    if (c > r) throw new IllegalArgumentException("column number must not be larger than the row number")
    else if ((c == 0) || (c == r)) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {

    def checkBalance(counter: Int, chars: List[Char]): Boolean = {
      if ((counter == 0) && chars.isEmpty) {
        true
      } else if ((counter < 0) || (chars.isEmpty && (counter > 0))) {
        false
      } else {
        val first = chars.head
        val increment: Int =
          if (first == '(') 1
          else if (first == ')') -1
          else 0

        checkBalance(counter + increment, chars.tail)
      }
    }

    checkBalance(0, chars)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0 || coins.isEmpty) 0
    else if (money == 0) 1
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
