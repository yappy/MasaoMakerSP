package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

//はいかいいえを押すまでブロックするコンファームダイアログ
/**
 * メッセージを表示し、「はい」か「いいえ」を押すまでブロックするダイアログです。
 * SwingのJOptionPane.showConfirmDialogをAWTで実現します。
 * どのボタンが押されたかはshowメソッドの帰り値とBlockDialogの定数を比較します。
 * <hr>
 * 使用例:<br>
 * Frame f=new Frame("YesNoDialogテスト");<br>
 * f.setBounds(0,0,100,100);<br>
 * YesNoDialog yd=new YesNoDialog(f);<br>
 * f.show();<br>
 * int ans=yd.show("テスト","はいかいいえを押してください。\n結果を出力します。");<br>
 * switch(ans){<br>
 * case YesNoDialog.FIRST:<br>
 *      System.out.println("はい");<br>
 *      break;<br>
 * case YesNoDialog.SECOND:<br>
 *      System.out.println("いいえ");<br>
 *      break;<br>
 * case YesNoDialog.CLOSE:<br>
 *      System.out.println("×");<br>
 *      break;<br>
 * }
 * <hr>
 */
public class YesNoDialog extends BlockDialog{
	/**
	 * 新しいYesNoDialogオブジェクトを生成します。
	 * 毎回生成する必要はありません。
	 * BlockDialogから継承した show(String title,String mes)を利用して使ってください。
	 * @param own 親フレーム
	 */
	public YesNoDialog(Frame own){
		super(own,BlockDialog.YES_NO);
	}
}