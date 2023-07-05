package com.demoDesk.desk.models.personel;

import com.demoDesk.desk.models.Enums.EmployeeStatus;
import com.demoDesk.desk.models.queries.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "Employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;

    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks;
//
//    @Column(name = "status")
//    private EmployeeStatus status;

    public String getDepartmentTitle() {
        return department.getTitle();
    }
}
