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

package neoapp.fixture.scenario;

import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import neoapp.fixture.module.host.HostFixtureScript;
import neoapp.fixture.teardown.HostsTearDownFixture;

public class RecreateHostsScenarioFixture extends FixtureScript {

    public RecreateHostsScenarioFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //region > numberOfHosts
    private Integer numberOfHosts;
    @Property(optionality = Optionality.OPTIONAL)
    public Integer getNumberOfHosts() {
        return numberOfHosts;
    }
    public void setNumberOfHosts(final Integer numberOfHosts) {
        this.numberOfHosts = numberOfHosts;
    }
    //endregion


    @Override
    protected void execute(ExecutionContext executionContext) {

        int numberOfHosts = this.defaultParam("numberOfHosts", executionContext, fakeDataService.ints().between(3, 5));

        executionContext.executeChild(this, new HostsTearDownFixture());

        for (int i = 0; i < numberOfHosts; i++) {
            executionContext.executeChild(this, new HostFixtureScript());
        }
    }


    @javax.inject.Inject
    private FakeDataService fakeDataService;

}
