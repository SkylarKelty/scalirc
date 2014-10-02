
package uk.skelty.ScalIRC

object Response {
	/**
	 * Crafts a PRIVMSG response.
	 * @type String
	 */
	def message(channel: String, message: String): String = "PRIVMSG " + channel + " :" + message
}
