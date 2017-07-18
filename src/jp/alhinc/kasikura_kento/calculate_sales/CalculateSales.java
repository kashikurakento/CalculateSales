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
//読込メソッド
	public static boolean fileIn(String dirPath,String filename,String braORcom,String regex,
			HashMap<String, String> name, HashMap<String, Long> sale){
		BufferedReader br = null;
		try {
			File file = new File(dirPath,filename);
			if (!file.exists()) {
				System.out.println(braORcom + "定義ファイルが存在しません");
				return false;
			}
			br = new BufferedReader(new FileReader(file));
			String s;
			while((s = br.readLine()) != null) {
				String[] codeANDname = s.split(",");
				if(!codeANDname[0].matches(regex) || codeANDname.length != 2) {
					System.out.println(braORcom + "定義ファイルのフォーマットが不正です");
					return false;
				}
				name.put(codeANDname[0],codeANDname[1]);
				sale.put(codeANDname[0],0L);
			}
		} catch(IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return false;
		} finally {
			if(br !=null){
				try{
					br.close();
				}  catch(IOException e) {
					System.out.println("予期せぬエラーが発生しました");
					return false;
				}
			}
		}
		return true;
	}
//出力メソッド
	public static boolean  fileOut(String dirPath, String fileName, HashMap<String, String> name, HashMap<String, Long> sale){
		List<Map.Entry<String,Long>> entries = new ArrayList<Map.Entry<String,Long>>(sale.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String,Long>>() {
			public int compare(Entry<String,Long> entry1, Entry<String,Long> entry2) {
				return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
			}
		});
		BufferedWriter bw =null;
		try{
			File file = new File(dirPath, fileName);
			bw = new BufferedWriter(new FileWriter(file));
			for (Entry<String,Long> s : entries){
				bw.write(s.getKey() + "," + name.get(s.getKey()) + "," + s.getValue() + System.lineSeparator());
			}

		} catch(IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return false;
		} finally {
			if(bw != null){
				try{
					bw.close();
				} catch(IOException e) {
					System.out.println("予期せぬエラーが発生しました");
					return false;
				}
			}
		}
		return true;
	}

//メインメソッド
	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
		BufferedReader br = null;
		HashMap<String, String> branchname = new HashMap<String, String>();
		HashMap<String, String> commodityname = new HashMap<String, String>();
		HashMap<String, Long> branchsale = new HashMap<String, Long>();
		HashMap<String, Long> commoditysale = new HashMap<String, Long>();
		if(!fileIn(args[0],"branch.lst","支店","\\d{3}",branchname,branchsale)){
			return;
		}
		if(!fileIn(args[0],"commodity.lst","商品","\\w{8}",commodityname,commoditysale)){
			return;
		}
//売上集計
		try{
			File file = new File(args[0]);
			File fileAll[] = file.listFiles();
			List<File> filercd  = new ArrayList<File>();
			for(int i = 0;i < fileAll.length; i++){
				if(fileAll[i].isFile() && fileAll[i].getName().matches("\\d{8}.rcd") ){
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
				br = new BufferedReader(new FileReader(filercd.get(i)));
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
				if(!rcdRead.get(2).matches("^[0-9]+$")){
					System.out.println("予期せぬエラーが発生しました");
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
			return;
		} finally {
			if(br != null){
				try{
					br.close();
				} catch(IOException e) {
					System.out.println("予期せぬエラーが発生しました");
					return;
				}
			}
		}
		if(!fileOut(args[0],"branch.out",branchname, branchsale)){
			return;
		}
		if(!fileOut(args[0],"commodity.out",commodityname, commoditysale)){
			return;
		}
	}
}
