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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class UriMongoConfigurationTest {

    private static ObjectMapper mapper;


    @BeforeClass
    public static void setUpAll() throws Exception {
        mapper = new ObjectMapper();
    }


    @Test
    public void deserialize() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("fixtures/uri_mongo_configuration.yml");

        MongoConfiguration actual = mapper.readValue(stream, UriMongoConfiguration.class);
        assertThat(actual).isNotNull().isInstanceOf(UriMongoConfiguration.class);

        UriMongoConfiguration configuration = (UriMongoConfiguration) actual;
        assertThat(configuration)
                .hasFieldOrPropertyWithValue("dbName", "test")
                .hasFieldOrPropertyWithValue("storeNulls", false)
                .hasFieldOrPropertyWithValue("storeEmpties", false)
                .hasFieldOrPropertyWithValue("ignoreFinals", false)
                .hasFieldOrPropertyWithValue("useLowerCaseCollectionNames", false)
                .hasFieldOrPropertyWithValue("enableValidationExtension", false);

        assertThat(configuration.getUri().getURI()).isNotEmpty().isEqualTo("mongodb://localhost:1337");
    }
}
