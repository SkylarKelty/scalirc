
package uk.skelty.ScalIRC

import java.net._
import java.io._
import scala.io._
import scala.util.control.Breaks._

class IRCClient () {
	def connect() {
		val socket  = new Socket("irc.kent.ac.uk", 6667);
		val writer  = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		val reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		val nick = "ScalIRC"
		val login = "ScalIRC"
		val channel = "#skytown"

		writer.write("NICK " + nick + "\r\n");
		writer.write("USER " + login + " 8 * :Skylar\'s Bot\r\n");
		writer.flush();

		var line = ""

		breakable {
		    while ({line = reader.readLine(); line != null}) {
		        if (line.indexOf("004") >= 0) {
		            println(" All ok, break now?")
		            break;
		        } else if (line.indexOf("433") >= 0) {
		            println("Nickname is already in use.");
		            break;
		        }
		    }
		}

		writer.write("JOIN " + channel + "\r\n");
		writer.flush();
 
		breakable {
			while ({line = reader.readLine(); line != null}) {
				var result = ""

		        println(line);

			    if (line.toLowerCase().startsWith("ping ")) {
			    	result = "PONG " + line.substring(5)
			    } else {
			    	// Split the line up.
			    	val parts = line.split(" ")

			    	// Do we know what to do with this?
			    	if (parts.length >= 3) {
				    	val ident = parts(0)
				    	// Does this look like an ident?
				    	if (ident.indexOf("!") >= 0 && ident.indexOf(":") >= 0 && ident.indexOf("@") >= 0) {
					    	val msgtype = parts(1)
					    	val channel = parts(2)

					    	// Parse the ident.
					    	val nick = ident.substring(1, ident.indexOf("!"))

					    	// Create a message object.
					    	val message = new Message(nick, channel, msgtype)

					    	// Process the message.
					    	if (parts.length > 3) {
					    		val msg = parts(3)
					    		result = message.process(msg)
					    	} else {
					    		result = message.process()
					    	}
				    	}
			    	}
			    }

			    if (result == "shutdown") {
			    	break;
			    } else if (result != "") {
			    	println("RESULT: " + result)

			        writer.write(result + "\r\n");
			        writer.flush();
			    }
			}
		}

		writer.close()
		reader.close()
		socket.close()
	}
}
