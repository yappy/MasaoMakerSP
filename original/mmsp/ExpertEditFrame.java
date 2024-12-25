import java.awt.*;
import java.awt.event.*;

/*
エキスパート編集ウィンドウ!
*/

public class ExpertEditFrame extends Frame implements ActionListener{
	//メニューのラベル(nullはセパレーター)
	private static final String[][] MENU_STRING={
		{"選択","すべて選択",null,"選択範囲の左を1拡張","選択範囲の右を1拡張","選択範囲の上を1拡張","選択範囲の下を1拡張",null,
			"選択範囲の左を1縮小","選択範囲の右を1縮小","選択範囲の下を1縮小","選択範囲の上を1縮小"},
		{"範囲編集","リストで選ばれているもので塗りつぶし","「なにもなし」でクリア",null,
			"コピー","切り取り","貼り付け","選択範囲にクリッピングして貼り付け"},
		{"範囲移動","左右反転","上下反転","上下左右反転",null,"右へ移動","左へ移動","上へ移動","下へ移動"},
		{"システム","画面を更新",null,"閉じる"}
	};
	//メニューのショートカット
	private static final int[][] SHORTCUTS={
		//選択
		{KeyEvent.VK_A,0,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN,0,
			KeyEvent.VK_RIGHT,KeyEvent.VK_LEFT,KeyEvent.VK_DOWN,KeyEvent.VK_UP},
		//範囲編集
		{KeyEvent.VK_F,KeyEvent.VK_DELETE,0,KeyEvent.VK_C,KeyEvent.VK_X,KeyEvent.VK_V,KeyEvent.VK_P},
		//範囲移動
		{KeyEvent.VK_H,KeyEvent.VK_V,KeyEvent.VK_B,0,KeyEvent.VK_R,KeyEvent.VK_L,KeyEvent.VK_U,KeyEvent.VK_D},
		//システム
		{KeyEvent.VK_R,0,KeyEvent.VK_X}
	};
	//SHIFTキーがいるか
	private static final boolean[][] SHIFT={
		{false,false,false,false,false,false,false,true,true,true,true},
		{false,false,false,false,false,false,false},
		{false,false,false,false,false,false,false,false},
		{false,false,false}
	};

	//本体から受け取る
	private byte[][] map;
	private Image mainimg,subimg;
	private Graphics subgra;
	private MasaoList mlist;

	//メインキャンバス
	private PreCan can=new PreCan();

	//ステータス表示
	private Label lx=new Label("0");
	private Label ly=new Label("0");
	private Label selstrx=new Label("0");
	private Label selstry=new Label("0");
	private Label selendx=new Label("0");
	private Label selendy=new Label("0");
	//選択範囲
	private int strx,stry,endx,endy;
	//クリップボード
	private byte[][] clip=new byte[0][0];

	public ExpertEditFrame(byte[][] map,Image mainimg,Image subimg,MasaoList mlist){
		super("エキスパート編集");
		this.map=map;
		this.mainimg=mainimg;
		this.subimg=subimg;
		this.mlist=mlist;
		subgra=subimg.getGraphics();
		//メニュー作成
		MenuBar mb=new MenuBar();
		for(int i=0;i<MENU_STRING.length;i++){
			Menu m=new Menu(MENU_STRING[i][0]);
			for(int j=1;j<MENU_STRING[i].length;j++){
				if(MENU_STRING[i][j]==null){
					m.addSeparator();
				}else{
					MenuItem mi=new MenuItem(MENU_STRING[i][j],new MenuShortcut(SHORTCUTS[i][j-1],SHIFT[i][j-1]));
					mi.setActionCommand(i+","+(j-1));
					mi.addActionListener(this);
					m.add(mi);
				}
			}
			mb.add(m);
		}
		setMenuBar(mb);
		//コンポーネント配置
		ScrollPane sp=new ScrollPane();
		sp.add(can);
		Panel info=new Panel();
		info.setLayout(new GridLayout(4,4));
		info.add(new Label("X座標"));
		info.add(lx);
		info.add(new Label("Y座標"));
		info.add(ly);
		info.add(new Label("選択始点-X"));
		info.add(selstrx);
		info.add(new Label("選択始点-Y"));
		info.add(selstry);
		info.add(new Label("選択終点-X"));
		info.add(selendx);
		info.add(new Label("選択終点-Y"));
		info.add(selendy);
		info.add(new Label("クリックで始点選択"));
		info.add(new Label("Shift+クリックで終点選択"));
		add(sp,BorderLayout.CENTER);
		add(info,BorderLayout.SOUTH);

		setBounds(10,10,600,500);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){setVisible(false);}});
	}

	//メニュークリック
	public void actionPerformed(ActionEvent ae){
		String src=ae.getActionCommand();
		//メニューのインデックス
		int menu_index=Integer.parseInt(src.substring(0,src.indexOf(",")));
		//メニューアイテムのインデックス
		int item_index=Integer.parseInt(src.substring(src.indexOf(",")+1));
		switch(menu_index){
		//選択
		case 0:
			switch(item_index){
			//すべて選択
			case 0:
				strx=0;
				stry=0;
				endx=179;
				endy=29;
			break;
			//左へ1拡張
			case 2:
				if(strx!=0){strx-=1;}
			break;
			//右へ1拡張
			case 3:
				if(endx!=179){endx+=1;}
			break;
			//上へ1拡張
			case 4:
				if(stry!=0){stry-=1;}
			break;
			//下へ1拡張
			case 5:
				if(endy!=29){endy+=1;}
			break;
			//右へ1縮小
			case 7:
				if(strx!=endx){strx++;}
			break;
			//左へ1縮小
			case 8:
				if(strx!=endx){endx--;}
			break;
			//上へ1縮小
			case 9:
				if(stry!=endy){endy--;}
			break;
			//下へ1縮小
			case 10:
				if(stry!=endy){stry++;}
			break;
			}
		break;
		//選択範囲編集
		case 1:
			switch(item_index){
				//塗りつぶし
			case 0:
				byte selitem=mlist.getSelectedMap();
				for(int i=strx;i<=endx;i++){
					for(int j=stry;j<=endy;j++){
						map[i][j]=selitem;
					}
				}
			break;
			//クリア
			case 1:
				for(int i=strx;i<=endx;i++){
					for(int j=stry;j<=endy;j++){
						map[i][j]=99;
					}
				}
			break;
			//コピー
			case 3:
				clip=new byte[endx-strx+1][endy-stry+1];
				for(int i=0;i<clip.length;i++){
					for(int j=0;j<clip[i].length;j++){
						clip[i][j]=map[i+strx][j+stry];
					}
				}
			break;
			//切り取り
			case 4:
				clip=new byte[endx-strx+1][endy-stry+1];
				for(int i=0;i<clip.length;i++){
					for(int j=0;j<clip[i].length;j++){
						clip[i][j]=map[i+strx][j+stry];
						map[i+strx][j+stry]=99;
					}
				}
			break;
			//貼り付け
			case 5:
				for(int i=0;i<clip.length;i++){
					for(int j=0;j<clip[i].length;j++){
						try{
							map[i+strx][j+stry]=clip[i][j];
						}catch(ArrayIndexOutOfBoundsException e){
						}
					}
				}
			break;
			//クリッピングして貼り付け
			case 6:
				for(int i=0;i<clip.length;i++){
					for(int j=0;j<clip[i].length;j++){
						try{
							//選択範囲内なら
							if(i<=endx-strx && j<=endy-stry){
								map[i+strx][j+stry]=clip[i][j];
							}
						}catch(ArrayIndexOutOfBoundsException e){
						}
					}
				}
			break;
			}
		break;
		//選択範囲移動
		case 2:
			//選択範囲のマップデータバッファ
			byte[][] buf=new byte[endx-strx+1][endy-stry+1];
			//バッファ1に選択範囲をコピー
			for(int i=0;i<buf.length;i++){
				for(int j=0;j<buf[i].length;j++){
					buf[i][j]=map[i+strx][j+stry];
				}
			}
			switch(item_index){
			//左右反転
			case 0:
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[buf.length-1-i+strx][j+stry]=buf[i][j];
					}
				}
				System.out.println("left");
			break;
			//上下反転
			case 1:
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][buf[i].length-1-j+stry]=buf[i][j];
					}
				}
			break;
			//上下左右反転
			case 2:
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[buf.length-1-i+strx][buf[i].length-1-j+stry]=buf[i][j];
					}
				}
			break;
			//右へ移動
			case 4:
				//元の場所を一度クリア
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][j+stry]=99;
					}
				}
				//右にずらして代入
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						try{
							map[i+strx+1][j+stry]=buf[i][j];
						}catch(ArrayIndexOutOfBoundsException e){}
					}
				}
				//選択範囲を右へ移動
				if(strx!=179){strx++;}
				if(endx!=179){endx++;}
			break;
			//左へ移動
			case 5:
				//元の場所を一度クリア
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][j+stry]=99;
					}
				}
				//左にずらして代入
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						try{
							map[i+strx-1][j+stry]=buf[i][j];
						}catch(ArrayIndexOutOfBoundsException e){}
					}
				}
				//選択範囲を左へ移動
				if(strx!=0){strx--;}
				if(endx!=0){endx--;}
			break;
			//上へ移動
			case 6:
				//元の場所を一度クリア
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][j+stry]=99;
					}
				}
				//上にずらして代入
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						try{
							map[i+strx][j+stry-1]=buf[i][j];
						}catch(ArrayIndexOutOfBoundsException e){}
					}
				}
				//選択範囲を上へ移動
				if(stry!=0){stry--;}
				if(endy!=0){endy--;}
			break;
			//下へ移動
			case 7:
				//元の場所を一度クリア
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						map[i+strx][j+stry]=99;
					}
				}
				//下にずらして代入
				for(int i=0;i<buf.length;i++){
					for(int j=0;j<buf[i].length;j++){
						try{
							map[i+strx][j+stry+1]=buf[i][j];
						}catch(ArrayIndexOutOfBoundsException e){}
					}
				}
				//選択範囲を下へ移動
				if(stry!=29){stry++;}
				if(endy!=29){endy++;}
			break;
			}
		break;
		//システム
		case 3:
			switch(item_index){
				//画面を更新
				case 0:
					//何もしなくていいや。
				break;
				//閉じる
				case 2:
					setVisible(false);
				break;
			}
		break;
		}
		can.repaint();
	}

	//プレビューキャンバス
	private class PreCan extends Canvas implements MouseListener,MouseMotionListener{
		public PreCan(){
			setBounds(0,0,2880,480);
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		public void paint(Graphics g){
			subgra.setColor(Color.cyan);
			subgra.fillRect(0,0,2880,480);
			//キャラを描く
			for(int i=0;i<30;i++){
				for(int j=0;j<180;j++){
					if(map[j][i]!=99){
						subgra.drawImage(mainimg,j*16,i*16,j*16+16,i*16+16,map[j][i]%10*32,map[j][i]/10*32,map[j][i]%10*32+32,map[j][i]/10*32+32,null);
					}
				}
			}
			//グリッド描画
			subgra.setColor(Color.yellow);
			for(int i=0;i<30;i++){
				subgra.drawLine(0,i*16,2879,i*16);
			}
			for(int i=0;i<180;i++){
				subgra.drawLine(i*16,0,i*16,479);
			}
			//選択範囲描画
			subgra.setColor(Color.red);
			subgra.drawRect(strx*16,stry*16,(endx-strx)*16+15,(endy-stry)*16+15);
			//選択範囲データ更新
			selstrx.setText(""+strx);
			selstry.setText(""+stry);
			selendx.setText(""+endx);
			selendy.setText(""+endy);
			//バッファから移す
			g.drawImage(subimg,0,0,null);
		}
		public void update(Graphics g){
			paint(g);
		}
		//マウスが動いたとき
		public void mouseMoved(MouseEvent me){
			lx.setText(""+me.getX()/16);
			ly.setText(""+me.getY()/16);
		}
		//クリックされたとき
		public void mouseClicked(MouseEvent me){
			//SHIFTが押されている
			if(me.isShiftDown()){
				endx=me.getX()/16;
				endy=me.getY()/16;
			//押されていない
			}else{
				strx=me.getX()/16;
				stry=me.getY()/16;
			}
			//補正
			if(strx>endx){
				endx=strx;
			}
			if(stry>endy){
				endy=stry;
			}
			repaint();
		}
		//他(スカ)
		public void mouseEntered(MouseEvent me){}
		public void mouseExited(MouseEvent me){}
		public void mousePressed(MouseEvent me){}
		public void mouseReleased(MouseEvent me){}
		public void mouseDragged(MouseEvent me){}
	}
}