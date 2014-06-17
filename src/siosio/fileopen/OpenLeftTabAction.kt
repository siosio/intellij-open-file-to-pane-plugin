package siosio.fileopen

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.util.OpenSourceUtil

public class OpenLeftTabAction : OpenTabActionSupport() {

  override fun actionPerformed(e: AnActionEvent?) {
    if (e == null) {
      return
    }
    val project = e.getData(CommonDataKeys.PROJECT)
    val fileEditorManager = FileEditorManagerEx.getInstanceEx(project)
    if (fileEditorManager == null) {
      return
    }
    val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
    if (file == null) {
      return
    }

    fileEditorManager.getWindows()[0].setAsCurrentWindow(true)
    fileEditorManager.openFile(file, true)
  }

  override fun isEnabled(fileEditorManager: FileEditorManagerEx): Boolean {
    val splitCount = fileEditorManager.getWindowSplitCount()
    if (splitCount != 2) {
      return false
    }
    return !getSplitter(fileEditorManager).getOrientation()
  }
}

