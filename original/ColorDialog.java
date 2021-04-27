import java.awt.*;
import java.awt.event.*;
import yappy.awtdialog.*;

public class ColorDialog extends DefCloseDlg implements AdjustmentListener{		//�F�̃e�X�g�p�_�C�A���O
	private Scrollbar red=new Scrollbar(Scrollbar.HORIZONTAL,0,10,0,265);		//�X�N���[���o�[
	private Scrollbar green=new Scrollbar(Scrollbar.HORIZONTAL,0,10,0,265);
	private Scrollbar blue=new Scrollbar(Scrollbar.HORIZONTAL,0,10,0,265);
	private Label label=new Label();
	private Label tostr=new Label("��:0 ��:0 ��:0",Label.CENTER);

	public ColorDialog(Frame own,String title){
		super(own,title,true,DefCloseDlg.HIDE);
		setBounds(100,100,300,200);
		red.addAdjustmentListener(this);
		green.addAdjustmentListener(this);
		blue.addAdjustmentListener(this);
		setLayout(new GridLayout(5,0));
		label.setBackground(Color.black);
		add(red);
		add(green);
		add(blue);
		add(label);
		add(tostr);
	}

	public void adjustmentValueChanged(AdjustmentEvent ae){
		int r=red.getValue();
		int g=green.getValue();
		int b=blue.getValue();
		label.setBackground(new Color(r,g,b));
		tostr.setText("��:"+r+" ��:"+g+" ��:"+b);
	}
}