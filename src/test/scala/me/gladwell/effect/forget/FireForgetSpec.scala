package me.gladwell.effect.forget

import cats._
import org.scalacheck.Arbitrary
import org.specs2.SpecificationLike
import org.specs2.mutable.Specification
import org.typelevel.discipline.specs2.Discipline

class FireForgetSpec extends Specification with Discipline with SpecificationLike {

  import cats.implicits._
  import cats.laws.discipline.MonadTests

  implicit def arbFireForget[T](implicit a: Arbitrary[T]): Arbitrary[FireForget[Id, T]] =
    Arbitrary {
      for(e <- Arbitrary.arbitrary[T]) yield {
        val channel: FireForget[Id, T] = FireForget[Id, T](e, Monad[Id].unit)
        channel
      }
    }

  checkAll("FireForget[Id, Int]", MonadTests[FireForget[Id, *]].monad[Int, Int, Int])

}
