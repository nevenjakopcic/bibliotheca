<template>
  <v-card
      class="mx-auto"
      max-width="95%"
  >
    <v-card-text>
      <div>{{ book.author.name }}</div>
      <p class="text-h4 text--primary">
        {{ book.title }}
      </p>
      <p>{{ book.genre.name }}</p>
      <div class="text--primary">
        {{ book.description }}
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
import LoadingMixin from "../mixins/loadingMixin";
import BookService from "../services/bookService";

export default {
  name: "Book",
  mixins: [LoadingMixin],
  methods: {
    async getBook() {
      this.setLoading(true);
      const {
        data: {data}
      } = await BookService.getBook(this.$route.params.id);
      this.book = data;
      this.setLoading(false);
      console.log(data)
    }
  },
  async created() {
    await this.getBook();
  },
  data: () => ({
    book: null
  })
}
</script>