package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

//�͂����������������܂Ńu���b�N����R���t�@�[���_�C�A���O
/**
 * ���b�Z�[�W��\�����A�u�͂��v���u�������v�������܂Ńu���b�N����_�C�A���O�ł��B
 * Swing��JOptionPane.showConfirmDialog��AWT�Ŏ������܂��B
 * �ǂ̃{�^���������ꂽ����show���\�b�h�̋A��l��BlockDialog�̒萔���r���܂��B
 * <hr>
 * �g�p��:<br>
 * Frame f=new Frame("YesNoDialog�e�X�g");<br>
 * f.setBounds(0,0,100,100);<br>
 * YesNoDialog yd=new YesNoDialog(f);<br>
 * f.show();<br>
 * int ans=yd.show("�e�X�g","�͂����������������Ă��������B\n���ʂ��o�͂��܂��B");<br>
 * switch(ans){<br>
 * case YesNoDialog.FIRST:<br>
 *      System.out.println("�͂�");<br>
 *      break;<br>
 * case YesNoDialog.SECOND:<br>
 *      System.out.println("������");<br>
 *      break;<br>
 * case YesNoDialog.CLOSE:<br>
 *      System.out.println("�~");<br>
 *      break;<br>
 * }
 * <hr>
 */
public class YesNoDialog extends BlockDialog{
	/**
	 * �V����YesNoDialog�I�u�W�F�N�g�𐶐����܂��B
	 * ���񐶐�����K�v�͂���܂���B
	 * BlockDialog����p������ show(String title,String mes)�𗘗p���Ďg���Ă��������B
	 * @param own �e�t���[��
	 */
	public YesNoDialog(Frame own){
		super(own,BlockDialog.YES_NO);
	}
}