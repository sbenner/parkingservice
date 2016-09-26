package com.parking.service.persistence.repository;

import com.parking.service.common.dto.model.Total;
import com.parking.service.persistence.model.ClientInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Client: sbenner
 * Date: 5/4/16
 * Time: 6:26 AM
 */
@Repository
public interface ClientInvoiceRepository extends JpaRepository<ClientInvoice, Long>, QueryDslPredicateExecutor<ClientInvoice> {

    List<ClientInvoice> getClientInvoiceByClientIdAndParkingMonth(Long clientId,int month);

    List<ClientInvoice> getClientInvoiceByClientId(Long clientId);

    @Query(value = "select new com.parking.service.common.dto.model.Total(ci.parkingMonth,sum(ci.total)) from ClientInvoice ci where ci.client.id=?1 and ci.parkingMonth=?2 group by ci.client.id,ci.parkingMonth")
    Total getTotalForClient(Long clientId,int month);

    @Query(value = "select new com.parking.service.common.dto.model.Total(ci.parkingMonth,sum(ci.total)) from ClientInvoice ci where ci.client.id=?1 group by ci.client.id,ci.parkingMonth")
    List<Total> getTotalsForClient(Long clientId);
}
