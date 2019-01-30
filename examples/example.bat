rem Open the "Tools" section in IntelliJ preferences dialog and find the IntelliJ eXtend configuration tab.
rem In the field "Command to execute" fill something like:
rem
rem C:\windows\system32\cmd.exe /C C:\Users\james\example.bat $TransferPath$ $TransferBuffer$ $TransferSelection$
rem
rem Then switch into an editor and execute the action "Execute IntelliJ eXtend..." in the "Edit" menu or use the
rem keys [control-alt-G] [control-alt-G] (on a Mac use [command-option-G] [command-option-G]).
rem
rem The current selection should be replace by something like this:
rem
rem  29.01.2019
rem  13:40
rem  "ProjectFilePath   = C:\Users\james\IdeaProjects\MyProject\.idea\misc.xml"
rem  "FilePath          = C:\Users\james\IdeaProjects\MyProject\test.txt"
rem  "TransferPath      = C:\Users\james\AppData\Local\Temp\.com.sjd.intellijextend"
rem  "TransferBuffer    = C:\Users\james\AppData\Local\Temp\.com.sjd.intellijextend\.buffer"
rem  "TransferSelection = C:\Users\james\AppData\Local\Temp\.com.sjd.intellijextend\.selection"
rem  "-------------------------------------------------------------"
rem  "The rest is up to you :-)"
rem  ...

set ProjectFilePath=%1
set FilePath=%2
set TransferPath=%3
set TransferBuffer=%4
set TransferSelection=%5
set CaretOffset=%6

date /t > %TransferSelection%
time /t >> %TransferSelection%
echo "ProjectFilePath   = %ProjectFilePath%" >> %TransferSelection%
echo "FilePath          = %FilePath%" >> %TransferSelection%
echo "TransferPath      = %TransferPath%" >> %TransferSelection%
echo "TransferBuffer    = %TransferBuffer%" >> %TransferSelection%
echo "TransferSelection = %TransferSelection%" >> %TransferSelection%
echo "CaretOffset       = %CaretOffset%" >> %TransferSelection%
echo "-------------------------------------------------------------" >> %TransferSelection%
echo "The rest is up to you :-)" >> %TransferSelection%
