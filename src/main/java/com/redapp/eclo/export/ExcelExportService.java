package com.redapp.eclo.export;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.template.SimpleExporter;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.redapp.eclo.entities.FileExport;
import com.redapp.eclo.entities.Program;
import com.redapp.eclo.services.LessonsService;

@Service
public class ExcelExportService {
	@Autowired
	LessonsService lessonsService;

 
 
 public FileExport programExcelExport(Long month,Long year,String username,String categorie) throws FileNotFoundException, IOException {
	 String usernameTitle="";
	 String categorieTitle="";
	 if(!username.equals("*"))
		 usernameTitle=username;
	 if(!categorie.equals("*"))
		 categorieTitle=categorie;
	 String fileName="programme_"+month+"_"+year+"_"+usernameTitle+"_"+categorieTitle+".xls";
	 
     try (OutputStream os = new FileOutputStream("target/"+fileName)) {
	        List<String> headers = Arrays.asList("zone", "encadrant" ,"horaire","date"  ,"sujet","type");
	        SimpleExporter exporter = new SimpleExporter();
	        exporter.gridExport(headers, this.lessonsService.getLessons(month, year, username,categorie), "appUser.area,appUser.name,time,date,title,type", os);
	

    		 
     }
	 

	 catch(Exception e) {
		 System.out.println(e.getMessage());
	 }
	return new FileExport(fileName);
	 
 }
 
 
 public ResponseEntity downloadFile(String fileName) {
 	Path path = Paths.get("target/"+ fileName);
 	Resource resource = null;
 	try {
 		resource = new UrlResource(path.toUri());
 	} catch (MalformedURLException e) {
 		e.printStackTrace();
 	}
 	return ResponseEntity.ok()
 			.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
 			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
 			.body(resource);
 }
	 
     
}
