package jp.alhinc.kasikura_kento.calculate_sales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CalculateSales {
	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
		HashMap<String, String> branchname = new HashMap<String, String>();
		HashMap<String, String> commodityname = new HashMap<String, String>();
		HashMap<String, Long> branchsale = new HashMap<String, Long>();
		HashMap<String, Long> commoditysale = new HashMap<String, Long>();
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
//支店定義ファイル読み込み
		try {
			file = new File(args[0],"branch.lst");
			if (!file.exists()) {
				System.out.println("支店定義ファイルが存在しません");
				return;
			}
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null) {
				String[] branches = s.split(",");
				if(!branches[0].matches("\\d{3}") || branches.length != 2) {
					System.out.println("支店定義ファイルのフォーマットが不正です");
					return;
				}
				branchname.put(branches[0],branches[1]);
				branchsale.put(branches[0],0L);
			}
		} catch(IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		} finally {
			if(br !=null)
				try{
					br.close();
				}  catch(IOException e) {
					System.out.println("予期せぬエラーが発生しました");
				}
		}
//商品定義ファイル読み込み
		try {
			file = new File(args[0],"commodity.lst");
			if (!file.exists()) {
				System.out.println("商品定義ファイルが存在しません");
				return;
			}
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null) {
				String[] commodities = s.split(",");
				if(!commodities[0].matches("\\w{8}") || commodities.length != 2) {
					System.out.println("商品定義ファイルのフォーマットが不正です");
					return;
				}
				commodityname.put(commodities[0],commodities[1]);
				commoditysale.put(commodities[0], 0L);
			}
		} catch(IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return;
		}  finally {
			if(br != null);
			try{
				br.close();
			} catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
			}
		}
//売上集計
		try{
			file = new File(args[0]);
			File fileAll[] = file.listFiles();
			List<File> filercd  = new ArrayList<File>();
			for(int i = 0;i < fileAll.length; i++){
				if(fileAll[i].getName().matches("\\d{8}.rcd") ){
					filercd.add(fileAll[i]);
				}
			}
			for(int i = 1;i < filercd.size(); i++){
				int n = Integer.parseInt(filercd.get(i -1).getName().substring(0,8));
				int m = Integer.parseInt(filercd.get(i).getName().substring(0,8));
				if(m - n != 1){
					System.out.println( "売上ファイル名が連番になっていません");
					return;
				}
			}
			for(int i = 0;i < filercd.size();i++){
				fr = new FileReader(filercd.get(i));
				br = new BufferedReader(fr);
				String s;
				List<String> rcdRead = new ArrayList<String>();
				while((s = br.readLine()) != null) {
					rcdRead.add(s);
				}
				if (rcdRead.size() != 3){
					System.out.println(fileAll[i].getName() + "のフォーマットが不正です");
					return;
				}
				if(branchname.get(rcdRead.get(0)) == null){
					System.out.println(fileAll[i].getName() + "の支店コードが不正です");
					return;
				}
				if(commodityname.get(rcdRead.get(1)) == null){
					System.out.println(fileAll[i].getName() + "の商品コードが不正です");
					return;
				}
				Long branchsum = branchsale.get(rcdRead.get(0));
				branchsum += Long.parseLong(rcdRead.get(2));
				Long commoditysum = commoditysale.get(rcdRead.get(1));
				commoditysum += Long.parseLong(rcdRead.get(2));
				if(!String.valueOf( branchsum ).matches("\\d{0,10}") || !String.valueOf( commoditysum ).matches("\\d{0,10}")){
					System.out.println("合計金額が10桁を超えました");
					return;
				}
				branchsale.put(rcdRead.get(0), branchsum);
				commoditysale.put(rcdRead.get(1), commoditysum);
			}
		} catch(IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		} finally {
			if(br != null);
			try{
				br.close();
			} catch(IOException e) {
				System.out.println("予期せぬエラーが発生しました");
			}
		}
//出力
	//支店ソート売上降順
		List<Map.Entry<String,Long>> entries = new ArrayList<Map.Entry<String,Long>>(branchsale.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String,Long>>() {
			public int compare(Entry<String,Long> entry1, Entry<String,Long> entry2) {
				return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
			}
		});
	//商品ソート売上降順
		List<Map.Entry<String,Long>> entries2 = new ArrayList<Map.Entry<String,Long>>(commoditysale.entrySet());
		Collections.sort(entries2, new Comparator<Map.Entry<String,Long>>() {
			public int compare(Entry<String,Long> entry1, Entry<String,Long> entry2) {
				return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
			}
		});
	//ファイルを作成し、実際に出力
		FileWriter fw = null;
		BufferedWriter bw =null;
		try{
			File newfile = new File(args[0],"branch.out");
			newfile.createNewFile();
			File bsf = new File(args[0],"branch.out");
			fw = new FileWriter(bsf);
			bw = new BufferedWriter(fw);
			for (Entry<String,Long> s : entries){
				bw.write(s.getKey() + "," + branchname.get(s.getKey()) + "," + s.getValue() + "\n");
			}

		} catch(IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		} finally {
			try{
				if(bw != null){
					bw.close();
				}
			} catch(IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return;
			}
		}
		try{
			File newfile = new File(args[0],"commodity.out");
			newfile.createNewFile();
			File csf = new File(args[0],"commodity.out");
			fw = new FileWriter(csf);
			bw = new BufferedWriter(fw);
			for (Entry<String,Long> s : entries2){
				bw.write(s.getKey() + "," + commodityname.get(s.getKey()) + "," + s.getValue() + "\n");
			}
		} catch(IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		} finally {
			try{
				if(bw != null){
					bw.close();
				}
			} catch(IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return;
			}
		}
	}
}
