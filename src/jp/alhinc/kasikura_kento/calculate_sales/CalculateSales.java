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
	public static boolean fileRead(String dirPath,String fileName,String errorMessage,String regex,
			HashMap<String, String> name, HashMap<String, Long> sale){
		BufferedReader br = null;
		try {
			File file = new File(dirPath,fileName);
			if (!file.exists()) {
				System.out.println(errorMessage + "定義ファイルが存在しません");
				return false;
			}
			br = new BufferedReader(new FileReader(file));
			String s;
			while((s = br.readLine()) != null) {
				String[] nameSplit = s.split(",");
				if(!nameSplit[0].matches(regex) || nameSplit.length != 2) {
					System.out.println(errorMessage + "定義ファイルのフォーマットが不正です");
					return false;
				}
				name.put(nameSplit[0],nameSplit[1]);
				sale.put(nameSplit[0],0L);
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
	public static boolean  fileWrite(String dirPath, String fileName, HashMap<String, String> name, HashMap<String, Long> sale){
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
		HashMap<String, String> branchName = new HashMap<String, String>();
		HashMap<String, String> commodityName = new HashMap<String, String>();
		HashMap<String, Long> branchSale = new HashMap<String, Long>();
		HashMap<String, Long> commoditySale = new HashMap<String, Long>();
		if(!fileRead(args[0],"branch.lst","支店","\\d{3}",branchName,branchSale)){
			return;
		}
		if(!fileRead(args[0],"commodity.lst","商品","\\w{8}",commodityName,commoditySale)){
			return;
		}
//売上集計
		try{
			File file = new File(args[0]);
			File fileAll[] = file.listFiles();
			List<File> fileRcd  = new ArrayList<File>();
			for(int i = 0;i < fileAll.length; i++){
				if(fileAll[i].isFile() && fileAll[i].getName().matches("\\d{8}.rcd") ){
					fileRcd.add(fileAll[i]);
				}
			}
			for(int i = 1;i < fileRcd.size(); i++){
				int n = Integer.parseInt(fileRcd.get(i -1).getName().substring(0,8));
				int m = Integer.parseInt(fileRcd.get(i).getName().substring(0,8));
				if(m - n != 1){
					System.out.println( "売上ファイル名が連番になっていません");
					return;
				}
			}
			for(int i = 0;i < fileRcd.size();i++){
				br = new BufferedReader(new FileReader(fileRcd.get(i)));
				String s;
				List<String> rcdReadLine = new ArrayList<String>();
				while((s = br.readLine()) != null) {
					rcdReadLine.add(s);
				}
				if (rcdReadLine.size() != 3){
					System.out.println(fileAll[i].getName() + "のフォーマットが不正です");
					return;
				}
				if(branchName.get(rcdReadLine.get(0)) == null){
					System.out.println(fileAll[i].getName() + "の支店コードが不正です");
					return;
				}
				if(commodityName.get(rcdReadLine.get(1)) == null){
					System.out.println(fileAll[i].getName() + "の商品コードが不正です");
					return;
				}
				if(!rcdReadLine.get(2).matches("^[0-9]+$")){
					System.out.println("予期せぬエラーが発生しました");
					return;
				}
				Long branchSum = branchSale.get(rcdReadLine.get(0));
				branchSum += Long.parseLong(rcdReadLine.get(2));
				Long commoditySum = commoditySale.get(rcdReadLine.get(1));
				commoditySum += Long.parseLong(rcdReadLine.get(2));
				if(!String.valueOf( branchSum ).matches("\\d{1,10}") || !String.valueOf( commoditySum ).matches("\\d{0,10}")){
					System.out.println("合計金額が10桁を超えました");
					return;
				}
				branchSale.put(rcdReadLine.get(0), branchSum);
				commoditySale.put(rcdReadLine.get(1), commoditySum);
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
		if(!fileWrite(args[0],"branch.out",branchName, branchSale)){
			return;
		}
		if(!fileWrite(args[0],"commodity.out",commodityName, commoditySale)){
			return;
		}
	}
}
