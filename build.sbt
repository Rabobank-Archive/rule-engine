import sbt.Keys._

// scalastyle:off

// *** Settings ***

lazy val commonSettings = Seq(
  organization := "org.scala-rules",
  organizationHomepage := Some(url("https://github.com/scala-rules/scala-rules")),
  homepage := Some(url("https://github.com/scala-rules/scala-rules")),
  version := "0.2-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-Xlint", "-Xfatal-warnings")
) ++ staticAnalysisSettings ++ publishSettings


// *** Projects ***

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "scala-rules",
    description := "Scala Rules"
  )
  .aggregate(engineCore, engine, engineTestUtils)

lazy val engineCore = (project in file("engine-core"))
  .settings(commonSettings: _*)
  .settings(
    name := "rule-engine-core",
    description := "Rule Engine Core",
    libraryDependencies ++= engineCoreDependencies
  )

lazy val engine = (project in file("engine"))
  .settings(commonSettings: _*)
  .settings(
    name := "rule-engine",
    description := "Rule Engine",
    libraryDependencies ++= engineDependencies
  )
  .dependsOn(engineCore)

lazy val engineTestUtils = (project in file("engine-test-utils"))
  .settings(commonSettings: _*)
  .settings(
    name := "rule-engine-test-utils",
    description := "Rule Engine Test Utils",
    libraryDependencies ++= testUtilDependencies
  )
  .dependsOn(engine)



// *** Dependencies ***

lazy val scalaTestVersion = "2.2.5"

lazy val commonDependencies = Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "javax" % "javaee-api" % "7.0" % Provided,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.7.2",
  "com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-json-provider" % "2.7.3",
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.scalacheck" %% "scalacheck" % "1.12.5" % Test,
  "com.storm-enroute" %% "scalameter" % "0.7" % Test
)

lazy val engineCoreDependencies = commonDependencies

lazy val engineDependencies = commonDependencies

lazy val testUtilDependencies = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion
) ++ commonDependencies



// *** Static analysis ***

lazy val staticAnalysisSettings = {
  lazy val compileScalastyle = taskKey[Unit]("Runs Scalastyle on production code")
  lazy val testScalastyle = taskKey[Unit]("Runs Scalastyle on test code")

  Seq(
    scalastyleConfig in Compile := (baseDirectory in ThisBuild).value / "project" / "scalastyle-config.xml",
    scalastyleConfig in Test := (baseDirectory in ThisBuild).value / "project" / "scalastyle-test-config.xml",

    // The line below is needed until this issue is fixed: https://github.com/scalastyle/scalastyle-sbt-plugin/issues/44
    scalastyleConfig in scalastyle := (baseDirectory in ThisBuild).value / "project" / "scalastyle-test-config.xml",

    compileScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Compile).toTask("").value,
    testScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Test).toTask("").value
  )
}

addCommandAlias("verify", ";compileScalastyle;testScalastyle;coverage;test;coverageReport;coverageAggregate")



// *** Publishing ***

lazy val publishSettings = Seq(
  pomExtra := pom,
  publishMavenStyle := true,
  useGpg := true,
  pomIncludeRepository := { _ => false },
  licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php")),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  }
)

lazy val pom =
  <developers>
    <developer>
      <name>Vincent Zorge</name>
      <email>scala-rules@linuse.nl</email>
      <organization>Linuse</organization>
      <organizationUrl>https://github.com/vzorge</organizationUrl>
    </developer>
  </developers>
  <scm>
    <connection>scm:git:git@github.com:scala-rules/scala-rules.git</connection>
    <developerConnection>scm:git:git@github.com:scala-rules/scala-rules.git</developerConnection>
    <url>git@github.com:scala-rules/scala-rules.git</url>
  </scm>
  