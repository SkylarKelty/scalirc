
package uk.skelty.ScalIRC

object Response {
	def message(channel : String, message : String): String = "PRIVMSG " + channel + " :" + message
}
