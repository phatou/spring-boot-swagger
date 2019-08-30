package com.example.springbootswagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ApiModel(description = "All details about the Employee.")
@Getter
@Setter
@ToString
@Entity
@Table(name = "employees")
public class Employee {

  @ApiModelProperty(notes = "The database generated employee ID")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ApiModelProperty(notes = "The employee first name")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @ApiModelProperty(notes = "The employee last name")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @ApiModelProperty(notes = "The employee email")
  @Column(name = "email_address", nullable = false)
  private String email;

  public Employee(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }
}
