object LazyStudy {

  lazy val aaa = 666
  //
  val b = 6

  def main(args: Array[String]): Unit = {
    println("init lazy1")
    println(aaa)
    println("direct get lazy")
    println(aaa)
    println("end lazy test")
    println(b)
  }
}
