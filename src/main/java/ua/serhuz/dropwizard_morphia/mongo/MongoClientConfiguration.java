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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.validation.ValidationMethod;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
public class MongoClientConfiguration {

    @Valid
    private List<MongoConnection> connections;

    public MongoClientConfiguration() {
        // Jackson nop
    }

    @JsonProperty
    public List<MongoConnection> getConnections() {
        return connections;
    }

    @ValidationMethod(message = "connections should contain at least 1 entry")
    private boolean isConnectionsValid() {
        return connections.size() >= 1;
    }
}
