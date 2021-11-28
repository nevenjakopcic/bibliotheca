import { mapGetters, mapActions } from "vuex";

export default {
  computed: {
    ...mapGetters(["loading"])
  },
  methods: {
    ...mapActions(["setLoading"])
  }
}