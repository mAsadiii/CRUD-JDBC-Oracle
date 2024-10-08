package org.example.repository.impl;

import org.example.entity.Person;
import org.example.repository.PersonRepository;
import org.example.repository.base.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepoJdbcImpl implements PersonRepository {

    @Override
    public Person save(Person person) {

        try (DBConnect dbConnect = new DBConnect()){
            Connection con = dbConnect.getConnection();

            //get id
            Long id = null;
            Statement stm = con.createStatement();
            ResultSet idResultSet = stm.executeQuery("select PERSON_SEQ.nextval from dual");
            if(idResultSet.next())
                id = idResultSet.getLong(1);
            else throw new RuntimeException("id seq error!");
            stm.close();

            //save
            person.setId(id);
            PreparedStatement psm = con.prepareStatement(
                    "insert into PERSON values (?,?,?,?,?,?,?)"
            );
            psm.setLong(1, person.getId());
            psm.setString(2, person.getName());
            psm.setString(3, person.getFamily());
            psm.setDate(4, Date.valueOf(person.getBirthDate()));
            psm.setString(5, person.getEmail());
            psm.setString(6, person.getPhoneNumber());
            psm.setString(7, person.getAddress());

            psm.executeUpdate();
            return person;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Long id, Person newPerson) {
        try (DBConnect dbConnect = new DBConnect()){
            Connection con = dbConnect.getConnection();
            PreparedStatement psm = con.prepareStatement(
                    "update PERSON set NAME=?, FAMILY=?, BIRTH_DATE=?, EMAIL=?, PHONE=?, ADDRESS=? where ID=?"
            );
            psm.setString(1, newPerson.getName());
            psm.setString(2, newPerson.getFamily());
            psm.setDate(3, Date.valueOf(newPerson.getBirthDate()));
            psm.setString(4, newPerson.getEmail());
            psm.setString(5, newPerson.getPhoneNumber());
            psm.setString(6, newPerson.getAddress());
            psm.setLong(7, id);

            int result = psm.executeUpdate();
            return (result == 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (DBConnect dbConnect = new DBConnect()){
            Connection con = dbConnect.getConnection();
            PreparedStatement stm = con.prepareStatement("delete from person where id = ?");
            stm.setLong(1, id);
            int result = stm.executeUpdate();
            return result == 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();

        try (DBConnect dbConnect = new DBConnect()) {
            Connection con = dbConnect.getConnection();

            PreparedStatement stm = con.prepareStatement("select * from PERSON");
            ResultSet set = stm.executeQuery();
            while (set.next()){
                long id = set.getLong(1);
                String name = set.getString(2);
                String family = set.getString(3);
                Date date = set.getDate(4);
                String email = set.getString(5);
                String phone = set.getString(6);
                String address = set.getString(7);
                Person p = new Person(id, name, family, date.toLocalDate(), email, phone, address);
                personList.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return personList;
    }

    @Override
    public Person findById(Long id) {
        try (DBConnect dbConnect = new DBConnect()) {
            Connection con = dbConnect.getConnection();
            PreparedStatement stm = con.prepareStatement("select * from PERSON where ID = ?");
            stm.setLong(1, id);
            ResultSet set = stm.executeQuery();
            while (set.next()){
                Long id1 = set.getLong(1);
                String name = set.getString(2);
                String family = set.getString(3);
                Date date = set.getDate(4);
                String email = set.getString(5);
                String phone = set.getString(6);
                String address = set.getString(7);
                return new Person(id1, name, family, date.toLocalDate(), email, phone, address);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Person findByNameAndFamily(String name, String family) {
        try (DBConnect dbConnect = new DBConnect()) {
            Connection con = dbConnect.getConnection();
            PreparedStatement stm = con.prepareStatement("select * from PERSON where NAME = ? and FAMILY = ?");
            stm.setString(1, name);
            stm.setString(2,family);
            ResultSet set = stm.executeQuery();
            while (set.next()){
                Long id = set.getLong(1);
                String name1 = set.getString(2);
                String family1 = set.getString(3);
                Date date = set.getDate(4);
                String email = set.getString(5);
                String phone = set.getString(6);
                String address = set.getString(7);
                return new Person(id, name1, family1, date.toLocalDate(), email, phone, address);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Person findByPhoneNum(String phoneNum) {
        try (DBConnect dbConnect = new DBConnect()) {
            Connection con = dbConnect.getConnection();
            PreparedStatement stm = con.prepareStatement("select * from PERSON where PHONE = ?");
            stm.setString(1, phoneNum);
            ResultSet set = stm.executeQuery();
            while (set.next()){
                Long id = set.getLong(1);
                String name = set.getString(2);
                String family = set.getString(3);
                Date date = set.getDate(4);
                String email = set.getString(5);
                String phone = set.getString(6);
                String address = set.getString(7);
                return new Person(id, name, family, date.toLocalDate(), email, phone, address);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }
}
