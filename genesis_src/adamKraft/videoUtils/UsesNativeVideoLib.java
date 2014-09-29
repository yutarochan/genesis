/*   1:    */ package adamKraft.videoUtils;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.net.URL;
/*   6:    */ import utils.Mark;
/*   7:    */ 
/*   8:    */ public class UsesNativeVideoLib
/*   9:    */ {
/*  10:    */   public UsesNativeVideoLib()
/*  11:    */   {
/*  12: 14 */     lazyLoadNativeLib();
/*  13:    */   }
/*  14:    */   
/*  15: 17 */   private static boolean loaded = false;
/*  16:    */   
/*  17:    */   protected static synchronized void lazyLoadNativeLib()
/*  18:    */   {
/*  19: 20 */     if (!loaded)
/*  20:    */     {
/*  21: 21 */       loaded = true;
/*  22: 22 */       String LINUX_64_LIB_NAME = UsesNativeVideoLib.class.getResource("libmovieLinux64.so").getFile();
/*  23: 23 */       String LOSEDOWS_32_LIB_NAME = "movielib";
/*  24: 24 */       String LOSEDOWS_64_LIB_NAME = "movielib64";
/*  25: 25 */       Mark.say(new Object[] {"System properties:", System.getProperty("os.name"), System.getProperty("os.arch") });
/*  26: 26 */       if (System.getProperty("os.name").toLowerCase().contains("windows"))
/*  27:    */       {
/*  28: 27 */         if (System.getProperty("os.arch").toLowerCase().contains("x86"))
/*  29:    */         {
/*  30: 46 */           Mark.say(new Object[] {"Loading 32 bit version" });
/*  31: 47 */           System.loadLibrary("movielib");
/*  32: 48 */           Mark.say(new Object[] {"Seems to have loaded 32 bit version" });
/*  33:    */         }
/*  34: 50 */         else if (System.getProperty("os.arch").toLowerCase().contains("64"))
/*  35:    */         {
/*  36: 51 */           Mark.say(new Object[] {"Loading 64 bit version" });
/*  37: 52 */           System.loadLibrary("movielib64");
/*  38:    */         }
/*  39:    */         else
/*  40:    */         {
/*  41: 55 */           throw new RuntimeException("your architecture, " + System.getProperty("os.arch") + 
/*  42: 56 */             ", is unsupported on Windows. Get Linux or compile the native libs for your architecture");
/*  43:    */         }
/*  44:    */       }
/*  45: 60 */       else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
/*  46:    */         try
/*  47:    */         {
/*  48: 62 */           String LINUX_CUSTOM = MovieReader.class.getResource("opt/libmovie.so").getFile();
/*  49: 63 */           System.out.println("loading your custom library out of opt/");
/*  50: 64 */           System.load(LINUX_CUSTOM);
/*  51:    */         }
/*  52:    */         catch (NullPointerException f)
/*  53:    */         {
/*  54: 68 */           System.err.println("You probably wanted to build the library from source on your system, because you have Linux and you can. but trying the binary anyway...");
/*  55: 69 */           if (System.getProperty("os.arch").toLowerCase().contains("amd64"))
/*  56:    */           {
/*  57: 70 */             System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + 
/*  58: 71 */               UsesNativeVideoLib.class.getResource(".").getPath());
/*  59:    */             try
/*  60:    */             {
/*  61: 74 */               System.load(LINUX_64_LIB_NAME);
/*  62:    */             }
/*  63:    */             catch (UnsatisfiedLinkError e)
/*  64:    */             {
/*  65: 78 */               System.err.println("loading the libmovie binary failed. This could mean several things:");
/*  66: 79 */               System.err
/*  67: 80 */                 .println("\t*you don't have openCV\n\t*you have a different version of opencv than what the binary was built against");
/*  68: 81 */               System.err.println("you probably should just build the binary yourself.");
/*  69: 82 */               System.err
/*  70: 83 */                 .println("Create and opt subpackage of the videoUtils package and put your binary in it. Be careful not to check it in!!!");
/*  71: 84 */               throw e;
/*  72:    */             }
/*  73:    */           }
/*  74: 88 */           throw new RuntimeException("only amd64 Linux kernels presently supported by packaged binary. build the lib on your system.");
/*  75:    */         }
/*  76:    */       } else {
/*  77: 93 */         throw new RuntimeException("unsupported OS: " + System.getProperty("os.name") + 
/*  78: 94 */           ". gee wow, super fun bonus points if you compile the videoUtils native libs for your OS+architecture(s)!!!");
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static String getResourceFileName(String resource)
/*  84:    */   {
/*  85:102 */     String genericFileName = UsesNativeVideoLib.class.getResource(resource).getPath();
/*  86:103 */     if (System.getProperty("os.name").toLowerCase().contains("windows"))
/*  87:    */     {
/*  88:105 */       if (genericFileName.startsWith("/")) {
/*  89:106 */         genericFileName = genericFileName.substring(1);
/*  90:    */       }
/*  91:108 */       genericFileName = genericFileName.replace('/', '\\').replace("%20", " ");
/*  92:    */     }
/*  93:110 */     return genericFileName;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.videoUtils.UsesNativeVideoLib
 * JD-Core Version:    0.7.0.1
 */