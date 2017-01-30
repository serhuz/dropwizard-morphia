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

import com.codahale.metrics.health.HealthCheckRegistry;
import com.mongodb.MongoClientURI;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import xyz.randomcode.dropwizard_morphia.dummy.DummyConfiguration;
import xyz.randomcode.dropwizard_morphia.dummy.DummyEntity;
import xyz.randomcode.dropwizard_morphia.health.MongoHealthCheck;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class MorphiaBundleTest extends BaseMongoTest {

    private static MorphiaBundle<DummyConfiguration> morphiaBundle;
    private DummyConfiguration mockConfiguration;
    private Environment mockEnvironment;
    private LifecycleEnvironment mockLifecycle;
    private JerseyEnvironment mockJersey;
    private HealthCheckRegistry mockHealthchecks;

    @BeforeClass
    public static void setUpAll() throws Exception {
        morphiaBundle = new MorphiaBundle<DummyConfiguration>(DummyEntity.class) {
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

    @Before
    public void setUp() throws Exception {
        mockConfiguration = mock(DummyConfiguration.class);
        mockEnvironment = mock(Environment.class);
        mockLifecycle = mock(LifecycleEnvironment.class);
        mockJersey = mock(JerseyEnvironment.class);
        mockHealthchecks = mock(HealthCheckRegistry.class);
        when(mockEnvironment.lifecycle()).thenReturn(mockLifecycle);
        when(mockEnvironment.healthChecks()).thenReturn(mockHealthchecks);
    }

    @After
    public void tearDown() throws Exception {
        reset(mockConfiguration, mockEnvironment, mockLifecycle, mockLifecycle, mockJersey, mockHealthchecks);
    }

    @Test
    public void testRun() throws Exception {
        morphiaBundle.run(mockConfiguration, mockEnvironment);

        verify(mockEnvironment.healthChecks(), times(1)).register(eq("mongo"), any(MongoHealthCheck.class));
    }
}