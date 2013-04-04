package br.com.batuta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.batuta.events.Joinable;
import br.com.batuta.events.Produce;
import br.com.batuta.events.Task;

public class Batuta<T> {

	private static int DEFAULT_THREAD_POOL = 10;
	private int threads; 
	
	private List<Task> tasks = new ArrayList<Task>();
	private Joinable<T> joinable;
	
	public Batuta<T> add(Task task) {
		tasks.add(task);
		return this;
	}
	
	public Batuta<T> join(Joinable<T> joinable) {
		this.joinable = joinable;
		return this;
	}
	
	public T execute() {
		ExecutorService service = Executors.newFixedThreadPool(numbersOfThreads());
		
		final List<Produce<T>> produces = Collections.synchronizedList(new ArrayList<Produce<T>>());
		
		for (final Task task : tasks) {
			service.execute(new Runnable() {
				@Override
				@SuppressWarnings("unchecked")
				public void run() {
					produces.add((Produce<T>) task.produce());	
				}
			});
		}

		service.shutdown();
		waitTerminated(service);
		
		return joinable.join(produces);
	}

	private void waitTerminated(ExecutorService service) {
		while (!service.isTerminated());
	}
	
	private int numbersOfThreads() {
		return threads > 0 ? threads : DEFAULT_THREAD_POOL;
	}
}
