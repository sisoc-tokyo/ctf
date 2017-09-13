package ctf;

import java.io.*;
import java.util.*;

public class Statistics {
	//生徒名が検索キーになるので、生徒名をキー、チームを値として、Map(pythonでは辞書型)に持たせておく
	private static Map<String,Student> students;
	//チーム名が検索キーになるので、チーム名をキー、スコアを値として、Map(pythonでは辞書型)に持たせておく
	private static Map<String,Integer> teamtestHistories;
	private static final String INPUT_FILE_PATH_GROUP="data/group.csv";
	private static final String INPUT_FILE_PATH_HISTORY="data/history_record.csv";
	// 各問題の配点は１００点固定の想定
	private static int SCORE =100;
	
	//コンストラクタ
	Statistics(){
		//初期化
		students=new HashMap<String,Student>();
		teamtestHistories=new HashMap<String,Integer>();
	}
	
	// group.csvを読み込んでStudentオブジェクトを生成する
	private void readStudentInfo(){
		BufferedReader br = null;
		try {
			File f = new File(INPUT_FILE_PATH_GROUP);
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",", 0);
				Student student = new Student(data[0],data[1]);
				students.put(data[0],student);
			}
		}catch (IOException e){
			e.printStackTrace();
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// group.csvを読み込んでStudentオブジェクトを生成する
	private void readTestHistory(){
		BufferedReader br = null;
		try {
			File f = new File(INPUT_FILE_PATH_HISTORY);
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",", 0);
				try {
					Integer.parseInt(data[0]);
				}catch (NumberFormatException e) {
					// 数値型に変換できなければ、スキップ（ヘッダなど）
					continue;
				}
				// 2列目に記録されているユーザ名を取得
				String username=data[4];
				// そのユーザが所属するチームを取得
				String team=students.get(username).getTeam();
				//正解したかを取得
				boolean isCorrect=Boolean.parseBoolean(data[8]);
				//チーム名をキーとして、該当チームのスコアを取得
				Integer score=teamtestHistories.get(team);
				if(null==score){
					score=0;
				}
				if(isCorrect){
					//正解しているなら加算する
					score+=SCORE;
				}
				//加算したスコア情報を更新する
				teamtestHistories.put(team, score);
			}
		}catch (IOException e){
			e.printStackTrace();
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]){
		Statistics statistics=new Statistics();
		statistics.readStudentInfo();
		statistics.readTestHistory();
		/*
		for (Iterator it = students.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String,Student> entry = (Map.Entry<String,Student>) it.next();
			Student student=(Student)entry.getValue();
			System.out.println(student.getName()+","+student.getTeam());
		}
		*/
		for (Iterator it = teamtestHistories.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) it.next();
			String team=entry.getKey();
			int score=(Integer)entry.getValue();
			System.out.println(team+": "+score);
		}
	}
}
