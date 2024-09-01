package com.github.commerce.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_infos")
public class UsersInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User users;

    @Column(name = "grade")
    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    @Size(max = 10)
    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @Size(max = 255)
    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Size(max = 255)
    @Column(name = "address_detail")
    private String addressDetail;

    @Size(max = 4)
    @NotNull
    @Column(name = "age", nullable = false)
    private String age;

    @Size(max = 255)
    @Column(name = "nickname")
    private String nickname;

}