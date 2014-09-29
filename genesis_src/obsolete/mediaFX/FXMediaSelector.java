/*   1:    */ package obsolete.mediaFX;
/*   2:    */ 
/*   3:    */ import adamKraft.Co57Connector;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Dimension;
/*   9:    */ import java.awt.event.MouseAdapter;
/*  10:    */ import java.awt.event.MouseEvent;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import javax.swing.BorderFactory;
/*  13:    */ import javax.swing.JLabel;
/*  14:    */ import javax.swing.JPanel;
/*  15:    */ import layout.TableLayout;
/*  16:    */ import utils.Mark;
/*  17:    */ 
/*  18:    */ public class FXMediaSelector
/*  19:    */   extends JPanel
/*  20:    */   implements WiredBox
/*  21:    */ {
/*  22:    */   private static String SOURCE_URL;
/*  23:    */   private static final String SOURCE_URL1 = "file:/C:/Users/phw/DARPA_VIDEO_CACHE/";
/*  24:    */   private static final String SOURCE_URL2 = "http://groups.csail.mit.edu/genesis/fxMedia/";
/*  25: 28 */   double aspectRatio = 2.0D;
/*  26:    */   Co57Connector co57Connector;
/*  27:    */   PreviewerListener previewerListener;
/*  28:    */   LabelListener lableListener;
/*  29:    */   
/*  30:    */   public Co57Connector getCo57Connector()
/*  31:    */   {
/*  32: 37 */     this.co57Connector = new Co57Connector();
/*  33: 38 */     return this.co57Connector;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public FXMediaSelector()
/*  37:    */   {
/*  38: 42 */     Connections.getPorts(this).addSignalProcessor("reinitialize");
/*  39: 43 */     setBackground(Color.WHITE);
/*  40: 44 */     setOpaque(true);
/*  41:    */     
/*  42: 46 */     SOURCE_URL = "http://groups.csail.mit.edu/genesis/fxMedia/";
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void reinitialize(Object signal)
/*  46:    */   {
/*  47: 50 */     if ((signal instanceof ArrayList))
/*  48:    */     {
/*  49: 51 */       ArrayList returnedFileNames = (ArrayList)signal;
/*  50: 52 */       Mark.say(new Object[] {"Obtained", Integer.valueOf(returnedFileNames.size()), "movie names from Co57" });
/*  51: 53 */       initialize(returnedFileNames);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static String toMediaUrl(String title, String ext)
/*  56:    */   {
/*  57: 61 */     title = replaceExtension(title, ext);
/*  58:    */     
/*  59: 63 */     return SOURCE_URL + title;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String removeExtension(String title)
/*  63:    */   {
/*  64: 67 */     int index = title.lastIndexOf('.');
/*  65: 68 */     if (index < 0) {
/*  66: 69 */       Mark.say(new Object[] {"Name has no extension" });
/*  67:    */     } else {
/*  68: 72 */       title = title.substring(0, index);
/*  69:    */     }
/*  70: 74 */     return title;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static String replaceExtension(String title, String ext)
/*  74:    */   {
/*  75: 78 */     int index = title.lastIndexOf('.');
/*  76: 79 */     if (index < 0)
/*  77:    */     {
/*  78: 80 */       Mark.say(new Object[] {"Name has no extension" });
/*  79: 81 */       title = title + "." + ext;
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83: 84 */       title = title.substring(0, index + 1) + ext;
/*  84:    */     }
/*  85: 86 */     return title;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void initialize(ArrayList<String> fileNames)
/*  89:    */   {
/*  90: 92 */     int limit = 30;
/*  91:    */     
/*  92: 94 */     ArrayList<WiredPictureViewer> pictureViewers = new ArrayList();
/*  93: 96 */     for (String name : fileNames) {
/*  94:    */       try
/*  95:    */       {
/*  96: 98 */         Mark.say(new Object[] {"Making picture viewer for", name });
/*  97: 99 */         String url = toMediaUrl(name, "jpg");
/*  98:100 */         WiredPictureViewer pictureViewer = new WiredPictureViewer(removeExtension(name), url);
/*  99:101 */         pictureViewers.add(pictureViewer);
/* 100:    */       }
/* 101:    */       catch (Exception e)
/* 102:    */       {
/* 103:104 */         Mark.err(new Object[] {"Unable to make movie player for", name });
/* 104:    */       }
/* 105:    */     }
/* 106:109 */     setPreferredSize(new Dimension(1000, 100));
/* 107:110 */     Mark.say(new Object[] {"Initializing movie selector" });
/* 108:111 */     removeAll();
/* 109:    */     
/* 110:113 */     int total = pictureViewers.size();
/* 111:    */     
/* 112:115 */     int count = Math.min(total, limit);
/* 113:    */     
/* 114:117 */     int nColumns = getBestColumnNumber(count);
/* 115:118 */     int nRows = count / nColumns;
/* 116:120 */     if (count % nColumns > 0) {
/* 117:121 */       nRows++;
/* 118:    */     }
/* 119:124 */     double[] columns = new double[nColumns];
/* 120:125 */     double[] rows = new double[nRows];
/* 121:126 */     for (int n = 0; n < nColumns; n++) {
/* 122:127 */       columns[n] = -1.0D;
/* 123:    */     }
/* 124:130 */     for (int n = 0; n < nRows; n++) {
/* 125:131 */       rows[n] = -1.0D;
/* 126:    */     }
/* 127:134 */     double[][] size = { columns, rows };
/* 128:    */     
/* 129:136 */     Mark.say(new Object[] {"Rows/columns", Integer.valueOf(rows.length), Integer.valueOf(columns.length) });
/* 130:    */     
/* 131:138 */     setLayout(new TableLayout(size));
/* 132:    */     
/* 133:140 */     int column = 0;
/* 134:    */     
/* 135:142 */     int row = 0;
/* 136:144 */     for (WiredPictureViewer pictureViewer : pictureViewers)
/* 137:    */     {
/* 138:146 */       if (limit-- <= 0) {
/* 139:147 */         return;
/* 140:    */       }
/* 141:149 */       String name = pictureViewer.getName();
/* 142:    */       try
/* 143:    */       {
/* 144:151 */         Mark.say(new Object[] {"Trying to add viewer for", name });
/* 145:152 */         pictureViewer.addMouseListener(getPreviewerListener());
/* 146:153 */         add(pictureViewer, makeString(column++, row));
/* 147:    */       }
/* 148:    */       catch (Exception e)
/* 149:    */       {
/* 150:156 */         Mark.err(new Object[] {"The video", name, "is not available now" });
/* 151:157 */         e.printStackTrace();
/* 152:158 */         JLabel label = new JLabel(name);
/* 153:159 */         label.setBorder(BorderFactory.createEtchedBorder());
/* 154:160 */         label.setName(name);
/* 155:161 */         label.setToolTipText("Cannot show movie preview, infrastructure not available");
/* 156:162 */         label.addMouseListener(getLableListener());
/* 157:163 */         add(label, makeString(column++, row));
/* 158:    */       }
/* 159:166 */       if (column >= nColumns)
/* 160:    */       {
/* 161:167 */         column = 0;
/* 162:168 */         row++;
/* 163:    */       }
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   private String makeString(int c, int r)
/* 168:    */   {
/* 169:174 */     String result = c + ", " + r;
/* 170:    */     
/* 171:176 */     return result;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public int getBestColumnNumber(int fileCount)
/* 175:    */   {
/* 176:180 */     if (fileCount == 0) {
/* 177:181 */       return 1;
/* 178:    */     }
/* 179:183 */     if (fileCount < 6) {
/* 180:184 */       return fileCount;
/* 181:    */     }
/* 182:186 */     double bestQuality = -1.0D;
/* 183:187 */     int bestRowCount = 1;
/* 184:188 */     for (int rows = 1; rows < fileCount; rows++)
/* 185:    */     {
/* 186:189 */       double filesPerRow = fileCount / rows;
/* 187:190 */       double quality = Math.abs(filesPerRow / rows - this.aspectRatio);
/* 188:191 */       if ((bestQuality < 0.0D) || (quality < bestQuality))
/* 189:    */       {
/* 190:192 */         bestQuality = quality;
/* 191:193 */         bestRowCount = rows;
/* 192:    */       }
/* 193:    */     }
/* 194:196 */     return fileCount / bestRowCount;
/* 195:    */   }
/* 196:    */   
/* 197:    */   private PreviewerListener getPreviewerListener()
/* 198:    */   {
/* 199:202 */     if (this.previewerListener == null) {
/* 200:203 */       this.previewerListener = new PreviewerListener(null);
/* 201:    */     }
/* 202:205 */     return this.previewerListener;
/* 203:    */   }
/* 204:    */   
/* 205:    */   private LabelListener getLableListener()
/* 206:    */   {
/* 207:211 */     if (this.lableListener == null) {
/* 208:212 */       this.lableListener = new LabelListener(null);
/* 209:    */     }
/* 210:214 */     return this.lableListener;
/* 211:    */   }
/* 212:    */   
/* 213:    */   private class LabelListener
/* 214:    */     extends MouseAdapter
/* 215:    */   {
/* 216:    */     private LabelListener() {}
/* 217:    */     
/* 218:    */     public void mouseClicked(MouseEvent e)
/* 219:    */     {
/* 220:220 */       Mark.say(
/* 221:    */       
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:226 */         new Object[] { "Hello LabelListener" });
/* 227:221 */       if ((e.getSource() instanceof JLabel))
/* 228:    */       {
/* 229:222 */         JLabel label = (JLabel)e.getSource();
/* 230:223 */         Mark.say(new Object[] {"Label is", label.getName() });
/* 231:224 */         Connections.getPorts(FXMediaSelector.this).transmit("selected file name", label.getText());
/* 232:    */       }
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   private class PreviewerListener
/* 237:    */     extends MouseAdapter
/* 238:    */   {
/* 239:    */     private PreviewerListener() {}
/* 240:    */     
/* 241:    */     public void mouseClicked(MouseEvent e)
/* 242:    */     {
/* 243:232 */       Mark.say(
/* 244:    */       
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:248 */         new Object[] { "Hello PreviewerListener" });
/* 260:233 */       if ((e.getButton() == 1) && 
/* 261:234 */         ((e.getSource() instanceof WiredPictureViewer)))
/* 262:    */       {
/* 263:235 */         WiredPictureViewer previewer = (WiredPictureViewer)e.getSource();
/* 264:    */         
/* 265:    */ 
/* 266:    */ 
/* 267:239 */         String name = previewer.getName();
/* 268:240 */         Mark.say(new Object[] {"Name for analysis is", name });
/* 269:    */         
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:245 */         Connections.getPorts(FXMediaSelector.this).transmit("selected file name", name);
/* 274:    */       }
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   class ProcessSelectedMovie
/* 279:    */     extends Thread
/* 280:    */   {
/* 281:    */     String name;
/* 282:    */     
/* 283:    */     public ProcessSelectedMovie(String name)
/* 284:    */     {
/* 285:255 */       this.name = name;
/* 286:    */     }
/* 287:    */     
/* 288:    */     public void run()
/* 289:    */     {
/* 290:259 */       Connections.getPorts(FXMediaSelector.this).transmit("selected file name", this.name);
/* 291:    */     }
/* 292:    */   }
/* 293:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mediaFX.FXMediaSelector
 * JD-Core Version:    0.7.0.1
 */