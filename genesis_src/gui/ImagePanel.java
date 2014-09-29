/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import connections.WiredBox;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Graphics2D;
/*   6:    */ import java.awt.image.RenderedImage;
/*   7:    */ import java.io.File;
/*   8:    */ import java.io.FilenameFilter;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import javax.swing.JPanel;
/*  12:    */ 
/*  13:    */ public class ImagePanel
/*  14:    */   extends JPanel
/*  15:    */   implements WiredBox
/*  16:    */ {
/*  17:    */   private RenderedImage image;
/*  18:    */   private int frameRate;
/*  19:    */   private File source;
/*  20:    */   private static boolean running;
/*  21:    */   
/*  22:    */   public void processInput(Object paramObject)
/*  23:    */   {
/*  24: 39 */     throw new Error("Unresolved compilation problem: \n");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setMovie(URL paramURL)
/*  28:    */   {
/*  29: 60 */     throw new Error("Unresolved compilation problem: \n");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setMovie(File paramFile)
/*  33:    */   {
/*  34: 66 */     throw new Error("Unresolved compilation problem: \n");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setMovie(File paramFile, String paramString)
/*  38:    */   {
/*  39: 86 */     throw new Error("Unresolved compilation problem: \n\tJAI cannot be resolved\n");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public File getMovie()
/*  43:    */   {
/*  44:106 */     throw new Error("Unresolved compilation problem: \n");
/*  45:    */   }
/*  46:    */   
/*  47:    */   class PaintingTread
/*  48:    */     extends Thread
/*  49:    */   {
/*  50:    */     private ArrayList<String> frames;
/*  51:    */     private File directory;
/*  52:    */     
/*  53:    */     public PaintingTread(ArrayList<String> paramArrayList) {}
/*  54:    */     
/*  55:    */     public void run()
/*  56:    */     {
/*  57:139 */       throw new Error("Unresolved compilation problem: \n\tJAI cannot be resolved\n");
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   class PaintFrame
/*  62:    */     implements Runnable
/*  63:    */   {
/*  64:    */     RenderedImage myImage;
/*  65:    */     
/*  66:    */     public PaintFrame(RenderedImage paramRenderedImage) {}
/*  67:    */     
/*  68:    */     public void run()
/*  69:    */     {
/*  70:161 */       throw new Error("Unresolved compilation problem: \n");
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   class PaintFrames
/*  75:    */     implements Runnable
/*  76:    */   {
/*  77:    */     ArrayList<RenderedImage> frames;
/*  78:    */     
/*  79:    */     public PaintFrames() {}
/*  80:    */     
/*  81:    */     public void run()
/*  82:    */     {
/*  83:174 */       throw new Error("Unresolved compilation problem: \n");
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   class MyFilter
/*  88:    */     implements FilenameFilter
/*  89:    */   {
/*  90:    */     String range;
/*  91:    */     int start;
/*  92:    */     int end;
/*  93:    */     
/*  94:    */     public MyFilter(String paramString) {}
/*  95:    */     
/*  96:    */     private void instantiateStartAndEnd(String paramString)
/*  97:    */     {
/*  98:205 */       throw new Error("Unresolved compilation problem: \n");
/*  99:    */     }
/* 100:    */     
/* 101:    */     public boolean accept(File paramFile, String paramString)
/* 102:    */     {
/* 103:225 */       throw new Error("Unresolved compilation problem: \n");
/* 104:    */     }
/* 105:    */     
/* 106:    */     private int extractFrame(String paramString)
/* 107:    */     {
/* 108:248 */       throw new Error("Unresolved compilation problem: \n");
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setImage(String paramString)
/* 113:    */   {
/* 114:255 */     throw new Error("Unresolved compilation problem: \n");
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setImageAux(String paramString)
/* 118:    */   {
/* 119:259 */     throw new Error("Unresolved compilation problem: \n");
/* 120:    */   }
/* 121:    */   
/* 122:    */   class ShowImage
/* 123:    */     implements Runnable
/* 124:    */   {
/* 125:    */     String name;
/* 126:    */     
/* 127:    */     public ShowImage(String paramString) {}
/* 128:    */     
/* 129:    */     public void run()
/* 130:    */     {
/* 131:270 */       throw new Error("Unresolved compilation problem: \n");
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setImage(RenderedImage paramRenderedImage)
/* 136:    */   {
/* 137:276 */     throw new Error("Unresolved compilation problem: \n");
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void clear()
/* 141:    */   {
/* 142:281 */     throw new Error("Unresolved compilation problem: \n");
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void paint(Graphics paramGraphics)
/* 146:    */   {
/* 147:286 */     throw new Error("Unresolved compilation problem: \n");
/* 148:    */   }
/* 149:    */   
/* 150:    */   private void paintImage(Graphics2D paramGraphics2D, RenderedImage paramRenderedImage)
/* 151:    */   {
/* 152:296 */     throw new Error("Unresolved compilation problem: \n");
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void replay()
/* 156:    */   {
/* 157:333 */     throw new Error("Unresolved compilation problem: \n");
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.ImagePanel
 * JD-Core Version:    0.7.0.1
 */