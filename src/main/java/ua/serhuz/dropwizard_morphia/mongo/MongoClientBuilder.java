/*
 *  Copyright (C) 2015 Sergei Munovarov.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package ua.serhuz.dropwizard_morphia.mongo;

import com.mongodb.ServerAddress;
import io.dropwizard.setup.Environment;
import ua.serhuz.dropwizard_morphia.health.MongoHealthCheck;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class MongoClientBuilder {

    private final Environment environment;
    private MongoClientConfiguration configuration;

    public MongoClientBuilder(Environment environment) {
        this.environment = environment;
    }

    public MongoClientBuilder using(MongoClientConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public MongoClient build() throws UnknownHostException {
        List<MongoConnection> connections = configuration.getConnections();
        List<ServerAddress> addrs = new LinkedList<ServerAddress>();

        for (MongoConnection connection : connections) {
            addrs.add(new ServerAddress(connection.getHost(), connection.getPort()));
        }

        MongoClient mongoClient = new MongoClient(addrs);

        environment.lifecycle().manage(mongoClient);
        environment.jersey().register(new MongoHealthCheck(mongoClient));

        return mongoClient;
    }
}
