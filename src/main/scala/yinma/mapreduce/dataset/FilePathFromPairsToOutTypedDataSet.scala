package yinma.mapreduce.dataset

import java.io.FileOutputStream
import java.nio.file.{Files, Path}

/**
  * Created by xxh on 18-9-5.
  */
class FilePathFromPairsToOutTypedDataSet(path:Path,createIfNotExists:Boolean = true,converter:Option[Converter[(_,_),String]] = Some(PairToStringCommaSeparated)) extends OutputDataSet[(_,_),String] with AutoCloseable {

  logger.info(s"FilePathOutDataSet created from $path")

  if (! Files.exists(path) && createIfNotExists){
    Files.createDirectory(path)
    logger.debug(s"directories created for path - $path")
  }

  lazy val outputStream = new FileOutputStream(path.toFile)

  override def convert(rec: Pair[_, _]): String = {
    converter.get.convert(rec)
  }

  override def write(rec: String): Unit = outputStream.write(rec.getBytes())

  override def close(): Unit = outputStream.close()
}
