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

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import xyz.randomcode.dropwizard_morphia.health.MongoHealthCheck;


public abstract class BaseMorphiaBundle<T extends Configuration> implements ConfiguredBundle<T> {

    protected static final String DEFAULT_NAME = "mongo";
    protected Datastore datastore;
    protected Morphia morphia;


    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }


    @Override
    public void run(T configuration, Environment environment) throws Exception {
        environment.healthChecks().register(getName(), new MongoHealthCheck(datastore));
    }


    protected String getName() {
        return DEFAULT_NAME;
    }


    public Datastore getDatastore() {
        return datastore;
    }


    public Morphia getMorphia() {
        return morphia;
    }


    protected abstract MongoConfiguration getMongo(T configuration);
}
