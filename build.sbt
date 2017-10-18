// scalastyle:off


// *** Settings ***

useGpg := false

lazy val commonSettings = Seq(
  organization := "nl.rabobank.oss.rules",
  organizationHomepage := Some(url("https://github.com/rabobank-nederland")),
  homepage := Some(url("https://github.com/rabobank-nederland/rule-engine")),
  version := "0.6.1-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-Xlint", "-Xfatal-warnings")
) ++ staticAnalysisSettings ++ publishSettings


// *** Projects ***

lazy val ruleEngineRoot = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "rabobank-rules",
    description := "Rabobank Rules"
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
    libraryDependencies ++= engineDependencies,
    addCompilerPlugin(
      "org.scalameta" % "paradise" % "3.0.0.95" cross CrossVersion.full ),
    scalacOptions += "-Xplugin-require:macroparadise",
    resolvers += MavenRepository("scalameta-bintray", file("./provided-repo").toURI.toString)

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
lazy val jodaTimeVersion = "2.4"
lazy val jodaConvertVersion = "1.8"

lazy val commonDependencies = Seq(
  "nl.rabobank.oss.rules" %% "finance-dsl" % "0.2.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.7.2",
  "com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-json-provider" % "2.7.3",
  "joda-time" % "joda-time" % jodaTimeVersion,
  "org.joda" % "joda-convert" % jodaConvertVersion,
  "org.scalameta"  %% "scalameta" % "1.2.0",
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.scalacheck" %% "scalacheck" % "1.12.5" % Test
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

coverageExcludedPackages := ".*Macros"

// Temporarily disabling scalastyle and coverage because they choke on scala meta macros
// TODO : Extract the macros to their own subproject and disable these things there
//addCommandAlias("verify", ";compileScalastyle;testScalastyle;coverage;test;coverageReport;coverageAggregate")
addCommandAlias("verify", ";testScalastyle;test")
addCommandAlias("release", ";clean;compile;publishSigned")


// *** Publishing ***

lazy val publishSettings = Seq(
  pomExtra := pom,
  publishMavenStyle := true,
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
    <developer>
      <name>Jan-Hendrik Kuperus</name>
      <email>jan-hendrik@scala-rules.org</email>
      <organization>Yoink Development</organization>
      <organizationUrl>http://www.yoink.nl</organizationUrl>
    </developer>
    <developer>
      <name>Nathan Perdijk</name>
      <email>nathan@scala-rules.org</email>
    </developer>
  </developers>
  <scm>
    <connection>scm:git:git@github.com:rabobank-nederland/rule-engine.git</connection>
    <developerConnection>scm:git:git@github.com:rabobank-nederland/rule-engine.git</developerConnection>
    <url>git@github.com:rabobank-nederland/rule-engine.git</url>
  </scm>
  
