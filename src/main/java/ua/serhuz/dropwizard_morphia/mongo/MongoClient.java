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
import io.dropwizard.lifecycle.Managed;

import java.net.UnknownHostException;
import java.util.List;

@SuppressWarnings("unused")
public class MongoClient extends com.mongodb.MongoClient implements Managed {

    public MongoClient(String host, int port) throws UnknownHostException {
        super(host, port);
    }

    public MongoClient(ServerAddress addr) {
        super(addr);
    }

    public MongoClient(List<ServerAddress> seeds) {
        super(seeds);
    }

    public void start() throws Exception {

    }

    public void stop() throws Exception {
        close();
    }
}
