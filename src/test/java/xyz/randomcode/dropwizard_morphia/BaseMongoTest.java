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

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.AfterClass;
import org.junit.BeforeClass;


public class BaseMongoTest {

    private static final MongodStarter STARTER = MongodStarter.getDefaultInstance();

    protected static int port;

    private static MongodProcess process;


    @BeforeClass
    public static void configureMongo() throws Exception {
        port = Network.getFreeServerPort();

        IMongodConfig config = new MongodConfigBuilder()
                .version(Version.Main.V3_2)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        MongodExecutable executable = STARTER.prepare(config);
        process = executable.start();
    }


    @AfterClass
    public static void stopMongo() throws Exception {
        process.stop();
    }
}
