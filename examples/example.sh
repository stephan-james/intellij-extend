#!/bin/sh

# Open the "Tools" section in IntelliJ preferences dialog and find the IntelliJ eXtend configuration tab.
# In the field "Command to execute" fill something like:
#
# /Users/james/intellij-extend/examples/example.sh $TransferPath$ $TransferBuffer$ $TransferSelection$
#
# Then switch into an editor and execute the action "Execute IntelliJ eXtend..." in the "Edit" menu or use the
# keys [control-alt-G] [control-alt-G] (on a Mac use [command-option-G] [command-option-G]).
#
# The current selection should be replace by something like this:
#
#  Fri May  8 15:61:93 CEST 2019
#  TransferPath      = /private/var/folders/2s/xHJhgvHG/T/.com.sjd.intellijextend
#  TransferBuffer    = /private/var/folders/2s/xHJhgvHG/T/.buffer
#  ...

ProjectFilePath=$1
FilePath=$2
TransferPath=$3
TransferBuffer=$4
TransferSelection=$5
CaretOffset=$6

date > ${TransferSelection}
echo "ProjectFilePath   = $ProjectFilePath" >> ${TransferSelection}
echo "FilePath          = $FilePath" >> ${TransferSelection}
echo "TransferPath      = $TransferPath" >> ${TransferSelection}
echo "TransferBuffer    = $TransferBuffer" >> ${TransferSelection}
echo "TransferSelection = $TransferSelection" >> ${TransferSelection}
echo "CaretOffset       = $CaretOffset" >> ${TransferSelection}
echo "-------------------------------------------------------------" >> ${TransferSelection}
echo "The rest is up to you :-)" >> ${TransferSelection}
