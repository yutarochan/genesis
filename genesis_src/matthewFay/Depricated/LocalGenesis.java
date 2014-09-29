/*   1:    */ package matthewFay.Depricated;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Connections.NetWireException;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import genesis.Genesis;
/*   8:    */ import java.io.FileInputStream;
/*   9:    */ import java.io.ObjectInputStream;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import start.Start;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ @Deprecated
/*  15:    */ public class LocalGenesis
/*  16:    */   extends Genesis
/*  17:    */ {
/*  18:    */   public static TeachingProcessor teachingProcessor;
/*  19:    */   
/*  20:    */   public static TeachingProcessor getTeachingProcessor()
/*  21:    */   {
/*  22: 30 */     if (teachingProcessor == null) {
/*  23: 31 */       teachingProcessor = new TeachingProcessor();
/*  24:    */     }
/*  25: 33 */     return teachingProcessor;
/*  26:    */   }
/*  27:    */   
/*  28: 44 */   private static PersonaProcessor personaProcessor = null;
/*  29:    */   
/*  30:    */   public static PersonaProcessor getPersonaProcessor()
/*  31:    */   {
/*  32: 47 */     if (personaProcessor == null) {
/*  33: 48 */       personaProcessor = new PersonaProcessor();
/*  34:    */     }
/*  35: 49 */     return personaProcessor;
/*  36:    */   }
/*  37:    */   
/*  38: 52 */   private static LocalGenesis localGenesis = null;
/*  39:    */   
/*  40:    */   public static LocalGenesis localGenesis()
/*  41:    */   {
/*  42: 55 */     return localGenesis;
/*  43:    */   }
/*  44:    */   
/*  45: 58 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/*  46:    */   
/*  47:    */   public LocalGenesis()
/*  48:    */   {
/*  49: 62 */     localGenesis = this;
/*  50: 63 */     Mark.say(new Object[] {"Running Matthew's constructor" });
/*  51:    */     
/*  52: 65 */     boolean ONLINE = true;
/*  53: 66 */     if (ONLINE) {
/*  54:    */       try
/*  55:    */       {
/*  56: 68 */         System.out.println("Connecting to Persona");
/*  57:    */         
/*  58: 70 */         WiredBox pub = Connections.subscribe("persona", 2.0D);
/*  59:    */         
/*  60:    */ 
/*  61: 73 */         Connections.getPorts(getPersonaProcessor()).addSignalProcessor("processPersonaSignal");
/*  62:    */         
/*  63:    */ 
/*  64: 76 */         Connections.wire(pub, getPersonaProcessor());
/*  65: 77 */         Connections.wire(getPersonaProcessor(), pub);
/*  66:    */         
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70: 82 */         System.out.println("Connected to Persona");
/*  71:    */       }
/*  72:    */       catch (Connections.NetWireException e)
/*  73:    */       {
/*  74: 85 */         ONLINE = false;
/*  75: 86 */         e.printStackTrace();
/*  76:    */       }
/*  77:    */     }
/*  78: 89 */     if (!ONLINE)
/*  79:    */     {
/*  80: 90 */       System.out.println("Connecting to Persona");
/*  81:    */       
/*  82: 92 */       PersonaServer.PersonaPublisher pub = new PersonaServer.PersonaPublisher();
/*  83:    */       try
/*  84:    */       {
/*  85: 94 */         FileInputStream fin = new FileInputStream("persona.dat");
/*  86: 95 */         ObjectInputStream ois = new ObjectInputStream(fin);
/*  87: 96 */         pub = (PersonaServer.PersonaPublisher)ois.readObject();
/*  88: 97 */         ois.close();
/*  89:    */       }
/*  90:    */       catch (Exception e)
/*  91:    */       {
/*  92: 99 */         e.printStackTrace();
/*  93:    */       }
/*  94:101 */       Connections.getPorts(pub).addSignalProcessor("process");
/*  95:    */       
/*  96:    */ 
/*  97:    */ 
/*  98:105 */       Connections.getPorts(getPersonaProcessor()).addSignalProcessor("processPersona");
/*  99:    */       
/* 100:107 */       Connections.wire(pub, getPersonaProcessor());
/* 101:108 */       Connections.wire(getPersonaProcessor(), pub);
/* 102:    */       
/* 103:110 */       System.out.println("Connected to Persona");
/* 104:    */     }
/* 105:113 */     Connections.wire("complete story events port", getMentalModel1(), "story port", getTeachingProcessor());
/* 106:114 */     Connections.wire("complete story events port", getMentalModel2(), "story port2", getTeachingProcessor());
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:127 */     Connections.biwire("reflections port", getMentalModel1(), "reflection port", getTeachingProcessor());
/* 120:128 */     Connections.biwire("reflections port", getMentalModel2(), "reflection port2", getTeachingProcessor());
/* 121:129 */     Connections.biwire("rule port", getMentalModel1(), "rule port", getTeachingProcessor());
/* 122:130 */     Connections.biwire("rule port", getMentalModel2(), "rule port2", getTeachingProcessor());
/* 123:    */     
/* 124:    */ 
/* 125:133 */     Connections.wire("used rules port", getMentalModel1(), "used rules1", getTeachingProcessor());
/* 126:134 */     Connections.wire("used rules port", getMentalModel2(), "used rules2", getTeachingProcessor());
/* 127:    */     
/* 128:    */ 
/* 129:137 */     Connections.wire(Start.STAGE_DIRECTION_PORT, getStartParser(), "stage direction", getTeachingProcessor());
/* 130:    */     
/* 131:139 */     Connections.wire("rule", getTeachingProcessor(), "port for rule injection", getMentalModel1());
/* 132:140 */     Connections.wire("rule", getTeachingProcessor(), "port for rule injection", getMentalModel2());
/* 133:    */     
/* 134:    */ 
/* 135:    */ 
/* 136:144 */     Connections.biwire("rule port", getMentalModel1(), "rule port 1", getPersonaProcessor());
/* 137:145 */     Connections.biwire("reflections port", getMentalModel1(), "reflection port 1", getPersonaProcessor());
/* 138:146 */     Connections.biwire("rule port", getMentalModel2(), "rule port 2", getPersonaProcessor());
/* 139:147 */     Connections.biwire("reflections port", getMentalModel2(), "reflection port 2", getPersonaProcessor());
/* 140:148 */     Connections.wire("persona port", getStartParser(), "start parser", getPersonaProcessor());
/* 141:149 */     Connections.wire(Start.STAGE_DIRECTION_PORT, getStartParser(), "stage direction 1", getPersonaProcessor());
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static void main(String[] args)
/* 145:    */   {
/* 146:153 */     LocalGenesis myGenesis = new LocalGenesis();
/* 147:154 */     myGenesis.startInFrame();
/* 148:    */   }
/* 149:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */