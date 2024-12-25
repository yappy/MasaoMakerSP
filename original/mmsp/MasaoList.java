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
			public void keyTyped(KeyEvent ke){			//�L�[�������ꂽ
				char press=ke.getKeyChar();			//���͂��ꂽ����
				String[] lists=getItems();			//���X�g�̂��ׂĂ̕�����
				for(int i=0;i<lists.length;i++){
					if(lists[i].charAt(0)==press){	//������̍ŏ��̕����ƈ�v������
						select(i);
						break;
					}
				}
			}
		});
	}
	public byte getSelectedMap(){			//�I������Ă�����̂�map[][]�p�ɕԂ�
		byte select=(byte)getSelectedIndex();
		if(select!=65){
			return select;
		}else{
			return 99;
		}
	}
}