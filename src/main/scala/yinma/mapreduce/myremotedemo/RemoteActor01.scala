package yinma.mapreduce.myremotedemo

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by xxh on 18-9-12.
  */
class RemoteActor01 extends Actor{
  override def receive: Receive = {
    case msg:String => {
      sender() ! "ok"
    }
    case _ => println("...")
  }
}

object RemoteActor01{
  def main(args: Array[String]): Unit = {
    val remoteActorSystem01 = ActorSystem("remoteActorSystem01",ConfigFactory.parseString(
      s"""
         |      akka {
         |       actor {
         |          provider = "akka.remote.RemoteActorRefProvider"
         |        }
         |        remote {
         |          enabled-transports = ["akka.remote.netty.tcp"]
         |          netty.tcp {
         |            hostname = "172.17.70.82"
         |            port = 2666
         |          }
         |        }
         |      }
       """.stripMargin))
  remoteActorSystem01.actorOf(Props[RemoteActor01],"remoteActor01")

  }
}