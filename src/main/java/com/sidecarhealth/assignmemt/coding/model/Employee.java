package com.sidecarhealth.assignmemt.coding.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Employee {
    @Id
    @Column(name = "employee_id")
    @NotNull
    @ApiModelProperty(required = true)
    private String id;

    @Column(name = "name")
    @NotNull
    @ApiModelProperty(required = true)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
