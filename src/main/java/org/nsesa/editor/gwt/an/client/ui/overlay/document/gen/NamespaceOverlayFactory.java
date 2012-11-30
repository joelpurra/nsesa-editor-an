package org.nsesa.editor.gwt.an.client.ui.overlay.document.gen;

import com.google.gwt.dom.client.Element;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.DefaultOverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayStrategy;

import java.util.logging.Logger;
/**
* Note: this file is generated. Rather than changing this file, correct the template called <tt>overlayFactory.ftl</tt>.
*/
public class NamespaceOverlayFactory extends DefaultOverlayFactory  {

    private final static Logger LOG = Logger.getLogger(NamespaceOverlayFactory.class.getName());
    // the namespace of the overlay factory
    private final String namespace = "http://www.w3.org/XML/1998/namespace";

    @Inject
    public NamespaceOverlayFactory(final OverlayStrategy overlayStrategy, final ClientFactory clientFactory) {
        super(overlayStrategy, clientFactory);
    }
    @Override
    public String getNamespace() {
        return namespace;
    }
    @Override
    public AmendableWidget toAmendableWidget(final Element element) {
        final String nodeName = overlayStrategy.getType(element);
        final String namespaceURI = overlayStrategy.getNamespaceURI(element);

        if ("".equals(nodeName)) {
            throw new IllegalArgumentException("Empty element or null passed.");
        }
        // nothing found
        LOG.warning("Could not find overlay element (nodename: " + nodeName + " in namespace URI '" + namespaceURI + "')");
        return null;
    }
}