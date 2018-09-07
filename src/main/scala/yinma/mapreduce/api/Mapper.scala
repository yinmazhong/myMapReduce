package yinma.mapreduce.api

/**
  * Created by xxh on 18-9-5.
  */

trait Mapper[KIN,VIN,KOUT,VOUT] {
  type K = KIN
  type V = VIN

  final def runMap(k:Any,v:Any):Iterable[(KOUT,VOUT)] = {
    map(k.asInstanceOf[K],v.asInstanceOf[V])
  }

  def map(k:KIN, v: VIN):Iterable[(KOUT,VOUT)]
}
