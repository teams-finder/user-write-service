package com.teamsfinder.userwriteservice.user.model;

 import com.teamsfinder.userwriteservice.tag.model.Tag;
 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Getter;
 import lombok.NoArgsConstructor;
 import lombok.Setter;

 import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
 import javax.persistence.Table;
 import javax.validation.constraints.NotEmpty;
 import javax.validation.constraints.NotNull;
 import java.util.ArrayList;
 import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String keyCloakId;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AccountType accountType = AccountType.USER;
    private String githubProfileUrl;
    private String profilePictureUrl;
    private boolean blocked;
    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
}
