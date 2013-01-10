package org.nsesa.editor.gwt.an.client.handler.delete;

import com.google.gwt.user.client.DOM;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.an.client.ui.overlay.document.gen.akomantoso20.*;
import org.nsesa.editor.gwt.an.client.ui.overlay.document.gen.xmlschema.StringSimpleType;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.author.AuthorPanelController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.content.ContentPanelController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.delete.AmendmentDialogDeleteController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.delete.AmendmentDialogDeleteView;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Date: 23/11/12 10:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AkomaNtoso20AmendmentDialogDeleteController extends AmendmentDialogDeleteController {

    private static final Logger LOG = Logger.getLogger(AkomaNtoso20AmendmentDialogDeleteController.class.getName());

    final AuthorPanelController authorPanelController;
    final ContentPanelController contentPanelController;

    @Inject
    public AkomaNtoso20AmendmentDialogDeleteController(final ClientFactory clientFactory,
                                                       final AmendmentDialogDeleteView view,
                                                       final Locator locator,
                                                       final OverlayFactory overlayFactory,
                                                       final AuthorPanelController authorPanelController,
                                                       final ContentPanelController contentPanelController
    ) {
        super(clientFactory, view, locator, overlayFactory, Arrays.asList(contentPanelController, authorPanelController));
        this.authorPanelController = authorPanelController;
        this.contentPanelController = contentPanelController;
    }

    @Override
    public void handleSave() {
        AmendableWidget amendableWidget = dialogContext.getAmendableWidget();

        final AkomaNtoso akomaNtoso = new AkomaNtoso();
        final Amendment root = akomaNtoso.setAmendment(new Amendment());

        // preface
        root.setPreface(new Preface())
                .addContainer(new Container())
                .addP(new P()).html(clientFactory.getClientContext().getPrincipal());

        // amendment body
        final AmendmentBody amendmentBody = root.setAmendmentBody(new AmendmentBody());

        final String languageIso = dialogContext.getDocumentController().getDocument().getLanguageIso();
        amendmentBody
                .addAmendmentHeading(new AmendmentHeading())
                .addBlock(new Block()).html(locator.getLocation(amendableWidget, null, languageIso, true));

        // amendment content
        final AmendmentContent amendmentContent = amendmentBody
                .addAmendmentContent(new AmendmentContent());

        amendmentContent
                .addBlock(new Block()).nameAttr(s("versionTitle")).html("Text proposed by ...");
        amendmentContent
                .addBlock(new Block()).nameAttr(s("versionTitle")).html("Amendment");

        final Mod mod = amendmentContent
                .addBlock(new Block()).nameAttr(s("changeBlock"))
                .addMod(new Mod());

        // original content
        final QuotedStructure quotedStructureOriginal = mod.addQuotedStructure(new QuotedStructure());

        final String originalText = contentPanelController.getView().getOriginalText();
        final com.google.gwt.user.client.Element cloneOriginal = DOM.clone(amendableWidget.asWidget().getElement(), false);
        cloneOriginal.setInnerHTML(originalText);

        final AmendableWidget overlayedOriginal = overlayFactory.getAmendableWidget(cloneOriginal);
        quotedStructureOriginal.addAmendableWidget(overlayedOriginal);

        // amendment content
        final QuotedStructure quotedStructureAmendment = mod.addQuotedStructure(new QuotedStructure());
        final com.google.gwt.user.client.Element clone = DOM.clone(amendableWidget.asWidget().getElement(), false);
        clone.setInnerHTML("Deleted");
        final AmendableWidget overlayed = overlayFactory.getAmendableWidget(clone);
        quotedStructureAmendment.addAmendableWidget(overlayed);

        akomaNtoso.addAmendableWidget(root);
        dialogContext.getAmendment().setRoot(akomaNtoso);

        super.handleSave();
    }

    private <T extends AmendableWidget> T a(final String tag) {
        return (T) overlayFactory.getAmendableWidget(tag);
    }

    private StringSimpleType s(final String text) {
        StringSimpleType s = new StringSimpleType();
        s.setValue(text);
        return s;
    }

    @Override
    public void setContext(DialogContext dialogContext) {
        super.setContext(dialogContext);
        if (dialogContext.getAmendmentController() != null) {
            // get the location from the amendable widget, if it is passed
            view.setTitle("Edit amendment");
        } else {
            view.setTitle(locator.getLocation(dialogContext.getAmendableWidget(), clientFactory.getClientContext().getDocumentIso(), false));
        }
    }
}