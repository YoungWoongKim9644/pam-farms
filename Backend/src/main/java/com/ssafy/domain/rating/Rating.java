
package com.ssafy.domain.rating;

import com.ssafy.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEWER_ID")
    private User reviewer;

    @Column
    private int score;

    @Column
    private String review;

    @Builder
    public Rating(User user, User reviewer, int score, String review) {
        this.user = user;
        this.reviewer = reviewer;
        this.score = score;
        this.review = review;
    }
}

