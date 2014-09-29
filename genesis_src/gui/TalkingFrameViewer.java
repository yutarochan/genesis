/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.views.frameviews.classic.FrameViewer;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import connections.WiredBox;
/*  8:   */ import expert.SimpleGenerator;
/*  9:   */ import genesis.GenesisGetters;
/* 10:   */ import java.awt.BorderLayout;
/* 11:   */ import java.awt.Color;
/* 12:   */ import java.io.PrintStream;
/* 13:   */ import javax.swing.JPanel;
/* 14:   */ import windowGroup.WindowGroupManager;
/* 15:   */ 
/* 16:   */ public class TalkingFrameViewer
/* 17:   */   extends JPanel
/* 18:   */   implements WiredBox
/* 19:   */ {
/* 20:   */   Ports ports;
/* 21:22 */   FrameViewer fv = new FrameViewer();
/* 22:24 */   Entity input = null;
/* 23:   */   GenesisGetters genesisGetters;
/* 24:   */   
/* 25:   */   public TalkingFrameViewer(GenesisGetters genesisGetters)
/* 26:   */   {
/* 27:29 */     this.genesisGetters = genesisGetters;
/* 28:30 */     Connections.getPorts(this).addSignalProcessor("process");
/* 29:31 */     setLayout(new BorderLayout());
/* 30:32 */     WiredLabelPanel textBox = new WiredLabelPanel();
/* 31:33 */     textBox.setName("Text box");
/* 32:34 */     add(this.fv, "Center");
/* 33:35 */     add(textBox, "South");
/* 34:36 */     setBackground(Color.WHITE);
/* 35:37 */     SimpleGenerator translator = new SimpleGenerator();
/* 36:38 */     translator.setName("Talker translator");
/* 37:39 */     Connections.wire(this, translator);
/* 38:40 */     Connections.wire(translator, textBox);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void process(Object signal)
/* 42:   */   {
/* 43:44 */     if (((signal instanceof Entity)) || (signal == null))
/* 44:   */     {
/* 45:45 */       this.input = ((Entity)signal);
/* 46:   */       
/* 47:47 */       this.genesisGetters.getWindowGroupManager().setGuts(this.genesisGetters.getRightPanel(), this);
/* 48:   */       
/* 49:49 */       this.fv.setInput(this.input);
/* 50:50 */       Connections.getPorts(this).transmit(this.input);
/* 51:   */     }
/* 52:   */     else
/* 53:   */     {
/* 54:53 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/* 55:54 */         signal + " in NewFrameViewer");
/* 56:   */     }
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void clear()
/* 60:   */   {
/* 61:59 */     this.fv.clearData();
/* 62:   */   }
/* 63:   */   
/* 64:   */   public Ports getPorts()
/* 65:   */   {
/* 66:63 */     if (this.ports == null) {
/* 67:64 */       this.ports = new Ports();
/* 68:   */     }
/* 69:66 */     return this.ports;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public Entity getInput()
/* 73:   */   {
/* 74:70 */     return this.input;
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TalkingFrameViewer
 * JD-Core Version:    0.7.0.1
 */