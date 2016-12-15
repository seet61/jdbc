package ru.seet61.spring.jdbc;

/**
 * Схема доступа к данным проста: вы будете работать с такими данными, как имя и фамилия клиентов.
 * Created by seet on 15.12.16.
 */
public class Customer {
    private long id;
    private String firstName, lastName;


    public Customer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%s, firstName=%s, lastName=%s]", id, firstName, lastName);
    }

    //Можно добавить метоты getter's & setter's
}
