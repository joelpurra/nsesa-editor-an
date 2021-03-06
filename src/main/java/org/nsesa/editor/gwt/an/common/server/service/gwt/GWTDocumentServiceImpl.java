/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.an.common.server.service.gwt;

import com.inspiresoftware.lib.dto.geda.assembler.Assembler;
import com.inspiresoftware.lib.dto.geda.assembler.DTOAssembler;
import com.inspiresoftware.lib.dto.geda.assembler.dsl.impl.DefaultDSLRegistry;
import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.nsesa.editor.gwt.an.common.server.convertor.CalendarDateConvertor;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentService;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.server.dto.DocumentContentDTO;
import org.nsesa.server.service.api.DocumentContentService;
import org.nsesa.server.service.api.DocumentService;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 24/06/12 19:57
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GWTDocumentServiceImpl extends SpringRemoteServiceServlet implements GWTDocumentService {

    private DocumentService documentService;
    private DocumentContentService documentContentService;

    private Resource documentTemplate;

    private CalendarDateConvertor calendarDateConvertor;

    final Assembler documentAssembler = DTOAssembler.newAssembler(DocumentDTO.class, org.nsesa.server.dto.DocumentDTO.class);

    @Override
    public HashMap<String, String> getMetaInformation(final ClientContext clientContext, final String documentID) {
        return null;
    }

    @Override
    public DocumentDTO getDocument(final ClientContext clientContext, final String documentID) {
        final org.nsesa.server.dto.DocumentDTO fromServices = documentService.getDocument(documentID);
        DocumentDTO documentDTO = new DocumentDTO();
        documentAssembler.assembleDto(documentDTO, fromServices, getConvertors(), new DefaultDSLRegistry());
        return documentDTO;
    }

    @Override
    public String getDocumentFragment(final ClientContext clientContext, final String documentID, final String elementID) {
        return documentContentService.getDocumentFragment(documentID, elementID);
    }

    @Override
    public ArrayList<DocumentDTO> getAvailableTranslations(final ClientContext clientContext, final String documentID) {
        final List<org.nsesa.server.dto.DocumentDTO> availableTranslations = documentService.getAvailableTranslations(documentID);
        final List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        if (availableTranslations != null) {
            documentAssembler.assembleDtos(documentDTOs, availableTranslations, getConvertors(), new DefaultDSLRegistry());
            return new ArrayList<DocumentDTO>(documentDTOs);
        }
        return new ArrayList<DocumentDTO>();
    }

    @Override
    public ArrayList<DocumentDTO> getRelatedDocuments(ClientContext clientContext, String documentID) {
        final List<org.nsesa.server.dto.DocumentDTO> relatedDocuments = documentService.getRelatedDocuments(documentID);
        final List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        if (relatedDocuments != null) {
            documentAssembler.assembleDtos(documentDTOs, relatedDocuments, getConvertors(), new DefaultDSLRegistry());
            return new ArrayList<DocumentDTO>(documentDTOs);
        }
        return new ArrayList<DocumentDTO>();
    }

    @Override
    public org.nsesa.editor.gwt.core.shared.DocumentContentDTO getDocumentContent(final ClientContext clientContext, final String documentID) {
        final DocumentContentDTO fromBackend = documentContentService.getDocumentContent(documentID);

        final org.nsesa.editor.gwt.core.shared.DocumentContentDTO documentContentDTO =
                new org.nsesa.editor.gwt.core.shared.DocumentContentDTO();
        documentContentDTO.setDocumentID(fromBackend.getDocumentID());
        documentContentDTO.setDocumentContentType(fromBackend.getDocumentContentType());


        if ("XML".equalsIgnoreCase(fromBackend.getDocumentContentType())) {
            final InputSource inputSource;
            if (fromBackend.getContent() != null) {
                byte[] bytes = fromBackend.getContent().getBytes(Charset.forName("UTF-8"));
                inputSource = new InputSource(new ByteArrayInputStream(bytes));
            } else {
                throw new RuntimeException("Could not retrieve document content.");
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

                documentContentDTO.setContent(sw.toString());
                return documentContentDTO;

            } catch (IOException e) {
                throw new RuntimeException("Could not read file.", e);
            } catch (SAXException e) {
                throw new RuntimeException("Could not parse file.", e);
            } catch (ParserConfigurationException e) {
                throw new RuntimeException("Could not parse file.", e);
            } catch (TemplateException e) {
                throw new RuntimeException("Could not load template.", e);
            }
        } else if ("PLAIN".equalsIgnoreCase(fromBackend.getDocumentContentType())) {
            // no transformation at the moment
            documentContentDTO.setContent(fromBackend.getContent());
            return documentContentDTO;
        } else {
            throw new UnsupportedOperationException("Not yet implemented.");
        }
    }

    @Override
    public void saveDocumentContent(final ClientContext clientContext, final String documentID, final String content) {
        DocumentContentDTO documentContentDTO = new DocumentContentDTO();
        documentContentDTO.setDocumentID(documentID);
        documentContentDTO.setContent(content);
        documentContentService.saveDocumentContent(documentContentDTO);
    }

    // TODO replace with assemblers
    /*private DocumentDTO fromBackend(org.nsesa.server.dto.DocumentDTO fromServices) {
        if (fromServices == null) return null;

        final DocumentDTO document = new DocumentDTO();
        document.setDocumentID(fromServices.getDocumentID());
        document.setAmendable(fromServices.isAmendable());
        document.setName(fromServices.getName());
        document.setLanguageIso(fromServices.getLanguageIso());
        document.setDeadline(fromServices.getDeadline() != null ? new Date(fromServices.getDeadline().getTime().getTime()) : null);
        return document;
    }*/

    private Map<String, Object> getConvertors() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("calendarDateConvertor", calendarDateConvertor);
        return map;
    }

    // Spring setters ----------------------

    public void setDocumentTemplate(Resource documentTemplate) {
        this.documentTemplate = documentTemplate;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void setDocumentContentService(DocumentContentService documentContentService) {
        this.documentContentService = documentContentService;
    }

    public void setCalendarDateConvertor(CalendarDateConvertor calendarDateConvertor) {
        this.calendarDateConvertor = calendarDateConvertor;
    }
}
