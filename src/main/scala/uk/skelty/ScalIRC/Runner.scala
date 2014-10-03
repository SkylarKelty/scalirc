
package uk.skelty.ScalIRC

object Runner {
	def main(args: Array[String]) {
		println("Loading ScalaBot...")
		val server = new IRCClient()
		server.connect()
		println("Shutting down!")
	}
}
