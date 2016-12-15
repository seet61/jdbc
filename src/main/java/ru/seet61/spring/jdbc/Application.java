package ru.seet61.spring.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Spring предоставляет шаблонный класс JdbcTemplate, который упрощает работу с SQL и JDBC реляционных СУБД.
 * Большая часть JDBC кода состоит из получения ресурсов, управления соединением, обработки исключений и вообще проверки ошибок,
 * ни как не связанных с тем, для чего предназначен код. JdbcTemplate берет на себя все это за вас.
 * Все, что остается вам сделать, это сосредоточиться на решении поставленной задачи.
 * Created by seet on 15.12.16.
 */
public class Application {
    public static void main(String[] args) {
        // простой DS для тестирования (не для реального использования!)
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setUrl("jdbc:h2:mem");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("Создаем таблицы");
        jdbcTemplate.execute("drop table customers if exists");
        jdbcTemplate.execute("create table customers (id serial, first_name varchar(255), last_name varchar(255))");

        String[] names = "John Woo;Jeff Dean;Josh Bloch;Josh Long".split(";");
        for (String fullname: names) {
            String[] name = fullname.split(" ");
            System.out.printf("Вставляем записи о %s %s%n", name[0], name[1]);
            jdbcTemplate.update("insert into customers(first_name, last_name) values (?, ?)", name[0], name[1]);
        }

        System.out.println("Выбираем все записи с first_name = 'Josh':");
        List<Customer> results = jdbcTemplate.query("select * from customers where first_name = ?", new Object[]{"John"},
                new RowMapper<Customer>() {
                    @Override
                    public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                        return new Customer(resultSet.getLong("id"), resultSet.getString("first_name"),
                                resultSet.getString("last_name"));
                    }
                }
        );

        for (Customer customer: results) {
            System.out.println(customer);
        }
    }
}
