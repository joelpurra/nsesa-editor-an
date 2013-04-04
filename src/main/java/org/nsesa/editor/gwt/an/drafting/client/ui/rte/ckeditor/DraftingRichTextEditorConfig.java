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
package org.nsesa.editor.gwt.an.drafting.client.ui.rte.ckeditor;

import com.google.gwt.core.client.GWT;
import org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorToolbar;

import java.util.Arrays;

/**
 * The default configuration for Akomantoso20 editor
 * User: groza
 * Date: 14/01/13
 * Time: 14:47
 */
public class DraftingRichTextEditorConfig extends CKEditorConfig {

    public DraftingRichTextEditorConfig() {
        keepEmptyTag("span");
        setDraftingClassName("akomaNtoso-drafting");
        //configure the editor configuration
        setContentCss(Arrays.asList(new String[]{
                        GWT.getModuleBaseURL() + "../" + "css/editor.css",
                        GWT.getModuleBaseURL() + "../" + "css/document.css",
                        GWT.getModuleBaseURL() + "../" + "css/amendment.css",
                        GWT.getModuleBaseURL() + "../" + "css/akomaNtoso-all.css",
                        GWT.getModuleBaseURL() + "../" + "css/akomaNtoso-override.css",
                        GWT.getModuleBaseURL() + "../" + "css/akomaNtoso-display.css",
                        GWT.getModuleBaseURL() + "../" + "css/akomaNtoso-drafting.css",
                        GWT.getModuleBaseURL() + "../" + "css/ckeditor.css"}))
                .setBodyClass("akomaNtoso")
                .setRemovePlugins("elementspath")
                        //.setExtraPlugins("nsesa")
                .setToolbarLocation("bottom")
                .setResize_enabled(false)
                .setAutoParagraph(false)
                .setFillEmptyBlocks(false)
                .setForcePasteAsPlainText(true)
                .setToolbar(CKEditorToolbar.DEFAULT_TOOLBAR);
    }

}
