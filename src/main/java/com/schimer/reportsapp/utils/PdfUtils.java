package com.schimer.reportsapp.utils;

import com.documents4j.api.DocumentType;
import com.documents4j.job.LocalConverter;

import java.io.*;

public class PdfUtils {
    public static String generatePdf(String path){
        var pdfPath = path.replace(".docx", ".pdf");

        var docxFile = new File(path);
        var pdfFile = new File(pdfPath);

        try (InputStream docxInputStream = new FileInputStream(docxFile);
             OutputStream pdfOutputStream = new FileOutputStream(pdfFile)) {

            var converter = LocalConverter.builder().build();


            converter.convert(docxInputStream).as(DocumentType.DOCX)
                    .to(pdfOutputStream).as(DocumentType.PDF)
                    .execute();


            converter.shutDown();

            return pdfFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir a PDF con documents4j: " + path, e);
        }
    }
}
