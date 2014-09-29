/*   1:    */ package obsolete.mediaFX;
/*   2:    */ 
/*   3:    */ import ChangeListener;
/*   4:    */ import Duration;
/*   5:    */ import HBox;
/*   6:    */ import Label;
/*   7:    */ import MediaPlayer;
/*   8:    */ import MediaView;
/*   9:    */ import ParallelTransition;
/*  10:    */ import Slider;
/*  11:    */ import Stage;
/*  12:    */ import ToolBar;
/*  13:    */ 
/*  14:    */ public class OverlayMediaPlayer
/*  15:    */ {
/*  16:    */   private static final String MEDIA_URL = "http://download.oracle.com/otndocs/javafx/JavaRap_ProRes_H264_768kbit_Widescreen.mp4";
/*  17:    */   private MediaPlayer mediaPlayer;
/*  18:    */   final double mediaWidth = 480.0D;
/*  19:    */   final double mediaHeight = 270.0D;
/*  20:    */   
/*  21:    */   void init(Stage paramStage)
/*  22:    */   {
/*  23: 34 */     throw new Error("Unresolved compilation problems: \n\tStage cannot be resolved to a type\n\tGroup cannot be resolved to a type\n\tGroup cannot be resolved to a type\n\tScene cannot be resolved to a type\n\tMediaPlayer cannot be resolved to a type\n\tMediaPlayer cannot be resolved to a type\n\tMedia cannot be resolved to a type\n\tMediaPlayer cannot be resolved to a type\n\tMediaPlayer cannot be resolved to a type\n\tThe method setMinSize(double, double) is undefined for the type OverlayMediaPlayer.PlayerPane\n\tThe method setPrefSize(double, double) is undefined for the type OverlayMediaPlayer.PlayerPane\n\tThe method setMaxSize(double, double) is undefined for the type OverlayMediaPlayer.PlayerPane\n");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void play()
/*  27:    */   {
/*  28: 48 */     throw new Error("Unresolved compilation problems: \n\tStatus cannot be resolved to a type\n\tMediaPlayer cannot be resolved to a type\n\tStatus cannot be resolved to a variable\n\tStatus cannot be resolved to a variable\n\tStatus cannot be resolved to a variable\n\tStatus cannot be resolved to a variable\n\tStatus cannot be resolved to a variable\n\tMediaPlayer cannot be resolved to a type\n");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void stop()
/*  32:    */   {
/*  33: 57 */     throw new Error("Unresolved compilation problems: \n\tThe method stop() of type OverlayMediaPlayer must override or implement a supertype method\n\tMediaPlayer cannot be resolved to a type\n");
/*  34:    */   }
/*  35:    */   
/*  36:    */   static class PlayerPane
/*  37:    */   {
/*  38:    */     private MediaPlayer mp;
/*  39:    */     private MediaView mediaView;
/*  40:    */     private final boolean repeat = false;
/*  41:    */     private boolean stopRequested;
/*  42:    */     private boolean atEndOfMedia;
/*  43:    */     private Duration duration;
/*  44:    */     private Slider timeSlider;
/*  45:    */     private Label playTime;
/*  46:    */     private Slider volumeSlider;
/*  47:    */     private ToolBar topBar;
/*  48:    */     private ToolBar bottomBar;
/*  49:    */     private HBox mediaTopBar;
/*  50:    */     private HBox mediaBottomBar;
/*  51:    */     private ParallelTransition transition;
/*  52:    */     final double mediaWidth = 480.0D;
/*  53:    */     final double mediaHeight = 270.0D;
/*  54:    */     private ChangeListener layoutListener;
/*  55:    */     
/*  56:    */     protected void layoutChildren()
/*  57:    */     {
/*  58: 85 */       throw new Error("Unresolved compilation problems: \n\tThe method layoutChildren() of type OverlayMediaPlayer.PlayerPane must override or implement a supertype method\n\tMediaView cannot be resolved to a type\n\tMediaView cannot be resolved to a type\n\tMediaView cannot be resolved to a type\n\tToolBar cannot be resolved to a type\n\tToolBar cannot be resolved to a type\n");
/*  59:    */     }
/*  60:    */     
/*  61:    */     protected double computeMinWidth(double paramDouble)
/*  62:    */     {
/*  63: 95 */       throw new Error("Unresolved compilation problems: \n\tThe method computeMinWidth(double) of type OverlayMediaPlayer.PlayerPane must override or implement a supertype method\n\tHBox cannot be resolved to a type\n");
/*  64:    */     }
/*  65:    */     
/*  66:    */     protected double computeMinHeight(double paramDouble)
/*  67:    */     {
/*  68: 99 */       throw new Error("Unresolved compilation problem: \n\tThe method computeMinHeight(double) of type OverlayMediaPlayer.PlayerPane must override or implement a supertype method\n");
/*  69:    */     }
/*  70:    */     
/*  71:    */     protected double computePrefWidth(double paramDouble)
/*  72:    */     {
/*  73:103 */       throw new Error("Unresolved compilation problems: \n\tThe method computePrefWidth(double) of type OverlayMediaPlayer.PlayerPane must override or implement a supertype method\n\tMediaPlayer cannot be resolved to a type\n\tHBox cannot be resolved to a type\n");
/*  74:    */     }
/*  75:    */     
/*  76:    */     protected double computePrefHeight(double paramDouble)
/*  77:    */     {
/*  78:107 */       throw new Error("Unresolved compilation problems: \n\tThe method computePrefHeight(double) of type OverlayMediaPlayer.PlayerPane must override or implement a supertype method\n\tMediaPlayer cannot be resolved to a type\n\tHBox cannot be resolved to a type\n");
/*  79:    */     }
/*  80:    */     
/*  81:    */     protected double computeMaxWidth(double paramDouble)
/*  82:    */     {
/*  83:111 */       throw new Error("Unresolved compilation problem: \n\tThe method computeMaxWidth(double) of type OverlayMediaPlayer.PlayerPane must override or implement a supertype method\n");
/*  84:    */     }
/*  85:    */     
/*  86:    */     protected double computeMaxHeight(double paramDouble)
/*  87:    */     {
/*  88:113 */       throw new Error("Unresolved compilation problem: \n\tThe method computeMaxHeight(double) of type OverlayMediaPlayer.PlayerPane must override or implement a supertype method\n");
/*  89:    */     }
/*  90:    */     
/*  91:    */     public PlayerPane(MediaPlayer paramMediaPlayer) {}
/*  92:    */     
/*  93:    */     protected void updateValues()
/*  94:    */     {
/*  95:346 */       throw new Error("Unresolved compilation problems: \n\tLabel cannot be resolved to a type\n\tDuration cannot be resolved to a type\n");
/*  96:    */     }
/*  97:    */     
/*  98:    */     static String formatTime(Duration paramDuration1, Duration paramDuration2)
/*  99:    */     {
/* 100:363 */       throw new Error("Unresolved compilation problems: \n\tDuration cannot be resolved to a type\n\tDuration cannot be resolved to a type\n\tDuration cannot be resolved to a variable\n");
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void start(Stage paramStage)
/* 105:    */     throws Exception
/* 106:    */   {
/* 107:400 */     throw new Error("Unresolved compilation problem: \n\tStage cannot be resolved to a type\n");
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static void main(String[] paramArrayOfString)
/* 111:    */   {
/* 112:405 */     throw new Error("Unresolved compilation problem: \n\tThe method launch(String[]) is undefined for the type OverlayMediaPlayer\n");
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mediaFX.OverlayMediaPlayer
 * JD-Core Version:    0.7.0.1
 */