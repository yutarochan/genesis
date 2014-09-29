/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.Font;
/*   7:    */ import java.awt.GridLayout;
/*   8:    */ import java.awt.event.KeyEvent;
/*   9:    */ import java.awt.event.KeyListener;
/*  10:    */ import javax.swing.BoxLayout;
/*  11:    */ import javax.swing.JButton;
/*  12:    */ import javax.swing.JFrame;
/*  13:    */ import javax.swing.JLabel;
/*  14:    */ import javax.swing.JPanel;
/*  15:    */ import javax.swing.JSplitPane;
/*  16:    */ import javax.swing.JTextArea;
/*  17:    */ import javax.swing.JTextField;
/*  18:    */ 
/*  19:    */ public class AutomatedUI
/*  20:    */   extends JFrame
/*  21:    */ {
/*  22:    */   JTextArea text;
/*  23:    */   JTextArea analysis;
/*  24:    */   JLabel instructLbl;
/*  25:    */   JTextField questionField;
/*  26:    */   JButton clearButton;
/*  27:    */   private final AutomatedPanel lp;
/*  28:    */   
/*  29:    */   public AutomatedUI(AutomatedPanel latticePanel, String title)
/*  30:    */   {
/*  31: 41 */     this.lp = latticePanel;
/*  32: 42 */     String threadList = latticePanel.getThreadList();
/*  33: 43 */     setTitle(title);
/*  34: 44 */     setSize(1024, 768);
/*  35: 45 */     this.text = new JTextArea(threadList);
/*  36: 46 */     this.text.setLineWrap(true);
/*  37: 47 */     this.text.setWrapStyleWord(true);
/*  38: 48 */     this.text.setEditable(false);
/*  39: 49 */     this.text.setPreferredSize(new Dimension(getWidth() / 2, getWidth() / 2));
/*  40: 50 */     this.instructLbl = new JLabel("Ask a question or make a statement.");
/*  41: 51 */     this.instructLbl.setAlignmentX(0.5F);
/*  42: 52 */     this.questionField = new JTextField();
/*  43: 53 */     this.questionField.setMaximumSize(new Dimension(getWidth() / 2, 50));
/*  44: 54 */     this.questionField.addKeyListener(new KeyListener()
/*  45:    */     {
/*  46:    */       public void keyPressed(KeyEvent arg0) {}
/*  47:    */       
/*  48:    */       public void keyReleased(KeyEvent arg0) {}
/*  49:    */       
/*  50:    */       public void keyTyped(KeyEvent arg0)
/*  51:    */       {
/*  52: 63 */         if (KeyEvent.getKeyText(arg0.getKeyChar()).equalsIgnoreCase("Enter"))
/*  53:    */         {
/*  54: 64 */           String question = AutomatedUI.this.questionField.getText();
/*  55: 65 */           String answer = "";
/*  56:    */           try
/*  57:    */           {
/*  58: 67 */             answer = AutomatedLearner.answerQuestion(question, AutomatedUI.this.lp);
/*  59:    */           }
/*  60:    */           catch (Exception e)
/*  61:    */           {
/*  62: 69 */             answer = "An error occurred.";
/*  63: 70 */             e.printStackTrace();
/*  64:    */           }
/*  65: 72 */           AutomatedUI.this.instructLbl.setText(question + " " + answer);
/*  66: 73 */           AutomatedUI.this.questionField.setText("");
/*  67: 74 */           AutomatedUI.this.repaint();
/*  68: 75 */           AutomatedUI.this.revalidate();
/*  69:    */         }
/*  70:    */       }
/*  71: 80 */     });
/*  72: 81 */     this.analysis = latticePanel.getTextField();
/*  73: 82 */     this.analysis.setEditable(false);
/*  74: 83 */     this.analysis.setFont(this.analysis.getFont().deriveFont(20.0F));
/*  75: 84 */     this.analysis.setWrapStyleWord(true);
/*  76:    */     
/*  77: 86 */     this.clearButton = new JButton("Clear Lattice");
/*  78: 87 */     this.clearButton.setMaximumSize(new Dimension(getWidth() / 2, 50));
/*  79: 88 */     JPanel right = new JPanel();
/*  80: 89 */     right.setLayout(new BoxLayout(right, 1));
/*  81:    */     
/*  82: 91 */     right.setBackground(Color.WHITE);
/*  83: 92 */     right.add(this.clearButton);
/*  84: 93 */     right.add(this.text);
/*  85: 94 */     right.add(this.analysis);
/*  86: 95 */     right.add(this.instructLbl);
/*  87: 96 */     right.add(this.questionField);
/*  88:    */     
/*  89: 98 */     JSplitPane split = new JSplitPane(1, this.lp, right);
/*  90: 99 */     split.setResizeWeight(1.0D);
/*  91:100 */     split.setContinuousLayout(true);
/*  92:    */     
/*  93:102 */     getContentPane().setLayout(new GridLayout());
/*  94:103 */     getContentPane().add(split);
/*  95:104 */     setDefaultCloseOperation(3);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public AutomatedPanel getLP()
/*  99:    */   {
/* 100:108 */     return this.lp;
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.AutomatedUI
 * JD-Core Version:    0.7.0.1
 */