package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

/**
 * メッセージを表示し、OKを押すまでブロックするダイアログです。
 * SwingのJOptionPane.showMessageDialogをAWTで実現します。
 * <hr>
 * 使用例:<br>
 * Frame f=new Frame("MessageDialogテスト");<br>
 * f.setBounds(0,0,100,100);<br>
 * MessageDialog md=new MessageDialog(f);<br>
 * f.show();<br>
 * md.show("テスト","OKを押すまで下のプログラムが\n実行されません");<br>
 * System.out.println("ブロック解除");
 * <hr>
 */
//OKを押すまでブロックするメッセージダイアログ
public class MessageDialog extends BlockDialog{
	/**
	 * 新しいMessageDialogオブジェクトを生成します。
	 * 毎回生成する必要はありません。
	 * BlockDialogから継承した show(String title,String mes)を利用して使ってください。
	 * @param own 親フレーム
	 */
	public MessageDialog(Frame own){
		super(own,BlockDialog.OK);
	}

}