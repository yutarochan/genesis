/*   1:    */ package obsolete.mindsEye;
/*   2:    */ 
/*   3:    */ import adamKraft.Co57Connector;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Dimension;
/*   9:    */ import java.awt.event.MouseAdapter;
/*  10:    */ import java.awt.event.MouseEvent;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import javax.swing.BorderFactory;
/*  14:    */ import javax.swing.JLabel;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import layout.TableLayout;
/*  17:    */ import utils.Mark;
/*  18:    */ 
/*  19:    */ public class MovieSelector
/*  20:    */   extends JPanel
/*  21:    */   implements WiredBox
/*  22:    */ {
/*  23: 23 */   double aspectRatio = 2.0D;
/*  24: 25 */   public static String SELECTED_FILE_NAME = "selected file name";
/*  25:    */   Co57Connector co57Connector;
/*  26:    */   PreviewerListener previewerListener;
/*  27:    */   LabelListener lableListener;
/*  28:    */   
/*  29:    */   public Co57Connector getCo57Connector()
/*  30:    */   {
/*  31: 30 */     this.co57Connector = new Co57Connector();
/*  32: 31 */     return this.co57Connector;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public MovieSelector()
/*  36:    */   {
/*  37: 35 */     Connections.getPorts(this).addSignalProcessor("reinitialize");
/*  38: 36 */     setBackground(Color.WHITE);
/*  39: 37 */     setOpaque(true);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void reinitialize(Object signal)
/*  43:    */   {
/*  44: 41 */     if ((signal instanceof ArrayList))
/*  45:    */     {
/*  46: 42 */       ArrayList returnedFileNames = (ArrayList)signal;
/*  47: 43 */       Mark.say(new Object[] {"Obtained", Integer.valueOf(returnedFileNames.size()), "movie names from Co57!!!!!!!!!!!!!!!!!!!!!!!!!!!!" });
/*  48:    */       
/*  49: 45 */       initialize(returnedFileNames);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void initialize(ArrayList<String> fileNames)
/*  54:    */   {
/*  55: 51 */     int limit = 30;
/*  56:    */     
/*  57: 53 */     ArrayList<MindsEyeMoviePlayer> players = new ArrayList();
/*  58: 54 */     Mark.say(new Object[] {"The names are:" });
/*  59: 55 */     for (String name : fileNames) {
/*  60:    */       try
/*  61:    */       {
/*  62: 57 */         Mark.say(new Object[] {name });
/*  63: 58 */         MindsEyeMoviePlayer player = new MindsEyeMoviePlayer(name);
/*  64: 59 */         players.add(player);
/*  65:    */       }
/*  66:    */       catch (IOException e)
/*  67:    */       {
/*  68: 62 */         Mark.err(new Object[] {"Unable to make movie player for", name });
/*  69:    */       }
/*  70:    */     }
/*  71: 67 */     setPreferredSize(new Dimension(1000, 100));
/*  72: 68 */     Mark.say(new Object[] {"Initializing movie selector" });
/*  73: 69 */     removeAll();
/*  74:    */     
/*  75: 71 */     int total = players.size();
/*  76:    */     
/*  77: 73 */     int count = Math.min(total, limit);
/*  78:    */     
/*  79: 75 */     int nColumns = getBestColumnNumber(count);
/*  80: 76 */     int nRows = count / nColumns;
/*  81: 78 */     if (count % nColumns > 0) {
/*  82: 79 */       nRows++;
/*  83:    */     }
/*  84: 82 */     double[] columns = new double[nColumns];
/*  85: 83 */     double[] rows = new double[nRows];
/*  86: 84 */     for (int n = 0; n < nColumns; n++) {
/*  87: 85 */       columns[n] = -1.0D;
/*  88:    */     }
/*  89: 88 */     for (int n = 0; n < nRows; n++) {
/*  90: 89 */       rows[n] = -1.0D;
/*  91:    */     }
/*  92: 92 */     double[][] size = { columns, rows };
/*  93:    */     
/*  94: 94 */     Mark.say(new Object[] {"Rows/columns", Integer.valueOf(rows.length), Integer.valueOf(columns.length) });
/*  95:    */     
/*  96: 96 */     setLayout(new TableLayout(size));
/*  97:    */     
/*  98: 98 */     int column = 0;
/*  99:    */     
/* 100:100 */     int row = 0;
/* 101:102 */     for (MindsEyeMoviePlayer player : players)
/* 102:    */     {
/* 103:104 */       if (limit-- <= 0) {
/* 104:105 */         return;
/* 105:    */       }
/* 106:107 */       String name = player.getMovieTitle();
/* 107:    */       try
/* 108:    */       {
/* 109:109 */         Mark.say(new Object[] {"Trying to add viewer for", name });
/* 110:110 */         MindsEyeMovieViewerScreen previewer = new MindsEyeMovieViewerScreen(name);
/* 111:111 */         previewer.setImage(player.display());
/* 112:    */         
/* 113:113 */         previewer.addMouseListener(getPreviewerListener());
/* 114:114 */         add(previewer, makeString(column++, row));
/* 115:    */       }
/* 116:    */       catch (Exception e)
/* 117:    */       {
/* 118:117 */         Mark.err(new Object[] {"The video", name, "is not available now" });
/* 119:118 */         e.printStackTrace();
/* 120:119 */         JLabel label = new JLabel(name);
/* 121:120 */         label.setBorder(BorderFactory.createEtchedBorder());
/* 122:121 */         label.setName(name);
/* 123:122 */         label.setToolTipText("Cannot show movie preview, infrastructure not available");
/* 124:123 */         label.addMouseListener(getLableListener());
/* 125:124 */         add(label, makeString(column++, row));
/* 126:    */       }
/* 127:127 */       if (column >= nColumns)
/* 128:    */       {
/* 129:128 */         column = 0;
/* 130:129 */         row++;
/* 131:    */       }
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private String makeString(int c, int r)
/* 136:    */   {
/* 137:135 */     String result = c + ", " + r;
/* 138:    */     
/* 139:137 */     return result;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getBestColumnNumber(int fileCount)
/* 143:    */   {
/* 144:141 */     if (fileCount == 0) {
/* 145:142 */       return 1;
/* 146:    */     }
/* 147:144 */     if (fileCount < 6) {
/* 148:145 */       return fileCount;
/* 149:    */     }
/* 150:147 */     double bestQuality = -1.0D;
/* 151:148 */     int bestRowCount = 1;
/* 152:149 */     for (int rows = 1; rows < fileCount; rows++)
/* 153:    */     {
/* 154:150 */       double filesPerRow = fileCount / rows;
/* 155:151 */       double quality = Math.abs(filesPerRow / rows - this.aspectRatio);
/* 156:152 */       if ((bestQuality < 0.0D) || (quality < bestQuality))
/* 157:    */       {
/* 158:153 */         bestQuality = quality;
/* 159:154 */         bestRowCount = rows;
/* 160:    */       }
/* 161:    */     }
/* 162:157 */     return fileCount / bestRowCount;
/* 163:    */   }
/* 164:    */   
/* 165:    */   private PreviewerListener getPreviewerListener()
/* 166:    */   {
/* 167:163 */     if (this.previewerListener == null) {
/* 168:164 */       this.previewerListener = new PreviewerListener(null);
/* 169:    */     }
/* 170:166 */     return this.previewerListener;
/* 171:    */   }
/* 172:    */   
/* 173:    */   private LabelListener getLableListener()
/* 174:    */   {
/* 175:172 */     if (this.lableListener == null) {
/* 176:173 */       this.lableListener = new LabelListener(null);
/* 177:    */     }
/* 178:175 */     return this.lableListener;
/* 179:    */   }
/* 180:    */   
/* 181:    */   private class LabelListener
/* 182:    */     extends MouseAdapter
/* 183:    */   {
/* 184:    */     private LabelListener() {}
/* 185:    */     
/* 186:    */     public void mouseClicked(MouseEvent e)
/* 187:    */     {
/* 188:180 */       if ((e.getSource() instanceof JLabel))
/* 189:    */       {
/* 190:181 */         JLabel label = (JLabel)e.getSource();
/* 191:182 */         Mark.say(new Object[] {"Label is", label.getName() });
/* 192:183 */         Connections.getPorts(MovieSelector.this).transmit(MovieSelector.SELECTED_FILE_NAME, label.getText());
/* 193:    */       }
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   private class PreviewerListener
/* 198:    */     extends MouseAdapter
/* 199:    */   {
/* 200:    */     private PreviewerListener() {}
/* 201:    */     
/* 202:    */     public void mouseClicked(MouseEvent e)
/* 203:    */     {
/* 204:191 */       if ((e.getButton() == 1) && 
/* 205:192 */         ((e.getSource() instanceof MindsEyeMovieViewerScreen)))
/* 206:    */       {
/* 207:193 */         MindsEyeMovieViewerScreen previewer = (MindsEyeMovieViewerScreen)e.getSource();
/* 208:    */         
/* 209:    */ 
/* 210:    */ 
/* 211:197 */         String name = previewer.getMovieName();
/* 212:198 */         Mark.say(new Object[] {"Name for analysis is", name });
/* 213:    */         
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:203 */         Connections.getPorts(MovieSelector.this).transmit(MovieSelector.SELECTED_FILE_NAME, name);
/* 218:    */       }
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   class ProcessSelectedMovie
/* 223:    */     extends Thread
/* 224:    */   {
/* 225:    */     String name;
/* 226:    */     
/* 227:    */     public ProcessSelectedMovie(String name)
/* 228:    */     {
/* 229:213 */       this.name = name;
/* 230:    */     }
/* 231:    */     
/* 232:    */     public void run()
/* 233:    */     {
/* 234:217 */       Connections.getPorts(MovieSelector.this).transmit(MovieSelector.SELECTED_FILE_NAME, this.name);
/* 235:    */     }
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mindsEye.MovieSelector
 * JD-Core Version:    0.7.0.1
 */