package zy.auction;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Generate car info
 * 
 * @author kaiser_zhao
 *
 */
public class CarInfoGenerator {
	
	private static String[] seeked = {"2","3","4","5","6","7","8","9"};
	private static WebDriver driver;
	private static String baseUrl = "http://ssxkc.cbex.com.cn/jcas/auctionlist";
	 
	public static void main(String...strings){
		// generate();
		Car car = new Car();
		
		System.setProperty("webdriver.chrome.driver", "/Users/kaiser_zhao/Work/idcs_driver/chromedriver");
//		System.setProperty("webdriver.safari.noinstall", "true");
//		driver = new SafariDriver();
		driver = new ChromeDriver();
		car.setUrl("http://ssxkc.cbex.com.cn/jcas/auction/639310");
		setDetail4Car(car);
		driver.close();
	}
	public static List<Car> generate(){
		List<Car> list = new ArrayList<Car>();
		// scan page
		scanPages(list);
		// scan item
		return list;
	}
	
	private static void scanPages(List<Car> list){
		// 1. click every page link
		System.setProperty("webdriver.chrome.driver", "/Users/kaiser_zhao/Work/idcs_driver/chromedriver");
		driver = new ChromeDriver();
	    driver.get(baseUrl);
	    
	    // 2. for the first page
	    scanItems(list);

	    // 3. for page 2-9

	    for(String index : seeked){
	    	WebElement element = driver.findElement(By.xpath("//div[@class='page']//span[text()='"+index+"']"));
	    	print(element.getText());
	    	element.click();
	    	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    scanItems(list);
	    }
	    
	    for (Car c : list) {
			setDetail4Car(c);
		}
		driver.close();
	}
	
	private static void scanItems(List<Car> list){
		// 1. click every item
		List<WebElement> elements = driver.findElements(By.xpath("//article[@class='list']/ul/li"));
		for(WebElement element : elements){
			String line = element.findElement(By.tagName("p")).getText();
			Car car = scanPage(line);
			String href = (element.findElement(By.tagName("a"))).getAttribute("href");
			car.setUrl(href);
			
			list.add(car);
		}
	}
	
	private static Car scanPage(String strCar){
		Car car = new Car();
		String[] strs = strCar.split("\n");
		car.setName(strs[1]);
		car.setPrice(strs[3]);
		car.setSold(isSold(strs[4]));
		return car;
	}
	
	private static void print(String str){
		System.out.println(str);	
	}
	
	private static boolean isSold(String signDate){
		if(signDate.contains("2017")){
			return false;
		}else{
			return true;
		}
	}
	
	private static void setDetail4Car(Car car){
		String url = car.getUrl();
		if(url.contains("639310")){
			return;
		}
		//print(url);
		driver.get(url);
		
		//*[@id="tab1"]/table/tbody/tr[9]/td/p[2]
		//*[@id="tab1"]/div[1]/table/tbody/tr[9]/td/p[2]
		//*[@id="tab1"]/div[1]/table/tbody/tr[8]/td/p[2]/span/span/font/span[2]
		//*[@id="tab1"]/div[1]/table/tbody/tr[9]/td/p[3]
		//*[@id="tab1"]/div[1]/table/tbody/tr[9]/td/p[3]
		//*[@id="tab1"]/div[1]/table/tbody/tr[9]/td/p[3]
		WebElement e = null;

		try {
			e = driver.findElement(By.xpath("//*[@id='tab1']//table/tbody/tr[9]/td/p[2]"));
		} catch (Exception e1) {
			print("failed to get detail info for " +url);
			e = driver.findElement(By.xpath("//*[@id='tab1']/div[1]/table/tbody/tr[8]/td/p[2]/span/span/font/span[2]"));
		}
		String date = e.getText();
		if(null == date || "".equals(date.trim())){
			try {
				e = driver.findElement(By.xpath("//*[@id='tab1']/div[1]/table/tbody/tr[9]/td/p[3]"));
				date = e.getText();
			} catch (Exception e1) {
				print("failed to get detail info for " +url);
				e = driver.findElement(By.xpath("//*[@id='tab1']/div[1]/table/tbody/tr[8]/td/p[2]/span/span/font/span[2]"));
			}
		}
		car.setSignDate(date);

		if(car.isSold() && ! url.contains("592630")){
			WebElement number = driver.findElement(By.xpath("//*[@id='content']/aside[2]/article/div[4]/h3/strong"));
			car.setActionN(number.getText());
		}
		
		//print(e.getText());
	}
}
