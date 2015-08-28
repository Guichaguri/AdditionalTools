package me.guichaguri.additionaltools;

import com.intellij.codeInsight.daemon.QuickFixActionRegistrar;
import com.intellij.codeInsight.quickfix.UnresolvedReferenceQuickFixProvider;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author Guilherme Chaguri
 */
public class QuickFixer extends UnresolvedReferenceQuickFixProvider<PsiJavaCodeReferenceElement> {


    @Override
    public void registerFixes(PsiJavaCodeReferenceElement ref, QuickFixActionRegistrar registrar) {
        registrar.register(new ImportAll(ref));
    }

    @NotNull
    @Override
    public Class<PsiJavaCodeReferenceElement> getReferenceClass() {
        return PsiJavaCodeReferenceElement.class;
    }
}
