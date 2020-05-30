package com.redapp.eclo.export;

import java.awt.Color;
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
import java.util.Date;
import java.util.Iterator;
//import java.util.List;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lowagie.text.Anchor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.redapp.eclo.entities.AppUser;
import com.redapp.eclo.entities.FileExport;
import com.redapp.eclo.entities.Lesson;
import com.redapp.eclo.entities.Program;
import com.redapp.eclo.services.LessonsService;
import com.redapp.eclo.services.ProgramsService;
import com.redapp.eclo.services.UsersService;


@Service
public class PdfExportService {
	@Autowired
	LessonsService lessonsService;
	@Autowired
	UsersService usersService;
	@Autowired
	ProgramsService programService;
	private static BaseFont bf;
    private static Font bigFont;
    private static Font smallFont;
    private static Font xsmallFont;
   private static String [] months= {"يناير","فبراير","مارس","ابريل","ماي","يونيو","يوليوز","غشت","شتنبر","اكتوبر","نونبر","دجنبر"};
 
 
 public FileExport programPdfExport(Long month,Long year,String username) throws FileNotFoundException, IOException, DocumentException {
	 AppUser u=this.usersService.findByUsername(username);
	 if(u.equals(null))
		 throw new RuntimeException("utilisateur introuvable");
	 
	 Program p=this.programService.getProgram(month, year);
	 if(p.equals(null))
		 throw new RuntimeException("Programme introuvable");
	 
	this.bf= BaseFont.createFont(
			 new ClassPathResource("fonts/ARIALUNI.TTF").getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	this.bigFont= new Font(bf, 16,
            Font.BOLDITALIC);
	this.smallFont= new Font(bf, 12,
            Font.NORMAL);
	this.xsmallFont= new Font(bf, 10,
            Font.NORMAL);
	 String fileName="programme_"+month+"_"+year+"_"+username+".pdf";
     try {
    	
         Document document = new Document();
         PdfWriter.getInstance(document, new FileOutputStream("target/"+fileName));
         document.open();
         this.addMetaData(document,month,year,u.getName());
         this.addTitlePage(document,u,month,year,p.getHijri());
         this.addContent(document,month,year,username);
         this.addFooter(document,u);
         document.close();
        
     } catch (Exception e) {
         e.printStackTrace();
     }
     return new FileExport(fileName);
	
 }

 // iText allows to add metadata to the PDF which can be viewed in your Adobe
 // Reader
 // under File -> Properties
 private  void addMetaData(Document document, Long month, Long year, String name) {
     document.addTitle("programme "+month+"/"+year+" "+name);
     document.addSubject("");
     document.addKeywords("eclo, PDF");
     document.addAuthor("clo taounate");
     document.addCreator("clo taounate");
 }

 private  void addTitlePage(Document document, AppUser user,Long month,Long year, String hijri)
         throws DocumentException, MalformedURLException, IOException {
     Paragraph entete = new Paragraph();
     entete.setAlignment(Paragraph.ALIGN_CENTER);
     addEmptyLine(document, 2);
     Image img = Image.getInstance( new ClassPathResource("images/royaume.png").getURL().toString());
     Image img2 = Image.getInstance(new ClassPathResource("images/logo.png").getURL().toString());
     img.scaleAbsolute(50, 50);
     img.setAbsolutePosition(270, 780);
     img2.scaleAbsolute(100, 80);
     img2.setAbsolutePosition(490, 745);
     entete.add(img);
     entete.add(img2);
     addEmptyLine(document, 2);
     document.add(entete);
      
     PdfPTable table = new PdfPTable(1);
     Phrase phrase = new Phrase();
     
     String title=" البرنامج الشهري لـ"+":"+this.categorieToName(user.getCategorie());
     title+=" "+user.getName();
     title+=" لشهر ";
     title+=" "+months[(int) (month-1)];
     title+=" "+year+"م";
     title+=System.getProperty("line.separator");
     title+=" موافق "+hijri;
     phrase.add(new Chunk(title, bigFont));
     PdfPCell cell = new PdfPCell(phrase);
     cell.setUseDescender(true);
     cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
     cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
     cell.setBorder(0);
     table.addCell(cell);
     document.add(table);
    
 }
 

 private  void addContent(Document document, Long month, Long year, String username) throws DocumentException {
	 java.util.List<Lesson> lessons=this.lessonsService.getLessons(month, year, username,"*");
	 this.addEmptyLine(document, 2);
	  PdfPTable table = new PdfPTable(6);
	  table.setWidthPercentage(90);
	  table.setWidths(new float[] { 1, 1,1,3,1,1 });
	  table.setHeaderRows(1);
	  table.addCell(this.formatedCell("المكان"));
	    table.addCell(this.formatedCell("التوقيت"));
	    table.addCell(this.formatedCell("التاريخ"));
	     table.addCell(this.formatedCell("الموضوع"));
	     table.addCell(this.formatedCell("نوع النشاط"));
     table.addCell(this.formatedCell("ر ت"));
     
     for(int i=0;i<lessons.size();i++) {
    	 Lesson l=lessons.get(i);
    	 table.addCell(formatedCell(l.getArea()));
    	 table.addCell(formatedCell(l.getTime()));
 		table.addCell(formatedSmallCell((l.getDate().getYear()+1900)+"/"+(l.getDate().getMonth()+1)+"/"+l.getDate().getDate()));
 		table.addCell(formatedCell(l.getTitle()));
 		table.addCell(formatedCell(l.getType()));
 		table.addCell(formatedCell((i+1)+""));
     }
    
     document.add(table);

 }

 private PdfPCell formatedCell(String text) {
	  Phrase phrase = new Phrase();
     phrase.add(new Chunk(text, smallFont));
	 PdfPCell c1 = new PdfPCell(phrase);
     c1.setHorizontalAlignment(Element.ALIGN_CENTER);
     c1.setUseDescender(true);
     c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
     return c1;
 }
 
 private PdfPCell formatedSmallCell(String text) {
	  Phrase phrase = new Phrase();
    phrase.add(new Chunk(text, this.xsmallFont));
	 PdfPCell c1 = new PdfPCell(phrase);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setUseDescender(true);
    c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
    return c1;
}
 private  void addFooter(Document document, AppUser u)
         throws DocumentException, MalformedURLException, IOException {

     addEmptyLine(document, 2);   
     PdfPTable table = new PdfPTable(2);
     table.setWidthPercentage(80);
     PdfPCell c1=this.formatedCell("الرئيس");
     PdfPCell c2=this.formatedCell(this.categorieToName(u.getCategorie()));
     c1.setBorder(0);
     c2.setBorder(0);
     table.addCell(c1);
     table.addCell(c2);
     document.add(table);
    
 }

 
 private  void addEmptyLine(Document document, int number) throws DocumentException {
	 Paragraph paragraph=new Paragraph();
     for (int i = 0; i < number; i++) {
         paragraph.add(new Paragraph(" "));
     }
     document.add(paragraph);
     
     
 }

 private String categorieToName(String categorie) {
	 String t="(ة)";
	 if(categorie.equals("w"))
		 return  "الواعظ"+ t;
		 else if (categorie.equals("t"))
				 return  "ا لمتطوع "+ t  +" الواعظ" + t;
		 else if (categorie.equals("o"))
			 return "عضو المجلس";	
		 else return
				"المؤطر"+t;
 }

     
}
