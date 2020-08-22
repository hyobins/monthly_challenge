package Project;

public class EndListItem {
    private String projectId;
    private String title;
    private String deadline;
    private String reward;
    public EndListItem(String projectId, String title, String deadline, String reward) {
        this.projectId = projectId;
        this.title = title;
        this.deadline = deadline;
        this.reward = reward;
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
