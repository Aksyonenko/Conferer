package com.akqa.kiev.conferer.server.dao.config;

import java.io.IOException;
import java.net.ServerSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.akqa.kiev.conferer.server.dao.config.MongoConfig;
import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
@Profile("embedded")
public class EmbeddedMongoConfig extends MongoConfig {

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		final int port = getFreePort();
		
		MongodConfig config = new MongodConfig(Version.V2_4_1, port, Network.localhostIsIPv6());
		MongodExecutable prepared = MongodStarter.getDefaultInstance().prepare(config);
		prepared.start();

		Mongo mongo = new Mongo("localhost", port);
		
		return mongo;
	}
	
	private static int getFreePort() {
		try {
			ServerSocket socket = new ServerSocket(0);
			int port = socket.getLocalPort();
			socket.close();
			
			return port;
			
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
	}

}
