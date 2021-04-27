package yappy.awtdialog;

import java.awt.*;
import java.awt.event.*;

/**
 * �R���X�g���N�^�Ń^�C�g���o�[�́~�������ꂽ�Ƃ���<br>
 * ������ݒ�ł���֗��ȃ_�C�A���O�ł��B<br><br>
 * ���̃N���X�͒��ۃN���X�ł���A<br>
 * �T�C�Y�̐ݒ�Ȃǂ̓T�u�N���X�Ŏ������ׂ��ł��B
 */
public abstract class DefCloseDlg extends Dialog implements WindowListener{	//�ŏ�����C�x���g�����Ă���_�C�A���O
	/**
	 * �R���X�g���N�^�p�̒萔�ł��B
	 * �~�������ꂽ�Ƃ��̉�����ݒ肵�܂��B
	 * hide()���Ăяo���ă_�C�A���O���B���܂��B
	 */
	public static final int HIDE=0;
	/**
	 * �R���X�g���N�^�p�̒萔�ł��B
	 * �~�������ꂽ�Ƃ��̉�����ݒ肵�܂��B
	 * System.exit()���Ăяo���ăv���O�������I�����܂��B
	 */
	public static final int EXIT=1;
	/**
	 * �R���X�g���N�^�p�̒萔�ł��B
	 * �~�������ꂽ�Ƃ��̉�����ݒ肵�܂��B
	 * �������܂���B
	 */
	public static final int NOTHING=3;

	private int mode;

	/**
	 * �R���X�g���N�^�ł��B<br>
	 * �T�u�N���X�ł͂����super�ŌĂяo���Ă��������B<br>
	 * @param own �e�̃t���[��
	 * @param title �^�C�g���o�[�ɕ\������镶����
	 * @param modal ���[�_���ł��邩�ǂ���
	 * @param mode �~�������ꂽ�Ƃ��̉���,�萔�𗘗p���Ă�������
	 */
	protected DefCloseDlg(Frame own,String title,boolean modal,int mode){
		super(own,title,modal);
		addWindowListener(this);
		this.mode=mode;
	}

	/**
	 * ���[�U���A�E�B���h�E�̃V�X�e�����j���[�ŃE�B���h�E����悤�Ƃ����Ƃ��ɌĂяo����܂��B<br>
	 * �ݒ肳�ꂽ���������܂��B<br>
	 * �I�[�o�[���C�h����ꍇ�́A�K��super.windowClosing(e)���Ăяo���Ă��������B<br>
	 */
	public void windowClosing(WindowEvent e){
		if(mode==HIDE){
			hide();
		}else if(mode==EXIT){
			System.exit(0);
		}
	}
	/**�E�B���h�E���A�N�e�B�u�ɐݒ肳���ƌĂяo����܂��B<br>�f�t�H���g�ł͉������܂���B<br>�ꍇ�ɉ����ăI�[�o�[���C�h���Ă��������B*/
	public void windowActivated(WindowEvent e){}
	/**�E�B���h�E���N���[�Y���ꂽ�Ƃ��ɌĂяo����܂��B<br>�f�t�H���g�ł͉������܂���B<br>�ꍇ�ɉ����ăI�[�o�[���C�h���Ă��������B*/
	public void windowClosed(WindowEvent e){}
	/**�E�B���h�E���A�N�e�B�u�łȂ��Ȃ��ƌĂяo����܂��B<br>�f�t�H���g�ł͉������܂���B<br>�ꍇ�ɉ����ăI�[�o�[���C�h���Ă��������B*/
	public void windowDeactivated(WindowEvent e){}
	/**�E�B���h�E���ŏ������ꂽ��Ԃ���ʏ�̏�ԂɕύX���ꂽ�Ƃ��ɌĂяo����܂��B<br>�f�t�H���g�ł͉������܂���B<br>�ꍇ�ɉ����ăI�[�o�[���C�h���Ă��������B*/
	public void windowDeiconified(WindowEvent e){}
	/**�E�B���h�E���ŏ������ꂽ�Ƃ��ɌĂяo����܂��B<br>�f�t�H���g�ł͉������܂���B<br>�ꍇ�ɉ����ăI�[�o�[���C�h���Ă��������B*/
	public void windowIconified(WindowEvent e){}
	/**�E�B���h�E���ŏ��ɉ��ɂȂ����Ƃ��ɌĂяo����܂��B<br>�f�t�H���g�ł͉������܂���B<br>�ꍇ�ɉ����ăI�[�o�[���C�h���Ă��������B*/
	public void windowOpened(WindowEvent e){}
}