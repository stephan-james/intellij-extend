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

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IntelliJExtendConfiguration implements SearchableConfigurable, Configurable.NoScroll {

    private IntelliJExtendConfigurationFormBase form;

    @Nls
    @Override
    public String getDisplayName() {
        return IntelliJExtend.NAME + " Configuration";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (form == null) {
            form = new IntelliJExtendConfigurationForm();
            initValuesByState();
        }
        return form.mainPanel;
    }


    @Override
    public boolean isModified() {
        return !(IntelliJExtendComponent.getPropertyValueCommand().equals(getFieldValueCommand())
                && IntelliJExtendComponent.getPropertyValueTransferPath().equals(getFieldValueTransferPath()));
    }

    private String getFieldValueTransferPath() {
        return form.transferPathField.getText();
    }

    private String getFieldValueCommand() {
        return form.commandField.getText();
    }

    @Override
    public void apply() throws ConfigurationException {
        IntelliJExtendComponent.getInstance().getState().setCommand(getFieldValueCommand());
        IntelliJExtendComponent.getInstance().getState().setTransferPath(getFieldValueTransferPath());
    }

    @Override
    public void reset() {
        if (form == null)
            return;
        initValuesByState();
    }

    private void initValuesByState() {
        form.commandField.setText(IntelliJExtendComponent.getPropertyValueCommand());
        form.transferPathField.setText(IntelliJExtendComponent.getPropertyValueTransferPath());
    }

    @Override
    public void disposeUIResources() {
    }

    @NotNull
    @Override
    public String getId() {
        return IntelliJExtendConfiguration.class.getName();
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

}
