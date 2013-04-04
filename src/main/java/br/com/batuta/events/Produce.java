package br.com.batuta.events;

public class Produce<T> {

	private T value;

	public Produce(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
