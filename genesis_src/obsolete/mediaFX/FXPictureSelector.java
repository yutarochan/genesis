/*   1:    */ package obsolete.mediaFX;
/*   2:    */ 
/*   3:    */ import adamKraft.videoUtils.MovieReader;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import gui.PictureViewer;
/*   8:    */ import java.awt.Container;
/*   9:    */ import java.awt.GridLayout;
/*  10:    */ import java.awt.event.MouseAdapter;
/*  11:    */ import java.awt.event.MouseEvent;
/*  12:    */ import java.awt.image.BufferedImage;
/*  13:    */ import java.io.File;
/*  14:    */ import java.io.FilenameFilter;
/*  15:    */ import javax.imageio.ImageIO;
/*  16:    */ import javax.swing.JFrame;
/*  17:    */ import javax.swing.JPanel;
/*  18:    */ import javax.swing.JScrollPane;
/*  19:    */ import javax.swing.JViewport;
/*  20:    */ import utils.Mark;
/*  21:    */ 
/*  22:    */ public class FXPictureSelector
/*  23:    */   extends JScrollPane
/*  24:    */   implements WiredBox
/*  25:    */ {
/*  26: 32 */   public File directory = new File("C:/Users/phw/Co57_cache/videos/");
/*  27: 34 */   public File thumb = new File("C:/Users/phw/Co57_cache/videos/thumb.jpg");
/*  28:    */   private MovieFilter movieFilter;
/*  29:    */   private JPanel picturePanel;
/*  30:    */   
/*  31:    */   public FXPictureSelector()
/*  32:    */   {
/*  33: 44 */     this.picturePanel = new JPanel();
/*  34: 45 */     this.picturePanel.setLayout(new GridLayout(0, 8));
/*  35: 46 */     getViewport().add(this.picturePanel);
/*  36: 47 */     setName("Video selector");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void fetchInventory()
/*  40:    */   {
/*  41: 54 */     File[] files = this.directory.listFiles(getMovieFilter());
/*  42: 55 */     Mark.say(new Object[] {"File count:", Integer.valueOf(files.length) });
/*  43: 56 */     for (File file : files)
/*  44:    */     {
/*  45: 57 */       checkForThumbnail(file);
/*  46: 58 */       PictureViewer pictureViewer = new PictureViewer(fileTypeToFileType(".mp4", ".jpg", file));
/*  47: 59 */       pictureViewer.addMouseListener(new SelectionListener(pictureViewer));
/*  48: 60 */       this.picturePanel.add(pictureViewer);
/*  49:    */     }
/*  50: 62 */     revalidate();
/*  51:    */   }
/*  52:    */   
/*  53:    */   private String fileTypeNameToFileTypeName(String from, String to, String name)
/*  54:    */   {
/*  55: 69 */     if (name.endsWith(from)) {
/*  56: 70 */       return name.substring(0, name.length() - 4) + to;
/*  57:    */     }
/*  58: 72 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   private File fileTypeToFileType(String from, String to, File file)
/*  62:    */   {
/*  63: 79 */     String name = file.getPath();
/*  64: 80 */     if (name.endsWith(from)) {
/*  65: 81 */       return new File(name.substring(0, name.length() - 4) + to);
/*  66:    */     }
/*  67: 83 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private boolean checkForThumbnail(File file)
/*  71:    */   {
/*  72: 90 */     String total = file.getPath();
/*  73: 91 */     String revised = fileTypeNameToFileTypeName(".mp4", ".png", total);
/*  74: 92 */     if (revised == null) {
/*  75: 93 */       return false;
/*  76:    */     }
/*  77: 95 */     File revisedFile = new File(revised);
/*  78: 96 */     if (revised != null) {
/*  79: 98 */       if (revisedFile.exists()) {
/*  80:100 */         return true;
/*  81:    */       }
/*  82:    */     }
/*  83:103 */     Mark.say(new Object[] {"Can't find", ".png for", file.getPath() });
/*  84:    */     try
/*  85:    */     {
/*  86:105 */       MovieReader movieReader = new MovieReader(total);
/*  87:106 */       movieReader.seek(20L);
/*  88:107 */       BufferedImage image = movieReader.getNextFrame();
/*  89:    */       
/*  90:109 */       ImageIO.write(image, "png", revisedFile);
/*  91:110 */       Mark.say(new Object[] {"Wrote image", revisedFile });
/*  92:111 */       return true;
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:114 */       Mark.err(new Object[] {"OOps" });
/*  97:115 */       e.printStackTrace();
/*  98:    */     }
/*  99:116 */     return false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   private MovieFilter getMovieFilter()
/* 103:    */   {
/* 104:124 */     if (this.movieFilter == null) {
/* 105:125 */       this.movieFilter = new MovieFilter(null);
/* 106:    */     }
/* 107:127 */     return this.movieFilter;
/* 108:    */   }
/* 109:    */   
/* 110:    */   private class MovieFilter
/* 111:    */     implements FilenameFilter
/* 112:    */   {
/* 113:    */     private MovieFilter() {}
/* 114:    */     
/* 115:    */     public boolean accept(File dir, String name)
/* 116:    */     {
/* 117:135 */       if ((name.indexOf(".mp4") > 0) || (name.indexOf(".mpg") > 0)) {
/* 118:136 */         return true;
/* 119:    */       }
/* 120:138 */       return false;
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void test()
/* 125:    */   {
/* 126:144 */     JFrame frame = new JFrame();
/* 127:    */     
/* 128:146 */     FXPictureSelector selector = new FXPictureSelector();
/* 129:    */     
/* 130:148 */     frame.getContentPane().add(selector);
/* 131:    */     
/* 132:150 */     frame.setBounds(0, 0, 800, 500);
/* 133:    */     
/* 134:152 */     frame.setVisible(true);
/* 135:    */     
/* 136:154 */     frame.setDefaultCloseOperation(3);
/* 137:    */     
/* 138:156 */     selector.fetchInventory();
/* 139:159 */     for (String s : ImageIO.getWriterFormatNames()) {
/* 140:160 */       Mark.say(new Object[] {"Format:", s });
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   private class SelectionListener
/* 145:    */     extends MouseAdapter
/* 146:    */   {
/* 147:    */     PictureViewer pictureViewer;
/* 148:    */     
/* 149:    */     public SelectionListener(PictureViewer viewer)
/* 150:    */     {
/* 151:170 */       this.pictureViewer = viewer;
/* 152:    */     }
/* 153:    */     
/* 154:    */     public void mouseClicked(MouseEvent e)
/* 155:    */     {
/* 156:177 */       PictureViewer viewer = (PictureViewer)e.getSource();
/* 157:178 */       File videoFile = FXPictureSelector.this.fileTypeToFileType(".jpg", ".mp4", viewer.getSource());
/* 158:179 */       Mark.say(new Object[] {"Time to process", videoFile });
/* 159:180 */       Connections.getPorts(FXPictureSelector.this).transmit("selected file name", videoFile.getPath());
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static void main(String[] args)
/* 164:    */   {
/* 165:188 */     new FXPictureSelector().test();
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mediaFX.FXPictureSelector
 * JD-Core Version:    0.7.0.1
 */