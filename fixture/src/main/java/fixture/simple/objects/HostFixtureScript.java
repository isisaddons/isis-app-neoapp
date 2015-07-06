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

package fixture.simple.objects;

import java.util.List;

import com.google.common.collect.Iterables;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import dom.simple.Host;
import dom.simple.HostsMenu;
import dom.simple.IpAddress;

public class HostFixtureScript extends FixtureScript {

    //region > name
    private String name;
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    //region > numberOfAddresses
    private Integer numberAddresses;
    @Property(optionality = Optionality.OPTIONAL)
    public Integer getNumberAddresses() {
        return numberAddresses;
    }
    public void setNumberAddresses(final Integer numberAddresses) {
        this.numberAddresses = numberAddresses;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext executionContext) {

        final String name = this.defaultParam("name", executionContext, fakeDataService.strings().fixed(8).replace(' ', '_').toLowerCase());
        final int numberAddresses = this.defaultParam("numberAddresses", executionContext, fakeDataService.ints().between(0, 5));

        final Host host = hostsMenu.create(name);

        for (int i = 0; i < numberAddresses; i++) {
            final String address = unusedRandomAddress();
            host.addIpAddress(address);
        }

        executionContext.addResult(this, host);
    }

    private String unusedRandomAddress() {
        String address = randomAddress();
        while(inUse(address)) {
            address = randomAddress();
        }
        return address;
    }

    private boolean inUse(final String address) {
        final List<IpAddress> ipAddresses = container.allInstances(IpAddress.class);
        return Iterables.any(ipAddresses, ipAddress -> ipAddress.getAddress().equals(address));
    }

    private String randomAddress() {
        return "192.168." + fakeDataService.ints().between(0, 254) + "." + fakeDataService.ints().between(0, 254);
    }

    @javax.inject.Inject
    private DomainObjectContainer container;
    @javax.inject.Inject
    private HostsMenu hostsMenu;
    @javax.inject.Inject
    private FakeDataService fakeDataService;


}
