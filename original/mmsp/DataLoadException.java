public class DataLoadException extends Exception{
	public static final int NOT_FOUND=0;
	public static final int FORMAT_ERROR=1;

	private int type=0;
	private String message="";

	public DataLoadException(){}
	public DataLoadException(String not_found_data){
		message=not_found_data;
	}
	public DataLoadException(String could_not_format_data,int type){
		this.type=type;
		message=could_not_format_data;
	}

	public String toString(){
		if(type==NOT_FOUND){
			return "DataLoadException : "+message+" が見つかりません。";
		}else if(type==FORMAT_ERROR){
			return "DataLoadException : "+message+" はマップデータに変換できません。";
		}else{
			return "DataLoadException";
		}
	}
}