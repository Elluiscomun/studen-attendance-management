package com.fighthub.attentance.model;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.Pattern;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+"
        +"(?:[ '-][A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+)*$", message = "{Invalid First Name}")
    @Nonnull
    private String firstName;
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+"
        +"(?:[ '-][A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+)*$", message = "{Invalid Last Name}")
    private String lastName;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
            + "(?:[a-z0-9-]*[a-z0-9])?",
            message = "{invalid.email}")
    private String email;
    @Nonnull
    @Pattern(regexp = "^3\\d{9}$", message = "{invalid.phone}") 
    private String phone;
    @OneToMany(mappedBy = "student")
    private Set<Attendance> attendances = new HashSet<Attendance>();
    @Column(unique = true)
    @Pattern(regexp = "^[1-9]\\d{5,9}$", message = "{invalid.identificationNumber}")
    private String identificationNumber;
    private boolean active = true;
    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public void disableStudent() {
        this.active = false;
    }

    public void update(Student updatedStudent) {
        this.firstName = updatedStudent.getFirstName();
        this.lastName = updatedStudent.getLastName();
        this.email = updatedStudent.getEmail();
        this.phone = updatedStudent.getPhone();
        this.identificationNumber = updatedStudent.getIdentificationNumber();
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
