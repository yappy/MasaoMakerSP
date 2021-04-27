package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

//�f�[�^����͂���܂Ńu���b�N����C���v�b�g�_�C�A���O
/**
 * ���b�Z�[�W�ƃe�L�X�g�t�B�[���h��\�����A�{�^���������܂Ńu���b�N����_�C�A���O�ł��B
 * Swing��JOptionPane.showInputDialog��AWT�Ŏ������܂��B
 * �ǂ�ȕ����񂪓��͂��ꂽ����show���\�b�h�̋A��l�𗘗p���܂��B
 * OK�������ꂽ�ꍇ�́A���͂��ꂽ������A�L�����Z�����~�������ꂽ�ꍇ��null���Ԃ�܂��B
 * <hr>
 * �g�p��:<br>
 * Frame f=new Frame("InputDialog�e�X�g");<br>
 * f.setBounds(0,0,100,100);<br>
 * InputDialog id=new InputDialog(f);<br>
 * f.show();<br>
 * String ans=yd.show("�e�X�g","���������͂���OK�������Ă��������B�B\n���ʂ��o�͂��܂��B","�����ɓ��͂��Ă�������");<br>
 * if(ans!=null){<br>
 *      System.out.println(ans);<br>
 * }else{<br>
 *      Ststem.out.println("�L�����Z������܂����B");<br>
 * }
 * <hr>
 * �x��:�Ԃ�l��int��show���\�b�h�͖����ɂȂ��Ă��܂��B
 * �Ԃ�l��String��show���\�b�h���g���Ă��������B
 */
public class InputDialog extends BlockDialog{
	private TextField tf=new TextField();
	/**
	 * �V����InputDialog�I�u�W�F�N�g�𐶐����܂��B
	 * ���񐶐�����K�v�͂���܂���B
	 * show(String title,String mes,String def)�𗘗p���Ďg���Ă��������B
	 * @param own �e�t���[��
	 */
	public InputDialog(Frame own){
		super(own,BlockDialog.OK_CANCEL);
		ue.setLayout(new GridLayout(4,0));
		ue.add(tf);
	}

	/**
	 * �����ɂ���Ă��܂��B
	 * ��p��show(String title,String mes,String def)���g���Ă��������B
	 */
	public int show(String title,String mes){
		System.err.println("InputDialog����x��");
		System.err.println("int show(String title,String mes)�̓I�[�o�[���C�h���ꖳ��������Ă��܂��B");
		System.err.println("");
		return 0;
	}
	/**
	 * �_�C�A���O��\�����܂��B�{�^�����������܂Ńu���b�N���܂��B
	 * ����ݒ�ł���̂ŁA�^�C�g���⃁�b�Z�[�W���ƂɃI�u�W�F�N�g��new�Ő�������K�v�͂���܂���B
	 * ���b�Z�[�W�́A\n�ŋ�؂邱�Ƃɂ��A3�s�܂ŉ��s�ł��܂��B
	 * @return ���͂��ꂽ������,�������L�����Z�����~�������ꂽ�ꍇ��null
	 * @param title �^�C�g���o�[�ɕ\������^�C�g��
	 * @param mes �\�����郁�b�Z�[�W,\n�ŉ��s
	 * @param def �e�L�X�g�G���A�ɍŏ�������͂���Ă��镶����
	 */
	public String show(String title,String mes,String def){			//���C��
		tf.setText(def);						//OK�̏ꍇ�͏����ꂽ������,�L�����Z�����~��null
		int push=super.show(title,mes);
		if(push==0){						//OK�������ꂽ
			return tf.getText();
		}else{								//�L�����Z���������ꂽ.�܂��́~�������ꂽ
			return null;
		}
	}
}