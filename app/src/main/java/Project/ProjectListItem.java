package Project;
public class ProjectListItem {
    private String projectId;
    private String title;
    private String company;
    private String deadline;
    private String reward;
    public ProjectListItem(String projectId, String title, String company, String deadline, String reward) {
        this.projectId = projectId;
        this.title = title;
        this.company = company;
        this.deadline = deadline;
        this.reward = reward;
    }

    public String getCompany() {
        return this.company;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public String getReward() {
        return this.reward;
    }
}
