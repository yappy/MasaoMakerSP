package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

//ブロックするダイアログ
/**
 * ボタンが押されるまでプログラムを停止させる(ブロックする)ダイアログの抽象基底クラスです。<br>
 * よってモーダルであることが必要です。<br>
 * 親フレームはきちんと設定してください。<br>
 * メッセージは \n で区切ることにより3行に分割可能です。<br>
 * また、このクラスには文字列を分解する split メソッドがあります。splitメソッドは1.4以降でしかjava.lang.Stringに搭載されていません。
 */
public abstract class BlockDialog extends DefCloseDlg implements ActionListener{
	/**コンストラクタ用のボタンの種類を決める定数です。「OK」ボタンを表示します。*/
	public static final int OK=0;					//ボタンの種類
	/**コンストラクタ用のボタンの種類を決める定数です。「はい」と「いいえ」ボタンを表示します。*/
	public static final int YES_NO=1;
	/**コンストラクタ用のボタンの種類を決める定数です。「OK」と「キャンセル」ボタンを表示します。*/
	public static final int OK_CANCEL=2;
	/**コンストラクタ用のボタンの種類を決める定数です。「はい」と「いいえ」と「キャンセル」ボタンを表示します。*/
	public static final int YES_NO_CANCEL=3;

	/**押されたボタンを判別する定数です。×ボタンが押されたことを表します。*/
	public static final int CLOSE=-1;
	/**押されたボタンを判別する定数です。一番左のボタンが押されたことを表します。*/
	public static final int FIRST=0;
	/**押されたボタンを判別する定数です。左から二番目のボタンが押されたことを表します。*/
	public static final int SECOND=1;
	/**押されたボタンを判別する定数です。左から三番目のボタンが押されたことを表します。*/
	public static final int THIRD=2;

	private boolean oked=false;						//ブロックを解除することを表す
	private int pushed=0;							//押されたボタン
	private Label[] mes=new Label[3];				//メッセージは3行
	/**ダイアログ上部のコンテナです。GridLayoutにより3分割されメッセージ表示用のラベルが入っています。*/
	protected Panel ue=new Panel();
	/**ダイアログ下部のコンテナです。FlowLayoutによりボタンが入っています。*/
	protected Panel sita=new Panel();

	/**
	 * サブクラスでは必ずこれを super で呼び出してください。
	 * @param own ダイアログのオーナー
	 * @param button ボタンの種類,定数を利用してください
	 */
	protected BlockDialog(Frame own,int button){			//コンストラクタ
		super(own,"",true,DefCloseDlg.HIDE);
		setBounds(200,200,300,200);
		setResizable(false);

		setLayout(new BorderLayout());
		ue.setLayout(new GridLayout(3,0));
		for(int i=0;i<mes.length;i++){
			mes[i]=new Label("",Label.CENTER);
			ue.add(mes[i]);
		}
		add(ue,BorderLayout.CENTER);

		Button[] btns;
		if(button==OK){
			Button b0=new Button("OK");
			btns=new Button[1];
			btns[0]=b0;
		}else if(button==YES_NO){
			Button b0=new Button("はい");
			Button b1=new Button("いいえ");
			btns=new Button[2];
			btns[0]=b0;
			btns[1]=b1;
		}else if(button==OK_CANCEL){
			Button b0=new Button("OK");
			Button b1=new Button("キャンセル");
			btns=new Button[2];
			btns[0]=b0;
			btns[1]=b1;
		}else{									//button==YES_NO_CANCEL
			Button b0=new Button("はい");
			Button b1=new Button("いいえ");
			Button b2=new Button("キャンセル");
			btns=new Button[3];
			btns[0]=b0;
			btns[1]=b1;
			btns[2]=b2;
		}
		for(int i=0;i<btns.length;i++){
			btns[i].setActionCommand(""+i);
			btns[i].addActionListener(this);
			sita.add(btns[i]);
		}
		add(sita,BorderLayout.SOUTH);
	}
	/**
	 * 無効にされています。
	 * 専用のshow(String title,String mes)を使ってください。
	 */
	public void show(){
		System.err.println("BlockDialogから警告");
		System.err.println("show()はオーバーライドされて無効化されています。");
		System.err.println("show(String title,String mes)を呼び出してください。");
	}
	/**
	 * ダイアログを表示します。ボタンが押されるまでブロックします。
	 * 毎回設定できるので、タイトルやメッセージごとにオブジェクトをnewで生成する必要はありません。
	 * メッセージは、\nで区切ることにより、3行まで改行できます。
	 * @return 押されたボタン,定数を利用してください。表示するだけなら戻り値を利用する必要はありません。
	 * @param title タイトルバーに表示するタイトル
	 * @param mes 表示するメッセージ,\nで改行
	 */
	public int show(String title,String mes){
		setTitle(title);
		String[] splited=split(mes,"\n");			//"\n"で分けて
		for(int i=0;i<this.mes.length;i++){
			this.mes[i].setText("");
		}
		for(int i=0;i<splited.length;i++){
			try{
			this.mes[i].setText(splited[i]);
			}catch(ArrayIndexOutOfBoundsException e){break;}
		}
		super.show();
		while(!oked){
			try{Thread.sleep(10);}catch(InterruptedException e){}
		}
		return pushed;
	}
	/**ボタンクリックイベントです。*/
	public synchronized  final void actionPerformed(ActionEvent ae){		//whileの条件分岐と重ならないようにするため
		pushed=Integer.parseInt(ae.getActionCommand());
		oked=true;
		hide();
	}
	/**×が押されたときのイベントです。*/
	public final void windowClosing(WindowEvent we){
		oked=true;
		pushed=-1;
		hide();
	}

	/**
	 * 文字列を分割します。<br>
	 * java.lang.StringのsplitはJava1.4で搭載されました。
	 * 1.1互換のアプレットなどで活用してください。
	 * @param target 分割対象文字列
	 * @param regex 分割に使う文字列
	 * @return 分割済みの文字列の配列
	 */
	public static String[] split(String target,String regex){			//targetをregexで分割する
		int count=0;
		int begin=0;
		while(true){
			if(target.indexOf(regex,begin) != -1){
				count++;
				begin=target.indexOf(regex,begin)+1;
			}else{
				break;
			}
		}
		String[] forret=new String[count+1];
		begin=0;
		for(int i=0;i<forret.length;i++){
			if(i==forret.length-1){
				forret[i]=target.substring(begin);
			}else{
				forret[i]=target.substring(begin,target.indexOf(regex,begin));
			}
			begin=target.indexOf(regex,begin)+regex.length();
		}
		return forret;
	}

}