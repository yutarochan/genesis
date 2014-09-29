/*  1:   */ package links.videos;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.net.URL;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ 
/*  7:   */ public class MovieDescription
/*  8:   */ {
/*  9:   */   private ArrayList<MovieLink> summaries;
/* 10:   */   private ArrayList<MovieLink> events;
/* 11:   */   private URL url;
/* 12:   */   private File file;
/* 13:   */   
/* 14:   */   public MovieDescription(URL url)
/* 15:   */   {
/* 16:22 */     this.url = url;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public MovieDescription(File file)
/* 20:   */   {
/* 21:26 */     this.file = file;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ArrayList<MovieLink> getSummaries()
/* 25:   */   {
/* 26:30 */     if (this.summaries == null) {
/* 27:31 */       this.summaries = new ArrayList();
/* 28:   */     }
/* 29:33 */     return this.summaries;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void addSummary(MovieLink t)
/* 33:   */   {
/* 34:37 */     getSummaries().add(t);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ArrayList<MovieLink> getEvents()
/* 38:   */   {
/* 39:41 */     if (this.events == null) {
/* 40:42 */       this.events = new ArrayList();
/* 41:   */     }
/* 42:44 */     return this.events;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void addEvent(MovieLink t)
/* 46:   */   {
/* 47:48 */     getEvents().add(t);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String toString()
/* 51:   */   {
/* 52:52 */     if (this.url == null) {
/* 53:53 */       return null;
/* 54:   */     }
/* 55:55 */     int index = this.url.getPath().lastIndexOf('/');
/* 56:   */     
/* 57:57 */     return this.url.getPath();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public URL getUrl()
/* 61:   */   {
/* 62:61 */     return this.url;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public File getDirectory()
/* 66:   */   {
/* 67:65 */     return this.file;
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     links.videos.MovieDescription
 * JD-Core Version:    0.7.0.1
 */