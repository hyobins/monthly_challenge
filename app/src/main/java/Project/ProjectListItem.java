package Project;
public class ProjectListItem {
    private String projectId;
    private String title;
    private String company;
    private String deadline;
    private String reward;
    private String direction;
    private String state;
    private String submit;
    private String description;

    public ProjectListItem(String projectId, String title, String company, String deadline, String reward, String direction, String state, String submit, String description) {
        this.projectId = projectId;
        this.title = title;
        this.company = company;
        this.deadline = deadline;
        this.reward = reward;
        this.direction = direction;
        this.state = state;
        this.submit = submit;
        this.description = description;
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

    public String getState() {
        return this.state;
    }

    public String getDirection() {
        return this.direction;
    }


    public String getSubmit() {
        return this.submit;
    }

    public String getDescription() {
        return this.description;
    }
}
