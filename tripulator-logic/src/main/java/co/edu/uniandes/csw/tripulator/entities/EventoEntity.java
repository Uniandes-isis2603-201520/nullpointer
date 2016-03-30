/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.entities;

@Entity
public class EventoEntity extends BaseEntity implements Serializable {

    private String isbn;
    private String image;
    @Temporal(TemporalType.DATE)
    private Date publishDate;
    private String description;

    @ManyToMany
    private List<co.edu.uniandes.csw.bookstore.entities.AuthorEntity> authors = new ArrayList<>();

    @ManyToOne
    private co.edu.uniandes.csw.bookstore.entities.EditorialEntity editorial;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<co.edu.uniandes.csw.bookstore.entities.ReviewEntity> reviews = new ArrayList<>();

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the publishDate
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * @param publishDate the publishDate to set
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public List<co.edu.uniandes.csw.bookstore.entities.AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(List<co.edu.uniandes.csw.bookstore.entities.AuthorEntity> authors) {
        this.authors = authors;
    }

    public co.edu.uniandes.csw.bookstore.entities.EditorialEntity getEditorial() {
        return editorial;
    }

    public void setEditorial(co.edu.uniandes.csw.bookstore.entities.EditorialEntity editorial) {
        this.editorial = editorial;
    }

    public List<co.edu.uniandes.csw.bookstore.entities.ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<co.edu.uniandes.csw.bookstore.entities.ReviewEntity> reviews) {
        this.reviews = reviews;
    }
}

