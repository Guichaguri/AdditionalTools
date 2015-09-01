package me.guichaguri.additionaltools;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import me.guichaguri.additionaltools.ATService.State;

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
        State state = service.getState();
        return importAll.isSelected() != state.importAllEnabled ||
                (docTime.getValue() instanceof Long && (Long)docTime.getValue() != state.documentationTime);
    }

    public void save(ATService service) {
        State state = service.getState();
        state.importAllEnabled = importAll.isSelected();
        System.out.println(state.importAllEnabled);
        System.out.println(docTime.getValue() + " - " + docTime.getValue().getClass().getName());
        if(docTime.getValue() instanceof Number) {
            state.documentationTime = ((Number)docTime.getValue()).longValue();
            System.out.println(state.documentationTime);
        }
    }

    public void reset(ATService service) {
        State state = service.getState();

        importAll.setSelected(state.importAllEnabled);
        docTime.setValue(state.documentationTime);
    }
}
