/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.AFactory;
/*   4:    */ import bridge.reps.entities.EFactory;
/*   5:    */ import bridge.reps.entities.Entity;
/*   6:    */ import bridge.reps.entities.Function;
/*   7:    */ import bridge.reps.entities.Relation;
/*   8:    */ import bridge.reps.entities.Sequence;
/*   9:    */ import bridge.views.frameviews.classic.FrameViewer;
/*  10:    */ import connections.Connections;
/*  11:    */ import connections.Ports;
/*  12:    */ import java.awt.Container;
/*  13:    */ import java.awt.GridLayout;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import javax.swing.JFrame;
/*  16:    */ import tools.BFactory;
/*  17:    */ import tools.JFactory;
/*  18:    */ 
/*  19:    */ public class NewFrameViewer
/*  20:    */   extends NegatableJPanel
/*  21:    */ {
/*  22:    */   Ports ports;
/*  23: 20 */   FrameViewer fv = new FrameViewer();
/*  24: 22 */   Entity input = null;
/*  25:    */   
/*  26:    */   public NewFrameViewer()
/*  27:    */   {
/*  28: 25 */     setLayout(new GridLayout(1, 1));
/*  29: 26 */     add(this.fv);
/*  30: 27 */     setOpaque(false);
/*  31: 28 */     Connections.getPorts(this).addSignalProcessor("view");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void view(Object signal)
/*  35:    */   {
/*  36: 33 */     if ((signal != null) && ((signal instanceof Entity)))
/*  37:    */     {
/*  38: 34 */       this.input = ((Entity)signal);
/*  39:    */       
/*  40: 36 */       this.fv.setInput(this.input);
/*  41:    */     }
/*  42:    */     else
/*  43:    */     {
/*  44: 39 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/*  45: 40 */         signal + " in NewFrameViewer");
/*  46:    */     }
/*  47: 43 */     this.fv.getBodyPanel().setTruthValue(signal);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void clear()
/*  51:    */   {
/*  52: 47 */     this.fv.clearData();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Ports getPorts()
/*  56:    */   {
/*  57: 51 */     if (this.ports == null) {
/*  58: 52 */       this.ports = new Ports();
/*  59:    */     }
/*  60: 54 */     return this.ports;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Entity getInput()
/*  64:    */   {
/*  65: 58 */     return this.input;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void main(String[] ignore)
/*  69:    */   {
/*  70: 62 */     JFrame frame = new JFrame();
/*  71: 63 */     NewFrameViewer view = new NewFrameViewer();
/*  72: 64 */     frame.getContentPane().add(view);
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91: 83 */     Entity rock = new Entity("Rock");
/*  92: 84 */     Function place2 = JFactory.createPlace("at", rock);
/*  93:    */     
/*  94:    */ 
/*  95: 87 */     Function pathElement2 = JFactory.createPathElement("to", place2);
/*  96:    */     
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104: 96 */     Entity ball = new Entity("ball");
/* 105: 97 */     ball.addFeature("red");
/* 106: 98 */     ball.addProperty("owner", "Patrick");
/* 107: 99 */     Entity table = new Entity("table");
/* 108:100 */     Function place = JFactory.createPlace("below", table);
/* 109:101 */     Function destination = JFactory.createPathElement("to", place);
/* 110:102 */     Sequence path = JFactory.createPath();
/* 111:103 */     path.addElement(destination);
/* 112:104 */     Relation roll = new Relation(ball, path);
/* 113:105 */     roll.addType("roll");
/* 114:    */     
/* 115:107 */     Entity cat = new Entity("cat");
/* 116:108 */     Entity bird = new Entity("bird");
/* 117:109 */     Function appear = BFactory.createTransitionElement("appear", cat);
/* 118:110 */     Function disappear = BFactory.createTransitionElement("disappear", bird);
/* 119:111 */     Relation r12 = AFactory.createTimeRelation("before", appear, disappear);
/* 120:    */     
/* 121:113 */     Sequence ladder1 = EFactory.createEventLadder();
/* 122:114 */     ladder1.addElement(appear);
/* 123:115 */     ladder1.addElement(roll);
/* 124:    */     
/* 125:117 */     Sequence ladder2 = EFactory.createEventLadder();
/* 126:118 */     ladder2.addElement(disappear);
/* 127:    */     
/* 128:120 */     Sequence space = EFactory.createEventSpace();
/* 129:121 */     space.addElement(ladder1);
/* 130:122 */     space.addElement(ladder2);
/* 131:123 */     space.addFeature("not");
/* 132:    */     
/* 133:125 */     System.out.println(r12);
/* 134:126 */     view.view(space);
/* 135:127 */     frame.setBounds(0, 0, 300, 800);
/* 136:128 */     frame.setVisible(true);
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.NewFrameViewer
 * JD-Core Version:    0.7.0.1
 */