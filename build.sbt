/** PROJECT */
lazy val specs2Scalaz = project.in(file(".")).
  enablePlugins(GitBranchPrompt, GitVersioning).
  settings(
    organization := "org.specs2",
    name := "specs2-scalaz",
    ThisBuild / crossScalaVersions := Seq(Scala3),
    ThisBuild / scalaVersion := Scala3,
    libraryDependencies ++= dependencies,
    buildSettings,
  )

/** SETTINGS */

val Scala3 = "3.0.0"

val specs2Version = "5.0.0-ALPHA-03"
val scalazVersion = "7.4.0-M7"

val dependencies = Seq(
      "org.specs2" %% "specs2-core" % specs2Version,
      "org.scalaz" %% "scalaz-core" % scalazVersion,
      "org.scalaz" %% "scalaz-effect" % scalazVersion)

lazy val buildSettings =
  compilationSettings  ++
  testingSettings      ++
  testingJvmSettings   ++
  releaseSettings

lazy val compilationSettings = Seq(
  maxErrors := 20,
  Global / onChangedBuildSource := ReloadOnSourceChanges,
  Compile / scalacOptions ++= compilationOptions,
  Compile / doc / scalacOptions ++= compilationOptions)

lazy val compilationOptions =  Seq(
    "-source:future-migration",
    "-language:implicitConversions,postfixOps",
    "-Ykind-projector",
    "-Xcheck-macros",
    "-deprecation:false",
    "-unchecked",
    "-feature")

lazy val testingSettings = Seq(
  logBuffered := false,
  Global / cancelable := true,
  testFrameworks := Seq(TestFramework("org.specs2.runner.Specs2Framework")),
  testOptions := Seq(Tests.Filter(s =>
    Seq("Spec").exists(s.endsWith))))

lazy val testingJvmSettings = Seq(
  javaOptions ++= Seq("-Xmx3G", "-Xss4M"),
  Test / fork := true)

/**
 * RELEASE
 */
lazy val releaseSettings: Seq[Setting[_]] = Seq(
  ThisBuild / versionScheme := Some("early-semver"),
  ThisBuild / githubWorkflowArtifactUpload := false,
  ThisBuild / githubWorkflowBuild := Seq(
    WorkflowStep.Sbt(
      name = Some("Build and test 🔧"),
      commands = List("testOnly -- xonly exclude ci timefactor 3"))),
  ThisBuild / githubWorkflowTargetTags ++= Seq(SPECS2+"*"),
  ThisBuild / githubWorkflowPublishTargetBranches := Seq(RefPredicate.StartsWith(Ref.Tag(SPECS2))),
  ThisBuild / githubWorkflowPublish := Seq(
    WorkflowStep.Sbt(
      name = Some("Release to Sonatype 📇"),
      commands = List("ci-release"),
      env = Map(
        "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
        "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
        "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
        "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
      )
    )),
  homepage := Some(url("https://github.com/etorreborre/specs2-scalaz")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "etorreborre",
      "Eric Torreborre",
      "etorreborre@yahoo.com",
      url("https://github.com/etorreborre")
    )
  ),
  ThisBuild / git.gitTagToVersionNumber := { tag: String => if (tag matches SPECS2+".*") Some(tag.replace(SPECS2, "")) else None },
  ThisBuild / dynverTagPrefix := SPECS2)

val SPECS2 = "SPECS2-SCALAZ-"
