
package uk.skelty.ScalIRC

import java.net._
import java.io._
import scala.io._
import scala.util.control.Breaks._

class IRCClient {
	def connect() {
		val socket  = new Socket("irc.kent.ac.uk", 6667);
		val writer  = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		val reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		val nick = "ScalIRC"
		val login = "ScalIRC"
		val channel = "#skytown"

		writer.write("NICK " + nick + "\r\n");
		writer.write("USER " + login + " 8 * : scalirc.skelty.uk Bot\r\n");
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
 
		while ({line = reader.readLine(); line != null}) {
		 
		    if (line.toLowerCase().startsWith("ping ")) {
		        // We must respond to PINGs to avoid being disconnected.
		        writer.write("PONG " + line.substring(5) + "\r\n");
		        writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
		        writer.flush( );
		    } else {
		        println(line);
		    }
		 
		    if (line.toLowerCase().contains("shutdown")) {
		    	break;
		    }
		}

		writer.close()
		reader.close()
		socket.close()
	}
}
