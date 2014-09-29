/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Ports;
/*   5:    */ import connections.WiredBox;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Container;
/*   8:    */ import java.awt.Dimension;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import javax.swing.JFrame;
/*  11:    */ import javax.swing.JScrollPane;
/*  12:    */ import javax.swing.JTextPane;
/*  13:    */ import text.Html;
/*  14:    */ import text.Punctuator;
/*  15:    */ import utils.Colors;
/*  16:    */ import utils.Mark;
/*  17:    */ 
/*  18:    */ public class TextViewer
/*  19:    */   extends JScrollPane
/*  20:    */   implements WiredBox
/*  21:    */ {
/*  22:    */   public static final String CLEAR = "clear";
/*  23:    */   public static final String REPLY = "reply";
/*  24:    */   public static final String TEXT = "text";
/*  25: 31 */   private String text = "";
/*  26: 33 */   private String header = "<html><body>";
/*  27: 35 */   private String trailer = "</body></html>";
/*  28: 37 */   private JTextPane label = new JTextPane();
/*  29:    */   private String previousText;
/*  30:    */   private TabbedTextViewer container;
/*  31:    */   
/*  32:    */   public TextViewer()
/*  33:    */   {
/*  34: 46 */     this.label.setPreferredSize(new Dimension(300, 2000));
/*  35: 47 */     this.label.setContentType("text/html");
/*  36: 48 */     setViewportView(this.label);
/*  37: 49 */     setName("Text viewer");
/*  38: 50 */     setOpaque(true);
/*  39: 51 */     Connections.getPorts(this).addSignalProcessor("process");
/*  40: 52 */     Connections.getPorts(this).addSignalProcessor("reset", "clearText");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void process(Object o)
/*  44:    */   {
/*  45: 57 */     processViaDirectCall(o);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void processViaDirectCall(Object o)
/*  49:    */   {
/*  50: 61 */     if (o == "clear")
/*  51:    */     {
/*  52: 62 */       clear();
/*  53: 63 */       return;
/*  54:    */     }
/*  55: 66 */     addText((String)o);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public TextViewer(TabbedTextViewer tabbedTextViewerX)
/*  59:    */   {
/*  60: 70 */     this();
/*  61: 71 */     this.container = tabbedTextViewerX;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void clearText(Object object)
/*  65:    */   {
/*  66: 75 */     clear();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void replyText(Object object)
/*  70:    */   {
/*  71: 79 */     clear();
/*  72: 80 */     System.out.println("Object is " + object);
/*  73: 81 */     addText(object);
/*  74: 82 */     this.label.setBackground(Colors.REPLY_COLOR);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void addText(Object o)
/*  78:    */   {
/*  79: 86 */     this.label.setBackground(Color.WHITE);
/*  80: 87 */     String s = o.toString();
/*  81:    */     
/*  82: 89 */     s.equals(this.previousText);
/*  83:    */     
/*  84:    */ 
/*  85: 92 */     this.previousText = s.toString();
/*  86:    */     
/*  87:    */ 
/*  88: 95 */     this.text += Punctuator.addSpace(s);
/*  89:    */     
/*  90: 97 */     setText(this.text);
/*  91: 98 */     scrollToEnd();
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void setText(String s)
/*  95:    */   {
/*  96:102 */     String contents = this.header + Html.normal(s) + this.trailer;
/*  97:103 */     String stuff = Html.convertLf(contents);
/*  98:    */     try
/*  99:    */     {
/* 100:106 */       this.label.setText(stuff);
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:109 */       Mark.say(new Object[] {"Error!!!\n>>", s, "\n>>", contents, "\n>>", stuff });
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void scrollToEnd()
/* 109:    */   {
/* 110:120 */     this.label.selectAll();
/* 111:121 */     int x = this.label.getSelectionEnd();
/* 112:122 */     this.label.select(x, x);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void clear()
/* 116:    */   {
/* 117:127 */     this.text = "";
/* 118:128 */     setText(this.text);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static void main(String[] args)
/* 122:    */   {
/* 123:135 */     JFrame frame = new JFrame();
/* 124:136 */     TabbedTextViewer viewer = new TabbedTextViewer();
/* 125:137 */     viewer.setBackground(Color.WHITE);
/* 126:138 */     viewer.setOpaque(true);
/* 127:139 */     frame.getContentPane().add(viewer);
/* 128:140 */     frame.setBounds(0, 0, 500, 700);
/* 129:141 */     viewer.switchTab("Test 1");
/* 130:142 */     viewer.process("Hello");
/* 131:    */     
/* 132:144 */     viewer.process("World.");
/* 133:145 */     viewer.switchTab("Test 2");
/* 134:146 */     viewer.process(Html.h1("Hello World"));
/* 135:147 */     viewer.process("Lady Macbeth wanted Macbeth to murder Duncan.");
/* 136:148 */     viewer.process(Html.red("Found instance") + " " + Html.ital("of success") + " tomorrow.");
/* 137:149 */     frame.setVisible(true);
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TextViewer
 * JD-Core Version:    0.7.0.1
 */