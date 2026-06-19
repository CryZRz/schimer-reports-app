package com.schimer.reportsapp.services.admin;

import com.schimer.reportsapp.domain.entities.ProductFinishedEntity;
import com.schimer.reportsapp.domain.entities.TemplatePTEntity;
import com.schimer.reportsapp.domain.repositories.templates.TemplatePTRepository;
import com.schimer.reportsapp.models.QualityFormRowTemplate;
import com.schimer.reportsapp.utils.PdfUtils;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.docx.preprocessor.sax.DocxPreprocessor;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

import java.io.*;
import java.util.List;

public class TemplatePTService {

    private final TemplatePTRepository templatePTRepository = new TemplatePTRepository();

    public void generateReport(File file){
        try {
            var outputFile = new File("storage/templates/template-product-finished.docx");
            var in = new FileInputStream(file);
            var output = new FileOutputStream(outputFile);

            var report = XDocReportRegistry.getRegistry()
                    .loadReport(in, TemplateEngineKind.Velocity);

            report.addPreprocessor("word/document.xml", DocxPreprocessor.INSTANCE);

            var context = report.createContext();

            report.process(context, output);

            templatePTRepository.save(new TemplatePTEntity(null, outputFile.getName(), outputFile.getAbsolutePath()));
        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateReport(ProductFinishedEntity entity, String templatePath){
        if (entity.getTemplate() == null) return null;

        var docxPath = "storage/reports/" + entity.getFolio() + ".docx";

        try {
            var report = XDocReportRegistry.getRegistry()
                    .loadReport(new FileInputStream(templatePath), TemplateEngineKind.Velocity);

            report.addPreprocessor("word/document.xml", DocxPreprocessor.INSTANCE);

            var metadata = new FieldsMetadata();
            metadata.addFieldAsList("items.especificacion");
            metadata.addFieldAsList("items.parametro");
            metadata.addFieldAsList("items.resultado");
            metadata.addFieldAsList("items.unidades");
            metadata.addFieldAsList("items.metodologia");
            report.setFieldsMetadata(metadata);

            var context = report.createContext();

            var listQualities = entity.getQualityCertificate().getQualityDetails().stream()
                    .map(detail -> {
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
            context.put("autor", entity.getUser().getName() + " " + entity.getUser().getLastName());

            try (var output = new FileOutputStream(docxPath)) {
                report.process(context, output);
            }

            return PdfUtils.generatePdf(docxPath);
        } catch (IOException | XDocReportException e) {
            throw new RuntimeException("Error generando reporte para folio: " + entity.getFolio(), e);
        }
    }

    public List<TemplatePTEntity> getAll(){
        return templatePTRepository.getAll();
    }

    public TemplatePTEntity save(TemplatePTEntity entity){
        return templatePTRepository.save(entity);
    }
}
