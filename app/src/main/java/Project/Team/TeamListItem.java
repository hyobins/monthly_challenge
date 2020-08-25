package Project.Team;

public class TeamListItem {
    private String teamName;
    private int max_designers;
    private int max_developers;
    private int apply_designers;
    private int apply_developers;
    private String openchat_url;
    public TeamListItem(String teamName, int max_designers, int max_developers, int apply_designers, int apply_developers, String openchat_url){
        this.teamName = teamName;
        this.max_designers = max_designers;
        this.max_developers = max_developers;
        this.apply_designers = apply_designers;
        this.apply_developers = apply_developers;
        this.openchat_url = openchat_url;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public int getMax_designers() {
        return this.max_designers;
    }

    public int getMax_developers() {
        return this.max_developers;
    }

    public int getApply_designers() {
        return this.apply_designers;
    }

    public int getApply_developers() {
        return this.apply_developers;
    }

    public String getOpenchat_url() {
        return this.openchat_url;
    }

    public void setApply_developers(int apply_developers) {
        this.apply_developers = apply_developers;
    }

    public void setApply_designers(int apply_designers) {
        this.apply_designers = apply_designers;
    }
}
