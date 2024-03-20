package com.lab52;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

import io.cucumber.java.ParameterType;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSearchSteps {

    private final Library library = new Library();
    private final List<Book> result = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();

    @Given("a book with the title {string}, written by {string}, published in {int}")
    public void a_book_with_the_title_written_by_published_in(final String title, final String author, final Integer year) {
        library.addBook(new Book(title, author, Date.from(LocalDateTime.of(year, 1, 1, 0, 0).toInstant(ZoneOffset.UTC))));
    }

    @And("another book with the title {string}, written by {string}, published in {int}")
    public void another_book_with_the_title_written_by_published_in(final String title, final String author, final Integer year) {
        library.addBook(new Book(title, author, Date.from(LocalDateTime.of(year, 1, 1, 0, 0).toInstant(ZoneOffset.UTC))));
    }

    @When("the customer searches for books published between {int} and {int}")
    public void the_customer_searches_for_books_published_between_and(final Integer from, final Integer to) {
        result.addAll(library.findBooks(Date.from(LocalDateTime.of(from, 1, 1, 0, 0).toInstant(ZoneOffset.UTC)),
                Date.from(LocalDateTime.of(to, 1, 1, 0, 0).toInstant(ZoneOffset.UTC))));
    }

    @Then("{int} books should have been found")
    public void books_should_have_been_found(final Integer booksFound) {
        assertEquals(booksFound, result.size());
    }

    @Then("Book {int} should have the title {string}")
    public void book_should_have_the_title(final Integer position, final String title) {
        assertEquals(title, result.get(position - 1).getTitle());
    }

    @And("Book {int} should have the author {string}")
    public void book_should_have_the_author(final Integer position, final String author) {
        assertEquals(author, result.get(position - 1).getAuthor());
    }

    @And("Book {int} should have been published in {int}")
    public void book_should_have_been_published_in(final Integer position, final Integer year) {
        assertEquals(year, result.get(position - 1).getPublished().toInstant().atZone(ZoneOffset.UTC).toLocalDate().getYear());
    }
    
}
