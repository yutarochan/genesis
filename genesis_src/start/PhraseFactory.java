/*   1:    */ package start;
/*   2:    */ 
/*   3:    */ import adam.RPCBox;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Connections.NetWireException;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import constants.GenesisConstants;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.UUID;
/*  11:    */ import utils.Mark;
/*  12:    */ 
/*  13:    */ public class PhraseFactory
/*  14:    */   extends RoleFrameParent
/*  15:    */ {
/*  16:    */   private static PhraseFactory phraseFactory;
/*  17:    */   RPCBox clientBox;
/*  18:    */   WiredBox clientProxy;
/*  19: 24 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/*  20: 26 */   UUID uuid = UUID.randomUUID();
/*  21:    */   
/*  22:    */   public static PhraseFactory getPhraseFactory()
/*  23:    */   {
/*  24: 29 */     if (phraseFactory == null) {
/*  25: 30 */       phraseFactory = new PhraseFactory();
/*  26:    */     }
/*  27: 32 */     return phraseFactory;
/*  28:    */   }
/*  29:    */   
/*  30:    */   private PhraseFactory()
/*  31:    */   {
/*  32: 36 */     createClient();
/*  33:    */   }
/*  34:    */   
/*  35:    */   private boolean createClient()
/*  36:    */   {
/*  37:    */     try
/*  38:    */     {
/*  39: 41 */       this.clientProxy = Connections.subscribe(GenesisConstants.server, 5.0D);
/*  40: 42 */       this.clientBox = ((RPCBox)this.clientProxy);
/*  41: 43 */       return true;
/*  42:    */     }
/*  43:    */     catch (Connections.NetWireException e)
/*  44:    */     {
/*  45: 46 */       Mark.err(new Object[] {"Failed to create Start client" });
/*  46:    */     }
/*  47: 47 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String before(RoleFrameParent one, RoleFrameParent two)
/*  51:    */   {
/*  52: 52 */     Mark.say(
/*  53:    */     
/*  54:    */ 
/*  55: 55 */       new Object[] { "One's relation", one.getHead() });Mark.say(new Object[] { "Two's relation", two.getHead() });return makeTriple(one.getHead(), "before", two.getHead()) + one + two;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String generate(RoleFrame r)
/*  59:    */   {
/*  60: 58 */     return translate(r.getRendering());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String generate(RoleFrameParent p)
/*  64:    */   {
/*  65: 62 */     return generate(p.toString(), new RoleFrameGrandParent[0]);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String generate(String request, RoleFrameGrandParent... entities)
/*  69:    */   {
/*  70: 66 */     String instructions = request.trim();
/*  71: 67 */     if (instructions.startsWith("[")) {
/*  72: 68 */       return translate(instructions);
/*  73:    */     }
/*  74: 70 */     instructions = new RoleFrameParent(request, entities).toString();
/*  75:    */     
/*  76: 72 */     return translate(instructions);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String translate(String instructions)
/*  80:    */   {
/*  81: 76 */     String request = remap(instructions);
/*  82: 77 */     String result = null;
/*  83:    */     try
/*  84:    */     {
/*  85: 79 */       Object[] arguments = { request, this.uuid.toString() };
/*  86: 80 */       Object value = this.clientBox.rpc("remoteGenerate", arguments);
/*  87: 81 */       if (value != null) {
/*  88: 82 */         result = ((String)value).trim();
/*  89:    */       }
/*  90:    */     }
/*  91:    */     catch (Exception e)
/*  92:    */     {
/*  93:    */       try
/*  94:    */       {
/*  95: 87 */         Mark.say(new Object[] {"Bug in effort to process sentence remotely!" });
/*  96: 88 */         result = (String)StartServerBox.getStartServerBox().remoteGenerate(request, this.uuid.toString());
/*  97: 89 */         Mark.say(new Object[] {"Succeeded locally:", result });
/*  98:    */       }
/*  99:    */       catch (Exception e1)
/* 100:    */       {
/* 101: 92 */         Mark.say(new Object[] {"Bug in effort to process sentence locally too!  Give up." });
/* 102: 93 */         e1.printStackTrace();
/* 103:    */       }
/* 104:    */     }
/* 105: 96 */     return result;
/* 106:    */   }
/* 107:    */   
/* 108:    */   private String remap(String triples)
/* 109:    */   {
/* 110:103 */     HashMap<String, String> substitutions = new HashMap();
/* 111:104 */     int index = 0;
/* 112:105 */     StringBuffer buffer = new StringBuffer(triples);
/* 113:    */     
/* 114:    */ 
/* 115:108 */     int from = 0;
/* 116:    */     for (;;)
/* 117:    */     {
/* 118:110 */       int nextPlus = buffer.indexOf("+", from);
/* 119:111 */       if (nextPlus < 0) {
/* 120:    */         break;
/* 121:    */       }
/* 122:115 */       int nextSpace = buffer.indexOf(" ", nextPlus);
/* 123:116 */       int nextBracket = buffer.indexOf("]", nextPlus);
/* 124:117 */       int winner = 0;
/* 125:118 */       if ((nextSpace >= 0) && (nextBracket >= 0)) {
/* 126:119 */         winner = Math.min(nextSpace, nextBracket);
/* 127:121 */       } else if (nextSpace >= 0) {
/* 128:122 */         winner = nextSpace;
/* 129:124 */       } else if (nextBracket >= 0) {
/* 130:125 */         winner = nextBracket;
/* 131:    */       } else {
/* 132:128 */         Mark.err(new Object[] {"Ooops, bug in Start.remap" });
/* 133:    */       }
/* 134:130 */       String key = buffer.substring(nextPlus, winner);
/* 135:131 */       String substitution = (String)substitutions.get(key);
/* 136:132 */       if (substitution == null)
/* 137:    */       {
/* 138:133 */         substitution = Integer.toString(index++);
/* 139:134 */         substitutions.put(key, substitution);
/* 140:    */       }
/* 141:136 */       buffer.replace(nextPlus + 1, winner, substitution);
/* 142:137 */       from = nextPlus + 1;
/* 143:    */     }
/* 144:139 */     return buffer.toString();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public RPCBox getClientBox()
/* 148:    */   {
/* 149:143 */     return this.clientBox;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static void main(String[] x)
/* 153:    */   {
/* 154:148 */     Generator generator = Generator.getGenerator();
/* 155:    */     
/* 156:150 */     RoleFrame p1 = new RoleFrame("man").addFeature("large");
/* 157:    */     
/* 158:152 */     RoleFrame p2 = new RoleFrame("woman").addFeature("small").makeIndefinite();
/* 159:    */     
/* 160:154 */     RoleFrame p3 = new RoleFrame("person").addFeature("second").makeIndefinite();
/* 161:    */     
/* 162:156 */     RoleFrame t1 = new RoleFrame("truck").makeIndefinite();
/* 163:    */     
/* 164:    */ 
/* 165:159 */     RoleFrame o1 = new RoleFrame("phone").addFeature("cell").addFeature("black").makeIndefinite();
/* 166:    */     
/* 167:161 */     RoleFrame f1 = new RoleFrame(p2, "hide").makeFuture();
/* 168:    */     
/* 169:163 */     RoleFrame f2 = new RoleFrame(t1, "appear").makePresent();
/* 170:    */     
/* 171:165 */     System.out.println(generator.generate(f2));
/* 172:    */     
/* 173:167 */     System.out.println(generator.generate(new RoleFrame("truck", "appear")));
/* 174:    */     
/* 175:169 */     System.out.println(generator.generate(new RoleFrame("man", "give", o1).addRole("to", "woman").makePast()));
/* 176:    */     
/* 177:171 */     System.out.println(generator.generate(new RoleFrame("man", "give", o1).addRole("to", "woman").makePresent()));
/* 178:    */     
/* 179:173 */     System.out.println(generator.generate(new RoleFrame("man", "give", o1).addRole("to", "woman").makeFuture()));
/* 180:    */     
/* 181:175 */     System.out.println(generator.generate(new RoleFrame(p1, "give", o1).addRole("to", p2).makePresent()));
/* 182:    */     
/* 183:177 */     System.out.println(generator.generate(f1.connect("before", f2.makePast())));
/* 184:    */     
/* 185:179 */     System.out.println(generator.generate(p1.embed("know", f2)));
/* 186:    */     
/* 187:181 */     System.out.println(generator.generate(p2.embed("think", f2.negative()).negative().makePast()));
/* 188:    */     
/* 189:183 */     System.out.println(generator.generate(new RoleFrame(p2, "left", p1).makeFuture().connect("after", new RoleFrame(p2, "bury", o1).makePresent())));
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.PhraseFactory
 * JD-Core Version:    0.7.0.1
 */