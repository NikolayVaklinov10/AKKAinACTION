package testdriven

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.{MustMatchers, WordSpecLike}
import testdriven.SilentActor.{GetState, SilentMessage}


class SilentActorTest extends TestKit(ActorSystem("testsystem"))
  with WordSpecLike
  with MustMatchers
  with StopSystemAfterAll {

  "A Silent Actor" must {

    "change internal state when it receives a message, single" in {
      import SilentActor._
      val silentActor = TestActorRef[SilentActor]
      silentActor ! SilentActor.SilentMessage("whisper")
      silentActor.underlyingActor.state must (contain("whisper"))
    }
    "change internal state when it receives a message, multi" in {
      import SilentActor._
      val silentActormulti = system.actorOf(Props[SilentActor], "s3")
      silentActormulti ! SilentMessage("whisper1")
      silentActormulti ! SilentMessage("whisper2")
      silentActormulti ! GetState(testActor)
      expectMsg(Vector("whisper1", "whisper2"))
    }
  }
}

