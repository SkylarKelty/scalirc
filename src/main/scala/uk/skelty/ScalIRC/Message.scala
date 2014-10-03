
package uk.skelty.ScalIRC

import java.io._
import scala.io._

class Message (writer: BufferedWriter, ident: String, channel: String, msgtype: String) {
	/**
	 * Process a message.
	 * @type String
	 */
	def process (isadmin: Boolean, extra: String = "") {
		println("Processing message type: " + msgtype)

		if (msgtype == "PRIVMSG") {
			val msg = extra.substring(1)
			println("Processing message: " + msg)

			val parts = msg.split(" ")
			if (parts(0) == "!S") {
				val command = parts(1)
				command match {
					case "remind" => Responses.Reminder.respond(writer, channel, msg)
				}
			}
		}
	}
}
