package com.h4ad.ac1.services;

import java.util.ArrayList;
import java.util.List;

import com.h4ad.ac1.dto.EventDTO;
import com.h4ad.ac1.dto.EventInsertDTO;
import com.h4ad.ac1.dto.EventUpdateDTO;
import com.h4ad.ac1.entities.Event;
import com.h4ad.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

  @Autowired
  private EventRepository repository;

  public List<EventDTO> getEvents() {
    return toDTOList(repository.findAll());
  }

  public EventDTO createEvent(EventInsertDTO dto) {
    Event entity = new Event(dto);

    entity = repository.save(entity);

    return new EventDTO(entity);
  }

  public EventDTO updateEvent(Long eventId, EventUpdateDTO dto) {
    Event entity = repository.findById(eventId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O evento com essa identificação não foi encontrado."));

    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setPlace(dto.getPlace());
    entity.setEmailContact(dto.getEmailContact());
    entity.setStartDate(dto.getStartDate());
    entity.setEndDate(dto.getEndDate());
    entity.setStartTime(dto.getStartTime());
    entity.setEndTime(dto.getEndTime());

    entity = repository.save(entity);

    return new EventDTO(entity);
  }

  public List<EventDTO> toDTOList(List<Event> list) {
    List<EventDTO> listDTO = new ArrayList<>();

    for (Event event : list) {
      EventDTO dto = new EventDTO(event);

      listDTO.add(dto);
    }

    return listDTO;
  }

}
