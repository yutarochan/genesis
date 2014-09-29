/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.net.URI;
/*  5:   */ 
/*  6:   */ public class AnnotationFile
/*  7:   */   extends File
/*  8:   */ {
/*  9:   */   int startFrame;
/* 10:   */   int endFrame;
/* 11:   */   
/* 12:   */   public AnnotationFile(File file, int startFrame, int endFrame)
/* 13:   */   {
/* 14:18 */     this(file.getPath(), startFrame, endFrame);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public AnnotationFile(String pathname, int startFrame, int endFrame)
/* 18:   */   {
/* 19:22 */     this(pathname);
/* 20:23 */     this.startFrame = startFrame;
/* 21:24 */     this.endFrame = endFrame;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public AnnotationFile(String pathname)
/* 25:   */   {
/* 26:28 */     super(pathname);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public AnnotationFile(URI uri)
/* 30:   */   {
/* 31:32 */     super(uri);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public AnnotationFile(String parent, String child)
/* 35:   */   {
/* 36:36 */     super(parent, child);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public AnnotationFile(File parent, String child)
/* 40:   */   {
/* 41:40 */     super(parent, child);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int getStartFrame()
/* 45:   */   {
/* 46:44 */     return this.startFrame;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int getEndFrame()
/* 50:   */   {
/* 51:48 */     return this.endFrame;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.AnnotationFile
 * JD-Core Version:    0.7.0.1
 */