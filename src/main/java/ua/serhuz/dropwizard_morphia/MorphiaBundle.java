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

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import ua.serhuz.dropwizard_morphia.health.MongoHealthCheck;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class MorphiaBundle<T extends Configuration> implements ConfiguredBundle<T> {

    private static final String DEFAULT_NAME = "mongo";
    private final Set<Class> entitySet;
    private Datastore datastore;

    protected MorphiaBundle(Class<?> entity, Class<?>... entities) {
        entitySet = new HashSet<>();
        entitySet.add(entity);
        entitySet.addAll(Arrays.asList(entities));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        datastore = getMongo(configuration)
                .using(environment)
                .with(entitySet)
                .buildDatastore();

        environment.healthChecks().register(getName(), new MongoHealthCheck(datastore));
    }

    public Datastore getDatastore() {
        return datastore;
    }

    protected String getName() {
        return DEFAULT_NAME;
    }

    protected abstract MongoConfiguration getMongo(T configuration);
}
