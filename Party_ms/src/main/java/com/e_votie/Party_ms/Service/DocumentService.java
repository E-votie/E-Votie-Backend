package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Document;
import com.e_votie.Party_ms.Model.Party;
import com.e_votie.Party_ms.Repository.DocumentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class DocumentService {

    @Autowired
    private FileMsClient fileMsClient;

    @Autowired
    private DocumentRepository documentRepository;

    public Document createAndSaveDocument(MultipartFile file, Party party) {
        try {
            // Generate a unique file name including the original name
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            String baseFileName = "";
            if (originalFileName != null) {
                int dotIndex = originalFileName.lastIndexOf(".");
                if (dotIndex != -1) {
                    fileExtension = originalFileName.substring(dotIndex);
                    baseFileName = originalFileName.substring(0, dotIndex);
                } else {
                    baseFileName = originalFileName;
                }
            }

            String uniqueFileName = baseFileName + "_" + UUID.randomUUID() + fileExtension;

            // Split the base name by underscore (_)
            String[] parts = baseFileName.split("_");

            // Rename the file (create a new MultipartFile with the updated name)
            MultipartFile renamedFile = new MockMultipartFile(
                    uniqueFileName,
                    uniqueFileName,
                    file.getContentType(),
                    file.getInputStream()
            );

            // Upload the file using Feign client
            ResponseEntity<String> response = fileMsClient.uploadFile(renamedFile);

            if (response.getStatusCode().is2xxSuccessful()) {
                String documentUrl = fileMsClient.getFileUrl(uniqueFileName);

                // Create and populate the Document object
                Document document = new Document();
                document.setDocumentName(uniqueFileName);
                document.setDocumentUrl(documentUrl);
                document.setDocumentType(parts[1]);
                document.setDocumentUploadedDate(LocalDateTime.now().toString());
                document.setParty(party);

                // Save the Document and return it
                return documentRepository.save(document);
            } else {
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename());
            }
        } catch (Exception e) {
            log.error("Error uploading file or saving document: ", e);
            throw new RuntimeException("Document upload failed: " + file.getOriginalFilename(), e);
        }
    }

    @Transactional
    public void deleteDocument(Party party, String fileType) {
        try{
            // Delete existing documents with the same party and document type
            documentRepository.deleteByPartyAndDocumentType(party.getRegistrationId(), fileType);
        } catch (Exception e) {
            log.error("Error deleting document for party: " + fileType  + party.getRegistrationId(), e);
            throw new RuntimeException("Document deletion failed for party: " + fileType + party.getRegistrationId(), e);
        }
    }

    public String getDocumentUrl(String fileName) {
        try {
            // Call the file management service client to get the URL for the file
            String documentUrl = fileMsClient.getFileUrl(fileName);

            // Return the retrieved URL
            return documentUrl;
        } catch (Exception e) {
            log.error("Error retrieving document URL for file: " + fileName, e);
            throw new RuntimeException("Failed to retrieve document URL for file: " + fileName, e);
        }
    }
}
