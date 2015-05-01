# intellij-extend
## Powerful IntelliJ plugin for external buffer and selection manipulation.

### General usage instructions

- Go into an editor.
- Select some text fragment.
- Press [control-alt-G] *twice*  or on a Mac use [command-option-G] *twice*.
- Alternativly use the action "Execute IntelliJ eXtend..." in the edit menu.
- The plugin will create two files in a temporary directory:
  - one with the complete current editor buffer (*.buffer*) and 
  - one with the current selection (*.selection*).
- The plugin then executes the external program you have configured.
- If your program changes the content of the .buffer file the complete editor content will be replaced by the content of this file.
- Otherwise if your programm changes the content of the .selection file the current selection will be replaced by the content of that file.

That's all. Seems not to be too much. But believe me: used in the right way it becomes very powerful!

![intellij-extend preferences](https://cloud.githubusercontent.com/assets/11229521/7433259/dcfe59c4-f02f-11e4-97ef-1fb2d517240c.png)
