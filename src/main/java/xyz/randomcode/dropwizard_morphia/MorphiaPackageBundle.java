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


public abstract class MorphiaPackageBundle<T extends Configuration> extends BaseMorphiaBundle<T> {

    private final String packageName;
    private final boolean ignoreInvalidClasses;


    /**
     * Creates new {@link MorphiaPackageBundle}
     *
     * @param packageName          name of package with {@link org.mongodb.morphia.dao.BasicDAO} entities
     * @param ignoreInvalidClasses specifies whether to ignore classes in the package that cannot be mapped
     */
    protected MorphiaPackageBundle(String packageName, boolean ignoreInvalidClasses) {
        this.packageName = packageName;
        this.ignoreInvalidClasses = ignoreInvalidClasses;
    }


    @Override
    public void run(T configuration, Environment environment) throws Exception {
        morphia = new Morphia().mapPackage(packageName, ignoreInvalidClasses);

        datastore = getMongo(configuration)
                .using(environment)
                .with(morphia)
                .buildDatastore();

        super.run(configuration, environment);
    }
}
