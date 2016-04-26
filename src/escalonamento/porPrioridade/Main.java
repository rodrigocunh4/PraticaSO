/**
 * @author Bruno Marques e Rodrigo Cunha
 * 
 * Sistemas Operacionais - 2016.1
 * Mini-simulador de processos usando round robin.
 */

package escalonamento.porPrioridade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static long tempoLimite = 5000L;
	public static List<Processo> filaProcessos = new ArrayList<Processo>();
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.baixa));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.normal));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.baixa));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.alta));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.baixa));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.alta));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.baixa));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.normal));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.alta));
		addProcesso(new Processo((rand.nextInt(10)+1)*1000L, EPrioridade.alta));
		
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
		if(filaProcessos.isEmpty()){	// adiciona na fila se for vazia								
			filaProcessos.add(p);
			
		}else if(p.getPrioridade() == EPrioridade.alta){	// se nao for vazia e o processo tiver alta prioridade
			for (int i=0; i<filaProcessos.size(); i++) {	// percorra a fila e adicione na ultima posicao dos processos de alta prioridade
				if(filaProcessos.get(i).getPrioridade() != EPrioridade.alta){	
					if(i==0){
						filaProcessos.add(0, p);
					}else{
						filaProcessos.add(i, p);
					}
					break;
				}else if(filaProcessos.size()-1 == i){
					filaProcessos.add(p);
					break;
				}
			}
			
		}else if(p.getPrioridade() == EPrioridade.normal){	// se nao for vazia e o processo tiver prioridade normal
			for (int i=0; i<filaProcessos.size(); i++) {	// percorra a fila e adicione na ultima posicao dos processos de prioridade normal
				if(filaProcessos.get(i).getPrioridade() == EPrioridade.baixa){
					if(i==0){
						filaProcessos.add(0, p);
					}else{
						filaProcessos.add(i-1, p);
					}
					break;
				}else if(filaProcessos.size()-1 == i){
					filaProcessos.add(p);
					break;
				}
			}
			
		}else{												// se nao for vazia e o processo tiver prioridade baixa
			filaProcessos.add(p);							// adicione no final da fila
		}
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
