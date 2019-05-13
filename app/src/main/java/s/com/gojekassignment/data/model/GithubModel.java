package s.com.gojekassignment.data.model;

import java.util.Comparator;

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

    private boolean isChildVisible;
    private boolean isShadowVisible;

    public boolean isShadowVisible() {
        return isShadowVisible;
    }

    public void setShadowVisible(boolean shadowVisible) {
        isShadowVisible = shadowVisible;
    }

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


    public static class SortByStars implements Comparator<GithubModel> {

        @Override
        public int compare(GithubModel o1, GithubModel o2) {
            try {
                return Integer.valueOf(o1.getStars()).compareTo(Integer.valueOf(o2.getStars()));
            } catch (NumberFormatException e) {
                return 0;
            }

        }
    }

    public static class SortByName implements Comparator<GithubModel> {

        @Override
        public int compare(GithubModel o1, GithubModel o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    }
}