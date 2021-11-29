import { mapGetters, mapActions } from "vuex";

export default {
  computed: {
    ...mapGetters(["membership", "validMembership"])
  },
  methods: {
    ...mapActions(["setMembership"])
  }
}