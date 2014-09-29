/*   1:    */ package matthewFay.Exporter;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import com.google.common.collect.HashMultimap;
/*   7:    */ import com.google.common.collect.Multimap;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import connections.WiredBox;
/*  11:    */ import java.awt.BorderLayout;
/*  12:    */ import java.awt.event.ActionEvent;
/*  13:    */ import java.awt.event.ActionListener;
/*  14:    */ import java.io.BufferedWriter;
/*  15:    */ import java.io.File;
/*  16:    */ import java.io.FileWriter;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Collection;
/*  20:    */ import java.util.Iterator;
/*  21:    */ import java.util.List;
/*  22:    */ import javax.swing.JButton;
/*  23:    */ import javax.swing.JFileChooser;
/*  24:    */ import javax.swing.JFrame;
/*  25:    */ import javax.swing.JPanel;
/*  26:    */ import javax.swing.filechooser.FileNameExtensionFilter;
/*  27:    */ import matthewFay.CharacterModeling.CharacterProcessor;
/*  28:    */ import matthewFay.CharacterModeling.representations.Trait;
/*  29:    */ import matthewFay.representations.BasicCharacterModel;
/*  30:    */ import utils.Mark;
/*  31:    */ 
/*  32:    */ public class ExperimentExportProcessor
/*  33:    */   extends JPanel
/*  34:    */   implements WiredBox, ActionListener
/*  35:    */ {
/*  36: 40 */   private static ExperimentExportProcessor eep = null;
/*  37:    */   
/*  38:    */   public static ExperimentExportProcessor getExperimentExportProcessor()
/*  39:    */   {
/*  40: 42 */     if (eep == null) {
/*  41: 43 */       eep = new ExperimentExportProcessor();
/*  42:    */     }
/*  43: 44 */     return eep;
/*  44:    */   }
/*  45:    */   
/*  46: 47 */   private JButton exportStoriesButton = new JButton("Export Stories to JSON");
/*  47:    */   List<BetterSignal> signals;
/*  48:    */   Multimap<Entity, BasicCharacterModel> character_map;
/*  49:    */   
/*  50:    */   public String getName()
/*  51:    */   {
/*  52: 50 */     return "Export Processor";
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ExperimentExportProcessor()
/*  56:    */   {
/*  57: 54 */     super(new BorderLayout());
/*  58:    */     
/*  59: 56 */     this.exportStoriesButton.addActionListener(this);
/*  60: 57 */     add(this.exportStoriesButton);
/*  61:    */     
/*  62:    */ 
/*  63: 60 */     setName("Export Processor");
/*  64: 61 */     Connections.getPorts(this).addSignalProcessor("complete story analysis port", "processStory");
/*  65:    */     
/*  66: 63 */     this.signals = new ArrayList();
/*  67: 64 */     this.character_map = HashMultimap.create();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void processStory(Object o)
/*  71:    */   {
/*  72: 71 */     BetterSignal signal = BetterSignal.isSignal(o);
/*  73: 72 */     if (signal == null) {
/*  74: 73 */       return;
/*  75:    */     }
/*  76: 75 */     Sequence story = (Sequence)signal.get(0, Sequence.class);
/*  77: 76 */     Sequence explicitElements = (Sequence)signal.get(1, Sequence.class);
/*  78: 77 */     Sequence inferences = (Sequence)signal.get(2, Sequence.class);
/*  79: 78 */     Sequence concepts = (Sequence)signal.get(3, Sequence.class);
/*  80:    */     
/*  81: 80 */     this.signals.add(signal);
/*  82: 81 */     List<BasicCharacterModel> characters = CharacterProcessor.getActiveCharacters();
/*  83: 82 */     this.character_map.putAll(story, characters);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void actionPerformed(ActionEvent actionEvent)
/*  87:    */   {
/*  88: 87 */     Mark.say(
/*  89:    */     
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
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
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:197 */       new Object[] { "Exporting..." });JFrame parentFrame = new JFrame();JFileChooser fileChooser = new JFileChooser();fileChooser.setDialogTitle("Specify a file to save");fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Data", new String[] { "json" }));int userSelection = fileChooser.showSaveDialog(parentFrame);
/* 199: 98 */     if (userSelection == 0)
/* 200:    */     {
/* 201: 99 */       File fileToSave = fileChooser.getSelectedFile();
/* 202:100 */       Mark.say(new Object[] {"Save as file: " + fileToSave.getAbsolutePath() });
/* 203:    */       try
/* 204:    */       {
/* 205:103 */         BufferedWriter out = null;
/* 206:104 */         FileWriter fstream = new FileWriter(fileToSave);
/* 207:105 */         out = new BufferedWriter(fstream);
/* 208:    */         
/* 209:107 */         out.write("{\n");
/* 210:    */         
/* 211:109 */         out.write("\t\"stories\": [\n");
/* 212:    */         
/* 213:111 */         boolean firstStory = true;
/* 214:112 */         for (BetterSignal signal : this.signals)
/* 215:    */         {
/* 216:114 */           if (!firstStory) {
/* 217:115 */             out.write(",\n");
/* 218:    */           }
/* 219:118 */           out.write("\t\t{\n");
/* 220:    */           
/* 221:120 */           Sequence story = (Sequence)signal.get(0, Sequence.class);
/* 222:121 */           String title = story.getType();
/* 223:    */           
/* 224:    */ 
/* 225:124 */           out.write("\t\t\t\"title\":\"" + title + "\",\n");
/* 226:    */           
/* 227:    */ 
/* 228:127 */           out.write("\t\t\t\"characters\": [\n");
/* 229:128 */           Collection<BasicCharacterModel> characters = this.character_map.get(story);
/* 230:    */           
/* 231:130 */           boolean firstCharacter = true;
/* 232:    */           Trait trait;
/* 233:131 */           for (BasicCharacterModel character : characters)
/* 234:    */           {
/* 235:132 */             if (!firstCharacter) {
/* 236:133 */               out.write(",\n");
/* 237:    */             }
/* 238:135 */             out.write("\t\t\t\t{ ");
/* 239:136 */             out.write("\"name\":\"" + character.getSimpleName() + "\", ");
/* 240:    */             
/* 241:    */ 
/* 242:139 */             out.write("\"traits\": [");
/* 243:    */             
/* 244:141 */             boolean firstTrait = true;
/* 245:142 */             for (Iterator localIterator3 = character.getTraits(true).iterator(); localIterator3.hasNext();)
/* 246:    */             {
/* 247:142 */               trait = (Trait)localIterator3.next();
/* 248:143 */               if (!firstTrait) {
/* 249:144 */                 out.write(", ");
/* 250:    */               }
/* 251:146 */               out.write("\"" + trait.getName() + "\"");
/* 252:147 */               firstTrait = false;
/* 253:    */             }
/* 254:151 */             out.write("]");
/* 255:    */             
/* 256:    */ 
/* 257:154 */             out.write("}");
/* 258:155 */             firstCharacter = false;
/* 259:    */           }
/* 260:158 */           out.write("\n\t\t\t],\n");
/* 261:    */           
/* 262:    */ 
/* 263:161 */           out.write("\t\t\t\"text\": [\n");
/* 264:    */           
/* 265:163 */           Sequence explicitElements = (Sequence)signal.get(1, Sequence.class);
/* 266:164 */           boolean firstElt = true;
/* 267:165 */           for (Entity e : explicitElements.getAllComponents())
/* 268:    */           {
/* 269:167 */             if (!firstElt) {
/* 270:168 */               out.write(",\n");
/* 271:    */             }
/* 272:170 */             String english = e.toEnglish();
/* 273:    */             
/* 274:    */ 
/* 275:173 */             out.write("\t\t\t\t\"" + english + "\"");
/* 276:    */             
/* 277:175 */             firstElt = false;
/* 278:    */           }
/* 279:178 */           out.write("\n\t\t\t]\n");
/* 280:    */           
/* 281:    */ 
/* 282:181 */           out.write("\t\t}");
/* 283:182 */           firstStory = false;
/* 284:    */         }
/* 285:185 */         out.write("\n\t]\n");
/* 286:    */         
/* 287:187 */         out.write("}");
/* 288:189 */         if (out != null) {
/* 289:190 */           out.close();
/* 290:    */         }
/* 291:    */       }
/* 292:    */       catch (IOException e1)
/* 293:    */       {
/* 294:194 */         e1.printStackTrace();
/* 295:    */       }
/* 296:    */     }
/* 297:    */   }
/* 298:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Exporter.ExperimentExportProcessor
 * JD-Core Version:    0.7.0.1
 */