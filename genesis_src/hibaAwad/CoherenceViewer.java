/*   1:    */ package hibaAwad;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Ports;
/*   5:    */ import connections.WiredBox;
/*   6:    */ import java.awt.BorderLayout;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Container;
/*   9:    */ import java.awt.event.WindowAdapter;
/*  10:    */ import java.awt.event.WindowEvent;
/*  11:    */ import javax.swing.JFrame;
/*  12:    */ import javax.swing.JPanel;
/*  13:    */ import javax.swing.JTextPane;
/*  14:    */ import text.Html;
/*  15:    */ import text.Punctuator;
/*  16:    */ import utils.Mark;
/*  17:    */ 
/*  18:    */ public class CoherenceViewer
/*  19:    */   extends JPanel
/*  20:    */   implements WiredBox
/*  21:    */ {
/*  22:    */   public static final String CLEAR = "clear";
/*  23:    */   public static final String textPort = "My Text Port";
/*  24:    */   public static final String dataPort = "My Data Port";
/*  25:    */   public static final String labelPort = "My Label Port";
/*  26:    */   public static final String axisLabelPort = "Axis labels";
/*  27: 25 */   private String header = "<html><body style=" + Html.normal + ">";
/*  28: 26 */   private String trailer = "</body></html>";
/*  29: 28 */   private JTextPane label = new JTextPane();
/*  30: 29 */   private Spider spider = new Spider();
/*  31: 31 */   private String previousText = "";
/*  32: 32 */   private String text = "";
/*  33: 33 */   private String[] axisLabels = { "Number of chains", "Length of longest chain", "Number of caused events" };
/*  34:    */   
/*  35:    */   public CoherenceViewer()
/*  36:    */   {
/*  37: 35 */     setLayout(new BorderLayout());
/*  38: 36 */     this.label.setContentType("text/html");
/*  39:    */     
/*  40:    */ 
/*  41: 39 */     add(this.spider, "Center");
/*  42: 40 */     add(this.label, "Before");
/*  43:    */     
/*  44: 42 */     Connections.getPorts(this).addSignalProcessor("My Text Port", "processText");
/*  45: 43 */     Connections.getPorts(this).addSignalProcessor("My Data Port", "processData");
/*  46: 44 */     Connections.getPorts(this).addSignalProcessor("My Label Port", "processStoryLabels");
/*  47: 45 */     Connections.getPorts(this).addSignalProcessor("Axis labels", "processAxisLabels");
/*  48: 46 */     this.spider.setAxislabels(this.axisLabels);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void processData(Object o)
/*  52:    */   {
/*  53: 51 */     Mark.say(
/*  54:    */     
/*  55:    */ 
/*  56: 54 */       new Object[] { "Processing", o, "in Viewer viewer via call through direct wire", o.getClass() });processViaDirectCall(o);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void processViaDirectCall(Object o)
/*  60:    */   {
/*  61: 57 */     double[] datapoints = (double[])o;
/*  62: 58 */     this.spider.setData(datapoints);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void processStoryLabels(Object o)
/*  66:    */   {
/*  67: 63 */     Mark.say(
/*  68:    */     
/*  69:    */ 
/*  70:    */ 
/*  71: 67 */       new Object[] { "Processing", o, "in Coherence viewer via call through direct wire", o.getClass() });String label = (String)o;this.spider.addStoryLabel(label);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void processAxisLabels(Object o)
/*  75:    */   {
/*  76: 69 */     Mark.say(
/*  77:    */     
/*  78:    */ 
/*  79:    */ 
/*  80: 73 */       new Object[] { "here" });String[] labels = (String[])o;this.spider.setAxislabels(labels);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void processText(Object o)
/*  84:    */   {
/*  85: 76 */     if (o == "clear")
/*  86:    */     {
/*  87: 77 */       clear();
/*  88: 78 */       return;
/*  89:    */     }
/*  90: 81 */     addText((String)o);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void addText(Object o)
/*  94:    */   {
/*  95: 85 */     this.label.setBackground(Color.WHITE);
/*  96: 86 */     String s = o.toString();
/*  97:    */     
/*  98: 88 */     s.equals(this.previousText);
/*  99: 92 */     if (this.previousText.equals("")) {
/* 100: 93 */       s = Html.h1(s);
/* 101:    */     }
/* 102: 95 */     this.previousText = s.toString();
/* 103: 96 */     this.text = (this.text + Punctuator.addPeriod(s) + "\n");
/* 104:    */     
/* 105:    */ 
/* 106:    */ 
/* 107:100 */     setText(this.text);
/* 108:101 */     scrollToEnd();
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void scrollToEnd()
/* 112:    */   {
/* 113:105 */     this.label.selectAll();
/* 114:106 */     int x = this.label.getSelectionEnd();
/* 115:107 */     this.label.select(x, x);
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void setText(String s)
/* 119:    */   {
/* 120:111 */     String contents = this.header + Html.normal(s) + this.trailer;
/* 121:112 */     String stuff = Html.convertLf(contents);
/* 122:    */     
/* 123:114 */     this.label.setText(stuff);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void clear()
/* 127:    */   {
/* 128:119 */     this.text = "";
/* 129:120 */     setText(this.text);
/* 130:121 */     this.previousText = "";
/* 131:122 */     this.spider.clearData();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static void main(String[] ignore)
/* 135:    */   {
/* 136:128 */     CoherenceViewer viewer = new CoherenceViewer();
/* 137:129 */     double[] constant1 = { 1.0D, 0.1D, 0.3D, 0.5D, 0.7D, 0.9D };
/* 138:130 */     String[] labels = { "label1", "label2", "label3", "label4", "label5", "label6" };
/* 139:    */     
/* 140:132 */     JFrame frame = new JFrame();
/* 141:133 */     frame.getContentPane().add(viewer);
/* 142:134 */     frame.setBounds(100, 100, 500, 700);
/* 143:135 */     viewer.processText(Html.h1("h1"));
/* 144:136 */     viewer.processText("text");
/* 145:137 */     viewer.processData(constant1);
/* 146:138 */     viewer.processAxisLabels(labels);
/* 147:139 */     frame.addWindowListener(new WindowAdapter()
/* 148:    */     {
/* 149:    */       public void windowClosing(WindowEvent e)
/* 150:    */       {
/* 151:141 */         System.exit(0);
/* 152:    */       }
/* 153:143 */     });
/* 154:144 */     frame.show();
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     hibaAwad.CoherenceViewer
 * JD-Core Version:    0.7.0.1
 */