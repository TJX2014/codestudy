object Sort {
  def main(args: Array[String]): Unit = {
    val list = List(3, 12, 43, 23, 7, 1, 2, 0)
    println(BubbleSort.sort(list))
    println(QuickSort.sort(list))
  }
}

object BubbleSort {
  def sort(list: List[Int]): List[Int] = list match {
    case List() => List()
    case head :: tail => compute(head, sort(tail))
  }

  def compute(data: Int, ds: List[Int]): List[Int]  = ds match {
    case List() => List(data)
    case head :: tail => if (data < head) data :: ds else head :: compute(data, tail)
  }
}

object QuickSort {
  def sort(list: List[Int]): List[Int] = list match {
    case Nil => Nil
    case List() => List()
    case head :: tail => {
      val (left, right) = tail.partition(_ < head)
      sort(left) ++ (head :: sort(right))
    }
  }
}

