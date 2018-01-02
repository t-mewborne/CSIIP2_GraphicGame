name := "Graphical Game"
version := "1.0"
scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
	"org.scalafx" %% "scalafx" % "8.0.144-R12",
	"com.novocode" % "junit-interface" % "0.11" % Test,
	"org.scalactic" %% "scalactic" % "3.0.4",
	"org.scalatest" %% "scalatest" % "3.0.4" % "test"
)
