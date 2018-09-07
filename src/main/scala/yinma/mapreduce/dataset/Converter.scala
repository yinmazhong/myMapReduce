package yinma.mapreduce.dataset

/**
  * Created by xxh on 18-9-5.
  */
trait Converter[In,Out] {
    def convert(in:In):Out
}
