package loginpage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Login_page {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\Selenium\\chromedriver.exe");


		File file = new File("D:\\login.xls");

		FileInputStream inputStream = new FileInputStream(file);

		HSSFWorkbook wb = new HSSFWorkbook(inputStream);

		HSSFSheet sheet = wb.getSheet("login");

		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get("https://2hrdoorway.live1.dev.radixweb.net/login");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement username = driver.findElement(By.xpath("//*[contains(@formcontrolname,'email')]"));
		WebElement password = driver.findElement(By.xpath("//*[contains(@formcontrolname,'password')]"));
		WebElement signinbtn = driver.findElement(By.xpath("//button[contains(text(),'Sign In')]"));

		for(int i=1;i<=rowCount;i++) {

			Thread.sleep(1000);

			username.sendKeys(sheet.getRow(i).getCell(0).getStringCellValue());
			password.sendKeys(sheet.getRow(i).getCell(1).getStringCellValue());

			Thread.sleep(1000);

			signinbtn.click();

			WebElement confirmationMessage = driver.findElement(By.xpath("//div[contains(text(),'Login Successful.')]"));

			HSSFCell cell = sheet.getRow(i).createCell(2);

			if (confirmationMessage.isDisplayed()) {

				cell.setCellValue("PASS");

			} else {

				cell.setCellValue("FAIL");
			}
			FileOutputStream outputStream = new FileOutputStream("D:\\login.xls");
			wb.write(outputStream);

			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@class = 'icon feather icon-user']")).click();

			driver.findElement(By.xpath("//*[@class = 'feather icon-log-out']")).click();
		}
		wb.close();
		driver.quit();

	}

}
