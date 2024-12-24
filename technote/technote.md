# Technical Note

## トラブル突破方法

### ClassFormatError

```txt
ClassFormatError accessible:
module java.base does not "opens java.lang" to unnamed module
```

Java 16 or 17 あたりからモジュール (Java の新機能) 間のアクセス制限が
厳しくなったらしい。
リフレクション等で怪しいことをしているプログラムが被害にあっている。
外部からの文字列データとして内部 API のクラス名 (com.sun... みたいな) を渡して
リフレクション経由で悪さをするセキュリティアタックへの対応措置と思われる。

詳しいことは後で調べることとして、とりあえず JVM option に以下を渡せば突破できる。

```txt
--add-opens=java.base/java.lang=ALL-UNNAMED
```

### VerifyError

バイトコードの書き換えが不完全だとクラスをロードしたあたりで JVM から発生。
おかしなメソッド呼び出しを含むクラス + メソッド名が表示されるが、
その中のどの呼び出しが悪いのかは分からない。

```txt
java.lang.Error
    java.lang.LinkageError
        java.lang.VerifyError
```

問題を起こしているメソッドの逆アセンブルを気合いで全部読めば理論上解決できる。

例えば、java.awt.Component.repaint() を invokevirtual していたりするので
スーパークラスを差し替えるならばここが解決できるようにしなければならない。

```java
public class MasaoConstruction extends java.applet.Applet implements java.lang.Runnable
...
public void run();
  descriptor: ()V
  flags: (0x0001) ACC_PUBLIC
  Code:
    stack=3, locals=2, args_size=1
        0: goto          3
        ...
        77: invokevirtual #26                 // Method java/awt/Component.repaint:()V
```

## javap

JDK についてくる標準ツール javap で逆アセンブルが可能。
-v をつけると色々出てくる。逆アセンブルも含まれる。
このディレクトリ内のテキストファイルを参照。

```sh
for file in *.class; do javap -v $file > ${file}.txt; done
```

-v をつけないと概要が得られる。以下にまとめる。

## MasaoConstruction.class

```java
Compiled from "MasaoConstruction.java"
public class MasaoConstruction extends java.applet.Applet implements java.lang.Runnable {
  java.lang.Thread th;
  int th_interval;
  int th_jm;
  GameGraphics gg;
  GameMouse gm;
  GameKey gk;
  MainProgram mp;
  public MasaoConstruction();
  public void init();
  public void start();
  public void stop();
  public void destroy();
  public void paint(java.awt.Graphics);
  public void update(java.awt.Graphics);
  public void run();
  public void init_j();
}
```

## CharacterObject.class

```java
Compiled from "MasaoConstruction.java"
class CharacterObject {
  int c;
  int x;
  int y;
  int vx;
  int vy;
  int wx;
  int wy;
  int c1;
  int c2;
  int c3;
  int c4;
  int c5;
  int pt;
  int pth;
  int muki;
  int ac;
  int level;
  boolean jimen_f;
  int score;
  int ss;
  boolean gf;
  CharacterObject();
  void init();
}
```

## GameGraphics.class

```java
Compiled from "MasaoConstruction.java"
class GameGraphics {
  final int spt_kazu_x;
  final int spt_kazu_y;
  int spt_kazu;
  final int spt_h_kijyun;
  java.applet.Applet ap;
  java.awt.Dimension di;
  java.awt.MediaTracker mt;
  java.awt.Color backcolor;
  java.awt.Image os_img;
  java.awt.Graphics os_g;
  java.awt.Image os2_img;
  java.awt.Graphics os2_g;
  java.awt.Image apt_img;
  java.awt.image.PixelGrabber pg;
  java.awt.Image[] li;
  java.awt.Image[][] spt_img;
  int[][][] spt_pa;
  java.awt.Image[] hi;
  GameGraphics(java.applet.Applet);
  void addListImage(int, java.lang.String);
  void loadImage();
  void copyOS(java.awt.Graphics);
  void fill();
  void fill2();
  void setBackcolor(java.awt.Color);
  void drawPT(int, int, int, int);
  void drawPT2(int, int, int);
  void drawBG2(int, int, int);
  void drawBG3(int, int, int, java.awt.Color);
  void drawListImage(int, int, int);
}
```

## GameKey.class

```java
Compiled from "MasaoConstruction.java"
class GameKey extends java.awt.event.KeyAdapter {
  int key_code;
  char key_char;
  boolean up_f;
  boolean down_f;
  boolean left_f;
  boolean right_f;
  boolean tr1_f;
  boolean tr2_f;
  boolean tr3_f;
  boolean start_f;
  boolean x_f;
  int left_c;
  int right_c;
  int up_c;
  int down_c;
  public GameKey();
  void init();
  public void keyPressed(java.awt.event.KeyEvent);
  public void keyReleased(java.awt.event.KeyEvent);
  int getKeyCode();
  char getKeyChar();
}
```

## GameMouse.class

```java
Compiled from "MasaoConstruction.java"
class GameMouse extends java.awt.event.MouseAdapter {
  boolean button_f;
  int click_x;
  int click_y;
  public GameMouse();
  void init();
  public void mousePressed(java.awt.event.MouseEvent);
  public void mouseReleased(java.awt.event.MouseEvent);
}
```

## IdouGamen.class

```java
Compiled from "MasaoConstruction.java"
class IdouGamen {
  GameKey gk;
  GameGraphics gg;
  KeyboardMenu km;
  MainProgram mp;
  CharacterObject co_j;
  java.lang.String[] map_string;
  int[][] map_bg;
  int[] stage_c;
  int[] stage_x;
  int[] stage_y;
  boolean[] stage_cf;
  int stage_kcID;
  int door_koID;
  int dokan_khID;
  int door_score_open;
  int[] ie_c;
  int[] ie_x;
  int[] ie_y;
  final int zure_x;
  final int zure_y;
  int mp_mode;
  int shop_kattaitem;
  int cc_hankei;
  int cc_kakudo;
  int[] cc_p1_x;
  int[] cc_p1_y;
  int[] cc_p2_x;
  int[] cc_p2_y;
  IdouGamen(GameGraphics, GameKey, KeyboardMenu, MainProgram);
  void worldInit();
  void worldInit2();
  void worldInit3();
  void drawOs2();
  void drawMap();
  int getBGZ(int, int);
  int checkStage();
  void mainProgram();
  void jMove();
  void circleCLS(int);
  void squareCLS(int, int);
}
```

## KeyboardMenu.class

```java
Compiled from "MasaoConstruction.java"
class KeyboardMenu {
  GameGraphics gg;
  GameKey gk;
  java.awt.Image[] hi;
  java.awt.Image[][] hih;
  java.awt.Graphics hg;
  java.applet.Applet ap;
  int[] c;
  int[] x;
  int[] y;
  int[] width;
  int[] selectedIndex;
  int[] item_kazu;
  java.lang.String[] message;
  java.lang.String[][] item;
  int[][] item_int;
  java.awt.Color[] item_color;
  int[] list_IDlist;
  int list_kazu;
  int list_s;
  int aw;
  int mode;
  int kettei_c;
  int cancel_c;
  int c_fc;
  java.lang.String name_crys;
  KeyboardMenu(GameGraphics, GameKey, java.lang.String);
  void initAll();
  void init1(int);
  void setMessage(int, java.lang.String);
  void addItem(int, java.lang.String);
  void addIntitem(int, int);
  void addItem2(int, java.lang.String, int);
  void active(int, int, int);
  void active(int, int, int, int);
  void activeSerifutuki(int, int, int, int, java.lang.String);
  void activeKaimono(int, int, int, int);
  void activeIchigyou(int, int, int, int);
  void activeNigyou(int, int, int, int, java.awt.Color);
  void activeYongyou(int, int, int, int);
  void activeYongyou2(int, int, int, int);
  void activeSerifu(int, int, int, int, java.awt.Color);
  void activeYasumu(int, int, int);
  void onKao(int, int, int, int, int);
  void onMituketa(int, int, int);
  void onOkozukai(int, int, int, int, int);
  void activeIchigyouTime(int, int, int, int);
  void activeNigyouTime(int, int, int, int, java.awt.Color);
  void activeJibun(int, int, int, int, int, int, int, int);
  void activeToujou(int, int, int, int, int, java.lang.String);
  void initCS();
  void activeCS();
  void off(int);
  void offActivewindow(int, int);
  void move();
  void drawMenus();
  void drawWindowbox(int, int, int, int);
}
```

## MainProgram.class

```java
Compiled from "MasaoConstruction.java"
class MainProgram {
  GameGraphics gg;
  GameMouse gm;
  GameKey gk;
  MapSystem maps;
  KeyboardMenu km;
  IdouGamen ig;
  java.util.Random ran;
  int ml_mode;
  int ml_mode_c;
  int score;
  int highscore;
  int score_1up_1;
  int score_1up_2;
  int score_1up_1_para;
  int score_1up_2_para;
  boolean score_v;
  int stage;
  int stage_cc;
  int stage_max;
  int stage_kaishi;
  int g_c1;
  int g_c2;
  int g_c3;
  int g_ac;
  int g_ac2;
  int tr1_c;
  int tr2_c;
  int left_dcc;
  int right_dcc;
  java.awt.Color gamecolor_back;
  java.awt.Color gamecolor_back_s;
  java.awt.Color gamecolor_back_t;
  java.awt.Color gamecolor_back_f;
  java.awt.Color gamecolor_score;
  java.awt.Color gamecolor_grenade1;
  java.awt.Color gamecolor_grenade2;
  java.awt.Color gamecolor_firebar1;
  java.awt.Color gamecolor_firebar2;
  java.awt.Color gamecolor_mizunohadou;
  java.awt.Color gamecolor_kaishi;
  CharacterObject co_j;
  CharacterObject[] co_t;
  CharacterObject[] co_m;
  CharacterObject[] co_a;
  CharacterObject[] co_h;
  CharacterObject[] co_jm;
  CharacterObject co_b;
  int[][] vo_x;
  int[][] vo_y;
  java.awt.Image[] hi;
  java.awt.Image[][] hih;
  java.awt.Graphics hg;
  java.applet.Applet ap;
  int ochiru_y;
  boolean j_hashiru_f;
  int j_jump_level;
  int j_jump_type;
  boolean j_zan_f;
  int[] j_zan_x;
  int[] j_zan_y;
  int[] j_zan_pt;
  int[] j_zan_pth;
  int j_zan_p;
  int j_zan_nagasa;
  int j_zan_c;
  int j_a_id;
  boolean j_mizu_f;
  int j_mizu_ac;
  int j_mizu_awa_c;
  int j_left;
  int j_left_shoki;
  boolean j_jdai_f;
  int time;
  int time_max;
  int t_kazu;
  int m_kazu;
  int jm_kazu;
  int a_kazu;
  boolean a_hf;
  boolean j_fire_f;
  int j_v_c;
  int j_v_kakudo;
  int j_jet_c;
  boolean j_jet_kf;
  int j_jet_fuel;
  boolean j_helm_f;
  boolean j_drell_f;
  boolean j_tail_f;
  int j_tail_ac;
  int j_gr_kazu;
  int[] vo_pa_x;
  int[] vo_pa_y;
  int sl_step;
  int sl_wx;
  int sl_wy;
  int sl_speed;
  int ks_wx;
  int ks_wy;
  java.lang.String moji_score;
  java.lang.String moji_highscore;
  java.lang.String moji_time;
  java.lang.String moji_jet;
  java.lang.String moji_grenade;
  java.lang.String moji_left;
  int moji_size;
  boolean[] stage_1up_f;
  boolean j_tail_hf;
  int j_tail_type;
  int grenade_type;
  int suberuyuka_hkf;
  boolean j_fire_mkf;
  boolean dengeki_mkf;
  boolean yachamo_cf;
  boolean airms_kf;
  int ugokuyuka1_type;
  int ugokuyuka2_type;
  int ugokuyuka3_type;
  int boss_type;
  int boss2_type;
  int boss3_type;
  int dokan_mode;
  int stage_select;
  java.lang.String mes1_name;
  java.lang.String mes2_name;
  java.lang.String shop_name;
  java.lang.String setumei_name;
  java.lang.String[] shop_item_name;
  int[] shop_item_teika;
  int door_score;
  int hitokoto_c;
  int hitokoto_num;
  int boss_kijyun_y;
  MainProgram(GameGraphics, GameMouse, GameKey);
  void start();
  int paraInt(java.lang.String);
  void moveGameCounter();
  void ranInit();
  int ranInt(int);
  void drawScore();
  void drawScore2();
  void addScore(int);
  void addSerifu(int, int, int);
  void mL100();
  void mainLoop();
  void init1();
  void init2();
  void init3();
  void mapsMakeStageData(int);
  void drawGamescreen();
  void jM100();
  void jMove();
  void jShinu(int);
  void jZutuki(int, int, int);
  void tSet(int, int, int, int);
  void tSetBoss(int, int, int, int);
  void tMove();
  int sakamichiY(int, int);
  void mSet(int, int, int);
  void mSet2(int, int, int, int, int);
  void mMove();
  void jmSet(int, int, int);
  void jmMove();
  void aSet(int, int, int, int);
  void aMove();
  void bMove();
  void hSet(int, int, int);
  void hAttack(int, int);
}
```

## MapSystem.class

```java
Compiled from "MasaoConstruction.java"
class MapSystem {
  GameGraphics gg;
  MainProgram mp;
  int width;
  int height;
  short[][] map_bg;
  java.lang.String[] map_string;
  int wx;
  int wy;
  int wx_mini;
  int wy_mini;
  int wx_max;
  int wy_max;
  int os2_wx;
  int os2_wy;
  java.lang.String bg_space;
  java.awt.Image[] hi;
  java.awt.Graphics g2;
  java.applet.Applet ap;
  MapSystem(int, int, GameGraphics, MainProgram);
  void init();
  void setBank(int);
  void drawMap(int, int);
  void drawMapScroll(int);
  int getBGCode(int, int);
  void putBGCode(int, int, int);
}
```
