/*
 * Copyright (c) 2024 Wonderful Kenect Systems
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Represents a Contact object with fields such as id, name, email, source, createdAt, and updatedAt.
 * This class includes serialization and deserialization configurations for proper handling
 * of date-time fields in JSON format.
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({ "id", "name", "email", "source", "createdAt", "updatedAt" })  // Ensures the correct order
public class Contact {

    /**
     * The unique identifier for the contact.
     */
    @JsonProperty("id")
    private Long id;

    /**
     * The name of the contact.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The email address of the contact.
     */
    @JsonProperty("email")
    private String email;

    /**
     * The source of the contact, default value is "KENECT_LABS".
     */
    @JsonProperty("source")
    private String source = "KENECT_LABS";

    /**
     * The date and time when the contact was created, in the format "yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX".
     * This is serialized and deserialized as an {@link OffsetDateTime} object.
     */
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX", timezone = "UTC")
    private OffsetDateTime createdAt;

    /**
     * The date and time when the contact was last updated, in the format "yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX".
     * This is serialized and deserialized as an {@link OffsetDateTime} object.
     */
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX", timezone = "UTC")
    private OffsetDateTime updatedAt;

    // Getters and Setters

    /**
     * Gets the unique identifier of the contact.
     *
     * @return the contact's id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the contact.
     *
     * @param id the contact's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the contact.
     *
     * @return the contact's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the contact.
     *
     * @param name the contact's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the contact.
     *
     * @return the contact's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the contact.
     *
     * @param email the contact's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the source of the contact. Default is "KENECT_LABS".
     *
     * @return the source of the contact
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source of the contact.
     *
     * @param source the source of the contact
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Gets the creation date and time of the contact.
     *
     * @return the creation {@link OffsetDateTime} of the contact
     */
    public OffsetDateTime getCreated_at() {
        return createdAt;
    }

    /**
     * Sets the creation date and time of the contact.
     *
     * @param createdAt the {@link OffsetDateTime} when the contact was created
     */
    public void setCreated_at(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the last updated date and time of the contact.
     *
     * @return the last updated {@link OffsetDateTime} of the contact
     */
    public OffsetDateTime getUpdated_at() {
        return updatedAt;
    }

    /**
     * Sets the last updated date and time of the contact.
     *
     * @param updatedAt the {@link OffsetDateTime} when the contact was last updated
     */
    public void setUpdated_at(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns a string representation of the Contact object.
     *
     * @return a string that describes the contact
     */
    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", source='" + source + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Parses a string into an {@link OffsetDateTime} object.
     * The input string must be in ISO 8601 format, such as "yyyy-MM-dd'T'HH:mm:ss.SSSZ".
     *
     * @param dateString the string to be parsed into {@link OffsetDateTime}
     * @return the parsed {@link OffsetDateTime}
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static OffsetDateTime parseStringToOffsetDateTime(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            return OffsetDateTime.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + dateString);
            throw e;
        }
    }
}
