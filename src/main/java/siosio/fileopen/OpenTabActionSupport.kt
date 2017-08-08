package siosio.fileopen

import com.intellij.ide.actions.BaseNavigateToSourceAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.ex.util.*
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.ui.Splitter

abstract class OpenTabActionSupport : BaseNavigateToSourceAction(true) {
    override fun update(e: AnActionEvent) {
        super.update(e)

        if (!e.presentation.isVisible || !e.presentation.isEnabled) {
            return
        }

        e.project?.let { project ->
            val fileEditorManager = FileEditorManagerEx.getInstanceEx(project) ?: return
            e.presentation.isEnabled = isEnabled(fileEditorManager)
        }
    }

    fun getSplitter(fileEditorManager: FileEditorManagerEx): Splitter? {
        val editorWindow = fileEditorManager.currentWindow
        val container = editorWindow?.tabbedPane?.component?.parent?.parent
        return when (container) {
            is Splitter -> container
            else -> null
        }
    }

    abstract fun isEnabled(fileEditorManager: FileEditorManagerEx): Boolean
}
