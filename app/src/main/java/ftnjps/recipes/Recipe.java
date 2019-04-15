package ftnjps.recipes;

import java.util.Date;

public class Recipe {

    private static Long id_counter = 1l;

    private Long id;
    private String imgURL;
    private String title;
    private String description;
    private String difficulty;
    private int numberOfPeople;
    private int timeOfPreparation;
    private String preparationSteps;
    private Date creationDate;

    public Recipe() {}

    public Recipe(String imgURL, String title, String description, String difficulty, int numberOfPeople, int timeOfPreparation, String preparationSteps, Date creationDate) {
        this.imgURL = imgURL;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.numberOfPeople = numberOfPeople;
        this.timeOfPreparation = timeOfPreparation;
        this.preparationSteps = preparationSteps;
        this.creationDate = creationDate;
        this.id = id_counter++;
    }

    public static Long getId_counter() {
        return id_counter;
    }

    public static void setId_counter(Long id_counter) {
        Recipe.id_counter = id_counter;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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


}
