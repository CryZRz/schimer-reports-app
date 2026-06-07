package com.schimer.reportsapp.services.admin;

import fr.opensagres.xdocreport.document.docx.preprocessor.sax.DocxPreprocessor;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ChangeTemplatePTService {

    private static String pathTemplates = "storage/templates/";

    public void generateReport(File file){
        try (var in = new FileInputStream(file);
             var output = new FileOutputStream(pathTemplates+file.getName())) {

            var report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

            report.addPreprocessor("word/document.xml", DocxPreprocessor.INSTANCE);

            var context = report.createContext();

            report.process(context, output);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al procesar el reporte:");
        }
    }

}
