
package uk.skelty.ScalIRC

class Message (ident : String, channel : String, msgtype : String) {
	def process (extra : String = ""): String = msgtype match {
		case "PRIVMSG" => process_privmsg(extra.substring(1))
		case _ => ""
	}

	def process_privmsg(extra : String): String = extra match {
		case "shutdown" => "shutdown"
		case "hey" => Response.message(channel, "hello!")
		case _ => ""
	}
}
