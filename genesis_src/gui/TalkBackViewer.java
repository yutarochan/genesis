/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import java.awt.BorderLayout;
/*  6:   */ import java.util.Vector;
/*  7:   */ import javax.swing.JScrollPane;
/*  8:   */ import javax.swing.JTextArea;
/*  9:   */ 
/* 10:   */ public class TalkBackViewer
/* 11:   */   extends WiredPanel
/* 12:   */ {
/* 13:20 */   private JTextArea textArea = new JTextArea();
/* 14:   */   private JScrollPane scroller;
/* 15:24 */   private Vector<String> sentences = new Vector();
/* 16:   */   
/* 17:   */   public TalkBackViewer()
/* 18:   */   {
/* 19:27 */     setLayout(new BorderLayout());
/* 20:28 */     this.scroller = new JScrollPane(this.textArea);
/* 21:29 */     add(this.scroller);
/* 22:   */     
/* 23:31 */     Connections.getPorts(this).addSignalProcessor("add");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void add(Object input)
/* 27:   */   {
/* 28:36 */     String sentence = (String)input;
/* 29:37 */     this.sentences.add(sentence);
/* 30:38 */     display();
/* 31:   */   }
/* 32:   */   
/* 33:   */   private void display()
/* 34:   */   {
/* 35:42 */     String text = "Sentences: \n";
/* 36:43 */     for (int i = 0; i < this.sentences.size(); i++)
/* 37:   */     {
/* 38:44 */       text = text + (String)this.sentences.get(i);
/* 39:45 */       text = text + "\n";
/* 40:   */     }
/* 41:47 */     this.textArea.setText(text);
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TalkBackViewer
 * JD-Core Version:    0.7.0.1
 */