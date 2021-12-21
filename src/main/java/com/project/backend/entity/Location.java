package com.project.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "location")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Location extends BaseEntity {

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "owner_name")
    String ownerName;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<History> histories = new ArrayList<>();

}
