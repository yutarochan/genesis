/*   1:    */ package m2.gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.awt.Dimension;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Vector;
/*  12:    */ import javax.swing.BoxLayout;
/*  13:    */ import javax.swing.JLabel;
/*  14:    */ import javax.swing.JList;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.JScrollPane;
/*  17:    */ import javax.swing.event.ListSelectionEvent;
/*  18:    */ import javax.swing.event.ListSelectionListener;
/*  19:    */ 
/*  20:    */ public class PredictionsViewer
/*  21:    */   extends JPanel
/*  22:    */   implements WiredBox
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 7584315067624862864L;
/*  25: 26 */   private ThingList l1list = new ThingList(null);
/*  26: 27 */   private ThingList l2list = new ThingList(null);
/*  27: 28 */   private ThingList l3list = new ThingList(null);
/*  28: 29 */   private ThingList l4list = new ThingList(null);
/*  29:    */   
/*  30:    */   public PredictionsViewer()
/*  31:    */   {
/*  32: 34 */     setLayout(new BoxLayout(this, 3));
/*  33:    */     
/*  34: 36 */     JLabel l1label = new JLabel("exact causal precedence");
/*  35: 37 */     this.l1list.setSelectionMode(0);
/*  36: 38 */     this.l1list.setVisibleRowCount(-1);
/*  37: 39 */     this.l1list.addListSelectionListener(this.l1list);
/*  38: 40 */     JScrollPane l1Scroller = new JScrollPane(this.l1list);
/*  39: 41 */     l1Scroller.setPreferredSize(new Dimension(250, 80));
/*  40: 42 */     add(l1label);
/*  41: 43 */     add(l1Scroller);
/*  42:    */     
/*  43: 45 */     JLabel l2label = new JLabel("subject precedence");
/*  44: 46 */     this.l2list.setSelectionMode(0);
/*  45: 47 */     this.l2list.setVisibleRowCount(-1);
/*  46: 48 */     this.l2list.addListSelectionListener(this.l2list);
/*  47: 49 */     JScrollPane l2Scroller = new JScrollPane(this.l2list);
/*  48: 50 */     l2Scroller.setPreferredSize(new Dimension(250, 80));
/*  49: 51 */     add(l2label);
/*  50: 52 */     add(l2Scroller);
/*  51:    */     
/*  52: 54 */     JLabel l3label = new JLabel("historical event precedence");
/*  53: 55 */     this.l3list.setSelectionMode(0);
/*  54: 56 */     this.l3list.setVisibleRowCount(-1);
/*  55: 57 */     this.l3list.addListSelectionListener(this.l3list);
/*  56: 58 */     JScrollPane l3Scroller = new JScrollPane(this.l3list);
/*  57: 59 */     l3Scroller.setPreferredSize(new Dimension(250, 80));
/*  58: 60 */     add(l3label);
/*  59: 61 */     add(l3Scroller);
/*  60:    */     
/*  61: 63 */     JLabel l4label = new JLabel("analogy from known causal relation");
/*  62: 64 */     this.l4list.setSelectionMode(0);
/*  63: 65 */     this.l4list.setVisibleRowCount(-1);
/*  64: 66 */     this.l4list.addListSelectionListener(this.l4list);
/*  65: 67 */     JScrollPane l4Scroller = new JScrollPane(this.l4list);
/*  66: 68 */     l4Scroller.setPreferredSize(new Dimension(250, 80));
/*  67: 69 */     add(l4label);
/*  68: 70 */     add(l4Scroller);
/*  69:    */     
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79: 81 */     Connections.getPorts(this).addSignalProcessor("input");
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void display(Map<String, List<Entity>> preds)
/*  83:    */   {
/*  84: 87 */     clearAllLists();
/*  85: 88 */     if (preds != null)
/*  86:    */     {
/*  87: 89 */       if (preds.containsKey("exact causal precedence")) {
/*  88: 89 */         this.l1list.setListData(((List)preds.get("exact causal precedence")).toArray());
/*  89:    */       }
/*  90: 90 */       if (preds.containsKey("subject precedence")) {
/*  91: 90 */         this.l2list.setListData(((List)preds.get("subject precedence")).toArray());
/*  92:    */       }
/*  93: 91 */       if (preds.containsKey("historical event precedence")) {
/*  94: 91 */         this.l3list.setListData(((List)preds.get("historical event precedence")).toArray());
/*  95:    */       }
/*  96: 92 */       if (preds.containsKey("analogy from known causal relation")) {
/*  97: 92 */         this.l4list.setListData(((List)preds.get("analogy from known causal relation")).toArray());
/*  98:    */       }
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void input(Object input)
/* 103:    */   {
/* 104:102 */     if ((input instanceof Map))
/* 105:    */     {
/* 106:103 */       display((Map)input);
/* 107:104 */       return;
/* 108:    */     }
/* 109:106 */     System.err.println("Bad input to PredictionsViewer");
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void showSelectedThing(Entity t)
/* 113:    */   {
/* 114:110 */     Connections.getPorts(this).transmit(t);
/* 115:    */   }
/* 116:    */   
/* 117:    */   private void clearOtherLists(ThingList lst)
/* 118:    */   {
/* 119:114 */     if (this.l1list != lst) {
/* 120:114 */       this.l1list.clearSelection();
/* 121:    */     }
/* 122:115 */     if (this.l2list != lst) {
/* 123:115 */       this.l2list.clearSelection();
/* 124:    */     }
/* 125:116 */     if (this.l3list != lst) {
/* 126:116 */       this.l3list.clearSelection();
/* 127:    */     }
/* 128:117 */     if (this.l4list != lst) {
/* 129:117 */       this.l4list.clearSelection();
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   private void clearAllLists()
/* 134:    */   {
/* 135:122 */     this.l1list.setListData(new Vector());
/* 136:123 */     this.l2list.setListData(new Vector());
/* 137:124 */     this.l3list.setListData(new Vector());
/* 138:125 */     this.l4list.setListData(new Vector());
/* 139:    */   }
/* 140:    */   
/* 141:    */   private class ThingList
/* 142:    */     extends JList
/* 143:    */     implements ListSelectionListener
/* 144:    */   {
/* 145:    */     private static final long serialVersionUID = -903641950356317204L;
/* 146:    */     
/* 147:    */     private ThingList() {}
/* 148:    */     
/* 149:    */     public void valueChanged(ListSelectionEvent e)
/* 150:    */     {
/* 151:132 */       if ((!e.getValueIsAdjusting()) && 
/* 152:133 */         (getSelectedIndex() != -1))
/* 153:    */       {
/* 154:137 */         Entity selected = (Entity)getSelectedValue();
/* 155:138 */         PredictionsViewer.this.clearOtherLists(this);
/* 156:139 */         PredictionsViewer.this.showSelectedThing(selected);
/* 157:    */       }
/* 158:    */     }
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.gui.PredictionsViewer
 * JD-Core Version:    0.7.0.1
 */