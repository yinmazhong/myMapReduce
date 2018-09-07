package yinma.mapreduce.api

/**
  * Created by xxh on 18-9-5.
  */
abstract class MapReduce[KIN, VIN, KOUT, VOUT, RKIN, RVIN, R] extends Mapper[KIN, VIN, KOUT, VOUT] with Reducer[RKIN, RVIN, R]


object MapReduce {
  type MapReduceUpB = Mapper[_, _, _, _] with Reducer[_, _, _]
  type MapperT = Mapper[_, _, _, _]
  type ReducerT = Reducer[_, _, _]
}