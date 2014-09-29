/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import bridge.reps.entities.Relation;
/*  6:   */ import bridge.reps.entities.Sequence;
/*  7:   */ import bridge.views.frameviews.classic.FrameViewer;
/*  8:   */ import connections.Connections;
/*  9:   */ import connections.Ports;
/* 10:   */ import genesis.Quantum;
/* 11:   */ import java.awt.BorderLayout;
/* 12:   */ import java.awt.Color;
/* 13:   */ import java.awt.Container;
/* 14:   */ import java.io.File;
/* 15:   */ import java.io.PrintStream;
/* 16:   */ import javax.swing.JFrame;
/* 17:   */ import javax.swing.JLabel;
/* 18:   */ 
/* 19:   */ public class EventKnowledgeViewer
/* 20:   */   extends NewFrameViewer
/* 21:   */ {
/* 22:   */   File file;
/* 23:21 */   FrameViewer fv = new FrameViewer();
/* 24:23 */   JLabel fileLabel = new JLabel();
/* 25:25 */   Entity input = null;
/* 26:   */   
/* 27:   */   public EventKnowledgeViewer()
/* 28:   */   {
/* 29:28 */     Connections.getPorts(this).addSignalProcessor("process");
/* 30:29 */     setLayout(new BorderLayout());
/* 31:30 */     add(this.fv, "Center");
/* 32:31 */     add(this.fileLabel, "South");
/* 33:32 */     setBackground(Color.WHITE);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void process(Object signal)
/* 37:   */   {
/* 38:36 */     if ((signal instanceof Quantum))
/* 39:   */     {
/* 40:37 */       Quantum q = (Quantum)signal;
/* 41:38 */       this.fv.setInput(q.getThing());
/* 42:   */     }
/* 43:   */     else
/* 44:   */     {
/* 45:43 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/* 46:44 */         signal + " in NewFrameViewer");
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void clear()
/* 51:   */   {
/* 52:49 */     this.fv.clearData();
/* 53:   */   }
/* 54:   */   
/* 55:   */   public Entity getInput()
/* 56:   */   {
/* 57:53 */     return this.input;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void setLabel(String text)
/* 61:   */   {
/* 62:57 */     this.fileLabel.setText(text);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static void main(String[] ignore)
/* 66:   */   {
/* 67:61 */     JFrame frame = new JFrame();
/* 68:62 */     EventKnowledgeViewer view = new EventKnowledgeViewer();
/* 69:63 */     frame.getContentPane().add(view);
/* 70:64 */     Entity t = new Entity("Patrick");
/* 71:65 */     Function d = new Function(t);
/* 72:66 */     d.addType("Foo");
/* 73:67 */     d.addType("Bar");
/* 74:68 */     Relation r = new Relation(d, d);
/* 75:69 */     r.removeType("thing");
/* 76:70 */     r.addType("action");
/* 77:71 */     r.addType("X");
/* 78:72 */     r.addType("Y");
/* 79:73 */     r.addType("Z");
/* 80:74 */     Sequence s = new Sequence("hello");
/* 81:75 */     s.addType("big");
/* 82:76 */     s.addType("little");
/* 83:77 */     s.addType("red", "feature");
/* 84:78 */     s.addElement(r);
/* 85:79 */     view.process(new Quantum(r, s, true));
/* 86:80 */     frame.setBounds(0, 0, 200, 200);
/* 87:81 */     frame.setVisible(true);
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.EventKnowledgeViewer
 * JD-Core Version:    0.7.0.1
 */