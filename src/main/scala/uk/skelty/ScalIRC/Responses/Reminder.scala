
package uk.skelty.ScalIRC.Responses

import java.io._
import scala.io._
import scala.concurrent.ops._

object Reminder {
	def respond(writer: BufferedWriter, channel: String, msg: String) {
		val parts = msg.split(" ")

		val who = parts(3)

		var time: Int = 0

		val datetype = parts(5)
		val amount = parts(5)

		if (datetype == "in") {
			val period = parts(6)

			time = period match {
				case "weeks" => amount.toInt * 60 * 60 * 24 * 7
				case "days" => amount.toInt * 60 * 60 * 24
				case "hours" => amount.toInt * 60 * 60
				case "minutes" => amount.toInt * 60
				case "seconds" => amount.toInt
				case _ => 0
			}
		}

		// Start a thread to respond in `time` seconds.
		if (time > 0) {
			spawn {
				Thread.sleep(time) 
				writer.write(uk.skelty.ScalIRC.Response.message(channel, who + ": Try this first"))
				writer.flush()
			}
		}
	}
}