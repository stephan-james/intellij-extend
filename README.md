# IntelliJ eXtend

Powerful IntelliJ plugin for external buffer and selection manipulation.

### Installation

- Download the plugin bundle from [here](https://github.com/stephan-james/intellij-extend/files/991989/intellij-extend.zip "Download Plugin Bundle...").
- Open the "Settings/Preferences" dialog box and select "Plugins" on the left pane.
- On the right pane of the dialog, click the "Install plugin from disk" button.
- In the dialog that opens, select the downloaded plugin bundle.
- Click "Apply" button of the Settings/Preferences dialog.
- Following the system prompt that appears, restart IntelliJ IDEA to activate the installed plugin, or postpone it, at your choice. 

### General usage instructions

- Go into an editor.
- Select some text fragment.
- Press [control-alt-G] **twice**  or on a Mac use [command-option-G] **twice**.
- Alternatively use the action "Execute IntelliJ eXtend..." in the edit menu.
- The plugin will create two files in a temporary directory:
  - one with the complete current editor buffer (*.buffer*) and 
  - one with the current selection (*.selection*).
- The plugin then executes the external program you have configured.
- If your program changes the content of the .buffer file the complete editor content will be replaced by the content of this file.
- Otherwise if your program changes the content of the .selection file the current selection will be replaced by the content of that file.

That's all. Seems not to be too much. But believe me: used in the right way it becomes very powerful!

### Configuration

- Open the "Tools" section in IntelliJ preferences dialog and find the IntelliJ eXtend configuration tab. ![intellij-extend preferences](https://cloud.githubusercontent.com/assets/11229521/7433259/dcfe59c4-f02f-11e4-97ef-1fb2d517240c.png)
- In the field **"Command to execute"** fill in the command line of the program you want to be executed. This can be any arbitrary command which regularly can be used in an operating system shell too.
- There are three macros (argument variables) provided for this command line:
  - **$TransferPath$**: the absolute path of the transfer files directory
  - **$TransferBuffer$**: the absolute path of the buffer transfer file (*.buffer*)
  - **$TransferSelection$**: the absolute path of the selection transfer file (*.selection*)
- In the field **“Directory for transfer files”** you can specify the path to the directory in which the transfer files will be created. If you leave this field blank the plugin uses a directory named *“com.sjd.intellijextend”* within the temporary io directory.

