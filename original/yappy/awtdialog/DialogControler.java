package yappy.awtdialog;

import java.awt.*;

public class DialogControler{

	public static void showMessageDialog(Frame own,String title,String mes){
		MessageDialog md=new MessageDialog(own);
		md.show(title,mes);
	}

	public static boolean showYesNoDialog(Frame own,String title,String mes){
		YesNoDialog yd=new YesNoDialog(own);
		if(yd.show(title,mes)==BlockDialog.FIRST){
			return true;
		}else{
			return false;
		}
	}

	public static String showInputDialog(Frame own,String title,String mes,String def){
		InputDialog id=new InputDialog(own);
		return id.show(title,mes,def);
	}
}
