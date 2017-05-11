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
rem  Fri May  8 15:61:93 CEST 2019
rem  TransferPath      = /private/var/folders/2s/xHJhgvHG/T/.com.sjd.intellijextend
rem  TransferBuffer    = /private/var/folders/2s/xHJhgvHG/T/.buffer
rem  ...

set TransferPath=%1
set TransferBuffer=%2
set TransferSelection=%3

date /t > %TransferSelection%
time /t >> %TransferSelection%
echo "TransferPath      = %TransferPath%" >> %TransferSelection%
echo "TransferBuffer    = %TransferBuffer%" >> %TransferSelection%
echo "TransferSelection = %TransferSelection%" >> %TransferSelection%
echo "-------------------------------------------------------------" >> %TransferSelection%
echo "The rest is up to you :-)" >> %TransferSelection%
