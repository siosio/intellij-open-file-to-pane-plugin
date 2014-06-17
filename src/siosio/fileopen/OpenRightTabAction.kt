package siosio.fileopen

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.ui.Splitter
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.util.OpenSourceUtil
import javax.swing.SwingConstants
import com.intellij.openapi.fileEditor.impl.EditorWindow

public class OpenRightTabAction : OpenTabActionSupport() {


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

    if (fileEditorManager.isInSplitter()) {
      fileEditorManager.getWindows()[1].setAsCurrentWindow(true)
      fileEditorManager.openFile(file, true)
    } else {
      val window = e.getData(EditorWindow.DATA_KEY)
      fileEditorManager.createSplitter(SwingConstants.VERTICAL, window)

      val currentFile = fileEditorManager.getCurrentFile()
      if (currentFile == file) {
        return
      }

      fileEditorManager.openFile(file, true)
      fileEditorManager.getCurrentWindow()!!.closeFile(currentFile)
    }
  }

  override fun isEnabled(fileEditorManager: FileEditorManagerEx): Boolean {
    val splitCount = fileEditorManager.getWindowSplitCount()
    if (splitCount != 1 && splitCount != 2) {
      return false
    }
    return if (splitCount == 1) {
      true
    } else {
      !getSplitter(fileEditorManager).getOrientation()
    }
  }
}