package Profile;

import java.io.Serializable;

public class IndividualItem implements Serializable {
    private String uid;
    private String email;
    private String interest;
    private String introduce;
    private String name;
    private String position;
    private String profile_url;
    private String virtual_account;

    public IndividualItem(String uid, String email, String interest, String introduce, String name, String position, String profile_url, String virtual_account){
        this.uid = uid;
        this.email = email;
        this.interest = interest;
        this.introduce = introduce;
        this.name = name;
        this.position = position;
        this.profile_url = profile_url;
        this.virtual_account = virtual_account;
    }

    public String getEmail() {
        return this.email;
    }

    public String getInterest() {
        return this.interest;
    }

    public String getIntroduce() {
        return this.introduce;
    }

    public String getName() {
        return this.name;
    }

    public String getUid() {
        return this.uid;
    }

    public String getPosition() {
        return this.position;
    }

    public String getProfile_url() {
        return this.profile_url;
    }

    public String getVirtual_account() {
        return this.virtual_account;
    }
}
