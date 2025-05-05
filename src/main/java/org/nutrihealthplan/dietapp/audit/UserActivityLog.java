package org.nutrihealthplan.dietapp.audit;

import jakarta.persistence.*;
import lombok.*;
import org.nutrihealthplan.dietapp.model.User;

import java.time.Instant;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_activity_log")
public class UserActivityLog{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id") // referencedColumnName = "id" by default from "user_id"
    User user;
    @Enumerated(EnumType.STRING)
    private UserActionType action;
    private Instant timestamp;
    private String metadata;
    @Version
    private Long version;

}