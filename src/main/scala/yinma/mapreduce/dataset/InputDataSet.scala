package yinma.mapreduce.dataset

import yinma.mapreduce.utils.EngineLogger

/**
  * Created by xxh on 18-8-17.
  */
trait InputDataSet[Record] extends EngineLogger{
def readNext: Option[Record]
def hasNext:Boolean
  def readSlice(from: Int,to:Int):Iterator[_]
}

