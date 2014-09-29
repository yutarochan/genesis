/*   1:    */ package matthewFay.Depricated;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import ati.ParallelJPanel;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Connections.NetWireException;
/*  10:    */ import connections.Ports;
/*  11:    */ import connections.WiredBox;
/*  12:    */ import genesis.FileSourceReader;
/*  13:    */ import genesis.GenesisControls;
/*  14:    */ import java.awt.event.ActionEvent;
/*  15:    */ import java.awt.event.ActionListener;
/*  16:    */ import java.io.PrintStream;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import javax.swing.JButton;
/*  19:    */ import javax.swing.JComboBox;
/*  20:    */ import javax.swing.JPanel;
/*  21:    */ import javax.swing.JRadioButton;
/*  22:    */ import javax.swing.JTabbedPane;
/*  23:    */ import javax.swing.JTextField;
/*  24:    */ import utils.Mark;
/*  25:    */ 
/*  26:    */ @Deprecated
/*  27:    */ public class PersonaProcessor
/*  28:    */   extends AbstractWiredBox
/*  29:    */   implements ActionListener
/*  30:    */ {
/*  31:    */   public static final String RULE_PORT = "rule port 1";
/*  32:    */   public static final String REFLECTION_PORT = "reflection port 1";
/*  33:    */   public static final String RULE_PORT2 = "rule port 2";
/*  34:    */   public static final String REFLECTION_PORT2 = "reflection port 2";
/*  35:    */   public static final String IDIOM = "start parser";
/*  36:    */   public static final String STAGE_DIRECTION = "stage direction 1";
/*  37:    */   private HashMap<String, Persona> personas;
/*  38:    */   private Persona currentPersona;
/*  39:    */   
/*  40:    */   private static enum State
/*  41:    */   {
/*  42: 38 */     building,  loading,  idle,  versioning;
/*  43:    */   }
/*  44:    */   
/*  45: 39 */   private State state = State.idle;
/*  46: 40 */   private String personaToLoad = "";
/*  47:    */   public JButton listPersonasButton;
/*  48:    */   public JComboBox<String> personasList;
/*  49:    */   public JTextField personaNameField;
/*  50:    */   public JButton addPersonaButton;
/*  51:    */   public JButton deletePersonaButton;
/*  52:    */   public JButton loadPersonaButton1;
/*  53:    */   public JButton loadPersonaButton2;
/*  54:    */   public JButton readMacbethButton;
/*  55:    */   public JButton readHamletButton;
/*  56:    */   public JButton readEstoniaButton;
/*  57:    */   public JButton connectToPersonaButton;
/*  58:    */   public JButton savePersonasButton;
/*  59: 55 */   private static ParallelJPanel personaControls = null;
/*  60:    */   
/*  61:    */   public JPanel getPersonaControls()
/*  62:    */   {
/*  63: 58 */     if (personaControls == null)
/*  64:    */     {
/*  65: 59 */       personaControls = new ParallelJPanel();
/*  66:    */       
/*  67: 61 */       this.connectToPersonaButton = new JButton("Connect To Persona Server");
/*  68: 62 */       this.savePersonasButton = new JButton("Save all Personas");
/*  69:    */       
/*  70: 64 */       this.listPersonasButton = new JButton("Get Personas List");
/*  71: 65 */       this.personasList = new JComboBox();
/*  72: 66 */       this.personasList.setPrototypeDisplayValue("This is a long persona name");
/*  73:    */       
/*  74: 68 */       this.loadPersonaButton1 = new JButton("Load Persona");
/*  75:    */       
/*  76: 70 */       this.deletePersonaButton = new JButton("Delete Persona");
/*  77:    */       
/*  78: 72 */       personaControls.addLeft(this.connectToPersonaButton);
/*  79: 73 */       personaControls.addLeft(this.savePersonasButton);
/*  80: 74 */       personaControls.addLeft(this.personasList);
/*  81: 75 */       personaControls.addLeft(this.listPersonasButton);
/*  82: 76 */       personaControls.addLeft(this.loadPersonaButton1);
/*  83: 77 */       personaControls.addLeft(this.deletePersonaButton);
/*  84:    */       
/*  85: 79 */       this.personaNameField = new JTextField("persona name", 20);
/*  86: 80 */       this.addPersonaButton = new JButton("Add Persona");
/*  87:    */       
/*  88: 82 */       personaControls.addCenter(this.personaNameField);
/*  89: 83 */       personaControls.addCenter(this.addPersonaButton);
/*  90:    */       
/*  91: 85 */       this.readMacbethButton = new JButton("Read Macbeth");
/*  92: 86 */       this.readHamletButton = new JButton("Read Hamlet");
/*  93: 87 */       this.readEstoniaButton = new JButton("Read Estonia");
/*  94:    */       
/*  95: 89 */       personaControls.addRight(this.readMacbethButton);
/*  96:    */     }
/*  97: 91 */     return personaControls;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public PersonaProcessor()
/* 101:    */   {
/* 102: 95 */     setName("Persona Processor");
/* 103: 96 */     Connections.getPorts(this).addSignalProcessor("reflection port 1", "processReflections");
/* 104: 97 */     Connections.getPorts(this).addSignalProcessor("rule port 1", "processRules");
/* 105: 98 */     Connections.getPorts(this).addSignalProcessor("start parser", "processIdiom");
/* 106: 99 */     Connections.getPorts(this).addSignalProcessor("stage direction 1", "processStageDirection");
/* 107:    */     
/* 108:101 */     this.personas = new HashMap();
/* 109:102 */     this.currentPersona = new Persona();
/* 110:    */     
/* 111:104 */     this.state = State.building;
/* 112:    */     
/* 113:106 */     LocalGenesis.localGenesis().getControls().addTab("Persona", getPersonaControls());
/* 114:    */     
/* 115:108 */     this.connectToPersonaButton.setActionCommand("connect to persona");
/* 116:109 */     this.connectToPersonaButton.addActionListener(this);
/* 117:    */     
/* 118:111 */     this.savePersonasButton.setActionCommand("save all personas");
/* 119:112 */     this.savePersonasButton.addActionListener(this);
/* 120:    */     
/* 121:114 */     this.addPersonaButton.setActionCommand("store persona");
/* 122:115 */     this.addPersonaButton.addActionListener(this);
/* 123:    */     
/* 124:117 */     this.listPersonasButton.setActionCommand("get persona list");
/* 125:118 */     this.listPersonasButton.addActionListener(this);
/* 126:    */     
/* 127:120 */     this.loadPersonaButton1.setActionCommand("load persona");
/* 128:121 */     this.loadPersonaButton1.addActionListener(this);
/* 129:    */     
/* 130:123 */     this.deletePersonaButton.setActionCommand("delete persona");
/* 131:124 */     this.deletePersonaButton.addActionListener(this);
/* 132:    */     
/* 133:126 */     this.readMacbethButton.setActionCommand("read macbeth");
/* 134:127 */     this.readMacbethButton.addActionListener(this);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void addPersona(String name, Persona persona)
/* 138:    */   {
/* 139:132 */     this.personas.put(name, persona);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void processStageDirection(Object signal)
/* 143:    */   {
/* 144:136 */     if (signal == "reset")
/* 145:    */     {
/* 146:137 */       this.currentPersona = new Persona();
/* 147:138 */       this.state = State.building;
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void processIdiom(Object signal)
/* 152:    */   {
/* 153:145 */     String personaLongName = null;
/* 154:146 */     if ((signal instanceof String))
/* 155:    */     {
/* 156:147 */       personaLongName = (String)signal;
/* 157:148 */       personaLongName = personaLongName.substring(1, personaLongName.length() - 1);
/* 158:    */     }
/* 159:150 */     if (personaLongName != null)
/* 160:    */     {
/* 161:151 */       String[] splitName = personaLongName.split("\\\\");
/* 162:152 */       if (splitName.length == 2)
/* 163:    */       {
/* 164:153 */         String personaLocation = splitName[0];
/* 165:154 */         String personaName = splitName[1];
/* 166:155 */         Mark.say(new Object[] {"Location: " + personaLocation });
/* 167:156 */         Mark.say(new Object[] {"Name: " + personaName });
/* 168:157 */         if (personaLocation.equals("Server"))
/* 169:    */         {
/* 170:158 */           Mark.say(new Object[] {"Sending Request..." });
/* 171:159 */           this.personaToLoad = personaName;
/* 172:160 */           this.state = State.loading;
/* 173:161 */           Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "get", this.personaToLoad }));
/* 174:    */         }
/* 175:162 */         else if (personaLocation.equals("Local"))
/* 176:    */         {
/* 177:163 */           if (this.personas.containsKey(personaName)) {
/* 178:164 */             loadPersona((Persona)this.personas.get(personaName));
/* 179:    */           } else {
/* 180:166 */             Mark.say(new Object[] {"Persona not found in local pool, falling back to server" });
/* 181:    */           }
/* 182:    */         }
/* 183:    */       }
/* 184:    */       else
/* 185:    */       {
/* 186:171 */         this.personaToLoad = personaLongName;
/* 187:172 */         if (!this.personas.containsKey(personaLongName))
/* 188:    */         {
/* 189:173 */           this.state = State.loading;
/* 190:174 */           Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "get", this.personaToLoad }));
/* 191:    */         }
/* 192:    */         else
/* 193:    */         {
/* 194:176 */           this.state = State.versioning;
/* 195:177 */           Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "version", personaLongName }));
/* 196:    */         }
/* 197:    */       }
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void processReflections(Object signal)
/* 202:    */   {
/* 203:184 */     if ((signal instanceof Sequence))
/* 204:    */     {
/* 205:185 */       Sequence reflections = (Sequence)signal;
/* 206:186 */       if (this.state == State.building) {
/* 207:187 */         this.currentPersona.addReflection((Sequence)reflections.getElement(reflections.getNumberOfChildren() - 1));
/* 208:    */       }
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void processRules(Object signal)
/* 213:    */   {
/* 214:193 */     if ((signal instanceof Sequence))
/* 215:    */     {
/* 216:194 */       Sequence rules = (Sequence)signal;
/* 217:195 */       if (this.state == State.building) {
/* 218:196 */         this.currentPersona.addRule((Relation)rules.getElement(rules.getNumberOfChildren() - 1));
/* 219:    */       }
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void actionPerformed(ActionEvent e)
/* 224:    */   {
/* 225:203 */     if (e.getActionCommand().equals("connect to persona"))
/* 226:    */     {
/* 227:    */       try
/* 228:    */       {
/* 229:205 */         System.out.println("Connecting to Persona");
/* 230:    */         
/* 231:207 */         WiredBox pub = Connections.subscribe("persona", 3.0D);
/* 232:    */         
/* 233:209 */         Connections.getPorts(this).addSignalProcessor("processPersona");
/* 234:    */         
/* 235:211 */         Connections.wire(pub, this);
/* 236:212 */         Connections.wire(this, pub);
/* 237:213 */         System.out.println("Connected to Persona");
/* 238:    */       }
/* 239:    */       catch (Connections.NetWireException e1)
/* 240:    */       {
/* 241:215 */         e1.printStackTrace();
/* 242:    */       }
/* 243:    */     }
/* 244:217 */     else if (e.getActionCommand().equals("save all personas"))
/* 245:    */     {
/* 246:218 */       Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "save" }));
/* 247:    */     }
/* 248:219 */     else if (e.getActionCommand().equals("store persona"))
/* 249:    */     {
/* 250:220 */       Mark.say(new Object[] {"Sending Persona!" });
/* 251:221 */       this.currentPersona.setName(this.personaNameField.getText());
/* 252:222 */       Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "add", this.currentPersona }));
/* 253:    */     }
/* 254:223 */     else if (e.getActionCommand().equals("get persona list"))
/* 255:    */     {
/* 256:225 */       this.personasList.removeAllItems();
/* 257:226 */       Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "list" }));
/* 258:    */     }
/* 259:227 */     else if (e.getActionCommand().equals("delete persona"))
/* 260:    */     {
/* 261:229 */       Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "delete", this.personasList.getSelectedItem().toString() }));
/* 262:230 */       this.personasList.removeAllItems();
/* 263:231 */       Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "list" }));
/* 264:    */     }
/* 265:232 */     else if (e.getActionCommand().equals("load persona"))
/* 266:    */     {
/* 267:233 */       if (this.personasList.getItemCount() > 0)
/* 268:    */       {
/* 269:234 */         this.personaToLoad = this.personasList.getSelectedItem().toString();
/* 270:235 */         this.state = State.loading;
/* 271:236 */         Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "get", this.personaToLoad }));
/* 272:    */       }
/* 273:    */     }
/* 274:238 */     else if (e.getActionCommand().equals("read macbeth"))
/* 275:    */     {
/* 276:239 */       LocalGenesis.localGenesis().getFileSourceReader().readStory("personaReadMacbeth.txt");
/* 277:    */     }
/* 278:240 */     else if (e.getActionCommand().equals("read estonia"))
/* 279:    */     {
/* 280:241 */       LocalGenesis.localGenesis().getFileSourceReader().readStory("personaReadEstonia.txt");
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void processPersonaSignal(Object sig)
/* 285:    */   {
/* 286:246 */     BetterSignal signal = BetterSignal.isSignal(sig);
/* 287:247 */     if (signal == null) {
/* 288:248 */       return;
/* 289:    */     }
/* 290:249 */     if (((String)signal.get(0, String.class)).equals("list")) {
/* 291:250 */       this.personasList.addItem((String)signal.get(1, String.class));
/* 292:    */     }
/* 293:252 */     if (((String)signal.get(0, String.class)).equals("version"))
/* 294:    */     {
/* 295:253 */       String name = (String)signal.get(1, String.class);
/* 296:254 */       if ((name.equals(this.personaToLoad)) && (this.state == State.versioning))
/* 297:    */       {
/* 298:255 */         int vserver = ((Integer)signal.get(2, Integer.class)).intValue();
/* 299:256 */         int vlocal = ((Persona)this.personas.get(this.personaToLoad)).getVersion();
/* 300:257 */         if (vlocal >= vserver)
/* 301:    */         {
/* 302:258 */           loadPersona((Persona)this.personas.get(this.personaToLoad));
/* 303:    */         }
/* 304:    */         else
/* 305:    */         {
/* 306:260 */           this.state = State.loading;
/* 307:261 */           Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "get", this.personaToLoad }));
/* 308:    */         }
/* 309:    */       }
/* 310:    */     }
/* 311:265 */     if (((String)signal.get(0, String.class)).equals("persona"))
/* 312:    */     {
/* 313:266 */       Persona recievedPersona = (Persona)signal.get(1, Persona.class);
/* 314:267 */       if (!this.personas.containsKey(recievedPersona.getName()))
/* 315:    */       {
/* 316:268 */         this.personas.put(recievedPersona.getName(), recievedPersona);
/* 317:    */       }
/* 318:    */       else
/* 319:    */       {
/* 320:270 */         Persona myPersona = (Persona)this.personas.get(recievedPersona.getName());
/* 321:271 */         if (myPersona.getVersion() < recievedPersona.getVersion()) {
/* 322:272 */           this.personas.put(recievedPersona.getName(), recievedPersona);
/* 323:    */         }
/* 324:    */       }
/* 325:275 */       if ((recievedPersona.getName().equals(this.personaToLoad)) && (this.state == State.loading)) {
/* 326:276 */         loadPersona(recievedPersona);
/* 327:    */       }
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void loadPersona(Persona persona)
/* 332:    */   {
/* 333:282 */     this.currentPersona = persona;
/* 334:    */     
/* 335:    */ 
/* 336:285 */     Sequence rulesToLoad = this.currentPersona.getRules();
/* 337:286 */     Sequence reflectionsToLoad = this.currentPersona.getReflections();
/* 338:    */     
/* 339:288 */     System.out.println("Sending Rules: " + rulesToLoad.asString());
/* 340:    */     
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:294 */     LocalGenesis.localGenesis().getFileSourceReader().readStory("personaLoadCommonSense.txt");
/* 346:    */     
/* 347:296 */     LocalGenesis.localGenesis();
/* 348:297 */     LocalGenesis.localGenesis();
/* 349:298 */     if ((GenesisControls.leftButton.isSelected()) || (GenesisControls.bothButton.isSelected()))
/* 350:    */     {
/* 351:299 */       Connections.getPorts(this).transmit("rule port 1", rulesToLoad);
/* 352:300 */       Connections.getPorts(this).transmit("reflection port 1", reflectionsToLoad);
/* 353:    */     }
/* 354:302 */     LocalGenesis.localGenesis();
/* 355:303 */     LocalGenesis.localGenesis();
/* 356:304 */     if ((GenesisControls.rightButton.isSelected()) || (GenesisControls.bothButton.isSelected()))
/* 357:    */     {
/* 358:305 */       Connections.getPorts(this).transmit("rule port 2", rulesToLoad);
/* 359:306 */       Connections.getPorts(this).transmit("reflection port 2", reflectionsToLoad);
/* 360:    */     }
/* 361:309 */     this.personaToLoad = "";
/* 362:310 */     this.state = State.idle;
/* 363:    */   }
/* 364:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.PersonaProcessor
 * JD-Core Version:    0.7.0.1
 */