import java.awt.*;
import java.awt.event.*;
import yappy.awtdialog.*;

/*まさおメーカー用標準拡張フレーム!*/

public class MasaoFrameSP extends Frame implements ActionListener{
	private byte[][] map;					//マップ配列への参照
	private byte[][] backup;					//マップのバックアップ
	private MasaoMakerSP.MasaoCanvas mc;	//Canvasをrepaint()するため
	private SettingFrame sf;				//消去でデフォルトに戻すため,詳細設定もロードするため
	private MasaoList list;					//全て変更などのため

	private OutputDialog od=new OutputDialog("出力");
	private InputDialog id=new InputDialog("ロード");

	private byte toroku=99;					//登録

	public static int MENU=7;				//メニューの数
	private Button[] btn=new Button[MENU];	//メニューボタン

	public static MessageDialog			ALERT;		//ダイアログ
	public static YesNoDialog 			CONFIRM;
	public static yappy.awtdialog.InputDialog INPUT;

	public MasaoFrameSP(String title,byte[][] map,MasaoMakerSP.MasaoCanvas mc,MasaoList list,SettingFrame sf){
		super(title);
		setBounds(100,100,500,400);
		setBackground(Color.gray);

		ALERT=new MessageDialog(this);
		CONFIRM=new YesNoDialog(this);
		INPUT=new yappy.awtdialog.InputDialog(this);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				hide();
			}
		});
		this.map=map;
		this.mc=mc;
		this.list=list;
		this.sf=sf;

		setLayout(new GridLayout(MENU,0,0,10));
		btn[0]=new Button("マップをすべてリストで選ばれているものに変更");
		btn[1]=new Button("マップの一番下をすべてリストで選ばれているものに変更");
		btn[2]=new Button("x軸を指定してたて一列をすべてリストで選ばれているものに変更");
		btn[3]=new Button("y軸を指定してよこ一列をすべてリストで選ばれているものに変更");
		btn[4]=new Button("バックアップを取る");
		btn[5]=new Button("バックアップから復元");
		btn[6]=new Button("リストで選ばれているものを登録(Alt+クリックで使用)");
		//btn[7]=new Button("");
		//btn[8]=new Button("");
		//btn[9]=new Button("");
		for(int i=0;i<btn.length;i++){
			add(btn[i]);
			btn[i].setActionCommand(""+i);
			btn[i].addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent ae){
		switch(Integer.parseInt(ae.getActionCommand())){
		case 0:						//すべて変更
			byte selected=list.getSelectedMap();		//選ばれているのを一度保存
			for(int i=0;i<5400;i++){
				map[i/30][i%30]=selected;
			}
			mc.repaint();
			hide();
		break;
		case 1:						//一番下を変更
			selected=list.getSelectedMap();			//選ばれているのを一度保存
			for(int i=0;i<180;i++){
				map[i][29]=selected;
			}
			mc.repaint();
			hide();
		break;
		case 2:						//x軸で一気(タテ)
			try{
				selected=list.getSelectedMap();			//選ばれているのを一度保存
				int targetx=Integer.parseInt(			//X座標入力
						INPUT.show("X座標記入","目標のX座標\n(メイン画面の赤いところの上の数字)\nを書いてください(0～179)","0"));
				for(int i=0;i<30;i++){
					map[targetx][i]=selected;
				}
				mc.repaint();
				hide();
			}catch(Exception e){		//NumberFormat,NullPointer,ArrayIndexOutOfBounds全部発生したら中止
				ALERT.show("失敗","取り消されたか、\n数値変換に失敗したか、\n数値が範囲外です。");
			}
		break;
		case 3:						//y軸で一気(ヨコ)
			try{
				selected=list.getSelectedMap();			//選ばれているのを一度保存
				int targety=Integer.parseInt(			//X座標入力
						INPUT.show("Y座標記入","目標のY座標\n(メイン画面の赤いところの左の数字)\nを書いてください(0～29)","0"));
				for(int i=0;i<180;i++){
					map[i][targety]=selected;
				}
				mc.repaint();
				hide();
			}catch(Exception e){		//NumberFormat,NullPointer,ArrayIndexOutOfBounds全部発生したら中止
				ALERT.show("失敗","取り消されたか、\n数値変換に失敗したか、\n数値が範囲外です。");
			}
		break;
		case 4:						//バックアップ
			backup=new byte[180][30];
			for(int i=0;i<5400;i++){
				backup[i/30][i%30]=map[i/30][i%30];
			}
			ALERT.show("成功","バックアップしました。");
		break;
		case 5:						//バックアップから復元
			try{
				for(int i=0;i<map.length;i++){
					System.arraycopy(backup[i],0,map[i],0,map[i].length);
				}
				mc.repaint();
				ALERT.show("成功","バックアップから復元しました。");
			}catch(NullPointerException e){
				ALERT.show("失敗","バックアップがとられていません。");
			}finally{
				hide();
			}
		break;
		case 6:						//登録
			toroku=list.getSelectedMap();
			ALERT.show("成功",list.getSelectedItem()+"を登録しました。");
		break;
		}
	}

	public byte getToroku(){
		return toroku;
	}
	public void output(String data){		//出力
		od.output(data);
	}
	public void input(){					//ロード
		id.show();
	}

	private class OutputDialog extends DefCloseDlg{		//内部クラス,出力用ダイアログ
		private TextArea ta=new TextArea();			//出力先

		public OutputDialog(String title){
			super(MasaoFrameSP.this,title,true,DefCloseDlg.HIDE);
			setBounds(100,100,600,500);
			ta.setEditable(false);
			setLayout(new BorderLayout());
			add(new Label("コピーして、保存してください。コピー:Ctrl(command)+C"),BorderLayout.NORTH);
			add(ta,BorderLayout.CENTER);
		}
		public void output(String data){							//出力!
			ta.setText(data);
			ta.selectAll();
			show();
		}
	}
	private class InputDialog extends DefCloseDlg implements ActionListener{		//内部クラス,ロード用ダイアログ
		private TextArea ta=new TextArea();

		public InputDialog(String title){
			super(MasaoFrameSP.this,title,true,DefCloseDlg.HIDE);
			setBounds(100,100,600,500);
			setLayout(new BorderLayout());
			add(new Label("出力されたものをコピーして貼り付けてください。これからロードすることができます。貼り付け:Ctrl(command)+V"),BorderLayout.NORTH);
			add(ta,BorderLayout.CENTER);
			Button b=new Button("OK!ロード開始!");
			b.setBackground(Color.red);
			b.setForeground(Color.yellow);
			b.addActionListener(this);
			add(b,BorderLayout.SOUTH);
		}
		public void show(){						//オーバーライド
			ta.setText("");
			super.show();
		}
		public void actionPerformed(ActionEvent ae){				//ロード開始
			String data=ta.getText();							//ロードするデータ
			byte[][] submap=new byte[180][30];				//成功した時のみこれがコピーされる
			try{
				for(int i1=0;i1<3;i1++){					//3つのかたまり
					for(int i2=0;i2<30;i2++){				//1行
						int begin=data.indexOf("<PARAM NAME=\"map"+i1+"-"+i2+"\"");		//始まりを探す
						if(begin==-1){
							throw new DataLoadException("<PARAM NAME=\"map"+i1+"-"+i2+"\"");
						}
						begin+=29;							//60個の始まりのインデックス
						String oneline=data.substring(begin,begin+60);
						for(int i3=0;i3<60;i3++){			//1文字
							submap[i1*60+i3][i2]=MasaoMakerSP.charToMapByte(oneline.charAt(i3));
						}
					}
				}
				sf.loadByString(data);						//詳細設定ロード,例外発生アリ

				/*ここからは例外が発生せず、終わったとき*/
				for(int i=0;i<map.length;i++){
					System.arraycopy(submap[i],0,map[i],0,map[i].length);
				}
				mc.repaint();
				ALERT.show("成功","ロードに成功しました\nそれではがんばってください。");
				hide();
			}catch(DataLoadException e){					//キーワードが見つからないとthrowされる
				ta.setText(e.toString());
			}
		}
	}

}