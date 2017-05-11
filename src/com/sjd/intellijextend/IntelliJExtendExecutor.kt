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

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.io.FileUtil.loadFile
import com.intellij.openapi.util.io.FileUtil.writeToFile
import com.sjd.intellijx.Balloons
import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.StringUtils.defaultString
import java.io.File

class IntelliJExtendExecutor(private val editor: Editor) {

    private val LOG = com.intellij.openapi.diagnostic.Logger.getInstance(IntelliJExtendExecutor::class.java)

    fun execute() {

        if (editor.project == null)
            return

        if (!IntelliJExtendComponent.openSettingsIfNotConfigured(editor.project!!))
            return

        try {

            createFilesAndExecuteCommand()

        } catch (e: Throwable) {
            LOG.info("Failed", e)
            balloon(MessageType.ERROR, "Failed: " + e.message)
        }
    }

    private fun createFilesAndExecuteCommand() {

        val buffer = writeBufferToTransferFile(editorBuffer)
        val selection = writeSelectionToTransferFile(editorSelection)

        val exitCode = executeCommand()

        if (!replaceBufferIfChanged(buffer))
            replaceSelectionIfChanged(selection)

        balloon(MessageType.INFO, "Run successfully (exit code = $exitCode).")
    }

    private var editorSelection: String
        get() = defaultString(editor.selectionModel.selectedText)
        set(newSelection) {
            editor.document.replaceString(
                    editor.selectionModel.selectionStart,
                    editor.selectionModel.selectionEnd,
                    rightLineEnded(newSelection))
        }

    private fun rightLineEnded(newSelection: String) = newSelection
            .replace("\r".toRegex(), "")

    private var editorBuffer: String
        get() = defaultString(editor.document.charsSequence.toString())
        set(newBuffer) = editor.document.setText(rightLineEnded(newBuffer))

    private fun executeCommand() = DefaultExecutor().execute(CommandLine.parse(buildCommand()))

    private fun replaceSelectionIfChanged(selection: String) {
        val newSelection = readSelectionFromTransferFile()
        if (newSelection != selection)
            editorSelection = newSelection
    }

    private fun replaceBufferIfChanged(buffer: String): Boolean {
        val newBuffer = readBufferFromTransferFile()
        if (newBuffer == buffer)
            return false

        editorBuffer = newBuffer
        return true
    }

    private fun readSelectionFromTransferFile() = defaultString(loadFile(selectionFile))

    private fun readBufferFromTransferFile() = defaultString(loadFile(bufferFile))

    private fun buildCommand() = IntelliJExtendComponent.command
            .replace("\$TransferPath$", transferPath.absolutePath)
            .replace("\$TransferBuffer$", bufferFile.absolutePath)
            .replace("\$TransferSelection$", selectionFile.absolutePath)

    private fun writeSelectionToTransferFile(selection: String) =
            writeToAndLoadFile(selectionFile, selection)

    private fun writeBufferToTransferFile(buffer: String) =
        writeToAndLoadFile(bufferFile, buffer)

    private fun writeToAndLoadFile(file: File, buffer: String): String {
        writeToFile(file, buffer)
        return loadFile(file)
    }

    private val selectionFile: File
        get() = File(transferPath.absolutePath + "/.selection")

    private val bufferFile: File
        get() = File(transferPath.absolutePath + "/.buffer")

    private val transferPath: File
        get() = File(nonBlankTransferPath)

    private val nonBlankTransferPath: String
        get() = if (StringUtils.isBlank(IntelliJExtendComponent.transferPath))
            defaultTransferPath
        else
            IntelliJExtendComponent.transferPath

    private val defaultTransferPath: String
        get() = File(FileUtil.getTempDirectory() + "/." + IntelliJExtend.ID).absolutePath

    private fun balloon(messageType: MessageType, message: String) {
        Balloons.show(editor.project!!, messageType, """<strong>${IntelliJExtend.ID}</strong>: $message""")
    }

}
