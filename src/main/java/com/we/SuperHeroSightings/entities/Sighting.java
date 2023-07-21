
package com.we.SuperHeroSightings.entities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author jtriolo
 */
public class Sighting {
    
    private int id;
    
    @NotBlank(message = "Description must not be blank.")
    @Size(max = 255, message="Description must be fewer than 255 characters")
    private String description;  
    
    
    //@NotBlank(message = "Please provide a date.")
//    @NotEmpty //validates that the property is not null or empty; can be applied to String, Collection, Map or Array values.
    //@JsonDeserialize(using = localDateTimeDeserializer.class)
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ssZ", iso = DateTimeFormat.ISO.DATE_TIME)
    //@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ssZ", iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "The date must not be empty")
    @Past(message = "The date must be in the past")
    private LocalDateTime date;
    
    private Hero hero;    
    private Location location;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.description);
        hash = 13 * hash + Objects.hashCode(this.date);
        hash = 13 * hash + Objects.hashCode(this.hero);
        hash = 13 * hash + Objects.hashCode(this.location);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.hero, other.hero)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sighting{" + "id=" + id + ", description=" + description + ", date=" + date + ", hero=" + hero + ", location=" + location + '}';
    }   
    
    
}
//
//class localDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
//  private static final long serialVersionUID = 9152770723354619045L;
//  public localDateTimeDeserializer() { this(null);}
//  protected localDateTimeDeserializer(Class<LocalDateTime> type) { super(type);}
//
//  @Override
//  public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
//      throws IOException, JsonProcessingException {
//    if (parser.getValueAsString().isEmpty()) {
//       return null;
//    }
//    return LocalDateTime.parse(parser.getValueAsString(),
//                                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"));
//  }
//}