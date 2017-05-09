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

package com.sjd.intellijextend

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent

class IntelliJExtendConfiguration : SearchableConfigurable, Configurable.NoScroll {

    override fun getId(): String = IntelliJExtendConfiguration::class.java.name

    override fun getDisplayName() = IntelliJExtend.NAME + " Configuration"

    override fun createComponent(): JComponent? = form.mainPanel

    override fun isModified() = isCommandModified() || isTransferPathModified()

    override fun apply() {
        IntelliJExtendComponent.command = command()
        IntelliJExtendComponent.transferPath = transferPath()
    }

    override fun reset() {
        initValuesByState(form)
    }

    private fun isCommandModified() = IntelliJExtendComponent.command != command()

    private fun isTransferPathModified() = IntelliJExtendComponent.transferPath != transferPath()

    private fun transferPath(): String = form.transferPathField.text

    private fun command(): String = form.commandField.text

    private val form: IntelliJExtendConfigurationFormBase by lazy {
        val form = IntelliJExtendConfigurationForm()
        initValuesByState(form)
        form
    }

    private fun initValuesByState(form: IntelliJExtendConfigurationFormBase) {
        form.commandField.text = IntelliJExtendComponent.command
        form.transferPathField.text = IntelliJExtendComponent.transferPath
    }

}
