package com.dosol.securitydemo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity(name = "tbl_board")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    private String title;
    private String writer;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regdate") //이거 필요한가? 어차피 이름이 regdate인데;;
    private Date regdate;

    @ColumnDefault("0") //이거 안적으면 null값이 들어가니까 디폴트로 잡아주는거임.
    private Long hitcount;

    //엮는거를 MTO에 해줘야한다? 왜냐하면 와따가따 이상하게 될 수 있으니까.
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            optional = true)
    @JoinColumn(name = "user_id") //명시적으로 적어주는거임.
    private User user;

    public void updateHitcount() {
        this.hitcount = this.hitcount + 1;
    }
}
