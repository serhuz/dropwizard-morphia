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

import com.mongodb.MongoClientURI;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import xyz.randomcode.dropwizard_morphia.dummy.DummyConfiguration;
import xyz.randomcode.dropwizard_morphia.dummy.DummyEntity;
import xyz.randomcode.dropwizard_morphia.health.MongoHealthCheck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class MorphiaPackageBundleTest extends BaseBundleTest {

    private static MorphiaPackageBundle<DummyConfiguration> morphiaBundle;


    @BeforeClass
    public static void setUpAll() throws Exception {
        morphiaBundle = new MorphiaPackageBundle<DummyConfiguration>(DummyEntity.class.getPackage().getName(), false) {
            @Override
            protected MongoConfiguration getMongo(DummyConfiguration configuration) {
                UriMongoConfiguration mongoConfiguration = new UriMongoConfiguration();
                mongoConfiguration.setDbName("test");
                mongoConfiguration.setStoreEmpties(false);
                mongoConfiguration.setStoreNulls(false);
                mongoConfiguration.setUri(new MongoClientURI(String.format("mongodb://localhost:%d", port)));

                return mongoConfiguration;
            }
        };
    }


    @Test
    public void runBundle() throws Exception {
        morphiaBundle.run(mockConfiguration, mockEnvironment);

        verify(mockEnvironment.healthChecks(), times(1)).register(eq("mongo"), any(MongoHealthCheck.class));

        assertThat(morphiaBundle.getMorphia().isMapped(DummyEntity.class)).isTrue();

        Datastore datastore = morphiaBundle.getDatastore();
        assertThat(datastore.getDB().getStats().ok()).isTrue();
    }
}
