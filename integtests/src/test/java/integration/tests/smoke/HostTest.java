/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package integration.tests.smoke;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import dom.simple.Host;
import dom.simple.HostRepository;
import fixture.simple.scenario.RecreateHostsScenarioFixture;
import integration.tests.NeoAppIntegTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HostTest extends NeoAppIntegTest {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    HostRepository hostRepository;

    FixtureScript fixtureScript;

    public static class Title extends HostTest {

        @Test
        public void derivedFromName() throws Exception {

            // given
            fixtureScript = new RecreateHostsScenarioFixture();
            fixtureScripts.runFixtureScript(fixtureScript, null);

            // when
            final List<Host> hosts = hostRepository.listAll();

            // then
            for (Host host : hosts) {
                assertThat(host.getTitle(), is(host.getName()));

            }
        }

    }

}