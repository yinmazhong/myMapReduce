package yinma.mapreduce.dataset

import yinma.mapreduce.utils.EngineLogger

/**
  * Created by xxh on 18-9-5.
  */
trait OutputDataSet[RecordIn,RecordOut] extends EngineLogger{
  type RecIn = RecordIn

  final def convertAndWrite(rec:Any):Unit = {
    write{
      convert(rec.asInstanceOf[RecIn])
    }

  }

  def convert(rec:RecordIn):RecordOut

  def write(rec:RecordOut):Unit
}
