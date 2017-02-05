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

package xyz.randomcode.dropwizard_morphia;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import io.dropwizard.lifecycle.Managed;


@JsonTypeName("uriConfig")
public class UriMongoConfiguration extends AbstractMongoConfiguration {

    /**
     * A {@code mongodb://} URI.
     * If this URI doesn't specify DB name which should be used with Morphia,
     * specify {@link AbstractMongoConfiguration#dbName} instead.
     * If nothing is specified exception will be thrown at runtime.
     */
    @JsonSerialize(using = MongoClientUriSerializer.class)
    private MongoClientURI uri;


    @Override
    protected MongoClient buildClient() {
        String database = uri.getDatabase();
        if (database != null) setDbName(database);

        final MongoClient mongoClient = new MongoClient(uri);

        environment.lifecycle().manage(
                new Managed() {
                    @Override
                    public void start() throws Exception {
                    }


                    @Override
                    public void stop() throws Exception {
                        mongoClient.close();
                    }
                }
        );

        return mongoClient;
    }


    @JsonProperty
    public MongoClientURI getUri() {
        return uri;
    }


    @JsonProperty
    public void setUri(MongoClientURI uri) {
        this.uri = uri;
    }
}
