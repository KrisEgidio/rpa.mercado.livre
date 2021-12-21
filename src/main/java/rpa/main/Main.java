package rpa.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import rpa.model.Product;

public class Main {

	public static void main(String[] args) {
		// Init
		System.setProperty("webdriver.chrome.driver", "resources\\chromedriver.exe");
		startRobot();
	}

	private static void startRobot() {
		WebDriver driver = new ChromeDriver(getChromeOptions());

		List<Product> products = new ArrayList<>();

		products.add(new Product("Celular", "", ""));
		products.add(new Product("Notebook", "", ""));
		products.add(new Product("Tv", "", ""));

		for (Product product : products) {
			driver.get("https://lista.mercadolivre.com.br/" + product.getNome());

			// Resgatar valor
			List<WebElement> linksProdutos = driver.findElements(By.xpath(
					"//div[@class='ui-search-item__group ui-search-item__group--title']/a[@class='ui-search-item__group__element ui-search-link']"));
			List<String> urlProdutos = new ArrayList<>();

			// Extraindo url dos elementos
			for (WebElement webElement : linksProdutos) {
				urlProdutos.add(webElement.getAttribute("href"));
			}

			// Acessando cada URL
			for (String currentUrl : urlProdutos) {
				driver.get(currentUrl);

				String nomeProduto = driver.findElement(By.xpath("//h1[@class='ui-pdp-title']")).getText();
				String precoProduto = driver
						.findElement(By.xpath(
								"//div[@class='ui-pdp-price__second-line']/span//span[@class='price-tag-fraction']"))
						.getText();

				System.out.println(nomeProduto + " - " + precoProduto);
			}

		}

		driver.close();
		driver.quit();
	}

	private static ChromeOptions getChromeOptions() {
		// manipula o navegador
		ChromeOptions options = new ChromeOptions();

		HashMap<String, Object> chromePreferences = new HashMap<String, Object>();

		// 0 = default, 1 = allow, 2 = block
		chromePreferences.put("profile.managed_default_content_settings.javascript", 0); // desabilita o js da p�gina
		chromePreferences.put("profile.managed_default_content_settings.images", 2); // desabilita o carregamento deimagens na p�gina
		chromePreferences.put("profile.managed_default_content_settings.stylesheets", 2); // desabilita o css da p�gina
		chromePreferences.put("profile.managed_default_content_settings.geolocation", 2); // desabilita a localiza��o
		chromePreferences.put("profile.managed_default_content_settings.cookies", 0); // desabilita os cookies da p�gina
		chromePreferences.put("profile.managed_default_content_settings.media_stream", 0); // desabilita a m�dia
		chromePreferences.put("download.default_directory", "\\downloads\\pastaRobo"); // configura a pasta padr�o de download daquela sess�o
		chromePreferences.put("profile.default_content_setting_values.automatic_downloads", 1); // habilita downloadas em massa
		chromePreferences.put("profile.managed_default_content_settings.popups", 2); // desabilita popups
		chromePreferences.put("profile.managed_default_content_settings.notifications", 2); // desabilita notifica��es
		chromePreferences.put("profile.managed_default_content_settings.plugins", 2); // desabilita plugins

		// site da documenta��o: chromedriver.chromium.org/capabilities

		options.addArguments("--start-maximized"); // maximizado
		options.addArguments("--incognito"); // an�nimo
		options.addArguments("--disable-extensions"); // desabilita as extens�es do chrome
		options.addArguments("--disable-default-apps"); // desabilita as plugins ou qualquer coisa relacionada ao chrome
		options.addArguments("--disable-infobars"); // desabilita a barra de informa��es
		// options.addArguments("--headless"); //n�o deixa o navegador vis�vel

		options.setExperimentalOption("prefs", chromePreferences);

		return options;
	}
}
