package rpa.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import rpa.controller.MercadoLivreController;

public class Main {

	private static Logger log = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {		
		System.setProperty("webdriver.chrome.driver", "resources\\chromedriver.exe");
		PropertyConfigurator.configure("resources\\log4j.properties");
		
		log.info("Iniciando da automação");
		
		MercadoLivreController mercadoLivreController = new MercadoLivreController();
		mercadoLivreController.startFlow();
		
		log.info("Fim da automação");
	}

}
