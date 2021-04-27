import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import yappy.awtdialog.*;

/*まさおメーカーはSPとなって復活したのだ。*/

public class MasaoMakerSP extends Applet{
	byte[][] map=new byte[180][30];		//マップ配列
	Image img=null;						//画像
	MasaoCanvas mc;						//メインキャンバス
	MapCanvas mapcan;					//マップ表示用
	MasaoFrameSP mf;					//拡張フレーム
	SettingFrame sf;					//詳細設定フレーム
	ExpertEditFrame ef;					//特殊編集フレーム
	MasaoList list;						//リスト

	String before="";					//HTMLの最初と最後につける文字列
	String after="";

	Button top=new Button("↑");		//移動ボタン
	Button left=new Button("←");
	Button right=new Button("→");
	Button low=new Button("↓");

	Button output=new Button("出力!");		//各種ボタン
	Button input=new Button("ロード");
	Button delete=new Button("消去");
	Button menu=new Button("拡張メニュー");
	Button setting=new Button("詳細設定");
	Button preview=new Button("エキスパート編集");

//初期化
//----------------------------------------------------------------------------------------------------------
	public void init(){
		img=getImage(getDocumentBase(),getParameter("image_file"));			//イメージ読み込み
		MediaTracker mt=new MediaTracker(this);
		mt.addImage(img,0);
		try{mt.waitForAll();}catch(InterruptedException e){}
		if(img==null){
			System.err.println("MasaoMakerSPから警告:イメージファイルを読み込めません。");
			System.err.println("FileName: "+getParameter("image_file"));
			return;
		}

		BufferedReader br=null;
		try{					//リストのためのデータ読み込み
			br=new BufferedReader(
				new InputStreamReader(new URL(getDocumentBase(),getParameter("chara_data")).openStream()));
			String[] lister=new String[66];
			for(int i=0;i<lister.length;i++){
				lister[i]=br.readLine();
			}
			list=new MasaoList(lister);
		}catch(IOException e){
			System.err.println("MasaoMakerSPから警告:キャラデータファイルを読み込めません。");
			System.err.println("FileName: "+getParameter("chara_data"));
			return;
		}finally{
			try{br.close();}catch(Exception e){}
		}
		try{					//HTMLの前後のデータ読み込み
			br=new BufferedReader(
				new InputStreamReader(new URL(getDocumentBase(),getParameter("before")).openStream()));
			String data;
			while((data=br.readLine())!=null){
				before+=data+System.getProperty("line.separator");
			}
			br=new BufferedReader(
				new InputStreamReader(new URL(getDocumentBase(),getParameter("after")).openStream()));
			while((data=br.readLine())!=null){
				after+=data+System.getProperty("line.separator");
			}
		}catch(IOException e){
			System.err.println("MasaoMakerSPから警告:HTMLの前後ファイルを読み込めません。");
			System.err.println("FileName: "+getParameter("before")+"または"+getParameter("after"));
			return;
		}finally{
			try{br.close();}catch(Exception e){}
		}
		try{					//詳細設定のためのデータ読み込み
			br=new BufferedReader(
				new InputStreamReader(new URL(getDocumentBase(),getParameter("setting_data")).openStream()));
			String[] txtdata=new String[SettingFrame.SET_NUM*3];
			for(int i=0;i<SettingFrame.SET_NUM*3;i++){
				txtdata[i]=br.readLine();
			}
			sf=new SettingFrame("詳細設定",txtdata);
		}catch(IOException e){
			System.err.println("MasaoMakerSPから警告:詳細設定データファイルを読み込めません。");
			System.err.println("FileName: "+getParameter("setting_data"));
		}finally{
			try{br.close();}catch(Exception e){}
		}
		for(int i=0;i<5400;i++){			//マップ初期化
			map[i/30][i%30]=99;
		}

		ActionListener al=new ActionListener(){				//移動ボタンイベント
			public void actionPerformed(ActionEvent ae){
				if(ae.getSource()==top){
					mc.up();
				}else if(ae.getSource()==low){
					mc.down();
				}else if(ae.getSource()==left){
					mc.left();
				}else if(ae.getSource()==right){
					mc.right();
				}
				mapcan.repaint();
			}
		};
		top.addActionListener(al);
		left.addActionListener(al);
		right.addActionListener(al);
		low.addActionListener(al);

		al=new ActionListener(){								//コマンドボタンイベント
			public void actionPerformed(ActionEvent ae){
				if(ae.getSource()==output){			//出力
					mf.output(makeHTML());
				}else if(ae.getSource()==input){	//入力
					mf.input();
				}else if(ae.getSource()==delete){	//消去
					if(MasaoFrameSP.CONFIRM.show("本当にいいですか","本当にすべて消去していいですか。\n元には戻せません。\n"+"詳細設定もすべてデフォルトに戻ります。")==BlockDialog.FIRST){
						for(int i=0;i<5400;i++){
						map[i/30][i%30]=99;
					}
					sf.delete();			//詳細設定初期化
					mc.repaint();
				}
				}else if(ae.getSource()==menu){		//メニュー呼び出し
					mf.show();
				}else if(ae.getSource()==setting){	//詳細設定ウィンドウ呼び出し
					sf.show();
				}else if(ae.getSource()==preview){	//プレビューウィンドウ呼び出し
					ef.show();
				}
			}
		};
		output.addActionListener(al);
		input.addActionListener(al);
		delete.addActionListener(al);
		menu.addActionListener(al);
		setting.addActionListener(al);
		preview.addActionListener(al);

		add(mapcan=new MapCanvas());					//地図をadd

		Panel ue=new Panel();						//コンポーネント配置
		Panel btn_box=new Panel();
		btn_box.setBounds(352,0,90,90);
		btn_box.setLayout(new GridLayout(3,3));
		btn_box.add(new Label());		btn_box.add(top);			btn_box.add(new Label());
		btn_box.add(left);				btn_box.add(new Label());	btn_box.add(right);
		btn_box.add(new Label());		btn_box.add(low);			btn_box.add(new Label());
		
		ue.setLayout(null);
		ue.setBounds(0,0,500,352);
		ue.add(btn_box);
		ue.add(mc=new MasaoCanvas());
		ue.add(list);

		Panel sita=new Panel();
		sita.setLayout(new GridLayout(2,4,5,5));
		sita.setBounds(0,352,300,48);
		sita.setBackground(Color.green);
		sita.add(output);
		sita.add(input);
		sita.add(delete);
		sita.add(menu);
		sita.add(setting);
		sita.add(preview);

		setLayout(null);
		add(ue);
		add(sita);

		mf=new MasaoFrameSP("標準拡張機能",map,mc,list,sf);			//標準拡張フレーム用意
		ef=new ExpertEditFrame(map,img,createImage(2880,480),list);
		list.select(0);				//リストを選んでおく
	}

	public String makeHTML(){							//HTMLを生成,JavaScriptからも使う
		String maked="";
		for(int i1=0;i1<3;i1++){
			for(int i2=0;i2<30;i2++){
				String linedata="";				//1行
				for(int i3=0;i3<60;i3++){
					linedata=linedata+MasaoMakerSP.mapByteToString(map[60*i1+i3][i2]);
				}
				if(i2<10){
					linedata="<PARAM NAME=\"map"+i1+"-"+i2+"\"  VALUE=\""+linedata+"\">"+System.getProperty("line.separator");
				}else{
					linedata="<PARAM NAME=\"map"+i1+"-"+i2+"\" VALUE=\""+linedata+"\">"+System.getProperty("line.separator");
				}
				maked+=linedata;
			}
			maked+=System.getProperty("line.separator");
		}
		maked= before + maked + sf.toStr() + after;		//詳細設定とその他を追加

		return maked;
	}

	public String makeCompactHTML(){					//アプレットタグ部分だけのHTML
		String maked="";
		for(int i1=0;i1<3;i1++){
			for(int i2=0;i2<30;i2++){
				String linedata="";				//1行
				for(int i3=0;i3<60;i3++){
					linedata=linedata+MasaoMakerSP.mapByteToString(map[60*i1+i3][i2]);
				}
				if(i2<10){
					linedata="<PARAM NAME=\"map"+i1+"-"+i2+"\"  VALUE=\""+linedata+"\">"+System.getProperty("line.separator");
				}else{
					linedata="<PARAM NAME=\"map"+i1+"-"+i2+"\" VALUE=\""+linedata+"\">"+System.getProperty("line.separator");
				}
				maked+=linedata;
			}
			maked+=System.getProperty("line.separator");
		}
		maked=maked+sf.toStr();
		return maked;
	}

	public String makeCompactSave(){					//容量を抑えた数字の羅列,JavaScriptから使う
		String maked="";
		for(int i1=0;i1<30;i1++){
			for(int i2=0;i2<180;i2++){
				int now=map[i2][i1];
				if(now<10){
					maked=maked+"0"+now;
				}else{
					maked+=now;
				}
			}
		}
		return maked;
	}

	public String getDefaultSetting(){					//デフォルトの詳細設定,JavaScriptから使う
		return sf.getDefaultString();
	}

	public static String mapByteToString(byte map){		//intのマップデータをHTMLでのStringに変換
		char c='.';
		if(map<26){					//A～Z
			c=(char)(map+'A');
		}else if(map<52){			//a～z
			c=(char)(map-26+'a');
		}else if(map<61){			//1～9
			c=(char)(map-52+'1');
		}else if(map<65){			//+,-,*,/
			switch(map){
			case 61:
				c='+';
			break;
			case 62:
				c='-';
			break;
			case 63:
				c='*';
			break;
			case 64:
				c='/';
			break;
			}
		}else{
			c='.';
		}
		return new Character(c).toString();
	}
	public static byte charToMapByte(char c)throws DataLoadException{	//HTMLでのStringのマップデータをcharにしたものをbyteに変換
		if(c>='A' && c<='Z'){			//A～Z
			return (byte)(c-'A');		//Aとの差を返す
		}else if(c>='a' && c<='z'){		//a～z
			return (byte)(c-'a'+26);	//aとの差+26を返す
		}else if(c>='1' && c<='9'){		//1～9
			return (byte)(c-'1'+52);
		}else if(c=='+'){return 61;}
		else if(c=='-'){return 62;}
		else if(c=='*'){return 63;}
		else if(c=='/'){return 64;}
		else if(c=='.'){return 99;}
		else{
			throw new DataLoadException(new Character(c).toString(),DataLoadException.FORMAT_ERROR);
		}
	}
//-----------------------------------------------------------------------------------------------------------

	public class MasaoCanvas extends Canvas{					//内部クラス,マップ表示用キャンバス
		private int x=0;			//左上の座標
		private int y=0;
		private Image subimg;		//ダブルスクリーン
		private Graphics subg;

		public MasaoCanvas(){
			super();
			setBounds(0,0,352,352);
			subimg=MasaoMakerSP.this.createImage(352,352);
			subg=subimg.getGraphics();
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			addMouseMotionListener(new MouseMotionAdapter(){	//ドラッグ
				public void mouseDragged(MouseEvent me){
					mauseDragAndClick(me);
				}
			});
			addMouseListener(new MouseAdapter(){				//クリック
				public void mouseClicked(MouseEvent me){
					mauseDragAndClick(me);
				}
			});
		}
		public void mauseDragAndClick(MouseEvent me){			//ドラッグもクリックも一緒なのでまとめる
			if(me.getX()<32||me.getY()<32||me.getX()>352-1||me.getY()>352-1){
				return;
			}
			if(me.isShiftDown()){							//Shiftを押しながらで消す
				map[(me.getX()-32)/32+x][(me.getY()-32)/32+y]=99;
			}else if(me.isAltDown()){						//Altを押しながらで登録したのを使用
				map[(me.getX()-32)/32+x][(me.getY()-32)/32+y]=mf.getToroku();
			}else{											//標準
				map[(me.getX()-32)/32+x][(me.getY()-32)/32+y]=list.getSelectedMap();
			}
			repaint();
		}
		public void paint(Graphics g){							//ペイント
			subg.setColor(Color.red);
			subg.fillRect(0,0,352,352);		//画面をクリア
			subg.setColor(Color.black);
			for(int i=0;i<10;i++){							//座標表示
				subg.drawString(Integer.toString(x+i),32*i+32+10,20);
			}
			for(int i=0;i<10;i++){
				subg.drawString(Integer.toString(y+i),10,32*i+32+20);
			}
			for(int i=0;i<10;i++){
				for(int ii=0;ii<10;ii++){
					if(map[x+i][y+ii]==99){
						subg.setColor(Color.cyan);
						subg.fillRect(i*32+32,ii*32+32,32,32);
					}else{
						subg.drawImage(img,i*32+32,ii*32+32,i*32+64,ii*32+64,
							map[x+i][y+ii]%10*32,map[x+i][y+ii]/10*32,map[x+i][y+ii]%10*32+32,map[x+i][y+ii]/10*32+32,this);
					}
				}
			}
			subg.setColor(Color.yellow);
			for(int i=0;i<11;i++){							//グリッドで区切る
				subg.drawLine(0,i*32-1,351,i*32-1);
			}
			for(int i=0;i<11;i++){
				subg.drawLine(i*32+32,0,i*32+32,351);
			}
			g.drawImage(subimg,0,0,this);
		}
		public void update(Graphics g){
			paint(g);
		}
		public int getX(){			//x,yを返す
			return x;
		}
		public int getY(){
			return y;
		}
		public void up(){			//上へ移動
			if(y!=0){
				y-=10;
			}
			repaint();
		}
		public void down(){			//下へ移動
			if(y!=20){
				y+=10;
			}
			repaint();
		}
		public void right(){		//右へ移動
			if(x!=170){
				x+=10;
			}
			repaint();
		}
		public void left(){			//左へ移動
			if(x!=0){
				x-=10;
			}
			repaint();
		}
		public void reset(){		//(0,0)へ移動
			x=0;
			y=0;
			repaint();
		}
	}

	private class MapCanvas extends Canvas{			//現在位置を示す地図のキャンバス
		public final int WIDTH=10;
		public final int HEIGHT=10;
		public final int START_X;
		public final int START_Y;

		public MapCanvas(){
			super();
			setBounds(300,352,200,400-352);
			START_X=(200-WIDTH*18)/2;
			START_Y=(400-352-HEIGHT*3)/2;
			setBackground(Color.cyan);
		}
		public void paint(Graphics g){			//メイン
			g.setColor(Color.yellow);
			g.fillRect(START_X,START_Y,WIDTH*18,HEIGHT*3);
			g.setColor(Color.red);
			g.fillRect(START_X+mc.getX()/10*WIDTH,START_Y+mc.getY()/10*HEIGHT,WIDTH,HEIGHT);
		}
		public void update(Graphics g){
			paint(g);
		}
		public int getWidth(){
			return getBounds().width;
		}
		public int getHeight(){
			return getBounds().height;
		}
	}

}

/*
<html>
<head><title>まさおメーカーSP</title></head>
<body>

<applet code="MasaoMakerSP.class"width="500"height="400">
	<param name="image_file"value="makerpat.gif">
	<param name="before"value="Before.txt">
	<param name="after"value="After.txt">
	<param name="chara_data"value="CharaData.txt">
	<param name="setting_data"value="SettingData.txt">
</applet>

</body>
*/