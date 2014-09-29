/*   1:    */ package force;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import frames.ForceFrame;
/*  11:    */ import gui.ForceViewer;
/*  12:    */ import java.awt.Container;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import javax.swing.JFrame;
/*  15:    */ 
/*  16:    */ public class ForceInterpreter
/*  17:    */   extends AbstractWiredBox
/*  18:    */ {
/*  19: 35 */   public static final String[] forceWords = { "coerce" };
/*  20: 36 */   public static final String[] forceThread = { "action", "induce", "compel", "coerce", "force", "forced" };
/*  21: 37 */   public static final String[] causeWords = { "because", "cause" };
/*  22: 38 */   public static final String[] activeWords = { "trajectory", "transition" };
/*  23:    */   
/*  24:    */   public ForceInterpreter()
/*  25:    */   {
/*  26: 41 */     Connections.getPorts(this).addSignalProcessor("view");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void view(Object signal)
/*  30:    */   {
/*  31: 45 */     if ((signal instanceof Relation))
/*  32:    */     {
/*  33: 46 */       Relation force = (Relation)signal;
/*  34: 47 */       setParameters(force);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setParameters(Relation force)
/*  39:    */   {
/*  40: 63 */     Entity agonist = getAgonist(force);
/*  41: 64 */     Entity antagonist = getAntagonist(force);
/*  42:    */     
/*  43:    */ 
/*  44: 67 */     String agoStrength = findAgonistStrength(force);
/*  45: 68 */     String agoTend = findAgonistTendency(force);
/*  46:    */     
/*  47:    */ 
/*  48: 71 */     String agoShift = "notExit";
/*  49: 72 */     String antShift = "notExit";
/*  50: 73 */     Relation forceRelation = ForceFrame.makeForceRelation(agonist, agoShift, agoStrength, agoTend, antagonist, antShift);
/*  51: 74 */     if (force.hasFeature("not")) {
/*  52: 75 */       forceRelation.addFeature("not");
/*  53:    */     }
/*  54: 78 */     transmit(forceRelation);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static String findAgonistTendency(Relation force)
/*  58:    */   {
/*  59: 86 */     return findAgonistTendencyHelper(force.getObject());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String findAntagonistTendency(Relation force)
/*  63:    */   {
/*  64: 94 */     return ForceFrame.oppositeStrength(findAgonistTendency(force));
/*  65:    */   }
/*  66:    */   
/*  67:    */   private static String findAgonistTendencyHelper(Entity agoTree)
/*  68:    */   {
/*  69:105 */     if ((agoTree instanceof Sequence)) {
/*  70:106 */       return findAgonistTendencyHelper(agoTree.getElement(0));
/*  71:    */     }
/*  72:108 */     if ((agoTree instanceof Relation))
/*  73:    */     {
/*  74:    */       String result;
/*  75:112 */       if (agoTree.isAnyOf(activeWords)) {
/*  76:113 */         result = ForceFrame.tendencies[0];
/*  77:    */       } else {
/*  78:116 */         result = ForceFrame.tendencies[1];
/*  79:    */       }
/*  80:120 */       String result = ForceFrame.oppositeTendency(result);
/*  81:    */       
/*  82:122 */       return result;
/*  83:    */     }
/*  84:124 */     if ((agoTree instanceof Function))
/*  85:    */     {
/*  86:    */       String result;
/*  87:128 */       if (agoTree.isAnyOf(activeWords)) {
/*  88:129 */         result = ForceFrame.tendencies[0];
/*  89:    */       } else {
/*  90:132 */         result = ForceFrame.tendencies[1];
/*  91:    */       }
/*  92:136 */       String result = ForceFrame.oppositeTendency(result);
/*  93:    */       
/*  94:138 */       return result;
/*  95:    */     }
/*  96:142 */     return ForceFrame.tendencies[2];
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static String findAgonistStrength(Relation force)
/* 100:    */   {
/* 101:152 */     return ForceFrame.oppositeStrength(findAntagonistStrength(force));
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static String findAntagonistStrength(Relation force)
/* 105:    */   {
/* 106:164 */     if (force.isAnyOf(forceWords)) {
/* 107:166 */       return ForceFrame.strengths[0];
/* 108:    */     }
/* 109:170 */     if (force.isAnyOf(causeWords)) {
/* 110:171 */       return ForceFrame.strengths[0];
/* 111:    */     }
/* 112:175 */     if (ForceMemory.isForceRelation(force)) {
/* 113:176 */       return ForceFrame.strengths[0];
/* 114:    */     }
/* 115:180 */     return ForceFrame.strengths[4];
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static Entity getAgonist(Relation force)
/* 119:    */   {
/* 120:184 */     return findSubjectThing(force.getObject());
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static Entity getAntagonist(Relation force)
/* 124:    */   {
/* 125:188 */     return findSubjectThing(force.getSubject());
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static Entity findSubjectThing(Entity input)
/* 129:    */   {
/* 130:198 */     if ((input instanceof Relation)) {
/* 131:199 */       return findSubjectThing(((Relation)input).getSubject());
/* 132:    */     }
/* 133:201 */     if ((input instanceof Function)) {
/* 134:202 */       return findSubjectThing(((Function)input).getSubject());
/* 135:    */     }
/* 136:204 */     if ((input instanceof Sequence)) {
/* 137:205 */       return new Entity("unknown");
/* 138:    */     }
/* 139:208 */     return input;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void transmit(Entity t)
/* 143:    */   {
/* 144:214 */     Connections.getPorts(this).transmit("viewer", t);
/* 145:    */   }
/* 146:    */   
/* 147:    */   private void transmit(ForceFrame f)
/* 148:    */   {
/* 149:218 */     transmit(f.getThing());
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static void main(String[] args)
/* 153:    */   {
/* 154:226 */     ForceViewer view = new ForceViewer();
/* 155:    */     
/* 156:228 */     JFrame frame = new JFrame();
/* 157:229 */     frame.getContentPane().add(view);
/* 158:230 */     frame.setBounds(0, 0, 200, 200);
/* 159:231 */     frame.setVisible(true);
/* 160:232 */     view.view(ForceFrame.getMap().get("The ball kept rolling despite the stiff grass"));
/* 161:    */   }
/* 162:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     force.ForceInterpreter
 * JD-Core Version:    0.7.0.1
 */