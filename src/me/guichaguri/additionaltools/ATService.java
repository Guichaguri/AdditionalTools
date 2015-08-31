package me.guichaguri.additionaltools;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import me.guichaguri.additionaltools.ATService.ATState;
import org.jetbrains.annotations.Nullable;

/**
 * @author Guilherme Chaguri
 */
@State(
    name = "ATService",
    storages = @Storage(file = StoragePathMacros.APP_CONFIG + "/additionaltools.xml")
)
public class ATService implements PersistentStateComponent<ATState> {

    public static ATService getInstance() {
        return ServiceManager.getService(ATService.class);
    }

    ATState state = new ATState();

    @Nullable
    @Override
    public ATState getState() {
        return state;
    }
    @Override
    public void loadState(ATState state) {
        this.state.importAllEnabled = state.importAllEnabled;
        this.state.documentationTime = state.documentationTime;
    }

    class ATState {
        public long documentationTime = 1000L;
        public boolean importAllEnabled = true;
    }
}
