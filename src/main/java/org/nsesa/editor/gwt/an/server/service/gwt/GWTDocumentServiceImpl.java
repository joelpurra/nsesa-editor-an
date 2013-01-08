package org.nsesa.editor.gwt.an.server.service.gwt;

import com.google.common.io.Files;
import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentService;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 24/06/12 19:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GWTDocumentServiceImpl extends SpringRemoteServiceServlet implements GWTDocumentService {

    private Map<String, Resource> documents;
    private Resource documentTemplate;

    @Override
    public HashMap<String, String> getMetaInformation(final ClientContext clientContext, final String documentID) {
        return null;
    }

    @Override
    public DocumentDTO getDocument(final ClientContext clientContext, final String documentID) {
        final DocumentDTO document = new DocumentDTO();
        document.setDocumentID(documentID);
        document.setAmendable(true);
        document.setName("Document " + documentID);
        document.setLanguageIso("EN");
        document.setDeadline(new Date(new Date().getTime() + (60 * 60 * 1000)));
        return document;
    }

    @Override
    public String getDocumentFragment(final ClientContext clientContext, final String documentID, final String elementID) {
        return null;
    }

    @Override
    public ArrayList<DocumentDTO> getAvailableTranslations(final ClientContext clientContext, final String documentID) {
        ArrayList<DocumentDTO> translations = new ArrayList<DocumentDTO>();

        final DocumentDTO documentDTO1 = new DocumentDTO();
        documentDTO1.setLanguageIso("EN");
        documentDTO1.setName("English");
        documentDTO1.setDocumentID("1");
        translations.add(documentDTO1);

        final DocumentDTO documentDTO2 = new DocumentDTO();
        documentDTO2.setLanguageIso("FR");
        documentDTO2.setName("French");
        documentDTO2.setDocumentID("2");
        translations.add(documentDTO2);

        final DocumentDTO documentDTO3 = new DocumentDTO();
        documentDTO3.setLanguageIso("DE");
        documentDTO3.setName("German");
        documentDTO3.setDocumentID("3");
        translations.add(documentDTO3);

        return translations;
    }

    @Override
    public ArrayList<DocumentDTO> getRelatedDocuments(ClientContext clientContext, String documentID) {
        ArrayList<DocumentDTO> related = new ArrayList<DocumentDTO>();

        final DocumentDTO documentDTO1 = new DocumentDTO();
        documentDTO1.setLanguageIso("EN");
        documentDTO1.setName("Related 1");
        documentDTO1.setDocumentID("4");
        related.add(documentDTO1);

        final DocumentDTO documentDTO2 = new DocumentDTO();
        documentDTO2.setLanguageIso("EN");
        documentDTO2.setName("Related 2");
        documentDTO2.setDocumentID("5");
        related.add(documentDTO2);

        return related;
    }

    @Override
    public String getDocumentContent(final ClientContext clientContext, final String documentID) {
        final Resource documentResource = documents.get(documentID);
        final InputSource inputSource;
        if (documentResource != null) {
            /*

            -- When requiring XML transformations --
            */
            try {
                byte[] bytes = Files.toByteArray(documentResource.getFile());
                inputSource = new InputSource(new ByteArrayInputStream(bytes));

            } catch (IOException e) {
                throw new RuntimeException("Could not read file.", e);
            }

//            try {
//                return Files.toString(documentResource.getFile(), Charset.forName("UTF-8"));
//            } catch (IOException e) {
//                throw new RuntimeException("Could not read file resource.", e);
//            }
        } else {
            inputSource = new InputSource(documentID);
        }

        try {
            final NodeModel model = NodeModel.parse(inputSource);
            final Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setDirectoryForTemplateLoading(documentTemplate.getFile().getParentFile());
            final StringWriter sw = new StringWriter();
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("doc", model);
            configuration.getTemplate(documentTemplate.getFile().getName()).process(root, sw);
            return sw.toString();

        } catch (IOException e) {
            throw new RuntimeException("Could not read file.", e);
        } catch (SAXException e) {
            throw new RuntimeException("Could not parse file.", e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Could not parse file.", e);
        } catch (TemplateException e) {
            throw new RuntimeException("Could not load template.", e);
        }
    }

    // Spring setters ----------------------


    public void setDocuments(final Map<String, Resource> documents) {
        this.documents = documents;
    }

    public void setDocumentTemplate(Resource documentTemplate) {
        this.documentTemplate = documentTemplate;
    }
}
