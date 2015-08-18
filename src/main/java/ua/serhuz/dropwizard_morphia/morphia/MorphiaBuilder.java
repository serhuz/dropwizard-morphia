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

package ua.serhuz.dropwizard_morphia.morphia;

import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Morphia;
import ua.serhuz.dropwizard_morphia.mongo.MongoClient;
import ua.serhuz.dropwizard_morphia.mongo.MongoClientBuilder;

import java.net.UnknownHostException;
import java.util.List;

@SuppressWarnings("unused")
public class MorphiaBuilder {

    private final Environment environment;
    private MorphiaConfiguration morphiaConfiguration;
    private MongoClient mongoClient;

    public MorphiaBuilder(Environment environment) {
        this.environment = environment;
    }

    public MorphiaBuilder using(MorphiaConfiguration morphiaConfiguration) {
        this.morphiaConfiguration = morphiaConfiguration;
        return this;
    }

    public Morphia build() throws UnknownHostException {
        mongoClient = new MongoClientBuilder(environment)
                .using(morphiaConfiguration.getMongoClientConfiguration())
                .build();

        Morphia morphia = new Morphia();

        List<String> packagesToMap = morphiaConfiguration.getPackages();

        for (String pkg : packagesToMap) {
            morphia.mapPackage(pkg);
        }

        return morphia;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
