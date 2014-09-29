/*   1:    */ package m2.gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Thread;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.awt.BorderLayout;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.awt.Dimension;
/*  10:    */ import java.awt.FlowLayout;
/*  11:    */ import java.awt.Font;
/*  12:    */ import java.awt.event.MouseEvent;
/*  13:    */ import java.awt.event.MouseListener;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import javax.swing.BorderFactory;
/*  16:    */ import javax.swing.JLabel;
/*  17:    */ import javax.swing.JPanel;
/*  18:    */ import javax.swing.JScrollPane;
/*  19:    */ import javax.swing.JTextArea;
/*  20:    */ import javax.swing.SwingUtilities;
/*  21:    */ import m2.datatypes.Chain;
/*  22:    */ import m2.datatypes.DoubleBundle;
/*  23:    */ 
/*  24:    */ public class ChainViewer
/*  25:    */   extends JPanel
/*  26:    */   implements WiredBox
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 6564876398596734669L;
/*  29:    */   private Chain chain;
/*  30: 29 */   JPanel chainpanel = new JPanel(new FlowLayout(1, 5, 10));
/*  31: 30 */   JTextArea bundlebox = new JTextArea(5, 50);
/*  32:    */   
/*  33:    */   public ChainViewer()
/*  34:    */   {
/*  35: 34 */     setLayout(new BorderLayout());
/*  36: 35 */     this.chainpanel.setPreferredSize(new Dimension(40, 40));
/*  37: 36 */     add(this.chainpanel, "North");
/*  38: 37 */     this.bundlebox.setEditable(false);
/*  39: 38 */     JScrollPane areaScrollPane = new JScrollPane(this.bundlebox);
/*  40: 39 */     areaScrollPane.setVerticalScrollBarPolicy(22);
/*  41: 40 */     areaScrollPane.setHorizontalScrollBarPolicy(32);
/*  42: 41 */     add(areaScrollPane, "Center");
/*  43: 42 */     setBackground(Color.WHITE);
/*  44:    */     
/*  45: 44 */     Connections.getPorts(this).addSignalProcessor("input");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void input(Object input)
/*  49:    */   {
/*  50: 50 */     if ((input instanceof Chain))
/*  51:    */     {
/*  52: 51 */       Chain clean = (Chain)input;
/*  53: 52 */       viewChain(clean);
/*  54: 53 */       return;
/*  55:    */     }
/*  56: 55 */     System.err.println("Bad input to ChainViewer: " + input);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void viewChain(Chain c)
/*  60:    */   {
/*  61: 61 */     this.chain = c;
/*  62: 62 */     this.chainpanel.removeAll();
/*  63: 63 */     this.bundlebox.setText("mouse over above items");
/*  64: 64 */     this.bundlebox.setFont(new Font("Monospaced", 2, 12));
/*  65: 65 */     for (DoubleBundle db : this.chain)
/*  66:    */     {
/*  67: 66 */       DBLabel label = new DBLabel(db);
/*  68: 67 */       label.setFont(new Font("Sans-serif", 0, 12));
/*  69: 68 */       label.setBorder(BorderFactory.createLineBorder(Color.blue));
/*  70: 69 */       this.chainpanel.add(label);
/*  71: 70 */       label.addMouseListener(label);
/*  72:    */     }
/*  73: 72 */     updateUI();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void clear()
/*  77:    */   {
/*  78: 78 */     SwingUtilities.invokeLater(new Runnable()
/*  79:    */     {
/*  80:    */       public void run()
/*  81:    */       {
/*  82: 80 */         ChainViewer.this.chain = null;
/*  83: 81 */         ChainViewer.this.chainpanel.removeAll();
/*  84: 82 */         ChainViewer.this.bundlebox.setText("");
/*  85:    */       }
/*  86:    */     });
/*  87:    */   }
/*  88:    */   
/*  89:    */   private String shortThread(Thread t)
/*  90:    */   {
/*  91: 88 */     if (t.size() > 5) {
/*  92: 89 */       return ((String)t.get(0)).toString() + " " + ((String)t.get(1)).toString() + " ... " + ((String)t.get(t.size() - 3)).toString() + " " + ((String)t.get(t.size() - 2)).toString() + " " + ((String)t.get(t.size() - 1)).toString();
/*  93:    */     }
/*  94: 91 */     return t.toString(true);
/*  95:    */   }
/*  96:    */   
/*  97:    */   private class DBLabel
/*  98:    */     extends JLabel
/*  99:    */     implements MouseListener
/* 100:    */   {
/* 101:    */     private static final long serialVersionUID = 4272170115844899274L;
/* 102:    */     private DoubleBundle db;
/* 103:    */     
/* 104:    */     public DBLabel(DoubleBundle d)
/* 105:    */     {
/* 106: 99 */       super();
/* 107:100 */       this.db = d;
/* 108:    */     }
/* 109:    */     
/* 110:    */     public void mouseClicked(MouseEvent e) {}
/* 111:    */     
/* 112:    */     public void mouseEntered(MouseEvent e)
/* 113:    */     {
/* 114:106 */       ChainViewer.this.bundlebox.setText("positive examples:\n");
/* 115:107 */       for (Thread t : this.db.getPosSet()) {
/* 116:108 */         ChainViewer.this.bundlebox.append("   " + ChainViewer.this.shortThread(t) + "\n");
/* 117:    */       }
/* 118:110 */       ChainViewer.this.bundlebox.append("negative examples:\n");
/* 119:111 */       for (Thread t : this.db.getNegSet()) {
/* 120:112 */         ChainViewer.this.bundlebox.append("   " + ChainViewer.this.shortThread(t) + "\n");
/* 121:    */       }
/* 122:114 */       ChainViewer.this.bundlebox.setFont(new Font("Monospaced", 0, 12));
/* 123:    */     }
/* 124:    */     
/* 125:    */     public void mouseExited(MouseEvent e) {}
/* 126:    */     
/* 127:    */     public void mousePressed(MouseEvent e) {}
/* 128:    */     
/* 129:    */     public void mouseReleased(MouseEvent e) {}
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.gui.ChainViewer
 * JD-Core Version:    0.7.0.1
 */