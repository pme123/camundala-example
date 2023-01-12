ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.1"

lazy val root = (project in file("."))
  .settings(
    name := "camundala-example",
    libraryDependencies ++=
      camundalaDependencies ++
        camundaDependencies,
    scalacOptions ++= Seq(
      "-Xmax-inlines",
      "100" // is declared as erased, but is in fact used
    )
  ) // Simulations
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    IntegrationTest / fork := true, // needed otherwise it is too much for camunda on docker
    testFrameworks += new TestFramework("camundala.simulation.custom.SimulationTestFramework")
  )

val camundalaVersion = "0.14.0"
lazy val camundalaDependencies = Seq(
  "io.github.pme123" %% "camundala-api" % camundalaVersion,
  "io.github.pme123" %% "camundala-simulation" % camundalaVersion % IntegrationTest
)
val camundaVersion = "7.18.0"
val springBootVersion = "2.7.6"
val h2Version = "2.1.214"
// depending on your project you need:
lazy val camundaDependencies = Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion exclude ("org.slf4j", "slf4j-api"),
  "org.springframework.boot" % "spring-boot-starter-jdbc" % springBootVersion exclude ("org.slf4j", "slf4j-api"),
  "io.netty" % "netty-all" % "4.1.73.Final", // needed for Spring Boot Version > 2.5.*
  "org.camunda.bpm.springboot" % "camunda-bpm-spring-boot-starter-rest" % camundaVersion,
  "org.camunda.bpm.springboot" % "camunda-bpm-spring-boot-starter-webapp" % camundaVersion,
  // json support
  "org.camunda.bpm" % "camunda-engine-plugin-spin" % camundaVersion,
  "org.camunda.spin" % "camunda-spin-dataformat-json-jackson" % "1.17.0",
  // groovy support
  "org.codehaus.groovy" % "groovy-jsr223" % "3.0.13",
  // embedded database
  "com.h2database" % "h2" % h2Version
)