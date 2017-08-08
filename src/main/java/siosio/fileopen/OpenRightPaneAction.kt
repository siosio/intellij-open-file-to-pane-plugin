package siosio.fileopen

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.fileEditor.ex.*
import com.intellij.openapi.fileEditor.impl.*
import javax.swing.*

class OpenRightPaneAction : OpenPaneActionSupport() {

    override fun actionPerformed(e: AnActionEvent) {
        e.getData(CommonDataKeys.PROJECT)?.let { project ->
            val fileEditorManager = FileEditorManagerEx.getInstanceEx(project) ?: return
            val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

            if (fileEditorManager.isInSplitter) {
                fileEditorManager.windows[1].setAsCurrentWindow(true)
                fileEditorManager.openFile(file, true)
            } else {
                val window = e.getData(EditorWindow.DATA_KEY)
                fileEditorManager.createSplitter(SwingConstants.VERTICAL, window)

                val currentFile = fileEditorManager.currentFile
                if (currentFile == file) {
                    return
                }

                fileEditorManager.openFile(file, true)
                fileEditorManager.currentWindow!!.closeFile(currentFile)
            }
        }
    }

    override fun isEnabled(fileEditorManager: FileEditorManagerEx): Boolean {
        val splitCount = fileEditorManager.windowSplitCount
        if (splitCount != 1 && splitCount != 2) {
            return false
        }
        return if (splitCount == 1) {
            true
        } else {
            getSplitter(fileEditorManager)?.let {
                !it.getOrientation()
            } ?: false
        }
    }
}
