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

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;

import static com.intellij.openapi.util.io.FileUtil.loadFile;
import static com.intellij.openapi.util.io.FileUtil.writeToFile;
import static org.apache.commons.lang.StringUtils.defaultString;

public class IntelliJExtendExecutor {

    private static final Logger LOG = com.intellij.openapi.diagnostic.Logger.getInstance(IntelliJExtendExecutor.class);

    private Editor editor;

    public IntelliJExtendExecutor(Editor editor) {
        this.editor = editor;
    }

    public void execute() {

        if (!IntelliJExtendComponent.getInstance().openSettingsIfNotConfigured(editor.getProject()))
            return;

        if (editor.getSelectionModel().hasBlockSelection()) {
            balloon(IntelliJExtend.NAME + " is not enabled in block selection mode.", MessageType.WARNING);
            return;
        }

        try {
            createFilesAndExecuteCommand();
        } catch (Throwable e) {
            LOG.info("Failed", e);
            balloon("Failed: " + e.getMessage(), MessageType.ERROR);
        }
    }

    private void createFilesAndExecuteCommand() throws IOException {

        String buffer = getEditorBuffer();
        writeBufferToTransferFile(buffer);

        String selection = getEditorSelection();
        writeSelectionToTransferFile(selection);

        int exitCode = executeCommand();

        if (!replaceBufferIfChanged(buffer))
            replaceSelectionIfChanged(selection);

        balloon("Run successfully (exit code = " + exitCode + ").", MessageType.INFO);
    }

    private String getEditorSelection() {
        return defaultString(editor.getSelectionModel().getSelectedText());
    }

    private String getEditorBuffer() {
        return defaultString(editor.getDocument().getCharsSequence().toString());
    }

    private int executeCommand() throws IOException {
        String command = buildCommand();
        CommandLine commandLine = CommandLine.parse(command);
        return new DefaultExecutor().execute(commandLine);
    }

    private void replaceSelectionIfChanged(String selection) throws IOException {
        String newSelection = readSelectionFromTransferFile();
        if (!newSelection.equals(selection))
            setEditorSelection(newSelection);
    }

    private boolean replaceBufferIfChanged(String buffer) throws IOException {
        String newBuffer = readBufferFromTransferFile();
        if (newBuffer.equals(buffer))
            return false;

        setEditorBuffer(newBuffer);
        return true;
    }

    private void setEditorSelection(String newSelection) {
        editor.getDocument().replaceString(editor.getSelectionModel().getSelectionStart(), editor.getSelectionModel().getSelectionEnd(), newSelection);
    }

    private void setEditorBuffer(String newBuffer) {
        editor.getDocument().setText(newBuffer);
    }

    private String readSelectionFromTransferFile() throws IOException {
        return defaultString(loadFile(getSelectionFile()));
    }

    private String readBufferFromTransferFile() throws IOException {
        return defaultString(loadFile(getBufferFile()));
    }

    private String buildCommand() {
        return IntelliJExtendComponent.getPropertyValueCommand()
                .replace("$TransferPath$", getTransferPath().getAbsolutePath())
                .replace("$TransferBuffer$", getBufferFile().getAbsolutePath())
                .replace("$TransferSelection$", getSelectionFile().getAbsolutePath());
    }

    private void writeSelectionToTransferFile(String selection) throws IOException {
        writeToFile(getSelectionFile(), selection);
    }

    private void writeBufferToTransferFile(String buffer) throws IOException {
        writeToFile(getBufferFile(), buffer);
    }

    private File getTransferPath() {
        return new File(getNonBlankTransferPath());
    }

    private File getSelectionFile() {
        return new File(getTransferPath().getAbsolutePath() + "/.selection");
    }

    private File getBufferFile() {
        return new File(getTransferPath().getAbsolutePath() + "/.buffer");
    }

    public String getNonBlankTransferPath() {
        String transferPath = IntelliJExtendComponent.getPropertyValueTransferPath();
        return StringUtils.isBlank(transferPath) ?
                defaultTransferPath() :
                transferPath;
    }

    public static String defaultTransferPath() {
        return new File(FileUtil.getTempDirectory() + "/." + IntelliJExtend.ID).getAbsolutePath();
    }

    private void balloon(String message, MessageType messageType) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(editor.getProject());
        RelativePoint relativePoint = RelativePoint.getCenterOf(statusBar.getComponent());
        String balloonText = "<strong>" + IntelliJExtend.NAME + "</strong>: " + message;
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(balloonText, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(relativePoint, Balloon.Position.atRight);
    }

}
