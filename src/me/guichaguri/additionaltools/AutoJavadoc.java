package me.guichaguri.additionaltools;

import com.intellij.codeInsight.documentation.DocumentationComponent;
import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.codeInsight.lookup.Lookup;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupEvent;
import com.intellij.codeInsight.lookup.LookupListener;
import com.intellij.codeInsight.lookup.impl.LookupImpl;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.PsiElement;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.Alarm;
import com.intellij.util.Alarm.ThreadToUse;
import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import javax.swing.JComponent;

/**
 * @author Guilherme Chaguri
 */
public class AutoJavadoc implements PropertyChangeListener {
    private static Long time = null;
    protected static Alarm alarm = new Alarm(ThreadToUse.SWING_THREAD);

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        Object o = event.getNewValue();
        if(o != null && o instanceof Lookup) {
            ((Lookup)o).addLookupListener(new AutoJavadocLookup());
        }
    }

    public static class AutoJavadocLookup implements LookupListener {
        JBPopup popup = null;
        HashMap<String, DocumentationComponent> hs = new HashMap<String, DocumentationComponent>();

        @Override
        public void itemSelected(LookupEvent event) {
            if(popup != null) {
                popup.cancel();
                popup = null;
            }
            alarm.cancelAllRequests();
        }

        @Override
        public void lookupCanceled(LookupEvent event) {
            if(popup != null) {
                popup.cancel();
                popup = null;
            }
            alarm.cancelAllRequests();
        }

        @Override
        public void currentItemChanged(LookupEvent event) {
            if(popup != null) {
                popup.cancel();
                popup = null;
            }
            alarm.cancelAllRequests();

            final Lookup lookup = event.getLookup();

            final LookupElement item = lookup.getCurrentItem();
            if(item == null) return;

            if(time == null) {
                time = ATService.getInstance().getState().documentationTime;
            }

            alarm.addRequest(new Runnable() {
                @Override
                public void run() {
                    String key = item.getLookupString();
                    DocumentationComponent comp;

                    if(hs.containsKey(key)) {
                        comp = hs.get(key);
                    } else {
                        PsiElement e = item.getPsiElement();
                        if(e == null) return;

                        DocumentationManager manager = DocumentationManager.getInstance(lookup.getEditor().getProject());
                        comp = new DocumentationComponent(manager);
                        manager.queueFetchDocInfo(e, comp);
                        hs.put(key, comp);
                    }

                    JComponent j = ((LookupImpl)lookup).getComponent();
                    if((!j.isValid()) || (!j.isVisible())) return;
                    ComponentPopupBuilder b = JBPopupFactory.getInstance().createComponentPopupBuilder(comp, j);
                    popup = b.createPopup();
                    Dimension dim = new Dimension(j.getWidth(), 200);
                    popup.setMinimumSize(dim);
                    popup.setSize(dim);
                    popup.show(new RelativePoint(j, new Point(0, -200 - lookup.getEditor().getLineHeight())));
                }
            }, time);
        }

    }

}