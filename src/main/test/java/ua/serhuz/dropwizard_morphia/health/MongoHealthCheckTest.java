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

package ua.serhuz.dropwizard_morphia.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import ua.serhuz.dropwizard_morphia.BaseMongoTest;
import ua.serhuz.dropwizard_morphia.dummy.DummyEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class MongoHealthCheckTest extends BaseMongoTest {

    private static Datastore datastore;

    @BeforeClass
    public static void setUpAll() throws Exception {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(String.format("mongodb://localhost:%d", port)));
        datastore = new Morphia().map(DummyEntity.class).createDatastore(mongoClient, "test");
    }

    @AfterClass
    public static void tearDownAll() throws Exception {
        datastore.getMongo().close();
    }

    @Test
    public void checkHealthyResult() throws Exception {
        MongoHealthCheck healthCheck = new MongoHealthCheck(datastore);
        HealthCheck.Result result = healthCheck.check();
        assertThat(result.isHealthy()).isTrue();
    }
}