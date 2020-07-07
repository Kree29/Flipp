package com.traininghub.io.selenium;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Flipp {
	static String[] listlink;
	static String[] pricelist;
	static String[] Products;
	private static final String FILE_NAME = "FlippCheese.xlsx";
	 	        


	public static void main(String[] args) throws InterruptedException {
		
			XSSFWorkbook workbook = new XSSFWorkbook();
        				
        	Scanner read = new Scanner(System.in);
        	System.out.println("Enter the first product");
        	String Products = read.next();
			System.setProperty("webdriver.chrome.driver", "/Users/allocen27/WebDriver/chromedriver");
			
			WebDriver driver = new ChromeDriver();			
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
			driver.get("https://flipp.com/home");
			//Thread.sleep(3000);
			
			driver.manage().window().maximize();
	       	driver.findElement(By.xpath("//*[@name='postal_code']")).sendKeys("M9W7B9");						
			driver.findElement(By.xpath("//*[@name='postal_code']")).sendKeys(Keys.ENTER);						
			//Thread.sleep(5000);
			driver.findElement(By.xpath("//*[@class='sl-button']")).click();					     		    
	
			driver.findElement(By.xpath("//*[@title='Add an item']")).sendKeys(Products);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@title='Add an item']")).sendKeys(Keys.ENTER);
			WebElement OfferBtn = (new WebDriverWait(driver,30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='matchups-drillin']")));
			if(OfferBtn.isEnabled())
			{
				driver.findElement(By.xpath("//div[@class='accessories']")).click();
	            Thread.sleep(4000);
				
			}			
			XSSFSheet sheet = workbook.createSheet(Products);

			List <WebElement> items = driver.findElements(By.xpath("//div[@class='item-block']//a[@source-action='search_flyer_item_click']"));	        
			
	        listlink = new String[items.size()+1];	       
	        for(int i = 0; i < 5; i++)
	        {	
	        	//System.out.println(i);
	        	listlink[i] = "https://www.flipp.com" + driver.findElements(By.xpath("//a[@source-action='search_flyer_item_click']")).get(i).getAttribute("href");
	        	System.out.println("Link List"+listlink[i]);	
	        }
	        
	        int rowNum = 0;
	        for (String s : listlink) {
	        	Row row = sheet.createRow(rowNum);
				if(s != null) {
					int colNum = 0;				
	        	for (String text : getPriceofItem(driver, s)) {
	        		System.out.println("value of text:- "+ text);
	        		Cell cell = row.createCell(colNum++);
	        		cell.setCellValue(text);
	        	}
	        		
				}
				
			}
	        driver.quit();
	      
	        
	        try {
	            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }	     
	} 
	        	    
		private static String[] getPriceofItem(WebDriver driver, String s) throws InterruptedException  {		
			
			String[] valueDescription =new String[2];
			
			driver.get(s);
			
			if(driver.findElements(By.xpath("//flipp-price")).size() !=0)	{
			valueDescription[0] = (driver.findElement(By.xpath("//*[@content-slot='title']")).getText());
			valueDescription[1] = (driver.findElement(By.xpath("//flipp-price")).getAttribute("Value"));									
		        	 
				}
		return valueDescription;			 					 								       
	  }
}


								
							
						
	
		

	        	
				
		

				
			
			

	


