/*
 * Copyright (c) 2015, http://stephan-james.github.io/intellij-extend
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  Neither the name of the project nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sjd.intellijextend;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(
        name = IntelliJExtend.ID,
        storages = {
                @Storage(id = "default", file = StoragePathMacros.APP_CONFIG + "/" + IntelliJExtend.ID + ".xml")
        }
)
public class IntelliJExtendComponent implements ApplicationComponent, PersistentStateComponent<IntelliJExtendSettings> {

    private IntelliJExtendSettings settings = new IntelliJExtendSettings();

    public IntelliJExtendComponent() {
    }

    @Override
    public IntelliJExtendSettings getState() {
        return settings;
    }

    @Override
    public void loadState(IntelliJExtendSettings state) {
        XmlSerializerUtil.copyBean(state, settings);
    }


    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return IntelliJExtendComponent.class.getName();
    }

    public boolean openSettingsIfNotConfigured(Project project) {
        return getState().isValid() || ShowSettingsUtil.getInstance().editConfigurable(project, new IntelliJExtendConfiguration());
    }

    public static IntelliJExtendComponent getInstance() {
        return ApplicationManager.getApplication().getComponent(IntelliJExtendComponent.class);
    }

    public static String getPropertyValueCommand() {
        return getInstance().getState().getCommand();
    }

    public static String getPropertyValueTransferPath() {
        return getInstance().getState().getTransferPath();
    }

}
