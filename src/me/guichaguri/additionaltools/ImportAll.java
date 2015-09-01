package me.guichaguri.additionaltools;

import com.intellij.codeInsight.daemon.impl.actions.AddImportAction;
import com.intellij.codeInsight.daemon.impl.quickfix.ImportClassFix;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.util.containers.WeakList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * @author Guilherme Chaguri
 */
public class ImportAll extends ImportClassFix {
    private static final WeakList<ImportAll> importAlls = new WeakList<ImportAll>();

    public static void importAll(Project project, Editor editor) {
        Iterator<ImportAll> i = importAlls.iterator();
        while(i.hasNext()) {
            ImportAll all = i.next();
            if(all == null) continue;
            if(!all.element.isValid()) {
                i.remove();
                continue;
            }
            List<PsiClass> cl = all.getClassesToImport();
            if(cl.isEmpty()) continue;
            PsiClass[] classes = new PsiClass[1];
            classes[0] = cl.get(0);
            i.remove();
            final AddImportAction action = all.createAddImportAction(classes, project, editor);
            CommandProcessor.getInstance().runUndoTransparentAction(new Runnable() {
                public void run() {
                    action.execute();
                }
            });
        }
    }

    protected PsiJavaCodeReferenceElement element;
    public ImportAll(PsiJavaCodeReferenceElement e) {
        super(e);
        this.element = e;
        importAlls.add(this);
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Import All";
    }

    @NotNull
    @Override
    public String getText() {
        return "Import All";
    }

    @Override
    public void invoke(Project project, Editor editor, PsiFile file) {
        importAll(project, editor);
    }


    public static class ImportAllAction extends AnAction {
        private Boolean importAll = null;

        public void actionPerformed(AnActionEvent event) {
            if(importAll == null) {
                importAll = ATService.getInstance().getState().importAllEnabled;
            }
            if(!importAll) return;
            ImportAll.importAll(event.getProject(), FileEditorManager.getInstance(event.getProject()).getSelectedTextEditor());
        }

    }
}
