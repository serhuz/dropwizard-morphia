/*
 * Copyright 2017 Sergei Munovarov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.serhuz.dropwizard_morphia;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mongodb.MongoClient;
import io.dropwizard.jackson.Discoverable;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;

import java.util.Set;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UriMongoConfiguration.class, name = "uriConfig")
})
public interface MongoConfiguration extends Discoverable {

    MongoConfiguration using(Environment environment);

    MongoConfiguration with(Set<Class> entitySet);

    MongoClient buildClient();

    Datastore buildDatastore();
}
