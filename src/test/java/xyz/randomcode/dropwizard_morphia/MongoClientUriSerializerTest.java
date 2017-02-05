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

import com.fasterxml.jackson.core.JsonGenerator;
import com.mongodb.MongoClientURI;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class MongoClientUriSerializerTest {

    @Test
    public void serializeURI() throws Exception {
        final String uriString = "mongodb://user:abc123@127.0.0.1:3344/somedb";

        MongoClientURI uri = new MongoClientURI(uriString);
        JsonGenerator generator = mock(JsonGenerator.class);

        MongoClientUriSerializer serializer = new MongoClientUriSerializer();
        serializer.serialize(uri, generator, null);

        verify(generator).writeString(eq(uriString));
    }
}
