package yinma.mapreduce.api

import java.nio.file.{Path, Paths}

import yinma.mapreduce.api.MapReduce.{MapReduceUpB, MapperT, ReducerT}
import yinma.mapreduce.dataset.{FilePathInputDataSet, InputDataSet, OutputDataSet}
import yinma.mapreduce.utils.EngineLogger

import scala.concurrent.duration.Duration

/**
  * Created by xxh on 18-9-5.
  */
trait Job extends EngineLogger {
  private var output: Option[OutputDataSet[_, _]] = None
  private val mappers = collection.mutable.Map[String, Class[_ <: MapperT]]()
  private var reducer: Class[_ <: ReducerT] = _

  private var inputDataSet = Seq[Path]()

  protected val name: String

  protected var timeout: Duration = _

  def withTimeout(timeout: Duration): Job = {
    this.timeout = timeout
    this
  }

  def getTimeout: Duration = {
    this.timeout
  }

  def addReducer[A <: ReducerT](clazz: Class[A]): Job = {
    reducer = clazz
    logger.debug(s"added reducer $clazz")
    this
  }

  def addMapper[A <: MapperT](clazz: Class[A]): Job = {
    mappers += clazz.getName -> clazz
    logger.debug(s"added mapper $clazz")
    this
  }

  def addSingleMapReduce[A <: MapReduceUpB](clazz: Class[A]): Job = {
    logger.debug(s"adding single MapReduce class implementation $clazz")
    addMapper(clazz)
    addReducer(clazz)
    this

  }

  def getSingleMapperClass: Class[_ <: MapperT] = mappers.head._2


  def getReducer:Option[Class[_ <: ReducerT]] = Option(reducer)

  def addInputPaths(paths:Path*)={
    inputDataSet = paths
  }

  def addInputPathsFromStrs(paths:String*)={
    addInputPaths(paths.map(Paths.get(_)): _*)
  }

  def getInputDataSetPaths:Seq[InputDataSet[_]] = inputDataSet.map(new FilePathInputDataSet((_)))

  def addOutput(outputDataSet: OutputDataSet[_, _]): Job = {
    this.output = Some(outputDataSet)
    this
  }

  def getOutput: Option[OutputDataSet[_, _]] = output

  def getName: String = name

  override def hashCode(): Int = name.hashCode

  override def equals(other: scala.Any): Boolean = this.name == other.asInstanceOf[Job].name


}

  object Job {
    def apply(jName: String): Job = new Job {
      require(jName != null && jName.nonEmpty, "jobName should be given,and it can't be an empty String")
      override protected val name: String = jName
    }

    def newJobId(j: Job): String = {
      s"${j.name}-${java.util.UUID.randomUUID()}"
    }
  }

  sealed trait JobAction

  case object Start extends JobAction
