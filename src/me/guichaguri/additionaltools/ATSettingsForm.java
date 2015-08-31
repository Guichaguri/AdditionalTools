package me.guichaguri.additionaltools;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import me.guichaguri.additionaltools.ATService.ATState;

/**
 * @author Guilherme Chaguri
 */
public class ATSettingsForm {
    private JPanel wholePanel;
    private JCheckBox importAll;
    private JSpinner docTime;

    public JPanel createPanel(ATService service) {

        reset(service);

        return wholePanel;
    }

    public boolean isModified(ATService service) {
        ATState state = service.getState();
        return importAll.isSelected() != state.importAllEnabled ||
                (docTime.getValue() instanceof Long && (Long)docTime.getValue() != state.documentationTime);
    }

    public void save(ATService service) {
        ATState state = service.getState();
        state.importAllEnabled = importAll.isSelected();
        if(docTime.getValue() instanceof Long) {
            state.documentationTime = (Long)docTime.getValue();
        }
    }

    public void reset(ATService service) {
        ATState state = service.getState();

        importAll.setSelected(state.importAllEnabled);
        docTime.setValue(state.documentationTime);
    }
}
