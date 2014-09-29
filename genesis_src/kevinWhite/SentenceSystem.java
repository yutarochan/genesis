/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.InputStreamReader;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ 
/*   9:    */ public class SentenceSystem
/*  10:    */ {
/*  11: 18 */   ArrayList<String> sentences = new ArrayList();
/*  12:    */   ConceptManager cm;
/*  13:    */   
/*  14:    */   public SentenceSystem()
/*  15:    */   {
/*  16: 22 */     this.cm = new ConceptManager();
/*  17: 23 */     getSwimmers();
/*  18: 24 */     getSpeakers();
/*  19: 25 */     getLookers();
/*  20: 26 */     getCrawlers();
/*  21: 27 */     getFlyers();
/*  22: 28 */     getGrabbers();
/*  23: 29 */     getWalkers();
/*  24: 30 */     getSwayers();
/*  25: 31 */     getThrowers();
/*  26: 32 */     getSpinners();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void getSwimmers()
/*  30:    */   {
/*  31: 36 */     this.sentences.add("Humans can swim.");
/*  32: 37 */     this.sentences.add("Lions can swim.");
/*  33: 38 */     this.sentences.add("Bats can't swim.");
/*  34: 39 */     this.sentences.add("Ducks can swim.");
/*  35: 40 */     this.sentences.add("Robots can't swim.");
/*  36: 41 */     this.sentences.add("Boats can swim.");
/*  37: 42 */     this.sentences.add("Helicopters can't swim.");
/*  38: 43 */     this.sentences.add("Spaceships can swim.");
/*  39: 44 */     this.sentences.add("Ants can't swim.");
/*  40: 45 */     this.sentences.add("Plants can't swim.");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void getSpeakers()
/*  44:    */   {
/*  45: 49 */     this.sentences.add("Humans can speak.");
/*  46: 50 */     this.sentences.add("Insects can't speak.");
/*  47: 51 */     this.sentences.add("Plants can't speak.");
/*  48: 52 */     this.sentences.add("Whales can speak.");
/*  49: 53 */     this.sentences.add("Lizards can speak.");
/*  50: 54 */     this.sentences.add("Robots can speak.");
/*  51: 55 */     this.sentences.add("Drones can't speak.");
/*  52: 56 */     this.sentences.add("Airplanes can't speak.");
/*  53: 57 */     this.sentences.add("Computers can speak.");
/*  54: 58 */     this.sentences.add("Boats can't speak.");
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void getLookers()
/*  58:    */   {
/*  59: 62 */     this.sentences.add("Humans can look.");
/*  60: 63 */     this.sentences.add("Birds can look.");
/*  61: 64 */     this.sentences.add("Insects can look.");
/*  62: 65 */     this.sentences.add("Reptiles can look.");
/*  63: 66 */     this.sentences.add("Mammals can look.");
/*  64: 67 */     this.sentences.add("Boats can't look.");
/*  65: 68 */     this.sentences.add("Drones can look.");
/*  66: 69 */     this.sentences.add("Airplanes can't look");
/*  67: 70 */     this.sentences.add("Cars can't look.");
/*  68: 71 */     this.sentences.add("Robots can look.");
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void getCrawlers()
/*  72:    */   {
/*  73: 75 */     this.sentences.add("Humans can crawl.");
/*  74: 76 */     this.sentences.add("Elephants can't crawl.");
/*  75: 77 */     this.sentences.add("Lions can crawl.");
/*  76: 78 */     this.sentences.add("Ants can crawl.");
/*  77: 79 */     this.sentences.add("Snakes can't crawl.");
/*  78: 80 */     this.sentences.add("Tanks can crawl.");
/*  79: 81 */     this.sentences.add("Boats can't crawl.");
/*  80: 82 */     this.sentences.add("Cars can't crawl.");
/*  81: 83 */     this.sentences.add("Airplanes can't crawl.");
/*  82: 84 */     this.sentences.add("Robots can crawl.");
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void getFlyers()
/*  86:    */   {
/*  87: 88 */     this.sentences.add("Humans can't fly.");
/*  88: 89 */     this.sentences.add("Centipedes can't fly.");
/*  89: 90 */     this.sentences.add("Chickens can't fly.");
/*  90: 91 */     this.sentences.add("Hawks can fly.");
/*  91: 92 */     this.sentences.add("Butterflies can fly.");
/*  92: 93 */     this.sentences.add("Boats can't fly.");
/*  93: 94 */     this.sentences.add("Cars can't fly.");
/*  94: 95 */     this.sentences.add("Airplanes can fly.");
/*  95: 96 */     this.sentences.add("Drones can fly.");
/*  96: 97 */     this.sentences.add("Phones can't fly.");
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void getGrabbers()
/* 100:    */   {
/* 101:101 */     this.sentences.add("Humans can grab.");
/* 102:102 */     this.sentences.add("Cats can grab.");
/* 103:103 */     this.sentences.add("Dogs can grab.");
/* 104:104 */     this.sentences.add("Worms can't grab.");
/* 105:105 */     this.sentences.add("Fish can grab.");
/* 106:106 */     this.sentences.add("Cars can't grab.");
/* 107:107 */     this.sentences.add("Robots can grab.");
/* 108:108 */     this.sentences.add("Drones can grab.");
/* 109:109 */     this.sentences.add("Airplanes can't grab.");
/* 110:110 */     this.sentences.add("Boats can't grab.");
/* 111:    */   }
/* 112:    */   
/* 113:    */   private void getWalkers()
/* 114:    */   {
/* 115:114 */     this.sentences.add("Snakes can't walk.");
/* 116:115 */     this.sentences.add("Humans can walk.");
/* 117:116 */     this.sentences.add("Lions can walk.");
/* 118:117 */     this.sentences.add("Worms can't walk.");
/* 119:118 */     this.sentences.add("Insects can walk.");
/* 120:119 */     this.sentences.add("Robots can walk.");
/* 121:120 */     this.sentences.add("Boats can't walk.");
/* 122:121 */     this.sentences.add("Cars can't walk.");
/* 123:122 */     this.sentences.add("Airplanes can't walk.");
/* 124:123 */     this.sentences.add("Slugs can't walk.");
/* 125:    */   }
/* 126:    */   
/* 127:    */   private void getSwayers()
/* 128:    */   {
/* 129:127 */     this.sentences.add("Humans can sway.");
/* 130:128 */     this.sentences.add("Birds can sway.");
/* 131:129 */     this.sentences.add("Ants can't sway.");
/* 132:130 */     this.sentences.add("Butterflies can sway.");
/* 133:131 */     this.sentences.add("Scorpions can't sway.");
/* 134:132 */     this.sentences.add("Boats can sway.");
/* 135:133 */     this.sentences.add("Airplanes can sway.");
/* 136:134 */     this.sentences.add("Cars can sway.");
/* 137:135 */     this.sentences.add("Poles can sway.");
/* 138:136 */     this.sentences.add("Boxes can't sway.");
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void getThrowers()
/* 142:    */   {
/* 143:140 */     this.sentences.add("Humans can throw a ball.");
/* 144:141 */     this.sentences.add("Insects can throw a ball.");
/* 145:142 */     this.sentences.add("Fish can't throw a ball.");
/* 146:143 */     this.sentences.add("Dogs can throw a ball.");
/* 147:144 */     this.sentences.add("Snakes can't throw a ball.");
/* 148:145 */     this.sentences.add("Robots can throw a ball.");
/* 149:146 */     this.sentences.add("Airplanes can't throw a ball.");
/* 150:147 */     this.sentences.add("Boats can't throw a ball.");
/* 151:148 */     this.sentences.add("A bulldozer can throw a ball.");
/* 152:149 */     this.sentences.add("Catapults can throw a ball.");
/* 153:    */   }
/* 154:    */   
/* 155:    */   private void getSpinners()
/* 156:    */   {
/* 157:153 */     this.sentences.add("Humans can spin.");
/* 158:154 */     this.sentences.add("Snakes can't spin.");
/* 159:155 */     this.sentences.add("Dogs can spin.");
/* 160:156 */     this.sentences.add("Plants can't spin.");
/* 161:157 */     this.sentences.add("Butterflies can't spin.");
/* 162:158 */     this.sentences.add("Airplanes can spin.");
/* 163:159 */     this.sentences.add("Cars can spin.");
/* 164:160 */     this.sentences.add("Robots can spin.");
/* 165:161 */     this.sentences.add("Balls can spin.");
/* 166:162 */     this.sentences.add("Blocks can't spin.");
/* 167:    */   }
/* 168:    */   
/* 169:    */   public ArrayList<String> getSentences()
/* 170:    */   {
/* 171:166 */     return this.sentences;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static void main(String[] args)
/* 175:    */   {
/* 176:170 */     SentenceSystem ss = new SentenceSystem();
/* 177:171 */     AutomatedLearner learner = new AutomatedLearner(ss.getSentences(), false);
/* 178:172 */     for (FasterLLConcept flc : learner.getConceptList()) {
/* 179:173 */       ss.cm.addConcept(flc);
/* 180:    */     }
/* 181:175 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
/* 182:    */     try
/* 183:    */     {
/* 184:178 */       String line = "";
/* 185:179 */       while (!line.equals("exit"))
/* 186:    */       {
/* 187:181 */         System.out.print("Enter a sentence or type exit to terminate:");
/* 188:182 */         line = bufferedReader.readLine();
/* 189:183 */         System.out.print("I saw this line: " + line);
/* 190:184 */         HashMap<String, Object> sentData = FasterLLConcept.parseSimpleSentence(line);
/* 191:185 */         FasterLLConcept verbConcept = ss.cm.getConcept((String)sentData.get("verb"));
/* 192:186 */         System.out.println(verbConcept.interpretSimpleSentence(sentData));
/* 193:    */       }
/* 194:    */     }
/* 195:    */     catch (Exception e)
/* 196:    */     {
/* 197:190 */       e.printStackTrace();
/* 198:    */     }
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.SentenceSystem
 * JD-Core Version:    0.7.0.1
 */