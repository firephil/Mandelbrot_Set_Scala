lazy val root = project
  .in(file("."))
  .settings(
    name := "Mandelbrot Set",
    description := "Mandelbrot Set in Scala 3",
    version := "1.0.0",
    scalaVersion := "3.0.0-RC3"
    )