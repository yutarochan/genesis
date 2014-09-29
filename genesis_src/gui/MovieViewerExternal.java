/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import java.io.File;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ import java.net.URISyntaxException;
/*  9:   */ import java.net.URL;
/* 10:   */ import utils.Mark;
/* 11:   */ import utils.ProcessExternally;
/* 12:   */ 
/* 13:   */ public class MovieViewerExternal
/* 14:   */   extends AbstractWiredBox
/* 15:   */ {
/* 16:   */   public MovieViewerExternal()
/* 17:   */   {
/* 18:17 */     setName("External video viewer");
/* 19:18 */     Connections.getPorts(this).addSignalProcessor("processInput");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void processInput(Object o)
/* 23:   */   {
/* 24:22 */     if ((o instanceof String))
/* 25:   */     {
/* 26:23 */       String s = (String)o;
/* 27:24 */       Mark.say(new Object[] {"Processing movie externally, internal method abandoned and check box shut off" });
/* 28:25 */       ProcessExternally.processFileExternally(s);
/* 29:   */     }
/* 30:27 */     else if ((o instanceof URL))
/* 31:   */     {
/* 32:28 */       URL url = (URL)o;
/* 33:   */       try
/* 34:   */       {
/* 35:30 */         ProcessExternally.processFileExternally(new File(url.toURI()).getPath());
/* 36:   */       }
/* 37:   */       catch (URISyntaxException e)
/* 38:   */       {
/* 39:33 */         e.printStackTrace();
/* 40:   */       }
/* 41:   */     }
/* 42:36 */     else if (o != null)
/* 43:   */     {
/* 44:39 */       System.err.println("ImagePanel.setImage got a " + o.getClass());
/* 45:   */     }
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static void main(String[] ignore)
/* 49:   */   {
/* 50:44 */     new MovieViewerExternal().processInput("c:/phw/java/gauntlet/memories/visualmemory/videos/Give.mpg");
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.MovieViewerExternal
 * JD-Core Version:    0.7.0.1
 */