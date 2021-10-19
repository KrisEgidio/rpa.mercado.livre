package rpa;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "resources\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.mercadolivre.com.br/");
		
		// //div/form[@class='nav-search']/input
		
		driver.findElement(By.xpath("//div/form[@class='nav-search']/input")).sendKeys("Celular");
		driver.findElement(By.xpath("//form[@class='nav-search']/button[@class='nav-search-btn']")).click();
		
		// Resgatar valor
		List<WebElement> linksProdutos = driver.findElements(By.xpath("//div[@class='ui-search-item__group ui-search-item__group--title']/a[@class='ui-search-item__group__element ui-search-link']"));
		for (WebElement webElement : linksProdutos) {
			System.out.println(webElement.getAttribute("href"));
		}
		
	}

}
