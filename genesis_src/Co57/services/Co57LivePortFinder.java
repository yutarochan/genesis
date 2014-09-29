/*   1:    */ package Co57.services;
/*   2:    */ 
/*   3:    */ import Co57.infrastructure.GenericZMQConnection;
/*   4:    */ import Co57.infrastructure.GenericZMQConnection.Type;
/*   5:    */ import adam.RPCBox;
/*   6:    */ import com.google.gson.Gson;
/*   7:    */ import com.google.gson.GsonBuilder;
/*   8:    */ import com.google.gson.reflect.TypeToken;
/*   9:    */ import connections.Connections;
/*  10:    */ import connections.WiredBox;
/*  11:    */ import java.lang.reflect.Type;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import utils.Mark;
/*  16:    */ 
/*  17:    */ public class Co57LivePortFinder
/*  18:    */   implements WiredBox
/*  19:    */ {
/*  20:    */   private static final String service = "Co57LivePortFinder";
/*  21: 32 */   private GenericZMQConnection co57Connection = null;
/*  22:    */   
/*  23:    */   private Co57LivePortFinder()
/*  24:    */   {
/*  25: 34 */     Mark.say(new Object[] {"Starting Co57 Port Finder Service" });
/*  26: 35 */     this.co57Connection = new GenericZMQConnection(GenericZMQConnection.Type.REQ, 5700);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void main(String[] args)
/*  30:    */   {
/*  31: 42 */     Map<String, Integer> ports = getPorts();
/*  32: 43 */     for (String name : ports.keySet()) {
/*  33: 44 */       Mark.say(new Object[] {name + ":" + ports.get(name) });
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String findPorts(Object param_1)
/*  38:    */   {
/*  39: 63 */     Mark.say(
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46: 70 */       new Object[] { "Finding Ports..." });String json = this.co57Connection.request("{'action':'getPorts'}");Mark.say(new Object[] { "JSON Response: " + json });return json;
/*  47:    */   }
/*  48:    */   
/*  49: 72 */   private static RPCBox remotePortFinder = null;
/*  50:    */   
/*  51:    */   public static HashMap<String, Integer> getPorts()
/*  52:    */   {
/*  53: 76 */     if (remotePortFinder == null) {
/*  54:    */       try
/*  55:    */       {
/*  56: 78 */         WiredBox remoteWiredBox = Connections.subscribe("Co57LivePortFinder");
/*  57: 79 */         remotePortFinder = (RPCBox)remoteWiredBox;
/*  58:    */       }
/*  59:    */       catch (Exception e)
/*  60:    */       {
/*  61: 81 */         e.printStackTrace();
/*  62: 82 */         Mark.say(new Object[] {"Failed to Connect to Co57!" });
/*  63: 83 */         return new HashMap();
/*  64:    */       }
/*  65:    */     }
/*  66: 87 */     String ports_json = (String)remotePortFinder.rpc("findPorts", new Object[1]);
/*  67:    */     
/*  68: 89 */     Gson gson = new GsonBuilder().create();
/*  69:    */     
/*  70: 91 */     Type typeOfData = new TypeToken() {}.getType();
/*  71: 92 */     List<Map<String, String>> ports = (List)gson.fromJson(ports_json, typeOfData);
/*  72:    */     
/*  73: 94 */     HashMap<String, Integer> port_map = new HashMap();
/*  74: 95 */     for (Map<String, String> m : ports) {
/*  75: 96 */       port_map.put((String)m.get("name"), Integer.valueOf(Integer.parseInt((String)m.get("port"))));
/*  76:    */     }
/*  77: 99 */     return port_map;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getName()
/*  81:    */   {
/*  82:104 */     return "Co57LivePortFinder";
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.services.Co57LivePortFinder
 * JD-Core Version:    0.7.0.1
 */