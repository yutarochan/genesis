/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Component;
/*   9:    */ import java.awt.Dimension;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import javax.swing.JButton;
/*  13:    */ import javax.swing.JComponent;
/*  14:    */ import javax.swing.JPanel;
/*  15:    */ import javax.swing.plaf.ColorUIResource;
/*  16:    */ import storyProcessor.ReflectionDescription;
/*  17:    */ import text.Punctuator;
/*  18:    */ import utils.Mark;
/*  19:    */ 
/*  20:    */ public class ReflectionBar
/*  21:    */   extends JPanel
/*  22:    */   implements WiredBox
/*  23:    */ {
/*  24: 22 */   private static boolean debug = true;
/*  25:    */   public static final String CLEAR_PLOT_UNIT_BUTTONS = "clearButtons";
/*  26:    */   public static final String REFLECTION_BUTTON = "addPlotUnitButton";
/*  27:    */   public static final String RESET = "reset";
/*  28:    */   public static final String TO_ELABORATION_VIEWER = "elaboration display port";
/*  29:    */   public static final String TO_STATISTICS_BAR = "statistics bar output port";
/*  30: 34 */   private static ColorUIResource normalColorResource = new ColorUIResource(new JButton().getBackground());
/*  31:    */   
/*  32:    */   public void addPlotUnitButton(Object input)
/*  33:    */   {
/*  34: 37 */     if (input == "reset")
/*  35:    */     {
/*  36: 38 */       clear();
/*  37:    */     }
/*  38: 40 */     else if ((input instanceof JComponent))
/*  39:    */     {
/*  40: 41 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Actuated PlotUnitBar.addPlotUnitButton" });
/*  41: 42 */       JComponent c = (JComponent)input;
/*  42: 43 */       add(c);
/*  43:    */       
/*  44:    */ 
/*  45: 46 */       validate();
/*  46: 47 */       repaint();
/*  47:    */       
/*  48: 49 */       Mark.say(new Object[] {"Data1", Integer.valueOf(getComponents().length) });
/*  49: 50 */       Connections.getPorts(this).transmit("statistics bar output port", new BetterSignal(new Object[] { StatisticsBar.CONCEPT_DISCOVERY_COUNT, 
/*  50: 51 */         Integer.valueOf(getComponents().length) }));
/*  51:    */     }
/*  52: 53 */     else if ((input instanceof ReflectionDescription))
/*  53:    */     {
/*  54: 54 */       add(makeButton((ReflectionDescription)input));
/*  55: 55 */       validate();
/*  56: 56 */       repaint();
/*  57:    */       
/*  58:    */ 
/*  59: 59 */       Connections.getPorts(this).transmit("statistics bar output port", new BetterSignal(new Object[] { StatisticsBar.CONCEPT_DISCOVERY_COUNT, 
/*  60: 60 */         Integer.valueOf(getComponents().length) }));
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   private JButton makeButton(ReflectionDescription completion)
/*  65:    */   {
/*  66: 67 */     JButton button = new JButton(Punctuator.conditionName(completion.getName()));
/*  67: 68 */     button.addActionListener(new MyButtonListener(button, completion));
/*  68: 69 */     return button;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private class MyButtonListener
/*  72:    */     implements ActionListener
/*  73:    */   {
/*  74:    */     private JButton button;
/*  75:    */     private ColorUIResource normalColorResource;
/*  76:    */     private ReflectionDescription completion;
/*  77: 79 */     private final ReflectionDescription emptyCompletion = new ReflectionDescription();
/*  78:    */     
/*  79:    */     public MyButtonListener(JButton button, ReflectionDescription completion)
/*  80:    */     {
/*  81: 82 */       this.button = button;
/*  82: 83 */       this.completion = completion;
/*  83: 84 */       this.normalColorResource = new ColorUIResource(button.getBackground());
/*  84:    */     }
/*  85:    */     
/*  86:    */     public void actionPerformed(ActionEvent e)
/*  87:    */     {
/*  88: 91 */       if (this.button.getBackground() != Color.GREEN)
/*  89:    */       {
/*  90: 92 */         ReflectionBar.this.resetButtons(new Object());
/*  91:    */         
/*  92: 94 */         Connections.getPorts(ReflectionBar.this).transmit("elaboration display port", this.completion);
/*  93: 95 */         Connections.getPorts(ReflectionBar.this).transmit("reset", "reset");
/*  94: 96 */         this.button.setBackground(Color.GREEN);
/*  95:    */       }
/*  96:    */       else
/*  97:    */       {
/*  98: 99 */         this.button.setBackground(this.normalColorResource);
/*  99:100 */         ReflectionBar.this.resetButtons();
/* 100:101 */         Connections.getPorts(ReflectionBar.this).transmit("elaboration display port", this.emptyCompletion);
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void clearButtons(Object input)
/* 106:    */   {
/* 107:109 */     if (input == "reset") {
/* 108:110 */       clear();
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void clear()
/* 113:    */   {
/* 114:115 */     removeAll();
/* 115:116 */     validate();
/* 116:117 */     repaint();
/* 117:118 */     Connections.getPorts(this).transmit("reflection count label", Integer.valueOf(getComponents().length));
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void resetButtons(Object input)
/* 121:    */   {
/* 122:122 */     for (Component c : getComponents()) {
/* 123:123 */       if ((c instanceof JButton)) {
/* 124:124 */         c.setBackground(normalColorResource);
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void resetButtons()
/* 130:    */   {
/* 131:130 */     for (Component c : getComponents()) {
/* 132:131 */       if ((c instanceof JButton)) {
/* 133:132 */         c.setBackground(normalColorResource);
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public ReflectionBar()
/* 139:    */   {
/* 140:138 */     setLayout(new RepairedGridLayout(1, 0));
/* 141:139 */     setPreferredSize(new Dimension(0, 20));
/* 142:140 */     setBackground(Color.WHITE);
/* 143:141 */     setOpaque(true);
/* 144:142 */     Connections.getPorts(this).addSignalProcessor("addPlotUnitButton", "addPlotUnitButton");
/* 145:143 */     Connections.getPorts(this).addSignalProcessor("clearButtons", "clearButtons");
/* 146:144 */     Connections.getPorts(this).addSignalProcessor("reset", "resetButtons");
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.ReflectionBar
 * JD-Core Version:    0.7.0.1
 */