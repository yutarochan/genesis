/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Thread;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.Font;
/*   8:    */ import java.awt.GridLayout;
/*   9:    */ import java.awt.event.ActionEvent;
/*  10:    */ import java.awt.event.ActionListener;
/*  11:    */ import java.awt.event.KeyEvent;
/*  12:    */ import java.awt.event.KeyListener;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Set;
/*  17:    */ import javax.swing.BoxLayout;
/*  18:    */ import javax.swing.JButton;
/*  19:    */ import javax.swing.JFrame;
/*  20:    */ import javax.swing.JLabel;
/*  21:    */ import javax.swing.JPanel;
/*  22:    */ import javax.swing.JSplitPane;
/*  23:    */ import javax.swing.JTextArea;
/*  24:    */ import javax.swing.JTextField;
/*  25:    */ import javax.swing.UIManager;
/*  26:    */ import javax.swing.UnsupportedLookAndFeelException;
/*  27:    */ 
/*  28:    */ public class UI
/*  29:    */   extends JFrame
/*  30:    */ {
/*  31:    */   private JButton clearButton;
/*  32:    */   private JTextArea text;
/*  33:    */   private JTextArea analysis;
/*  34:    */   private JTextField questionField;
/*  35:    */   private JLabel instructLbl;
/*  36:    */   private LatticePanel lp;
/*  37:    */   
/*  38:    */   public UI()
/*  39:    */   {
/*  40: 38 */     this.text = new JTextArea("entity animate animal mammal cat\nmammal dog\nanimal fish\nanimate plant\nentity inanimate rug\ninanimate car\n");
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46: 44 */     this.text.setLineWrap(true);
/*  47: 45 */     this.text.setWrapStyleWord(true);
/*  48:    */     
/*  49: 47 */     setSize(1024, 768);
/*  50:    */     
/*  51: 49 */     this.text.setLineWrap(true);
/*  52: 50 */     this.text.setWrapStyleWord(true);
/*  53: 51 */     this.text.setEditable(false);
/*  54: 52 */     this.text.setPreferredSize(new Dimension(getWidth() / 2, getWidth() / 2));
/*  55:    */     
/*  56: 54 */     this.questionField = new JTextField();
/*  57: 55 */     this.questionField.addKeyListener(new KeyListener()
/*  58:    */     {
/*  59:    */       public void keyPressed(KeyEvent arg0) {}
/*  60:    */       
/*  61:    */       public void keyReleased(KeyEvent arg0) {}
/*  62:    */       
/*  63:    */       public void keyTyped(KeyEvent arg0)
/*  64:    */       {
/*  65: 64 */         if (KeyEvent.getKeyText(arg0.getKeyChar()).equalsIgnoreCase("Enter"))
/*  66:    */         {
/*  67: 65 */           String question = UI.this.questionField.getText();
/*  68: 66 */           String answer = "";
/*  69:    */           try
/*  70:    */           {
/*  71: 68 */             answer = LatticeLearner.answerQuestion(question, UI.this.lp.getConcept().getPositives(), UI.this.lp.getConcept().getNegatives());
/*  72:    */           }
/*  73:    */           catch (Exception e)
/*  74:    */           {
/*  75: 70 */             answer = "An error occurred. Check for spelling/grammar errors.";
/*  76: 71 */             e.printStackTrace();
/*  77:    */           }
/*  78: 73 */           UI.this.instructLbl.setText(question + " " + answer);
/*  79: 74 */           UI.this.questionField.setText("");
/*  80:    */         }
/*  81:    */       }
/*  82: 78 */     });
/*  83: 79 */     this.analysis = new JTextArea();
/*  84: 80 */     this.analysis.setEditable(false);
/*  85: 81 */     this.analysis.setFont(this.analysis.getFont().deriveFont(20.0F));
/*  86: 82 */     this.analysis.setWrapStyleWord(true);
/*  87:    */     
/*  88: 84 */     this.clearButton = new JButton("Clear Lattice");
/*  89: 85 */     this.clearButton.setSize(new Dimension(this.text.getSize().width, 20));
/*  90: 86 */     JPanel right = new JPanel();
/*  91: 87 */     right.setLayout(new BoxLayout(right, 3));
/*  92: 88 */     right.setBackground(Color.WHITE);
/*  93: 89 */     right.add(this.text);
/*  94: 90 */     right.add(this.analysis);
/*  95: 91 */     right.add(this.questionField);
/*  96: 92 */     right.add(this.clearButton);
/*  97:    */     
/*  98: 94 */     List<Thread> threads = new ArrayList();
/*  99: 95 */     for (String string : this.text.getText().split("\n")) {
/* 100: 96 */       threads.add(Thread.parse(string));
/* 101:    */     }
/* 102: 98 */     TypeLattice lattice = new TypeLattice(threads);
/* 103: 99 */     this.lp = new LatticePanel(lattice, this.analysis);
/* 104:100 */     JSplitPane split = new JSplitPane(1, this.lp, right);
/* 105:101 */     split.setResizeWeight(1.0D);
/* 106:102 */     split.setContinuousLayout(true);
/* 107:    */     
/* 108:104 */     getContentPane().setLayout(new GridLayout());
/* 109:105 */     getContentPane().add(split);
/* 110:    */     
/* 111:107 */     setDefaultCloseOperation(3);
/* 112:108 */     this.clearButton.addActionListener(new ActionListener()
/* 113:    */     {
/* 114:    */       public void actionPerformed(ActionEvent arg0)
/* 115:    */       {
/* 116:112 */         List<Thread> threads = new ArrayList();
/* 117:113 */         for (String string : UI.this.text.getText().split("\n")) {
/* 118:114 */           threads.add(Thread.parse(string));
/* 119:    */         }
/* 120:116 */         UI.this.lp.setLattice(new TypeLattice(threads));
/* 121:117 */         UI.this.lp.repaint();
/* 122:    */       }
/* 123:    */     });
/* 124:    */   }
/* 125:    */   
/* 126:    */   public UI(Object[] entities)
/* 127:    */   {
/* 128:123 */     String tempString = "";
/* 129:124 */     for (Object entity : entities) {
/* 130:125 */       tempString = tempString.concat(entity + "\n\n");
/* 131:    */     }
/* 132:127 */     setSize(1024, 768);
/* 133:128 */     this.text = new JTextArea(tempString);
/* 134:129 */     this.text.setLineWrap(true);
/* 135:130 */     this.text.setWrapStyleWord(true);
/* 136:131 */     this.text.setEditable(false);
/* 137:132 */     this.text.setPreferredSize(new Dimension(getWidth() / 2, getWidth() / 2));
/* 138:133 */     this.instructLbl = new JLabel("Ask a question or make a statement.");
/* 139:134 */     this.instructLbl.setAlignmentX(0.5F);
/* 140:135 */     this.questionField = new JTextField();
/* 141:136 */     this.questionField.setMaximumSize(new Dimension(getWidth() / 2, 50));
/* 142:137 */     this.questionField.addKeyListener(new KeyListener()
/* 143:    */     {
/* 144:    */       public void keyPressed(KeyEvent arg0) {}
/* 145:    */       
/* 146:    */       public void keyReleased(KeyEvent arg0) {}
/* 147:    */       
/* 148:    */       public void keyTyped(KeyEvent arg0)
/* 149:    */       {
/* 150:146 */         if (KeyEvent.getKeyText(arg0.getKeyChar()).equalsIgnoreCase("Enter"))
/* 151:    */         {
/* 152:147 */           String question = UI.this.questionField.getText();
/* 153:148 */           String answer = "";
/* 154:    */           try
/* 155:    */           {
/* 156:150 */             answer = LatticeLearner.answerQuestion(question, UI.this.lp.getPositives(), UI.this.lp.getNegatives());
/* 157:    */           }
/* 158:    */           catch (Exception e)
/* 159:    */           {
/* 160:152 */             answer = "An error occurred.";
/* 161:153 */             e.printStackTrace();
/* 162:    */           }
/* 163:155 */           UI.this.instructLbl.setText(question + " " + answer);
/* 164:156 */           UI.this.questionField.setText("");
/* 165:    */         }
/* 166:    */       }
/* 167:160 */     });
/* 168:161 */     this.analysis = new JTextArea();
/* 169:162 */     this.analysis.setEditable(false);
/* 170:163 */     this.analysis.setFont(this.analysis.getFont().deriveFont(20.0F));
/* 171:164 */     this.analysis.setWrapStyleWord(true);
/* 172:    */     
/* 173:166 */     this.clearButton = new JButton("Clear Lattice");
/* 174:167 */     this.clearButton.setMaximumSize(new Dimension(getWidth() / 2, 50));
/* 175:168 */     JPanel right = new JPanel();
/* 176:169 */     right.setLayout(new BoxLayout(right, 1));
/* 177:    */     
/* 178:171 */     right.setBackground(Color.WHITE);
/* 179:172 */     right.add(this.clearButton);
/* 180:173 */     right.add(this.text);
/* 181:174 */     right.add(this.analysis);
/* 182:175 */     right.add(this.instructLbl);
/* 183:176 */     right.add(this.questionField);
/* 184:    */     
/* 185:178 */     Object threads = new ArrayList();
/* 186:179 */     for (String string : this.text.getText().split("\n"))
/* 187:    */     {
/* 188:180 */       System.out.println(string);
/* 189:181 */       ((List)threads).add(Thread.parse(string));
/* 190:    */     }
/* 191:183 */     TypeLattice lattice = new TypeLattice((Iterable)threads);
/* 192:184 */     this.lp = new LatticePanel(lattice, this.analysis);
/* 193:185 */     JSplitPane split = new JSplitPane(1, this.lp, right);
/* 194:186 */     split.setResizeWeight(1.0D);
/* 195:187 */     split.setContinuousLayout(true);
/* 196:    */     
/* 197:189 */     getContentPane().setLayout(new GridLayout());
/* 198:190 */     getContentPane().add(split);
/* 199:    */     
/* 200:192 */     setDefaultCloseOperation(3);
/* 201:193 */     this.clearButton.addActionListener(new ActionListener()
/* 202:    */     {
/* 203:    */       public void actionPerformed(ActionEvent arg0)
/* 204:    */       {
/* 205:196 */         List<Thread> threads = new ArrayList();
/* 206:197 */         for (String string : UI.this.text.getText().split("\n")) {
/* 207:198 */           threads.add(Thread.parse(string));
/* 208:    */         }
/* 209:200 */         UI.this.lp.setLattice(new TypeLattice(threads));
/* 210:201 */         UI.this.lp.repaint();
/* 211:202 */         LatticeLearner.getImpliedPositives().clear();
/* 212:203 */         LatticeLearner.getImpliedNegatives().clear();
/* 213:204 */         UI.this.instructLbl.setText("Ask a question or make a statement.");
/* 214:    */       }
/* 215:    */     });
/* 216:    */   }
/* 217:    */   
/* 218:    */   public LatticePanel getLP()
/* 219:    */   {
/* 220:210 */     return this.lp;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public static void main(String[] args)
/* 224:    */   {
/* 225:    */     try
/* 226:    */     {
/* 227:215 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/* 228:    */     }
/* 229:    */     catch (UnsupportedLookAndFeelException localUnsupportedLookAndFeelException) {}catch (IllegalAccessException localIllegalAccessException) {}catch (InstantiationException localInstantiationException) {}catch (ClassNotFoundException localClassNotFoundException) {}
/* 230:226 */     String[] testArray = { "entity animate animal mammal cat", "mammal dog", "animal fish", "animate plant", "entity inanimate rug", "inanimate car" };
/* 231:    */     
/* 232:    */ 
/* 233:229 */     UI arrayUI = new UI(testArray);
/* 234:230 */     arrayUI.setVisible(true);
/* 235:    */   }
/* 236:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.UI
 * JD-Core Version:    0.7.0.1
 */