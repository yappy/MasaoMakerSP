import java.awt.*;
import java.awt.event.*;

/*
プレビュー用フレーム！
*/

public class PreviewFrame extends Frame implements ActionListener{
	private int[][] map;
	private Image mainimg;
	private Image subimg;
	private Graphics subgra;
	private PreCan can=new PreCan();
	private Button left=new Button("左(0..59)");
	private Button center=new Button("中央(60..119)");
	private Button right=new Button("右(120..179)");
	private Button update=new Button("更新");
	private int pos=0;

	public PreviewFrame(int[][] map,Image mainimg,Image subimg){
		super("プレビュー");
		this.map=map;
		this.mainimg=mainimg;
		this.subimg=subimg;
		subgra=subimg.getGraphics();
		setBounds(100,100,500,350);

		Panel btns=new Panel();
		btns.setLayout(new GridLayout(1,4));
		btns.add(left);
		btns.add(center);
		btns.add(right);
		btns.add(update);
		left.addActionListener(this);
		center.addActionListener(this);
		right.addActionListener(this);
		update.addActionListener(this);
		left.setBackground(Color.red);

		add(can,BorderLayout.CENTER);
		add(btns,BorderLayout.SOUTH);

		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){setVisible(false);}});
	}

	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==left){
			pos=0;
			ButtonColorClear();
			left.setBackground(Color.red);
		}else if(ae.getSource()==center){
			pos=60;
			ButtonColorClear();
			center.setBackground(Color.red);
		}else if(ae.getSource()==right){
			pos=120;
			ButtonColorClear();
			right.setBackground(Color.red);
		}
		can.repaint();
	}

	private void ButtonColorClear(){
		left.setBackground(update.getBackground());
		center.setBackground(update.getBackground());
		right.setBackground(update.getBackground());
	}

	private class PreCan extends Canvas{
		public void paint(Graphics g){
			subgra.setColor(Color.cyan);
			subgra.fillRect(0,0,480,240);

			for(int i=0;i<30;i++){
				for(int j=0;j<60;j++){
					if(map[j][i]!=99){
						subgra.drawImage(mainimg,j*8,i*8,j*8+8,i*8+8,map[j+pos][i]%10*32,map[j+pos][i]/10*32,map[j+pos][i]%10*32+32,map[j+pos][i]/10*32+32,null);
					}
				}
			}

			g.drawImage(subimg,0,0,null);
		}
		public void update(Graphics g){
			paint(g);
		}
	}
}