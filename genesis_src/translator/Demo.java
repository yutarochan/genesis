/*   1:    */ package translator;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import connections.Connections;
/*   6:    */ import connections.Ports;
/*   7:    */ import gui.NewFrameViewer;
/*   8:    */ import java.awt.BorderLayout;
/*   9:    */ import java.awt.Container;
/*  10:    */ import java.awt.GridLayout;
/*  11:    */ import java.awt.event.ActionEvent;
/*  12:    */ import java.awt.event.ActionListener;
/*  13:    */ import java.awt.event.KeyEvent;
/*  14:    */ import java.awt.event.KeyListener;
/*  15:    */ import java.io.PrintStream;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import javax.swing.JButton;
/*  18:    */ import javax.swing.JFrame;
/*  19:    */ import javax.swing.JPanel;
/*  20:    */ import utils.WiredTextField;
/*  21:    */ 
/*  22:    */ public class Demo
/*  23:    */   extends JPanel
/*  24:    */ {
/*  25: 16 */   private final JButton stepButton = new JButton("Step forward");
/*  26: 18 */   private final JButton retreatButton = new JButton("Retreat");
/*  27: 20 */   private final JButton runButton = new JButton("Run");
/*  28: 22 */   final HardWiredTranslator hardWiredTranslator = new HardWiredTranslator();
/*  29: 24 */   private String sentence = "";
/*  30: 26 */   public static final WiredTextField textField = new WiredTextField();
/*  31:    */   
/*  32:    */   public Demo()
/*  33:    */   {
/*  34: 29 */     this.stepButton.addActionListener(new ButtonActionListener(null));
/*  35: 30 */     this.retreatButton.addActionListener(new ButtonActionListener(null));
/*  36: 31 */     this.runButton.addActionListener(new ButtonActionListener(null));
/*  37:    */     
/*  38: 33 */     setLayout(new BorderLayout());
/*  39: 34 */     textField.addKeyListener(new PunctuationListener());
/*  40: 35 */     NewFrameViewer viewer = new NewFrameViewer();
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45: 40 */     Connections.wire(HardWiredTranslator.PROGRESS, this.hardWiredTranslator, viewer);
/*  46:    */     
/*  47:    */ 
/*  48: 43 */     add(viewer, "Center");
/*  49: 44 */     add(textField, "South");
/*  50: 45 */     JPanel buttonPanel = new JPanel();
/*  51: 46 */     buttonPanel.setLayout(new GridLayout(1, 0));
/*  52: 47 */     buttonPanel.add(this.stepButton);
/*  53: 48 */     buttonPanel.add(this.retreatButton);
/*  54: 49 */     buttonPanel.add(this.runButton);
/*  55: 50 */     add(buttonPanel, "North");
/*  56: 51 */     textField.requestFocusInWindow();
/*  57:    */   }
/*  58:    */   
/*  59:    */   private class ButtonActionListener
/*  60:    */     implements ActionListener
/*  61:    */   {
/*  62:    */     private ButtonActionListener() {}
/*  63:    */     
/*  64:    */     public void actionPerformed(ActionEvent e)
/*  65:    */     {
/*  66: 56 */       if (e.getSource() == Demo.this.stepButton)
/*  67:    */       {
/*  68: 57 */         Demo.this.hardWiredTranslator.step();
/*  69:    */       }
/*  70: 61 */       else if (e.getSource() == Demo.this.retreatButton)
/*  71:    */       {
/*  72: 62 */         int steps = Demo.this.hardWiredTranslator.getTransformations().size();
/*  73: 63 */         steps--;
/*  74: 64 */         steps--;
/*  75: 65 */         String sentence = Demo.textField.getText();
/*  76: 66 */         sentence = removeStars(sentence);
/*  77: 67 */         Connections.getPorts(Demo.textField).transmit(sentence);
/*  78: 68 */         for (int i = 0; i < steps; i++) {
/*  79: 69 */           Demo.this.hardWiredTranslator.step();
/*  80:    */         }
/*  81:    */       }
/*  82: 72 */       else if (e.getSource() == Demo.this.runButton)
/*  83:    */       {
/*  84: 73 */         Demo.this.hardWiredTranslator.go();
/*  85:    */       }
/*  86:    */     }
/*  87:    */     
/*  88:    */     private String removeStars(String sentence)
/*  89:    */     {
/*  90:    */       for (;;)
/*  91:    */       {
/*  92: 79 */         int l = sentence.length();
/*  93: 80 */         char c = sentence.charAt(l - 1);
/*  94: 81 */         if ((c != '*') && (c != ' ')) {
/*  95:    */           break;
/*  96:    */         }
/*  97: 82 */         sentence = sentence.substring(0, l - 1);
/*  98:    */       }
/*  99: 88 */       return sentence;
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected class PunctuationListener
/* 104:    */     implements KeyListener
/* 105:    */   {
/* 106: 94 */     String steppers = "*";
/* 107: 96 */     String runners = "!&";
/* 108:    */     
/* 109:    */     protected PunctuationListener() {}
/* 110:    */     
/* 111:    */     public void keyTyped(KeyEvent e)
/* 112:    */     {
/* 113:100 */       String sent = null;
/* 114:101 */       char key = e.getKeyChar();
/* 115:102 */       if (this.steppers.indexOf(key) >= 0)
/* 116:    */       {
/* 117:105 */         if (countSteppers(Demo.textField.getText()) == 0) {
/* 118:107 */           Connections.getPorts(Demo.textField).transmit(Demo.textField.getText());
/* 119:    */         }
/* 120:109 */         Demo.this.hardWiredTranslator.step();
/* 121:    */       }
/* 122:111 */       else if (this.runners.indexOf(key) >= 0)
/* 123:    */       {
/* 124:112 */         Demo.this.hardWiredTranslator.go();
/* 125:    */       }
/* 126:    */     }
/* 127:    */     
/* 128:    */     private int countSteppers(String text)
/* 129:    */     {
/* 130:118 */       int count = 0;
/* 131:119 */       for (int s = 0; s < this.steppers.length(); s++)
/* 132:    */       {
/* 133:120 */         char c = this.steppers.charAt(s);
/* 134:121 */         for (int i = 0; i < text.length(); i++) {
/* 135:122 */           if (c == text.charAt(i)) {
/* 136:123 */             count++;
/* 137:    */           }
/* 138:    */         }
/* 139:    */       }
/* 140:127 */       return count;
/* 141:    */     }
/* 142:    */     
/* 143:    */     public void keyPressed(KeyEvent e) {}
/* 144:    */     
/* 145:    */     public void keyReleased(KeyEvent e) {}
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void main(String[] args)
/* 149:    */   {
/* 150:138 */     Demo d = new Demo();
/* 151:139 */     JFrame frame = new JFrame();
/* 152:140 */     frame.setDefaultCloseOperation(3);
/* 153:141 */     frame.getContentPane().add(d);
/* 154:142 */     frame.setSize(800, 600);
/* 155:143 */     frame.setVisible(true);
/* 156:    */     
/* 157:145 */     Entity t = new Entity();
/* 158:146 */     System.out.println(new Function("foo", t));
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.Demo
 * JD-Core Version:    0.7.0.1
 */