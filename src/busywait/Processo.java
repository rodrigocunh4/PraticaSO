/**
 * @author Bruno Marques e Rodrigo Cunha
 * 
 * Sistemas Operacionais - 2016.1
 * Mini-simulador de processos usando busy wait.
 */

package busywait;

public class Processo extends Thread {

	private static int countID = 1;
	private int processoID;
	
	public Processo(){
		this.processoID = countID;
		countID++;
	}
	
	@Override
	public void run() {
		try {			
			// Alternancia escrita
			while (Main.regiaoCritica == false) {	// pergunta se eh a sua vez
													// se nao for, espera a sua vez
				sleep(1000);						// delay pra ver o que estah acontecendo
				System.out.println("ProcessoID "+ this.processoID +" esperando");
			}
			entrarRegiaoCritica();
			sairRegiaoCritica();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public int getProcessoID(){
		return this.processoID;
	}
	
	private void entrarRegiaoCritica() throws InterruptedException{
		Main.regiaoCritica = false;
		System.err.println("ProcessoID entrou na Regiao Critica: "+ this.processoID);
		sleep(5000);
	}
	
	private void sairRegiaoCritica(){
		Main.regiaoCritica = true;
		System.err.println("ProcessoID saiu da Regiao Critica: "+ this.processoID);
	}
	
}
