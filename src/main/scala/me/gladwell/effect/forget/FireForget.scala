package me.gladwell.effect.forget

import cats._

/**
 * A monad that supports fire-and-forget processing in a pure, functional way. A FireForget encapsulates two
 * effect monads of the same time: one main and one for fire-and-forget processing.
 *
 * When FireForget resolves it assumes the main monad is resolved and returned and the forget monad is started
 * separately (for example, in another thread) but never returned, and its result is ignored.
 *
 * FireForget is main biased.
 *
 * @param main
 * @param forget
 * @tparam F
 * @tparam A
 */
case class FireForget[F[_], A] private (private val main: F[A], private val forget: F[Unit])  {

  def unsafeRunSync()(implicit faf: FireAndForgetSupport[F]): Unit = {
    faf.forget(forget)
    faf.fire(main)
  }

}

object FireForget {

  def pure[F[_], A](a: A)(implicit F: Monad[F]): FireForget[F, A] = FireForget(F.pure(a), F.unit)

  implicit def monad[F[_]](implicit F: Monad[F]): Monad[FireForget[F, *]] = new Monad[FireForget[F, *]] {

    override def pure[A](a: A): FireForget[F, A] = FireForget(F.pure(a), F.unit)

    override def flatMap[A, B](fa: FireForget[F, A])(f: (A) =>  FireForget[F, B]): FireForget[F, B] =
      FireForget(F.flatMap(fa.main)(f.andThen(_.main)), fa.forget)

    override def tailRecM[A, B](a: A)(fe: A => FireForget[F, Either[A, B]]): FireForget[F, B] = {
      val channel = fe(a)
      val f = F.tailRecM(a) { a: A => fe(a).main }
      // TODO losing forget mutations from fe
      FireForget(f, channel.forget)
    }
  }

  implicit def eq[F[_], A](implicit ieq: Eq[F[A]]): Eq[FireForget[F, A]] = new Eq[FireForget[F, A]] {
    override def eqv(x: FireForget[F, A], y: FireForget[F, A]): Boolean =
      ieq.eqv(x.main, y.main)
  }

}
