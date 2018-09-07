package yinma.mapreduce.executors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.routing.{DefaultResizer, RoundRobinPool}
import yinma.mapreduce.api.MapReduce.{MapperT, ReducerT}
import yinma.mapreduce.api.Start
import yinma.mapreduce.dataset.{InputDataSet, OutputDataSet}

/**
  * Created by xxh on 18-9-6.
  */
class MapReduceCoordinatorActor(inputDataSet: Seq[InputDataSet[_]],
                                mapper:MapperT,
                                reducer:ReducerT,
                                outputDataSet: OutputDataSet[_,_],
                                jobName:String,
                                jobId:String) extends Actor with ActorLogging{

  private val chunkSize = 10000

  private def resizer = DefaultResizer(upperBound = Runtime.getRuntime.availableProcessors() * 10,rampupRate = 0.5,backoffRate = 0.3)

  private val mapperRouter = context.actorOf(RoundRobinPool(Runtime.getRuntime.availableProcessors,Some(resizer)).props(Props[MapperActor]),s"mapper-router-$jobId")
  private val reducerRouter = context.actorOf(RoundRobinPool(Runtime.getRuntime.availableProcessors,Some(resizer)).props(Props[ReducerActor]),s"reducerr-router-$jobId")

  private val sorter = context.actorOf(Props[SortedActor],s"sorter-$jobId")
  private val writer = context.actorOf(Props[WriterActor],s"writer-$jobId")


  private var nrMapperChunks = 0

  private var nrReducerChunks = 0

  private var startTime:Long = _

  private var jobStarterSender: ActorRef =_


  private def closeOutputResources():Unit = {
    outputDataSet match {
      case o : AutoCloseable => o.close()
      case _ => log.debug(s"tried closing outputDataSet resources resources of job $jobId, but it isn't AutoCloseable")
    }
  }


  private def closeInputResources(): Unit = {
    inputDataSet.foreach {
      case i: AutoCloseable => i.close()
      case _ => log.debug(s"tried closing all inputDataSets resources of job $jobId, but none is AutoCloseable")
    }
  }

  override def receive: Receive = {
    case Start =>
      startTime = System.currentTimeMillis

  }
}
