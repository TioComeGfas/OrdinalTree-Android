package cl.tiocomegfas.ubb.loud.backend.model;

import cl.tiocomegfas.ubb.loud.backend.constants.Sex;

public class Person {
    private int id;
    private String name;
    private String lastName;
    private Sex sex;

    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    //Patron Builder
    public static class Builder{
        private int id;
        private String name;
        private String lastName;
        private Sex sex;

        public Builder(){ }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setSex(Sex sex) {
            this.sex = sex;
            return this;
        }

        public Person build(){
            Person person = new Person();
            person.setId(id);
            person.setName(name);
            person.setLastName(lastName);
            person.setSex(sex);
            return person;
        }
    }
}
