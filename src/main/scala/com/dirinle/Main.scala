package com.dirinle


object Main{
  def main(args: Array[String]): Unit = {
    import Rest._
    Rest.bindingFuture.foreach(_ => println(s"Server online at ${Settings.interface}:${Settings.port}"))
  }

}
