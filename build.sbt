name := "fire-forget-monad"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.typelevel" %% "cats-laws" % "2.0.0"

libraryDependencies += "org.typelevel" %% "cats-effect" % "2.0.0"

libraryDependencies += "org.specs2" %% "specs2-core" % "4.6.0" % "test"

libraryDependencies += "org.typelevel" %% "discipline-specs2" % "1.0.0" % "test"

scalacOptions in Test ++= Seq("-Yrangepos")

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
