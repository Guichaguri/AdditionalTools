package me.guichaguri.additionaltools;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;

/**
 * @author Guilherme Chaguri
 */
public class ImportAllAction extends AnAction {

    public void actionPerformed(AnActionEvent event) {
        ImportAll.importAll(event.getProject(), FileEditorManager.getInstance(event.getProject()).getSelectedTextEditor());
    }

}
