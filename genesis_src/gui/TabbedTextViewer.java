/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.awt.Color;
/*   8:    */ import javax.swing.JTabbedPane;
/*   9:    */ import javax.swing.event.ChangeEvent;
/*  10:    */ import javax.swing.event.ChangeListener;
/*  11:    */ 
/*  12:    */ public class TabbedTextViewer
/*  13:    */   extends JTabbedPane
/*  14:    */   implements WiredBox
/*  15:    */ {
/*  16:    */   public static final String TAB = "switch tab";
/*  17:    */   public static final String SILENCE = "silence";
/*  18:    */   public static final String CLEAR = "clear";
/*  19:    */   public static final String ALL = "all";
/*  20:    */   public static final String SELECTED_TAB = "selected tab";
/*  21:    */   private TextViewer currentViewer;
/*  22:    */   
/*  23:    */   public TabbedTextViewer()
/*  24:    */   {
/*  25: 31 */     setOpaque(true);
/*  26: 32 */     setBackground(Color.WHITE);
/*  27: 33 */     addChangeListener(new ChangeListener()
/*  28:    */     {
/*  29:    */       public void stateChanged(ChangeEvent e)
/*  30:    */       {
/*  31: 35 */         int index = TabbedTextViewer.this.getSelectedIndex();
/*  32: 36 */         if (index >= 0)
/*  33:    */         {
/*  34: 37 */           String tabName = ((JTabbedPane)e.getSource()).getTitleAt(index);
/*  35: 38 */           Connections.getPorts(TabbedTextViewer.this).transmit("selected tab", new BetterSignal(new Object[] { "selected tab", tabName }));
/*  36:    */         }
/*  37:    */       }
/*  38: 41 */     });
/*  39: 42 */     Connections.getPorts(this).addSignalProcessor("switch tab", "blowout");
/*  40: 43 */     Connections.getPorts(this).addSignalProcessor("process");
/*  41: 44 */     Connections.getPorts(this).addSignalProcessor("clear", "clear");
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void blowout(Object o)
/*  45:    */   {
/*  46: 50 */     switchTab(o);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void process(Object o)
/*  50:    */   {
/*  51: 55 */     if ((o instanceof BetterSignal))
/*  52:    */     {
/*  53: 56 */       BetterSignal input = (BetterSignal)o;
/*  54: 57 */       switchTab(input.get(0, Object.class));
/*  55: 58 */       if (input.get(1, String.class) == "clear") {
/*  56: 60 */         clear(o);
/*  57:    */       } else {
/*  58: 63 */         this.currentViewer.processViaDirectCall(input.get(1, Object.class));
/*  59:    */       }
/*  60: 65 */       return;
/*  61:    */     }
/*  62: 67 */     if (o == "silence")
/*  63:    */     {
/*  64: 68 */       this.currentViewer = null;
/*  65: 69 */       return;
/*  66:    */     }
/*  67: 72 */     if (this.currentViewer == null) {
/*  68: 74 */       return;
/*  69:    */     }
/*  70: 78 */     this.currentViewer.processViaDirectCall(o);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void switchTab(Object o)
/*  74:    */   {
/*  75: 84 */     if (o == "silence")
/*  76:    */     {
/*  77: 85 */       this.currentViewer = null;
/*  78: 86 */       return;
/*  79:    */     }
/*  80: 89 */     String title = o.toString();
/*  81: 90 */     for (int i = 0; i < getTabCount(); i++) {
/*  82: 91 */       if (title.equals(getTitleAt(i)))
/*  83:    */       {
/*  84: 92 */         setSelectedIndex(i);
/*  85: 93 */         this.currentViewer = ((TextViewer)getSelectedComponent());
/*  86: 94 */         return;
/*  87:    */       }
/*  88:    */     }
/*  89: 98 */     TextViewer viewer = new TextViewer();
/*  90: 99 */     this.currentViewer = viewer;
/*  91:100 */     this.currentViewer.setOpaque(true);
/*  92:101 */     this.currentViewer.setBackground(Color.WHITE);
/*  93:102 */     viewer.setName(title);
/*  94:103 */     addTab(title, viewer);
/*  95:104 */     setSelectedIndex(getTabCount() - 1);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void clear(Object o)
/*  99:    */   {
/* 100:108 */     if ("all" == o) {
/* 101:110 */       removeAll();
/* 102:112 */     } else if (this.currentViewer != null) {
/* 103:114 */       this.currentViewer.clear();
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void clear()
/* 108:    */   {
/* 109:119 */     removeAll();
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TabbedTextViewer
 * JD-Core Version:    0.7.0.1
 */