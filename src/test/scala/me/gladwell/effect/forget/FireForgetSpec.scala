package me.gladwell.effect.forget

import cats._
import cats.tests.CatsSuite
import cats.laws.discipline.MonadTests
import org.scalacheck.Arbitrary
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class FireForgetSpec extends CatsSuite with FunSuiteDiscipline {

  implicit def arbFireForget[T](implicit a: Arbitrary[T]): Arbitrary[FireForget[Id, T]] =
    Arbitrary {
      for(e <- Arbitrary.arbitrary[T]) yield {
        val channel: FireForget[Id, T] = FireForget[Id, T](e, Monad[Id].unit)
        channel
      }
    }

  checkAll("FireForget[Id, Int]", MonadTests[FireForget[Id, *]].monad[Int, Int, Int])

}
