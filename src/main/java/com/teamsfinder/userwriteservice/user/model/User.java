package com.teamsfinder.userwriteservice.user.model;

 import com.teamsfinder.userwriteservice.tag.model.Tag;
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
 import javax.validation.constraints.NotNull;
 import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private UUID keyCloakId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String githubProfileUrl;
    private String profilePictureUrl;
    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
