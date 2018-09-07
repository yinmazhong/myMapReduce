package yinma.mapreduce.executors

import akka.actor.{Actor, ActorLogging}

import scala.collection.mutable

/**
  * Created by xxh on 18-9-6.
  */
class SortedActor extends Actor with ActorLogging{
  private val items = mutable.ListBuffer[(Any,Any)]()

  override def receive: Receive = {
    case PreSort(jobName,a) =>
      log.debug(s"caching for preSorter in Mapper for job $jobName items size to cache ${a.size}")
      items.append(a: _*)
    case Sort(jobName) =>
      log.debug(s"sorting <key , values> phase initiated for job $jobName - items in size ${items.size}")
      sender() ! Sorted(jobName,items)
  }
}
