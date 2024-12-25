import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MasaoList extends List {
	public MasaoList(String[] data){
		super();
		setBounds(353,90,500-352,352-90);
		setBackground(Color.pink);
		for(int i=0;i<data.length;i++){
			add(data[i]);
		}
		select(0);
		addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent ke){			//キーが押された
				char press=ke.getKeyChar();			//入力された文字
				String[] lists=getItems();			//リストのすべての文字列
				for(int i=0;i<lists.length;i++){
					if(lists[i].charAt(0)==press){	//文字列の最初の文字と一致したら
						select(i);
						break;
					}
				}
			}
		});
	}
	public byte getSelectedMap(){			//選択されているものをmap[][]用に返す
		byte select=(byte)getSelectedIndex();
		if(select!=65){
			return select;
		}else{
			return 99;
		}
	}
}