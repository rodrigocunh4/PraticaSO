/**
 * @author Bruno Marques e Rodrigo Cunha
 * 
 * Sistemas Operacionais - 2016.1
 * Mini-simulador de processos usando synchronized.
 */

package comunicacao.bloqueio;

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
			synchronized (Main.regiaoCritica) {
				entrarRegiaoCritica();
				sairRegiaoCritica();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public int getProcessoID(){
		return this.processoID;
	}
	
	private void entrarRegiaoCritica() throws InterruptedException{
		System.err.println("\nProcessoID entrou na Regiao Critica: "+ this.processoID);
		System.out.println("SEMAFORO FECHADO!");
		processar();
	}
	
	private void sairRegiaoCritica(){
		System.err.println("ProcessoID saiu da Regiao Critica: "+ this.processoID);
		System.out.println("SEMAFORO ABERTO!\n");
	}
	
	private void processar() throws InterruptedException{
		System.out.println("ProcessoID: "+ this.processoID +" executando...");
		sleep(5000);
	}
}
