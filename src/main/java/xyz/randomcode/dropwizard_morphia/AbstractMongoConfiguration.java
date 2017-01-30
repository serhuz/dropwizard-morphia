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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.MongoClient;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.ValidationExtension;
import org.mongodb.morphia.mapping.MapperOptions;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractMongoConfiguration implements MongoConfiguration {

    protected String dbName;
    protected boolean storeNulls = false;
    protected boolean storeEmpties = false;
    protected boolean enableValidationExtension = false;

    protected Environment environment;
    protected Set<Class> entitySet;

    @JsonProperty
    public String getDbName() {
        return dbName;
    }

    @JsonProperty
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @JsonProperty
    public boolean getStoreNulls() {
        return storeNulls;
    }

    @JsonProperty
    public void setStoreNulls(boolean storeNulls) {
        this.storeNulls = storeNulls;
    }

    @JsonProperty
    public boolean getStoreEmpties() {
        return storeEmpties;
    }

    @JsonProperty
    public void setStoreEmpties(boolean storeEmpties) {
        this.storeEmpties = storeEmpties;
    }

    @JsonProperty
    public boolean getEnableValidationExtension() {
        return enableValidationExtension;
    }

    @JsonProperty
    public void setEnableValidationExtension(boolean enableValidationExtension) {
        this.enableValidationExtension = enableValidationExtension;
    }

    @JsonIgnore
    @Override
    public AbstractMongoConfiguration using(Environment environment) {
        this.environment = checkNotNull(environment);
        return this;
    }

    @JsonIgnore
    @Override
    public MongoConfiguration with(Set<Class> entitySet) {
        this.entitySet = entitySet;
        return this;
    }

    @JsonIgnore
    @Override
    public abstract MongoClient buildClient();

    @JsonIgnore
    @Override
    public Datastore buildDatastore() {
        MongoClient client = buildClient();

        Morphia morphia = new Morphia().map(entitySet);
        MapperOptions options = morphia.getMapper().getOptions();
        options.setStoreNulls(getStoreNulls());
        options.setStoreEmpties(getStoreEmpties());

        if (getEnableValidationExtension()) {
            new ValidationExtension(morphia);
        }

        return morphia.createDatastore(client, checkNotNull(getDbName()));
    }
}
