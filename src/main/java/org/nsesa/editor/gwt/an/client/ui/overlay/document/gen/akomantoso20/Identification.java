/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.an.client.ui.overlay.document.gen.akomantoso20;

import org.nsesa.editor.gwt.an.client.ui.overlay.document.gen.xmlschema.AnyURISimpleType;
import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import java.util.HashMap;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.gwt.user.client.DOM;

/**
* This file is generated. Rather than changing this file, correct the template called <tt>overlayClass.ftl</tt>.
*/

public class Identification extends OverlayWidgetImpl {
    private static Map<OverlayWidget, Occurrence> ALLOWED_SUB_TYPES = new HashMap<OverlayWidget, Occurrence>() {
        {
            put(new FRBRWork(), new Occurrence(1,1));
            put(new FRBRExpression(), new Occurrence(1,1));
            put(new FRBRManifestation(), new Occurrence(1,1));
            put(new FRBRItem(), new Occurrence(0,1));
        }
    };



    // STATIC create method
    public static Element create() {
        com.google.gwt.user.client.Element span = DOM.createSpan();
        span.setAttribute("type", "identification");
        span.setAttribute("ns", "http://www.akomantoso.org/2.0");
        span.setClassName("widget identification");
        return span;
    }

// CONSTRUCTORS ------------------
    public Identification() {
        super(create());
        setType("identification");
    }

    public Identification(Element element) {
        super(element);
    }

// FIELDS ------------------
    private AnyURISimpleType sourceAttr;

    public FRBRWork getFRBRWork() {
        FRBRWork result = null;
        for (OverlayWidget widget : getChildOverlayWidgets()) {
            if ("FRBRWork".equalsIgnoreCase(widget.getType())) {
                result = (FRBRWork)widget;
                break;
            }
         }
         return result;
    }
     //DSL Style get value already exists

     //DSL Style set value
    public FRBRWork setFRBRWork(FRBRWork FRBRWorkElem) {
        FRBRWork result = getFRBRWork();
        // remove the child of the same type if exist
        if (result != null) {
            this.removeOverlayWidget(result);
        }
        this.addOverlayWidget(FRBRWorkElem);

        return FRBRWorkElem;
    }
    public FRBRExpression getFRBRExpression() {
        FRBRExpression result = null;
        for (OverlayWidget widget : getChildOverlayWidgets()) {
            if ("FRBRExpression".equalsIgnoreCase(widget.getType())) {
                result = (FRBRExpression)widget;
                break;
            }
         }
         return result;
    }
     //DSL Style get value already exists

     //DSL Style set value
    public FRBRExpression setFRBRExpression(FRBRExpression FRBRExpressionElem) {
        FRBRExpression result = getFRBRExpression();
        // remove the child of the same type if exist
        if (result != null) {
            this.removeOverlayWidget(result);
        }
        this.addOverlayWidget(FRBRExpressionElem);

        return FRBRExpressionElem;
    }
    public FRBRManifestation getFRBRManifestation() {
        FRBRManifestation result = null;
        for (OverlayWidget widget : getChildOverlayWidgets()) {
            if ("FRBRManifestation".equalsIgnoreCase(widget.getType())) {
                result = (FRBRManifestation)widget;
                break;
            }
         }
         return result;
    }
     //DSL Style get value already exists

     //DSL Style set value
    public FRBRManifestation setFRBRManifestation(FRBRManifestation FRBRManifestationElem) {
        FRBRManifestation result = getFRBRManifestation();
        // remove the child of the same type if exist
        if (result != null) {
            this.removeOverlayWidget(result);
        }
        this.addOverlayWidget(FRBRManifestationElem);

        return FRBRManifestationElem;
    }
    public FRBRItem getFRBRItem() {
        FRBRItem result = null;
        for (OverlayWidget widget : getChildOverlayWidgets()) {
            if ("FRBRItem".equalsIgnoreCase(widget.getType())) {
                result = (FRBRItem)widget;
                break;
            }
         }
         return result;
    }
     //DSL Style get value already exists

     //DSL Style set value
    public FRBRItem setFRBRItem(FRBRItem FRBRItemElem) {
        FRBRItem result = getFRBRItem();
        // remove the child of the same type if exist
        if (result != null) {
            this.removeOverlayWidget(result);
        }
        this.addOverlayWidget(FRBRItemElem);

        return FRBRItemElem;
    }
    public AnyURISimpleType getSourceAttr() {
        if (sourceAttr == null) {
            sourceAttr = new AnyURISimpleType();
            sourceAttr.setValue(getElement().getAttribute("source"));
        }

        return sourceAttr;
     }
     //DSL Style get value
    public AnyURISimpleType sourceAttr() {
        return  getSourceAttr();
    }

    public void setSourceAttr(final AnyURISimpleType sourceAttr) {
        this.sourceAttr = sourceAttr;
    }
     //DSL Style set value
    public Identification sourceAttr(final AnyURISimpleType sourceAttr) {
        setSourceAttr(sourceAttr);
        return this;
    }
    //Override all attributes methods to be conformant with DSL approach

    /**
    * Returns possible children as a map of <tt>AmendableWidget, Occurrence</tt>s.
    */
    @Override
    public Map<OverlayWidget, Occurrence> getAllowedChildTypes() {
        return java.util.Collections.unmodifiableMap(ALLOWED_SUB_TYPES);
    }

/**
    * Returns the namespace URI of this amendable widget.
    */
    @Override
    public String getNamespaceURI() {
        return "http://www.akomantoso.org/2.0";
    }

    @Override
    public LinkedHashMap<String, String> getAttributes() {
        final LinkedHashMap<String, String> attrs = new LinkedHashMap<String, String>();
        attrs.putAll(super.getAttributes());
        attrs.put("source", getSourceAttr() != null ? getSourceAttr().getValue() : null);
        return attrs;
    }
}

