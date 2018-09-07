package yinma.mapreduce.executors

import akka.actor.{Actor, ActorLogging}

/**
  * Created by xxh on 18-9-6.
  */
class WriterActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case OutputDescription(jobName,outputDataSet,v) =>
      log.debug(s"Writing results for Job $jobName")
      try{
        outputDataSet.convertAndWrite(v)
        sender() ! WrittenSucceeded(jobName)
      }catch {
        case e: Exception =>
          log.error(s"error while writing to outputdataset in Job $jobName exception: {}", e)
          sender ! WrittenFailed(jobName)
      }
  }
}
