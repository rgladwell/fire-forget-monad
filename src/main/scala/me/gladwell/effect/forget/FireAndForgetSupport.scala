package me.gladwell.effect.forget

import cats.effect.{IO, unsafe}

trait FireAndForgetSupport[F[_]] {
  def fire[A](f: F[A])
  def forget(f: F[Unit])
}

object FireAndForgetSupport {

  implicit def ioFireForgetSupport(implicit runtime: unsafe.IORuntime): FireAndForgetSupport[IO] =
    new FireAndForgetSupport[IO] {
      override def fire[A](io: IO[A]) = io.unsafeRunSync()
      override def forget(io: IO[Unit]) = io.unsafeRunAndForget()
    }

}
