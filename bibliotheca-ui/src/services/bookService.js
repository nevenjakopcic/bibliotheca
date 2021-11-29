import axios from "axios";

export default {
  async getAllGenres() {
    return await axios.get("/book/genres");
  },
  async getAllAuthors() {
    return await axios.get("/book/authors");
  },
  async createGenre(name) {
    return await axios.post("/book/genre", {
      name
    });
  },
  async createAuthor(name) {
    return await axios.post("/book/author", {
      name
    });
  },
  async createBook({ title, genreId, authorId, description }) {
    return await axios.post("/book", {
      title,
      genreId,
      authorId,
      description
    });
  },
  async assignToCurrentUser(bookId) {
    return await axios.post(`/book/borrow/${bookId}`);
  },
  async returnBook(bookId) {
    return await axios.post(`/book/return/${bookId}`);
  },
  async getBooks(title, genre) {
    if (title === "") {
      title = null;
    }
    return await axios.get("/book", {
      params: {
        title,
        genre
      }
    });
  }
};