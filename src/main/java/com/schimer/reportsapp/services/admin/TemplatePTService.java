package com.schimer.reportsapp.services.admin;

import com.schimer.reportsapp.domain.entities.ProductFinishedEntity;
import com.schimer.reportsapp.domain.entities.TemplatePTEntity;
import com.schimer.reportsapp.domain.repositories.templates.TemplatePTRepository;
import com.schimer.reportsapp.models.QualityFormRowTemplate;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.docx.preprocessor.sax.DocxPreprocessor;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

import java.io.*;

public class TemplatePTService {

    private final TemplatePTRepository templatePTRepository = new TemplatePTRepository();

    public void generateReport(File file){
        try {
            var outputFile = new File("storage/templates/test.docx");
            var in = new FileInputStream(file);
            var output = new FileOutputStream(outputFile);

            var report = XDocReportRegistry.getRegistry()
                    .loadReport(in, TemplateEngineKind.Velocity);

            report.addPreprocessor("word/document.xml", DocxPreprocessor.INSTANCE);

            var context = report.createContext();
            context.put("producto", "Cloro");

            report.process(context, output);

            templatePTRepository.save(new TemplatePTEntity(null, outputFile.getName(), outputFile.getAbsolutePath()));
        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateReport(ProductFinishedEntity entity){
        if(entity.getTemplate() != null){
            try {
                var outputFile = new File("storage/templates/"+entity.getFolio()+".docx");
                var in = new FileInputStream(entity.getTemplate().getPath());
                var output = new FileOutputStream(outputFile);

                var report = XDocReportRegistry.getRegistry()
                        .loadReport(in, TemplateEngineKind.Velocity);

                report.addPreprocessor("word/document.xml", DocxPreprocessor.INSTANCE);

                var metadata = report.createFieldsMetadata();
                metadata.load("item", QualityFormRowTemplate.class);
                report.setFieldsMetadata(metadata);
                var context = report.createContext();

                var listQualities = entity.getQualityCertificate().getQualityDetails().stream().map(detail -> {
                    var row = new QualityFormRowTemplate();
                    row.setEspecificacion(detail.getSpecificationName());
                    row.setMetodologia(detail.getMethodologyValue());
                    row.setUnidades(detail.getUnitsValue());
                    row.setResultado(detail.getResultValue());
                    row.setParametro(detail.getParameterValue());
                    return row;
                }).toList();

                context.put("items", listQualities);

                context.put("producto", entity.getProduct());
                context.put("caducidad", entity.getQualityCertificate().getExpirationDate());
                context.put("cantidad", entity.getQualityCertificate().getAmount());
                context.put("folio", entity.getFolio());
                context.put("fecha", entity.getCreatedAt());
                context.put("firma", entity.getUser().getSignature());
                context.put("lote", entity.getBatch());
                context.put("autor", entity.getUser().getName()+" "+entity.getUser().getLastName());

                report.process(context, output);
            } catch (IOException | XDocReportException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }


    }

}
