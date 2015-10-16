package hmda.geo

import akka.stream.scaladsl.Tcp
import hmda.geo.http.HttpService
import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import hmda.geo.tcp.TcpService

object HMDAGeo extends App with TcpService with HttpService {
  override implicit val system = ActorSystem("hmda-geo")
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  val httpHost = config.getString("hmda.geo.http.interface")
  val httpPort = config.getInt("hmda.geo.http.port")

  val http = Http(system).bindAndHandle(routes, httpHost, httpPort)

  val tcpHost = config.getString("hmda.geo.tcp.interface")
  val tcpPort = config.getInt("hmda.geo.tcp.port")

  val tcpBinding = Tcp().bind(tcpHost, tcpPort)
  tcpBinding.to(tractHandler).run()

  sys.addShutdownHook {
    system.shutdown()
  }
}
