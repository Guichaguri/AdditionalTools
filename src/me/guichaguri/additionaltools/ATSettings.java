package me.guichaguri.additionaltools;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import javax.swing.JComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

/**
 * @author Guilherme Chaguri
 */
public class ATSettings implements Configurable { // TODO: register?
    private ATSettingsForm form;

    @Nls
    @Override
    public String getDisplayName() {
        return "additionaltools";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null; // Maybe?
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        form = new ATSettingsForm();

        return form.createPanel(ATService.getInstance());
    }

    @Override
    public boolean isModified() {
        return form.isModified(ATService.getInstance());
    }

    @Override
    public void apply() throws ConfigurationException {
        form.save(ATService.getInstance());
    }

    @Override
    public void reset() {
        form.reset(ATService.getInstance());
    }

    @Override
    public void disposeUIResources() {
        form = null;
    }
}
