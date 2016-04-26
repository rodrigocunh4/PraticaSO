/**
 * @author Bruno Marques e Rodrigo Cunha
 * 
 * Sistemas Operacionais - 2016.1
 * Mini-simulador de processos usando round robin.
 */

package escalonamento.roundRobin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static long tempoLimite = 5000L;
	public static List<Processo> filaProcessos = new ArrayList<Processo>();
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L));
		
		Processo p = null;
		Thread t = null;
		try {
			printFilaProcessos();
			
			while ( (p = getProcesso()) != null) {
				System.out.println(p.toString());
				t = new Thread(p);
				t.start();
				if(p.getProcessoTempo() >= tempoLimite){
					Thread.sleep(Main.tempoLimite);
					p.kill();
					if(p.getProcessoTempo() - Main.tempoLimite > 0L){
						p.setProcessoTempo(p.getProcessoTempo() - Main.tempoLimite);
						Main.addProcesso(p);
						System.err.println("ProcessoID: "+ p.getProcessoID() +" voltou para a fila de processos");
					}else{
						System.err.println("ProcessoID: "+ p.getProcessoID() +" finalizado");	
					}
					
				}else if(p.getProcessoTempo() < Main.tempoLimite && p.getProcessoTempo() > 0L){
					Thread.sleep(p.getProcessoTempo());
					p.kill();
					System.err.println("ProcessoID: "+ p.getProcessoID() +" finalizado");
				}				
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void addProcesso(Processo p){
			filaProcessos.add(p);
	}
	
	public static Processo getProcesso(){
		Processo p = null;
		if(filaProcessos.isEmpty()){
			return null;
		}
		p = filaProcessos.get(0);
		filaProcessos.remove(0);
		
		return p;
	}
	
	public static void printFilaProcessos(){
		System.out.println("\n\nTamanho da fila: "+ filaProcessos.size());
		for (Processo processo : filaProcessos) {
			System.out.println(processo.toString());
		}
		System.out.println("\n\n");
	}

}
