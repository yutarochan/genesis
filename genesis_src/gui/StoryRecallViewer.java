/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import java.awt.Color;
/*  6:   */ import java.awt.Graphics;
/*  7:   */ import java.awt.Graphics2D;
/*  8:   */ import java.text.DecimalFormat;
/*  9:   */ import java.util.Iterator;
/* 10:   */ import java.util.TreeSet;
/* 11:   */ import recall.MatchContribution;
/* 12:   */ import recall.MatchWrapper;
/* 13:   */ import recall.StoryVectorWrapper;
/* 14:   */ 
/* 15:   */ public class StoryRecallViewer
/* 16:   */   extends WiredPanel
/* 17:   */ {
/* 18:18 */   TreeSet<MatchWrapper> wrappers = new TreeSet();
/* 19:20 */   double maxDimensionValue = 0.0D;
/* 20:22 */   double graphWidth = 0.8D;
/* 21:24 */   private DecimalFormat twoPlaces = new DecimalFormat("0.00");
/* 22:   */   
/* 23:   */   public StoryRecallViewer()
/* 24:   */   {
/* 25:27 */     setBackground(Color.WHITE);
/* 26:28 */     setName("Story recall viewer");
/* 27:29 */     Connections.getPorts(this).addSignalProcessor("process");
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void process(Object o)
/* 31:   */   {
/* 32:33 */     if (!(o instanceof TreeSet)) {
/* 33:34 */       return;
/* 34:   */     }
/* 35:36 */     this.wrappers = ((TreeSet)o);
/* 36:37 */     calculateMaximumDimensionValue(this.wrappers);
/* 37:38 */     repaint();
/* 38:   */   }
/* 39:   */   
/* 40:   */   private void calculateMaximumDimensionValue(TreeSet<MatchWrapper> wrappers2)
/* 41:   */   {
/* 42:42 */     this.maxDimensionValue = 0.0D;
/* 43:   */     Iterator localIterator2;
/* 44:43 */     for (Iterator localIterator1 = this.wrappers.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 45:   */     {
/* 46:43 */       MatchWrapper w = (MatchWrapper)localIterator1.next();
/* 47:44 */       localIterator2 = w.getContributions().iterator(); continue;MatchContribution c = (MatchContribution)localIterator2.next();
/* 48:45 */       this.maxDimensionValue = Math.max(this.maxDimensionValue, c.getValue());
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void paintComponent(Graphics x)
/* 53:   */   {
/* 54:51 */     super.paintComponent(x);
/* 55:52 */     Graphics2D g = (Graphics2D)x;
/* 56:53 */     int height = (int)(0.95D * getHeight());
/* 57:54 */     int width = getWidth();
/* 58:56 */     if ((this.wrappers.isEmpty()) || (this.maxDimensionValue == 0.0D)) {
/* 59:57 */       return;
/* 60:   */     }
/* 61:59 */     double rowHeight = height / this.wrappers.size();
/* 62:60 */     int row = 0;
/* 63:61 */     for (MatchWrapper w : this.wrappers)
/* 64:   */     {
/* 65:63 */       drawWrapper(g, height, width, row, this.wrappers.size(), ((MatchWrapper)this.wrappers.first()).getContributions().size(), w);
/* 66:   */       
/* 67:65 */       row++;
/* 68:   */     }
/* 69:   */   }
/* 70:   */   
/* 71:   */   private void drawWrapper(Graphics2D g, int height, int width, int row, int rows, int columns, MatchWrapper w)
/* 72:   */   {
/* 73:71 */     String name = w.getPrecedent().getTitle();
/* 74:72 */     String value = this.twoPlaces.format(w.getSimilarity());
/* 75:73 */     int rowHeight = height / rows;
/* 76:74 */     int rowOffset = (row + 1) * rowHeight;
/* 77:75 */     int columnWidth = (int)(this.graphWidth * width / columns);
/* 78:76 */     int barWidth = (int)(0.9D * columnWidth);
/* 79:77 */     int barOffset = (int)(0.1D * rowHeight);
/* 80:78 */     int maxBarHeight = (int)(0.8D * rowHeight);
/* 81:79 */     g.drawString(name, 10, rowOffset - (int)(0.5D * rowHeight));
/* 82:80 */     g.drawString(value, 10, rowOffset - (int)(0.5D * rowHeight) + 15);
/* 83:   */     
/* 84:82 */     int bar = 0;
/* 85:83 */     int graphOffset = (int)((1.0D - this.graphWidth) * width);
/* 86:   */     
/* 87:85 */     int textXOffset = 5;
/* 88:86 */     int textYOffset = 10;
/* 89:88 */     for (MatchContribution c : w.getContributions())
/* 90:   */     {
/* 91:89 */       String barName = c.getDimension();
/* 92:90 */       int barHeight = (int)(c.getValue() * maxBarHeight / this.maxDimensionValue);
/* 93:91 */       g.setColor(Color.CYAN);
/* 94:92 */       g.fillRect(graphOffset + bar * columnWidth, rowOffset - barOffset - barHeight, barWidth, barHeight);
/* 95:93 */       g.setColor(Color.BLACK);
/* 96:94 */       g.drawRect(graphOffset + bar * columnWidth, rowOffset - barOffset - barHeight, barWidth, barHeight);
/* 97:95 */       g.drawString(barName, graphOffset + bar * columnWidth + textXOffset, rowOffset - textYOffset - barOffset);
/* 98:96 */       bar++;
/* 99:   */     }
/* :0:   */   }
/* :1:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.StoryRecallViewer
 * JD-Core Version:    0.7.0.1
 */