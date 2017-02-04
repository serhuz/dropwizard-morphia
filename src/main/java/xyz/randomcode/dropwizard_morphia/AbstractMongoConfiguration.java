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

import static com.google.common.base.Preconditions.checkNotNull;


public abstract class AbstractMongoConfiguration implements MongoConfiguration {

    /**
     * A name for db which will be used to store data
     */
    protected String dbName;

    /**
     * Specifies whether Morphia will store null values in documents
     */
    protected boolean storeNulls = false;

    /**
     * Specifies whether Morphia will store empty values in documents
     */
    protected boolean storeEmpties = false;

    /**
     * Specifies whether Hibernate Validator should be set to validate entities
     */
    protected boolean enableValidationExtension = false;

    protected Environment environment;
    protected Morphia morphia;


    @JsonIgnore
    @Override
    public AbstractMongoConfiguration using(Environment environment) {
        this.environment = checkNotNull(environment);
        return this;
    }


    @JsonIgnore
    @Override
    public MongoConfiguration with(Morphia morphia) {
        this.morphia = morphia;
        return this;
    }


    @JsonIgnore
    @Override
    public Datastore buildDatastore() {
        MongoClient client = buildClient();

        MapperOptions options = morphia.getMapper().getOptions();
        options.setStoreNulls(getStoreNulls());
        options.setStoreEmpties(getStoreEmpties());

        if (getEnableValidationExtension()) {
            new ValidationExtension(morphia);
        }

        return morphia.createDatastore(client, checkNotNull(getDbName()));
    }


    @JsonIgnore
    public abstract MongoClient buildClient();


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
    public String getDbName() {
        return dbName;
    }


    @JsonProperty
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }


    @JsonProperty
    public void setEnableValidationExtension(boolean enableValidationExtension) {
        this.enableValidationExtension = enableValidationExtension;
    }
}
