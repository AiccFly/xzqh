package cn.aiccfly.tongji

object Tongji {

  def main(args: Array[String]): Unit = {
/*    微1
    202009 208
    202010 814
    微2
    08 1141
    09 25
    10 329.5
    11 58.9
    支
    8 4330.2-144.93
    9 4250.7-86.8
    10 3971.8-32
    11 675.5*/

    val bayue = 1141+4330.2-144.93
    val jiuyue = 208+25+4250.7-86.8
    val shiyue = 814+329.5+3971.8-32
    val shiyiyue= 58.9+675.5
    val total = bayue+jiuyue+shiyue+shiyiyue

    println(bayue)
    println(jiuyue)
    println(shiyue)
    println(shiyiyue)
    println(total)
  }

}
