package yinma.mapreduce.executors

import akka.actor.{Actor, ActorLogging}

/**
  * Created by xxh on 18-9-6.
  */
class ReducerActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case ReducerDescription(jobName,reducer,chunk) =>
      log.debug(s"reduction phase of Job $jobName")
      val key = chunk.head._1
      val values = chunk.map(c =>
      c._2)
      val r = reducer.runReduce(key,values)

      sender() ! ReductionResult(jobName,r)
  }
}
