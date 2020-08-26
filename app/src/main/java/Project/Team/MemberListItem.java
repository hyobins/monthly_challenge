package Project.Team;

public class MemberListItem {
    private String position;
    private String name;
    private String email;
    private String profile;
    private String introduce;

    public MemberListItem(String position, String name, String email, String profile, String introduce){
        this.position = position;
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.introduce = introduce;
    }

    public String getMemberPosition() {
        return position;
    }

    public String getMemberName(){
        return name;
    }

    public String getMemberEmail() {
        return email;
    }

    public String getMemberProfile() {
        return profile;
    }

    public String getMemberIntroduce() {
        return introduce;
    }
}
