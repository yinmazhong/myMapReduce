package yinma.mapreduce.dataset

/**
  * Created by xxh on 18-9-5.
  */
case object PairToStringCommaSeparated extends Converter[(_,_),String]{
  override def convert(in: Pair[_, _]): String = {
    s"${in._1.toString},${in._2.toString}"
  }
}
