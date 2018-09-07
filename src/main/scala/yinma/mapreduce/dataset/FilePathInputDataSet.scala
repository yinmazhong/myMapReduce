package yinma.mapreduce.dataset

import java.nio.file.{Files, Path}

import scala.io.Source


/**
  * Created by xxh on 18-9-5.
  */
class FilePathInputDataSet(path:Path) extends InputDataSet[String] with AutoCloseable{


  logger.info(s"FilePathInputDataSet created from $path")

  if (! Files.exists(path)){
    logger.warn(s"The path does not exist - $path")
  }
  if (! Files.isReadable(path)){
    logger.warn(s"The path is not readable - $path")
  }

  private lazy val source  = Source.fromFile(path.toFile)
  private lazy val lines = source.getLines()

  override def readNext: Option[String] = if (lines.nonEmpty) Some(lines.next()) else None

  override def hasNext: Boolean = lines.hasNext

  override def readSlice(from: Int, to: Int): Iterator[_] = lines.slice(from,to)

  override def close(): Unit = source.close()
}
