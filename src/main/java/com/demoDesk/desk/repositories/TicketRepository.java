package com.demoDesk.desk.repositories;

import com.demoDesk.desk.models.queries.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    Ticket getTicketByTitle(String title);

    Ticket findFirstByOrderByIdDesc();
}
