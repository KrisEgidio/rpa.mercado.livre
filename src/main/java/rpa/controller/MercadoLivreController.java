package rpa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import rpa.model.Product;

public class MercadoLivreController {
	
	private static Logger log = Logger.getLogger(MercadoLivreController.class);
	
	public void startFlow() {

		List<String> products = new ArrayList<>();
		products.add("Xiaomi Redmi note 9 pro");

		List<Product> productsOutput = buscarProdutos(products);
		
		ExcelController excelController = new ExcelController();
		excelController.writeOutputFile(productsOutput);
		
		
		for (Product product : productsOutput) {
			System.out.println(product.getNome());			
		}

	}
	
	private ChromeOptions getChromeOptions() {
		// manipula o navegador
		ChromeOptions options = new ChromeOptions();

		HashMap<String, Object> chromePreferences = new HashMap<String, Object>();

		// 0 = default, 1 = allow, 2 = block
		chromePreferences.put("profile.managed_default_content_settings.javascript", 0); // desabilita o js da pagina
		chromePreferences.put("profile.managed_default_content_settings.images", 2); // desabilita o carregamento deimagens na pagina
		chromePreferences.put("profile.managed_default_content_settings.stylesheets", 2); // desabilita o css da pagina
		chromePreferences.put("profile.managed_default_content_settings.geolocation", 2); // desabilita a localizacao
		chromePreferences.put("profile.managed_default_content_settings.cookies", 0); // desabilita os cookies da pagina
		chromePreferences.put("profile.managed_default_content_settings.media_stream", 0); // desabilita a midia
		chromePreferences.put("download.default_directory", "\\downloads\\pastaRobo"); // configura a pasta padrao de download daquela sessao
		chromePreferences.put("profile.default_content_setting_values.automatic_downloads", 1); // habilita downloadas em massa
		chromePreferences.put("profile.managed_default_content_settings.popups", 2); // desabilita popups
		chromePreferences.put("profile.managed_default_content_settings.notifications", 2); // desabilita notificacoes
		chromePreferences.put("profile.managed_default_content_settings.plugins", 2); // desabilita plugins

		// site da documenta��o: chromedriver.chromium.org/capabilities

		options.addArguments("--start-maximized"); // maximizado
		options.addArguments("--incognito"); // anonimo
		options.addArguments("--disable-extensions"); // desabilita as extens�es do chrome
		options.addArguments("--disable-default-apps"); // desabilita as plugins ou qualquer coisa relacionada ao chrome
		options.addArguments("--disable-infobars"); // desabilita a barra de informa��es
		options.addArguments("--headless"); //nao deixa o navegador visivel

		options.setExperimentalOption("prefs", chromePreferences);

		return options;
	}
	
	private List<Product> buscarProdutos(List<String> products){
		WebDriver driver = new ChromeDriver(getChromeOptions());
		List<Product> productsOutput = new ArrayList<>();
		products.add("Xiaomi Redmi note 9 pro");

		for (String productName : products) {
			driver.get("https://lista.mercadolivre.com.br/" + productName);

			// Resgatar valor
			List<WebElement> linksProdutos = driver.findElements(By.xpath(
					"//div[@class='ui-search-item__group ui-search-item__group--title']/a[@class='ui-search-item__group__element ui-search-link']"));
			List<String> urlProdutos = new ArrayList<>();

			// Extraindo url dos elementos
			for (WebElement webElement : linksProdutos) {
				urlProdutos.add(webElement.getAttribute("href"));
			}
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			
			// Acessando cada URL
			for (String currentUrl : urlProdutos) {
				driver.get(currentUrl);
				

				// utilizando o jspath
				String nomeProduto = (String) js.executeScript("return document.querySelector('h1[class=ui-pdp-title]').textContent");
				String precoProduto = (String) js.executeScript("return document.querySelector('div[class=ui-pdp-price__second-line] span span[class=price-tag-fraction]').firstChild.textContent");

				// utilizando nativamente a captura de informações com selenium
				// String nomeProduto = driver.findElement(By.xpath("//h1[@class='ui-pdp-title']")).getText();
				// String precoProduto = driver.findElement(By.xpath("//div[@class='ui-pdp-price__second-line']/span//span[@class='price-tag-fraction']")).getText();
				
				Product product = new Product();
				
				product.setNome(nomeProduto);
				product.setPreco(precoProduto);
				product.setUrl(currentUrl);
				
				productsOutput.add(product);
				
				log.info(nomeProduto + " adicionado na lista");
			
			}

		}
		
		driver.close();
		driver.quit();
		
		return productsOutput;
	}
}
