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
import com.mongodb.CommandResult;
import com.mongodb.MongoException;
import org.mongodb.morphia.Datastore;

public class MongoHealthCheck extends HealthCheck {

    private final Datastore datastore;

    public MongoHealthCheck(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    protected Result check() throws Exception {
        try {
            final CommandResult stats = datastore.getDB().getStats();
            if (!stats.ok()) return Result.unhealthy(stats.getErrorMessage());
        } catch (MongoException ex) {
            return Result.unhealthy(ex.getMessage());
        }

        return Result.healthy();
    }
}
