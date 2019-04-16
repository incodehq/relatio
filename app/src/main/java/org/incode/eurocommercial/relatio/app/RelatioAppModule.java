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
package org.incode.eurocommercial.relatio.app;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.excel.ExcelModule;
import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;

import org.incode.eurocommercial.relatio.module.aspect.RelatioAspectModule;
import org.incode.eurocommercial.relatio.module.base.RelatioBaseModule;
import org.incode.eurocommercial.relatio.module.event.RelatioEventModule;
import org.incode.eurocommercial.relatio.module.profile.RelatioProfileModule;
import org.incode.module.settings.SettingsModule;

@XmlRootElement(name = "module")
public class RelatioAppModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new RelatioBaseModule(),
                new RelatioAspectModule(),
                new RelatioEventModule(),
                new RelatioProfileModule(),
                new SecurityModule(),
                new ExcelModule(),
                new SettingsModule(),
                new PasswordEncryptionServiceUsingJBcrypt.Module()
                );
    }
}
