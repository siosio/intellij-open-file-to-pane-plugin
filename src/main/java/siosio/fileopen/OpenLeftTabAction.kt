package siosio.fileopen

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.fileEditor.ex.*

class OpenLeftTabAction : OpenTabActionSupport() {

    override fun actionPerformed(e: AnActionEvent) {
        e.getData(CommonDataKeys.PROJECT)?.let {project ->
            val fileEditorManager = FileEditorManagerEx.getInstanceEx(project) ?: return
            val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
            fileEditorManager.getWindows()[0].setAsCurrentWindow(true)
            fileEditorManager.openFile(file, true)
        }
    }

    override fun isEnabled(fileEditorManager: FileEditorManagerEx): Boolean {
        if (fileEditorManager.getWindowSplitCount() != 2) {
            return false
        }
        return getSplitter(fileEditorManager)?.let {
            !it.orientation
        }?: false
    }
}

