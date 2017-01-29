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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mongodb.MongoClientURI;

import java.io.IOException;

public class MongoClientUriSerializer extends JsonSerializer<MongoClientURI> {

    @Override
    public void serialize(MongoClientURI value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if (value != null) {
            gen.writeString(value.getURI());
        }
    }
}
