package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

/**
 * ���b�Z�[�W��\�����AOK�������܂Ńu���b�N����_�C�A���O�ł��B
 * Swing��JOptionPane.showMessageDialog��AWT�Ŏ������܂��B
 * <hr>
 * �g�p��:<br>
 * Frame f=new Frame("MessageDialog�e�X�g");<br>
 * f.setBounds(0,0,100,100);<br>
 * MessageDialog md=new MessageDialog(f);<br>
 * f.show();<br>
 * md.show("�e�X�g","OK�������܂ŉ��̃v���O������\n���s����܂���");<br>
 * System.out.println("�u���b�N����");
 * <hr>
 */
//OK�������܂Ńu���b�N���郁�b�Z�[�W�_�C�A���O
public class MessageDialog extends BlockDialog{
	/**
	 * �V����MessageDialog�I�u�W�F�N�g�𐶐����܂��B
	 * ���񐶐�����K�v�͂���܂���B
	 * BlockDialog����p������ show(String title,String mes)�𗘗p���Ďg���Ă��������B
	 * @param own �e�t���[��
	 */
	public MessageDialog(Frame own){
		super(own,BlockDialog.OK);
	}

}