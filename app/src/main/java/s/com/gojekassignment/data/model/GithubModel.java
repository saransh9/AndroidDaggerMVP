package s.com.gojekassignment.data.model;

public class GithubModel {
    private String forks;

    private BuiltBy[] builtBy;

    private String author;

    private String name;

    private String description;

    private String language;

    private String languageColor;

    private String stars;

    private String url;

    private String currentPeriodStars;

    private boolean isChildVisible = false;

    public boolean isChildVisible() {
        return isChildVisible;
    }

    public void setChildVisible(boolean childVisible) {
        isChildVisible = childVisible;
    }

    public String getForks() {
        return forks;
    }

    public void setForks(String forks) {
        this.forks = forks;
    }

    public BuiltBy[] getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(BuiltBy[] builtBy) {
        this.builtBy = builtBy;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(String currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    @Override
    public String toString() {
        return "ClassPojo [forks = " + forks + ", builtBy = " + builtBy + ", author = " + author + ", name = " + name + ", description = " + description + ", language = " + language + ", languageColor = " + languageColor + ", stars = " + stars + ", url = " + url + ", currentPeriodStars = " + currentPeriodStars + "]";
    }
}