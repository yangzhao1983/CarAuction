package zy.auction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlGenerator {

	/**
	 * Launcher the generator
	 * 
	 * @param strings
	 */
	public static void main(String... strings) {
		// 1. get the car info.
		List<Car> list = CarInfoGenerator.generate();

		// 2. output hmtl file
		String filePath = "/Users/kaiser_zhao/Sites/car.html";
		try {
			FileWriter fw = new FileWriter(filePath);

			BufferedWriter bufw = new BufferedWriter(fw);
			bufw.write("<html><body><h1>It works! Changed!</h1>");
			bufw.write("<table border=\"1\"><tr><th>名称</th><th>价格</th><th>日期</th><th>已出售</th><th>参拍人数</th></tr>");
			for(Car car: list){
				String line = getStringCarIfo(car);
				bufw.write(line);
			}
			bufw.write("</table>");
			bufw.write("</body></html>");
			bufw.flush();
			bufw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static String getStringCarIfo(Car car){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<a href=\"");
		sb.append(car.getUrl());
		sb.append("\">");
		sb.append(car.getName());
		sb.append("</a>");
		sb.append("</td>");
		sb.append("<td>");
		sb.append(car.getPrice());
		sb.append("</td>");
		sb.append("<td>");
		sb.append(car.getSignDate());
		sb.append("</td>");
		sb.append("<td>");
		sb.append(car.isSold());
		sb.append("</td>");
		sb.append("<td>");
		if(car.isSold()){
			sb.append(car.getActionN());
		}
		sb.append("</td>");
		sb.append("</tr>");
		return sb.toString();
	}
}
