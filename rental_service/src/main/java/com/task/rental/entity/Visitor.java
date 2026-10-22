package com.task.rental.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitors")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(length = 254)
    private String email;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Column(name = "terminated_at")
    private LocalDateTime terminatedAt;

    @Column(name = "has_disk", nullable = false)
    private boolean hasDisk;

    protected Visitor() {
    }

    public Visitor(
            String fullName,
            String phone,
            String email,
            LocalDateTime registeredAt
    ) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.registeredAt = registeredAt;
        this.hasDisk = false;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDateTime getTerminatedAt() {
        return terminatedAt;
    }

    public boolean isHasDisk() {
        return hasDisk;
    }

    public boolean isActive() {
        return terminatedAt == null;
    }

    public void terminate(LocalDateTime terminatedAt) {
        this.terminatedAt = terminatedAt;
    }

    public void issueDisk() {
        this.hasDisk = true;
    }

    public void returnDisk() {
        this.hasDisk = false;
    }
}