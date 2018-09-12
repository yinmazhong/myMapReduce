package yinma.mapreduce.myremotedemo

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by xxh on 18-9-12.
  */
class RemoteActor02 extends Actor{

  //远程Actor
  var remoteActor : ActorSelection = null
  //当前Actor
  var localActor : akka.actor.ActorRef = null


  override def preStart(): Unit ={
    remoteActor = context.actorSelection("akka.tcp://remoteActorSystem01@172.17.70.82:2666/user/remoteActor01")
    println("远程服务端地址 : " + remoteActor)
  }

  override def receive: Receive = {
    case msg:String => {
      println(s"msg:$msg")
      this.localActor = sender()
      remoteActor ! msg
    }
    case _ => println("...")
  }
}


object RemoteActor02{
  def main(args: Array[String]): Unit = {
    val remoteActorSystem02 = ActorSystem("remoteActorSystem02",ConfigFactory.parseString(
      s"""
         | akka {
         |       actor {
         |          provider = "akka.remote.RemoteActorRefProvider"
         |        }
         |      }
       """.stripMargin))

  val remoteActor02 = remoteActorSystem02.actorOf(Props[RemoteActor02])

    remoteActor02 ! "xuxianhong"

  }
}

