import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import unyoawt.*;

/* 新機能!  詳細設定フレーム */

public class SettingFrame extends Frame implements ActionListener,ItemListener,TextListener{
	public static final int SET_NUM=71;			//設定内容の個数
	public static final String SPLIT=",";		//説明を区切る際の文字

	private String[][] info=new String[SET_NUM][];		//説明
	private String[] name=new String[SET_NUM];			//<param>のname
	private String[] value=new String[SET_NUM];			//value
	private String[] def=new String[SET_NUM];			//<param>の初期状態のvalue

	private Choice sel=new Choice();					//コンボボックス
	private TextArea ta=new TextArea("制限時間"+System.getProperty("line.separator")+"0を設定すると無制限");	//説明表示
	private TextField val=new TextField("0");			//値設定用
	private Button color=new Button("色をつくる");				//ボタン
	private Button defal=new Button("全てデフォルトに戻す");

	private ColorDialog cd=new ColorDialog(this,"色のテスト");

	public SettingFrame(String title,String[] txtdata){			//コンストラクタ
		super(title);
		setBounds(100,100,500,300);

		setLayout(new BorderLayout());						//コンポーネント配置
		sel.setBackground(Color.pink);
		add(sel,BorderLayout.NORTH);
		add(ta,BorderLayout.CENTER);

		Panel p=new Panel();
		Label mes=new Label("↓ここで値を設定します。");
		mes.setBackground(Color.yellow);
		ta.setEditable(false);
		ta.setBackground(Color.green);
		val.setBackground(Color.cyan);
		color.setBackground(Color.red);
		defal.setBackground(Color.black);
		defal.setForeground(Color.white);

		color.addActionListener(this);				//イベント設定
		defal.addActionListener(this);
		val.addTextListener(this);
		sel.addItemListener(this);

		p.setLayout(new GridLayout(4,0));
		p.add(mes);
		p.add(val);
		p.add(color);
		p.add(defal);
		add(p,BorderLayout.SOUTH);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				hide();
			}
		});
		for(int i=0;i<SET_NUM;i++){							//データ読み込み
			info[i]=split(txtdata[i*3],SPLIT);					//","で区切ってできた説明の配列を代入
			sel.add(info[i][0]);
			name[i]=txtdata[i*3+1];
			value[i]=txtdata[i*3+2];
			def[i]=value[i];
		}
	}

	public static String[] split(String target,String regex){			//targetをregexで分割する
		int count=0;
		int begin=0;
		while(true){
			if(target.indexOf(regex,begin) != -1){
				count++;
				begin=target.indexOf(regex,begin)+1;
			}else{
				break;
			}
		}
		String[] forret=new String[count+1];
		begin=0;
		for(int i=0;i<forret.length;i++){
			if(i==forret.length-1){
				forret[i]=target.substring(begin);
			}else{
				forret[i]=target.substring(begin,target.indexOf(regex,begin));
			}
			begin=target.indexOf(regex,begin)+regex.length();
		}
		return forret;
	}
	public void show(){									//オーバーライド
		super.show();
		val.setText(value[sel.getSelectedIndex()]);		//今選ばれている設定値にする
	}

	public void itemStateChanged(ItemEvent ie){			//コンボボックス変更
		updateValue();
	}
	public void textValueChanged(TextEvent te){			//テキストフィールド変更
		value[sel.getSelectedIndex()]=val.getText();
	}
	public void actionPerformed(ActionEvent ae){		//ボタンを押す
		if(ae.getSource()==color){			//色テスト
			cd.show();
		}else if(ae.getSource()==defal){	//デフォルトに戻す
			delete();
			updateValue();
		}
	}

	public void updateValue(){					//値を更新
		int num=sel.getSelectedIndex();
		String str="";
		val.setText(value[num]);
		for(int i=0;i<info[num].length;i++){
			str=str+info[num][i]+System.getProperty("line.separator");
		}
		ta.setText(str);
	}
	public void delete(){							//デフォルトに戻す
		System.arraycopy(def,0,value,0,def.length);		//defからvalueにコピー
	}

	public String toStr(){						//HTMLにする
		String str="";
		for(int i=0;i<SET_NUM;i++){
			str+="<PARAM NAME=\""+name[i]+"\" VALUE=\""+value[i]+"\">"+System.getProperty("line.separator");	//本体
			String infoall="";
			/*
			for(int ii=0;ii<info[i].length;ii++){		//説明をつなぐ
				infoall+=info[i][ii];
			}
			str+="<!-- "+infoall+" -->"+System.getProperty("line.separator");
			*/
		}
		return str;
	}

	public String getDefaultString(){					//デフォルトの詳細設定のHTML(JavaScript用)
		String str="";
		for(int i=0;i<name.length;i++){
			str+="<PARAM NAME=\""+name[i]+"\" VALUE=\""+def[i]+"\">"+System.getProperty("line.separator");
		}
		return str;
	}

	public void loadByString(String data)throws DataLoadException{			//HTMLからロード
		for(int i=0;i<value.length;i++){
			int begin=data.indexOf("<PARAM NAME=\""+name[i]+"\"");		//抜き出すはじめの位置
			if(begin==-1){
				throw new DataLoadException("<PARAM NAME=\""+name[i]+"\"");
			}
			begin=begin+22+name[i].length();
			int end=data.indexOf("\"",begin);							//抜き出すおわりの位置,beginの直後の 「"」
			value[i]=data.substring(begin,end);
		}
	}

}