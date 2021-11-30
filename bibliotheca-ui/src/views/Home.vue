<template>
  <v-row class="mt-0">
    <v-col cols="12">
      <v-row>
        <v-col cols="12" md="6">
          <v-text-field
              id="title"
              @input="search"
              outlined
              hide-details
              label="Title"
              dense
              clearable
              v-model="searchInput.title"
          />
        </v-col>
        <v-col cols="12" md="6">
          <v-select
              id="genre"
              @input="search"
              item-text="name"
              item-value="id"
              :return-object="false"
              outlined
              label="Genres"
              hide-details
              :items="genres"
              :loading="genresLoading"
              :disabled="genresLoading"
              dense
              clearable
              v-model="searchInput.genre"
          />
        </v-col>
        <v-col cols="12" v-if="isAdmin" class="text-right">
          <v-btn
              color="primary"
              small
              class="mr-3"
              @click="newGenreDialog = true"
          >
            {{ "New genre" }}
          </v-btn>
          <v-btn
              color="error"
              small
              class="mr-3"
              @click="newAuthorDialog = true"
          >
            {{ "New author" }}
          </v-btn>
          <v-btn color="success" small @click="newBookDialog = true">
            {{ "New book" }}
          </v-btn>
        </v-col>
      </v-row>
    </v-col>
    <v-col cols="12" class="text-center text-h5" v-if="books.length === 0 && !loading">
      <v-icon color="red" class="mr-2">mdi-alert</v-icon>
      {{ "No books found" }}
    </v-col>
    <template v-else v-for="b in books">
      <v-col cols="12" md="3" :key="b.id">
        <v-card rounded="lg">
          <v-card-title @click="viewBook(b.id)" class="clickable">
            {{ b.title }}
            <span class="grey--text text--darken-1 text-caption ml-1">
              ({{ b.genre.name }})
            </span>
          </v-card-title>
          <v-card-subtitle>
            {{ b.author.name }}
          </v-card-subtitle>
          <v-card-subtitle>
            <template v-if="b.reservationId == null">
              <v-icon color="green" class="mt-n1" small>
                mdi-check-circle
              </v-icon>
              {{ "Available" }}
            </template>
            <template v-else>
              <v-icon color="red" class="mt-n1" small>
                mdi-close-circle
              </v-icon>
              {{ "Borrowed" }}
            </template>
          </v-card-subtitle>
          <v-divider />
          <v-card-text class="text-center text-md-right">
            <v-btn v-if="!isAdmin"
                :disabled="b.reservationId !== null"
                small
                color="success"
                class="mr-2"
                @click="reserveBook(b.id)"
            >
              {{ "Borrow" }}
            </v-btn>
            <v-btn v-if="isAdmin"
                :disabled="b.reservationId === null"
                small
                color="error"
                @click="unassignBook(b.id)"
            >
              {{ "Return book" }}
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </template>
    <header-dialog
        max-width="50%"
        v-model="newGenreDialog"
        title="New genre"
        @close="resetNewGenreDialog"
    >
      <validation-observer ref="newGenreForm" v-slot="{ handleSubmit }">
        <form @submit.prevent="handleSubmit(addNewGenre)">
          <v-row class="mt-1">
            <v-col cols="12">
              <validation-provider
                  vid="newGenreName"
                  name="New genre name"
                  rules="required"
                  v-slot="{ errors, valid, untouched, required, failed }"
              >
                <v-text-field
                    id="newGenreName"
                    v-model="newGenreName"
                    :error-messages="errors"
                    :hide-details="valid || (untouched && !failed)"
                    dense
                >
                  <template #label>
                    <required-icon v-show="required" />
                    <span>{{ "New genre name" }}</span>
                  </template>
                </v-text-field>
              </validation-provider>
            </v-col>
            <v-col cols="12" class="text-center text-md-right mt-2">
              <v-btn
                  :loading="dialogLoading"
                  :disabled="dialogLoading"
                  small
                  type="submit"
                  color="primary"
              >
                {{ "Create" }}
              </v-btn>
            </v-col>
          </v-row>
        </form>
      </validation-observer>
    </header-dialog>
    <header-dialog
        max-width="50%"
        v-model="newAuthorDialog"
        title="New genre"
        @close="resetNewAuthorDialog"
    >
      <validation-observer ref="newAuthorForm" v-slot="{ handleSubmit }">
        <form @submit.prevent="handleSubmit(addNewAuthor)">
          <v-row class="mt-1">
            <v-col cols="12">
              <validation-provider
                  vid="newAuthorName"
                  name="New author name"
                  rules="required"
                  v-slot="{ errors, valid, untouched, required, failed }"
              >
                <v-text-field
                    id="newAuthorName"
                    v-model="newAuthorName"
                    :error-messages="errors"
                    :hide-details="valid || (untouched && !failed)"
                    dense
                >
                  <template #label>
                    <required-icon v-show="required" />
                    <span>{{ "New author name" }}</span>
                  </template>
                </v-text-field>
              </validation-provider>
            </v-col>
            <v-col cols="12" class="text-center text-md-right mt-2">
              <v-btn
                  :loading="dialogLoading"
                  :disabled="dialogLoading"
                  small
                  type="submit"
                  color="primary"
              >
                {{ "Create" }}
              </v-btn>
            </v-col>
          </v-row>
        </form>
      </validation-observer>
    </header-dialog>
    <header-dialog
        max-width="50%"
        v-model="newBookDialog"
        title="New book"
        @close="resetNewBookDialog"
    >
      <validation-observer ref="newBookForm" v-slot="{ handleSubmit }">
        <form @submit.prevent="handleSubmit(addNewBook)">
          <v-row class="mt-1">
            <v-col cols="12">
              <validation-provider
                  vid="newBookName"
                  name="Book title"
                  rules="required"
                  v-slot="{ errors, valid, untouched, required, failed }"
              >
                <v-text-field
                    id="newBookName"
                    v-model="newBook.title"
                    :error-messages="errors"
                    :hide-details="valid || (untouched && !failed)"
                    dense
                >
                  <template #label>
                    <required-icon v-show="required" />
                    <span>{{ "Book title" }}</span>
                  </template>
                </v-text-field>
              </validation-provider>
            </v-col>
            <v-col cols="12">
              <validation-provider
                  vid="newGenre"
                  name="Genre"
                  rules="required"
                  v-slot="{ errors, valid, untouched, required, failed }"
              >
                <v-select
                    id="newGenre"
                    :error-messages="errors"
                    :hide-details="valid || (untouched && !failed)"
                    dense
                    item-text="name"
                    item-value="id"
                    :return-object="false"
                    :items="genres"
                    v-model="newBook.genre"
                    clearable
                >
                  <template #label>
                    <required-icon v-show="required" />
                    <span>{{ "Genre" }}</span>
                  </template>
                </v-select>
              </validation-provider>
            </v-col>
            <v-col cols="12">
              <validation-provider
                  vid="newAuthor"
                  name="Author"
                  rules="required"
                  v-slot="{ errors, valid, untouched, required, failed }"
              >
                <v-select
                    id="newAuthor"
                    :error-messages="errors"
                    :hide-details="valid || (untouched && !failed)"
                    dense
                    item-text="name"
                    item-value="id"
                    :return-object="false"
                    :items="authors"
                    v-model="newBook.author"
                    clearable
                >
                  <template #label>
                    <required-icon v-show="required" />
                    <span>{{ "Author" }}</span>
                  </template>
                </v-select>
              </validation-provider>
            </v-col>
            <v-col cols="12">
              <validation-provider
                  vid="newBookDescription"
                  name="Book description"
                  rules="required"
                  v-slot="{ errors, valid, untouched, required, failed }"
              >
                <v-textarea
                    id="newBookDescription"
                    v-model="newBook.description"
                    :error-messages="errors"
                    :hide-details="valid || (untouched && !failed)"
                    dense
                >
                  <template #label>
                    <required-icon v-show="required" />
                    <span>{{ "Book description" }}</span>
                  </template>
                </v-textarea>
              </validation-provider>
            </v-col>
            <v-col cols="12" class="text-center text-md-right mt-2">
              <v-btn
                  :loading="newBookLoading"
                  :disabled="newBookLoading"
                  small
                  type="submit"
                  color="primary"
              >
                {{ "Create" }}
              </v-btn>
            </v-col>
          </v-row>
        </form>
      </validation-observer>
    </header-dialog>
  </v-row>
</template>

<style scoped>
.clickable {
  cursor: pointer;
}
</style>

<script>
import debounce from "debounce";
import UserMixin from "../mixins/userMixin";
import MembershipMixin from "../mixins/membershipMixin";
import LoadingMixin from "../mixins/loadingMixin";
import HeaderDialog from "../components/HeaderDialog";
import routeNames from "../router/routeNames";
import BookService from "../services/bookService";

export default {
  name: "Home",
  mixins: [UserMixin, MembershipMixin, LoadingMixin],
  components: {
    HeaderDialog
  },
  async created() {
    await this.getGenres();
    await this.getBooks();
    await this.getAuthors();
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      if (!vm.validMembership) {
        vm.$emit("show-snackbar", {
          color: "error",
          message: "Extend your membership to gain access",
        });
        next({ name: routeNames.HOME });
      } else {
        next();
      }
    });
  },
  methods: {
    async unassignBook(bookId) {
      try {
        await BookService.returnBook(bookId);
        this.$emit("show-snackbar", {
          color: "success",
          message: "Book returned"
        });
      } catch (e) {
        this.$emit("show-snackbar", {
          color: "error",
          message: "Book returning failed"
        });
      } finally {
        await this.getBooks();
      }
    },
    async getBooks() {
      this.setLoading(true);
      const {
        data: { data }
      } = await BookService.getBooks(
          this.searchInput.title,
          this.searchInput.genre
      );
      this.books = data;
      this.setLoading(false);
    },
    async reserveBook(bookId) {
      try {
        await BookService.assignToCurrentUser(bookId);
        this.$emit("show-snackbar", {
          color: "success",
          message: "Book borrowed"
        });
      } catch (e) {
        this.$emit("show-snackbar", {
          color: "error",
          message: "Book borrowing failed"
        });
      } finally {
        await this.getBooks();
      }
    },
    canUnassignBook(userId) {
      if (userId && (this.user.id === userId || this.isAdmin)) {
        return true;
      }
      return false;
    },
    async getGenres() {
      this.genresLoading = true;
      const {
        data: { data }
      } = await BookService.getAllGenres();
      this.genres = data;
      this.genresLoading = false;
    },
    async getAuthors() {
      this.authorsLoading = true;
      const {
        data: { data }
      } = await BookService.getAllAuthors();
      this.authors = data;
      this.authorsLoading = false;
    },
    search: debounce(async function() {
      await this.getBooks();
    }, 750),
    async addNewBook() {
      try {
        this.dialogLoading = true;
        await BookService.createBook({
          title: this.newBook.title,
          genreId: this.newBook.genre,
          authorId: this.newBook.author,
          description: this.newBook.description
        });
        this.$emit("show-snackbar", {
          color: "success",
          message: "Book created"
        });
      } catch (e) {
        this.$emit("show-snackbar", {
          color: "error",
          message: "Book creation failed"
        });
      } finally {
        this.dialogLoading = false;
        await this.getBooks();
        this.resetNewBookDialog();
      }
    },
    async addNewGenre() {
      try {
        this.dialogLoading = true;
        await BookService.createGenre(this.newGenreName);
        this.$emit("show-snackbar", {
          color: "success",
          message: "Genre created"
        });
      } catch(e) {
        this.$emit("show-snackbar", {
          color: "error",
          message: "Genre creation failed"
        });
      } finally {
        this.dialogLoading = false;
        await this.getGenres();
        this.resetNewGenreDialog();
      }
    },
    async addNewAuthor() {
      try {
        this.dialogLoading = true;
        await BookService.createAuthor(this.newAuthorName);
        this.$emit("show-snackbar", {
          color: "success",
          message: "Author created"
        });
      } catch(e) {
        this.$emit("show-snackbar", {
          color: "error",
          message: "Author creation failed"
        });
      } finally {
        this.dialogLoading = false;
        await this.getAuthors();
        this.resetNewAuthorDialog();
      }
    },
    resetNewBookDialog() {
      this.newBook.type = null;
      this.newBook.name = null;
      this.$refs.newBookForm.reset();
      this.newBookDialog = false;
    },
    resetNewGenreDialog() {
      this.newGenreName = null;
      this.$refs.newGenreForm.reset();
      this.getGenres();
      this.newGenreDialog = false;
    },
    resetNewAuthorDialog() {
      this.newAuthorName = null;
      this.$refs.newAuthorForm.reset();
      this.getAuthors();
      this.newAuthorsDialog = false;
    },
    viewBook(id) {
      this.$router.push({ name: routeNames.BOOK, params: { id } })
    }
  },
  data: () => ({
    genresLoading: false,
    authorsLoading: false,
    newBook: {
      title: null,
      genre: null,
      author: null,
      description: null
    },
    newGenreName: null,
    newAuthorName: null,
    newBookLoading: false,
    dialogLoading: false,
    newBookDialog: false,
    newGenreDialog: false,
    newAuthorDialog: false,
    searching: false,
    books: [],
    genres: [],
    authors: [],
    searchInput: {
      title: null,
      genre: null,
    }
  })
};
</script>

<style scoped>
::v-deep .v-input--selection-controls {
  margin-top: 0;
  padding-top: 4px;
}
</style>
