/*  1:   */ package dictionary;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Thread;
/*  5:   */ import java.awt.BorderLayout;
/*  6:   */ import java.awt.Container;
/*  7:   */ import java.awt.Font;
/*  8:   */ import java.awt.event.KeyAdapter;
/*  9:   */ import java.awt.event.KeyEvent;
/* 10:   */ import java.util.Iterator;
/* 11:   */ import javax.swing.JFrame;
/* 12:   */ import javax.swing.JPanel;
/* 13:   */ import javax.swing.JScrollPane;
/* 14:   */ import javax.swing.JTextArea;
/* 15:   */ import javax.swing.JTextField;
/* 16:   */ import links.words.WordNet;
/* 17:   */ 
/* 18:   */ public class DictionaryPage
/* 19:   */   extends JPanel
/* 20:   */ {
/* 21:17 */   JTextArea definition = new JTextArea();
/* 22:19 */   JTextField word = new JTextField();
/* 23:   */   JScrollPane scroller;
/* 24:23 */   WordNet wordNet = new WordNet();
/* 25:   */   
/* 26:   */   public DictionaryPage()
/* 27:   */   {
/* 28:27 */     setLayout(new BorderLayout());
/* 29:28 */     this.scroller = new JScrollPane(this.definition);
/* 30:29 */     add(this.scroller, "Center");
/* 31:30 */     add(this.word, "South");
/* 32:31 */     this.word.addKeyListener(new MyWordListener());
/* 33:   */     
/* 34:33 */     this.definition.setFont(new Font("Helvetica", 1, 18));
/* 35:34 */     this.word.setFont(new Font("Helvetica", 1, 18));
/* 36:   */   }
/* 37:   */   
/* 38:   */   class MyWordListener
/* 39:   */     extends KeyAdapter
/* 40:   */   {
/* 41:   */     MyWordListener() {}
/* 42:   */     
/* 43:   */     public void keyTyped(KeyEvent event)
/* 44:   */     {
/* 45:39 */       String w = DictionaryPage.this.word.getText().trim();
/* 46:40 */       String result = "";
/* 47:41 */       DictionaryPage.this.definition.setText(result);
/* 48:42 */       if (w.length() != 0)
/* 49:   */       {
/* 50:43 */         if (('\n' == event.getKeyChar()) || ('.' == event.getKeyChar()))
/* 51:   */         {
/* 52:44 */           int index = DictionaryPage.this.word.getText().indexOf('.');
/* 53:45 */           if (index >= 0) {
/* 54:46 */             w = w.substring(0, index);
/* 55:   */           }
/* 56:48 */           Bundle bundles = DictionaryPage.this.wordNet.lookup(w);
/* 57:49 */           if (bundles.size() == 0) {
/* 58:50 */             result = result + "Nothing found in WordNet for " + w;
/* 59:   */           } else {
/* 60:53 */             for (Iterator i = bundles.iterator(); i.hasNext();)
/* 61:   */             {
/* 62:54 */               Thread thread = (Thread)i.next();
/* 63:55 */               for (Iterator j = thread.iterator(); j.hasNext();) {
/* 64:56 */                 result = result + j.next() + " ";
/* 65:   */               }
/* 66:58 */               result = result + "\n";
/* 67:   */             }
/* 68:   */           }
/* 69:   */         }
/* 70:62 */         DictionaryPage.this.definition.setText(result);
/* 71:   */       }
/* 72:   */     }
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static void main(String[] ignore)
/* 76:   */   {
/* 77:68 */     DictionaryPage page = new DictionaryPage();
/* 78:69 */     JFrame frame = new JFrame();
/* 79:70 */     frame.getContentPane().add(page);
/* 80:71 */     frame.setBounds(50, 100, 200, 300);
/* 81:72 */     frame.setVisible(true);
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dictionary.DictionaryPage
 * JD-Core Version:    0.7.0.1
 */