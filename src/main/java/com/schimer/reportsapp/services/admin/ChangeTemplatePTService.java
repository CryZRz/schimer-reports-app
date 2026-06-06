package com.schimer.reportsapp.services.admin;

import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ChangeTemplatePTService {

    public void generateReport(File file){
        try{
            var in = new FileInputStream(file);
            var report = XDocReportRegistry.getRegistry()
                    .loadReport(in, TemplateEngineKind.Velocity);

            var context = report.createContext();
            context.put("nombre", "Alan Daniel");

            var output = new FileOutputStream("test.docx");
            report.process(context, output);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
