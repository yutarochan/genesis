/*   1:    */ package adamKraft;
/*   2:    */ 
/*   3:    */ import adamKraft.videoUtils.MovieReader;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.awt.image.BufferedImage;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.Comparator;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.TreeSet;
/*  12:    */ 
/*  13:    */ public class MovieReaderWithAnnotations
/*  14:    */   implements WiredBox
/*  15:    */ {
/*  16:    */   MovieReader movieReader;
/*  17:    */   TreeSet<AnnotationFile> annotationFiles;
/*  18:    */   String movieName;
/*  19:    */   
/*  20:    */   public void add(AnnotationFile f)
/*  21:    */   {
/*  22: 24 */     getAnnotationFiles().add(f);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void playWithAnnotations()
/*  26:    */     throws Exception
/*  27:    */   {
/*  28: 28 */     this.movieReader.seek(0L);
/*  29: 29 */     int frame = 0;
/*  30:    */     AnnotationFile annotationFile;
/*  31: 30 */     for (Iterator localIterator = getAnnotationFiles().iterator(); localIterator.hasNext(); frame < annotationFile.getEndFrame())
/*  32:    */     {
/*  33: 30 */       annotationFile = (AnnotationFile)localIterator.next();
/*  34: 32 */       while (frame + 30 < annotationFile.getStartFrame())
/*  35:    */       {
/*  36: 33 */         frame += 30;
/*  37: 34 */         this.movieReader.seek(frame);
/*  38: 35 */         BufferedImage signal = this.movieReader.getNextFrame();
/*  39: 36 */         if (signal != null) {
/*  40: 37 */           Connections.getPorts(this).transmit(signal);
/*  41:    */         }
/*  42: 39 */         Thread.sleep(30L);
/*  43:    */       }
/*  44: 42 */       continue;
/*  45: 43 */       BufferedImage signal = this.movieReader.getNextFrame();
/*  46: 44 */       frame++;
/*  47: 45 */       if (signal != null) {
/*  48: 46 */         Connections.getPorts(this).transmit(signal);
/*  49:    */       }
/*  50: 48 */       Thread.sleep(30L);
/*  51:    */     }
/*  52:    */     for (;;)
/*  53:    */     {
/*  54: 53 */       frame += 30;
/*  55: 54 */       this.movieReader.seek(frame);
/*  56: 55 */       BufferedImage signal = this.movieReader.getNextFrame();
/*  57: 56 */       if (signal == null) {
/*  58:    */         break;
/*  59:    */       }
/*  60: 57 */       Connections.getPorts(this).transmit(signal);
/*  61:    */       
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65: 62 */       Thread.sleep(30L);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public MovieReaderWithAnnotations(MovieReader m)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72: 67 */     this.movieReader = m;
/*  73:    */   }
/*  74:    */   
/*  75:    */   class FileComparator
/*  76:    */     implements Comparator<AnnotationFile>
/*  77:    */   {
/*  78:    */     FileComparator() {}
/*  79:    */     
/*  80:    */     public int compare(AnnotationFile o1, AnnotationFile o2)
/*  81:    */     {
/*  82: 76 */       if (o1.getStartFrame() < o2.getStartFrame()) {
/*  83: 77 */         return 1;
/*  84:    */       }
/*  85: 79 */       if (o1.getStartFrame() > o2.getStartFrame()) {
/*  86: 80 */         return -1;
/*  87:    */       }
/*  88: 82 */       if (o1.getEndFrame() < o2.getEndFrame()) {
/*  89: 83 */         return 1;
/*  90:    */       }
/*  91: 85 */       if (o1.getEndFrame() > o2.getEndFrame()) {
/*  92: 86 */         return -1;
/*  93:    */       }
/*  94: 88 */       return o1.compareTo(o2);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public TreeSet<AnnotationFile> getAnnotationFiles()
/*  99:    */   {
/* 100: 94 */     if (this.annotationFiles == null) {
/* 101: 95 */       this.annotationFiles = new TreeSet();
/* 102:    */     }
/* 103: 97 */     return this.annotationFiles;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getName()
/* 107:    */   {
/* 108:102 */     return "MovieReaderWithAnnotations";
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.MovieReaderWithAnnotations
 * JD-Core Version:    0.7.0.1
 */