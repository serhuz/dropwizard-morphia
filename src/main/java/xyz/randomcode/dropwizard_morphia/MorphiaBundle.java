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
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Morphia;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public abstract class MorphiaBundle<T extends Configuration> extends BaseMorphiaBundle<T> {

    private final Set<Class> entitySet;


    /**
     * Creates new {@link MorphiaBundle}
     *
     * @param entity   a valid entity class
     * @param entities additional entity classes to be mapped
     */
    protected MorphiaBundle(Class<?> entity, Class<?>... entities) {
        entitySet = new HashSet<>();
        entitySet.add(entity);
        entitySet.addAll(Arrays.asList(entities));
    }


    @Override
    public void run(T configuration, Environment environment) throws Exception {
        morphia = new Morphia().map(entitySet);

        datastore = getMongo(configuration)
                .using(environment)
                .with(morphia)
                .buildDatastore();

        super.run(configuration, environment);
    }
}
