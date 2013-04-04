package br.com.batuta.events;

public interface Task {

	<T> Produce<T> produce();
	
}
