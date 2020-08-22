package Project.Team;

public class TeamListItem {
    private String teamName;
    private int max_designers;
    private int max_developers;
    private int apply_designers;
    private int apply_developers;
    public TeamListItem(String teamName, int max_designers, int max_developers, int apply_designers, int apply_developers){
        this.teamName = teamName;
        this.max_designers = max_designers;
        this.max_developers = max_developers;
        this.apply_designers = apply_designers;
        this.apply_developers = apply_developers;
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
}
