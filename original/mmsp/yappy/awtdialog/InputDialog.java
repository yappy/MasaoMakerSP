package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

//データを入力するまでブロックするインプットダイアログ
/**
 * メッセージとテキストフィールドを表示し、ボタンを押すまでブロックするダイアログです。
 * SwingのJOptionPane.showInputDialogをAWTで実現します。
 * どんな文字列が入力されたかはshowメソッドの帰り値を利用します。
 * OKが押された場合は、入力された文字列、キャンセルか×が押された場合はnullが返ります。
 * <hr>
 * 使用例:<br>
 * Frame f=new Frame("InputDialogテスト");<br>
 * f.setBounds(0,0,100,100);<br>
 * InputDialog id=new InputDialog(f);<br>
 * f.show();<br>
 * String ans=yd.show("テスト","文字列を入力してOKを押してください。。\n結果を出力します。","ここに入力してください");<br>
 * if(ans!=null){<br>
 *      System.out.println(ans);<br>
 * }else{<br>
 *      Ststem.out.println("キャンセルされました。");<br>
 * }
 * <hr>
 * 警告:返り値がintのshowメソッドは無効になっています。
 * 返り値がStringのshowメソッドを使ってください。
 */
public class InputDialog extends BlockDialog{
	private TextField tf=new TextField();
	/**
	 * 新しいInputDialogオブジェクトを生成します。
	 * 毎回生成する必要はありません。
	 * show(String title,String mes,String def)を利用して使ってください。
	 * @param own 親フレーム
	 */
	public InputDialog(Frame own){
		super(own,BlockDialog.OK_CANCEL);
		ue.setLayout(new GridLayout(4,0));
		ue.add(tf);
	}

	/**
	 * 無効にされています。
	 * 専用のshow(String title,String mes,String def)を使ってください。
	 */
	public int show(String title,String mes){
		System.err.println("InputDialogから警告");
		System.err.println("int show(String title,String mes)はオーバーライドされ無効化されています。");
		System.err.println("");
		return 0;
	}
	/**
	 * ダイアログを表示します。ボタンが押されるまでブロックします。
	 * 毎回設定できるので、タイトルやメッセージごとにオブジェクトをnewで生成する必要はありません。
	 * メッセージは、\nで区切ることにより、3行まで改行できます。
	 * @return 入力された文字列,ただしキャンセルか×が押された場合はnull
	 * @param title タイトルバーに表示するタイトル
	 * @param mes 表示するメッセージ,\nで改行
	 * @param def テキストエリアに最初から入力されている文字列
	 */
	public String show(String title,String mes,String def){			//メイン
		tf.setText(def);						//OKの場合は書かれた文字列,キャンセルか×はnull
		int push=super.show(title,mes);
		if(push==0){						//OKが押された
			return tf.getText();
		}else{								//キャンセルが押された.または×が押された
			return null;
		}
	}
}