package me.guichaguri.additionaltools;

import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author Guilherme Chaguri
 */
public class ProjectListener implements ProjectComponent {
    private Project proj;
    public ProjectListener(Project project) {
        this.proj = project;
    }

    public void initComponent() {}

    public void disposeComponent() {}

    @NotNull
    public String getComponentName() {
        return "AutoJavadoc";
    }

    public void projectOpened() {
        LookupManager lookup = LookupManager.getInstance(proj);
        lookup.addPropertyChangeListener(new AutoJavadoc());
    }

    public void projectClosed() {}
}
