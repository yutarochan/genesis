/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import bridge.views.frameviews.classic.FrameViewer;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import connections.WiredBox;
/*  11:    */ import genesis.GenesisGetters;
/*  12:    */ import java.awt.Container;
/*  13:    */ import java.awt.Rectangle;
/*  14:    */ import java.util.Vector;
/*  15:    */ import javax.swing.JFrame;
/*  16:    */ import matchers.Substitutor;
/*  17:    */ import utils.Mark;
/*  18:    */ 
/*  19:    */ public class StoryViewer
/*  20:    */   extends FrameViewer
/*  21:    */   implements WiredBox
/*  22:    */ {
/*  23:    */   public static final String DISPLAY = "Stop";
/*  24: 27 */   Sequence buffer = new Sequence();
/*  25:    */   GenesisGetters genesisGetters;
/*  26:    */   
/*  27:    */   public StoryViewer(GenesisGetters genesisGetters)
/*  28:    */   {
/*  29: 32 */     super(null, 1);
/*  30: 33 */     setName("Story viewer");
/*  31: 34 */     Connections.getPorts(this).addSignalProcessor("process");
/*  32: 35 */     this.genesisGetters = genesisGetters;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void process(Object x)
/*  36:    */   {
/*  37: 39 */     if ((x instanceof String))
/*  38:    */     {
/*  39: 40 */       if (x == "reset") {
/*  40: 41 */         clearData();
/*  41:    */       }
/*  42:    */     }
/*  43: 45 */     else if ((x instanceof Entity))
/*  44:    */     {
/*  45: 46 */       Entity signal = (Entity)x;
/*  46: 47 */       if (signal.sequenceP())
/*  47:    */       {
/*  48: 48 */         Sequence sequence = (Sequence)signal;
/*  49: 50 */         if ((sequence.isAPrimed("story")) || (sequence.isAPrimed("reflection")) || (sequence.isAPrimed("onset")) || ((!sequence.getElements().isEmpty()) && (((Entity)sequence.getElements().get(0)).isA("reflection"))))
/*  50:    */         {
/*  51:    */           try
/*  52:    */           {
/*  53: 55 */             setStory(sequence);
/*  54:    */           }
/*  55:    */           catch (Exception e)
/*  56:    */           {
/*  57: 58 */             Mark.err(new Object[] {"StoryViewer.process blew out" });
/*  58:    */           }
/*  59: 60 */           int width = this.bodyPanel.getWidth();
/*  60: 61 */           width = 5000;
/*  61:    */           
/*  62:    */ 
/*  63: 64 */           this.bodyPanel.scrollRectToVisible(new Rectangle(width - 2, 0, 1, 1));
/*  64: 65 */           return;
/*  65:    */         }
/*  66:    */       }
/*  67: 68 */       this.buffer.addElement(signal);
/*  68: 69 */       if (this.buffer.getElements().size() > 5) {
/*  69: 70 */         this.buffer.getElements().remove(0);
/*  70:    */       }
/*  71: 72 */       setStory(this.buffer);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void clear()
/*  76:    */   {
/*  77: 77 */     clearData();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void main(String[] ignore)
/*  81:    */   {
/*  82: 81 */     JFrame frame = new JFrame();
/*  83: 82 */     StoryViewer view = new StoryViewer(null);
/*  84: 83 */     frame.getContentPane().add(view);
/*  85: 84 */     Entity bird1 = new Entity("bird");
/*  86: 85 */     Entity bird2 = new Entity("bird");
/*  87: 86 */     Entity tree1 = new Entity("tree");
/*  88: 87 */     Entity tree2 = new Entity("tree");
/*  89: 88 */     Entity elephant = new Entity("elephant");
/*  90:    */     
/*  91: 90 */     Function at1 = new Function("at", tree1);
/*  92: 91 */     Function at2 = new Function("at", bird2);
/*  93: 92 */     at2 = new Function("at", tree2);
/*  94:    */     
/*  95: 94 */     Function to1 = new Function("to", at1);
/*  96: 95 */     Function to2 = new Function("to", at2);
/*  97:    */     
/*  98: 97 */     Sequence path1 = new Sequence("path");
/*  99: 98 */     Sequence path2 = new Sequence("path");
/* 100:    */     
/* 101:100 */     path1.addElement(to1);
/* 102:101 */     path2.addElement(to2);
/* 103:    */     
/* 104:103 */     Relation fly = new Relation("flew", bird1, path1);
/* 105:104 */     Relation walk = new Relation("walked", elephant, path2);
/* 106:    */     
/* 107:106 */     Sequence v = new Sequence("story");
/* 108:107 */     v.addElement(fly);
/* 109:108 */     v.addElement(walk);
/* 110:    */     
/* 111:110 */     Entity substitution = Substitutor.dereference(v);
/* 112:    */     
/* 113:    */ 
/* 114:113 */     view.process(substitution);
/* 115:114 */     frame.setBounds(0, 0, 600, 600);
/* 116:115 */     frame.setVisible(true);
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.StoryViewer
 * JD-Core Version:    0.7.0.1
 */