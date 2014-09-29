/*   1:    */ package matthewFay.Depricated;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Connections.NetWireException;
/*   6:    */ import connections.Ports;
/*   7:    */ import connections.WiredBox;
/*   8:    */ import java.io.BufferedReader;
/*   9:    */ import java.io.FileInputStream;
/*  10:    */ import java.io.FileOutputStream;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.io.InputStreamReader;
/*  13:    */ import java.io.ObjectInputStream;
/*  14:    */ import java.io.ObjectOutputStream;
/*  15:    */ import java.io.PrintStream;
/*  16:    */ import java.io.Serializable;
/*  17:    */ import java.net.MalformedURLException;
/*  18:    */ import java.net.URL;
/*  19:    */ import java.util.HashMap;
/*  20:    */ 
/*  21:    */ @Deprecated
/*  22:    */ public class PersonaServer
/*  23:    */ {
/*  24: 24 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/*  25:    */   
/*  26:    */   public static class PersonaPublisher
/*  27:    */     implements WiredBox, Serializable
/*  28:    */   {
/*  29:    */     private static final long serialVersionUID = 3L;
/*  30:    */     private HashMap<String, Persona> personas;
/*  31:    */     
/*  32:    */     public PersonaPublisher()
/*  33:    */     {
/*  34: 36 */       this.personas = new HashMap();
/*  35:    */     }
/*  36:    */     
/*  37:    */     public String getName()
/*  38:    */     {
/*  39: 41 */       return "PersonaPublisher: online storage of commonsenese knowledge sets";
/*  40:    */     }
/*  41:    */     
/*  42:    */     public void save()
/*  43:    */     {
/*  44:    */       try
/*  45:    */       {
/*  46: 46 */         FileOutputStream fout = new FileOutputStream("persona.dat");
/*  47: 47 */         ObjectOutputStream oos = new ObjectOutputStream(fout);
/*  48: 48 */         oos.writeObject(this);
/*  49: 49 */         oos.close();
/*  50:    */       }
/*  51:    */       catch (Exception e)
/*  52:    */       {
/*  53: 51 */         e.printStackTrace();
/*  54:    */       }
/*  55:    */     }
/*  56:    */     
/*  57:    */     public void process(Object o)
/*  58:    */     {
/*  59: 55 */       BetterSignal signal = BetterSignal.isSignal(o);
/*  60: 56 */       if (o == null) {
/*  61: 57 */         return;
/*  62:    */       }
/*  63: 59 */       String command = (String)signal.get(0, String.class);
/*  64: 60 */       if (command.equals("list"))
/*  65:    */       {
/*  66: 61 */         System.out.println("List of personas:");
/*  67: 62 */         for (String s : this.personas.keySet())
/*  68:    */         {
/*  69: 63 */           Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "list", s }));
/*  70: 64 */           System.out.println(s);
/*  71:    */         }
/*  72:    */       }
/*  73: 67 */       if (command.equals("add"))
/*  74:    */       {
/*  75: 68 */         System.out.println("Adding persona...");
/*  76: 69 */         ((Persona)signal.get(1, Persona.class)).markVersion();
/*  77: 70 */         this.personas.put(((Persona)signal.get(1, Persona.class)).getName(), (Persona)signal.get(1, Persona.class));
/*  78:    */         
/*  79: 72 */         System.out.println("Persona Added: " + ((Persona)signal.get(1, Persona.class)).getName());
/*  80:    */       }
/*  81: 74 */       if (command.equals("get"))
/*  82:    */       {
/*  83: 75 */         System.out.println("Sending persona...");
/*  84: 76 */         String name = (String)signal.get(1, String.class);
/*  85: 77 */         if (this.personas.containsKey(name))
/*  86:    */         {
/*  87: 78 */           Persona persona = (Persona)this.personas.get(name);
/*  88: 79 */           Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "persona", persona }));
/*  89:    */         }
/*  90:    */       }
/*  91: 82 */       if (command.equals("version"))
/*  92:    */       {
/*  93: 83 */         String pv = (String)signal.get(1, String.class);
/*  94: 84 */         Integer version = Integer.valueOf(-1);
/*  95: 85 */         if (this.personas.containsKey(pv)) {
/*  96: 86 */           version = Integer.valueOf(((Persona)this.personas.get(pv)).getVersion());
/*  97:    */         }
/*  98: 88 */         Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "version", pv, version }));
/*  99:    */       }
/* 100: 90 */       if (command.equals("delete"))
/* 101:    */       {
/* 102: 91 */         String name = (String)signal.get(1, String.class);
/* 103: 92 */         System.out.println("Deleting persona... " + name);
/* 104: 93 */         this.personas.remove(name);
/* 105:    */       }
/* 106: 95 */       if (command.equals("save")) {
/* 107: 96 */         save();
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static void main(String[] args)
/* 113:    */   {
/* 114:102 */     InputStreamReader converter = new InputStreamReader(System.in);
/* 115:103 */     BufferedReader in = new BufferedReader(converter);
/* 116:104 */     URL serverURL = null;
/* 117:    */     try
/* 118:    */     {
/* 119:106 */       serverURL = new URL(wireServer);
/* 120:    */     }
/* 121:    */     catch (MalformedURLException e)
/* 122:    */     {
/* 123:108 */       e.printStackTrace();
/* 124:109 */       System.exit(1);
/* 125:    */     }
/* 126:    */     try
/* 127:    */     {
/* 128:113 */       String input = "";
/* 129:114 */       System.out.println("Starting PersonaPublisher...");
/* 130:115 */       Connections.useWireServer(serverURL);
/* 131:    */       
/* 132:117 */       PersonaPublisher pub = new PersonaPublisher();
/* 133:    */       try
/* 134:    */       {
/* 135:119 */         FileInputStream fin = new FileInputStream("persona.dat");
/* 136:120 */         ObjectInputStream ois = new ObjectInputStream(fin);
/* 137:121 */         pub = (PersonaPublisher)ois.readObject();
/* 138:122 */         ois.close();
/* 139:    */       }
/* 140:    */       catch (Exception e)
/* 141:    */       {
/* 142:124 */         e.printStackTrace();
/* 143:    */       }
/* 144:126 */       Connections.getPorts(pub).addSignalProcessor("process");
/* 145:127 */       Connections.publish(pub, "persona");
/* 146:    */       
/* 147:129 */       System.out.println("Server started, input commands");
/* 148:131 */       while (!input.toLowerCase().equals("quit"))
/* 149:    */       {
/* 150:132 */         input = in.readLine().trim().intern();
/* 151:133 */         BetterSignal b = new BetterSignal();
/* 152:134 */         String[] sigargs = input.split(" ");
/* 153:135 */         for (String s : sigargs) {
/* 154:136 */           b.add(s);
/* 155:    */         }
/* 156:138 */         pub.process(b);
/* 157:    */       }
/* 158:    */     }
/* 159:    */     catch (Connections.NetWireException e)
/* 160:    */     {
/* 161:141 */       e.printStackTrace();
/* 162:    */     }
/* 163:    */     catch (IOException e)
/* 164:    */     {
/* 165:143 */       e.printStackTrace();
/* 166:    */     }
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.PersonaServer
 * JD-Core Version:    0.7.0.1
 */