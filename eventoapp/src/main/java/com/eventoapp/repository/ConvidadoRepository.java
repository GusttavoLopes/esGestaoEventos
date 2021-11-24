package com.eventoapp.repository;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;

import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {

	Iterable<Convidado> findByEvento(Evento evento); // Método para buscar a lista de convidados pelo Evento
	Convidado findByRg(String rg); // Método para buscar o convidados pelo rg
}
