package org.nsesa.editor.gwt.an.client.ui.overlay.document.gen.akomantoso20;

import org.nsesa.editor.gwt.an.client.ui.overlay.document.gen.akomantoso20.MetaoptComplexType;
import org.nsesa.editor.gwt.an.client.ui.overlay.document.gen.xmlschema.StringSimpleType;
import com.google.gwt.dom.client.Element;
import java.util.ArrayList;
import java.util.Arrays;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import java.util.LinkedHashMap;

/**
* This file is generated. Rather than changing this file, correct the template called <tt>overlayClass.ftl</tt>.
*/
public class ValueTypeComplexType extends MetaoptComplexType  {

// CONSTRUCTORS ------------------

    public ValueTypeComplexType(Element element) {
        super(element);
    }

// FIELDS ------------------
    private StringSimpleType valueAttr;
    public StringSimpleType getValueAttr() {
        if (valueAttr == null) {
            valueAttr = new StringSimpleType();
            valueAttr.setValue(amendableElement.getAttribute("value"));
        }

        return valueAttr;
    }
    public void setValueAttr(final StringSimpleType valueAttr) {
        this.valueAttr = valueAttr;
    }
    /**
    * Returns possible children as list of String
    */
    @Override
    public String[] getAllowedChildTypes() {
        return new String[]{};
    }

    @Override
    public LinkedHashMap<String, String> getAttributes() {
        LinkedHashMap attrs = new LinkedHashMap();
        attrs.putAll(super.getAttributes());
        attrs.put("valueAttr", getValueAttr().getValue());

        return attrs;
    }

}
