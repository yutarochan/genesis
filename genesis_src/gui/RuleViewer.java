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
/*  12:    */ import java.awt.Color;
/*  13:    */ import java.awt.Container;
/*  14:    */ import java.awt.Rectangle;
/*  15:    */ import java.io.PrintStream;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import javax.swing.JFrame;
/*  18:    */ import matchers.Substitutor;
/*  19:    */ 
/*  20:    */ public class RuleViewer
/*  21:    */   extends FrameViewer
/*  22:    */   implements WiredBox
/*  23:    */ {
/*  24:    */   GenesisGetters genesisGetters;
/*  25:    */   public static final String FINAL_INFERENCE = "final-inference";
/*  26:    */   public static final String FINAL_STORY = "final-story";
/*  27:    */   
/*  28:    */   public RuleViewer(GenesisGetters genesisGetters)
/*  29:    */   {
/*  30: 33 */     super(null, 1);
/*  31: 34 */     setBackground(Color.WHITE);
/*  32: 35 */     Connections.getPorts(this).addSignalProcessor("process");
/*  33: 36 */     Connections.getPorts(this).addSignalProcessor("final-inference", "processFinalInference");
/*  34: 37 */     this.genesisGetters = genesisGetters;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void process(Object signal)
/*  38:    */   {
/*  39: 41 */     Sequence sequence = new Sequence();
/*  40: 43 */     if ((signal != null) && ((signal instanceof Sequence)))
/*  41:    */     {
/*  42: 44 */       sequence = (Sequence)signal;
/*  43:    */     }
/*  44: 46 */     else if ((signal != null) && ((signal instanceof Entity)))
/*  45:    */     {
/*  46: 47 */       ArrayList<Entity> rules = new ArrayList();
/*  47: 48 */       rules.add((Entity)signal);
/*  48:    */     }
/*  49: 50 */     else if ((signal != null) && ((signal instanceof ArrayList)))
/*  50:    */     {
/*  51: 51 */       ArrayList<Entity> rules = (ArrayList)signal;
/*  52: 52 */       for (Entity t : rules) {
/*  53: 53 */         sequence.addElement(t);
/*  54:    */       }
/*  55:    */     }
/*  56:    */     else
/*  57:    */     {
/*  58: 57 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/*  59: 58 */         signal + " in RuleViewer");
/*  60:    */     }
/*  61: 61 */     setStory(sequence);
/*  62: 62 */     int width = this.bodyPanel.getWidth();
/*  63: 63 */     width = 5000;
/*  64:    */     
/*  65:    */ 
/*  66: 66 */     this.bodyPanel.scrollRectToVisible(new Rectangle(width - 2, 0, 1, 1));
/*  67: 67 */     Connections.getPorts(this).transmit(sequence);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void processFinalInference(Object signal)
/*  71:    */   {
/*  72: 71 */     Sequence sequence = new Sequence();
/*  73: 73 */     if ((signal != null) && ((signal instanceof Sequence)))
/*  74:    */     {
/*  75: 74 */       sequence = (Sequence)signal;
/*  76:    */     }
/*  77: 76 */     else if ((signal != null) && ((signal instanceof Entity)))
/*  78:    */     {
/*  79: 77 */       ArrayList<Entity> rules = new ArrayList();
/*  80: 78 */       rules.add((Entity)signal);
/*  81:    */     }
/*  82: 80 */     else if ((signal != null) && ((signal instanceof ArrayList)))
/*  83:    */     {
/*  84: 81 */       ArrayList<Entity> rules = (ArrayList)signal;
/*  85: 82 */       for (Entity t : rules) {
/*  86: 83 */         sequence.addElement(t);
/*  87:    */       }
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91: 87 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/*  92: 88 */         signal + " in RuleViewer");
/*  93:    */     }
/*  94: 91 */     setStory(sequence);
/*  95: 92 */     int width = this.bodyPanel.getWidth();
/*  96: 93 */     width = 5000;
/*  97:    */     
/*  98:    */ 
/*  99: 96 */     this.bodyPanel.scrollRectToVisible(new Rectangle(width - 2, 0, 1, 1));
/* 100: 97 */     Connections.getPorts(this).transmit("final-story", sequence);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void clear()
/* 104:    */   {
/* 105:101 */     clearData();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static void main(String[] ignore)
/* 109:    */   {
/* 110:105 */     JFrame frame = new JFrame();
/* 111:106 */     StoryViewer view = new StoryViewer(null);
/* 112:107 */     frame.getContentPane().add(view);
/* 113:108 */     Entity bird1 = new Entity("bird");
/* 114:109 */     Entity bird2 = new Entity("bird");
/* 115:110 */     Entity tree1 = new Entity("tree");
/* 116:111 */     Entity tree2 = new Entity("tree");
/* 117:112 */     Entity elephant = new Entity("elephant");
/* 118:    */     
/* 119:114 */     Function at1 = new Function("at", tree1);
/* 120:115 */     Function at2 = new Function("at", bird2);
/* 121:116 */     at2 = new Function("at", tree2);
/* 122:    */     
/* 123:118 */     Function to1 = new Function("to", at1);
/* 124:119 */     Function to2 = new Function("to", at2);
/* 125:    */     
/* 126:121 */     Sequence path1 = new Sequence("path");
/* 127:122 */     Sequence path2 = new Sequence("path");
/* 128:    */     
/* 129:124 */     path1.addElement(to1);
/* 130:125 */     path2.addElement(to2);
/* 131:    */     
/* 132:127 */     Relation fly = new Relation("flew", bird1, path1);
/* 133:128 */     Relation walk = new Relation("walked", elephant, path2);
/* 134:    */     
/* 135:130 */     Sequence v = new Sequence("story");
/* 136:131 */     v.addElement(fly);
/* 137:132 */     v.addElement(walk);
/* 138:    */     
/* 139:134 */     Entity substitution = Substitutor.dereference(v);
/* 140:    */     
/* 141:    */ 
/* 142:137 */     view.process(substitution);
/* 143:138 */     frame.setBounds(0, 0, 600, 600);
/* 144:139 */     frame.setVisible(true);
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.RuleViewer
 * JD-Core Version:    0.7.0.1
 */