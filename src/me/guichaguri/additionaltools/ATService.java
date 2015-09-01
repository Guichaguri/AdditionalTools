package me.guichaguri.additionaltools;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import me.guichaguri.additionaltools.ATService.State;
import org.jetbrains.annotations.Nullable;

/**
 * @author Guilherme Chaguri
 */
@com.intellij.openapi.components.State(
    name = "ATService",
    storages = @Storage(file = StoragePathMacros.APP_CONFIG + "/additionaltools.xml")
)
public class ATService implements PersistentStateComponent<State> {

    public static ATService getInstance() {
        return ServiceManager.getService(ATService.class);
    }

    State state = new State();

    @Nullable
    @Override
    public State getState() {
        return state;
    }
    @Override
    public void loadState(State state) {
        this.state = state;
    }

    public static class State {
        public long documentationTime = 1000L;
        public boolean importAllEnabled = true;
    }
}
