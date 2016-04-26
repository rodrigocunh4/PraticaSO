/**
 * @author Bruno Marques e Rodrigo Cunha
 * 
 * Sistemas Operacionais - 2016.1
 * Mini-simulador de processos usando alternancia escrita.
 */

package comunicacao.busyWait;

public class Main {

	public static boolean regiaoCritica;
	
	public static void main(String[] args) {
		regiaoCritica = true;
		
		new Processo().start();
		new Processo().start();
		new Processo().start();
		new Processo().start();
		new Processo().start();
		
	}

}
