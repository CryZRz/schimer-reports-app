package com.schimer.reportsapp.services.admin;

import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialEntity;
import com.schimer.reportsapp.domain.entities.rawMaterial.TemplateRawMaterialEntity;
import com.schimer.reportsapp.domain.repositories.templates.TemplateMPRepository;
import com.schimer.reportsapp.models.QualityRowTemplateMaterialRelease;
import com.schimer.reportsapp.utils.PdfUtils;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.docx.preprocessor.sax.DocxPreprocessor;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class TemplateMPService {

    private final TemplateMPRepository templateMPRepository = new TemplateMPRepository();

    public String generateReport(RawMaterialEntity entity, String templatePath){
        var rawMaterialRelease = entity.getRawMaterialRelease();
        var docxPath = "storage/reports/" + entity.getFolio() + ".docx";

        try {
            var report = XDocReportRegistry.getRegistry()
                    .loadReport(new FileInputStream(templatePath), TemplateEngineKind.Velocity);

            report.addPreprocessor("word/document.xml", DocxPreprocessor.INSTANCE);

            var metadata = new FieldsMetadata();
            metadata.addFieldAsList("items.parametro");
            metadata.addFieldAsList("items.resultado");
            metadata.addFieldAsList("items.especificacion");
            report.setFieldsMetadata(metadata);

            var context = report.createContext();

            var listParameters = rawMaterialRelease.getQualityParameters().stream()
                    .map(detail -> {
                        var row = new QualityRowTemplateMaterialRelease();
                        row.setEspecificacion(detail.getSpecification());
                        row.setResultado(detail.getResult());
                        row.setParametro(detail.getParameter());
                        return row;
                    }).toList();

            context.put("items", listParameters);
            context.put("producto", entity.getProduct());
            context.put("caducidad", rawMaterialRelease.getExpirationDate());
            context.put("cantidad", rawMaterialRelease.getAmount());
            context.put("folio", entity.getFolio());
            context.put("fecha", entity.getReleaseDate());
            context.put("emision", LocalDate.now().toString());
            context.put("nota", rawMaterialRelease.getNote());
            if (rawMaterialRelease.isAccepted()){
                context.put("aceptado", "X");
                context.put("noaceptado", "");
            }else{
                context.put("noaceptado", "X");
                context.put("aceptado", "");
            }
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

    public List<TemplateRawMaterialEntity> getAll(){
        return templateMPRepository.getAll();
    }

    public TemplateRawMaterialEntity save(TemplateRawMaterialEntity entity){
        return templateMPRepository.save(entity);
    }

}
