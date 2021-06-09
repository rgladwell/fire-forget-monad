name := "fire-forget-monad"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.1.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"

libraryDependencies += "org.typelevel" %% "cats-laws" % "2.6.1" % "test"

libraryDependencies += "org.typelevel" %% "discipline-scalatest" % "2.1.5" % "test"

libraryDependencies += "org.typelevel" %% "cats-testkit-scalatest" %"2.1.5" % "test"

scalacOptions in Test ++= Seq("-Yrangepos")

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.0" cross CrossVersion.full)
