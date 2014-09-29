/*   1:    */ package matthewFay.CharacterModeling;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Set;
/*  13:    */ import javax.swing.JCheckBox;
/*  14:    */ import matthewFay.StoryGeneration.PlotWeaver;
/*  15:    */ import matthewFay.representations.BasicCharacterModel;
/*  16:    */ import matthewFay.viewers.CharacterViewer;
/*  17:    */ import start.StartPreprocessor;
/*  18:    */ import utils.Mark;
/*  19:    */ 
/*  20:    */ public class CharacterProcessor
/*  21:    */   extends AbstractWiredBox
/*  22:    */ {
/*  23: 23 */   public static boolean debug_logging = true;
/*  24:    */   public static final String PLOT_PLAY_BY_PLAY_PORT = "plot play by play port";
/*  25:    */   public static final String STAGE_DIRECTION_PORT = "reset port";
/*  26:    */   public static final String COMPLETE_STORY_ANALYSIS_PORT = "complete story analysis port";
/*  27:    */   
/*  28:    */   public CharacterProcessor()
/*  29:    */   {
/*  30: 30 */     setName("CharacterProcessor");
/*  31:    */     
/*  32: 32 */     Connections.getPorts(this).addSignalProcessor("reset port", "reset");
/*  33: 33 */     Connections.getPorts(this).addSignalProcessor("plot play by play port", "processPlotElement");
/*  34: 34 */     Connections.getPorts(this).addSignalProcessor("complete story analysis port", "processCompleteStory");
/*  35: 35 */     reset("reset");
/*  36:    */   }
/*  37:    */   
/*  38: 39 */   private List<String> seen_english = new ArrayList();
/*  39: 41 */   private static HashMap<Entity, BasicCharacterModel> character_library = new HashMap();
/*  40: 42 */   private static Set<Entity> generics_library = new HashSet();
/*  41: 44 */   private static ArrayList<BasicCharacterModel> active_characters = new ArrayList();
/*  42:    */   
/*  43:    */   public static ArrayList<BasicCharacterModel> getActiveCharacters()
/*  44:    */   {
/*  45: 46 */     return new ArrayList(active_characters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static BasicCharacterModel getCharacterModel(Entity e, boolean create_on_fail)
/*  49:    */   {
/*  50: 50 */     if (!character_library.containsKey(e)) {
/*  51: 51 */       if (create_on_fail)
/*  52:    */       {
/*  53: 52 */         BasicCharacterModel character = new BasicCharacterModel(e);
/*  54: 53 */         character_library.put(e, character);
/*  55: 54 */         active_characters.add(character);
/*  56: 55 */         Mark.say(new Object[] {Boolean.valueOf(debug_logging), "New character added to library: " + e });
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60: 57 */         return null;
/*  61:    */       }
/*  62:    */     }
/*  63: 60 */     return (BasicCharacterModel)character_library.get(e);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static BasicCharacterModel findBestCharacterModel(String name)
/*  67:    */   {
/*  68: 64 */     for (Entity character_entity : character_library.keySet()) {
/*  69: 65 */       if (character_entity.getType().equals(name)) {
/*  70: 66 */         return (BasicCharacterModel)character_library.get(character_entity);
/*  71:    */       }
/*  72:    */     }
/*  73: 69 */     return null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static BasicCharacterModel findBestCharacterModel(Entity e)
/*  77:    */   {
/*  78: 73 */     if (character_library.containsKey(e)) {
/*  79: 74 */       return (BasicCharacterModel)character_library.get(e);
/*  80:    */     }
/*  81: 75 */     for (Entity character_entity : character_library.keySet()) {
/*  82: 76 */       if (character_entity.getType().equals(e.getType())) {
/*  83: 77 */         return (BasicCharacterModel)character_library.get(character_entity);
/*  84:    */       }
/*  85:    */     }
/*  86: 80 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static boolean isCharacter(Entity e)
/*  90:    */   {
/*  91: 84 */     if (character_library.containsKey(e)) {
/*  92: 85 */       return true;
/*  93:    */     }
/*  94: 87 */     return false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static HashMap<Entity, BasicCharacterModel> getCharacterLibrary()
/*  98:    */   {
/*  99: 91 */     return character_library;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static Set<Entity> getGenericsLibrary()
/* 103:    */   {
/* 104: 95 */     return generics_library;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static void deleteCharacter(Entity e)
/* 108:    */   {
/* 109: 99 */     if (character_library.containsKey(e)) {
/* 110:100 */       active_characters.remove(character_library.get(e));
/* 111:    */     }
/* 112:102 */     character_library.remove(e);
/* 113:    */   }
/* 114:    */   
/* 115:105 */   private static HashMap<String, Integer> action_library = new HashMap();
/* 116:    */   
/* 117:    */   public void reset(Object o)
/* 118:    */   {
/* 119:112 */     if (o.equals("reset"))
/* 120:    */     {
/* 121:113 */       active_characters.clear();
/* 122:    */       
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:121 */       this.seen_english.clear();
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void processPlotElement(Object o)
/* 134:    */   {
/* 135:126 */     if (CharacterViewer.disableCharacterProcessor.isSelected()) {
/* 136:127 */       return;
/* 137:    */     }
/* 138:130 */     BetterSignal s = BetterSignal.isSignal(o);
/* 139:131 */     if (s == null) {
/* 140:131 */       return;
/* 141:    */     }
/* 142:134 */     Entity element = (Entity)s.get(0, Entity.class);
/* 143:135 */     if (element == null) {
/* 144:135 */       return;
/* 145:    */     }
/* 146:137 */     if (this.storyComplete)
/* 147:    */     {
/* 148:138 */       active_characters.clear();
/* 149:139 */       this.storyComplete = false;
/* 150:    */     }
/* 151:143 */     if ((element.toString().contains("save")) && (element.toString().contains("characters")))
/* 152:    */     {
/* 153:144 */       Mark.err(new Object[] {"Save command detected!" });
/* 154:145 */       Mark.err(new Object[] {element });
/* 155:146 */       return;
/* 156:    */     }
/* 157:149 */     this.seen_english.add(element.toEnglish());
/* 158:151 */     if (PlotWeaver.isWeaveCharactersEvent(element))
/* 159:    */     {
/* 160:152 */       Mark.say(new Object[] {"Weave character plots!" });
/* 161:153 */       for (BasicCharacterModel character : active_characters) {
/* 162:154 */         Mark.say(new Object[] {character });
/* 163:    */       }
/* 164:156 */       PlotWeaver pw = new PlotWeaver(active_characters);
/* 165:157 */       Object plot = pw.weavePlots();
/* 166:    */       
/* 167:    */ 
/* 168:160 */       CharacterViewer.disableCharacterProcessor.setSelected(true);
/* 169:161 */       for (Entity plot_elt : (List)plot)
/* 170:    */       {
/* 171:162 */         String english = plot_elt.toEnglish();
/* 172:163 */         Mark.say(new Object[] {english });
/* 173:165 */         if (!this.seen_english.contains(english)) {
/* 174:166 */           StartPreprocessor.getStartPreprocessor().process(english);
/* 175:    */         }
/* 176:    */       }
/* 177:169 */       CharacterViewer.disableCharacterProcessor.setSelected(false);
/* 178:170 */       return;
/* 179:    */     }
/* 180:174 */     if (BasicCharacterModel.isCharacterMarker(element))
/* 181:    */     {
/* 182:175 */       Entity character_entity = BasicCharacterModel.extractCharacterEntity(element);
/* 183:176 */       getCharacterModel(character_entity, true);
/* 184:    */     }
/* 185:180 */     else if (BasicCharacterModel.isGenericMarker(element))
/* 186:    */     {
/* 187:182 */       Entity e = BasicCharacterModel.extractGenericEntity(element);
/* 188:183 */       generics_library.add(e);
/* 189:    */       
/* 190:185 */       Mark.say(new Object[] {Boolean.valueOf(debug_logging), "New generic added to library: " + e });
/* 191:    */     }
/* 192:189 */     updateModels(element);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void updateModels(Entity element)
/* 196:    */   {
/* 197:194 */     for (BasicCharacterModel character : active_characters) {
/* 198:195 */       character.observeEvent(element);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:203 */   boolean storyComplete = true;
/* 203:    */   
/* 204:    */   public void processCompleteStory(Object o)
/* 205:    */   {
/* 206:208 */     this.storyComplete = true;
/* 207:    */   }
/* 208:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.CharacterModeling.CharacterProcessor
 * JD-Core Version:    0.7.0.1
 */