package siosio.fileopen

import com.intellij.ide.actions.BaseNavigateToSourceAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.ui.Splitter

abstract class OpenTabActionSupport  : BaseNavigateToSourceAction(true) {


  override fun update(e: AnActionEvent?) {
    if (e == null) {
      return
    }
    super.update(e)

    if (!e.getPresentation().isVisible() || !e.getPresentation().isEnabled()) {
      return
    }

    val project = e.getData(CommonDataKeys.PROJECT)
    val fileEditorManager = FileEditorManagerEx.getInstanceEx(project)

    if (fileEditorManager == null) {
      return
    }
    e.getPresentation().setEnabled(isEnabled(fileEditorManager))
  }

  fun getSplitter(fileEditorManager: FileEditorManagerEx):Splitter? {
    val editorWindow = fileEditorManager.getCurrentWindow()
    val container = editorWindow?.getTabbedPane()?.getComponent()?.getParent()?.getParent()
    return if (container is Splitter) {
      container as Splitter
    } else {
      null
    }
  }

  abstract fun isEnabled(fileEditorManager: FileEditorManagerEx):Boolean


}
