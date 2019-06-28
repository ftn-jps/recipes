package ftnjps.recipes.data;

import java.io.Serializable;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe implements Serializable {

    //private static Long id_counter = 1l;

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String imgURL;
    private String youtubeURL;
    private String title;
    private String description;
    private String difficulty;
    private int numberOfPeople;
    private int timeOfPreparation;
    private String preparationSteps;
    private Date creationDate;
    private Double latitude;
    private Double longitude;
    private boolean isFavorite = false;

    public Recipe() {
    }

    public Recipe(String imgURL, String title, String description, String difficulty, int numberOfPeople, int timeOfPreparation, String preparationSteps, Date creationDate, Double latitude, Double longitude, String youtubeURL) {
        this.imgURL = imgURL;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.numberOfPeople = numberOfPeople;
        this.timeOfPreparation = timeOfPreparation;
        this.preparationSteps = preparationSteps;
        this.creationDate = creationDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.youtubeURL = youtubeURL;
    }

    public String getYoutubeURL() {
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getTimeOfPreparation() {
        return timeOfPreparation;
    }

    public void setTimeOfPreparation(int timeOfPreparation) {
        this.timeOfPreparation = timeOfPreparation;
    }

    public String getPreparationSteps() {
        return preparationSteps;
    }

    public void setPreparationSteps(String preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    public String getRecipeDescription() {
        return title;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.title = recipeDescription;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

}
