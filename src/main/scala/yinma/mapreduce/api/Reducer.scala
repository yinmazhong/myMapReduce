package yinma.mapreduce.api

/**
  * Created by xxh on 18-9-5.
  */
trait Reducer[K,V,R] {
    type KIN = K
  type VIN = V
  type ROUT = R

  final def runReduce(k:Any,list: Iterable[Any]):ROUT={
    reduce(k.asInstanceOf[KIN],list.asInstanceOf[Iterable[VIN]])
  }

  def reduce(key:K,list:Iterable[V]):R
}
