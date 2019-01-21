package com.outfittery.appointmentbooking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name="APPOINTMENT",
        uniqueConstraints= {
                @UniqueConstraint(columnNames={"SESSION_START", "stylist_id"}),
                @UniqueConstraint(columnNames={"SESSION_START", "customer_id"})
        }
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "SESSION_START")
    private LocalDateTime sessionStart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stylist_id")
    private Stylist stylist;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Appointment() {
    }

    public Appointment(LocalDateTime sessionStart, Stylist stylist, Customer customer) {
        this.sessionStart = sessionStart;
        this.stylist = stylist;
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(LocalDateTime sessionStart) {
        this.sessionStart = sessionStart;
    }

    public Stylist getStylist() {
        return stylist;
    }

    public void setStylist(Stylist stylist) {
        this.stylist = stylist;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
