package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

/**
 * コンストラクタでタイトルバーの×が押されたときの<br>
 * 応答を設定できる便利なダイアログです。<br><br>
 * このクラスは抽象クラスであり、<br>
 * サイズの設定などはサブクラスで実装すべきです。
 */
public abstract class DefCloseDlg extends Dialog implements WindowListener{	//最初からイベントがついているダイアログ
	/**
	 * コンストラクタ用の定数です。
	 * ×が押されたときの応答を設定します。
	 * hide()を呼び出してダイアログを隠します。
	 */
	public static final int HIDE=0;
	/**
	 * コンストラクタ用の定数です。
	 * ×が押されたときの応答を設定します。
	 * System.exit()を呼び出してプログラムを終了します。
	 */
	public static final int EXIT=1;
	/**
	 * コンストラクタ用の定数です。
	 * ×が押されたときの応答を設定します。
	 * 何もしません。
	 */
	public static final int NOTHING=3;

	private int mode;

	/**
	 * コンストラクタです。<br>
	 * サブクラスではこれをsuperで呼び出してください。<br>
	 * @param own 親のフレーム
	 * @param title タイトルバーに表示される文字列
	 * @param modal モーダルであるかどうか
	 * @param mode ×が押されたときの応答,定数を利用してください
	 */
	protected DefCloseDlg(Frame own,String title,boolean modal,int mode){
		super(own,title,modal);
		addWindowListener(this);
		this.mode=mode;
	}

	/**
	 * ユーザが、ウィンドウのシステムメニューでウィンドウを閉じようとしたときに呼び出されます。<br>
	 * 設定された応答をします。<br>
	 * オーバーライドする場合は、必ずsuper.windowClosing(e)を呼び出してください。<br>
	 */
	public void windowClosing(WindowEvent e){
		if(mode==HIDE){
			hide();
		}else if(mode==EXIT){
			System.exit(0);
		}
	}
	/**ウィンドウがアクティブに設定されると呼び出されます。<br>デフォルトでは何もしません。<br>場合に応じてオーバーライドしてください。*/
	public void windowActivated(WindowEvent e){}
	/**ウィンドウがクローズされたときに呼び出されます。<br>デフォルトでは何もしません。<br>場合に応じてオーバーライドしてください。*/
	public void windowClosed(WindowEvent e){}
	/**ウィンドウがアクティブでなくなるると呼び出されます。<br>デフォルトでは何もしません。<br>場合に応じてオーバーライドしてください。*/
	public void windowDeactivated(WindowEvent e){}
	/**ウィンドウが最小化された状態から通常の状態に変更されたときに呼び出されます。<br>デフォルトでは何もしません。<br>場合に応じてオーバーライドしてください。*/
	public void windowDeiconified(WindowEvent e){}
	/**ウィンドウが最小化されたときに呼び出されます。<br>デフォルトでは何もしません。<br>場合に応じてオーバーライドしてください。*/
	public void windowIconified(WindowEvent e){}
	/**ウィンドウが最初に可視になったときに呼び出されます。<br>デフォルトでは何もしません。<br>場合に応じてオーバーライドしてください。*/
	public void windowOpened(WindowEvent e){}
}