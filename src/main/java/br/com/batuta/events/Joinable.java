package br.com.batuta.events;

import java.util.List;

public interface Joinable<T> {

	T join(List<Produce<T>> produces);

}
	